package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.remotejobs.SimpleSleepJob;
import server.AsyncRemoteService;
import utils.IJobMonitor;
import utils.IRemoteService;
import utils.InvalidStateException;

public class ClientMain {

	IRemoteService remoteService;
	
	public void startClient(){
		System.out.println("Client started.");
		initRMI();
		testRMI();
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
	
}
