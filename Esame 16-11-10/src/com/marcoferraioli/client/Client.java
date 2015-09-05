package com.marcoferraioli.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.marcoferraioli.utility.Messaggio;

public interface Client extends Remote {
	public void iscritto(String username) throws RemoteException;
	public void abbandonato(String username) throws RemoteException;
	public void detto(Messaggio m) throws RemoteException;
	public void notifica(String notifica) throws RemoteException;
}
