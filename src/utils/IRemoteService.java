package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

public interface IRemoteService extends Remote {
	
	public <T> IJob<T> submit(Callable<T> job) throws RemoteException;

}
