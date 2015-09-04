package com.marcoferraioli.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Logger;

import com.marcoferraioli.server.*;

public class Client extends UnicastRemoteObject {
	
	static Logger logger = Logger.getLogger("global");
	public static final long serialVersionUID = 1L;
	
	public Client() throws RemoteException{
		super();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.print("Iserisci la tua lingua: ");
			String lingua = scanner.nextLine();
			Factory server = (Factory) Naming.lookup("rmi://localhost/factory");
			System.out.println(server.creaHello(lingua).sayHello());
		} catch (Exception e) {
			logger.severe("Non riesco a collegarmi al server..  " + e.getMessage());
			e.printStackTrace();
		}
	}

}
