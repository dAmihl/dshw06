package application;


import client.ClientMain;
import server.ServerMain;

public class Application {

	private static boolean bRunAsServer = true;
	
	public static void main(String[] args) {
		if (args.length > 0 && args[0].toLowerCase().equals("client")){
			bRunAsServer = false;
		}
		
		if (bRunAsServer){
			runServer();
		}else{
			runClient();
		}
	}
	
	private static void runServer(){
		new ServerMain().startServer();
	}
	
	private static void runClient(){
		new ClientMain().startClient();
	}
	
}
