package com.appg.djTalk.common.exception;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ServiceException extends Exception
{

	private int						ErrorCode		= 0;
	private String					ErrorMessage	= "";
	private HashMap<String, Object>	map				= null;

	public ServiceException(int ErrorCode)
	{
		super();

		this.ErrorCode = ErrorCode;
	}

	public ServiceException(int ErrorCode, String ErrorMessage)
	{
		super();

		this.ErrorCode = ErrorCode;
		this.ErrorMessage = ErrorMessage;
	}

	public ServiceException(int ErrorCode, String ErrorMessage, HashMap<String, Object> map)
	{
		super();

		this.ErrorCode = ErrorCode;
		this.ErrorMessage = ErrorMessage;
		this.map = map;
	}

	public int getErrorCode()
	{
		return this.ErrorCode;
	}

	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	public HashMap<String, Object> getErrorData()
	{
		return this.map;
	}

}
