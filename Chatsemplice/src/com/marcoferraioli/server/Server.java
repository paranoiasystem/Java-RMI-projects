package com.marcoferraioli.server;

import com.marcoferraioli.client.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	public void dici (String username, String message) throws RemoteException;
	public void iscrivi (Client id, String username) throws RemoteException;
	public void abbandona (Client id, String username) throws RemoteException;
}
