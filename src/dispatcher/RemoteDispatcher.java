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
	
	private Integer NEXT_SERVER_INDEX = 0;
	
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
		determineNextServerIndex();
		
		IJobMonitor<T> monitor = null;
		
		// very simple dispatching algorithm :)
		for (int i = 0; i < this.registeredServers.size(); i++){
			monitor = registeredServers.get(NEXT_SERVER_INDEX).submit(job);
			if (monitor != null){
				break;
			}else{
				determineNextServerIndex();
			}
		}
		if (monitor != null){
			System.out.println("Job dispatched to "+registeredServers.get(NEXT_SERVER_INDEX));
		}else{
			System.out.println("Job couldnt be dispatched. All known servers are busy.. this COULD end in a queue though :)");
		}
		return monitor;
	}
	

	@Override
	public Boolean registerServer(IRemoteService serverStub) throws RemoteException {
		this.registeredServers.add(serverStub);
		return true;
	}
	
	private void determineNextServerIndex(){
		this.NEXT_SERVER_INDEX += 1;
		this.NEXT_SERVER_INDEX = this.NEXT_SERVER_INDEX % this.registeredServers.size();
	}

}
