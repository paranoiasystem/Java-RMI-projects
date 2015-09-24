package com.marcoferraioli.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.marcoferraioli.client.IClient;

public interface IServer extends Remote {
	public boolean iscrivo(IClient id, String nickname) throws RemoteException;
	public void addandono(String nickname) throws RemoteException;
	public void setTemperature(String nickname, Integer temperatura) throws RemoteException;
	public void mostraConnessi(String nickname) throws RemoteException;
	public void addVicino(String nickname, String nicknameVicino) throws RemoteException;
	public void allertaVicini(String nickname, String messaggio) throws RemoteException;
	public void dico(String nickname, String messaggio) throws RemoteException;
}
