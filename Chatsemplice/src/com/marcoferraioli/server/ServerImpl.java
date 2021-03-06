package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;
import java.rmi.server.*;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerImpl extends UnicastRemoteObject implements Server {
	
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");
	
	ArrayList<Client> clientList = new ArrayList<Client>();
	
	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			ServerImpl server = new ServerImpl();
			Naming.rebind("chat", server);
			System.out.println("Server avviato");
		} catch (Exception e) {
			logger.severe("Problemi con gli oggetti remoti: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public ServerImpl() throws RemoteException {
		super();
	}

	@Override
	public void dici(String username, String message) throws RemoteException {
		for (Client client : clientList) {
			client.detto(username, message);
		}		
	}

	@Override
	public void iscrivi(Client id, String username) throws RemoteException {
		ServerImpl.logger.info("\nEntra nel server " + username);
		for (Client client : clientList) {
			client.iscritto(username);
		}
		this.clientList.add(id);
	}

	@Override
	public void abbandona(Client id, String username) throws RemoteException {
		this.clientList.remove(id);
		ServerImpl.logger.info("\nAbbandona il server " + username);
		for (Client client : clientList) {
			client.abbandonato(username);
		}		
	}

}
