package com.appg.djTalk.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

import com.appg.djTalk.common.constants.Constants;

public class FileLogUtil
{

	/**
	 * 파일 로그 생성
	 * 
	 * @param basePath
	 * @param logFolderName
	 * @param logFileName
	 * @param logContents
	 */
	public static void writeFileLog(String basePath, String logFolderName, String logFileName, String logContents)
	{
		String logDirPath = getLogDirPath(basePath, logFolderName);
		logFileName = getLogFileName(logFileName);
		String logFilePath = logDirPath + "/" + logFileName;

		if(!makeDir(logDirPath))
			return;

		try
		{
			File logFile = new File(logFilePath);
			FileUtils.writeStringToFile(logFile, "\n" + logContents, true);

			if(Constants.IS_MONITORING)
			{
				System.out.println(logContents);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 로그 패스 생성
	 * 
	 * @param dirPath
	 * @return
	 */
	private static boolean makeDir(String dirPath)
	{
		File file = new File(dirPath);

		if(file.exists() == false)
		{
			return file.mkdirs();
		}

		return true;

	}

	/**
	 * 실제 로그 저장될 경로 만들기
	 * 
	 * @param basePath
	 * @return
	 */
	private static String getLogDirPath(String basePath, String logFolderName)
	{
		String logDirPath = basePath + "/" + logFolderName + "/" + getMonthPath();

		return logDirPath;
	}

	/**
	 * 월 경로 생성
	 * 
	 * @return
	 */
	private static String getMonthPath()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		String monthPath = df.format(cal.getTime());

		return monthPath;
	}

	/**
	 * 로그 파일명 생성
	 * 
	 * @return
	 */
	private static String getLogFileName(String logFileName)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String ymd = df.format(cal.getTime());

		logFileName = logFileName + "_" + ymd + ".log";

		return logFileName;
	}
}
