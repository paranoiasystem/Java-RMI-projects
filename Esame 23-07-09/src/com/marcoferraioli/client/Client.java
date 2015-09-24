package com.marcoferraioli.client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.server.IServer;

public class Client extends UnicastRemoteObject implements IClient {

	static Logger logger = Logger.getLogger("global");
	private static final long serialVersionUID = 1L;
	static String nickname = null;

	public Client() throws RemoteException {
		super();
	}

	@Override
	public void notifica(String notifica) throws RemoteException {
		System.out.println(notifica);
	}

	public static void main(String[] args) {
		Client myself = null;
		IServer server = null;
		Scanner scanner = new Scanner(System.in);
		String cmd = null;
		
		System.setSecurityManager(new RMISecurityManager());
		
		boolean value = false;
		while(!value){
		System.out.print("Inserisci l'username: ");
		nickname = scanner.nextLine();
			try {
				server = (IServer) Naming.lookup("rmi://localhost/meteo");
				myself = new Client();
				value = server.iscrivo(myself, nickname);
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
					if(cmd.startsWith("!temperatura")){
						System.out.print("Inserisci la temperatura: ");
						server.setTemperature(nickname, scanner.nextInt());
					}
					if (cmd.startsWith("!connessi")) {
						server.mostraConnessi(nickname);						
					}
					if (cmd.startsWith("!vicino")) {
						System.out.print("Nome vicino: ");
						server.addVicino(nickname, scanner.nextLine());
					}
					if (cmd.startsWith("!alert")) {
						System.out.print("Messaggio d'allerta: ");
						server.allertaVicini(nickname, scanner.nextLine());
					}
					if (cmd.startsWith("!quit")) {
						server.addandono(nickname);
					}
				} else {
					server.dico(nickname, cmd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
