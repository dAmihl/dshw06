package client.remotejobs;


public class SimpleSleepJob extends BasicRemoteJob<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3220189496134975084L;

	@Override
	public Boolean call() throws Exception {
		System.out.println("Executing job. Sleepjob for 5seconds.");
		Thread.sleep(10000);
		return true;
	}

}
