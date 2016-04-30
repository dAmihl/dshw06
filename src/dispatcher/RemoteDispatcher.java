package dispatcher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import utils.IJobMonitor;
import utils.IRemoteDispatcher;
import utils.IRemoteService;

public class RemoteDispatcher implements IRemoteDispatcher {
	
	public static final String DISPATCHER_NAME = "dispatcher";
	public static final Integer DISPATCHER_PORT = 1099;
	
	private ArrayList<IRemoteService> registeredServers;
	
	public RemoteDispatcher(){
		this.registeredServers = new ArrayList<>();
	}

	@Override
	public <T> IJobMonitor<T> submit(Callable<T> job) throws RemoteException {
		System.out.println("Job received. Dispatching job to a known server..");
		if (registeredServers.size() == 0){
			System.out.println("No registered servers.. rejecting job submission.");
			return null;
		}
		IJobMonitor<T> monitor = registeredServers.get(0).submit(job);
		System.out.println("Job dispatched.");
		return monitor;
	}

	@Override
	public Boolean registerServer(IRemoteService serverStub) throws RemoteException {
		this.registeredServers.add(serverStub);
		return true;
	}

}
