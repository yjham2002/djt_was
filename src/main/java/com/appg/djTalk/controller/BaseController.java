package com.appg.djTalk.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.bean.RetJsonBean;
import com.appg.djTalk.common.util.CookieUtil;

public class BaseController{

	/**
	 * req 데이터 파싱
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected DataMap makeProcessData(HttpServletRequest request, HttpServletResponse response){
		DataMap params = new DataMap();
		Enumeration<String> paramNames = request.getParameterNames();
		
		while (paramNames.hasMoreElements()){
			String name = (String) paramNames.nextElement();
			String[] value = request.getParameterValues(name);
			// String value = request.getParameter(name);

			if(value.length > 1)
				params.put(name, value);
			else
				params.put(name, value[0]);
		}
		
		return params;

	}
	
	
	protected List<DataMap> makeProcessDataList(HttpServletRequest request, HttpServletResponse response){
		List<DataMap> params = new ArrayList<DataMap>();
		Enumeration<String> paramNames = request.getParameterNames();
		
//		while (paramNames.hasMoreElements()){
//			String name = (String) paramNames.nextElement();
//			String[] value = request.getParameterValues(name);
//			// String value = request.getParameter(name);
//
//			if(value.length > 1)
//				params.put(name, value);
//			else
//				params.put(name, value[0]);
//		}
//		
		return params;

	}

	/**
	 * 결과 셋 만들기
	 * 
	 * @param returnCode
	 * @param returnMessage
	 * @param entity
	 * @param addData
	 * @return
	 */
	protected RetJsonBean makeResultJson(int returnCode, String returnMessage, Object entity, Object addData)
	{
		RetJsonBean resultData = new RetJsonBean();
		resultData.setReturnCode(returnCode);
		resultData.setReturnMessage(returnMessage);
		resultData.setEntity(entity);
		resultData.setAddData(addData);

		return resultData;

	}

	/**
	 * 결과 셋 만들기
	 * 
	 * @param returnCode
	 * @param returnMessage
	 * @param entity
	 * @return
	 */
	protected RetJsonBean makeResultJson(int returnCode, String returnMessage, Object entity)
	{
		RetJsonBean resultData = new RetJsonBean();
		resultData.setReturnCode(returnCode);
		resultData.setReturnMessage(returnMessage);
		resultData.setEntity(entity);

		return resultData;

	}

	/**
	 * 결과 셋 만들기
	 * 
	 * @param returnCode
	 * @param returnMessage
	 * @return
	 */
	protected RetJsonBean makeResultJson(int returnCode, String returnMessage)
	{
		RetJsonBean resultData = new RetJsonBean();
		resultData.setReturnCode(returnCode);
		resultData.setReturnMessage(returnMessage);

		return resultData;

	}
	
	/**
	 * 어플 로그인 데이터 조회
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	protected DataMap getAppLoginData(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CookieUtil cookieUtil = CookieUtil.getInstance(request, response);

		HashMap<String, Object> cookieData = cookieUtil.getCookieData(cookieUtil.APP_LOGIN_COOKIE_NAME);
		
		if(cookieData == null)
			return null;

		DataMap loginData = new DataMap();
		for (Entry<String, Object> entry : cookieData.entrySet())
		{
			loginData.put(entry.getKey(), entry.getValue());
		}

		return loginData;
	}
}
