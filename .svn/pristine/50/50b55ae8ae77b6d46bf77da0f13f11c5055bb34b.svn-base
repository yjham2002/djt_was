package com.appg.djTalk.common.util;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.appg.djTalk.common.constants.Constants;



public class WebAuth {
	
	//private final static Log logger = LogFactory.getLog(WebAuth.class);
	
	//세션 유지 시간. 
	final int _defaultCookieSecond = (60*10*60) * 8;
	final int _defaultSessionSecond = 60*60*24;
	public static WebAuth getInstance() {
		return new WebAuth();
	}
	
	public void setAuth(HttpServletRequest request, String authName, Object usr) throws Exception
	{
		HttpSession session  =  request.getSession(true);
		session.setAttribute(authName, usr);
		session.setMaxInactiveInterval(_defaultSessionSecond);
	}
	public void invalidateAuth(HttpServletRequest request,HttpServletResponse response,String applicationDomain,String authName) throws Exception
	{
		HttpSession session  =  request.getSession(false);
		if(session != null)
			session.invalidate();

	}
	public boolean hasAuth(HttpServletRequest request, String authName) throws Exception
	{
		boolean result = false;
		try
		{
			HttpSession session  =  request.getSession(false);
			
			if(session == null)
			{
				return false;
			}
			
			Object usr = (Object)session.getAttribute(authName);
			
			if(usr != null)
			{
				result =  true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	public void extendCookieExpireTime(HttpServletRequest request,HttpServletResponse response,String applicationDomain,String authName) throws Exception
	{
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null && cookies.length > 0) 
		{
			for (int i = 0 ; i < cookies.length ; i++) 
			{
				if(authName.equalsIgnoreCase(cookies[i].getName()))
				{
					
					long ts = (new Date()).getTime()/1000;
			    	long tlTime = ts + _defaultCookieSecond;
			    	
			    	EncryptMD5 md5 = new EncryptMD5();
			    	
			    	String decodedCookieString = md5.decrypt_md5(cookies[i].getValue(), Constants.COOKIE_KEY);
			    	
			    	JSONObject jsonCookieHash = new JSONObject(decodedCookieString);
					jsonCookieHash.put("expireTime", tlTime);
					
					String encodedCookieString = md5.encrypt_md5(jsonCookieHash.toString(), Constants.COOKIE_KEY);
					
					cookies[i].setValue(encodedCookieString);
					cookies[i].setMaxAge(_defaultCookieSecond);
					cookies[i].setPath("/");
					cookies[i].setDomain(applicationDomain);
					
					response.addCookie(cookies[i]);
				}
			}
		}
	}
	public String getUserKey(HttpServletRequest request,String authName) throws Exception
	{
		
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null && cookies.length > 0) 
		{
			for (int i = 0 ; i < cookies.length ; i++) 
			{	
				if(authName.equalsIgnoreCase(cookies[i].getName()))
				{
					EncryptMD5 md5 = new EncryptMD5();
					String decodedCookieString = md5.decrypt_md5(cookies[i].getValue(), Constants.COOKIE_KEY);
					
					JSONObject jsonCookieHash = new JSONObject(decodedCookieString);
					
					if(jsonCookieHash.length() > 1)
					{
						return jsonCookieHash.get("userID").toString();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 저장된 쿠키에서 key에 해당하는 값을 찾아서 리턴
	 * @param request
	 * @param authName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getAuthInfo(HttpServletRequest request, String authName, String key) throws Exception
	{
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null && cookies.length > 0) 
		{
			for (int i = 0 ; i < cookies.length ; i++) 
			{	
				if(authName.equalsIgnoreCase(cookies[i].getName()))
				{
					EncryptMD5 md5 = new EncryptMD5();
					String decodedCookieString = md5.decrypt_md5(cookies[i].getValue(), Constants.COOKIE_KEY);
					
					JSONObject jsonCookieHash = new JSONObject(decodedCookieString);
					
					if(jsonCookieHash.length() > 1)
					{
						return jsonCookieHash.get(key).toString();
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * 세션 로그인 정보에서 key에 해당한느 값을 리턴
	 * @param request
	 * @param authName
	 * @param key
	 * @return
	 */
	public Object getAuthInfoInSession(HttpServletRequest request, String authName, String key){
		HttpSession session = request.getSession(false);
		@SuppressWarnings("unchecked")
		Map<String, Object> loginInfo = (Map<String, Object>) session.getAttribute(authName);
		return loginInfo.get(key);
	}

}