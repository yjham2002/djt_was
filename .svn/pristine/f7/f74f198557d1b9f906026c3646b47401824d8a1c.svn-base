package com.appg.djTalk.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DateUtil
{
	public static String[]	weeks	= { "일", "월", "화", "수", "목", "금", "토" };

	public static Date getDate()
	{
		Calendar cal = Calendar.getInstance();

		return cal.getTime();

	}
	
	public static String convertToRelativeType(String millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(millis));

		int ampm = calendar.get(Calendar.AM_PM);
		String sAmpm = "";
		if(ampm == Calendar.AM) sAmpm = "오전";
		else sAmpm = "오후";
		int mYear = calendar.get(Calendar.YEAR);
		int mMonth = calendar.get(Calendar.MONTH) + 1;
		int mDay = calendar.get(Calendar.DAY_OF_MONTH);
		int mHour = calendar.get(Calendar.HOUR);
		int mMin = calendar.get(Calendar.MINUTE);
		int mSec = calendar.get(Calendar.SECOND);
		
		Calendar date = new GregorianCalendar();
		int year = date.get(Calendar.YEAR);  // 2012
		int month = date.get(Calendar.MONTH) + 1;  // 9 - October!!!
		int day = date.get(Calendar.DAY_OF_MONTH);
		
		if(mYear == year && mMonth == month && mDay == day) return sAmpm + " " + String.format("%02d", mHour) + ":" + String.format("%02d", mMin);
		else if(mYear == year && mMonth == month && mDay == day - 1) return "어제";
		else return mYear + "-" + String.format("%02d", mMonth) + "-" + String.format("%02d", mDay);
	}
	
	public static String convertToRelativeType(long millis){
		return convertToRelativeType(Long.toString(millis));
	}
	
	public static String convertToRelativeTypeWithTime(String time){
		Calendar calendar = Calendar.getInstance();
		Date dates = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            dates = simpleDateFormat.parse(time);
        }
        catch (ParseException ex){
            ex.printStackTrace();
        }
		
		calendar.setTime(dates);
		
		return convertToRelativeType(calendar.getTimeInMillis());
	}
	
	public static String convertMillisToDatetime(String millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(millis));

		int mYear = calendar.get(Calendar.YEAR);
		int mMonth = calendar.get(Calendar.MONTH) + 1;
		int mDay = calendar.get(Calendar.DAY_OF_MONTH);
		int mHour = calendar.get(Calendar.HOUR);
		int mMin = calendar.get(Calendar.MINUTE);
		int mSec = calendar.get(Calendar.SECOND);
		
		return mYear + "-" + mMonth + "-" + mDay + " " + mHour + ":" + mMin + ":" + mSec;
	}

	public static String getCntShorYear()
	{
		String longYear = getCntYear();

		return longYear.substring(2, 2);
	}

	public static String getCntYear()
	{
		Calendar cal = Calendar.getInstance();

		return String.valueOf(cal.get(Calendar.YEAR));

	}

	public static int getCntWeekNumber()
	{
		Calendar cal = Calendar.getInstance();

		int iWeek = cal.get(Calendar.DAY_OF_WEEK);

		return iWeek;
	}

	public static String getCntWeek()
	{
		Calendar cal = Calendar.getInstance();

		int iWeek = cal.get(Calendar.DAY_OF_WEEK);

		return weeks[iWeek - 1];

	}

	public static String getWeek(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int iWeek = cal.get(Calendar.DAY_OF_WEEK);

		return weeks[iWeek - 1];

	}

	public static String getString(String date, String format) throws ParseException
	{
		if("".equals(date) && "yyyy".equals(format))
		{
			return getCntYear();
		}
		else if("".equals(date) && "yyyy-MM-dd".equals(format))
		{
			return getCntDate();
		}
		else
		{
			return getString(parseKoreanString(date), format);
		}
	}

	public static Date parseKoreanString(String str) throws ParseException
	{

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = df.parse(str);

		return date;
	}

	/**
	 * 원하는 포멧으로 날짜 출력
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getString(Date date, String format)
	{

		if(date == null)
			return "";
		else
		{
			SimpleDateFormat df = new SimpleDateFormat(format);
			String str = df.format(date);
			return str;
		}
	}

	/**
	 * 원하는 포멧으로 날짜 출력
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getStringEng(Date date, String format)
	{
		if(date == null)
			return "";
		else
		{
			SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
			String str = df.format(date);
			return str;
		}
	}

	// 오늘글은 시간표시
	public static String getStringCompToday(Date date, String format)
	{

		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);

		long currentTime = System.currentTimeMillis();
		String today = df.format(new Date(currentTime));

		if(str.equals(today))
		{
			df = new SimpleDateFormat("HH:mm");
			str = df.format(date);
		}

		return str;
	}

	public static String getWeekString(Date date, String format)
	{
		String str = getString(date, format);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int iWeek = cal.get(Calendar.DAY_OF_WEEK);
		str += " " + weeks[iWeek - 1] + "요일";

		return str;

	}

	public static String getLastDateOfCntMonth()
	{

		String temp = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, 1);

		cal.add(Calendar.DATE, -1);

		temp += df.format(cal.getTime());

		return temp;
	}

	/**
	 * 현재 날짜 기준으로 날짜 계산 후 가지고 오기
	 * 
	 * @param addDate
	 * @param format
	 * @return
	 */
	public static String getDateForBaseCurDate(int addDate, String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, addDate);

		return df.format(cal.getTime());
	}

	public static String getNextDate(String date)
	{
		String temp = "";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		String[] dateArr = date.split("-");

		int year = Integer.parseInt(dateArr[0]);
		int month = Integer.parseInt(dateArr[1]) - 1;
		int day = Integer.parseInt(dateArr[2]);

		cal.set(year, month, day + 1);

		temp += df.format(cal.getTime());

		return temp;
	}

	public static String getCntMonth()
	{

		Calendar cal = Calendar.getInstance();

		String temp = "";

		int month = cal.get(Calendar.MONTH);

		month += 1;

		if(month < 10)
			temp += "0";

		temp += String.valueOf(month);

		return temp;
	}

	public static String getCntDay()
	{
		Calendar cal = Calendar.getInstance();

		String temp = "";

		int day = cal.get(Calendar.DAY_OF_MONTH);

		if(day < 10)
			temp += "0";

		temp += String.valueOf(day);
		return temp;
	}

	public static String getCntHour()
	{
		Calendar cal = Calendar.getInstance();

		return String.valueOf(cal.get(Calendar.HOUR));
	}

	public static String getCntMinute()
	{
		Calendar cal = Calendar.getInstance();

		return String.valueOf(cal.get(Calendar.MINUTE));
	}

	public static String getMillis()
	{
		Calendar cal = Calendar.getInstance();

		return String.valueOf(cal.getTimeInMillis());
	}

	public static String getCntDate()
	{
		return getCntYear() + "-" + getCntMonth() + "-" + getCntDay();
	}

	public static String getFullCntDate()
	{
		return getCntYear() + "년 " + getCntMonth() + "월 " + getCntDay() + "일 " + getCntWeek() + "요일";
	}

	public static String getBoardInsDate()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), 0);
		cal.set(cal.get(Calendar.MONTH), 0);
		cal.set(cal.get(Calendar.DATE), 0);

		String frevDate = df.format(cal.getTime());

		cal.clear();
		DateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		cal.set(cal.get(Calendar.HOUR), 0);
		cal.set(cal.get(Calendar.MINUTE), 0);
		cal.set(cal.get(Calendar.SECOND), 0);
		String tailDate = df2.format(cal.getTime());

		String retVal = "";

		retVal += frevDate + " " + ((String.valueOf(cal.get(Calendar.AM_PM)) == "0") ? "오전" : "오후");
		retVal += " " + tailDate;

		return retVal;
	}

	// 현재 날짜 날짜 포맷 전달받아서 처리
	public static String getFullCntDateByFormat(String foramt) throws ParseException
	{
		DateFormat df = new SimpleDateFormat(foramt);

		Calendar cal = Calendar.getInstance();
		String dateStr = df.format(cal.getTime());

		return dateStr;
	}

	public static String getFullCntDate(String sDate) throws ParseException
	{

		Calendar cal = Calendar.getInstance();

		if(sDate == null)
		{
			sDate = DateUtil.getFullCntDate();
		}
		else
		{

			Date dtDate = DateUtil.parseKoreanString(sDate);
			sDate = DateUtil.getString(dtDate, "yyyy년 MM월 dd일 ");
			cal.setTime(dtDate);
			int iWeek = cal.get(Calendar.DAY_OF_WEEK);
			sDate += weeks[iWeek - 1] + "요일";

		}

		return sDate;

	}

	/**
	 * 일정표에서 사용하는 반복적인 날짜 입력시 사용..
	 * 
	 * @param s
	 * @param type
	 * @param gubn
	 * @param rec
	 * @return
	 * @throws Exception
	 */
	public static String getDateAdd(String s, String type, String gubn, int rec) throws Exception
	{
		String result = "";
		int year = 0, month = 0, day = 0, length = s.length();

		if(s == null || type == null)
			return null;
		if(length != 8)
			return null;

		Calendar cal = Calendar.getInstance();

		try
		{
			year = Integer.parseInt(s.substring(0, 4));
			month = Integer.parseInt(s.substring(4, 6)) - 1; // month 는 Calendar 에서 0 base 으로 작동하므로 1 을 빼준다.
			day = Integer.parseInt(s.substring(6, 8));

			cal.set(year, month, day);
			if(gubn == "year")
				cal.add(Calendar.YEAR, rec);
			if(gubn == "month")
				cal.add(Calendar.MONTH, rec);
			if(gubn == "date")
				cal.add(Calendar.DATE, rec);
			if(gubn == "week")
				cal.add(Calendar.WEEK_OF_MONTH, rec);
			if(gubn == "hour")
				cal.add(Calendar.HOUR_OF_DAY, rec);
			if(gubn == "minute")
				cal.add(Calendar.MINUTE, rec);

			result = (new SimpleDateFormat(type)).format(cal.getTime());
		}
		finally
		{
			cal = null;
		}

		return result;
	}

	public static List<String> getBetweenDate(String sDate, String eDate)
	{
		List<String> list = new ArrayList<String>();

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		try
		{

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			// Date타입으로 변경
			Date d1 = df.parse(sDate);
			Date d2 = df.parse(eDate);

			// Calendar 타입으로 변경 add()메소드로 1일씩 추가해 주기위해 변경
			c1.setTime(d1);
			c2.setTime(d2);

			// 시작날짜와 끝 날짜를 비교해, 시작날짜가 작거나 같은 경우 출력

			while (c1.compareTo(c2) != 1)
			{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				list.add(format.format(c1.getTime()));
				// 출력 System.out.printf("%tF\n",c1.getTime());

				// 시작날짜 + 1 일
				c1.add(Calendar.DATE, 1);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			c1 = null;
			c2 = null;
		}

		return list;
	}

	public static String getQuarter()
	{
		Calendar cal = Calendar.getInstance();

		int month = cal.get(Calendar.MONTH);

		month += 1;

		int quarter = (int) Math.ceil(month / 3.0);

		return String.valueOf(quarter);
	}
	
	public static int getMaxDayOfMonth(String year, String month) {
		int lastDay = 0;
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.valueOf(year), Integer.valueOf(month), 0);
		
		lastDay = cal.getActualMaximum(Calendar.DATE);
		
		return lastDay ;
	}

	/**
	 * 요일 int 구하기
	 * @return
	 */
	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
		
		return day;
	}
}
