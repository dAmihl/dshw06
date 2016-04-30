package utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;

public interface IRemoteDispatcher extends Remote {

	public <T> IJobMonitor<T> submit(Callable<T> job) throws RemoteException;

	public Boolean registerServer(IRemoteService serverStub) throws RemoteException;
	
}
