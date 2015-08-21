package com.marcoferraioli.client;

import java.rmi.RemoteException;

public interface Client extends java.rmi.Remote{
	public void detto(String username, String message) throws RemoteException;
	public void iscritto(String username) throws RemoteException;
	public void abbandonato(String username) throws RemoteException;
}
