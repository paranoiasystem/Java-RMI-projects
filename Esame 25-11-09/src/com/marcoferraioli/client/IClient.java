package com.marcoferraioli.client;

import java.rmi.*;

public interface IClient extends Remote {
	public void notifica(String notifica) throws RemoteException;
}
