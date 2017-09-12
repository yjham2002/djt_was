package com.appg.djTalk.common.util.httpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

public class SimpleHttpClient
{

	private HttpClient				mClient				= null;
	private HttpConnectionManager	mConnMgr			= null;
	private PostMethod				mMethod				= null;
	private HttpState				mState				= null;

	/**
	 * 파라미터 리스트, String or File
	 */
	private ArrayList<GParam>		mParamList			= new ArrayList<GParam>();

	/**
	 * MultiPart 여부
	 */
	private boolean					isMultiPart			= false;

	/**
	 * 캐릭터셋
	 */
	private String					mCharset			= "UTF-8";

	/**
	 * URI
	 */
	private String					mUri				= "";

	/**
	 * 타임아웃
	 */
	private int						mTimeout			= 30 * 1000;

	/**
	 * 연결타임아웃
	 */
	private int						mConnectionTimeout	= 20 * 1000;

	public GBean execute()
	{
		GBean bean = new GBean();

		ByteArrayOutputStream outputStream = null;

		try
		{

			// URI체크
			if("".equals(mUri))
			{
				System.out.println("URI가 설정되지 않았습니다.");
				return bean;
			}

			// // Timeout 체크
			// if(mTimeout < 0)
			// {
			// System.out.println("타임아웃 시간을 올바르게 설정바랍니다.");
			// return bean;
			// }

			mClient = new HttpClient();
			
			// TODO 20141218 특정 USER_AGENT 추가시
			// mClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "INJAndroidApp");
			mConnMgr = mClient.getHttpConnectionManager();

			mConnMgr.getParams().setSoTimeout(mTimeout);
			mConnMgr.getParams().setConnectionTimeout(mConnectionTimeout);

			mClient.setHttpConnectionManager(mConnMgr);

			// State설정
			if(mState != null)
				mClient.setState(mState);

			mMethod = new PostMethod(mUri);

			int sizeOfParam = mParamList.size();
			int indexOfInput = 0;
			int i = 0;
			String name;
			Object value;

			if(isMultiPart)
			{
				Part[] parts = new Part[sizeOfParam];

				for (i = 0; i < sizeOfParam; i++)
				{
					name = mParamList.get(i).getName();
					value = mParamList.get(i).getValue();

					if(value instanceof String)
					{
						StringPart part = new StringPart(name, (String) value, mCharset);
						part.setContentType(null);
						part.setTransferEncoding(null);
						parts[indexOfInput] = part;
					}
					else if(value instanceof File)
					{
						FilePart part = new FilePart(name, (File) value);
						part.setTransferEncoding(null);
						parts[indexOfInput] = part;
					}
					indexOfInput++;

				}

				MultipartRequestEntity entity = new MultipartRequestEntity(parts, mMethod.getParams());

				mMethod.setRequestEntity(entity);

			}
			else
			{

				NameValuePair[] pairs = new NameValuePair[sizeOfParam];

				for (i = 0; i < sizeOfParam; i++)
				{
					name = mParamList.get(i).getName();
					value = mParamList.get(i).getValue();

					pairs[indexOfInput] = new NameValuePair(name, (String) value);
					indexOfInput++;
				}

				mMethod.addParameters(pairs);
				mMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + mCharset);
			}

			int requestResult = -1;

			requestResult = mClient.executeMethod(mMethod);

			outputStream = new ByteArrayOutputStream();
			byte[] byteArray = new byte[1024];
			int count = 0;

			while ((count = mMethod.getResponseBodyAsStream().read(byteArray, 0, byteArray.length)) > 0)
			{
				outputStream.write(byteArray, 0, count);
			}

			String strResponseBody = new String(outputStream.toByteArray(), mCharset);

			bean.putHttpStatus(requestResult);

			if(requestResult == HttpStatus.SC_OK)
			{
				bean.put("body", strResponseBody);
			}

		}
		catch (Exception e)
		{

			bean.putException(e);

			if(e instanceof FileNotFoundException)
			{
				System.out.println("파일을 찾을 수 없습니다.");
			}
			else if(e instanceof IOException)
			{
				e.printStackTrace();
				System.out.println("입/출력 오류가 발생했습니다.");
			}
			else if(e instanceof HttpException)
			{
				System.out.println("HTTP 오류가 발생했습니다.");
			}
			else if(e instanceof IllegalArgumentException)
			{
				System.out.println("HOST정보가 잘못되었습니다.");
			}
			else if(e instanceof SocketTimeoutException)
			{
				System.out.println("타임아웃이 발생했습니다.");
			}
			else if(e instanceof SocketException)
			{
				System.out.println("연결이 강제종료되었습니다.");
			}
			else
			{
				System.out.println("정의되지 않은 오류가 발생했습니다.");
			}

			// GLog.trace("HTTP 호출간 에러 발생", e);

		}
		finally
		{
			if(mMethod != null)
				mMethod.releaseConnection();

			if(outputStream != null)
			{
				try
				{
					outputStream.reset() ;
					outputStream.close() ;
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return bean;
	}

	// @Override
	// public void cancel()
	// {
	// if(mMethod != null)
	// {
	// if(isCancelled.compareAndSet(false, true))
	// {
	// mMethod.abort();
	// }
	// }
	// }

	/**
	 * URI 설정
	 * 
	 * @param uri
	 * @return
	 */
	public SimpleHttpClient setUri(String uri)
	{
		mUri = uri;

		return this;
	}

	/**
	 * 타임아웃
	 * 
	 * @param timeout
	 * @return
	 */
	public SimpleHttpClient setTimeout(int milliseconds)
	{
		mTimeout = milliseconds;

		return this;
	}

	/**
	 * 연결타임아웃
	 * 
	 * @param timeout
	 * @return
	 */
	public SimpleHttpClient setConnectionTimeout(int milliseconds)
	{
		mConnectionTimeout = milliseconds;

		return this;
	}

	/**
	 * 파라미터추가
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public SimpleHttpClient addParameter(String name, Object value)
	{
		GParam param = new GParam();

		if(value instanceof File)
		{
			isMultiPart = true;
			param.set(name, value);

		}
		else
		{
			param.set(name, String.valueOf(value));
		}

		mParamList.add(param);

		return this;
	}

	/**
	 * 파라미터추가
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public SimpleHttpClient addJsonParameter(String name, String value)
	{
		GParam param = new GParam();

		value = "\"" + value + "\"";

		param.set(name, value);

		mParamList.add(param);

		return this;
	}

	/**
	 * 쿠키추가
	 * 
	 * @param cookie
	 * @return
	 */
	public SimpleHttpClient addCookie(Cookie cookie)
	{
		addCookies(cookie);

		// System.out.println("cookieDomain : " + cookie.getDomain());
		// System.out.println("cookieName : " + cookie.getName());
		// System.out.println("cookiePath : " + cookie.getPath());
		// System.out.println("cookieValue : " + cookie.getValue());

		return this;
	}

	/**
	 * 쿠키추가
	 * 
	 * @param cookies
	 * @return
	 */
	public SimpleHttpClient addCookies(Cookie... cookies)
	{
		if(mState == null)
			mState = new HttpState();

		mState.addCookies(cookies);

		return this;
	}

	public static class GBean
	{

		public static final String		HTTP_STATUS_ERROR_MESSAGE	= "HTTP_STATUS_ERROR_MESSAGE";

		/**
		 * 결과
		 */
		private HashMap<String, Object>	mResult						= new HashMap<String, Object>();

		private Exception				mException					= null;

		private int						mHttpStatus					= HttpStatus.SC_OK;

		public void put(String key, Object value)
		{
			mResult.put(key, value);
		}

		public void putException(Exception ex)
		{
			mException = ex;
		}

		public void putHttpStatus(int status)
		{
			mHttpStatus = status;
		}

		public Object get(String key)
		{
			return get(key, null);
		}

		public Object get(String key, Object defaultValue)
		{
			if(has(key))
				defaultValue = mResult.get(key);

			return defaultValue;
		}

		public boolean has(String key)
		{
			return mResult.containsKey(key);
		}

		public void clear()
		{
			mResult.clear();
		}

		public Object remove(String key)
		{
			return mResult.remove(key);
		}

		public Exception getException()
		{
			return mException;
		}

		public int getHttpStatus()
		{
			return mHttpStatus;
		}

		@SuppressWarnings("unchecked")
		public Map.Entry<String, Object> entrySet()
		{
			return (Map.Entry<String, Object>) mResult.entrySet();
		}

	}

	public static class GParam
	{

		private String	mName	= "";
		private Object	mValue	= null;

		public String getName()
		{
			return mName;
		}

		public void setName(String name)
		{
			this.mName = name;
		}

		public Object getValue()
		{
			return mValue;
		}

		public void setValue(Object value)
		{
			this.mValue = value;
		}

		public void set(String name, Object value)
		{
			setName(name);
			setValue(value);
		}

	}

}
