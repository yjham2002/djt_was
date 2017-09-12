package com.appg.djTalk.common.batch.job;

import com.appg.djTalk.common.batch.manager.IBatchExecute;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.push.APNSSender;
import com.appg.djTalk.common.push.DataBean;

import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;

public class Job_ApnsSendPush implements IBatchExecute
{

	@Override
	public Object execute(Object jobDataObj)
	{
		try
		{
			APNSSender AS = new APNSSender();
			DataMap jobData = (DataMap) jobDataObj;
			DataBean pushData = (DataBean) jobData.get("pushData");
			String registrationKey = jobData.getString("registrationKey").replaceAll(" ", "").replaceAll(">", "").replace("<", "");
			System.out.println("regKey ::: JOBOFFERED ::: " + registrationKey);
			AS.sendPush(new BasicDevice(registrationKey), pushData);
			System.out.println("PUSH DATA[APNS] :::::: " + pushData);
		}
		catch (InvalidDeviceTokenFormatException e)
		{
			e.printStackTrace();
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
