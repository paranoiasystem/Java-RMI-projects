package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.client.Client;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");

	static ArrayList<Iscritto> clientList = new ArrayList<Iscritto>();
	static Scanner scanner = new Scanner(System.in);

	static String parla = null;

	public ServerImpl() throws RemoteException {
		super();
	}

	public static void main(String[] args) throws RemoteException {
		ServerImpl server = null;
		String cmd = null;
		System.setSecurityManager(new RMISecurityManager());

		try {
			server = new ServerImpl();
			Naming.rebind("comizio", server);
			System.out.println("Server Avviato..");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti: " + e.getMessage());
			e.printStackTrace();
		}
		while (true) {
			System.out.print(">> ");
			try {
				cmd = scanner.nextLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (cmd.equals("!help")) {
				System.out.println("!comizio: avvia modalità comizio\n" + "!stop: termina modalità comizio\n"
						+ "!quit: chiude il server");
			}
			if (cmd.equals("!quit")) {
				server.dico("server", "Chiusura server");
				System.exit(0);
			}
			if (cmd.equals("!comizio")) {
				System.out.println("Chi deve parlare?");
				String nome = scanner.nextLine();
				server.dico("server", "Modalità comizio\n Parla: " + nome);
				parla = nome;
			}
			if (cmd.equals("!stop")) {
				if (parla != null) {
					parla = null;
					server.dico("server", "Modalità comizio terminata");
				} else
					System.out.println("Errore, non sei in modalità comizio\n>> ");
			} else {
				server.dico("server", cmd);
			}
		}
	}

	@Override
	public void dico(String username, String message) throws RemoteException {
		if (parla == null) {
			System.out.print(username + ": " + message + "\n"
					+ ">> ");
			for (Iscritto iscritto : clientList)
				iscritto.getId().detto(username, message);
		} else {
			if (parla.equals(username)) {
				System.out.print(username + ": " + message + "\n"
						+ ">> ");
				for (Iscritto iscritto : clientList)
					iscritto.getId().detto(username, message);
			}
		}
	}

	@Override
	public void iscrivo(Client id, String username) throws RemoteException {
		logger.info("\nEntra nel comizio: " + username);
		for (Iscritto iscritto : clientList)
			iscritto.getId().iscritto(username);
		clientList.add(new Iscritto(username, id));
	}

	@Override
	public void abbandono(Client id, String username) throws RemoteException {
		logger.info("\nAbbandona il comizio: " + username);
		for (Iscritto iscritto : clientList){
			if(iscritto.getUsername().equals(username) && iscritto.getId().equals(id))
				clientList.remove(iscritto);
			else 
				iscritto.getId().abbandonato(username);
		}
	}

	@Override
	public void dicoprivato(String destinatario, String username, String message) throws RemoteException {
		if(parla != null) {
			logger.info("\nmessaggio privato da " + username + " a " + destinatario);
			for (Iscritto iscritto : clientList) {
				if (iscritto.getUsername().equals(destinatario)) {
					iscritto.getId().detto(username + "(privato)", message);
				}
			}
		}
		else{
			logger.info("\ntentativo di messaggio privato fuori dalla modalità comizio da " + username + " a " + destinatario);
			for (Iscritto iscritto : clientList) {
				if (iscritto.getUsername().equals(username)) {
					iscritto.getId().detto("server", "Non puoi usare i messaggi privari al di fuori della modalità comizio");
				}
			}
		}
	}

}
