package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import dispatcher.RemoteDispatcher;
import utils.IRemoteDispatcher;
import utils.IRemoteService;

public class ServerMain {
	
	IRemoteService service;
	IRemoteDispatcher dispatcherService;
	
	

	public void startServer(boolean useDispatcher){
		System.out.println("Server started.");
		if (useDispatcher){
			startServerUsingDispatcher();
		}else{
			startServerStandard();
		}
	}
	
	private void startServerStandard(){
		System.out.println("Running Server without using dispatcher.");
		initRMI();
	}
	
	private void startServerUsingDispatcher(){
		System.out.println("Running Server using dispatcher.");
		obtainDispatcherService();
		registerAtDispatcher();
	}
	
	private void initRMI(){
        try {
            String name = AsyncRemoteService.SERVICE_NAME;
            IRemoteService serviceEngine = new AsyncRemoteService();
            IRemoteService stub =
                (IRemoteService) UnicastRemoteObject.exportObject(serviceEngine, 0);
            Registry registry = LocateRegistry.createRegistry(AsyncRemoteService.SERVICE_PORT);
            registry.rebind(name, stub);
            System.out.println("Async Remote Service bound on port "+AsyncRemoteService.SERVICE_PORT);
        } catch (Exception e) {
            System.err.println("RMI Server exception:");
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
	
	private void registerAtDispatcher(){
		System.out.println("Registering server at dispatcher..");
        try {
    		IRemoteService serviceEngine = new AsyncRemoteService();
			IRemoteService stub =
			    (IRemoteService) UnicastRemoteObject.exportObject(serviceEngine, 0);
			this.dispatcherService.registerServer(stub);
			System.out.println("Succesful registered at dispatcher..");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
