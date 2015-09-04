package com.marcoferraioli.client;

import com.marcoferraioli.utility.Messaggio;

public interface Client extends java.rmi.Remote {
	public void iscritto(String username) throws java.rmi.RemoteException;
	public void abbandonato(String username) throws java.rmi.RemoteException;
	public void detto(Messaggio m) throws java.rmi.RemoteException;
	public void stampoLista(String lista) throws java.rmi.RemoteException;
	public void notifica(String notifica) throws java.rmi.RemoteException;
}
