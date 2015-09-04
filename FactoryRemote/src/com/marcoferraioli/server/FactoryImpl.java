package com.marcoferraioli.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import com.sun.org.apache.xalan.internal.xsltc.dom.LoadDocument;

public class FactoryImpl extends UnicastRemoteObject implements Factory {
	
	static Logger logger = Logger.getLogger("global");
	public static final long serialVersionUID = 1L;
	
	public FactoryImpl() throws RemoteException{
		super();
	}
	
	public static void main(String[] args) {
		try {
			FactoryImpl server = new FactoryImpl();
			Naming.rebind("rmi://localhost/factory", server);
			System.out.println("Server avviato...");
		} catch (Exception e) {
			logger.severe("Problema con la rebind: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public Hello creaHello(String lingua) throws RemoteException {
		if(lingua.equals("italiano"))
			return new HelloItaly();
		else
			return new HelloEnglish();
	}

}
