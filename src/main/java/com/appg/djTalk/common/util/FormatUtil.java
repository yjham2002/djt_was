package com.appg.djTalk.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * 1. class 명 : FormatUtil.java<br> 2. class 설명 : 각종 금액, 날짜, 숫자 등 포맷하여 리턴해주는 유틸성 클래스 <br> 3. 최초 작성일 : 2005. 6. 9.<br> 4. 작성자 : 박한나(swucs@hotmail.com)<br> 5. 최근 수정일 / 수정자 : <br> 6. 변경 내역 : <br>
 */
public class FormatUtil
{

	/**
	 * 금액포맷으로 변환한다.<br>
	 * 
	 * @param sSource
	 *            String<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(String sSource)
	{
		try
		{
			return toPriceFormat(Double.parseDouble(sSource));
		}
		catch (Exception ex)
		{
			return "";
		}
	}

	/**
	 * 금액포맷으로 변환하며, 천원단위로 표시할 경우 iLimit에 1000을 넘긴다.<br>
	 * 
	 * @param sSource
	 *            String<br>
	 * @param iLimit
	 *            int 금액단위<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(String sSource, int iLimit)
	{
		try
		{
			return toPriceFormat(Long.parseLong(sSource), iLimit);
		}
		catch (Exception ex)
		{
			return "";
		}

	}

	public static boolean isNullorEmpty(String str)
	{
		if(str == null || str.equals(""))
			return true;

		return false;
	}

	/**
	 * 금액포맷으로 변환한다.<br>
	 * 
	 * @param iSource
	 *            int<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(int iSource)
	{
		return toPriceFormat((long) iSource);
	}

	/**
	 * 금액포맷으로 변환하며, 천원단위로 표시할 경우 iLimit에 1000을 넘긴다.<br>
	 * 
	 * @param sSource
	 *            String<br>
	 * @param iLimit
	 *            int 금액단위<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(int iSource, int iLimit)
	{
		return toPriceFormat((long) iSource, iLimit);
	}

	/**
	 * 금액포맷으로 변환한다.("###,###,###,###,###,##0")<br>
	 * 
	 * @param lSource
	 *            long<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(long lSource)
	{
		String sPattern = "###,###,###,###,###,##0";
		DecimalFormat decimalformat = new DecimalFormat(sPattern);

		return decimalformat.format(lSource);
	}

	public static String toPriceFormat(double lSource)
	{

		// String sPattern = "###,###,###,###,###,##0.000";
		String sPattern = "###,###,###,###,###,##0";
		DecimalFormat decimalformat = new DecimalFormat(sPattern);

		return decimalformat.format(lSource);
	}

	public static String toRateFormat(double butget, double actual)
	{
		double lSource = ((double) ((double) actual / (double) butget) * 100) - 100;

		String sPattern = "###,###,###,###,###,##0.#";
		DecimalFormat decimalformat = new DecimalFormat(sPattern);

		return decimalformat.format(lSource);
	}

	public static String toRateVacationFormat(double butget, double actual)
	{
		double lSource = ((double) ((double) actual / (double) butget) * 100);

		String sPattern = "###,###,###,###,###,##0.#";
		DecimalFormat decimalformat = new DecimalFormat(sPattern);

		return decimalformat.format(lSource);
	}

	/**
	 * 금액포맷으로 변환한다.<br>
	 * 
	 * @param lSource
	 *            long<br>
	 * @param iLimit
	 *            int<br>
	 * @return String<br>
	 *
	 */
	public static String toPriceFormat(long lSource, int iLimit)
	{
		lSource = lSource / iLimit;
		return toPriceFormat(lSource);
	}

	/**
	 * 날짜포맷으로 변환한다. (yyyy:년, MM:월, dd:일)<br>
	 * 
	 * @param sDate
	 *            String<br>
	 * @param sSeparator
	 *            String<br>
	 * @return String<br>
	 *
	 */
	public static String toDateFormat(String sDate, String sFormat)
	{
		String sResult = sDate;

		if(sDate == null)
			return sResult;

		sDate = sDate.replaceAll("\\D", "");

		Calendar calendar = Calendar.getInstance();

		int iYear = 0;
		int iMonth = 0;
		int iDay = 0;
		int iHourOfDay = 0;
		int iMinute = 0;
		// int iSecond = 0;

		if(sDate.length() >= 8)
		{
			iYear = Integer.parseInt(sDate.substring(0, 4));
			iMonth = Integer.parseInt(sDate.substring(4, 6)) - 1;
			iDay = Integer.parseInt(sDate.substring(6, 8));

			calendar.set(iYear, iMonth, iDay);
		}

		if(sDate.length() >= 14)
		{
			iHourOfDay = Integer.parseInt(sDate.substring(8, 10));
			iMinute = Integer.parseInt(sDate.substring(10, 12));
			// iSecond = Integer.parseInt(sDate.substring(12, 14));
			calendar.set(iYear, iMonth, iDay, iHourOfDay, iMinute, iMinute);
		}

		sResult = toDateFormat(calendar.getTime(), sFormat);

		return sResult;
	}

	/**
	 * 날짜포맷으로 변환한다. (yyyy:년, MM:월, dd:일)<br>
	 * 
	 * @param date
	 *            Date<br>
	 * @param sFormat
	 *            String<br>
	 * @return String<br>
	 *
	 */
	public static String toDateFormat(Date date, String sFormat)
	{
		String sResult = "";

		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
			sResult = sdf.format(date);
		}
		catch (Exception ex)
		{
			return sResult;
		}

		return sResult;
	}

	/**
	 * 날짜포맷으로 변환한다. (2004-10-27)<br>
	 * 
	 * @param sDate
	 *            String<br>
	 * @return String<br>
	 *
	 */
	public static String toDateFormat(String sDate)
	{
		return toDateFormat(sDate, "yyyy-MM-dd");
	}

	/**
	 * 날짜포맷으로 변환한다. (2004-10-27 23:59:59)<br>
	 * 
	 * @param sDate
	 *            String<br>
	 * @return String<br>
	 *
	 */
	public static String toDateTimeFormat(String sDate)
	{
		return toDateFormat(sDate, "yyyy-MM-dd hh:mm:ss");
	}

	public static String toDateFormatSimple(String sDate)
	{
		return toDateFormat(sDate, "yyyyMMdd");
	}

	/**
	 * 날짜포맷으로 변환한다. (2004-10-27)<br>
	 * 
	 * @param date
	 *            Date<br>
	 * @return String<br>
	 *
	 */
	public static String toDateFormat(Date date)
	{
		return toDateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 현재일을 기준으로 해당일을 구한다. (2004-10-27)<br>
	 * 
	 * @param date
	 *            Date<br>
	 * @return String<br>
	 *
	 */
	public static String getDateFormat(String sFormat, int year, int month, int day)
	{
		String sResult = toDateFormat(new java.util.Date(), "yyyyMMdd");

		Calendar calendar = Calendar.getInstance();

		int iYear = Integer.parseInt(sResult.substring(0, 4)) + year;
		int iMonth = Integer.parseInt(sResult.substring(4, 6)) - 1 + month;
		int iDay = Integer.parseInt(sResult.substring(6, 8)) + day;

		calendar.set(iYear, iMonth, iDay);
		sResult = toDateFormat(calendar.getTime(), sFormat);

		return sResult;
	}

	/**
	 * 현재일을 기준으로 해당일을 구한다. (2004-10-27)<br>
	 * 
	 * @param date
	 *            Date<br>
	 * @return String<br>
	 *
	 */
	public static String getDateFormat(int year, int month, int day)
	{
		String sResult = toDateFormat(new java.util.Date(), "yyyyMMdd");

		Calendar calendar = Calendar.getInstance();

		int iYear = Integer.parseInt(sResult.substring(0, 4)) + year;
		int iMonth = Integer.parseInt(sResult.substring(4, 6)) - 1 + month;
		int iDay = Integer.parseInt(sResult.substring(6, 8)) + day;

		calendar.set(iYear, iMonth, iDay);
		sResult = toDateFormat(calendar.getTime());

		return sResult;
	}

	/**
	 * Korean to Unicode
	 * 
	 * @param : korean
	 * @return : unicode
	 */
	public static String ksc2uni(String kor) throws UnsupportedEncodingException
	{
		String uni = new String(kor.getBytes("KSC5601"), "8859_1");
		return uni;
	}

	/**
	 * Unicode to Korean.
	 * 
	 * @param : unicode
	 * @return : korean.
	 */
	public static String uni2ksc(String uni) throws UnsupportedEncodingException
	{
		String kor = new String(uni.getBytes("8859_1"), "KSC5601");
		return kor;
	}

	/**
	 * 
	 * 숫자 전화번호를 받아서 - 를 붙여서 리턴
	 *
	 * @param phoneno
	 * @return phoneno
	 */
	public static String getPhoneNumberFormat(String phoneNo)
	{
		String result = "";

		if(phoneNo != null && !phoneNo.equals(""))
		{
			phoneNo = phoneNo.trim().replaceAll(" ", "");

			if(phoneNo.startsWith("02"))
			{// 서울만 지역코드 2자리임
				if(phoneNo.length() == 10)
				{// 4자리 뒤에 -
					result += "02-" + phoneNo.substring(2, 6) + "-" + phoneNo.substring(6);
				}
				else if(phoneNo.length() == 9)
				{// 3자리 뒤에 -
					result += "02-" + phoneNo.substring(2, 5) + "-" + phoneNo.substring(5);
				}
				else
				{
					result = phoneNo;
				}
			}
			else
			{// 서울이 아닌경우 지역코드 3자리
				if(phoneNo.length() == 11)
				{
					result += phoneNo.substring(0, 3) + "-" + phoneNo.substring(3, 7) + "-" + phoneNo.substring(7);
				}
				else if(phoneNo.length() == 10)
				{
					result += phoneNo.substring(0, 3) + "-" + phoneNo.substring(3, 6) + "-" + phoneNo.substring(6);
				}
				else
				{
					result = phoneNo;
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * 주민번호 유효성 체크
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean checkSsn(String value) throws Exception
	{
		try
		{
			// 값의 길이가 13자리이며, 7번째 자리가 1,2,3,4 중에 하나인지 check.
			String regex = "\\d{6}[1234]\\d{6}";

			if(!value.matches(regex))
			{
				return false;
			}

			// 앞 6자리의 값이 유효한 날짜인지 check.

			String strDate = value.substring(0, 6);

			strDate = ((value.charAt(6) == '1' || value.charAt(6) == '2') ? "19" : "20") + strDate;
			strDate = strDate.substring(0, 4) + "/" + strDate.substring(4, 6) + "/" + strDate.substring(6, 8);

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = dateformat.parse(strDate);
			String resultStr = dateformat.format(date);

			if(!resultStr.equalsIgnoreCase(strDate))
			{
				return false;
			}

			// 주민등록번호 마지막 자리를 이용한 check.
			char[] charArray = value.toCharArray();
			long sum = 0;
			int[] arrDivide = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5 };

			for (int i = 0; i < charArray.length - 1; i++)
			{
				sum += Integer.parseInt(String.valueOf(charArray[i])) * arrDivide[i];
			}

			int checkdigit = (int) ((int) (11 - sum % 11)) % 10;
			return (checkdigit == Integer.parseInt(String.valueOf(charArray[12]))) ? true : false;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
