package application;

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
		
	}
	
	private static void runClient(){
		
	}
	
}
