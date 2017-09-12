package com.appg.djTalk.common.batch.job;

import com.appg.djTalk.common.batch.manager.IBatchExecute;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.push.DataBean;
import com.appg.djTalk.common.push.GCMResultBean;
import com.appg.djTalk.common.push.GCMSender;

public class Job_GcmSendPush implements IBatchExecute
{
	// private UserMapper userMapper;

	@Override
	public Object execute(Object jobDataObj)
	{
		try
		{
			GCMSender GS = new GCMSender();
			DataMap jobData = (DataMap) jobDataObj;
			DataBean pushData = (DataBean) jobData.get("pushData");
			String registrationKey = jobData.getString("registrationKey");
			GCMResultBean gcmResultBean = GS.sendPush(registrationKey, pushData);

			// if(gcmResultBean.getNewRegistrationKey() != null && gcmResultBean.getNewRegistrationKey() != "")
			// userMapper.gcmResultChangePushKeyUpdate(gcmResultBean.getReqRegistrationKey(), gcmResultBean.getNewRegistrationKey());
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
