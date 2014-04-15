package com.slug.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class CalendarUtil
{
    public static String getCurrentTimeNoDash14()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }

    public static String getCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }

    public static String getCurrentTimeNoDash()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }

    public static String getCurrentTimeNoDash16()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate.substring(0,16);
    }

    public static String getCurrentTimeNoDash16(Date dt)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curDate = formatter.format(dt);
        return curDate.substring(0,16);
    }
    public static String getCurrentTimeNoDash14(Date dt)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String curDate = formatter.format(dt);
        return curDate.substring(0,14);
    }

    public static String getKSTDateTime()
    {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return simpledateformat.format(date);
    }

    public static String getCurrentDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }

    public static String getDateMMDDHHMISS()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }

    public static String getDateYYYYMMDD()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }
    public static String getDateMMDDHHMI()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }
    /**
     * 1.���: �����췯���� ��� (���۽ð�)
     *   - �ú��� �Է¹޾Ƽ� ������ ��� �� ��,�и�������(0000)�� �߰��Ѵ�.
     */
    public static String setStartTime(String hhmm) throws Exception
    {

        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String rtTime = simpledateformat.format(date) + hhmm + "0000";
        return rtTime;
    }

     /**
     * 1.���: �����췯���� ��� (����ð�)
     *   - �ú��� �Է¹޾Ƽ� ������ ��� �� ��,�и�������(0000)�� �߰��Ѵ�.
     */
    public static String setEndTime(String hhmm) throws Exception
    {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpletimeformat = new SimpleDateFormat("HHmm");
        Date date = new Date();

        String systime = simpletimeformat.format(date);

        //hhmm�� ����ð����� Ŭ��� ���糯¥��
        if ( StringUtil.stoi(hhmm) > StringUtil.stoi(systime)){
             hhmm = simpledateformat.format(date) + hhmm + "0000";
        }
        //hhmm�� ����ð����� ũ��������� ������¥��
        else{
             hhmm = getDatewithSpan(simpledateformat.format(date),1) + hhmm + "0000";
        }
        return hhmm;
    }


    public static String getDatewithSpan(String s, int i)
    {
        int j = 0x36ee80;
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        SimpleTimeZone simpletimezone = new SimpleTimeZone(9 * j, "KST");
        simpledateformat.setTimeZone(simpletimezone);
        int k = Integer.valueOf(s.substring(0, 4)).intValue();
        int l = Integer.valueOf(s.substring(4, 6)).intValue() - 1;
        int i1 = Integer.valueOf(s.substring(6, 8)).intValue() + i;
        Calendar calendar = Calendar.getInstance();
        calendar.set(k, l, i1);
        return simpledateformat.format(calendar.getTime());
    }



}
