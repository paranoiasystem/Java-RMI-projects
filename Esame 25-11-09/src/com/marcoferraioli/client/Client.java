package com.marcoferraioli.client;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements IClient {
	
	static Logger logger = Logger.getLogger("global");
	public static final long serialVersionUID = 1L;

	public Client() throws RemoteException {
		super();
	}

	public Client(int arg0) throws RemoteException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Client(int arg0, RMIClientSocketFactory arg1, RMIServerSocketFactory arg2) throws RemoteException {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notifica(String notifica) throws RemoteException {
		// TODO Auto-generated method stub

	}

}
