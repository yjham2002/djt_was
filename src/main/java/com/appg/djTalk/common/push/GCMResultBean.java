package com.appg.djTalk.common.push;

public class GCMResultBean
{
	private String	reqRegistrationKey;
	private String	newRegistrationKey;

	public String getReqRegistrationKey()
	{
		return reqRegistrationKey;
	}

	public void setReqRegistrationKey(String reqRegistrationKey)
	{
		this.reqRegistrationKey = reqRegistrationKey;
	}

	public String getNewRegistrationKey()
	{
		return newRegistrationKey;
	}

	public void setNewRegistrationKey(String newRegistrationKey)
	{
		this.newRegistrationKey = newRegistrationKey;
	}

	@Override
	public String toString()
	{
		return "GCMResultBean [reqRegistrationKey=" + reqRegistrationKey + ", newRegistrationKey=" + newRegistrationKey + "]";
	}

}
