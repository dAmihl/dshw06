package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteMonitor<T> extends Remote {

	public boolean isDone() throws RemoteException;
	
	public T getResult() throws InvalidStateException, RemoteException;
	
}
