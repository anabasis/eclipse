package com.slug.util;

// Source File Name:   cmnUtil.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cmnUtil

{

    /**
     * 전문내역을 화면에 보여주기 위하여 날자 형식의 양식을 만들어준다.
     * 
     * @param String
     *            date
     * @return String sReturn
     * @throws GeneralException
     */
    public static String makeStrToDate(String date){
        String sReturn = "";
        if(date == null || date.equals("")){
            return sReturn;
        }else{
            sReturn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                    .valueOf(date.substring(0,4))))).append("/").append(date.substring(4,6))
                    .append("/").append(date.substring(6,8))));
            return sReturn;
        }
    }

    /**
     * 전문내역을 화면에 보여주기 위하여 날자 형식의 양식을 만들어준다.
     * 
     * @param String
     *            date
     * @return String sReturn
     * @throws GeneralException
     */
    public static String makeStrToDateTime(String date){
        String sReturn = "";
        if(date == null || date.equals("")){
            return sReturn;
        }else{
            sReturn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                    .valueOf(date.substring(0,4))))).append("/").append(date.substring(4,6))
                    .append("/").append(date.substring(6,8)).append(":").append(
                            date.substring(8,10)).append(":").append(date.substring(10,12)).append(
                            ":").append(date.substring(12,14))));
            return sReturn;
        }
    }

    public static String makeStrToRbNo(String RgNo){
        String sReturn = "";
        if(RgNo == null || RgNo.equals("")) return sReturn;
        if(RgNo.length() == 10)
            sReturn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                    .valueOf(RgNo.substring(0,3))))).append("-").append(RgNo.substring(3,5))
                    .append("-").append(RgNo.substring(5,10))));
        else if(RgNo.substring(0,3).equals("999") && RgNo.length() == 13)
            sReturn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                    .valueOf(RgNo.substring(3,6))))).append("-").append(RgNo.substring(6,8))
                    .append("-").append(RgNo.substring(8,13))));
        else if(RgNo.length() == 13)
            sReturn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                    .valueOf(RgNo.substring(0,6))))).append("-").append(RgNo.substring(6,13))));
        return sReturn;
    }

    public static String makeTelephoneNo(String teleNo){

        String sRtn = "";
        if(teleNo == null || teleNo.equals("")) return sRtn;

        if(teleNo.substring(0,2).equals("02")){
            if(teleNo.length() == 9)
                sRtn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                        .valueOf(teleNo.substring(0,2))))).append("-")
                        .append(teleNo.substring(2,5)).append("-").append(teleNo.substring(5,9))));
            else if(teleNo.length() == 10)
                sRtn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                        .valueOf(teleNo.substring(0,2))))).append("-")
                        .append(teleNo.substring(2,6)).append("-").append(teleNo.substring(6,10))));
            else sRtn = teleNo;
        }else{
            if(teleNo.length() == 10)
                sRtn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                        .valueOf(teleNo.substring(0,3))))).append("-")
                        .append(teleNo.substring(3,6)).append("-").append(teleNo.substring(6,10))));
            else if(teleNo.length() == 11)
                sRtn = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String
                        .valueOf(teleNo.substring(0,3))))).append("-")
                        .append(teleNo.substring(3,7)).append("-").append(teleNo.substring(7,11))));
            else sRtn = teleNo;

        }

        return sRtn;

    }

    public static String replace(String source, String patt, String rep)

    {

        int pos = source.lastIndexOf(patt);

        if(pos != -1)

        {

            return source.substring(0,pos) + rep + source.substring(pos + patt.length());

        }else return source;

    }

    protected PrintWriter out      = null;

    // /하여 String반환

    // multiply : * 값,0이면 /만

    // underNum : 소수점 아래값

    public String         savePath = "/tmp";

    // /하여 String반환

    // multiply : * 값,0이면 /만

    // underNum : 소수점 아래값

    public cmnUtil(){
    }

    // /하여 String반환

    // multiply : * 값,0이면 /만

    // underNum : 소수점 아래값

    public String appendHtmlBr(String comment)

    {

        int length = comment.length();

        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < length; ++i)

        {

            String comp = comment.substring(i,i + 1);

            if("\r".compareTo(comp) == 0)

            {

                comp = comment.substring(++i,i + 1);

                if("\n".compareTo(comp) == 0)

                    buffer.append("<BR>\r");

                else

                buffer.append("\r");

            }

            buffer.append(comp);

        }

        return buffer.toString();

    }

    // 개행문자대신 <br> 붙이기

    public String changeLine(String Data, int reqtColumn)

    {

        String newStr = "";

        int len = Data.length();

        try

        {

            if(Data == null)

            {

                return setSpace(reqtColumn);

            }

            if(len > reqtColumn)

            {// data길이가 요구 길이보다 클때

                if(Data.toLowerCase().indexOf("<br>\r") == -1)

                {// <br>\r이 없을때

                    int cursor = 0;

                    int engCount = 0;

                    for(int i = 0; i < len; i++)

                    {

                        String addStr = Data.substring(i,i + 1);

                        // 원하는 길이가 됐을때

                        if(cursor == reqtColumn)

                        {

                            addStr += "<br>\r";

                            cursor = 0;

                        }

                        cursor++; // 위에 쓰면 첫라인은 1개가 들찍힘

                        // 30개로 끊을때 한글은 30개,영문은 60개로 끊기위해

                        char c = addStr.charAt(0);

                        if((int)Character.toUpperCase(c) >= 65
                                && (int)Character.toUpperCase(c) <= 90)

                        {

                            engCount++;// 영문2개를 한개로 잡기위해

                            if(engCount == 2)

                            {

                                cursor--;

                                engCount = 0;

                            }

                        }

                        newStr += addStr;

                    }

                }

                else

                {// <br>\r이 있을때

                    int cursor = 0;

                    int engCount = 0;

                    String currStr = "";

                    for(int i = 0; i < len; i++)

                    {

                        if(len - i > 5)
                            currStr = Data.substring(i,i + 5);

                        else currStr = Data.substring(i,len);

                        String addStr = "";

                        if(currStr.toLowerCase().equals("<br>\r"))

                        {// <br>이 있을때

                            addStr = currStr;

                            i += 4;

                            cursor = 0;// cursor도중에 <br>\r있으면 0으로 초기화

                        }

                        else

                        {// <br>이 없을때

                            addStr = Data.substring(i,i + 1);

                            // 원하는 길이가 됐을때

                            if(cursor == reqtColumn)

                            {

                                addStr += "<br>\r";

                                cursor = 0;

                            }

                            cursor++; // 위에 쓰면 첫라인은 1개가 들찍힘

                            // 30개로 끊을때 한글은 30개,영문은 60개로 끊기위해

                            char c = addStr.charAt(0);

                            if((int)Character.toUpperCase(c) >= 65
                                    && (int)Character.toUpperCase(c) <= 90)

                            {

                                engCount++;// 영문2개를 한개로 잡기위해

                                if(engCount == 2)

                                {

                                    cursor--;

                                    engCount = 0;

                                }

                            }

                        }// - br이 있고 없을때

                        newStr += addStr;

                    }// for

                } // br이 있고 없을때

            }

            else

            {// data길이가 요구 길이보다 작을때

                newStr = Data;

            }

        }

        catch(java.lang.Exception ex)

        {

            return "changeline error :" + ex.getMessage();

        }

        return newStr;

    }

    // reqtColumn은 한글기준 (30이면 영문은 60개

    // \r \n: 길이 1 ,<br>길이 4

    // 원하는 길이에서 <br>을 추가해 라인 바꾸기

    public String checkBlank2Nbsp(String string)

    {

        if(string.length() == 0 || string.equals(""))

        {

            return "&nbsp;";

        }

        else

        {

            return string;

        }

    }

    // blank Check하여 &nbsp; 반환

    public String checkNull2Blank(String nullString)

    {

        if(nullString == null || nullString.equals("null"))

        {

            return "";

        }

        else

        {

            return nullString;

        }

    }

    // NullCheck하여 공백 반환

    public String checkNull2Dash(String nullString)

    {

        if(nullString == null || nullString.trim().equals("null"))

        {

            return "-";

        }

        else

        {

            return nullString.trim();

        }

    }

    // NullCheck하여 - 반환

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // Null Check하여 &nbsp; 반환
    public String checkNull2Space(String nullString)

    {

        if(nullString == null || nullString.equals("null"))

        {

            return "&nbsp;";

        }

        else

        {

            return nullString;

        }

    }

    public String checkNull2Value(String nullString, String value)

    {

        if(nullString == null || nullString.equals("null") || nullString == "")

        {

            return value;

        }

        else

        {

            return nullString;

        }

    }

    // String 배열을 입력받아 NullCheck하여 원하는 값 반환

    public String checkNull2Value(String[] nullArray, int i, String value)

    {

        if(nullArray == null)

        {

            return value;

        }

        else if(nullArray[i] == null)

        {

            return value;

        }

        else

        {

            return nullArray[i];

        }

    }

    // NullCheck하여 원하는 값 반환

    public String checkNull2Zero(String nullString)

    {

        if(nullString == null || nullString.equals("null"))

        {

            return "0";

        }

        else

        {

            return nullString;

        }

    }

    // NullCheck하여 "0" 반환

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // null이나 빈공백시 0로 setting
    public String checkReturnZero(String str)

    {

        if(str == null)
            str = "0";

        else if(str.trim().equals("") || str.trim().length() < 1) str = "0";

        return str;

    }

    public String checkString2Value(String orgStr, String compareStr, String rtnStr){
        if(orgStr.equals(compareStr))
            return rtnStr;
        else return orgStr;
    }

    public String conditionToTextArea(String data)

    {

        // Html textarea에서 enter치면 \r\n

        if(data == null || data.equals("")) return "";

        String find_str = "\r\n";

        String replace_str = "','";

        int pos = data.indexOf(find_str);

        if(pos == -1)

        {

            data = "('" + data + "')";

        }else

        {

            data = "('" + replaceStringAll2(data,find_str,replace_str) + "')";

        }

        return data;

    }

    // 파일지우기

    // Html Textarea에서 오는 값에 Sql의 in문에 쓸수 있도록 정의
    // _find_str : "\r\n" ,"_"
    public String conditionToTextArea(String data, String _find_str)

    {

        // Html textarea에서 enter치면 \r\n

        if(data == null || data.equals("")) return "";

        String find_str = _find_str; // "\r\n";

        String replace_str = "','";

        int pos = data.indexOf(find_str);

        if(pos == -1)

        {

            data = "('" + data + "')";

        }else

        {

            data = "('" + replaceStringAll2(data,find_str,replace_str) + "')";

        }

        return data;

    }

    // 파일지우기

    // /하여 String반환
    // multiply : * 값,0이면 /만
    // underNum : 소수점 아래값
    public HttpServletResponse Convert(HttpServletResponse res){
        res.setContentType("application/msexcel; charset=EUC_KR");
        return res;
    }

    // 파일지우기

    public void Convert2(HttpServletResponse res){
        res.setContentType("application/msexcel; charset=EUC_KR");
    }

    // 파일이름 실제로 바꾸기

    public String deleteFile(String file)

    {

        return deleteFile(savePath,file);

    }

    // 파일이름 바꾸기

    public String deleteFile(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        return runDeleteFile(path_file);

    }

    // 파일이름 바꾸기

    public String division(double above, double down, long multiply, int underNum)

    {

        double d = 0.0D;

        try

        {

            if(multiply == 0)

                d = above / down;

            else

            d = above / down * multiply;

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        return formatNum(d,underNum);

    }

    public String division(int above, int down, long multiply, int underNum)

    {

        double d = 0.0D;

        try

        {

            if(multiply == 0)

                d = above / down;

            else

            d = above / down * multiply;

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        return formatNum(d,underNum);

    }

    // 확장자 반환

    public String division(long above, long down, long multiply, int underNum)

    {

        double d = 0.0D;

        try

        {

            if(multiply == 0)

                d = above / down;

            else

            d = above / down * multiply;

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        return formatNum(d,underNum);

    }

    // 원하는 문자로 원하는 갯수(totalLength-data크기)만큼 채워준다

    // 반환 크기 data length

    public String division(String aboveStr, String downStr, long multiply, int underNum)

    {

        double above = 0.0D;

        double down = 0.0D;

        try

        {

            above = Double.valueOf(aboveStr).doubleValue();

        }

        catch(Exception _ex)

        {

            above = 0.0D;

        }

        try

        {

            down = Double.valueOf(downStr).doubleValue();

        }

        catch(Exception _ex)

        {

            down = 0.0D;

        }

        return division(above,down,multiply,underNum);

    }

    // 원하는 문자로 원하는 갯수(totalLength)만큼 채워준다

    // 반환 크기 data length +totalLength

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // 확장자에 따른 이미지 뿌리기
    public String fileImage(String attc_file, String imgPath)

    {

        // 확장자

        String file_type = fileType(attc_file).toLowerCase();

        String file_name = "";

        String file_image = "";

        if(attc_file == null) return "";

        if(attc_file.length() > 15)
            file_name = attc_file.substring(15);

        else file_name = attc_file;

        if(file_type.equals("exe"))

            file_image = "<img src=\"" + imgPath + "exe.gif\" border=0>" + file_name;

        else if(file_type.equals("doc"))

            file_image = "<img src=\"" + imgPath + "doc.gif\" border=0>" + file_name;

        else if(file_type.equals("htm") || file_type.equals("html"))

            file_image = "<img src=\"" + imgPath + "html.gif\" border=0>" + file_name;

        else if(file_type.equals("ppt"))

            file_image = "<img src=\"" + imgPath + "ppt.gif\" border=0>" + file_name;

        else if(file_type.equals("zip") || file_type.equals("arj") || file_type.equals("rar"))

            file_image = "<img src=\"" + imgPath + "press.gif\" border=0>" + file_name;

        else if(file_type.equals("hwp"))

            file_image = "<img src=\"" + imgPath + "hwp.gif\" border=0>" + file_name;

        else if(file_type.equals("txt"))

            file_image = "<img src=\"" + imgPath + "txt.gif\" border=0>" + file_name;

        else if(file_type.equals("null") || file_type.equals(""))

            file_image = "";

        else

        file_image = "<img src=\"" + imgPath + "unkn.gif\" border=0>" + file_name;

        return file_image;

    }

    public String fileType(String file)

    {

        String fileType = "";

        if(file == null || file.trim().equals("") || file.length() < 1) return "";

        int point = file.lastIndexOf('.');

        fileType = file.substring(point + 1);

        return fileType;

    }

    public String fillChar2String(String str, int totalLength, String fillChar, String align)

    {

        if(str == null)

        str = "";

        String strData = "";

        int CheckNum = totalLength - str.length();

        for(int i = 0; i < CheckNum; i++)

            strData += fillChar;

        if(align.toUpperCase().equals("RIGHT"))

            strData = str + strData;

        else

        strData = strData + str;

        return strData;

    }

    // 좌측정렬 : 문자열+원하는 스페이스크기

    // 오른쪽 정렬 : 원하는 스페이스크기 + 문자열

    // 중간 정렬 : 원하는 스페이스크기/2 + 문자열 + 원하는 스페이스크기/2

    // 반환 크기 SpaceNum

    public String fillChar2StringSumSize(String str, int totalLength, String data, String align)

    {

        if(str == null) str = "";

        String strData = "";

        for(int i = 0; i < totalLength; i++)

            strData += data;

        if(align.toUpperCase().equals("RIGHT"))

            strData = str + strData;

        else

        strData = strData + str;

        return strData;

    }

    // 반올림

    public String fillSpace2String(String Data, int SpaceNum, String Align)

    {

        String LeftSpace = "";

        String RightSpace = "";

        String ReturnValue = "";

        int CheckNum = 0;

        try

        {

            if(Data == null)

            {

                return setSpace(SpaceNum);

            }

            // 가져온 데이터가 보여주고자하는 길이보다 클 경우 보여주고자하는 길이만큼 잘라준다.

            if(toCode(Data).length() > SpaceNum)

            {

                // 잘리는 부분에 한글이 들어가면 그 컬럼 전체가 빠짐

                // 그래서 한글일 경우 그 전에서 자름

                if(Data.length() == toCode(Data).length())

                {

                    Data = Data.substring(0,SpaceNum);

                }

                else

                {

                    if(toHangul(toCode(Data).substring(SpaceNum - 1,SpaceNum + 1)).equals(

                    toCode(Data).substring(SpaceNum - 1,SpaceNum + 1))

                    )

                    {

                        Data = toCode(Data).substring(0,SpaceNum - 1) + " ";

                    }

                    else

                    {

                        Data = toCode(Data).substring(0,SpaceNum);

                    }

                    Data = toHangul(Data);

                }

            }

            else

            {

                CheckNum = SpaceNum - toCode(Data).length();

            }

            int LeftCheckNum = CheckNum / 2;

            int RightCheckNum = CheckNum - LeftCheckNum;

            for(int i = 0; i < LeftCheckNum; i++)

            {

                LeftSpace += " ";

            }

            for(int i = 0; i < RightCheckNum; i++)

            {

                RightSpace += " ";

            }

            // 왼쪽으로 정렬

            if(Align.toUpperCase().equals("LEFT"))

            {

                ReturnValue = Data + LeftSpace + RightSpace;

            }

            // 오른쪽으로 정렬

            else if(Align.toUpperCase().equals("RIGHT"))

            {

                ReturnValue = LeftSpace + RightSpace + Data;

            }

            // 가운데로 정렬

            else if(Align.toUpperCase().equals("CENTER"))

            {

                ReturnValue = LeftSpace + Data + RightSpace;

            }

        }

        catch(java.lang.Exception ex)

        {

            ReturnValue = Data + ":" + ex.getMessage();

        }

        return ReturnValue;

    }

    // 반올림

    public String formatNum(double num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // 반올림

    public String formatNum(int num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // 반올림

    public String formatNum(long num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // format반환

    public String formatNum(String num, int underNum)

    {

        double d = 0.0D;

        try

        {

            d = Double.valueOf(num).doubleValue();

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        return formatNum(d,underNum);

    }

    public String formatReturn(int underNum)

    {

        String formats = "";

        if(underNum == 0)
            formats = "###,###,###,##0";

        else formats = "###,###,###,##0." + setChar(underNum,"0");// 0의 갯수만큼
                                                                    // 채운다 ,#:0을
                                                                    // 한번만

        return formats;

    }

    // String 년월일 8자리or10자리 입력해 월일에 가감하여 년월일8자리 출력

    // 현재월에서 입력한 개월 수 이전의 마지막 날짜 구하기
    public String getBeforeMonthK(int mm){
        GregorianCalendar cal = new GregorianCalendar();

        int beforeYear = cal.get(Calendar.YEAR);
        int beforeMonth = (cal.get(Calendar.MONTH) + 1) - mm;

        if(beforeMonth < 1){
            beforeYear = cal.get(Calendar.YEAR) - 1;
            beforeMonth = 12 + ((cal.get(Calendar.MONTH) + 1) - 8);
        }

        return toLen(beforeYear,4) + "년 " + beforeMonth + "월";

    }

    // 년월일입력해 일에 가감하여 년월일 출력

    // 현재월에서 입력한 개월 수 이전의 마지막 날짜 구하기
    public String getBeforeMonthLastDay(int mm){
        GregorianCalendar cal = new GregorianCalendar();

        int beforeYear = cal.get(Calendar.YEAR);
        int beforeMonth = (cal.get(Calendar.MONTH) + 1) - mm;

        if(beforeMonth < 1){
            beforeYear = cal.get(Calendar.YEAR) - 1;
            beforeMonth = 12 + ((cal.get(Calendar.MONTH) + 1) - 8);
        }
        int endDate = getEndOfMonthDay(beforeYear,beforeMonth);

        return toLen(beforeYear,4) + "." + toLen(beforeMonth,2) + "." + toLen(endDate,2);

    }

    // String 년월일 8자리or10자리 입력해 일에 가감하여 년월일8자리 출력

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // 년월일입력해 월일에 가감하여 년월일 출력
    public String getChangeBoth(int year, int month, int day, int addmonth, int addday)

    {

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1 + addmonth,day + addday);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    /*
     * 월만 변경을 시켜야 할 경우 아래의 내용은 명확한 차이가 있다
     * 
     * 즉 현재 날짜가 2000년 12월 30일인 경우
     * 
     * getChangeMonth(getCurrDate(),2) -> 2001년 3월 2일
     * 
     * getChangeMonth(getYYYYMM()+"01",2) -> 2001년 2월 1일
     * 
     */

    public String getChangeBoth(String date, int addmonth, int addday)

    {

        GregorianCalendar cal = new GregorianCalendar();

        int year = 0;

        int month = 0;

        int day = 0;

        if(date.length() < 8) return "";

        int pointDash = date.lastIndexOf("-");

        int pointSlash = date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(5,7)).intValue();

            day = Integer.valueOf(date.substring(8,10)).intValue();

        }

        cal.set(year,month - 1 + addmonth,day + addday);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    public String getChangeDay(int year, int month, int day, int addday)

    {

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1,day + addday);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    // YYYYMMDD 20000427 반환

    public String getChangeDay(String date, int addday)

    {

        GregorianCalendar cal = new GregorianCalendar();

        int year = 0;

        int month = 0;

        int day = 0;

        if(date.length() < 8) return "";

        int pointDash = date.lastIndexOf("-");

        int pointSlash = date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(5,7)).intValue();

            day = Integer.valueOf(date.substring(8,10)).intValue();

        }

        cal.set(year,month - 1,day + addday);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    // YYYYMMDDHHmm 200004272109 반환

    // 년월일입력해 월에 가감하여 년월일 출력
    public String getChangeMonth(int year, int month, int day, int addmonth)

    {

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1 + addmonth,day);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    // YYYYMMDDHHmm 2000년04월27일21시09분 반환

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // String 년월일 8자리or10자리 입력해 월에 가감하여 년월일8자리 출력
    public String getChangeMonth(String date, int addmonth)

    {

        GregorianCalendar cal = new GregorianCalendar();

        int year = 0;

        int month = 0;

        int day = 0;

        if(date.length() < 8) return "";

        int pointDash = date.lastIndexOf("-");

        int pointSlash = date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12입력

            // 년 ,월 ,일

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(5,7)).intValue();

            day = Integer.valueOf(date.substring(8,10)).intValue();

        }

        cal.set(year,month - 1 + addmonth,day);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }
    /***
     * 입력 일자의 차이
     * @param args
     */
    public long getDateFromTo(String from, String to)  
    {  
      
	    // Creates two calendars instances  
	      
	    Calendar cal1 = Calendar.getInstance();  
	      
	    Calendar cal2 = Calendar.getInstance();  
	      
	    int from_y = string2Int(from.substring(0,4));
	    int from_m = string2Int(from.substring(5, 6));
	    int from_d = string2Int(from.substring(7, 8));

	    int to_y = string2Int(to.substring(0,4));
	    int to_m = string2Int(to.substring(5, 6));
	    int to_d = string2Int(to.substring(7, 8));

	    // Set the date for both of the calendar instance  
	      
	    cal1.set(from_y, from_m, from_d);  
	      
	    cal2.set(to_y, to_m, to_d);  
	      
	       
	      
	    // Get dates as milliseconds  
	      
	    long milis1 = cal1.getTimeInMillis();  
	      
	    long milis2 = cal2.getTimeInMillis();  
	      
	       
	      
	    // Calculate difference in milliseconds  
	      
	    long diff = milis2 - milis1;  
	      
	       
	      
	    // Calculate difference in seconds  
	      
	    //long diffSeconds = diff / 1000;  
	      
	       
	      
	    // Calculate difference in minutes  
	      
	    //long diffMinutes = diff / (60 * 1000);  
	      
	       
	      
	    // Calculate difference in hours  
	      
	    //long diffHours = diff / (60 * 60 * 1000);  
	      
	       
	      
	    // Calculate difference in days  
	      
	    long diffDays = diff / (24 * 60 * 60 * 1000);  
	      
	       
	      
	    //System.out.println("In milliseconds: " + diff + " milliseconds.");  
	      
	    //System.out.println("In seconds: " + diffSeconds + " seconds.");  
	      
	    //System.out.println("In minutes: " + diffMinutes + " minutes.");  
	      
	    //System.out.println("In hours: " + diffHours + " hours.");  
	      
	    //System.out.println("In days: " + diffDays + " days.");
	    return diffDays;
    }  

    // 현재 일 반환

    public String getCurrDate()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        date.append(cal.get(1));

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1);

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5));

        return date.toString();

    }

    public String getCurrDateTime()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        date.append(cal.get(1));

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1);

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5));

        if(cal.get(11) < 10) date.append('0');

        date.append(cal.get(11));

        if(cal.get(12) < 10) date.append('0');

        date.append(cal.get(12));

        return date.toString();

    }

    public String getCurrDateTimeFormat1()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        date.append(cal.get(1) + "년");

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1 + "월");

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5) + "일");

        if(cal.get(11) < 10) date.append('0');

        date.append(cal.get(11) + "시");

        if(cal.get(12) < 10) date.append('0');

        date.append(cal.get(12) + "분");

        return date.toString();

    }

    public String getCurrDay(){

        GregorianCalendar cal = new GregorianCalendar();

        String day = String.valueOf(cal.get(Calendar.DATE)).toString();

        if(day.length() == 1) day = "0" + day;

        return day;

    }

    // 현재 월가져오기

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // 오늘의 요일 가져오기
    public String getCurrDayOfWeek()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int day = cal.get(Calendar.DAY_OF_WEEK);

        String days = "";

        if(day == 1)
            days = "일";

        else if(day == 2)
            days = "월";

        else if(day == 3)
            days = "화";

        else if(day == 4)
            days = "수";

        else if(day == 5)
            days = "목";

        else if(day == 6)
            days = "금";

        else if(day == 7) days = "토";

        return days;

    }

    // 시:분:초

    public String getCurrMonth()

    {

        GregorianCalendar cal = new GregorianCalendar();

        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);

        if(month.length() == 1) month = "0" + month;

        return month;

    }

    // 시:분

    public String getCurrTime()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int sec = cal.get(Calendar.SECOND);

        String time = String.valueOf(toLen2(hour)) + ":" + String.valueOf(toLen2(minute)) + ":"
                + String.valueOf(toLen2(sec)) + "";

        return time;

    }

    // 년도 반환

    public String getCurrTimeNoSec()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int sec = cal.get(Calendar.SECOND);

        String time = String.valueOf(toLen2(hour)) + ":" + String.valueOf(toLen2(minute));

        return time;

    }

    // YYYYMMDDHHmmss 20000427210948 반환

    public String getCurrYear()

    {

        GregorianCalendar cal = new GregorianCalendar();

        String year = String.valueOf(cal.get(Calendar.YEAR));

        return year;

    }

    // 현재년월의 마지막날인 yyyymmdd반환

    public String getDateTimeSec()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        // 년

        date.append(cal.get(1));

        // 월

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1);

        // 일

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5));

        // 시

        if(cal.get(11) < 10) date.append('0');

        date.append(cal.get(11));

        // 분

        if(cal.get(12) < 10) date.append('0');

        date.append(cal.get(12));

        // 초

        if(cal.get(13) < 10) date.append('0');

        date.append(cal.get(13));

        return date.toString();

    }

    public String getCurrentTimeNoDash()
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curDate = formatter.format(Calendar.getInstance().getTime());
        return curDate;
    }
    
    // 년월의 마지막날인 yyyymmdd반환

    public String getEndDate()

    {

        int year = Integer.valueOf(getCurrYear()).intValue();

        int month = Integer.valueOf(getCurrMonth()).intValue();

        return toLen(year,4) + toLen2(month) + getEndOfMonthDay(year,month);

    }

    // 년월의 마지막날인 yyyymmdd반환

    public String getEndDate(int year, int month)

    {

        return toLen(year,4) + toLen2(month) + toLen2(getEndOfMonthDay(year,month));

    }

    // 마지막날 구하기

    public String getEndDate(String date)

    {

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        return toLen(year,4) + toLen2(month) + toLen2(getEndOfMonthDay(year,month));

    }

    // 마지막날 구하기

    public int getEndOfMonthDay(int year, int month)

    {

        if(String.valueOf(year).length() != 4 || String.valueOf(month).length() < 1
                || String.valueOf(month).length() > 2) return 0;

        int daysList[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if(month == 2)

        {

            if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))

            {

                return(29);

            }

        }

        return(daysList[month - 1]);

    }

    // 2000년04월22일 12:13:23반환

    public int getEndOfMonthDay(String date)

    {

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        if(String.valueOf(year).length() != 4 || String.valueOf(month).length() < 1
                || String.valueOf(month).length() > 2) return 0;

        int daysList[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if(month == 2)

        {

            if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))

            {

                return(29);

            }

        }

        return(daysList[month - 1]);

    }

    // 현재시간을 원하는 형식으로

    // format "YYYYMMDD hh:mm:ss

    // format "YYYYMMDDhhmmssms

    // format "YYYY년 MM월 DD일 hh-mm-ss 등등

    // format YYYY

    // format mm

    // format mm.dd

    public long getFileSize(String path_file)

    {

        long size = 0;

        File file = new File(path_file);

        try

        {

            if(file.exists())
                size = file.length();

            else size = -990;

        }

        catch(Exception e)

        {

            size = -991;

        }

        return size;

    }

    // 년월일을 원하는 형식으로

    public long getFileSize(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        return getFileSize(path_file);

    }

    // 년월일 8자리or10자리 입력받아 그 차를 일로 표시

    // public long getDiffDay(String from_date,String to_date)

    public String getFormatCurrDateTime()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH) + 1;

        int day = cal.get(Calendar.DATE);

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int sec = cal.get(Calendar.SECOND);

        StringBuffer date = new StringBuffer();

        date.append(year);

        date.append("년");

        date.append(toLen2(month));

        date.append("월");

        date.append(toLen2(day));

        date.append("일");

        date.append(" ");

        date.append(toLen2(hour));

        date.append(":");

        date.append(toLen2(minute));

        date.append(":");

        date.append(toLen2(sec));

        return date.toString();

    }

    // 년월일 8자리또는 6자리씩을 입력받아 그 월의 차를 표시

    // 통상적으로 199903에서 199904의 차는 1개월이지만 여기서는 2개월로

    // 년월일 8자리또는 6자리씩을 입력받아 그 월의 차를 표시

    // public int getDiffMonth(String from_date,String toxx_date)

    public String getFormatCurrDateTime(String format)

    {

        GregorianCalendar cal = new GregorianCalendar();

        int year = cal.get(Calendar.YEAR);

        int month = cal.get(Calendar.MONTH) + 1;

        int day = cal.get(Calendar.DATE);

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int sec = cal.get(Calendar.SECOND);

        int msec = cal.get(Calendar.MILLISECOND);

        format = replaceStringAll(format,"YYYY",toLen(year,4));

        // format = replaceString( format, "YY", yy );

        format = replaceStringAll(format,"MM",toLen2(month));

        format = replaceStringAll(format,"DD",toLen2(day));

        format = replaceStringAll(format,"hh",toLen2(hour));

        format = replaceStringAll(format,"mm",toLen2(minute));

        format = replaceStringAll(format,"ss",toLen2(sec));

        format = replaceStringAll(format,"ms",toLen2(msec));

        return format.toString();

    }

    // 사용 주의 사항 년/월/일 미리 체크 요망

    // 입력한날의 요일 가져오기

    public String getFormatDate(String date, String flag)

    {

        String result = "";

        if(date == null) return date; // || (date.length() != 8 &&
                                        // date.length() != 6)

        if(!date.equals(""))

        {

            StringBuffer newDate = new StringBuffer(date);

            if(date.length() == 8)

            {

                if(flag.toUpperCase().equals("KOR"))

                {

                    newDate.insert(8,"일");

                    newDate.insert(6,"월");

                    newDate.insert(4,"년");

                }else{

                    newDate.insert(6,flag);

                    newDate.insert(4,flag);

                }

            }else if(date.length() == 6)

            {

                if(flag.toUpperCase().equals("KOR"))

                {

                    newDate.insert(6,"월");

                    newDate.insert(4,"년");

                }else{

                    newDate.insert(4,flag);

                }

            }

            else if(date.length() == 4)

            {

                if(flag.toUpperCase().equals("KOR"))

                {

                    newDate.insert(4,"일");

                    newDate.insert(2,"월");

                }else{

                    newDate.insert(2,flag);

                }

            }

            else if(date.length() == 10)

            {

                newDate = new StringBuffer(date);

            }

            else

            {

                // newDate= new StringBuffer( getCurrDate() );

                newDate = new StringBuffer(date);

            }

            result = newDate.toString();

        }

        return result;

    }

    // 200003반환

    public String getHalfOfWeek(int year, int month, int day, int destday, String format)

    {

        GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(year,month - 1,day);

        // (1-일,2-월,...7-토)

        int dayofweek = calendar.get(calendar.DAY_OF_WEEK);

        // 그주의 월요일을 받아오기

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1,day - dayofweek + destday + 1);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        format = replaceStringAll(format,"YYYY",stryear);

        // format = replaceString( format, "YY", yy );

        format = replaceStringAll(format,"MM",strmonth);

        format = replaceStringAll(format,"DD",strday);

        return format;

    }

    // nareadme.htm입력받아 20000614090248_nareadme.htm반환

    public String getHalfOfWeek(String date, int destday, String format)

    {

        // 입력받은 날의 요일 반환

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        int day = Integer.valueOf(date.substring(6,8)).intValue();

        format = getHalfOfWeek(year,month,day,destday,format);

        return format;

    }

    public String getNextMonth(int year, int month, int day, int destmonth, String format)

    {

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1 + destmonth,day);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        format = replaceStringAll(format,"YYYY",stryear);

        // format = replaceString( format, "YY", yy );

        format = replaceStringAll(format,"MM",strmonth);

        format = replaceStringAll(format,"DD",strday);

        return format;

    }

    public String getNextMonth(String date, int destmonth, String format)

    {

        // var arg = getUserDate1( oneday )

        // arg - date형식

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        int day = Integer.valueOf(date.substring(6,8)).intValue();

        format = getNextMonth(year,month,day,destmonth,format);

        return format;

    }

    // file만들고 output스트림 만들기

    public long getSubtractDay(String from_date, String to_date)

    {

        long diff_day = 0; // 차를 일로 표시

        // 년 ,월 ,일

        int from_year = 0;

        int from_month = 0;

        int from_day = 0;

        // 년 ,월 ,일

        int to_year = 0;

        int to_month = 0;

        int to_day = 0;

        int pointDash = from_date.lastIndexOf("-");

        int pointSlash = from_date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212입력

            // 년 ,월 ,일

            from_year = Integer.valueOf(from_date.substring(0,4)).intValue();

            from_month = Integer.valueOf(from_date.substring(4,6)).intValue();

            from_day = Integer.valueOf(from_date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12입력

            // 년 ,월 ,일

            from_year = Integer.valueOf(from_date.substring(0,4)).intValue();

            from_month = Integer.valueOf(from_date.substring(5,7)).intValue();

            from_day = Integer.valueOf(from_date.substring(8,10)).intValue();

        }

        pointDash = to_date.lastIndexOf("-");

        pointSlash = to_date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212입력

            // 년 ,월 ,일

            to_year = Integer.valueOf(to_date.substring(0,4)).intValue();

            to_month = Integer.valueOf(to_date.substring(4,6)).intValue();

            to_day = Integer.valueOf(to_date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12입력

            // 년 ,월 ,일

            to_year = Integer.valueOf(to_date.substring(0,4)).intValue();

            to_month = Integer.valueOf(to_date.substring(5,7)).intValue();

            to_day = Integer.valueOf(to_date.substring(8,10)).intValue();

        }

        GregorianCalendar cal_of_start = new GregorianCalendar();

        cal_of_start.set(from_year,from_month - 1,from_day);

        java.util.Date startDate = cal_of_start.getTime();

        GregorianCalendar cal_of_end = new GregorianCalendar();

        cal_of_end.set(to_year,to_month - 1,to_day);

        java.util.Date endDate = cal_of_end.getTime();

        // 1970.1.1부터의 초

        long msec1 = startDate.getTime();

        long msec2 = endDate.getTime();

        long msec3 = 0;

        if(msec2 >= msec1)

        {

            msec3 = msec2 - msec1;

            // msec에 해당하는 날짜 수를 계산한다

            diff_day = msec3 / (24 * 60 * 60 * 1000L);

        }

        else

        {

            msec3 = msec1 - msec2;

            // msec에 해당하는 날짜 수를 계산한다

            diff_day = -(msec3 / (24 * 60 * 60 * 1000L));

        }

        // diff_day = msec3/(24 * 60 * 60 * 1000L);

        return diff_day;

    }

    // file만들고 output스트림 만들기

    // appent가 true시 덧 붙이기 ,false시 덮어쓰기

    public int getSubtractMonth(String from_date, String toxx_date)

    throws IOException

    {

        // 뿌려질 결과의 컬럼개수

        int diff_month = 0;

        int END_MONTH = 12;

        int start_mon_of_from_date = 0;// 시작년월의 시작월

        int start_mon_of_toxx_date = 0;// 끝년월의 시작월

        int loop_of_from_date = 0;// 시작년월의 loop 수

        int loop_of_toxx_date = 0;// 끝년월의 loop 수

        int loop_of_year = 0;

        // 시작 년월의 시작년

        int year_of_from_date = Integer.valueOf(from_date.substring(0,4)).intValue();

        // 끝 년월의 끝년

        int year_of_toxx_date = Integer.valueOf(toxx_date.substring(0,4)).intValue();

        int pointDash = 0;

        int pointSlash = 0;

        if(year_of_from_date == year_of_toxx_date)

        {// 시작 년과 끝 년이 같을때

            pointDash = from_date.lastIndexOf("-");

            pointSlash = from_date.lastIndexOf("/");

            if(pointDash == -1 && pointSlash == -1)

            {

                start_mon_of_from_date = Integer.valueOf(from_date.substring(4,6)).intValue();

                start_mon_of_toxx_date = Integer.valueOf(toxx_date.substring(4,6)).intValue();

            }

            else

            {

                start_mon_of_from_date = Integer.valueOf(from_date.substring(5,7)).intValue();

                start_mon_of_toxx_date = Integer.valueOf(toxx_date.substring(5,7)).intValue();

            }

            //

            loop_of_from_date = start_mon_of_toxx_date;

            loop_of_toxx_date = 0;

            // 컬럼에 개수 = toxx_date 와 from_date 컬럼 격차 +1

            if(start_mon_of_toxx_date >= start_mon_of_from_date)

                diff_month = (start_mon_of_toxx_date - start_mon_of_from_date + 1);

            else

            diff_month = -(start_mon_of_from_date - start_mon_of_toxx_date + 1);

        }

        else if(year_of_from_date < year_of_toxx_date)

        {

            pointDash = from_date.lastIndexOf("-");

            pointSlash = from_date.lastIndexOf("/");

            if(pointDash == -1 && pointSlash == -1)

            {

                // 시작달

                start_mon_of_from_date = Integer.valueOf(from_date.substring(4,6)).intValue();

            }

            else

            {

                start_mon_of_from_date = Integer.valueOf(from_date.substring(5,7)).intValue();

            }

            start_mon_of_toxx_date = 1;

            //

            loop_of_from_date = END_MONTH;

            pointDash = toxx_date.lastIndexOf("-");

            pointSlash = toxx_date.lastIndexOf("/");

            if(pointDash == -1 && pointSlash == -1)

            {

                loop_of_toxx_date = Integer.valueOf(toxx_date.substring(4,6)).intValue();

            }

            else

            {

                loop_of_toxx_date = Integer.valueOf(toxx_date.substring(5,7)).intValue();

            }

            loop_of_year = year_of_toxx_date - year_of_from_date - 1;

            diff_month = (loop_of_from_date - start_mon_of_from_date + 1)
                    + (loop_of_year * END_MONTH) + (loop_of_toxx_date - start_mon_of_toxx_date + 1);

        }

        else if(year_of_from_date > year_of_toxx_date)

        {

            pointDash = from_date.lastIndexOf("-");

            pointSlash = from_date.lastIndexOf("/");

            if(pointDash == -1 && pointSlash == -1)

            {

                // 시작달

                start_mon_of_from_date = Integer.valueOf(toxx_date.substring(4,6)).intValue();

                start_mon_of_toxx_date = 1;

                //

                loop_of_from_date = END_MONTH;

                loop_of_toxx_date = Integer.valueOf(from_date.substring(4,6)).intValue();

            }

            else

            {

                // 시작달

                start_mon_of_from_date = Integer.valueOf(toxx_date.substring(5,7)).intValue();

                start_mon_of_toxx_date = 1;

                //

                loop_of_from_date = END_MONTH;

                loop_of_toxx_date = Integer.valueOf(from_date.substring(5,7)).intValue();

            }

            loop_of_year = year_of_from_date - year_of_toxx_date - 1;

            diff_month = (loop_of_from_date - start_mon_of_from_date + 1)
                    + (loop_of_year * END_MONTH) + (loop_of_toxx_date - start_mon_of_toxx_date + 1);

            diff_month = -diff_month;

        }

        return diff_month;

    }

    public String getWantDayOfWeek(int yyyy, int mm, int dd)

    {

        GregorianCalendar calendar = new GregorianCalendar();

        String days = "";

        int day = 0;

        calendar.set(yyyy,mm - 1,dd);

        day = calendar.get(calendar.DAY_OF_WEEK);

        if(day == 1)
            days = "일";

        else if(day == 2)
            days = "월";

        else if(day == 3)
            days = "화";

        else if(day == 4)
            days = "수";

        else if(day == 5)
            days = "목";

        else if(day == 6)
            days = "금";

        else if(day == 7) days = "토";

        return days;

    }

    public String getYYYYMM()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        date.append(cal.get(1));

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1);

        return date.toString();

    }

    // 파일읽기

    // 가능 readFile2("c:\\","autoexec.bat")

    // 가능 readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // 불가능 readFile2("\\","Ws_ftp.log")

    // 불가능 readFile2("/","Ws_ftp.log")

    public String makeFileName(String srcfile)

    {

        StringBuffer filename = new StringBuffer();

        GregorianCalendar cal = new GregorianCalendar();

        // 년

        filename.append(cal.get(1));

        // 월

        if(cal.get(2) < 9) filename.append('0');

        filename.append(cal.get(2) + 1);

        // 일

        if(cal.get(5) < 10) filename.append('0');

        filename.append(cal.get(5));

        // 시

        if(cal.get(11) < 10) filename.append('0');

        filename.append(cal.get(11));

        // 분

        if(cal.get(12) < 10) filename.append('0');

        filename.append(cal.get(12));

        // 초

        if(cal.get(13) < 10) filename.append('0');

        filename.append(cal.get(13));

        filename.append('_');

        // 파일이름중 공백에 _로

        srcfile = replaceStringAll(srcfile," ","_");

        if(srcfile.lastIndexOf("\\") == -1)

            filename.append(srcfile);

        else

        filename.append(srcfile.substring(srcfile.lastIndexOf("\\") + 1));

        try

        {

            return filename.toString();

        }

        catch(Exception e)

        {

            return srcfile + ":" + e.getMessage();

        }

        // return null;

    }

    // 파일 읽기

    // 사용법

    // 가능 readFile2("c:\\","autoexec.bat")

    // 가능 readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // 불가능 readFile2("\\","Ws_ftp.log")

    // 불가능 readFile2("/","Ws_ftp.log")

    public PrintWriter openLog(String filename)

    {

        try

        {

            return new PrintWriter(new BufferedWriter(new FileWriter(filename)));

        }

        catch(IOException ie)

        {

            System.err.println("Error:" + ie.toString());

            return null;

        }

        catch(Exception e)

        {

            System.err.println("Error:" + e.toString());

            return null;

        }

    }

    // 파일읽기

    // 가능 readFile2("c:\\","autoexec.bat")

    // 가능 readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // 불가능 readFile2("\\","Ws_ftp.log")

    // 불가능 readFile2("/","Ws_ftp.log")

    public PrintWriter openLog(String path, String fileName, boolean append)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        try

        {

            return new PrintWriter(

            new BufferedWriter(

            new FileWriter(path_file, append)

            )

            );

        }

        catch(IOException ie)

        {

            System.err.println("Error:" + ie.toString());

            return null;

        }

        catch(Exception e)

        {

            System.err.println("Error:" + e.toString());

            return null;

        }

    }

    // 화일 읽기

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // file upload시 사용
    public Hashtable parseMulti(HttpServletRequest req, long maxSize)

    throws IOException

    {

        if(!req.getContentType().toLowerCase().startsWith("multipart/form-data"))

        {

            throw new IOException("Posted content type isn't multipart/form-data");

            // out.println("\uD30C\uC77C Upload\uB97C \uC704\uD55C
            // Content-type\uC774 \uC9C0\uC815\uB418\uC9C0
            // \uC54A\uC558\uC2B5\uB2C8\uB2E4");

            // return null;

        }

        long length = req.getContentLength();

        if(length > maxSize){

            throw new IOException("Posted content length of " + length +

            " exceeds limit of " + maxSize);

        }

        int ind = req.getContentType().indexOf("boundary=");

        if(ind == -1)

        {

            throw new IOException("Separation boundary was not specified");

            // return null;

        }

        String boundary = req.getContentType().substring(ind + 9);

        if(boundary == null)

        {

            throw new IOException("Separation boundary was not specified");

            // return null;

        }

        try

        {

            return parseMulti(boundary,req.getInputStream());

        }

        catch(Exception e)

        {

            e.printStackTrace();

        }

        return null;

    }

    // 원하는 문자 삭제

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // file upload시 사용
    Hashtable parseMulti(String boundary, ServletInputStream in)

    throws IOException

    {

        int buffSize = 8192 * 4;

        Hashtable hash = new Hashtable();

        String boundaryStr = "--" + boundary;

        byte b[] = new byte[buffSize];

        int result = in.readLine(b,0,b.length);

        if(result == -1)

        throw new IllegalArgumentException("InputStream truncated");

        String line = new String(b, 0, result);

        if(!line.startsWith(boundaryStr))

        throw new IllegalArgumentException("MIME boundary missing: " + line);

        do

        {

            String lowerline;

            ByteArrayOutputStream content;

            String filename;

            String contentType;

            String name;

            long filelen = 0l;

            do

            {

                filename = null;

                contentType = null;

                content = new ByteArrayOutputStream();

                name = null;

                result = in.readLine(b,0,b.length);

                if(result == -1)

                {

                    return hash;

                }

                line = new String(b, 0, result - 2);

                lowerline = line.toLowerCase();

            }while(!lowerline.startsWith("content-disposition"));

            int ind = lowerline.indexOf("content-disposition: ");

            int ind2 = lowerline.indexOf(";");

            if(ind == -1 || ind2 == -1)

            throw new IllegalArgumentException("Content Disposition line misformatted: " + line);

            String disposition = lowerline.substring(ind + 21,ind2);

            if(!disposition.equals("form-data"))

                throw new IllegalArgumentException("Content Disposition of " + disposition
                        + " is not supported");

            int ind3 = lowerline.indexOf("name=\"",ind2);

            int ind4 = lowerline.indexOf("\"",ind3 + 7);

            if(ind3 == -1 || ind4 == -1)

            throw new IllegalArgumentException("Content Disposition line misformatted: " + line);

            name = line.substring(ind3 + 6,ind4);

            int ind5 = lowerline.indexOf("filename=\"",ind4 + 2);

            int ind6 = lowerline.indexOf("\"",ind5 + 10);

            if(ind5 != -1 && ind6 != -1)

            filename = line.substring(ind5 + 10,ind6);

            result = in.readLine(b,0,b.length);

            if(result == -1)

            {

                return hash;

            }

            line = new String(b, 0, result - 2);

            lowerline = line.toLowerCase();

            if(lowerline.startsWith("content-type"))

            {

                int ind7 = lowerline.indexOf(" ");

                if(ind7 == -1)

                throw new IllegalArgumentException("Content-Type line misformatted: " + line);

                contentType = lowerline.substring(ind7 + 1);

                result = in.readLine(b,0,b.length);

                if(result == -1) return hash;

                line = new String(b, 0, result - 2);

                if(line.length() != 0)

                throw new IllegalArgumentException("Unexpected line in MIMEpart header: " + line);

            }else

            if(line.length() != 0)

            throw new IllegalArgumentException("Misformatted line following disposition: " + line);

            boolean readingContent = true;

            boolean firstLine = true;

            byte buffbytes[] = new byte[buffSize];

            int buffnum = 0;

            result = in.readLine(b,0,b.length);

            if(result == -1) return hash;

            line = new String(b, 0, result);

            if(!line.startsWith(boundaryStr))

            {

                System.arraycopy(b,0,buffbytes,0,result);

                buffnum = result;

                result = in.readLine(b,0,b.length);

                if(result == -1) return hash;

                line = new String(b, 0, result);

                firstLine = false;

                if(line.startsWith(boundaryStr)) readingContent = false;

            }else{

                readingContent = false;

            }

            while(readingContent)

            {

                content.write(buffbytes,0,buffnum);

                System.arraycopy(b,0,buffbytes,0,result);

                buffnum = result;

                result = in.readLine(b,0,b.length);

                filelen += result;

                if(result == -1) return hash;

                line = new String(b, 0, result);

                if(line.startsWith(boundaryStr)) readingContent = false;

            }

            if(!firstLine && buffnum > 2) content.write(buffbytes,0,buffnum - 2);

            if(filename == null)

            {// file태그가 아닐때

                if(hash.get(name) == null)

                {

                    String values[] = new String[1];

                    values[0] = content.toString();

                    hash.put(name,values);

                }else{

                    Object prevobj = hash.get(name);

                    if(prevobj instanceof String[])

                    {

                        String prev[] = (String[])prevobj;

                        String newStr[] = new String[prev.length + 1];

                        System.arraycopy(prev,0,newStr,0,prev.length);

                        newStr[prev.length] = content.toString();

                        hash.put(name,newStr);

                    }else

                    {

                        throw new IllegalArgumentException(
                                "failure in parseMulti hashtable building code");

                    }

                }

            }

            else

            {// file태그일때

                if(hash.get(name) == null)

                {// hash에 없다면

                    Hashtable filehash = new Hashtable(5);

                    filehash.put("name",name);

                    filehash.put("filename",filename);

                    if(contentType == null) contentType = "application/octet-stream";

                    filehash.put("content-type",contentType);

                    filehash.put("content",content.toByteArray());

                    filehash.put("filesize",String.valueOf(filelen));

                    Hashtable values[] = new Hashtable[1];

                    values[0] = filehash;

                    hash.put(name,values);

                }

                else

                {// hash에 이미 있다면

                    Object prevobj = hash.get(name);

                    if(prevobj instanceof Hashtable[])

                    {

                        Hashtable prev[] = (Hashtable[])prevobj;

                        Hashtable newStr[] = new Hashtable[prev.length + 1];

                        System.arraycopy(prev,0,newStr,0,prev.length);

                        Hashtable filehash = new Hashtable(5);

                        filehash.put("name",name);

                        filehash.put("filename",filename);

                        if(contentType == null) contentType = "application/octet-stream";

                        filehash.put("content-type",contentType);

                        filehash.put("content",content.toByteArray());

                        filehash.put("filesize",String.valueOf(filelen));

                        newStr[prev.length] = filehash;

                        hash.put(name,newStr);

                    }else{

                        throw new IllegalArgumentException(
                                "failure in parseMulti hashtable building code");

                    }

                }

            }// file태그인지 아닌지일때

        }while(true);

    }

    public Hashtable putOfHashtableToArray(Hashtable parameters, String name, String value)

    {

        if(parameters.get(name) == null)

        {

            String values[] = new String[1];

            values[0] = value;

            parameters.put(name,values);

        }else{

            Object prevobj = parameters.get(name);

            if(prevobj instanceof String[])

            {

                String prev[] = (String[])prevobj;

                String newStr[] = new String[prev.length + 1];

                System.arraycopy(prev,0,newStr,0,prev.length);

                newStr[prev.length] = value;

                parameters.put(name,newStr);

            }else

            {

                throw new IllegalArgumentException("failure in parseMulti hashtable building code");

            }

        }

        return parameters;

    }

    // 점이하 제거

    public String readFile(String path_file)

    {

        File myFile = new File(path_file);

        byte buf[] = new byte[(int)myFile.length()];

        FileInputStream i = null;

        try

        {

            i = new FileInputStream(myFile);

            i.read(buf);

            i.close();

        }

        catch(IOException e)

        {

            buf = ("Problems reading file :" + e.getMessage()).getBytes();

            e.printStackTrace();

        }

        return new String(buf);

    }

    // makefilename중 실제 파일명가져오기

    // 20000614090248_nareadme.htm중nareadme.htm

    public String readFile(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        StringBuffer ta = new StringBuffer();

        try

        {

            FileReader fr = new FileReader(path_file);

            BufferedReader br = new BufferedReader(fr);

            String line;

            while((line = br.readLine()) != null)

            {

                ta.append(line + "\n");

            }

        }

        catch(IOException e)

        {

            ta.append("Problems reading file" + e.getMessage());

        }

        return String.valueOf(ta).toString();

    }

    // 마지막으로 일치하는 문자열 교체

    public String readFile2(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        File myFile = new File(path_file);

        byte buf[] = new byte[(int)myFile.length()];

        FileInputStream i = null;

        try

        {

            i = new FileInputStream(myFile);

            i.read(buf);

            i.close();

        }

        catch(IOException e)

        {

            buf = ("Problems reading file" + e.getMessage()).getBytes();

            e.printStackTrace();

        }

        return new String(buf);

    }

    // 특수문자를 임의의 기호로

    public String readFile3(String path, String fileName) throws IOException

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        String rtn = "";

        try

        {

            StringBuffer rtnBuffer = new StringBuffer();

            URLConnection LinkPage = new URL(path_file).openConnection();

            LinkPage.connect();

            BufferedReader Data = new BufferedReader(new InputStreamReader(LinkPage
                    .getInputStream()));

            while((rtn = Data.readLine()) != null)

                rtnBuffer.append(rtn + "\n");

            return rtnBuffer.toString();

        }

        catch(IOException e)

        {

            rtn = "Problems reading file" + e.getMessage();

            return rtn;

        }

    }

    // 임의의 기호를 특수문자로

    public String removeChar(String str, String deli)

    {

        String result = "";

        if(!str.equals(""))

        {

            StringTokenizer st = new StringTokenizer(str, deli);

            StringBuffer buffer = new StringBuffer();

            for(; st.hasMoreTokens(); buffer.append(st.nextToken()))
                ;

            result = buffer.toString();

        }

        return result;

    }

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // comma제거
    public String removeComma(String s)

    {

        if(s == null) return null;

        if(s.indexOf(",") != -1)

        {

            StringBuffer buf = new StringBuffer();

            for(int i = 0; i < s.length(); i++){

                char c = s.charAt(i);

                if(c != ',') buf.append(c);

            }

            return buf.toString();

        }

        return s;

    }

    public String removePoint(String str)

    {

        int pt_index = 0;

        if(str == null || str == "" || str.length() < 1) return "";

        pt_index = str.indexOf('.');

        if(pt_index == -1) return str;

        str = str.substring(0,pt_index);

        return str;

    }

    public String removeTimestamp(String TimestampedFilename)

    {

        return TimestampedFilename.substring(TimestampedFilename.indexOf("_") + 1);

    }

    // 함수추가 : 방윤옥 2004.03.05 replaceStringAll2 에서 synchronized 제거

    public String renameFile(String file, String new_file)

    throws Exception, IOException

    {

        return renameFile(savePath,file,new_file);

    }

    // 문자열의 색깔변환(전체문자열에는 영향없음)

    public String renameFile(String path, String fileName, String newName)

    throws Exception, IOException

    {

        String path_file = "";

        String new_path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

        {

            path_file = path + fileName;

            new_path_file = path + newName;

        }else

        {

            path_file = path + System.getProperty("file.separator") + fileName;

            new_path_file = path + System.getProperty("file.separator") + newName;

        }

        return runRenameFile(path_file,new_path_file);

    }

    /*
     * double round = Math.pow(10,pntLen);
     * 
     * newDoub = Math.floor(doub * round +.5)/round;
     * 
     * 즉
     * 
     * 소수 2째자리 반올림은
     * 
     * y= Math.floor(x*100+.5)/100;
     * 
     * 
     * 
     * 일정자리수 넘어가면 4.7*E07식으로 나옴
     * 
     */

    public String replaceCode(String orgstr)

    {

        int len = orgstr.length();

        String rplStr = "";

        String currStr = "";

        String replaceStr = "";

        int i = 0;

        for(i = 0; i < len; i++)

        {

            currStr = orgstr.substring(i,i + 1);

            if(currStr.equals("\""))

            {

                rplStr = "##34";

            }else if(currStr.equals("\'"))

            {

                rplStr = "##39";

            }else if(currStr.equals(">"))

            {

                rplStr = "##60";

            }else if(currStr.equals("<"))

            {

                rplStr = "##62";

            }else if(currStr.equals("/"))

            {

                rplStr = "##47";

            }else if(currStr.equals("\\"))

            {

                rplStr = "##92";

            }else if(currStr.equals("("))

            {

                rplStr = "##40";

            }else if(currStr.equals(")"))

            {

                rplStr = "##41";

            }else if(currStr.equals(","))

            {

                rplStr = "##44";

            }else

            {

                rplStr = currStr;

            }

            replaceStr += rplStr;

        }

        return replaceStr;

        /*
         * 개행문자도 가능
         * 
         * }else if( currStr.equals("\n"))
         *  {
         * 
         * rplStr = "##10";
         * 
         */

    }

    public String replaceSign(String orgstr)

    {

        int len = orgstr.length();

        String rplStr = "";

        String currChrs = "";

        String currStr = "";

        String replaceStr = "";

        int j = 0, i = 0;

        boolean flag = true;

        for(i = 0; i < len; i++)

        {

            currChrs = orgstr.substring(i,i + 1);

            flag = true;

            j = i;

            if(len - j >= 4)
                currStr = orgstr.substring(j,j + 4);

            else currStr = orgstr.substring(j,j + 1);

            if(currStr.equals("##34"))

            {

                rplStr = "\"";

            }else if(currStr.equals("##39"))

            {

                rplStr = "\'";

            }else if(currStr.equals("##60"))

            {

                rplStr = ">";

            }else if(currStr.equals("##62"))

            {

                rplStr = "<";

            }else if(currStr.equals("##47"))

            {

                rplStr = "/";

            }else if(currStr.equals("##92"))

            {

                rplStr = "\\";

            }else if(currStr.equals("##40"))

            {

                rplStr = "(";

            }else if(currStr.equals("##41"))

            {

                rplStr = ")";

            }else if(currStr.equals("##44"))

            {

                rplStr = ",";

            }else

            {

                rplStr = currChrs;

                flag = false;

            }

            replaceStr += rplStr;

            if(flag == true)

            {

                i = i + 3;

            }

        }

        return replaceStr;

        /*
         * 개행문자도 가능
         * 
         * 
         * 
         * }else if( currStr.equals("##10"))
         *  {
         * 
         * rplStr = "";
         * 
         */

    }

    // byte단위로 넘어온 data를 서버에 저장

    // 파일의 실제 저장

    public String replaceSignSave(String orgstr)

    {

        // pos = 0;

        int len = orgstr.length();

        String rplStr = "";

        String currChrs = "";

        String currStr = "";

        String replaceStr = "";

        int j = 0, i = 0;

        boolean flag = true;

        for(i = 0; i < len; i++)

        {

            currChrs = orgstr.substring(i,i + 1);

            flag = true;

            j = i;

            if(len - j >= 4)
                currStr = orgstr.substring(j,j + 4);

            else currStr = orgstr.substring(j,j + 1);

            if(currStr.equals("##34"))

            {

                rplStr = "\"";

            }else if(currStr.equals("##39"))

            {

                rplStr = "\'";

            }else if(currStr.equals("##60"))

            {

                rplStr = ">";

            }else if(currStr.equals("##62"))

            {

                rplStr = "<";

            }else if(currStr.equals("##47"))

            {

                rplStr = "/";

            }else if(currStr.equals("##92"))

            {

                rplStr = "\\";

            }else if(currStr.equals("##10"))

            {

                rplStr = "";

            }else if(currStr.equals("##40"))

            {

                rplStr = "(";

            }else if(currStr.equals("##41"))

            {

                rplStr = ")";

            }else if(currStr.equals("##44"))

            {

                rplStr = ",";

            }else

            {

                rplStr = currChrs;

                flag = false;

            }

            replaceStr += rplStr;

            if(flag == true)

            {

                i = i + 3;

            }

        }

        return replaceStr;

    }

    // 원하는 사이즈만큼의 char넣기

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // String Replace 시킴 - 문자열의 변환(전체문자열에는 영향없음)
    public String replaceStringAll(String in, String find, String replace)

    {

        String data = in;

        int pos = 0;

        int npos = in.indexOf(find,pos);

        while(npos >= 0)

        {

            data = data.substring(0,npos) + replace
                    + data.substring(npos + find.length(),data.length());

            pos = npos + find.length();

            npos = data.indexOf(find,pos);

        }

        return data;

    }

    // comma세팅

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // 문자열의 변환(전체문자열에는 영향없음)
    public synchronized String replaceStringAll2(String Source, String FindText, String replace)

    {

        String rtnSource = "";
        String rtnCheck = "";

        int FindLength = Source.length();

        // 찾을 문자 길이

        int FindTextLength = FindText.length();

        char SourceArrayChar[] = new char[FindLength];

        SourceArrayChar = Source.toCharArray();

        for(int i = 0; i < FindLength; i++)

        {

            rtnCheck = Source.substring(i,i + 1);

            int j = i;

            if(FindLength - j >= FindTextLength)
                rtnCheck = Source.substring(j,j + FindTextLength);

            else rtnCheck = Source.substring(j,j + 1);

            if(rtnCheck.equals(FindText))

            {

                rtnSource += replace;

                i += (FindTextLength - 1);

            }

            else

            {

                rtnSource += SourceArrayChar[i];

            }

        }

        return rtnSource;

    }

    // comma세팅

    public String replaceStringAll3(String Source, String FindText, String replace)

    {

        String rtnSource = "";
        String rtnCheck = "";
        int FindLength = Source.length();
        // 찾을 문자 길이
        int FindTextLength = FindText.length();

        char SourceArrayChar[] = new char[FindLength];

        SourceArrayChar = Source.toCharArray();

        for(int i = 0; i < FindLength; i++)

        {

            rtnCheck = Source.substring(i,i + 1);

            int j = i;

            if(FindLength - j >= FindTextLength)
                rtnCheck = Source.substring(j,j + FindTextLength);
            else rtnCheck = Source.substring(j,j + 1);

            if(rtnCheck.equals(FindText)){
                rtnSource += replace;
                i += (FindTextLength - 1);
            }else{
                rtnSource += SourceArrayChar[i];
            }

        }

        return rtnSource;
    }

    // comma세팅

    public String replaceStringColor(String Source, String FindText, String Color,
                                     String replaceFlag)

    {

        String rtnSource = "";

        String rtnCheck = "";

        int FindLength = Source.length();

        int FindIndex = Source.indexOf(FindText);

        // 찾을 문자 길이

        int FindTextLength = FindText.length();

        char SourceArrayChar[] = new char[FindLength];

        SourceArrayChar = Source.toCharArray();

        for(int i = 0; i < FindLength; i++)

        {

            rtnCheck = Source.substring(i,i + 1);

            int j = i;

            if(FindLength - j >= FindTextLength)
                rtnCheck = Source.substring(j,j + FindTextLength);

            else rtnCheck = Source.substring(j,j + 1);

            if(replaceFlag.equals("BOTH"))

            {// 대문자이건 소문자이건

                if(rtnCheck.equals(FindText.toUpperCase())
                        || rtnCheck.equals(FindText.toLowerCase()))

                {

                    rtnSource += "<font color='" + Color + "'>" + rtnCheck + "</font>";

                    i += (FindTextLength - 1);

                }

                else

                {

                    rtnSource += SourceArrayChar[i];

                }

            }

            else

            {// 일치하는 것만 - ONLY

                if(rtnCheck.equals(FindText))

                {

                    rtnSource += "<font color='" + Color + "'>" + rtnCheck + "</font>";

                    i += (FindTextLength - 1);

                }

                else

                {

                    rtnSource += SourceArrayChar[i];

                }

            }

        }

        return rtnSource;

    }

    // comma세팅

    // 소수 반올림
    public double roundDouble(double num, int underNum)

    {

        /*
         * 
         * double round = Math.pow(10,underNum);
         * 
         * double newDoub = Math.floor(num * round +.5)/round;
         * 
         */

        double newDoub = 0.0d;

        try

        {

            String formats = formatReturn(underNum);

            DecimalFormat df = new DecimalFormat(formats);

            newDoub = Double.valueOf(df.format(num)).doubleValue();

        }

        catch(Exception _ex)

        {

            newDoub = 0.0D;

        }

        return newDoub;

    }

    // jsp에서 쓰기위한 out만들기

    /*
     * 
     * public void setPrintWriter(ServletResponse res)
     * 
     * throws ServletException
     *  {
     * 
     * try {
     * 
     * this.out = new java.io.PrintWriter(res.getOutputStream(),true);//false -
     * 에러
     *  // this.out = res.getWriter();
     *  } catch(Throwable e) {
     * 
     * 
     *  }
     * 
     * 
     *  }
     * 
     */

    public double roundDouble(String doub, int pntLen)

    {

        double d = 0.0D;

        try

        {

            d = Double.valueOf(doub).doubleValue();

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        return roundDouble(d,pntLen);

    }

    // 원하는 사이즈만큼의 space넣기

    public String runDeleteFile(String path_file)

    {

        String mesg = "";

        File file = new File(path_file);

        if(file.exists())

        {

            if(file.delete())

            {

                mesg = "Y";

            }

            else

            {

                mesg = "File error:" + path_file;

            }

        }else

        {

            mesg = "Not exist File:" + path_file;

        }

        return mesg;

    }

    // 주어진 문자열(str)을 double 형 숫자로 변환한다.

    // 단, 숫자 문자열로서 유효하지 않은 문자열은 0 을 리턴한다.

    public String runRenameFile(String path_file, String new_path_file)

    throws Exception, IOException

    {
        String mesg;

        try

        {

            File file = new File(path_file);

            File newfile = new File(new_path_file);

            // 새이름으로 이미 존재 하면 삭제 - 의미 없음

            // if (newfile.exists()) newfile.delete();

            if(file.exists() && file.isFile())

            {

                if(file.renameTo(newfile))

                {

                    mesg = "Y";

                }

                else

                {

                    mesg = "Rename Error :  Not exist source file(" + path_file
                            + ") , Exist target file(" + new_path_file + ")";

                }

            }else

            {

                mesg = "Not Exist Source File: " + path_file;

            }

        }catch(Exception e)

        {

            mesg = "Exception: " + e.getMessage() + "<br>Rename Error :  Not exist source file("
                    + path_file + ") , Exist target file(" + new_path_file + ")";

            // hrow new Exception("Exception: " + e.getMessage());

        }

        finally

        {

        }

        return mesg;

    }

    // 주어진 문자열(str)을 Integer 형 숫자로 변환한다.

    // 단, 숫자 문자열로서 유효하지 않은 문자열은 0 을 리턴한다.

    public String saveFile(String path, String file, byte bytes[])

    {

        String fileName = "";

        fileName = makeFileName(file);

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        try

        {

            FileOutputStream fout = new FileOutputStream(path_file);

            fout.write(bytes);

            fout.flush();

            fout.close();

            return fileName;

        }

        catch(Exception e)

        {

            return path_file + " : " + e.getMessage();

            // e.printStackTrace();

        }

        // return null;

    }

    // 주어진 문자열(str)을 long 형 숫자로 변환한다.

    // 단, 숫자 문자열로서 유효하지 않은 문자열은 0 을 리턴한다.

    public String setChar(int loopNum, String fillChar)

    {

        String rtn = "";

        for(int i = 0; i < loopNum; i++)

            rtn = rtn + fillChar;

        return rtn;

    }

    // 한글을 일반코드로 바꾸기 위해서

    public String setComma(double num)

    {

        String data = "";

        try

        {

            data = String.valueOf(num).toString();

        }

        catch(Exception _ex)

        {

            data = "0.00";

        }

        return setComma(data);

    }

    // 유니코드를 한글로

    public String setComma(int num)

    {

        String data = "";

        try

        {

            data = String.valueOf(num).toString();

        }

        catch(Exception _ex)

        {

            data = "0";

        }

        return setComma(data);

    }

    // length크기에 맞추어 0을 붙여 반환

    public String setComma(long num)

    {

        String data = "";

        try

        {

            data = String.valueOf(num).toString();

        }

        catch(Exception _ex)

        {

            data = "0";

        }

        return setComma(data);

    }

    public String setComma(String data)

    {

        if(data == null || data.trim().length() == 0) return "";

        data = data.trim();

        // .이하를 제외한 길이

        int strLen = 0;

        // -가 있으면 잘라냈다 반환시 붙여 준다

        int dash = data.indexOf('-');

        // - 의 위치를 못 찾았으면

        if(dash == -1)
            data = data;

        else data = data.substring(dash + 1);

        // .가 있으면 반환시 붙여 준다

        int point = data.indexOf('.');

        if(point == -1)
            strLen = data.length();

        else strLen = point;

        int underNum = data.length() - point;

        double d = 0.0D;

        try

        {

            d = Double.valueOf(data.substring(0,strLen)).doubleValue();

        }

        catch(Exception _ex)

        {

            d = 0.0D;

        }

        String formats = "";

        if(underNum == 0)
            formats = "###,###,###,###";

        else formats = "###,###,###,###." + setChar(underNum,"#");

        DecimalFormat df = new DecimalFormat(formats);

        String commaStr = df.format(d);

        // 소숫점이하 값이 있으면 마지막에 붙여 준다.

        if(point > -1) commaStr += data.substring(point,data.length());

        // - 가 있으면 앞에 붙여준다

        if(dash > -1) commaStr = '-' + commaStr;

        return commaStr;

    }

    // 실제로 파일에 쓰기

    // 클라이언트의 브라우저 cache못쓰게
    public void setResponse(HttpServletResponse response)

    throws UnsupportedEncodingException, IOException

    {

        response.setHeader("Pragma","no-cache");

        response.setHeader("Cache-Control","no-cache");

        // out = response.getWriter();

        // out = new PrintWriter(new OutputStreamWriter(
        // response.getOutputStream(), "KSC5601" ));

    }

    public String setSpace(int loopNum)

    {

        String rtn = "";

        for(int i = 0; i < loopNum; i++)

            rtn = rtn + " ";

        return rtn;

    }

    public double string2Double(String str)

    {

        double ret = 0.0;

        try{

            ret = new Double(str).doubleValue();

        }

        catch(Exception e){

            return 0.0;

        }

        return ret;

    }

    // 그 주의 월요일부터 일요일을 제외하고 3일씩 끊어 Format대로 반환

    public int string2Int(String str)

    {

        int ret = 0;

        try{

            if(str == null || str.trim().length() == 0) return 0;

            int position = str.indexOf(".");

            switch(position){

                case -1:

                    ret = new Integer(str).intValue();

                    break;

                case 0:

                    ret = 0;

                    break;

                default:

                    ret = new Integer(str.substring(0,position)).intValue();

            }

        }

        catch(Exception e){

            return 0;

        }

        return ret;

    }

    // 그 주의 월요일부터 일요일을 제외하고 3일씩 끊어 Format대로 반환

    public long string2Long(String str)

    {

        long ret = 0;

        try{

            if(str == null || str.trim().length() == 0) return 0;

            int position = str.indexOf(".");

            switch(position){

                case -1:

                    ret = new Long(str).longValue();

                    break;

                case 0:

                    ret = 0;

                    break;

                default:

                    ret = new Long(str.substring(0,position)).longValue();

            }

        }

        catch(Exception e){

            return 0;

        }

        return ret;

    }

    // 현재 날짜에서 월을 더해 Format대로 반환

    public String subString(String str, int i)

    {

        if(str == null) return "";

        if(str.length() == 0 || str.trim().equals("")) return "";

        try

        {

            if(str.length() >= i) str = str.substring(0,i);

        }catch(Exception e)

        {

            str = "Data cut error : [" + str + "]";

        }

        return str;

    }

    // 현재 날짜에서 월을 더해 Format대로 반환

    public String toCode(String kscode)// throws UnsupportedEncodingException

    {

        if(kscode == null) return null;

        String data = "";

        try

        {

            data = new String(kscode.getBytes("KSC5601"), "8859_1");

        }

        catch(UnsupportedEncodingException e)

        {

            data = kscode + " : " + e.getMessage();

        }

        catch(Exception e)

        {

            data = kscode + " : " + e.getMessage();

        }

        return data;

    }

    // 원하는 크기로 자르기

    public String toHangul(String str) // throws
                                        // java.io.UnsupportedEncodingException

    {

        if(str == null) return null;

        String data = "";

        try

        {

            data = new String(str.getBytes("8859_1"), "KSC5601");

        }

        catch(UnsupportedEncodingException e)

        {

            data = str + " : " + e.getMessage();

        }

        catch(Exception e)

        {

            data = str + " : " + e.getMessage();

        }

        return data;

        // 한글인데 한번 더 쓰면 ??? 표시

    }

    public String toLen(int nums, int length)

    {

        String num = String.valueOf(nums).toString();

        int space = length - num.length();

        int i = 0;

        String buf = "";

        for(i = 0; i < space; i++)

            buf += "0";

        num = buf + num;

        return num;

    }

    // 파일사이즈 얻기

    /**
     * 
     * 이 메소드는 VisualAge에서 작성되었습니다.
     * 
     */

    // 02로
    public String toLen2(int nums)

    {

        String num = String.valueOf(nums).toString();

        if(num.length() == 1)

        num = "0" + num;

        return num;

    }

    // 파일사이즈 얻기

    /*--------------------------------------------------
     ' ==> &rsquo; 로 변환
     " ==> &rdquo; 로 변환
     --------------------------------------------------*/
    public String unReplaced(String str){
        String rtnStr = new String();

        int start_idx = 0;
        int end_idx = 0;
        int last_idx = 0;

        if(str == null || str.equals("NULL")){
            rtnStr = "";
        }else{
            // ' ==> &rsquo; 로 변환
            last_idx = str.length();
            while(-1 != str.indexOf('\'')){
                start_idx = 0;
                end_idx = str.indexOf('\'');

                rtnStr = rtnStr + str.substring(start_idx,end_idx);
                rtnStr = rtnStr + "";

                str = str.substring(end_idx + 1,last_idx);
                last_idx = str.length();
            }

            str = rtnStr + str;
            rtnStr = "";

            // " ==> &rdquo; 로 변환
            last_idx = str.length();
            while(-1 != str.indexOf('"')){
                start_idx = 0;
                end_idx = str.indexOf('"');

                rtnStr = rtnStr + str.substring(start_idx,end_idx);
                rtnStr = rtnStr + "";

                str = str.substring(end_idx + 1,last_idx);
                last_idx = str.length();
            }

            rtnStr = rtnStr + str;
        }

        return rtnStr;
    }

    public void writeLog(String string, PrintWriter log)

    {

        if(log == null) return;

        // writing operation

        log.println(string);

        // print() method never throws IOException,

        // so we should check error while printing

        if(log.checkError())

        {

            System.err.println("File write error.");

        }

    }

}
