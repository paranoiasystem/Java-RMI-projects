package com.marcoferraioli.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.marcoferraioli.client.Client;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");
	
	static ArrayList<Iscritto> userList = new ArrayList<Iscritto>();
	static boolean pong = false;
	static boolean game = false;

	protected ServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public synchronized void dici(String username, String message) throws RemoteException {
		if(pong && game && message.equals("pong")){
			pong = false;
			
			for (Iscritto iscritto : userList)
				if(iscritto.username.equals(username)){
					iscritto.punteggio += 1; 
					if(iscritto.punteggio == 10){
						dici("Server", iscritto.username + " ha vinto");
						game = false;
					}
				}
		}
		
		for (Iscritto iscritto : userList) {
			try {
				iscritto.id.detto(username, message);
			} catch(Exception e) {
				logger.info(username + " non risulta rangiungibile, elimino il suo riferimento");
				userList.remove(iscritto);
			}
		}
		
	}

	@Override
	public void iscrivi(Client id, String username) throws RemoteException {
			userList.add(new Iscritto(id, username));
			for (Iscritto iscritto : userList) {
				iscritto.id.iscritto(username);
			}
		
	}
	
	@Override
	public void abbandono(Client id, String username) throws RemoteException {
		for (Iscritto iscritto : userList){
			if(iscritto.username.equals(username) && iscritto.id.equals(id))
				userList.remove(iscritto);
			else 
				iscritto.id.abbandonato(username);
		}
		
	}

}
