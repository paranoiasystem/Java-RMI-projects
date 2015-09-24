
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;


public class Client extends UnicastRemoteObject implements IClient {
	
	static Logger logger = Logger.getLogger("global");
	private static final long serialVersionUID = 1L; //nello scritto ho messo public
	static String username;
	static final String PROMPT = ">>";

	public Client() throws RemoteException {
		super();
	}

	@Override
	public void iscritto(String username) throws RemoteException {
		/* sbagliata condizione dell'if */
		if (!Client.username.equals(username)) { //aggiustata condizione dell'if
			System.out.print(username + " si è iscritto\n" + PROMPT);
		}
		
	}

	@Override
	public void abbandonato(String username) throws RemoteException {
		System.out.print(username + " ha abbandonato\n" + PROMPT);
		
	}

	@Override
	public void scritto(String username, String messaggio) throws RemoteException {
		System.out.print(username + ": " + messaggio + "\n" + PROMPT);
		
	}

	@Override
	public void notifica(String notifica) throws RemoteException {
		System.out.print(notifica + "\n" + PROMPT);
		
	}
	
	@Override
	public void scollegati() throws RemoteException {
		System.out.println("Mi scollego!");
		System.exit(0);
		
	}
	
	public static void main(String[] args) {
		/*
		 * correzione, nello scritto ho sbagliato richiamando SecurityManager al posto di setSecurityManager
		 * e ho mancato il new nel parametro della funzione
		 */
		System.setSecurityManager(new RMISecurityManager());
		Scanner s = new Scanner(System.in);
		Client myself = null;
		IServer server = null;
		String cmd = null;
		username = args[0];
		try {
			myself = new Client();
			server = (IServer) Naming.lookup("rmi://localhost/esame"); // nello scritto ho sbagliato il nome del server
			//controllo per verificare se l'username è occupato, altrimenti inserisco un nuovo username
			while(!server.iscrivo(myself, username)){
				System.out.print("Nuovo username: ");
				username = s.nextLine();
			}
		} catch (Exception e) {
			logger.severe("Errore lookup");
			e.printStackTrace();
		}
		//Aggiunta stampa di benvenuto
		System.out.print("Benvenuto " + Client.username + ", !help per i comandi\n" + PROMPT);
		while (true) {
			try {
				cmd = s.nextLine();
				if (cmd.startsWith("!")) {
					//cambiata struttura degli if per avere un miglior controllo
					if (cmd.contains("mipiace")) {
						//aggiunto try - catch per controllare l'input
						try {
							server.mipiace(username, Integer.parseInt(cmd.substring(9))); //aggiunto parsing da stringa ad intero
						} catch (Exception e) {
							System.out.print("Errore nel comando\n" + PROMPT);
						}
					} else if (cmd.contains("count")) {
						//aggiunto try - catch per controllare l'input
						try {
							server.countmipiace(username, Integer.parseInt(cmd.substring(7))); //aggiunto parsing da stringa ad intero
						} catch (Exception e) {
							System.out.print("Errore nel comando\n" + PROMPT);
						}
					} else if (cmd.contains("abbandono")) {
						server.abbandono(username);
					} else if (cmd.contains("help")) {
						System.out.print("!help: mostra questo aiuto\n"
								+ "!mipiace n: mette mi piace al messaggio\n"
								+ "!count n: restituisce il numero di mi piace del messaggio\n"
								+ "!abbandono: mi scollego dal server\n"
								+ PROMPT);
					} else {
						System.out.print("Comando non valido\n" + PROMPT); //aggiunto controllo sui comandi non validi
					}
				} else {
					server.scrivo(username, cmd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
