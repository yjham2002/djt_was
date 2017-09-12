package com.appg.djTalk.common.batch.manager;

/**
 * 배치잡을 수행 할 객체
 * @author Administrator
 *
 */
public class BatchJob {
	
	private IBatchExecute execute = null ;
	
	private Object data = null ;
	
	public BatchJob(Object data,IBatchExecute execute)
	{
		this.data = data ;
		this.execute = execute ;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public IBatchExecute getExecute()
	{
		return execute;
	}

	public void setExecute(IBatchExecute execute)
	{
		this.execute = execute;
	}
	
}