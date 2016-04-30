package dispatcher;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import utils.IRemoteDispatcher;

public class DispatcherMain {

	public void startDispatcher(){
		System.out.println("Dispatcher started.");
		initDispatcherService();
	}
	
	private void initDispatcherService(){
        try {
            String name = RemoteDispatcher.DISPATCHER_NAME;
            IRemoteDispatcher dispatcherEngine = new RemoteDispatcher();
            IRemoteDispatcher stub =
                (IRemoteDispatcher) UnicastRemoteObject.exportObject(dispatcherEngine, 0);
            Registry registry = LocateRegistry.createRegistry(RemoteDispatcher.DISPATCHER_PORT);
            registry.rebind(name, stub);
            System.out.println("Dispatch service bound on port "+RemoteDispatcher.DISPATCHER_PORT);
        } catch (Exception e) {
            System.err.println("RMI Dispatcher exception:");
            e.printStackTrace();
        }
	}
	
}
