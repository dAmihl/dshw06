package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.remotejobs.SimpleSleepJob;
import dispatcher.RemoteDispatcher;
import server.AsyncRemoteService;
import utils.IJobMonitor;
import utils.IRemoteDispatcher;
import utils.IRemoteService;
import utils.InvalidStateException;

public class ClientMain {

	IRemoteService remoteService;
	IRemoteDispatcher dispatcherService;
	
	public void startClient(boolean bUseDispatcher){
		System.out.println("Client started.");
		if (bUseDispatcher){
			startClientUsingDispatcher();
		}else{
			startClientStandard();
		}
	}
	
	private void startClientStandard(){
		System.out.println("Client started without using dispatcher.");
		initRMI();
		testRMI();
	}
	
	private void startClientUsingDispatcher(){
		System.out.println("Client started using dispatcher.");
		obtainDispatcherService();
		//testDispatcher();
		stressTestDispatcher();
	}
	
	private void initRMI(){
        try {
            String name = AsyncRemoteService.SERVICE_NAME;
            Registry registry = LocateRegistry.getRegistry(AsyncRemoteService.SERVICE_PORT);
            remoteService = (IRemoteService) registry.lookup(name);
            System.out.println("RMI RemoteService Initialized. Ready to use.");
        } catch (Exception e) {
            System.err.println("Compute RMI exception:");
            e.printStackTrace();
        }
	}
	
	private void obtainDispatcherService(){
		try {
            String name = RemoteDispatcher.DISPATCHER_NAME;
            Registry registry = LocateRegistry.getRegistry(RemoteDispatcher.DISPATCHER_PORT);
            dispatcherService = (IRemoteDispatcher) registry.lookup(name);
            System.out.println("RMI Dispatch Service obtained. Ready to use.");
        } catch (Exception e) {
            System.err.println("Compute RMI exception:");
            e.printStackTrace();
        }
	}
	
	private void testRMI(){
		System.out.println("Sending simple sleep job..");
		SimpleSleepJob sleepJob = new SimpleSleepJob();
		IJobMonitor<Boolean> job;
		try {
			if ((job = remoteService.submit(sleepJob)) == null){
				System.out.println("Job submission rejected by server..");
			}else{
				System.out.println("Sleepjob submitted..waiting.");
				while (!job.isDone());
				System.out.println("Sleepjob finished now.");
				System.out.println("Result of job is: "+job.getResult());
				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InvalidStateException e) {
			System.out.println("Job is done but No result found.");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void testDispatcher(){
		System.out.println("Sending simple sleep job to dispatcher..");
		SimpleSleepJob sleepJob = new SimpleSleepJob();
		IJobMonitor<Boolean> job;
		try {
			if ((job = dispatcherService.submit(sleepJob)) == null){
				System.out.println("Job submission rejected by server..");
			}else{
				System.out.println("Sleepjob submitted..waiting.");
				while (!job.isDone());
				System.out.println("Sleepjob finished now.");
				System.out.println("Result of job is: "+job.getResult());
				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InvalidStateException e) {
			System.out.println("Job is done but No result found.");
			e.printStackTrace();
		}
	}
	
	private void stressTestDispatcher(){
		int numSubmissions = 12;
		System.out.println("Stress testing dispatcher.. sending 12 job submissions.");
		
		for (int i = 0; i < numSubmissions; i++){
			SimpleSleepJob sleepJob = new SimpleSleepJob();
		
			try {
				@SuppressWarnings("unused")
				IJobMonitor<Boolean> job;
				if ((job = dispatcherService.submit(sleepJob)) == null){
					System.out.println("Job submission rejected by server..");
				}else{
					System.out.println("Sleepjob submitted..waiting.");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
