package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.IJobMonitor;
import utils.IRemoteService;

public class AsyncRemoteService implements IRemoteService{
	
	public static final String SERVICE_NAME = "RMI_SERVICE";
	public static final Integer SERVICE_PORT = 1099;
	
	private static final Integer MAX_NUMBER_JOBS = 1;
	
	private Integer CURRENT_NUM_JOBS = 0;
	
	private ExecutorService executorService;
	
	
	
	public AsyncRemoteService() {
		this.executorService = Executors.newFixedThreadPool(MAX_NUMBER_JOBS);
	}
	
	/**
	 * runs a thread and returns a JobMonitor object.
	 * The jobmonitor object gets exported and the stub returned to the client, so the executing
	 * thread is able to manipulate the object while the client can use the object to check whether the job is done
	 * or not.
	 * Server only accepts MAX_NUMBER_JOBS active jobs. When more are submitted, null is returned and the 
	 * job sumbmission gets rejected.
	 */
	@Override
	public <T> IJobMonitor<T> submit(Callable<T> job) throws RemoteException {
		
		if (getCurrentNumJobs() >= MAX_NUMBER_JOBS){
			return null;
		}
		else{
			IJobMonitor<T> monitor = new BasicJobMonitor<>();
			@SuppressWarnings("unchecked")
			IJobMonitor<T> stubMonitor = (IJobMonitor<T>) UnicastRemoteObject.exportObject(monitor,0);
			AsyncJobThread<T> jobThread = new AsyncJobThread<T>(job, monitor, this);
			
			this.executorService.submit(jobThread);
			incrementCurrentNumJobs();
			return stubMonitor;
		}
	
	}
	
	protected synchronized void jobFinished(){
		decrementCurrentNumJobs();
	}
	
	private synchronized void incrementCurrentNumJobs(){
		this.CURRENT_NUM_JOBS++;
	}
	
	private synchronized void decrementCurrentNumJobs(){
		this.CURRENT_NUM_JOBS--;
	}
	
	private synchronized Integer getCurrentNumJobs(){
		return this.CURRENT_NUM_JOBS;
	}

}
