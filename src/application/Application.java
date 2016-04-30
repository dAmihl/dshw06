package application;


import client.ClientMain;
import dispatcher.DispatcherMain;
import server.ServerMain;

public class Application {

	
	private static final boolean USE_DISPATCHER = true;
	
	public static void main(String[] args) {
		
		if (args.length > 0){
			if (args[0].toLowerCase().equals("client")){
				runClient();
			}else if (args[0].toLowerCase().equals("server")){
				runServer();
			}else if (args[0].toLowerCase().equals("dispatcher") && USE_DISPATCHER){
				runDispatcher();
			}else{
				System.out.println("Unknown argument. Use client, server or dispatcher. Or none, to start as server.");
			}
		}else{
			runServer();
		}
		
		
	}
	
	private static void runServer(){
		new ServerMain().startServer(USE_DISPATCHER);
	}
	
	private static void runClient(){
		new ClientMain().startClient(USE_DISPATCHER);
	}
	
	private static void runDispatcher(){
		new DispatcherMain().startDispatcher();
	}
	
}
