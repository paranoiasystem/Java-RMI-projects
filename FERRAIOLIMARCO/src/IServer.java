
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	public boolean iscrivo(IClient id, String username) throws RemoteException; //cambiato valore di ritorno
	public void abbandono(String username) throws RemoteException;
	public void scrivo(String username, String messaggio) throws RemoteException;
	public void mipiace(String username, int n) throws RemoteException;
	public void countmipiace(String username, int n) throws RemoteException;
}
