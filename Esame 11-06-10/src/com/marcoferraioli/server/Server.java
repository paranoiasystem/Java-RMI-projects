package com.marcoferraioli.server;

import java.rmi.RemoteException;

import com.marcoferraioli.client.Client;

public interface Server extends java.rmi.Remote{
	public void dici(String username, String message) throws RemoteException;
	public void iscrivi(Client id, String username) throws RemoteException;
	public void abbandono(Client id, String username) throws RemoteException;
}
