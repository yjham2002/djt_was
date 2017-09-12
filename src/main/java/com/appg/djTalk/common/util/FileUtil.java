package com.appg.djTalk.common.util;

import java.io.File;

public class FileUtil {

	/**
	 * 파일 멀티 삭제
	 * 
	 * @param filePaths
	 */
	public static void deleteFiles(String[] filePaths) {
		for (String filePath : filePaths) {
			deleteFile(filePath);
		}
	}

	/**
	 * 파일 삭제
	 * 
	 * @param filePath
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);

		return file.delete();

	}

}
