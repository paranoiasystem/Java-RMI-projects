
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
	public void iscritto(String username) throws RemoteException;
	public void abbandonato(String username) throws RemoteException;
	public void scritto(String username, String messaggio) throws RemoteException;
	public void notifica(String notifica) throws RemoteException;
	public void scollegati() throws RemoteException; //Aggiunto metodo per l'abbandono sicuro
}
