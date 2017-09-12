package http;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.appg.djTalk.common.bean.DataMap;

public class HttpRequest
{
	private final static Log	logger	= LogFactory.getLog(HttpRequest.class);

	public static String post(String url, List<DataMap> params) throws Exception
	{
		HttpClient httpclient = new DefaultHttpClient();

		try
		{
			HttpPost httppost = new HttpPost(url);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

			// nameValuePairs.add(new BasicNameValuePair("data", URLEncoder.encode(jsonData, "UTF-8")));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);

			return responseBody;
		}
		catch (Exception ex)
		{
			logger.error("ex", ex);
		}
		finally
		{
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	public static String multipart(String url, String jsonData, File file) throws Exception
	{
		HttpClient httpclient = new DefaultHttpClient();

		try
		{

			HttpPost httppost = new HttpPost(url);

			MultipartEntity multiPartEntity = new MultipartEntity();
			multiPartEntity.addPart("data", new StringBody(URLEncoder.encode(jsonData, "UTF-8")));

			FileBody fileBody = new FileBody(file);
			multiPartEntity.addPart("fileData", fileBody);
			httppost.setEntity(multiPartEntity);

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);

			return responseBody;
		}
		catch (Exception ex)
		{
			logger.error("ex", ex);
		}
		finally
		{
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}
}
