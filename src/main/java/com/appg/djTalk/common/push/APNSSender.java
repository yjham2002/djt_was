package com.appg.djTalk.common.push;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import javapns.Push;
import javapns.devices.Device;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import net.sf.json.util.JSONUtils;

import org.json.JSONException;

import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.util.DateUtil;
import com.appg.djTalk.common.util.FileLogUtil;
import com.appg.djTalk.common.util.JsonUtil;

public class APNSSender
{
	private final boolean	mIsDebug		= Constants.IS_DEBUG;
	private final String	mLogPath		= Constants.LOG_PATH_BASE;

	private final File		mKeyStore		= new File(Constants.APNS_KEYSTORE_PATH);
	private final String	mKeyStorePass	= Constants.APNS_KEYSTORE_PASS;
	private final boolean	isProduction	= Constants.APNS_IS_PRODUCTION;

	/**
	 * 푸시 전송
	 * 
	 * @param devices
	 * @param msg
	 */
	public void sendPush(List<Device> devices, List<DataBean> datas)
	{

		if(devices == null || devices.size() == 0 || datas == null || datas.size() == 0)
			return;

		/* Initialize the push manager's connection to the custom server */
		try
		{

			List<PayloadPerDevice> payloadDevicePairs = new Vector<PayloadPerDevice>();
			for (int i = 0; i < devices.size(); i++)
			{
				if(datas.get(i).getMessage().equals(""))
					continue;

				payloadDevicePairs.add(new PayloadPerDevice(makePayload(datas.get(i)), devices.get(i)));
			}

			if(payloadDevicePairs.size() > 0)
			{
				List<PushedNotification> notifications = Push.payloads(mKeyStore, mKeyStorePass, isProduction, payloadDevicePairs);
				if(mIsDebug)
					printPushedNotifications(notifications);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}

	}

	public void sendPush(Device device, DataBean data)
	{

		if(device == null || data == null || data.getMessage().equals(""))
			return;

		try
		{
			PushNotificationPayload payload = makePayload(data);

			List<PushedNotification> notifications = Push.payload(payload, mKeyStore, mKeyStorePass, isProduction, device);
			if(mIsDebug)
				printPushedNotifications(notifications);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		finally
		{

		}

	}

	/**
	 * 페이로드 생성
	 * 
	 * @param data
	 * @return
	 */
	private PushNotificationPayload makePayload(DataBean data)
	{

		PushNotificationPayload payload = new PushNotificationPayload();

		try
		{

			// 푸시 타입 저장
			payload.addCustomDictionary("pushTypeID", data.getPushTypeID());
			payload.addAlert((data.getMessage().length() > 20) ? (data.getMessage().substring(0, 20) + "...") : data.getMessage());

			// TODO 사운드/진동알림 관련 처리 필요

			if(data.isAlim())
			{
				payload.addSound((data.getPushSound() == "") ? "default" : data.getPushSound());
			}
			else
				payload.addSound("");

			if(!data.getCustomData().isEmpty())
			{
				for (Entry<String, String> entry : data.getCustomData().entrySet())
				{
					System.out.println(entry);
					payload.addCustomDictionary(entry.getKey(), entry.getValue());
				}
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("PAYLOAD : " + payload);
		
		return payload;
	}

	/**
	 * Print to the console a comprehensive report of all pushed notifications and results.
	 * 
	 * @param notifications
	 *            a raw list of pushed notifications
	 */
	public void printPushedNotifications(List<PushedNotification> notifications)
	{
		List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
		List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
		int failed = failedNotifications.size();
		int successful = successfulNotifications.size();

		if(successful > 0 && failed == 0)
		{
			printPushedNotifications("All notifications pushed successfully (" + successfulNotifications.size() + "):", successfulNotifications);
		}
		else if(successful == 0 && failed > 0)
		{
			printPushedNotifications("All notifications failed (" + failedNotifications.size() + "):", failedNotifications);
		}
		else if(successful == 0 && failed == 0)
		{
			printPushedNotifications("No notifications could be sent, probably because of a critical error", failedNotifications);
		}
		else
		{
			printPushedNotifications("Some notifications failed (" + failedNotifications.size() + "):", failedNotifications);
			printPushedNotifications("Others succeeded (" + successfulNotifications.size() + "):", successfulNotifications);
		}
	}

	/**
	 * Print to the console a list of pushed notifications.
	 * 
	 * @param description
	 *            a title for this list of notifications
	 * @param notifications
	 *            a list of pushed notifications to print
	 */
	public void printPushedNotifications(String description, List<PushedNotification> notifications)
	{

		FileLogUtil.writeFileLog(mLogPath, "apns", "apns_log", DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "  " + description);
		for (PushedNotification notification : notifications)
		{
			try
			{
				FileLogUtil.writeFileLog(mLogPath, "apns", "apns_log", DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + "  " + notification.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}
}
