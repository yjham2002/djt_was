package com.appg.djTalk.common.upload;

@SuppressWarnings("serial")
public class FileUploadException extends Exception
{
	private int		ErrorCode		= 0;
	private String	ErrorMessage	= "";

	public FileUploadException(int ErrorCode, String ErrorMessage)
	{
		super();

		this.ErrorCode = ErrorCode;
		this.ErrorMessage = ErrorMessage;
	}

	public int getErrorCode()
	{
		return this.ErrorCode;
	}

	public String getErrorMessage()
	{
		return this.ErrorMessage;
	}

	/**
	 * 파일 유틸 에러 정의 클래스
	 * 
	 * @author inyeong
	 *
	 */
	public static class FUECode
	{
		public static final int		DO_NOT_MAKE_DIR_CODE				= -10;
		public static final String	DO_NOT_MAKE_DIR_MSG					= "디렉토리 생성 실패";

		public static final int		FILE_SIZE_ERROR_CODE				= -11;
		public static final String	FILE_SIZE_ERROR_MSG					= "파일 용량을 확인해주세요.";

		public static final int		FILE_AVAIL_EXTENSION_ERROR_CODE		= -12;
		public static final String	FILE_AVAIL_EXTENSION_ERROR_MSG		= "업로드 가능한 확장자가 아닙니다.";

		public static final int		FILE_PREVENT_EXTENSION_ERROR_CODE	= -13;
		public static final String	FILE_PREVENT_EXTENSION_ERROR_MSG	= "업로드 가능한 확장자가 아닙니다.";

	}

}
