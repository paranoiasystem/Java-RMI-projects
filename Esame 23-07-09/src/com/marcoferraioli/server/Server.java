package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.client.IClient;

public class Server extends UnicastRemoteObject implements IServer {

	static Logger logger = Logger.getLogger("global");
	private static final long serialVersionUID = 1L;
	private static ArrayList<Stazione> stazioni = new ArrayList<Stazione>();

	public Server() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String cmd = null;
		Server server = null;
		
		System.setSecurityManager(new RMISecurityManager());
		try {
			server = new Server();
			Naming.rebind("meteo", server);
			System.out.println("Server avviato...");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti... " + e.getMessage());
			e.printStackTrace();
		}
		
		while(true){
			try {
				cmd = scanner.nextLine();
				if(cmd.startsWith("!")){
					if(cmd.startsWith("!mostratemperature")){
						server.mostraTemperature();
					}
				} else {
					server.dico("Server", cmd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	private void mostraTemperature() throws RemoteException{
		for (Stazione stazione : stazioni) {
			if(!stazione.getTemperatura().equals(null)){
				System.out.println(stazione.getNickname() + " Temperatura: " + stazione.getTemperatura());
				notificaTutti(stazione.getNickname() + " Temperatura: " + stazione.getTemperatura());
			}
		}
	}
	
	private void notificaTutti(String notifica) throws RemoteException{
		for (Stazione stazione : stazioni) {
			stazione.getId().notifica(notifica);
		}
	}

	@Override
	public synchronized boolean iscrivo(IClient id, String nickname) throws RemoteException {
		boolean ok = true;
		for (Stazione stazione : stazioni) {
			if(stazione.getNickname().equals(nickname)){
				ok = false;
				break;
			}
		}
		if(!ok){
			id.notifica("nickname già occupato");
			return false;
		} else {
			logger.info(nickname + " si è iscritto");
			stazioni.add(new Stazione(id, nickname));
			for (Stazione stazione : stazioni) {
				if (!stazione.getNickname().equals(nickname))
					stazione.getId().notifica(nickname + " si è iscritto");
			}
			return true;
		} 		
	}

	@Override
	public void addandono(String nickname) throws RemoteException {
		logger.info(nickname + " ha abbandonato");
		for (Stazione stazione : stazioni) {
			if (stazione.getNickname().equals(nickname)) {
				stazioni.remove(stazione);
			}
			else{
				stazione.getId().notifica(nickname + " ha abbandonato");
			}
		}
		
	}

	@Override
	public synchronized void setTemperature(String nickname, Integer temperatura) throws RemoteException {
		logger.info(nickname + " ha settato la temperatura");
		for (Stazione stazione : stazioni) {
			if(stazione.getNickname().equals(nickname)){
				stazione.setTemperatura(temperatura);
				break;
			}
		}
	}

	@Override
	public void mostraConnessi(String nickname) throws RemoteException {
		logger.info(nickname + " ha richiesto la lista dei client connessi");
		for (Stazione stazione : stazioni) {
			if(stazione.getNickname().equals(nickname)){
				for (Stazione s : stazioni) {
					stazione.getId().notifica(s.getNickname() + " è connesso");
				}
				break;
			}
		}
		
	}

	@Override
	public void addVicino(String nickname, String nicknameVicino) throws RemoteException {
		logger.info(nickname + " ha aggiunto " + nicknameVicino + " ai vicini");
		for (Stazione stazione : stazioni) {
			if(stazione.getNickname().equals(nickname)){
				stazione.addVicino(nicknameVicino);
			}
			if(stazione.getNickname().equals(nicknameVicino)){
				stazione.addVicino(nickname);
			}
		}
		
	}

	@Override
	public void allertaVicini(String nickname, String messaggio) throws RemoteException {
		logger.info(nickname + " allerta i vicini. Messaggio: " + messaggio);
		for (Stazione stazione : stazioni) {
			if (stazione.getNickname().equals(nickname)) {
				ArrayList<String> vicini = stazione.getVicini();
				for (Stazione stazione2 : stazioni) {
					if(vicini.contains(stazione2.getNickname())){
						stazione2.getId().notifica("ALLERTA: " + nickname + " -> " + messaggio);
					}
				}
				break;
			}
		}
		
	}

	@Override
	public void dico(String nickname, String messaggio) throws RemoteException {
		for (Stazione stazione : stazioni) {
			if (!stazione.getNickname().equals(nickname)) {
				System.out.println(nickname + ": " + messaggio);
				stazione.getId().notifica(nickname + ": " + messaggio);
			}
		}
		
	}
	
	

}
