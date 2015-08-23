package com.marcoferraioli.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.marcoferraioli.client.Client;

public interface Server extends Remote {
	public void dico(String username, String message) throws RemoteException;
	public void iscrivo(Client id, String username) throws RemoteException;
	public void abbandono(Client id, String username) throws RemoteException;
}
