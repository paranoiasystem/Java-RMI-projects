package com.marcoferraioli.server;

import java.rmi.Remote;

public interface Hello extends Remote {
	public String sayHello() throws java.rmi.RemoteException;
}
