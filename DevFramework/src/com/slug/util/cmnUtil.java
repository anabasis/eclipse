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
     * ����� ȭ�鿡 �����ֱ� ���Ͽ� ���� ����� ����� ������ش�.
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
     * ����� ȭ�鿡 �����ֱ� ���Ͽ� ���� ����� ����� ������ش�.
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

    // /�Ͽ� String��ȯ

    // multiply : * ��,0�̸� /��

    // underNum : �Ҽ��� �Ʒ���

    public String         savePath = "/tmp";

    // /�Ͽ� String��ȯ

    // multiply : * ��,0�̸� /��

    // underNum : �Ҽ��� �Ʒ���

    public cmnUtil(){
    }

    // /�Ͽ� String��ȯ

    // multiply : * ��,0�̸� /��

    // underNum : �Ҽ��� �Ʒ���

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

    // ���๮�ڴ�� <br> ���̱�

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

            {// data���̰� �䱸 ���̺��� Ŭ��

                if(Data.toLowerCase().indexOf("<br>\r") == -1)

                {// <br>\r�� ������

                    int cursor = 0;

                    int engCount = 0;

                    for(int i = 0; i < len; i++)

                    {

                        String addStr = Data.substring(i,i + 1);

                        // ���ϴ� ���̰� ������

                        if(cursor == reqtColumn)

                        {

                            addStr += "<br>\r";

                            cursor = 0;

                        }

                        cursor++; // ���� ���� ù������ 1���� ������

                        // 30���� ������ �ѱ��� 30��,������ 60���� ��������

                        char c = addStr.charAt(0);

                        if((int)Character.toUpperCase(c) >= 65
                                && (int)Character.toUpperCase(c) <= 90)

                        {

                            engCount++;// ����2���� �Ѱ��� �������

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

                {// <br>\r�� ������

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

                        {// <br>�� ������

                            addStr = currStr;

                            i += 4;

                            cursor = 0;// cursor���߿� <br>\r������ 0���� �ʱ�ȭ

                        }

                        else

                        {// <br>�� ������

                            addStr = Data.substring(i,i + 1);

                            // ���ϴ� ���̰� ������

                            if(cursor == reqtColumn)

                            {

                                addStr += "<br>\r";

                                cursor = 0;

                            }

                            cursor++; // ���� ���� ù������ 1���� ������

                            // 30���� ������ �ѱ��� 30��,������ 60���� ��������

                            char c = addStr.charAt(0);

                            if((int)Character.toUpperCase(c) >= 65
                                    && (int)Character.toUpperCase(c) <= 90)

                            {

                                engCount++;// ����2���� �Ѱ��� �������

                                if(engCount == 2)

                                {

                                    cursor--;

                                    engCount = 0;

                                }

                            }

                        }// - br�� �ְ� ������

                        newStr += addStr;

                    }// for

                } // br�� �ְ� ������

            }

            else

            {// data���̰� �䱸 ���̺��� ������

                newStr = Data;

            }

        }

        catch(java.lang.Exception ex)

        {

            return "changeline error :" + ex.getMessage();

        }

        return newStr;

    }

    // reqtColumn�� �ѱ۱��� (30�̸� ������ 60��

    // \r \n: ���� 1 ,<br>���� 4

    // ���ϴ� ���̿��� <br>�� �߰��� ���� �ٲٱ�

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

    // blank Check�Ͽ� &nbsp; ��ȯ

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

    // NullCheck�Ͽ� ��� ��ȯ

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

    // NullCheck�Ͽ� - ��ȯ

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // Null Check�Ͽ� &nbsp; ��ȯ
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

    // String �迭�� �Է¹޾� NullCheck�Ͽ� ���ϴ� �� ��ȯ

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

    // NullCheck�Ͽ� ���ϴ� �� ��ȯ

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

    // NullCheck�Ͽ� "0" ��ȯ

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // null�̳� ����� 0�� setting
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

        // Html textarea���� enterġ�� \r\n

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

    // ���������

    // Html Textarea���� ���� ���� Sql�� in���� ���� �ֵ��� ����
    // _find_str : "\r\n" ,"_"
    public String conditionToTextArea(String data, String _find_str)

    {

        // Html textarea���� enterġ�� \r\n

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

    // ���������

    // /�Ͽ� String��ȯ
    // multiply : * ��,0�̸� /��
    // underNum : �Ҽ��� �Ʒ���
    public HttpServletResponse Convert(HttpServletResponse res){
        res.setContentType("application/msexcel; charset=EUC_KR");
        return res;
    }

    // ���������

    public void Convert2(HttpServletResponse res){
        res.setContentType("application/msexcel; charset=EUC_KR");
    }

    // �����̸� ������ �ٲٱ�

    public String deleteFile(String file)

    {

        return deleteFile(savePath,file);

    }

    // �����̸� �ٲٱ�

    public String deleteFile(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        return runDeleteFile(path_file);

    }

    // �����̸� �ٲٱ�

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

    // Ȯ���� ��ȯ

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

    // ���ϴ� ���ڷ� ���ϴ� ����(totalLength-dataũ��)��ŭ ä���ش�

    // ��ȯ ũ�� data length

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

    // ���ϴ� ���ڷ� ���ϴ� ����(totalLength)��ŭ ä���ش�

    // ��ȯ ũ�� data length +totalLength

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // Ȯ���ڿ� �� �̹��� �Ѹ���
    public String fileImage(String attc_file, String imgPath)

    {

        // Ȯ����

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

    // �������� : ���ڿ�+���ϴ� �����̽�ũ��

    // ������ ���� : ���ϴ� �����̽�ũ�� + ���ڿ�

    // �߰� ���� : ���ϴ� �����̽�ũ��/2 + ���ڿ� + ���ϴ� �����̽�ũ��/2

    // ��ȯ ũ�� SpaceNum

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

    // �ݿø�

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

            // ������ �����Ͱ� �����ְ����ϴ� ���̺��� Ŭ ��� �����ְ����ϴ� ���̸�ŭ �߶��ش�.

            if(toCode(Data).length() > SpaceNum)

            {

                // �߸��� �κп� �ѱ��� ���� �� �÷� ��ü�� ����

                // �׷��� �ѱ��� ��� �� �� �ڸ�

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

            // �������� ����

            if(Align.toUpperCase().equals("LEFT"))

            {

                ReturnValue = Data + LeftSpace + RightSpace;

            }

            // ���������� ����

            else if(Align.toUpperCase().equals("RIGHT"))

            {

                ReturnValue = LeftSpace + RightSpace + Data;

            }

            // ����� ����

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

    // �ݿø�

    public String formatNum(double num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // �ݿø�

    public String formatNum(int num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // �ݿø�

    public String formatNum(long num, int underNum)

    {

        String formats = formatReturn(underNum);

        DecimalFormat df = new DecimalFormat(formats);

        return df.format(num);

    }

    // format��ȯ

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

        else formats = "###,###,###,##0." + setChar(underNum,"0");// 0�� ������ŭ
                                                                    // ä��� ,#:0��
                                                                    // �ѹ�

        return formats;

    }

    // String ����� 8�ڸ�or10�ڸ� �Է��� ���Ͽ� �����Ͽ� �����8�ڸ� ���

    // ����� �Է��� ���� �� ������ ������ ��¥ ���ϱ�
    public String getBeforeMonthK(int mm){
        GregorianCalendar cal = new GregorianCalendar();

        int beforeYear = cal.get(Calendar.YEAR);
        int beforeMonth = (cal.get(Calendar.MONTH) + 1) - mm;

        if(beforeMonth < 1){
            beforeYear = cal.get(Calendar.YEAR) - 1;
            beforeMonth = 12 + ((cal.get(Calendar.MONTH) + 1) - 8);
        }

        return toLen(beforeYear,4) + "�� " + beforeMonth + "��";

    }

    // ������Է��� �Ͽ� �����Ͽ� ����� ���

    // ����� �Է��� ���� �� ������ ������ ��¥ ���ϱ�
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

    // String ����� 8�ڸ�or10�ڸ� �Է��� �Ͽ� �����Ͽ� �����8�ڸ� ���

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // ������Է��� ���Ͽ� �����Ͽ� ����� ���
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
     * �� ������ ���Ѿ� �� ��� �Ʒ��� ������ ��Ȯ�� ���̰� �ִ�
     * 
     * �� ���� ��¥�� 2000�� 12�� 30���� ���
     * 
     * getChangeMonth(getCurrDate(),2) -> 2001�� 3�� 2��
     * 
     * getChangeMonth(getYYYYMM()+"01",2) -> 2001�� 2�� 1��
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

        {// 20001212�Է�

            // �� ,�� ,��

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12�Է�

            // �� ,�� ,��

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

    // YYYYMMDD 20000427 ��ȯ

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

        {// 20001212�Է�

            // �� ,�� ,��

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12�Է�

            // �� ,�� ,��

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

    // YYYYMMDDHHmm 200004272109 ��ȯ

    // ������Է��� �� �����Ͽ� ����� ���
    public String getChangeMonth(int year, int month, int day, int addmonth)

    {

        GregorianCalendar cal = new GregorianCalendar();

        cal.set(year,month - 1 + addmonth,day);

        String stryear = String.valueOf(cal.get(Calendar.YEAR)).toString();

        String strmonth = toLen2(cal.get(Calendar.MONTH) + 1);

        String strday = toLen2(cal.get(Calendar.DATE));

        return stryear + strmonth + strday;

    }

    // YYYYMMDDHHmm 2000��04��27��21��09�� ��ȯ

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // String ����� 8�ڸ�or10�ڸ� �Է��� �� �����Ͽ� �����8�ڸ� ���
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

        {// 20001212�Է�

            // �� ,�� ,��

            year = Integer.valueOf(date.substring(0,4)).intValue();

            month = Integer.valueOf(date.substring(4,6)).intValue();

            day = Integer.valueOf(date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12�Է�

            // �� ,�� ,��

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
     * �Է� ������ ����
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

    // ���� �� ��ȯ

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

        date.append(cal.get(1) + "��");

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1 + "��");

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5) + "��");

        if(cal.get(11) < 10) date.append('0');

        date.append(cal.get(11) + "��");

        if(cal.get(12) < 10) date.append('0');

        date.append(cal.get(12) + "��");

        return date.toString();

    }

    public String getCurrDay(){

        GregorianCalendar cal = new GregorianCalendar();

        String day = String.valueOf(cal.get(Calendar.DATE)).toString();

        if(day.length() == 1) day = "0" + day;

        return day;

    }

    // ���� ��������

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // ������ ���� ��������
    public String getCurrDayOfWeek()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int day = cal.get(Calendar.DAY_OF_WEEK);

        String days = "";

        if(day == 1)
            days = "��";

        else if(day == 2)
            days = "��";

        else if(day == 3)
            days = "ȭ";

        else if(day == 4)
            days = "��";

        else if(day == 5)
            days = "��";

        else if(day == 6)
            days = "��";

        else if(day == 7) days = "��";

        return days;

    }

    // ��:��:��

    public String getCurrMonth()

    {

        GregorianCalendar cal = new GregorianCalendar();

        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);

        if(month.length() == 1) month = "0" + month;

        return month;

    }

    // ��:��

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

    // �⵵ ��ȯ

    public String getCurrTimeNoSec()

    {

        GregorianCalendar cal = new GregorianCalendar();

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        int minute = cal.get(Calendar.MINUTE);

        int sec = cal.get(Calendar.SECOND);

        String time = String.valueOf(toLen2(hour)) + ":" + String.valueOf(toLen2(minute));

        return time;

    }

    // YYYYMMDDHHmmss 20000427210948 ��ȯ

    public String getCurrYear()

    {

        GregorianCalendar cal = new GregorianCalendar();

        String year = String.valueOf(cal.get(Calendar.YEAR));

        return year;

    }

    // �������� ���������� yyyymmdd��ȯ

    public String getDateTimeSec()

    {

        GregorianCalendar cal = new GregorianCalendar();

        StringBuffer date = new StringBuffer();

        // ��

        date.append(cal.get(1));

        // ��

        if(cal.get(2) < 9) date.append('0');

        date.append(cal.get(2) + 1);

        // ��

        if(cal.get(5) < 10) date.append('0');

        date.append(cal.get(5));

        // ��

        if(cal.get(11) < 10) date.append('0');

        date.append(cal.get(11));

        // ��

        if(cal.get(12) < 10) date.append('0');

        date.append(cal.get(12));

        // ��

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
    
    // ����� ���������� yyyymmdd��ȯ

    public String getEndDate()

    {

        int year = Integer.valueOf(getCurrYear()).intValue();

        int month = Integer.valueOf(getCurrMonth()).intValue();

        return toLen(year,4) + toLen2(month) + getEndOfMonthDay(year,month);

    }

    // ����� ���������� yyyymmdd��ȯ

    public String getEndDate(int year, int month)

    {

        return toLen(year,4) + toLen2(month) + toLen2(getEndOfMonthDay(year,month));

    }

    // �������� ���ϱ�

    public String getEndDate(String date)

    {

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        return toLen(year,4) + toLen2(month) + toLen2(getEndOfMonthDay(year,month));

    }

    // �������� ���ϱ�

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

    // 2000��04��22�� 12:13:23��ȯ

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

    // ����ð��� ���ϴ� �������

    // format "YYYYMMDD hh:mm:ss

    // format "YYYYMMDDhhmmssms

    // format "YYYY�� MM�� DD�� hh-mm-ss ���

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

    // ������� ���ϴ� �������

    public long getFileSize(String path, String fileName)

    {

        String path_file = "";

        if(path.endsWith(System.getProperty("file.separator")))

            path_file = path + fileName;

        else

        path_file = path + System.getProperty("file.separator") + fileName;

        return getFileSize(path_file);

    }

    // ����� 8�ڸ�or10�ڸ� �Է¹޾� �� ���� �Ϸ� ǥ��

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

        date.append("��");

        date.append(toLen2(month));

        date.append("��");

        date.append(toLen2(day));

        date.append("��");

        date.append(" ");

        date.append(toLen2(hour));

        date.append(":");

        date.append(toLen2(minute));

        date.append(":");

        date.append(toLen2(sec));

        return date.toString();

    }

    // ����� 8�ڸ��Ǵ� 6�ڸ����� �Է¹޾� �� ���� ���� ǥ��

    // ��������� 199903���� 199904�� ���� 1���������� ���⼭�� 2�����

    // ����� 8�ڸ��Ǵ� 6�ڸ����� �Է¹޾� �� ���� ���� ǥ��

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

    // ��� ���� ���� ��/��/�� �̸� üũ ���

    // �Է��ѳ��� ���� ��������

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

                    newDate.insert(8,"��");

                    newDate.insert(6,"��");

                    newDate.insert(4,"��");

                }else{

                    newDate.insert(6,flag);

                    newDate.insert(4,flag);

                }

            }else if(date.length() == 6)

            {

                if(flag.toUpperCase().equals("KOR"))

                {

                    newDate.insert(6,"��");

                    newDate.insert(4,"��");

                }else{

                    newDate.insert(4,flag);

                }

            }

            else if(date.length() == 4)

            {

                if(flag.toUpperCase().equals("KOR"))

                {

                    newDate.insert(4,"��");

                    newDate.insert(2,"��");

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

    // 200003��ȯ

    public String getHalfOfWeek(int year, int month, int day, int destday, String format)

    {

        GregorianCalendar calendar = new GregorianCalendar();

        calendar.set(year,month - 1,day);

        // (1-��,2-��,...7-��)

        int dayofweek = calendar.get(calendar.DAY_OF_WEEK);

        // ������ ������� �޾ƿ���

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

    // nareadme.htm�Է¹޾� 20000614090248_nareadme.htm��ȯ

    public String getHalfOfWeek(String date, int destday, String format)

    {

        // �Է¹��� ���� ���� ��ȯ

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

        // arg - date���

        int year = Integer.valueOf(date.substring(0,4)).intValue();

        int month = Integer.valueOf(date.substring(4,6)).intValue();

        int day = Integer.valueOf(date.substring(6,8)).intValue();

        format = getNextMonth(year,month,day,destmonth,format);

        return format;

    }

    // file����� output��Ʈ�� �����

    public long getSubtractDay(String from_date, String to_date)

    {

        long diff_day = 0; // ���� �Ϸ� ǥ��

        // �� ,�� ,��

        int from_year = 0;

        int from_month = 0;

        int from_day = 0;

        // �� ,�� ,��

        int to_year = 0;

        int to_month = 0;

        int to_day = 0;

        int pointDash = from_date.lastIndexOf("-");

        int pointSlash = from_date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212�Է�

            // �� ,�� ,��

            from_year = Integer.valueOf(from_date.substring(0,4)).intValue();

            from_month = Integer.valueOf(from_date.substring(4,6)).intValue();

            from_day = Integer.valueOf(from_date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12�Է�

            // �� ,�� ,��

            from_year = Integer.valueOf(from_date.substring(0,4)).intValue();

            from_month = Integer.valueOf(from_date.substring(5,7)).intValue();

            from_day = Integer.valueOf(from_date.substring(8,10)).intValue();

        }

        pointDash = to_date.lastIndexOf("-");

        pointSlash = to_date.lastIndexOf("/");

        if(pointDash == -1 && pointSlash == -1)

        {// 20001212�Է�

            // �� ,�� ,��

            to_year = Integer.valueOf(to_date.substring(0,4)).intValue();

            to_month = Integer.valueOf(to_date.substring(4,6)).intValue();

            to_day = Integer.valueOf(to_date.substring(6,8)).intValue();

        }

        else

        {// 2000-12-12,2000/12/12�Է�

            // �� ,�� ,��

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

        // 1970.1.1������ ��

        long msec1 = startDate.getTime();

        long msec2 = endDate.getTime();

        long msec3 = 0;

        if(msec2 >= msec1)

        {

            msec3 = msec2 - msec1;

            // msec�� �ش��ϴ� ��¥ ���� ����Ѵ�

            diff_day = msec3 / (24 * 60 * 60 * 1000L);

        }

        else

        {

            msec3 = msec1 - msec2;

            // msec�� �ش��ϴ� ��¥ ���� ����Ѵ�

            diff_day = -(msec3 / (24 * 60 * 60 * 1000L));

        }

        // diff_day = msec3/(24 * 60 * 60 * 1000L);

        return diff_day;

    }

    // file����� output��Ʈ�� �����

    // appent�� true�� �� ���̱� ,false�� �����

    public int getSubtractMonth(String from_date, String toxx_date)

    throws IOException

    {

        // �ѷ��� ����� �÷�����

        int diff_month = 0;

        int END_MONTH = 12;

        int start_mon_of_from_date = 0;// ���۳���� ���ۿ�

        int start_mon_of_toxx_date = 0;// ������� ���ۿ�

        int loop_of_from_date = 0;// ���۳���� loop ��

        int loop_of_toxx_date = 0;// ������� loop ��

        int loop_of_year = 0;

        // ���� ����� ���۳�

        int year_of_from_date = Integer.valueOf(from_date.substring(0,4)).intValue();

        // �� ����� ����

        int year_of_toxx_date = Integer.valueOf(toxx_date.substring(0,4)).intValue();

        int pointDash = 0;

        int pointSlash = 0;

        if(year_of_from_date == year_of_toxx_date)

        {// ���� ��� �� ���� ������

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

            // �÷��� ���� = toxx_date �� from_date �÷� ���� +1

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

                // ���۴�

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

                // ���۴�

                start_mon_of_from_date = Integer.valueOf(toxx_date.substring(4,6)).intValue();

                start_mon_of_toxx_date = 1;

                //

                loop_of_from_date = END_MONTH;

                loop_of_toxx_date = Integer.valueOf(from_date.substring(4,6)).intValue();

            }

            else

            {

                // ���۴�

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
            days = "��";

        else if(day == 2)
            days = "��";

        else if(day == 3)
            days = "ȭ";

        else if(day == 4)
            days = "��";

        else if(day == 5)
            days = "��";

        else if(day == 6)
            days = "��";

        else if(day == 7) days = "��";

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

    // �����б�

    // ���� readFile2("c:\\","autoexec.bat")

    // ���� readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // �Ұ��� readFile2("\\","Ws_ftp.log")

    // �Ұ��� readFile2("/","Ws_ftp.log")

    public String makeFileName(String srcfile)

    {

        StringBuffer filename = new StringBuffer();

        GregorianCalendar cal = new GregorianCalendar();

        // ��

        filename.append(cal.get(1));

        // ��

        if(cal.get(2) < 9) filename.append('0');

        filename.append(cal.get(2) + 1);

        // ��

        if(cal.get(5) < 10) filename.append('0');

        filename.append(cal.get(5));

        // ��

        if(cal.get(11) < 10) filename.append('0');

        filename.append(cal.get(11));

        // ��

        if(cal.get(12) < 10) filename.append('0');

        filename.append(cal.get(12));

        // ��

        if(cal.get(13) < 10) filename.append('0');

        filename.append(cal.get(13));

        filename.append('_');

        // �����̸��� ��鿡 _��

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

    // ���� �б�

    // ����

    // ���� readFile2("c:\\","autoexec.bat")

    // ���� readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // �Ұ��� readFile2("\\","Ws_ftp.log")

    // �Ұ��� readFile2("/","Ws_ftp.log")

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

    // �����б�

    // ���� readFile2("c:\\","autoexec.bat")

    // ���� readFile2("c:\\osdk\\sam001\\htdocs\\","Ws_ftp.log")

    // �Ұ��� readFile2("\\","Ws_ftp.log")

    // �Ұ��� readFile2("/","Ws_ftp.log")

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

    // ȭ�� �б�

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // file upload�� ���
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

    // ���ϴ� ���� ����

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // file upload�� ���
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

            {// file�±װ� �ƴҶ�

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

            {// file�±��϶�

                if(hash.get(name) == null)

                {// hash�� ��ٸ�

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

                {// hash�� �̹� �ִٸ�

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

            }// file�±����� �ƴ����϶�

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

    // ������ ����

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

    // makefilename�� ���� ���ϸ?������

    // 20000614090248_nareadme.htm��nareadme.htm

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

    // ���������� ��ġ�ϴ� ���ڿ� ��ü

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

    // Ư�����ڸ� ������ ��ȣ��

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

    // ������ ��ȣ�� Ư�����ڷ�

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
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // comma����
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

    // �Լ��߰� : ������ 2004.03.05 replaceStringAll2 ���� synchronized ����

    public String renameFile(String file, String new_file)

    throws Exception, IOException

    {

        return renameFile(savePath,file,new_file);

    }

    // ���ڿ��� ����ȯ(��ü���ڿ����� �������)

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
     * ��
     * 
     * �Ҽ� 2°�ڸ� �ݿø���
     * 
     * y= Math.floor(x*100+.5)/100;
     * 
     * 
     * 
     * �����ڸ��� �Ѿ�� 4.7*E07������ ����
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
         * ���๮�ڵ� ����
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
         * ���๮�ڵ� ����
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

    // byte������ �Ѿ�� data�� ������ ����

    // ������ ���� ����

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

    // ���ϴ� �����ŭ�� char�ֱ�

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // String Replace ��Ŵ - ���ڿ��� ��ȯ(��ü���ڿ����� �������)
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

    // comma����

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // ���ڿ��� ��ȯ(��ü���ڿ����� �������)
    public synchronized String replaceStringAll2(String Source, String FindText, String replace)

    {

        String rtnSource = "";
        String rtnCheck = "";

        int FindLength = Source.length();

        // ã�� ���� ����

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

    // comma����

    public String replaceStringAll3(String Source, String FindText, String replace)

    {

        String rtnSource = "";
        String rtnCheck = "";
        int FindLength = Source.length();
        // ã�� ���� ����
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

    // comma����

    public String replaceStringColor(String Source, String FindText, String Color,
                                     String replaceFlag)

    {

        String rtnSource = "";

        String rtnCheck = "";

        int FindLength = Source.length();

        int FindIndex = Source.indexOf(FindText);

        // ã�� ���� ����

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

            {// �빮���̰� �ҹ����̰�

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

            {// ��ġ�ϴ� �͸� - ONLY

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

    // comma����

    // �Ҽ� �ݿø�
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

    // jsp���� �������� out�����

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
     * ����
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

    // ���ϴ� �����ŭ�� space�ֱ�

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

    // �־��� ���ڿ�(str)�� double �� ���ڷ� ��ȯ�Ѵ�.

    // ��, ���� ���ڿ��μ� ��ȿ���� ���� ���ڿ��� 0 �� �����Ѵ�.

    public String runRenameFile(String path_file, String new_path_file)

    throws Exception, IOException

    {
        String mesg;

        try

        {

            File file = new File(path_file);

            File newfile = new File(new_path_file);

            // ���̸����� �̹� ���� �ϸ� ���� - �ǹ� ����

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

    // �־��� ���ڿ�(str)�� Integer �� ���ڷ� ��ȯ�Ѵ�.

    // ��, ���� ���ڿ��μ� ��ȿ���� ���� ���ڿ��� 0 �� �����Ѵ�.

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

    // �־��� ���ڿ�(str)�� long �� ���ڷ� ��ȯ�Ѵ�.

    // ��, ���� ���ڿ��μ� ��ȿ���� ���� ���ڿ��� 0 �� �����Ѵ�.

    public String setChar(int loopNum, String fillChar)

    {

        String rtn = "";

        for(int i = 0; i < loopNum; i++)

            rtn = rtn + fillChar;

        return rtn;

    }

    // �ѱ��� �Ϲ��ڵ�� �ٲٱ� ���ؼ�

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

    // �����ڵ带 �ѱ۷�

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

    // lengthũ�⿡ ���߾� 0�� �ٿ� ��ȯ

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

        // .���ϸ� ������ ����

        int strLen = 0;

        // -�� ������ �߶�´� ��ȯ�� �ٿ� �ش�

        int dash = data.indexOf('-');

        // - �� ��ġ�� �� ã������

        if(dash == -1)
            data = data;

        else data = data.substring(dash + 1);

        // .�� ������ ��ȯ�� �ٿ� �ش�

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

        // �Ҽ������� ���� ������ �������� �ٿ� �ش�.

        if(point > -1) commaStr += data.substring(point,data.length());

        // - �� ������ �տ� �ٿ��ش�

        if(dash > -1) commaStr = '-' + commaStr;

        return commaStr;

    }

    // ������ ���Ͽ� ����

    // Ŭ���̾�Ʈ�� ������ cache���
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

    // �� ���� ����Ϻ��� �Ͽ����� �����ϰ� 3�Ͼ� ���� Format��� ��ȯ

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

    // �� ���� ����Ϻ��� �Ͽ����� �����ϰ� 3�Ͼ� ���� Format��� ��ȯ

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

    // ���� ��¥���� ���� ���� Format��� ��ȯ

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

    // ���� ��¥���� ���� ���� Format��� ��ȯ

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

    // ���ϴ� ũ��� �ڸ���

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

        // �ѱ��ε� �ѹ� �� ���� ??? ǥ��

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

    // ���ϻ����� ���

    /**
     * 
     * �� �޼ҵ�� VisualAge���� �ۼ��Ǿ���ϴ�.
     * 
     */

    // 02��
    public String toLen2(int nums)

    {

        String num = String.valueOf(nums).toString();

        if(num.length() == 1)

        num = "0" + num;

        return num;

    }

    // ���ϻ����� ���

    /*--------------------------------------------------
     ' ==> &rsquo; �� ��ȯ
     " ==> &rdquo; �� ��ȯ
     --------------------------------------------------*/
    public String unReplaced(String str){
        String rtnStr = new String();

        int start_idx = 0;
        int end_idx = 0;
        int last_idx = 0;

        if(str == null || str.equals("NULL")){
            rtnStr = "";
        }else{
            // ' ==> &rsquo; �� ��ȯ
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

            // " ==> &rdquo; �� ��ȯ
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
