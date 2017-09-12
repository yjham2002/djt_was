package com.appg.djTalk.common.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class CookieUtil
{

	public final String			APP_LOGIN_COOKIE_NAME	= "APP_LOGIN_INFO";

	private String				SPLIT_KEY_VALUE_CHAR	= "";
	private String				SPLIT_ENTRY_CHAR		= String.valueOf((char) 31);
	private final String		COOKIE_AES_KEY			= "APPGCOOKIEAES256";
	private AES256Util			AES256Util				= null;
	private HttpServletRequest	request					= null;
	private HttpServletResponse	response				= null;

	private CookieUtil() throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException
	{
		AES256Util = new AES256Util(COOKIE_AES_KEY);
		this.SPLIT_KEY_VALUE_CHAR = AES256Util.encrypt("KV");
		this.SPLIT_ENTRY_CHAR = AES256Util.encrypt("EC");
	}

	private CookieUtil(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException
	{
		this();
		this.request = request;
		this.response = response;
	}

	/**
	 * 객체 생성
	 * 
	 * @return
	 * @throws GeneralSecurityException
	 * @throws NoSuchAlgorithmException
	 */
	public static CookieUtil getInstance(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, GeneralSecurityException
	{
		return new CookieUtil(request, response);
	}

	/**
	 * 쿠키 데이터 조회
	 * 
	 * @param cookieName
	 * @return
	 */
	private Cookie getCookie(String cookieName)
	{
		Cookie retCookie = null;
		Cookie[] cookies = this.request.getCookies();

		if(cookies != null)
		{
			for (Cookie cookie : cookies)
			{
				if(cookieName.equalsIgnoreCase(cookie.getName()))
				{
					retCookie = cookie;
				}
			}
		}

		return retCookie;
	}

	/**
	 * 쿠키 생성
	 * 
	 * @param data
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void makeCookie(HashMap<String, Object> data, String cookieName) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException
	{
		this.createCookie(data, cookieName, -1);
	}

	/**
	 * 쿠키 생성
	 * 
	 * @param data
	 * @param cookieName
	 * @param expirySec
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws GeneralSecurityException
	 */
	private void createCookie(HashMap<String, Object> data, String cookieName, int expirySec) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException
	{
		Cookie cookie = this.getCookie(cookieName);
		String cookieValue = "";

		System.out.println("data => " + data);
		
		if(cookie == null)
		{
			cookie = new Cookie(cookieName, "");
		}

		for (Entry<String, Object> entry : data.entrySet())
		{
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			if(!"userPwd".equalsIgnoreCase(key)){
				String entryData = key + this.SPLIT_KEY_VALUE_CHAR + value;
				cookieValue += ((cookieValue == "") ? entryData : (this.SPLIT_ENTRY_CHAR + entryData));
			}
		}
		
		System.out.println("cookieValue => " + cookieValue);
		
		cookie.setValue(AES256Util.encrypt(cookieValue));
		cookie.setPath("/");
		
		
		if(expirySec > 0)
			cookie.setMaxAge(expirySec);

		response.addCookie(cookie);
		
		System.out.println("COOKIE => " + this.getCookieData(cookieName));
	}

	/**
	 * 쿠키 데이터 조회
	 * 
	 * @param cookieName
	 * @return
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public HashMap<String, Object> getCookieData(String cookieName) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException
	{

		HashMap<String, Object> data = new HashMap<String, Object>();
		Cookie cookie = this.getCookie(cookieName);
		System.out.println(cookieName);
		System.out.println("cookie => " + cookie);
		
		if(cookie == null)
			return null;

		String cookieValue = cookie.getValue();

		if(cookieValue == "")
			return null;

		cookieValue = AES256Util.decrypt(cookieValue);

		String[] entryArr = cookieValue.split(this.SPLIT_ENTRY_CHAR);

		try
		{
			for (String entry : entryArr)
			{
				String[] entryData = entry.split(this.SPLIT_KEY_VALUE_CHAR);
				data.put(entryData[0], (entryData.length > 1) ? entryData[1] : "");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		return data;
	}

	/**
	 * 쿠키 삭제
	 * 
	 * @param cookieName
	 */
	public void delCooke(String cookieName)
	{
		Cookie cookie = this.getCookie(cookieName);

		if(cookie == null)
			return;

		cookie.setValue(null);
		cookie.setMaxAge(0);

		this.response.addCookie(cookie);
	}

	/**
	 * AES 암호화 유틸
	 * 
	 * @author inyeong
	 *
	 */
	private class AES256Util
	{
		private String	iv;
		private Key		keySpec;

		/**
		 * 16자리의 키값을 입력하여 객체를 생성한다.
		 * 
		 * @param key
		 *            암/복호화를 위한 키값
		 * @throws UnsupportedEncodingException
		 *             키값의 길이가 16이하일 경우 발생
		 */
		public AES256Util(String key) throws UnsupportedEncodingException
		{
			this.iv = key.substring(0, 16);
			byte[] keyBytes = new byte[16];
			byte[] b = key.getBytes("UTF-8");
			int len = b.length;
			if(len > keyBytes.length)
			{
				len = keyBytes.length;
			}
			System.arraycopy(b, 0, keyBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

			this.keySpec = keySpec;
		}

		/**
		 * AES256 으로 암호화 한다.
		 * 
		 * @param str
		 *            암호화할 문자열
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws GeneralSecurityException
		 * @throws UnsupportedEncodingException
		 */
		public String encrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException
		{
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
			String enStr = new String(Base64.encodeBase64(encrypted));
			return enStr;
		}

		/**
		 * AES256으로 암호화된 txt 를 복호화한다.
		 * 
		 * @param str
		 *            복호화할 문자열
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws GeneralSecurityException
		 * @throws UnsupportedEncodingException
		 */
		public String decrypt(String str) throws NoSuchAlgorithmException, GeneralSecurityException, UnsupportedEncodingException
		{
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] byteStr = Base64.decodeBase64(str.getBytes());
			return new String(c.doFinal(byteStr), "UTF-8");
		}
	}

}
