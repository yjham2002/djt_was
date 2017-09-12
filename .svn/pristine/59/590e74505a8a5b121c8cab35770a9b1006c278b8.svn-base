package com.appg.djTalk.common.batch.job;

import java.util.List;

import com.appg.djTalk.common.batch.manager.IBatchExecute;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.push.DataBean;
import com.appg.djTalk.common.push.GCMSender;

public class Job_GcmSendPushMulti implements IBatchExecute
{

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object jobDataObj)
	{
		try
		{
			GCMSender GS = new GCMSender();
			DataMap jobData = (DataMap) jobDataObj;
			List<DataBean> pushDatas = (List<DataBean>) jobData.get("pushDatas");
			List<String> registrationKeys = (List<String>) jobData.get("registrationKeys");
			
			GS.sendPush(registrationKeys, pushDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object postExecute(Object result)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
