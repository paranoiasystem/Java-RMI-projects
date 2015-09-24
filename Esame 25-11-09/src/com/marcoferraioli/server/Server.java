package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.client.IClient;
import com.marcoferraioli.utility.Messaggio;

public class Server extends UnicastRemoteObject implements IServer {

	static Logger logger = Logger.getLogger("global");
	public static final long serialVersionUID = 1L;
	static ArrayList<Giocatore> clientList = new ArrayList<Giocatore>();
	boolean inGame = false;
	boolean turnoAttivo = false;
	boolean decretaVincitore = false;
	
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
			Naming.rebind("dummy", server);
			System.out.println("Server avviato...");
		} catch (Exception e) {
			logger.severe("Problema con gli oggetti remoti... " + e.getMessage());
			e.printStackTrace();
		}
		
		while(true){
			
			try {
				cmd = scanner.nextLine();
				if(cmd.startsWith("!")){
					if(cmd.startsWith("!round")){
						if(server.inGame){
							server.notificaTutti("è possibile puntare");
							server.turnoAttivo = true;
							server.decretaVincitore = false;
						} else {
							System.out.println("Errore");
						}
					}
					if(cmd.startsWith("!high")){
						server.decretaVincitore(true);
					}
					if(cmd.startsWith("!low")){
						server.decretaVincitore(false);
					}
				} else {
					server.dico(new Messaggio("Server", cmd));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void decretaVincitore(boolean high){
		Giocatore vincitore = null;
		if(high && decretaVincitore){
			for (int i = 0; i < clientList.size(); i++) {
				Giocatore giocatore = clientList.get(i);
				Giocatore giocatore1 = clientList.get(i + 1);
				if(giocatore.getPuntata() >= giocatore1.getPuntata()){
					
				}
			}	
		}
		for (Giocatore giocatore : clientList) {
		}
	}
	
	private void checkGameReady(){
		if(clientList.size() == 4){
			inGame = true;
			System.out.println("è possibile avviare la partita");
		}
	}
	
	private void checkPuntate(){
		int value = 0;
		for (Giocatore giocatore : clientList) {
			if(giocatore.getPuntata() != 0)
				value++;
		}
		if(value == 4){
			turnoAttivo = false;
			decretaVincitore = true;
		}
	}
	
	private void notificaTutti(String notifica) throws RemoteException{
		for (Giocatore giocatore : clientList) {
			giocatore.getId().notifica(notifica);
		}
	}
	
	@Override
	public synchronized boolean iscrivo(IClient id, String username) throws RemoteException {
		if(!inGame){
			boolean ok = true;
			for (Giocatore giocatore : clientList) {
				if(giocatore.getUsername().equals(username)){
					ok = false;
					break;
				}
			}
			if(!ok){
				id.notifica("username già occupato");
				return false;
			} else {
				clientList.add(new Giocatore(id, username));
				for (Giocatore giocatore : clientList) {
					giocatore.getId().notifica(username + " si è iscritto");
				}
				checkGameReady();
				return true;
			} 
		} else {
			id.notifica("Al momento non puoi collegarti, si sta svolgendo una partita");
			return false;
		}
	}

	@Override
	public void abbandono(String username) throws RemoteException {
		for (Giocatore giocatore : clientList) {
			if(giocatore.getUsername().equals(username)){
				clientList.remove(giocatore);
			} else {
				giocatore.getId().notifica(username + " ha abbandotano");
			}
		}

	}

	@Override
	public synchronized void punto(String username, int puntata) throws RemoteException {
		for (Giocatore giocatore : clientList) {
			if(giocatore.getUsername().equals(username)){
				if(turnoAttivo){
					if(giocatore.getCredito() == 0){
						giocatore.getId().notifica("Non hai soldi per puntare\nGAME OVER!");
						abbandono(giocatore.getUsername());
						break;
					}
					if(giocatore.getCredito() < puntata){
						giocatore.getId().notifica("Non puoi puntare più fiquanto possiedi");
						break;
					}
					if(!giocatore.isPuntato()){
						if(puntata >= 1 && puntata <= 10){
							giocatore.setPuntato(true);
							giocatore.setCredito(giocatore.getCredito() - puntata);
							giocatore.setPuntata(puntata);
							giocatore.setVincitore(false);
							for (Giocatore g : clientList) {
								g.getId().notifica(giocatore.getUsername() + " ha puntato");
							}
							checkPuntate();
							break;
						} else {
							giocatore.getId().notifica("Puoi puntare solo una somma compresa tra 1 e 10");
							break;
						}
					} else {
						giocatore.getId().notifica("Non puoi puntare di nuovo");
						break;
					}
				} else {
					giocatore.getId().notifica("Non puoi puntare ora");
					break;
				}
			}
		}
	}

	@Override
	public void dico(Messaggio m) throws RemoteException {
		for (Giocatore giocatore : clientList) {
			if(!giocatore.getUsername().equals(m.username))
				giocatore.getId().notifica(m.username + ": " + m.messaggio);
		}

	}
}
