package server;

import utils.IJobMonitor;
import utils.InvalidStateException;

public class BasicJobMonitor<T> implements IJobMonitor<T> {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6234932608436719664L;
	protected boolean bIsDone = false;
	protected T result = null;
	
	protected void setIsDone(boolean isDone){
		this.bIsDone = isDone;
	}
	
	protected void setResult(T result){
		this.result = result;
	}
	
	
	@Override
	public boolean isDone(){
		return bIsDone;
	}


	@Override
	public T getResult() throws InvalidStateException {
		if (this.result == null){
			throw new InvalidStateException();
		}else{
			return this.result;
		}
	}


	
	
}
