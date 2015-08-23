package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.marcoferraioli.client.Client;

public class ServerImpl extends UnicastRemoteObject implements Server{

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");

	static ArrayList<Iscritto> clientList = new ArrayList<Iscritto>();
	
	protected ServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		ServerImpl server = null;
		System.setSecurityManager(new RMISecurityManager());

		try {
			server = new ServerImpl();
			Naming.rebind("server", server);
			System.out.println("Server Avviato..");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void verificaStato() throws RemoteException{
		int felici = 0;
		float value = 0f;
		for (Iscritto iscritto : clientList)
			if(iscritto.isFelice())
				felici++;
		//da controllare
		value = 0.75f * clientList.size();
		if(felici > value)
			for (Iscritto iscritto : clientList) 
					iscritto.getId().detto("Server", "Yuuu!");
		if (felici < value)
			for (Iscritto iscritto : clientList) 
				iscritto.getId().detto("Server", "Buuu!");
		//da controllare
	}
	
	public void setStato(String username, boolean stato) throws RemoteException {
		for (Iscritto iscritto : clientList) {
			if(iscritto.getUsername().equals(username) && stato)
				iscritto.setFelice(true);
			else if (iscritto.getUsername().equals(username) && !stato)
				iscritto.setFelice(false);
		}
		verificaStato();
	}
	
	@Override
	public void dico(String username, String message) throws RemoteException {
		if (message.equals("!felice")) {
			message = "è felice";
			setStato(username, true);
			}
		if (message.equals("!serio")) {
			message = "è serio";
			setStato(username, false);
			}
		for (Iscritto iscritto : clientList) {
			try{
				iscritto.getId().detto(username, message);
			} catch(Exception e) {
				logger.info(username + " non risulta rangiungibile, elimino il suo riferimento");
				clientList.remove(iscritto);
			}
		}
	}

	@Override
	public void iscrivo(Client id, String username) throws RemoteException {
		logger.info("Entra nel server " + username);
		for (Iscritto iscritto : clientList) {
			iscritto.getId().iscritto(username);
		}
		clientList.add(new Iscritto(id, username));
		verificaStato();
	}

	@Override
	public void abbandono(Client id, String username) throws RemoteException {
		logger.info("Esce dal server " + username);
		for (Iscritto iscritto : clientList) {
			if(iscritto.getId().equals(id) && iscritto.getUsername().equals(username))
				clientList.remove(iscritto);
			else
				iscritto.getId().abbandonato(username);
		}
		verificaStato();
	}
	
}
