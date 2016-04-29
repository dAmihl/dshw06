package utils;

public interface IJob<T> {
	
	public boolean isDone();
	
	public T getResult();

}
