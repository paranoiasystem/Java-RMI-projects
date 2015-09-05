package com.marcoferraioli.server;

import java.rmi.Remote;

import com.marcoferraioli.client.Client;
import com.marcoferraioli.utility.Messaggio;

public interface Server extends Remote {
	public void iscrivo(Client id, String username) throws java.rmi.RemoteException;
	public void abbandono(String username) throws java.rmi.RemoteException;
	public void dico(Messaggio m) throws java.rmi.RemoteException;
	public void dico(Messaggio m, String usernameDest) throws java.rmi.RemoteException;
	public void ricarica(String username) throws java.rmi.RemoteException;
	public void credito(String username) throws java.rmi.RemoteException;
	public void regala(String username, String usernameDest) throws java.rmi.RemoteException;
}
