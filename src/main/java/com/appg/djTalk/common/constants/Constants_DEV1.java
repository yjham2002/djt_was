package com.appg.djTalk.common.constants;

public class Constants_DEV1 {
	public static final boolean	IS_DEBUG				= true;
	public static final boolean	IS_MONITORING			= true;
	public static final boolean	IS_SERVER_REAL			= false;
	public static final String	PROJECT_PATH			= "C:/workspace/HseqChat";

	public static final String	UPLOAD_FILE_PATH_BASE	= PROJECT_PATH + "/webapp/resources/uploadFile";
	public static final String	UPLOAD_FILE_PATH_ORGIN	= UPLOAD_FILE_PATH_BASE + "/origin";
	public static final String	UPLOAD_FILE_PATH_720	= UPLOAD_FILE_PATH_BASE + "/720";
	public static final String	UPLOAD_FILE_PATH_240	= UPLOAD_FILE_PATH_BASE + "/240";

	public static final String	LOG_PATH_BASE			= PROJECT_PATH + "/log";

	 public static final String DW_GATE_URL = "http://mobile.daewooenc.com/imms/gateway/service/req.co";

	// 채팅
	public static final String	CHAT_HOST_DOMAIN		= "http://182.161.118.76:4080";
	public static final int		CHAT_VENDOR				= 1005;

	// APNS
	public static final String	APNS_KEYSTORE_PATH		= PROJECT_PATH + "/webapp/authFile/HSE-Q_dev.p12";
	public static final String	APNS_KEYSTORE_PASS		= "pass";
	public static final boolean	APNS_IS_PRODUCTION		= false;

	// GCM
	public static final String	GCM_GOOGLE_SERVER_KEY	= "AIzaSyBMIZOW4wNlY2-AM1iTE-Eu6TAXzDjFlYQ";
	public static final String	GCM_MESSAGE_KEY			= "message";
	public static final String	GCM_COLLAPSE_KEY		= "score_update";
	public static final int		GCM_TIME_TO_LIVE		= 1;
	public static final boolean	GCM_DELAY_WHILE_IDLE	= false;
	
	
	// 240 이미지 path 생성
	public static final String getImgPath_240(String imgPath)
	{
		return 	UPLOAD_FILE_PATH_240 + "/" + imgPath;
	}
	
	// 720 이미지 path 생성
	public static final String getImgPath_720(String imgPath)
	{
		return 	UPLOAD_FILE_PATH_720 + "/" + imgPath;
	}
	
	// 원본 파일 경로 생성
	public static final String getFilePath(String imgPath)
	{
		return 	UPLOAD_FILE_PATH_ORGIN + "/" + imgPath;
	}
}
