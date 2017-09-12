package com.appg.djTalk.common.push;

import java.util.HashMap;

public class DataBean
{
	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * @return the senduid
	 */
	public String getSenduid() {
		return senduid;
	}

	/**
	 * @param senduid the senduid to set
	 */
	public void setSenduid(String senduid) {
		this.senduid = senduid;
	}

	/**
	 * @return the recvuid
	 */
	public String getRecvuid() {
		return recvuid;
	}

	/**
	 * @param recvuid the recvuid to set
	 */
	public void setRecvuid(String recvuid) {
		this.recvuid = recvuid;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the mtime
	 */
	public String getMtime() {
		return mtime;
	}

	/**
	 * @param mtime the mtime to set
	 */
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	/**
	 * @return the vibration
	 */
	public int getVibration() {
		return vibration;
	}

	/**
	 * @param vibration the vibration to set
	 */
	public void setVibration(int vibration) {
		this.vibration = vibration;
	}

	/**
	 * @return the notifyWithSound
	 */
	public int getNotifyWithSound() {
		return notifyWithSound;
	}

	/**
	 * @param notifyWithSound the notifyWithSound to set
	 */
	public void setNotifyWithSound(int notifyWithSound) {
		this.notifyWithSound = notifyWithSound;
	}

	/**
	 * @return the notificationSound
	 */
	public String getNotificationSound() {
		return notificationSound;
	}

	/**
	 * @param notificationSound the notificationSound to set
	 */
	public void setNotificationSound(String notificationSound) {
		this.notificationSound = notificationSound;
	}
	private int notifyWithSound = 0;
	private int vibration = 0;
	private String notificationSound = "";
	private String rid = "";
	private String senduid = "";
	private String recvuid = "";
	private String data = "";
	private String mtime = "";
	private String					message			= "";
	private int						pushTypeID		= 0;
	private String					imgUrl			= "";
	private boolean					isAlim			= true;
	private String					pushSound		= "default";
	private int						isPushSound		= 0;
	private int						isPushVibrate	= 0;
	private HashMap<String, String>	customData		= new HashMap<String, String>();
	
	public DataBean(int vibration, int notifyWithSound, String notificationSound, String message, int pushTypeID) {
		super();
		this.vibration = vibration;
		this.notifyWithSound = notifyWithSound;
		this.notificationSound = notificationSound;
		this.message = message;
		this.pushTypeID = pushTypeID;
	}

	public DataBean(int vibration, int notifyWithSound, String notificationSound, String rid, String senduid,
			String recvuid, String data, String mtime, String message, int pushTypeID) {
		super();
		this.vibration = vibration;
		this.notifyWithSound = notifyWithSound;
		this.notificationSound = notificationSound;
		this.rid = rid;
		this.senduid = senduid;
		this.recvuid = recvuid;
		this.data = data;
		this.mtime = mtime;
		this.message = message;
		this.pushTypeID = pushTypeID;
	}

	public DataBean(int pushTypeID, String message, String img)
	{
		this.message = message;
		this.pushTypeID = pushTypeID;
		this.imgUrl = img;
	}
	
	public DataBean(int pushTypeID, String message, boolean isAlim)
	{
		this.message = message;
		this.isAlim = isAlim;
		this.pushTypeID = pushTypeID;
	}

	public DataBean(int pushTypeID, String message, boolean isAlim, HashMap<String, String> customData)
	{
		this.message = message;
		this.isAlim = isAlim;
		this.pushTypeID = pushTypeID;
		this.customData = customData;
	}

	public void clear()
	{
		this.clear();
	}

	public DataBean clone()
	{
		return this.clone();
	}

	public int getIsPushSound()
	{
		return isPushSound;
	}

	public void setIsPushSound(int isPushSound)
	{
		this.isPushSound = isPushSound;
	}

	public int getIsPushVibrate()
	{
		return isPushVibrate;
	}

	public void setIsPushVibrate(int isPushVibrate)
	{
		this.isPushVibrate = isPushVibrate;
	}

	public String getPushSound()
	{
		return pushSound;
	}

	public void setPushSound(String pushSound)
	{
		this.pushSound = pushSound;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean isAlim()
	{
		return isAlim;
	}

	public void setAlim(boolean isAlim)
	{
		this.isAlim = isAlim;
	}

	public int getPushTypeID()
	{
		return pushTypeID;
	}

	public void setPushTypeID(int pushTypeID)
	{
		this.pushTypeID = pushTypeID;
	}

	public HashMap<String, String> getCustomData()
	{
		return customData;
	}

	public void setCustomData(HashMap<String, String> customData)
	{
		this.customData = customData;
	}

	public void addCusomData(String key, String value)
	{
		this.customData.put(key, value);
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataBean [notifyWithSound=" + notifyWithSound + ", vibration=" + vibration + ", notificationSound="
				+ notificationSound + ", rid=" + rid + ", senduid=" + senduid + ", recvuid=" + recvuid + ", data="
				+ data + ", mtime=" + mtime + ", message=" + message + ", pushTypeID=" + pushTypeID + ", imgUrl="
				+ imgUrl + ", isAlim=" + isAlim + ", pushSound=" + pushSound + ", isPushSound=" + isPushSound
				+ ", isPushVibrate=" + isPushVibrate + ", customData=" + customData + "]";
	}
	

}