package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.marcoferraioli.client.Client;
import com.marcoferraioli.utility.Messaggio;

public class ServerImpl extends UnicastRemoteObject implements Server {
	
	static Logger logger = Logger.getLogger("global");
	public final static long serialVersionUID = 1L;
	static ArrayList<Iscritto> clientList = new ArrayList<Iscritto>();
	
	public ServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		try {
			ServerImpl server = new ServerImpl();
			Naming.rebind("chat", server);
			System.out.println("Server avviato...");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti... " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void dico(Messaggio m) throws RemoteException {
		logger.info(m.username + " ha inviato un messaggio");
		for (Iscritto iscritto : clientList) {
			if (iscritto.username.equals(m.username)) {
				if(iscritto.credito > 0){
					iscritto.credito--;
					for (Iscritto i : clientList) {
						i.id.detto(m);
					}
					break;
				} else {
					iscritto.id.notifica("Credito insufficiente");
					break;
				}
			}
		}
	}

	@Override
	public void dico(Messaggio m, String usernameDest) throws RemoteException {
		logger.info(m.username + " ha inviato un messaggio a " + usernameDest);
		for (Iscritto iscritto : clientList) {
			if (iscritto.username.equals(usernameDest)) {
				m.message += " (Privato)";
				iscritto.id.detto(m);
				break;
			}
		}
	}

	@Override
	public synchronized void ricarica(String username) throws RemoteException {
		logger.info(username + " ha effettuato una ricarica");
		for (Iscritto iscritto : clientList) {
			if(iscritto.username.equals(username)){
				iscritto.credito += 3;
				break;
			}
		}
	}

	@Override
	public void credito(String username) throws RemoteException {
		logger.info(username + " ha visualizzato il suo credito");
		for (Iscritto iscritto : clientList) {
			if(iscritto.username.equals(username)){
				iscritto.id.notifica("Hai a disposizione " + iscritto.credito + " messaggi");
				break;
			}
		}

	}

	@Override
	public synchronized void regala(String username, String usernameDest) throws RemoteException {
		logger.info(username + " ha ha effettuato un regalo a " + usernameDest);
		for (Iscritto iscritto : clientList) {
			if (iscritto.username.equals(usernameDest) && !iscritto.username.equals(username)) {
				iscritto.credito += 3;
				iscritto.id.notifica(username + " ti ha fatto un regalo");
				break;
			}
		}

	}

	@Override
	public synchronized boolean iscrivo(Client id, String username) throws RemoteException {
		logger.info(username + " si è iscritto");
		boolean value = true;
		for (Iscritto iscritto : clientList) {
			if(!iscritto.username.equals(username)){
				value = true;
			}
			else{
				value = false;
			}
		}
		if(value){
			clientList.add(new Iscritto(id, username));
			for (Iscritto iscritto : clientList) {
				iscritto.id.iscritto(username);
			}
			return true;
		} else {
			id.notifica("Username già occupato");
			return false;
		}
		
	}

	@Override
	public void abbandono(String username) throws RemoteException {
		logger.info(username + " ha abbandonato");
		for (Iscritto iscritto : clientList) {
			if (iscritto.username.equals(username)) {
				clientList.remove(iscritto);
			} else {
				iscritto.id.abbandonato(username);
			}
		}
		
	}

}
