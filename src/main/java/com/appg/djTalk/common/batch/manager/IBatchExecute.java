package com.appg.djTalk.common.batch.manager;

public interface IBatchExecute
{
	public Object execute(Object data);

	public Object postExecute(Object result);

}