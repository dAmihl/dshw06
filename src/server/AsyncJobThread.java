package server;

import java.util.concurrent.Callable;

import utils.IJobMonitor;

public class AsyncJobThread<T> extends Thread {
	
	
	public Callable<T> job;
	private BasicJobMonitor<T> monitor;
	private AsyncRemoteService service;
	
	public AsyncJobThread(Callable<T> job, IJobMonitor<T> jobMonitor, AsyncRemoteService service){
		this.job = job;
		this.monitor = (BasicJobMonitor<T>) jobMonitor;
		this.service = service;
	}
	
	public IJobMonitor<T> getRemoteMonitor(){
		return this.monitor;
	}
	
	@Override
	public void run() {
		try {
			T result = this.job.call();
			finish(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void finish(T result){
		this.monitor.setIsDone(true);
		this.monitor.setResult(result);
		ServerLog.jobLog(this.toString(), "Job is done..");
		this.service.jobFinished();
	}


}
