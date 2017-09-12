package com.appg.djTalk.common.bean.db;

public class AdminUserBean
{
	private int adminNumber;
	private String adminID;
	private String adminPwd;
	private String accessLevelCode;
	private String adminName;
	private String adminURL;
	private String regDate;

	
	public String getAccessLevelCode()
	{
		return accessLevelCode;
	}
	public void setAccessLevelCode(String accessLevelCode)
	{
		this.accessLevelCode = accessLevelCode;
	}
	public int getAdminNumber()
	{
		return adminNumber;
	}
	public void setAdminNumber(int adminNumber)
	{
		this.adminNumber = adminNumber;
	}
	public String getAdminID()
	{
		return adminID;
	}
	public void setAdminID(String adminID)
	{
		this.adminID = adminID;
	}
	public String getAdminPwd()
	{
		return adminPwd;
	}
	public void setAdminPwd(String adminPwd)
	{
		this.adminPwd = adminPwd;
	}
	public String getAdminName()
	{
		return adminName;
	}
	public void setAdminName(String adminName)
	{
		this.adminName = adminName;
	}
	public String getAdminURL()
	{
		return adminURL;
	}
	public void setAdminURL(String adminURL)
	{
		this.adminURL = adminURL;
	}
	public String getRegDate()
	{
		return regDate;
	}
	public void setRegDate(String regDate)
	{
		this.regDate = regDate;
	}
	
	
}
