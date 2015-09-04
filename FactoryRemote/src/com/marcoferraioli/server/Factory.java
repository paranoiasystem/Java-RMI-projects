package com.marcoferraioli.server;

import java.rmi.Remote;

public interface Factory extends Remote{
	public Hello creaHello(String lingua) throws java.rmi.RemoteException;
}
