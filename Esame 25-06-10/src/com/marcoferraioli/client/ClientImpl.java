package com.marcoferraioli.client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.server.Server;
import com.marcoferraioli.utility.Messaggio;

public class ClientImpl extends UnicastRemoteObject implements Client {

	static Logger logger = Logger.getLogger("global");
	private static final long serialVersionUID = 1L;
	static String username = null;
	static String password = null;

	protected ClientImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String cmd = null;
		
		Client myself = null;
		Server server = null;
		
		System.out.print("Inserisci l'username: ");
		username = scanner.nextLine();
		
		System.out.print("Inserisci la password: ");
		password = scanner.nextLine();

		System.setSecurityManager(new RMISecurityManager());
		try {
			server = (Server) Naming.lookup("rmi://localhost/facebook");
			myself = new ClientImpl();
			server.iscrivo(myself, username, password);
		} catch (Exception e) {
			logger.severe("Non trovo il server o non riesco ad iscrivermi.");
			e.printStackTrace();
			System.exit(1);
		}
		while(true){
			try {
				cmd = scanner.nextLine();
				if(cmd.startsWith("!")){
					if(cmd.equals("!list")){
						server.chiedoLista(username, myself);
					}
					if(cmd.startsWith("!status")){
						if(!cmd.equals("!status"))
							server.cambiaStato(username, cmd.substring(8));
						else
							System.out.println("Hai dimenticato lo stato");
					}
					if(cmd.startsWith("!commenta")){
						if(!cmd.equals("!commenta")){
							String commento = cmd.substring(10) + ":";
							System.out.print("Commento: ");
							commento += scanner.nextLine();
							server.commento(username, commento);
						} else {
							System.out.println("Hai dimenticato il nome dell'utente");
						}
					}
				} else {
					server.dico(new Messaggio(username, cmd));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void iscritto(String username) throws RemoteException {
		if(!username.equals(username))
			System.out.println(username + " si è iscritto");

	}

	@Override
	public void abbandonato(String username) throws RemoteException {
		if(!username.equals(username))
			System.out.println(username + " si è  disconesso");

	}

	@Override
	public void detto(Messaggio m) throws RemoteException {
		if(!m.username.equals(username))
			System.out.println(m.username + ": " + m.messaggio);

	}

	@Override
	public void stampoLista(String lista) throws RemoteException {
		System.out.print(lista);

	}

	@Override
	public void notifica(String notifica) throws RemoteException {
		System.out.println(notifica);

	}

}
