package com.marcoferraioli.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloItaly extends UnicastRemoteObject implements Hello{
	
	public static final long serialVersionUID = 1L;
	
	public HelloItaly() throws RemoteException{
		super();
	}
	
	@Override
	public String sayHello() throws RemoteException{
		return "Ciao";
	}
}
