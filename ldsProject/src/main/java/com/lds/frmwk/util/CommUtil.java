package com.lds.frmwk.util;

import java.io.InputStream;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommUtil.class);

	/* ?˜„?ž¬ ?‹œê°? yyyyMMddHHmmss */
	public static String getCurrentTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;

	}
	
	/* ?˜„?ž¬ ?‹œê°? yyyy-MM-dd HH:mm:ss */
	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	/* ?‹œê°?  yyyy-MM-dd HH:mm:ss */
	public static String getDay(String date, String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Calendar cal = Calendar.getInstance();
		Date sdate = null;
		String strDate = null;
		try {
			sdate = sdfDate.parse(date);
			cal.setTime(sdate);
			strDate = new java.text.SimpleDateFormat(format).format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/* ?˜„?ž¬ ?‹œê°?  format */
	public static String getCurrentDay(String format) {
		SimpleDateFormat sdfDate = new SimpleDateFormat(format);// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	/* ì¶”ê??‚ ì§?  format */
	public static String getDay(String format, int bday) {

		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, bday);
		String strDate = new java.text.SimpleDateFormat(format).format(day.getTime());
		return strDate;
	}
	
	/* ?–´? œ?‚ ì§? -1  */
	public static String getYesterDay() {

		//SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -1);
		String strDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(day.getTime());
		strDate = strDate.substring(0, 10) + " 23:59:59";
		return strDate;
	}

	/* ?–´? œ?‚ ì§? -1  format */
	public static String getYesterDay(String format) {

		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -1);
		String strDate = new java.text.SimpleDateFormat(format).format(day.getTime());
		return strDate;
	}

	/*  */
	public static String spaceDel(String str){
		str = str.replaceAll(" ", "_");
		str = str.replaceAll(":", ".");
		return str;
	}

	/*  */
	public static String getCurrentStartDate() {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		strDate = strDate.substring(0, 10) + " 00:00:00";
		return strDate;
	}

	/*  */
	public static String getCurrentBeforeDate(int date) {

		//SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, date);
		String strDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(day.getTime());
		strDate = strDate.substring(0, 10) + " 00:00:00";
		return strDate;
	}

	/*  */
	public static String getCurrentMinusSecond(String curDate, int sec) {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Calendar cal = Calendar.getInstance();
		Date sdate = null;
		String strDate = null;

		try {
			sdate = sdfDate.parse(curDate);
			//logger.debug("date : " + curDate);
			//logger.debug("sdate : " + sdate);
			cal.setTime(sdate);
			cal.add(Calendar.SECOND, -sec);
			strDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			//logger.debug("getCurrentMinusSecond strDate: " + strDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/*  */
	public static String getCurrentAddSecond(String date, int sec) {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Calendar cal = Calendar.getInstance();
		Date sdate = null;
		String strDate = null;
		try {

			sdate = sdfDate.parse(date);
			//logger.debug("date : " + date);
			//logger.debug("sdate : " + sdate);
			cal.setTime(sdate);
			cal.add(Calendar.SECOND, sec);
			strDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			//logger.debug("strDate : " + strDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/*  */
	public static String getCurrentAddMinute(String date, int min) {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Calendar cal = Calendar.getInstance();
		Date sdate = null;
		String strDate = null;

		try {

			sdate = sdfDate.parse(date);
			//logger.debug("date : " + date);
			//logger.debug("sdate : " + sdate);
			cal.setTime(sdate);
			cal.add(Calendar.MINUTE, min);
			strDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			//logger.debug("strDate : " + strDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}

	/*  */
	public static int dateCompare(String date, String yesterday) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date day1 = null;
		Date day2 = null;

		int compare = 0;

		try {

			day1 = format.parse(date);
			day2 = format.parse(yesterday);
			compare = day1.compareTo(day2);

		} catch (Exception e) {

		}
		return compare;
	}

	/*  */
	public static String calcDate(String str, String addMin){

		String sDay = str;
		String rDay = str;

		Calendar sCal = getCalendar(sDay);
		Date eDay = new Date(sCal.getTimeInMillis() + 1000 * 60 * Integer.parseInt(addMin));

		DateFormat formatter;
		formatter = new SimpleDateFormat("yyyyMMddHHmmss");

		rDay = formatter.format(eDay);
		return rDay;
	}

	/*  */
	private static Calendar getCalendar(String str){
		// parameter str = YYYYMMDDHH24MISS
		Calendar cal = Calendar.getInstance();
		str = str.replaceAll("-", "");
		str = str.replaceAll(" ", "");
		str = str.replaceAll(":", "");
		cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)) - 1,
				Integer.parseInt(str.substring(6, 8)), Integer.parseInt(str.substring(8, 10)),
				Integer.parseInt(str.substring(10, 12)), Integer.parseInt(str.substring(12, 14)));
		return cal;
	}

	/*  */
	public static String NullToStr(String str){
		return (str == null ? "" : str);
	}

	/*  */
	public static String nvl(String str, String value){

		if (str == null || str.length() == 0)
			return value;
		else
			return str;
	}

	/*  */
	public static String NullToStr(Object obj){
		return (obj == null ? "" : obj.toString());
	}

	/*  */
	public static String NullToStr(byte[] arrObj){

		try {
			return (arrObj == null ? "" : new String(arrObj, "MS949"));
		}
		catch (Exception e) {
			return "";
		}

	}

	/*  */
	public static String NullToZero(String str){
		return (str == null ? "0" : str);
	}

	/*  */
	public static String NullToBlank(String str){
		return (str == null || str.length() == 0 ? "&nbsp;" : str);
	}

	/*  */
	public static Object NullToObj(InputStream is){

		Object obj = null;
		StringBuffer buf = new StringBuffer();

		try {
			if (is == null)
				buf.append(' ');
			else
			{
				int c;
				while ((c = is.read()) != -1)
					buf.append((char) c);
			}
		} catch (Exception e){
			;
		}

		obj = buf;
		return obj;

	}

	/*  */
	public static String characterStreamToString(Reader rd){

		char[] buffer = new char[1024];
		int length = 0;
		String str = "";

		try {
			while ((length = rd.read(buffer)) != -1)
			{
				for (int j = 0; j < length; j++) {
					str = str + buffer[j];
				}
			}
		}
		catch (Exception e)
		{
		}
		return str;
	}
}