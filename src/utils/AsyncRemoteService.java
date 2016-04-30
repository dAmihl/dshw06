package utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.AsyncJobThread;
import server.BasicJobMonitor;

public class AsyncRemoteService implements IRemoteService{
	
	public static final String SERVICE_NAME = "RMI_SERVICE";
	public static final Integer SERVICE_PORT = 1099;
	
	private static final Integer MAX_NUMBER_JOBS = 10;
	
	private ExecutorService executorService;
	
	
	
	public AsyncRemoteService() {
		this.executorService = Executors.newFixedThreadPool(MAX_NUMBER_JOBS);
	}
	
	@Override
	public <T> IJobMonitor<T> submit(Callable<T> job) throws RemoteException {
		IJobMonitor<T> monitor = new BasicJobMonitor<>();
		@SuppressWarnings("unchecked")
		IJobMonitor<T> stubMonitor = (IJobMonitor<T>) UnicastRemoteObject.exportObject(monitor,0);
		AsyncJobThread<T> jobThread = new AsyncJobThread<T>(job, monitor);
		
		this.executorService.submit(jobThread);
		return stubMonitor;
	}

}
