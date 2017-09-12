package com.appg.djTalk.common.push;

import java.util.ArrayList;
import java.util.List;

import com.appg.djTalk.common.batch.job.Job_ApnsSendPush;
import com.appg.djTalk.common.batch.job.Job_ApnsSendPushMulti;
import com.appg.djTalk.common.batch.job.Job_GcmSendPush;
import com.appg.djTalk.common.batch.job.Job_GcmSendPushMulti;
import com.appg.djTalk.common.batch.manager.BatchJob;
import com.appg.djTalk.common.batch.manager.BatchJobProcessManager;
import com.appg.djTalk.common.bean.DataMap;

public class PushCls
{

	private BatchJobProcessManager	mBatchJobProcessManager	= null;

	public PushCls(BatchJobProcessManager batchJobProcessManager)
	{
		mBatchJobProcessManager = batchJobProcessManager;
	}

	/**
	 * 단일 푸시 전송
	 * 
	 * @param deviceTypeID
	 * @param registrationKey
	 * @param data
	 */
	public void sendPush(int deviceTypeID, String registrationKey, DataBean data)
	{
		System.out.println("Token ::: " + registrationKey);
		
		DataMap jobData = new DataMap();
		jobData.put("registrationKey", registrationKey);
		jobData.put("pushData", data);

		if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_AND && registrationKey != "" && registrationKey != null){
			System.out.println("GCM PUSHED");
			mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_GcmSendPush()));
		}
		else if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_IOS && registrationKey != "" && registrationKey != null){
			System.out.println("APNS PUSHED");
			mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_ApnsSendPush()));
		}

	}
	
	/**
	 * 멀티 푸시 전송
	 * 
	 * @param pushTargetList
	 * @param data
	 */
	public void sendPushMulti(List<DataMap> pushTargetList, DataBean data)
	{
		List<String> registrationKeys_AND = new ArrayList<String>();
		List<String> registrationKeys_IOS = new ArrayList<String>();

		List<DataBean> datas_AND = new ArrayList<DataBean>();
		List<DataBean> datas_IOS = new ArrayList<DataBean>();

		for (DataMap pushTarget : pushTargetList)
		{
			int deviceTypeID = pushTarget.getInt("deviceTypeID");
			String registrationKey = pushTarget.getString("registrationKey");

			if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_AND && registrationKey != "" && registrationKey != null)
			{
				registrationKeys_AND.add(registrationKey);
				datas_AND.add(getUserSetDataBean(pushTarget, data));
			}
			if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_IOS && registrationKey != "" && registrationKey != null)
			{
				registrationKeys_IOS.add(registrationKey);
				datas_IOS.add(getUserSetDataBean(pushTarget, data));
			}

			if(registrationKeys_AND.size() > 100)
			{
				DataMap jobData = new DataMap();
				jobData.put("registrationKeys", registrationKeys_AND);
				jobData.put("pushDatas", datas_AND);
				mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_GcmSendPushMulti()));

				registrationKeys_AND = new ArrayList<String>();
				datas_AND = new ArrayList<DataBean>();
			}

			if(registrationKeys_IOS.size() > 100)
			{
				DataMap jobData = new DataMap();
				jobData.put("registrationKeys", registrationKeys_IOS);
				jobData.put("pushDatas", datas_IOS);
				mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_ApnsSendPushMulti()));

				registrationKeys_IOS = new ArrayList<String>();
				datas_IOS = new ArrayList<DataBean>();
			}

		}

		if(registrationKeys_AND.size() > 0)
		{
			DataMap jobData = new DataMap();
			jobData.put("registrationKeys", registrationKeys_AND);
			jobData.put("pushDatas", datas_AND);
			mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_GcmSendPushMulti()));
		}

		if(registrationKeys_IOS.size() > 0)
		{
			DataMap jobData = new DataMap();
			jobData.put("registrationKeys", registrationKeys_IOS);
			jobData.put("pushDatas", datas_IOS);
			mBatchJobProcessManager.offerJob(new BatchJob(jobData, new Job_ApnsSendPushMulti()));
		}
	}	


	private DataBean getUserSetDataBean(DataMap userData, DataBean data)
	{
		data.setAlim(userData.getInt("isPush") == 1);
		data.setIsPushSound(userData.getInt("isPushSound"));
		data.setIsPushVibrate(userData.getInt("isPushVibrate"));
		data.setPushSound(userData.getString("pushSound"));
		
		return data;
	}

}
