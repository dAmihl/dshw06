package server;

import java.io.Serializable;
import java.util.concurrent.Callable;

import utils.IJob;

public class AsyncJobThread<T> extends Thread implements IJob<T>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3146147932805156791L;
	
	public Callable<T> job;
	private boolean bIsDone;
	private T result = null;
	
	public AsyncJobThread(Callable<T> job){
		this.job = job;
	}
	
	@Override
	public void run() {
		try {
			this.result = this.job.call();
			this.bIsDone = true;
			ServerLog.jobLog(this.toString(), "Job is done..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDone() {
		return this.bIsDone;
	}

	@Override
	public T getResult() {
		return this.result;
	}

}
