package utils;

import java.rmi.Remote;

public interface IRemoteMonitor<T> extends Remote {

	public boolean isDone();
	
	public T getResult() throws InvalidStateException;
	
}
