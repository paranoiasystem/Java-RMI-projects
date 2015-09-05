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
	static String username;

	protected ClientImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		String cmd;
		Scanner scanner = new Scanner(System.in);
		System.setSecurityManager(new RMISecurityManager());
		Server server = null;
		Client myself = null;
		
		boolean value = false;
		while(!value){
		System.out.print("Inserisci l'username: ");
		username = scanner.nextLine();
			try {
				server = (Server) Naming.lookup("rmi://localhost/chat");
				myself = new ClientImpl();
				value = server.iscrivo(myself, username);
			} catch (Exception e) {
				logger.severe("Non trovo il server o non riesco ad iscrivermi.");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		while(true){
			try {
				cmd = scanner.nextLine();
				if(cmd.startsWith("!")){
					if(cmd.startsWith("!credito")){
						server.credito(username);
					}
					if(cmd.startsWith("!regala")){
						server.regala(username, cmd.substring(8));
					}
					if(cmd.startsWith("!dico")){
						System.out.print("Messaggio: ");
						String mes = scanner.nextLine();
						server.dico(new Messaggio(username, mes), cmd.substring(6));
					}
					if(cmd.startsWith("!ricarica")){
						server.ricarica(username);
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
		if(!username.equals(ClientImpl.username))
			System.out.println(username + " si è iscritto");
	}

	@Override
	public void abbandonato(String username) throws RemoteException {
		if(!username.equals(ClientImpl.username))
			System.out.println(username + " ha abbandonato");
	}

	@Override
	public void detto(Messaggio m) throws RemoteException {
		if(!m.username.equals(username))
			System.out.println(m.username + ": " + m.message);		
	}

	@Override
	public void notifica(String notifica) throws RemoteException {
		System.out.println(notifica);
	}
	
}
