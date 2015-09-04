package com.marcoferraioli.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloEnglish extends UnicastRemoteObject implements Hello {

	public static final long  serialVersionUID = 1L;
	
	public HelloEnglish() throws RemoteException {
		super();
	}
	
	@Override
	public String sayHello() throws RemoteException {
		return "Hello";
	}

}
