
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements IServer {
	
	static Logger logger = Logger.getLogger("global");
	private static final long serialVersionUID = 1L; //nello scritto ho messo public
	static ArrayList<User> clientList = new ArrayList<User>();
	static ArrayList<Messaggio> messaggiList = new ArrayList<Messaggio>();
	static int count = 0; //contatore dei messaggi

	public Server() throws RemoteException {
		super();
	}

	/*
	 * Ho modificato da void a boolean il valore di ritorno della funzione per permettere all'utente
	 * nel caso l'username sia occupato, di inserirne uno alternativo.
	 * utilizzo synchronized per evitare la presenza di due o più utenti con lo stesso username 
	 */
	@Override
	public synchronized boolean iscrivo(IClient id, String username) throws RemoteException {
		/* 
		 * Istruzioni per controllare se l'username risulta occupato, quindi ho usato anche il syncronized
		 * per evitare che contemporaneamente più utenti potessero iscriversi con lo stesso username
		 */
		boolean occupato = false;
		for (User user : clientList) {
			/*
			 * Nel caso in cui trovo l'username occupato, setto a true la variabile boolean occupato
			 * ed esco dal ciclo
			 */
			if (user.getUsername().equals(username)) {
				occupato = true;
				break;
			}
		}
		/*
		 * controllo il valore di occupato e decido cosa fare
		 */
		if (!occupato) {
			clientList.add(new User(id, username));
			for (User user : clientList) {
				user.getId().iscritto(username);
			}
			logger.info(username + " si è iscritto");
			return true;
		} else {
			id.notifica(username + " già occupato, scegline un altro!"); //cambiato testo della notifica
			return false;
		}
	}

	@Override
	public void abbandono(String username) throws RemoteException {
		for (User user : clientList) {
			/*
			 * nel caso in cui trovo il client da rimuovere, lo rimuovo dalla lista
			 * aggiunto l'else in più a differenza dello scritto
			 */
			if (user.getUsername().equals(username)) {
				clientList.remove(user);
				user.getId().scollegati(); // metodo per l'abbandono sicuro
			} else {
				user.getId().abbandonato(username + " ha abbandonato"); //nello scritto ho mancato il getId()
			}
		}
		
	}

	/*
	 * utilizzo synchronized per evitare la presenza di due o più messaggi con lo stesso id 
	 */
	@Override
	public synchronized void scrivo(String username, String messaggio) throws RemoteException {
		messaggiList.add(new Messaggio(count, username));
		for (User user : clientList) {
			user.getId().scritto(username, "[" + count + "]" + messaggio); //rimosso .toString()
		}
		count++; //aggiorno il contatore dei messaggi
		logger.info(username + " ha scritto un messaggio"); 
	}

	@Override
	public synchronized void mipiace(String username, int n) throws RemoteException {
		/* controllo se l'id del messaggio risulta valido */
		if (n>=0 && n<count) {
			for (Messaggio messaggio : messaggiList) {
				/* trovo il messaggio con lo stesso id passato alla funzione */
				if (messaggio.getId() == n) {
					if (messaggio.getUsername().equals(username)) {
						/* rimosso blocco di codice ripetitivo in favore di una funzione */
						notificaUtente(username, "Non puoi metterti mi piace da solo");
						break;
					} else {
						/* incremento contatore dei mi piace */
						messaggio.mipiaceplus();
						/* aggiunta notifica quando si riceve mi piace */
						notificaUtente(messaggio.getUsername(), "hai un mi piace al messaggio " + messaggio.getId());
						break;
					}
				}
			}
		} else { /* caso in cui l'id del messaggio è errato */
			/* rimosso blocco di codice ripetitivo in favore di una funzione */
			notificaUtente(username, "Id del messaggio inesistente"); 
		}
		
	}

	@Override
	public void countmipiace(String username, int n) throws RemoteException {
		/* controllo se l'id del messaggio risulta valido */
		if (n>=0 && n<count) {
			for (Messaggio messaggio : messaggiList) {
				/* trovo il messaggio con lo stesso id passato alla funzione */
				if (messaggio.getId() == n) {
					if (messaggio.getUsername().equals(username)) {
						/* rimosso blocco di codice ripetitivo in favore di una funzione */
						notificaUtente(username, "Al messaggio " + n + " hai " + messaggio.getCount() + " mi piace");
						break;
					} else {
						/* rimosso blocco di codice ripetitivo in favore di una funzione */
						notificaUtente(username, "Puoi vedere il numeri di mi piace solo dei tuoi messaggi");
						break;
					}
				}
			}
		} else { /* caso in cui l'id del messaggio è errato */
			/* rimosso blocco di codice ripetitivo in favore di una funzione */
			notificaUtente(username, "Id del messaggio inesistente"); 
		}
		
	}
	
	/*
	 * Funzione aggiunta per ottimizzare il riuso del codice
	 * questa funzione invia una notifica ad un determinato utente
	 */
	private void notificaUtente(String username, String notifica) throws RemoteException{
		for (User user : clientList) {
			if (user.getUsername().equals(username)) {
				user.getId().notifica(notifica);
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		/*
		 * correzione, nello scritto ho sbagliato richiamando SecurityManager al posto di setSecurityManager
		 * e ho mancato il new nel parametro della funzione
		 */
		System.setSecurityManager(new RMISecurityManager());
		try {
			Server server = new Server(); //spostato all'interno del try per non inserire il throws nella firma del metodo
			Naming.rebind("esame", server);
			System.out.println("Server avviato...");
		} catch (Exception e) {
			logger.severe("problemi con la rebind");
			e.printStackTrace();
		}
	}

}
