package com.marcoferraioli.client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.server.Server;

public class ClientImpl extends UnicastRemoteObject implements Client {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");

	static String username;

	protected ClientImpl() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		Server server = null;
		Client myself = null;
		String cmd = "";

		System.out.print("Inserisci un username: ");
		username = scanner.nextLine();

		System.setSecurityManager(new RMISecurityManager());
		try {
			server = (Server) Naming.lookup("rmi://localhost/server");
			myself = new ClientImpl();
			server.iscrivo(myself, username);

		} catch (Exception e) {
			logger.severe("Non trovo il server o non riesco ad iscrivermi.");
			e.printStackTrace();
			System.exit(1);
		}
		while (true) {
			System.out.print(">> ");
			try {
				cmd = scanner.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (cmd.equals("!help")) {
				System.out.print("!privato: avvia modalità messaggio privato\n"
						+ "!stop: termina modalità, questa modalità funziona solo durante il comizio\n"
						+ ">> ");
			}
			if (cmd.equals("!quit")) {
				try {
					server.abbandono(myself, username);
					System.exit(0);
				} catch (Exception e) {
					logger.severe("Non riesco ad abbandonare.");
					e.printStackTrace();
					System.exit(1);
				}
			} else {
				if (cmd.length() != 0) {
					try {
						server.dico(username, cmd);
					} catch (Exception e) {
						logger.severe("Non riesco ad inviare il messaggio.");
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		}
	}

	@Override
	public void detto(String username, String message) throws RemoteException {
		if(!username.equals(ClientImpl.username))
			System.out.print(username + ": " + message + "\n"
				+ ">> ");
	}

	@Override
	public void iscritto(String username) throws RemoteException {
		System.out.print(username + " si è iscritto\n"
				+ ">> ");
	}

	@Override
	public void abbandonato(String username) throws RemoteException {
		System.out.println(username + " ha abbandonato\n"
				+ ">> ");
	}

}
