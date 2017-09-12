package com.appg.djTalk.common.push;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.util.DateUtil;
import com.appg.djTalk.common.util.FileLogUtil;
import com.appg.djTalk.common.util.JsonUtil;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMSender
{
	private final boolean	mIsDebug			= Constants.IS_DEBUG;
	private final String	mLogPath			= Constants.LOG_PATH_BASE;

	// Put your Google API Server Key here
	private final String	mGoogleServerKey	= Constants.GCM_GOOGLE_SERVER_KEY;
	private final String	mMessageKey			= Constants.GCM_MESSAGE_KEY;
	private final String	mCollapseKey		= Constants.GCM_COLLAPSE_KEY;

	// timeToLive - 메세지 생존시간
	private final int		mTimeToLive			= Constants.GCM_TIME_TO_LIVE;

	// delayWhileIdle - false : 절전시간과 상관없이 전송 / true : 절전시간일 경우 안감
	private final boolean	mDelayWhileIdle		= false;

	/**
	 * 단일 푸시 전송
	 * 
	 * @param registrationKey
	 * @param userMessage
	 */
	public GCMResultBean sendPush(String registrationKey, DataBean data)
	{
		GCMResultBean retVal = new GCMResultBean();
		Result result = null;

		if(registrationKey == null || registrationKey.equals("") || data == null || data.getMessage().equals(""))
			return null;

		try
		{

			Sender sender = new Sender(mGoogleServerKey);

			// 메세지 생성
			Builder msgBuilder = new Builder();
			msgBuilder.collapseKey(mCollapseKey);
			msgBuilder.timeToLive(mTimeToLive);
			msgBuilder.delayWhileIdle(mDelayWhileIdle);
			msgBuilder.addData(mMessageKey, toString_PushData(data));
			Message message = msgBuilder.build();

			result = sender.send(message, registrationKey, 1);

			if(result.getCanonicalRegistrationId() != null && result.getCanonicalRegistrationId() != "")
			{
				retVal.setReqRegistrationKey(registrationKey);
				retVal.setNewRegistrationKey(result.getCanonicalRegistrationId());
			}

			if(mIsDebug)
			{
				String logContents = "============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
				logContents += "\nregistrationKey: " + registrationKey;
				logContents += "\nuserMessage: " + data.getMessage();
				logContents += "\npushStatus: " + result.toString();
				logContents += "\n============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
				FileLogUtil.writeFileLog(mLogPath, "gcm", "gcm_log", logContents);
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.out.println("RegId required : " + ioe.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("pushStatus : " + e.toString());
		}

		return retVal;

	}

	/**
	 * 단체 푸시 전송
	 * 
	 * @param registrationKey
	 * @param userMessage
	 */
	public void sendPush(List<String> registrationKeys, DataBean data)
	{
		Result result = null;

		if(registrationKeys == null || data == null || data.getMessage().equals(""))
			return;

		try
		{

			String msgData = toString_PushData(data);

			Sender sender = new Sender(mGoogleServerKey);

			// 메세지 생성
			Builder msgBuilder = new Builder();
			msgBuilder.collapseKey(mCollapseKey);
			msgBuilder.timeToLive(mTimeToLive);
			msgBuilder.delayWhileIdle(mDelayWhileIdle);
			msgBuilder.addData(mMessageKey, msgData);
			Message message = msgBuilder.build();

			for (String registrationKey : registrationKeys)
			{
				result = sender.send(message, registrationKey, 1);

				if(mIsDebug)
				{
					String logContents = "============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
					logContents += "\nregistrationKey: " + registrationKey;
					logContents += "\nuserMessage: " + data.getMessage();
					logContents += "\npushStatus: " + result.toString();
					logContents += "\n============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
					FileLogUtil.writeFileLog(mLogPath, "gcm", "gcm_log", logContents);
				}
			}

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.out.println("RegId required : " + ioe.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("pushStatus : " + e.toString());
		}

	}

	/**
	 * 단체 푸시 전송
	 * 
	 * @param registrationKey
	 * @param userMessage
	 */
	public void sendPush(List<String> registrationKeys, List<DataBean> datas)
	{
		Result result = null;

		if(registrationKeys == null || datas == null || datas.size() == 0)
			return;

		try
		{

			Sender sender = new Sender(mGoogleServerKey);

			// 메세지 생성
			Builder msgBuilder = new Builder();
			msgBuilder.collapseKey(mCollapseKey);
			msgBuilder.timeToLive(mTimeToLive);
			msgBuilder.delayWhileIdle(mDelayWhileIdle);

			for (int i = 0; i < registrationKeys.size(); i++)
			{
				DataBean data = datas.get(i);
				String registrationKey = registrationKeys.get(i);

				System.out.println(registrationKey);
				if(data.getMessage().equals(""))
					continue;

				String msgData = toString_PushData(data);
				msgBuilder.addData(mMessageKey, msgData);
				Message message = msgBuilder.build();

				result = sender.send(message, registrationKey, 1);

				if(mIsDebug)
				{
					String logContents = "============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
					logContents += "\nregistrationKey: " + registrationKey;
					logContents += "\nuserMessage: " + data.getMessage();
					logContents += "\npushStatus: " + result.toString();
					logContents += "\n============ " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "================";
					FileLogUtil.writeFileLog(mLogPath, "gcm", "gcm_log", logContents);
				}

			}

		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			System.out.println("RegId required : " + ioe.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("pushStatus : " + e.toString());
		}

	}

	private JSONObject toJson_PushData(DataBean data)
	{

		JSONObject json = new JSONObject();
		JsonUtil.Obj.put(json, "message", data.getMessage());
		try {
			JsonUtil.Obj.put(json, "msg", JsonUtil.Obj.getString(JsonUtil.Obj.createWithException(data.getMessage()), "msg"));
		} catch (JSONException e) {
			JsonUtil.Obj.put(json, "msg", data.getMessage());
		}
		JsonUtil.Obj.put(json, "vibration", data.getVibration());
		JsonUtil.Obj.put(json, "notifyWithSound", data.getNotifyWithSound());
		JsonUtil.Obj.put(json, "notificationSound", data.getNotificationSound());
		JsonUtil.Obj.put(json, "rid", data.getRid());
		JsonUtil.Obj.put(json, "senduid", data.getSenduid());
		JsonUtil.Obj.put(json, "recvuid", data.getRecvuid());
		JsonUtil.Obj.put(json, "data", data.getData());
		JsonUtil.Obj.put(json, "mtime", data.getMtime());

		for (Entry<String, String> entry : data.getCustomData().entrySet())
		{
			JsonUtil.Obj.put(json, entry.getKey(), entry.getValue());
		}

		return json;
	}

	private String toString_PushData(DataBean data)
	{
		return toJson_PushData(data).toString();
	}
}