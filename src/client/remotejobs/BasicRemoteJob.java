package client.remotejobs;

import java.io.Serializable;
import java.util.concurrent.Callable;

public abstract class BasicRemoteJob<T> implements Callable<T>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2453549443090962590L;

}
