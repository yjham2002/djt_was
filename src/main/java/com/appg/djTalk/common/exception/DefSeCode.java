package com.appg.djTalk.common.exception;

public class DefSeCode{
	
	public static final int 	GENERAL_SUCC				= 1;
	public static final int 	GENERAL_FAIL				= -1;
	
	public static final String 	PHONE_VALID 				= "인증되었습니다.";
	public static final String 	PHONE_INVALID 				= "학사시스템에 저장된 휴대폰 번호와\n일치하지 않습니다. 학사정보를\n수정 후 서비스를 이용해 주시기 바랍니다.";
	
	public static final int  	DISTURB_OFF			 		= 450;
	public static final String 	DISTURB_OFF_MSG			 	= "방해금지 모드 해제됨";
	
	public static final int  	DISTURB_ON			 		= 400;
	public static final String 	DISTURB_ON_MSG			 	= "방해금지 모드 설정됨";
	
	public static final int  	NOREFRESH_SUCCEEDED 		= 550;
	public static final String 	NOREFRESH_SUCCEEDED_MSG 	= "정보를 갱신하지 않습니다.";
	
	public static final int  	REFRESH_SUCCEEDED 		= 500;
	public static final String 	REFRESH_SUCCEEDED_MSG 	= "정보가 갱신되었습니다.";
	
	public static final int     LOGIN_SUCCEEDED 		= 1500;
	public static final String  LOGIN_SUCCEEDED_MSG		= "로그인 되었습니다.";
	
	public static final int		DO_NOT_LOGIN_CODE		= -1000;
	public static final String	DO_NOT_LOGIN_MSG		= "일치하는 정보가 없습니다.";
	
	public static final int		DUPLICATE_ID_CODE		= -1001;
	public static final String	DUPLICATE_ID_MSG		= "중복된 이메일이 있습니다. 다른 이메일을 입력해 주세요.";
	
	public static final int		WITHDRAW_USER_CODE		= -1002;
	public static final String	WITHDRAW_USER_MSG		= "탈퇴된 회원 입니다.";
	
	public static final int		BASIC_ERROR_CODE		= -9999;
	public static final String	BASIC_ERROR_MSG			= "고객센터로 문의해 주세요."; //고객센터로 문의해 주세요.

	public static final int		NOT_DB_SAVE_CODE		= -9998;
	public static final String	NOT_DB_SAVE_MSG			= "데이터 저장에 실패했습니다.";

	public static final int		NO_DATA_CODE			= -9000;
	public static final String	NO_DATA_MSG				= "데이터가 없습니다.";

	public static final int		INVALID_PARAMS_CODE		= -9001;
	public static final String	INVALID_PARAMS_MSG		= "INVALID PARAMS";

	public static final int		DO_NOT_COMPLETE_CODE	= -9002;
	public static final String	DO_NOT_COMPLETE_MSG		= "요청사항을 완료하지 못하였습니다.";
	
	public static final int		DUPLICATE_CODE			= -9003;
	public static final String	DUPLICATE_MSG			= "이미 사용중인 %s 입니다.";

}
