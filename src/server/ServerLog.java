package server;

public class ServerLog {
	
	public static synchronized void jobLog(String writer, String msg){
		System.out.println(writer+": "+msg);
	}

}
