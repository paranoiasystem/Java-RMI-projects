package com.marcoferraioli.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.marcoferraioli.client.Client;
import com.marcoferraioli.utility.Messaggio;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger("global");
	static ArrayList<Iscritto> clientList= new ArrayList<Iscritto>();
	private static String password = "porcodio";

	protected ServerImpl() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		try {
			ServerImpl server = new ServerImpl();
			java.rmi.Naming.rebind("facebook", server);
			System.out.println("Server avviato... ");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void iscrivo(Client id, String username, String password) throws RemoteException {
		logger.info("Si è iscritto: " + username);
		if(password.equals(ServerImpl.password)){
			clientList.add(new Iscritto(id, username));
			for (Iscritto iscritto : clientList) {
				iscritto.id.iscritto(username);
			}
		} else {
			id.notifica("Password Errata!");
		}
	}

	@Override
	public void abbandono(Client id, String username) throws RemoteException {
		logger.info("Ha abbandonato: " + username);
		for (Iscritto iscritto : clientList){
			if (iscritto.id.equals(id) && iscritto.username.equals(username))
				clientList.remove(iscritto);
			else
				iscritto.id.abbandonato(username);
		}
	}

	@Override
	public void dico(Messaggio m) throws RemoteException {
		logger.info(m.username + " ha scritto un messaggio");
		for (Iscritto iscritto : clientList) {
			iscritto.id.detto(m);
		}
	}

	@Override
	public void chiedoLista(String username, Client id) throws RemoteException {
		logger.info(username + " ha chiesto la lista");
		String lista = "";
		for (Iscritto iscritto : clientList) {
			lista += iscritto.username + ": " + iscritto.stato + "\n";
			if (!iscritto.commenti.isEmpty()) {
				for (String commento : iscritto.commenti) {
					lista += "\t" + commento + "\n";
				}
			}
		}
		id.stampoLista(lista);
	}

	@Override
	public synchronized void commento(String username, String commento) throws RemoteException {
		logger.info(username + " ha scritto un commento");
		String parti[] = commento.split(":");
		for (Iscritto iscritto : clientList) {
			if(parti[0].equals(iscritto.username)){
				iscritto.addCommento(username, parti[1]);
				iscritto.id.notifica(username + " ha commentato il tuo stato");
				break;
			}
		}
	}

	@Override
	public void cambiaStato(String username, String stato) throws RemoteException {
		logger.info(username + " ha cambiato lo stato"); 
		for (Iscritto iscritto : clientList) {
			if (iscritto.username.equals(username)) {
				iscritto.stato = stato;
				if(!iscritto.commenti.isEmpty()){
					iscritto.commenti.clear();
				}
				break;
			}
		}		
	}

}
