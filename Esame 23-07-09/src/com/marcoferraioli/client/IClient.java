package com.marcoferraioli.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	public void notifica(String notifica) throws RemoteException;
}
