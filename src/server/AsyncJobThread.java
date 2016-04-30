package server;

import java.util.concurrent.Callable;

import utils.IJobMonitor;

public class AsyncJobThread<T> extends Thread {
	
	
	public Callable<T> job;
	private BasicJobMonitor<T> monitor;
	
	public AsyncJobThread(Callable<T> job, IJobMonitor<T> jobMonitor){
		this.job = job;
		this.monitor = (BasicJobMonitor<T>) jobMonitor;
	}
	
	public IJobMonitor<T> getRemoteMonitor(){
		return this.monitor;
	}
	
	@Override
	public void run() {
		try {
			T result = this.job.call();
			this.monitor.setIsDone(true);
			this.monitor.setResult(result);
			ServerLog.jobLog(this.toString(), "Job is done..");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
