package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import utils.AsyncRemoteService;
import utils.IRemoteService;

public class ServerMain {
	
	IRemoteService service;

	public void startServer(){
		System.out.println("Server started.");
		initRMI();
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
	
}
