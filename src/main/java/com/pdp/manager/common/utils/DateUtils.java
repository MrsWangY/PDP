package com.pdp.manager.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Title: DateUtils
 * @Description:
 * @author: LIXr
 * @version: 1.0
 */
public class DateUtils {

	private static final String FORMAT_YEARANDMONTH = "yyyyMM";
	private static final String FORMAT_DAY_ONLY = "dd";
	
	private static final String FORMAT_YEARANDMONTH1 = "yyyy-MM";
	
	private static final String FORMAT_YEARANDMONTH2 = "yyyy年MM月";
	
	private static final String FORMAT_MONTHANDDAY = "MM月dd日";
	
	private static final String FORMAT_DAY = "yyyy-MM-dd";
	
	private static final String FORMAT_DAY1 = "yyyy年MM月dd日";
	
	private static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	
	private static final String FORMAT_FULL1 = "yyyyMMddHHmmss";
	
	private static final String FORMAT_FULL2 = "yyyy-MM-dd HH:mm";
	
	private static final String FORMAT_FULL3 = "HH:mm";
	private static final String FORMAT_FULL4 = "HH:mm:ss";
	
	
	public static Date getDate() throws Exception{
		return new Date();
	}
	
	/**
	 * 将时间字符串转换成时间对象
	 * @param dateStr	时间字符串
	 * @return	Date时间对象
	 */
	public static Date stringToDate(String dateStr) throws Exception{
		DateFormat df = null;
		try{
			dateStr = dateStr.trim();
			if(dateStr.length()==10){
				df = new SimpleDateFormat(FORMAT_DAY);
			}else if(dateStr.length()==19){
				df = new SimpleDateFormat(FORMAT_FULL);
			}
		}catch(Exception e){
		}
		return df==null?null:df.parse(dateStr);
	}
	
	/**
	 * 将时间字符串转换成时间对象
	 * @param dateStr	时间字符串
	 * @return	Date时间对象
	 */
	public static String stringToDate1(String dateStr) throws Exception{
		String date = null;
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_DAY);
			Date d = df==null?null:df.parse(dateStr);
			DateFormat df1 = new SimpleDateFormat(FORMAT_DAY1);
			date = df1.format(d);
		}catch(Exception e){
		}
		return date;
	}
	
	/**
	 * 将时间字符串转换成时间对象
	 * @param dateStr	时间字符串
	 * @return	Date时间对象
	 */
	public static String stringToMonth(String dateStr) throws Exception{
		String date = null;
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_YEARANDMONTH1);
			Date d = df==null?null:df.parse(dateStr);
			DateFormat df1 = new SimpleDateFormat(FORMAT_YEARANDMONTH2);
			date = df1.format(d);
		}catch(Exception e){
		}
		return date;
	}
	
	/**
	 * 将时间字符串转换成时间对象
	 * @param dateStr	时间字符串
	 * @return	Date时间对象
	 */
	public static String stringToMonthDay(Date d) throws Exception{
		String date = null;
		try{
			DateFormat df1 = new SimpleDateFormat(FORMAT_MONTHANDDAY);
			date = df1.format(d);
		}catch(Exception e){
		}
		return date;
	}
	
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToString(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_FULL);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	
	public static String dateToString3(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_FULL3);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	
	public static String dateToString4(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_FULL4);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToString2(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_FULL2);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToString1(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_FULL1);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToDateString(Date date){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_DAY);;
			if(date!=null){
				dateStr = df.format(date);
			}
		}catch(Exception e){
			
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToDateString2(Date date){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_DAY1);;
			if(date!=null){
				dateStr = df.format(date);
			}
		}catch(Exception e){
			
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToDateString1(Date date){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_DAY);;
			if(date!=null){
				dateStr = df.format(date);
			}else{
				dateStr = df.format(new Date());
			}
		}catch(Exception e){
			
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成yyyyMM
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String getYearAndMonth(){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_YEARANDMONTH);
			dateStr = df.format(new Date());
		}catch(Exception e){
		}
		return dateStr;
	}
	
	/**
	 * 将时间对象转换成yyyyMM
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String getDayOnly(){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(FORMAT_DAY_ONLY);
			dateStr = df.format(new Date());
		}catch(Exception e){
		}
		return dateStr;
	}
	
	public static List<String> getBetweenDate(String sDate,String eDate){
		
		try {
			List<String> dL = new ArrayList<String>();
			//时间段
			Date dBegin =stringToDate(sDate);
			Date dEnd =  stringToDate(eDate);
			Long startTIme = dBegin.getTime();  
			Long endTime = dEnd.getTime();  
			
			Long oneDay = 1000 * 60 * 60 * 24l;  
			
			Long time = startTIme; 
			Date d = null;
			
			while (time <= endTime) {  
				
				d = new Date(time);  
				
				dL.add(dateToDateString(d));
			   
			    time += oneDay;  
			}
			return dL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ArrayList<String>();
	}
	
    /**
     *
     * 功能描述:
     *
     * @param: 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return:
     * @author: LIXr
     */
    public static String getCurrentDate(){
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        String date = df.format(System.currentTimeMillis());
        return date;
    }


    /**
     *
     * 功能描述: 
     *
     * @param: date类 获取当前系统时间 yyyy-MM-dd HH:mm:ss
     * @return: 
     * @author: LIXr
     */
    public static Date getCurrentDateToDate () {
        DateFormat df = new SimpleDateFormat( FORMAT_FULL );
        String date = df.format(System.currentTimeMillis());
        Date d = null;
        try {
            d = df.parse( date.toString( ) );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
        return d;
    }

    public static Date getDateToFullDate (Date dt) {
    	if(dt == null) return null;
        DateFormat df = new SimpleDateFormat( FORMAT_FULL );
        String date = df.format(dt);
        Date d = null;
        try {
            d = df.parse( date.toString( ) );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
        return d;
    }
    public static Date getDateToDate (Date dt) {
    	if(dt == null) return null;
        DateFormat df = new SimpleDateFormat( FORMAT_DAY );
        String date = df.format(dt);
        Date d = null;
        try {
            d = df.parse( date.toString( ) );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
        return d;
    }
    
    /**
     * 增加时间单位：天
     * @param day
     * @return
     */
    public static String getCurrentAddDay(int day) {
        SimpleDateFormat sdf = new SimpleDateFormat( FORMAT_FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, day);
        return sdf.format(cal.getTime());
    }

    /**
     * 增加时间单位：分钟
     * @param minute
     * @return
     */
    public static String getCurrentAddMin(int minute) {
        SimpleDateFormat sdf = new SimpleDateFormat( FORMAT_FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, minute);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getNowDateString (  ) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat( FORMAT_DAY);
        return sdf.format( d );
    }

    /**
     * 把Date转为String
     * @param date
     * @param format
     * @return
     */
    public static String getFormatTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 增加时间单位：天
     * @param day
     * @return
     */
    public static Date addDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 增加时间单位：天
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    /**
     * 减去多少天
     * @param date
     * @param day
     * @return
     */
    public static Date minusDay(Date date, int day) {
        return addDay(date, -day);
    }

    public static void main(String[] args) {
        System.out.println(getCurrentAddDay(2));
    }
}
