package com.marcoferraioli.server;

import com.marcoferraioli.client.*;
import com.marcoferraioli.utility.*;

public interface Server extends java.rmi.Remote{
	public void iscrivo(Client id, String username, String password) throws java.rmi.RemoteException;
	public void abbandono(Client id, String username) throws java.rmi.RemoteException;
	public void dico(Messaggio m) throws java.rmi.RemoteException;
	public void chiedoLista(String username, Client id) throws java.rmi.RemoteException;
	public void commento(String username, String commento) throws java.rmi.RemoteException;
	public void cambiaStato(String username, String stato) throws java.rmi.RemoteException;
}
