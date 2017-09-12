package com.appg.djTalk.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.appg.djTalk.common.bean.DataMap;
import com.appg.djTalk.common.bean.ListBean;

public class DataUtil
{

	/**
	 * 랜던 숫자코드 만들기
	 * 
	 * @param codeLength
	 * @return
	 */
	public static String getRandomCode_Number(int codeLength)
	{
		int startNum = 0;
		int endNum = (int) (Math.pow(10, codeLength)) -1;

		int randomNumber = getRandomNumber(startNum, endNum);

		return setLeftPad(String.valueOf(randomNumber), codeLength, "0");

	}

	
	/**
	 * 랜던 문자코드 만들기
	 * 
	 * @param codeLength
	 * @return
	 */
	public static String getRandomCode_Character(int codeLength)
	{
		return getRandomCharacter(codeLength);
	}

	/**
	 * 특정범위 랜덤 숫자 생성
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getRandomNumber(int min, int max)
	{
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	/**
	 * 문자 코드 만들기
	 * 
	 * @param charLength
	 * @return
	 */
	private static String getRandomCharacter(int charLength)
	{
		String codeString = "";

		for (int i = 0; i < charLength; i++)
		{
			// 아스키 코드의 문자 중 대문자를 기준으로 코드를 하나씩 뽑음
			int charAscii = getRandomNumber(65, 90);
			codeString += Character.toString((char) charAscii);
		}

		return codeString;
	}

	/**
	 * 왼쪽으로 특정 문자 채우기
	 * 
	 * @param str
	 * @param strSize
	 * @param padChar
	 * @return
	 */
	public static String setLeftPad(String str, int strSize, String padString)
	{
		return StringUtils.leftPad(str, strSize, padString);
	}

	/**
	 * 왼쪽으로 특정 문자 채우기
	 * 
	 * @param str
	 * @param strSize
	 * @param padChar
	 * @return
	 */
	public static String setRightPad(String str, int strSize, String padString)
	{
		return StringUtils.rightPad(str, strSize, padString);
	}

	/**
	 * 쿼리스트링 파싱
	 * 
	 * @param queryString
	 * @return
	 */
	public static HashMap<String, String> parseParam(String queryString)
	{
		HashMap<String, String> params = new HashMap<String, String>();

		if(queryString.indexOf("?") > -1)
			queryString = queryString.substring(0, queryString.indexOf("?"));

		if(!queryString.equals(""))
		{
			String[] paramsList = queryString.split("&");
			for (int i = 0; i < paramsList.length; i++)
			{
				System.out.println("parseParam : " + paramsList[i]);
				String[] pair = paramsList[i].split("=");
				params.put(pair[0], pair[1]);
			}
		}

		return params;

	}
	
	/**
	 * 페이징 데이타
	 * @param bean
	 * @return
	 */
	public static DataMap pagingData(ListBean bean) {
		DataMap data = new DataMap();
		data.put("page", bean.getPage());
		data.put("startBlock", bean.getStartPage());
		data.put("endBlock", bean.getEndPage());
		data.put("endPage", bean.getTotalPage());
		data.put("isNextBlock", bean.getIsNextBlock());
		data.put("isPrevBlock", bean.getIsPrevBlock());
		data.put("virtualNum", bean.getVirtualNum());
		
		return data;
	}
	
	public static String pagingStr(ListBean bean) {
		StringBuilder sb = new StringBuilder();
		
		if(bean.getIsPrevBlock() > 0) {
			sb.append("<a href='#' ><img class='jPage' page='1' src='/NaeKkot/resources/imgs/admin/paging_first.gif' alt='처음페이지'></a>");
			sb.append("<a href='#' ><img class='jPage' page='"+(bean.getStartPage() - 1)+"' src='/NaeKkot/resources/imgs/admin/paging_prev.gif' alt='이전'></a>");
		}
		for(int i = bean.getStartPage(); i <= bean.getEndPage(); i++) {
			if(bean.getPage() == i) sb.append("<a href='#' ><strong>"+i+"</strong></a>");
			else sb.append("<a href='#' class='jPage' page='"+i+"'>"+i+"</a>");
		}
		if(bean.getIsNextBlock() > 0) {
			sb.append("<a href='#' ><img class='jPage' page='"+(bean.getEndPage() + 1)+"' src='/NaeKkot/resources/imgs/admin/paging_next.gif' alt='다음'></a>");
			sb.append("<a href='#' ><img class='jPage' page='"+bean.getTotalPage()+"' src='/NaeKkot/resources/imgs/admin/paging_last.gif' alt='마지막페이지'></a>");
		}
		
		return sb.toString();
	}
	
	// 16진수를 원래대로 복원
	public static String deHex(String input)
	{

		StringBuffer returnStr = new StringBuffer("");

		String str = null;
		for (int i = 0; i < input.length(); i = i + 2)
		{
			str = input.substring(i, i + 2);
			char c = (char) Integer.parseInt(str, 16);
			returnStr.append(c);
		}
		return returnStr.toString();
	}

	// 16진수로 변환
	public static String enHex(String input)
	{

		input = (input == null) ? "" : input;

		StringBuffer returnStr = new StringBuffer("");

		for (int i = 0; i < input.length(); i++)
		{
			int k = input.charAt(i);
			String str = Integer.toHexString(k);
			returnStr.append(str);
		}

		return returnStr.toString();
	}
	
	//상품코드 생성	AA000000~ZZ999999
	public static String createCode(){
		
		Random rand = new Random();

		String retStr = "";
		String numStr = "1";
	    String plusNumStr = "1";
		String[] codeStr = new String[2];
		int min = 65;	// 65 == A
		int max = 90;		// 90 == Z
		int digit = 6;	//숫자의 수
		
		
		//알파벳 두자리 생성
		int a = rand.nextInt(max - min + 1) + min;
		int b = rand.nextInt(max - min + 1) + min;
		codeStr[0] = String.valueOf((char)a);
		codeStr[1] = String.valueOf((char)b);
		
		for(String makeStr : codeStr){
			retStr += makeStr;
		}
		
	    int code = 0;
	    
	    for (int i = 0; i < digit; i++) {
	        numStr += "0";
	 
	        if (i != digit - 1) {
	            plusNumStr += "0";
	        }
	    }
	 
	    code = rand.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);
	    if (code > Integer.parseInt(numStr)) {
	        code = code - Integer.parseInt(plusNumStr);
	    }
	    
	    
		
		return retStr + code;
	}
	
	/**
	 * 현재날짜를 YYYYMMDDHHMMSS로 리턴
	 */
	public static String getyyyyMMddHHmmss(){
		/** yyyyMMddHHmmss Date Format */
		SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

		return yyyyMMddHHmmss.format(new Date());
	}
	
	public static String SHA256Salt(String strData, String salt) { 
		  String SHA = "";
		  
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.reset();
			sh.update(salt.getBytes());
			byte byteData[] = sh.digest(strData.getBytes());
			
			//Hardening against the attacker's attack
			sh.reset();
			byteData = sh.digest(byteData);
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

			}
			
			SHA = sb.toString();
			byte[] raw = SHA.getBytes();
			byte[] encodedBytes = Base64.encodeBase64(raw);
			SHA = new String(encodedBytes);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		
		return SHA;
	}
	

	
	
}
