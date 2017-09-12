package com.appg.djTalk.common.constants;

public class Constants
{

	public static final boolean	IS_DEBUG				= true;
	public static final boolean	IS_MONITORING			= true;
	public static final boolean	IS_SERVER_REAL			= false;
	
//	public static final String		PROJECT_PATH			= "C:/Users/a/workspace/djTalk/"; // 개발 경로
//	public static final String		CERT_PATH				= "C:/Users/a/workspace/djTalk/webapp/authFile"; // 인증서 경로
//	public static final String		UPLOAD_PATH				= "C:/Users/a/workspace/djTalk/uploads";
	
	public static final String		PROJECT_PATH			= "/home"; // 개발 경로
	public static final String		CERT_PATH				= "/home"; // 인증서 경로
	public static final String		UPLOAD_PATH				= "/home/uploads";
	
	
	//public static final String	UPLOAD_FILE_PATH_BASE	= PROJECT_PATH + "/webapp/resources/uploadFile";
	public static final String	UPLOAD_FILE_PATH_BASE	= UPLOAD_PATH + "/files";
	public static final String	UPLOAD_FILE_PATH_ORGIN	= UPLOAD_FILE_PATH_BASE + "/origin";
	public static final String	UPLOAD_FILE_PATH_1080	= UPLOAD_FILE_PATH_BASE + "/1080";
	public static final String	UPLOAD_FILE_PATH_720	= UPLOAD_FILE_PATH_BASE + "/720";
	public static final String	UPLOAD_FILE_PATH_480	= UPLOAD_FILE_PATH_BASE + "/480";
	public static final String	UPLOAD_FILE_PATH_240	= UPLOAD_FILE_PATH_BASE + "/240";
	public static final String	UPLOAD_FILE_PATH_100	= UPLOAD_FILE_PATH_BASE + "/100";
	
	public static final String	LOG_PATH_BASE			= UPLOAD_PATH + "/log";

	public static final String	EMAIL_ID				= "hakmin.kim@richware.co.kr"; // TEST dev.hakmin 계정
	//public static final String	EMAIL_ID				= "misstar8@hotmail.com"; 
	public static String[]		UPLOAD_IMAGE_RESIZE		= {"240", "480", "720", "1080", "origin"};
	
	public static final int		ROWPER_SIZE				= 20;
	public static final int 	BLOCK_SIZE 				= 10;

    
    public static final String	IMG_PATH_URL			= "http://localhost:8080/uploads";
    public static final String	IMG_PATH				= "http://localhost:8080/uploads/files";
	
//    public static final String	IMG_PATH_URL			= "/NaeKkot/resources";
//    public static final String	IMG_PATH				= "/NaeKkot/resources/uploadImg";
    
    
    public static final String	CUSTOMER_ID				= "naekkot";
    public static final String	strKeyID  				= "79033b0eaaa28f43";
    public static String		strEncKey 				= "3f03b6c4f9b9f3f612d06aa11e7b6103";
    public static String		strKeyVer 				= "001";
	/**
	 * 웹용 Const
	 */
	public static final String COOKIE_KEY					= "d$i$a$o$y@u@b#i#n#g#g!a!n!CookieEncryptKey";
	public static final String REDIRECT_URL = "forward:/common/redirect";
	public static final String JSSCRIPT_URL = "forward:/common/jsscript";
	public static final String ADMIN_SESSION = "SERVER_ADMIN_INFO";
	public static final String ADMIN_COOKIR_KEY = "ADMIN_APP_INFO";
	public static final String BASE_PATH = "/NaeKkot";
	public static final String RESOURCES_PATH = "/NaeKkot/resources";
	public static final String USER_INFO = "USER_INFO_RICH";
	
	// APNS 셋팅
	//public static final String	APNS_KEYSTORE_PATH		= "/home/misstar/authFile/DST_PUSH_MEDIK.p12";
	public static final String APNS_KEYSTORE_PATH 		= CERT_PATH + "/APNS_PUSH_HIT.p12";
	public static final String	APNS_KEYSTORE_PASS		= "pass";
	public static final boolean	APNS_IS_PRODUCTION		= true;

	// GCM 셋팅
	public static final String	GCM_GOOGLE_SERVER_KEY	= "AAAAxxMsecI:APA91bE_Vj3DLeEz3oLOtBQJ8yZfKJVlCBOlmf7uiHEgjsjGvXcXq2kafrcRVNtoIMUfyUQ6pWeC-Ax1Lcjaa0kxD1kJFz7_t35g-g12h-zNk1B3wYrMZz1UG6LUQ4L33d9TE2RuXDxg";
	public static final String	GCM_MESSAGE_KEY			= "message";
	public static final String	GCM_COLLAPSE_KEY		= "score_update";
	public static final int		GCM_TIME_TO_LIVE		= 1;
	public static final boolean	GCM_DELAY_WHILE_IDLE	= false;

	public static final String NOTI_SOUND_DEFAULT = "default";
	public static final String NOTI_SOUND_SOUND01 = "sound01";
	public static final String NOTI_SOUND_SOUND02 = "sound02";
	public static final String NOTI_SOUND_SOUND03 = "sound03";
	public static final String NOTI_SOUND_SOUND04 = "sound04";
	public static final String NOTI_SOUND_SOUND05 = "sound05";
	
	// 공통코드 관련
	public interface CommonCode{
	
	}
	

	// 100 이미지 path 생성
	public static final String getImgPath_100(String imgPath)
	{
		return UPLOAD_FILE_PATH_100 + "/" + imgPath;
	}
	
	// 240 이미지 path 생성
	public static final String getImgPath_240(String imgPath)
	{
		return UPLOAD_FILE_PATH_240 + "/" + imgPath;
	}

	// 480 이미지 path 생성
	public static final String getImgPath_480(String imgPath)
	{
		return UPLOAD_FILE_PATH_480 + "/" + imgPath;
	}
	
	// 720 이미지 path 생성
	public static final String getImgPath_720(String imgPath)
	{
		return UPLOAD_FILE_PATH_720 + "/" + imgPath;
	}

	// 1080 이미지 path 생성
	public static final String getImgPath_1080(String imgPath)
	{
		return UPLOAD_FILE_PATH_1080 + "/" + imgPath;
	}
	
	// 원본 파일 경로 생성
	public static final String getFilePath(String imgPath)
	{
		return UPLOAD_FILE_PATH_ORGIN + "/" + imgPath;
	}

}
