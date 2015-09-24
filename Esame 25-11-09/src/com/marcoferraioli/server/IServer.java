package com.marcoferraioli.server;

import java.rmi.*;

import com.marcoferraioli.client.IClient;
import com.marcoferraioli.utility.Messaggio;

public interface IServer extends Remote {
	public boolean iscrivo(IClient id, String username) throws RemoteException;
	public void abbandono(String username) throws RemoteException;
	public void punto(String username, int puntata) throws RemoteException;
	public void dico(Messaggio m) throws RemoteException;
}
