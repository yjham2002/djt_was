package com.appg.djTalk.common.batch.job;

import java.util.ArrayList;
import java.util.List;

import com.appg.djTalk.common.batch.manager.IBatchExecute;
import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.push.APNSSender;
import com.appg.djTalk.common.push.DataBean;

import javapns.devices.Device;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.devices.implementations.basic.BasicDevice;

public class Job_ApnsSendPushMulti implements IBatchExecute
{

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object jobDataObj)
	{
		try
		{
			APNSSender AS = new APNSSender();
			DataMap jobData = (DataMap) jobDataObj;
			List<DataBean> pushDatas = (List<DataBean>) jobData.get("pushDatas");
			List<String> registrationKeys = (List<String>) jobData.get("registrationKeys");
			List<Device> devices = new ArrayList<Device>();

			for (String registrationKey : registrationKeys)
			{
				devices.add(new BasicDevice(registrationKey.replaceAll(" ", "").replaceAll(">", "").replace("<", "")));
			}

			AS.sendPush(devices, pushDatas);
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
