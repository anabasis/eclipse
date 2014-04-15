package com.slug.logging;

/**
 * @(#) LoggingFormat.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.text.SimpleDateFormat;

/**
 * �⺻������ ����Ǵ� Log format�� ���� Class�̴�.
 * Log ���� ����ϰ��� �ϴ� ���ڿ� �տ� prefix�� �ٰ� �ڿ�
 * postfix�� �ٴ´�. �⺻���� ���õǴ� ����� '[����][����]'
 * �̰� ���ڴ� 'yyyyMMdd'�� prefix�̰�postfix�� ���.
 *
 */
public class LoggingFormat {

	String rtnStr = "";

    public LoggingFormat() { }
    /**
     * HttpServletRequest�� �Ϲ� Object�� �����Ͽ� ������
     * String���� ����� �����Ѵ�
     *
     * @param o ������ ����� �ϴ� Object.
     * @return object�� information.
     */
    public static String getObjectInfo(Object o) {
        StringBuffer info = new StringBuffer();
        info.append('[');

        if ( o == null ) {
            info.append("null");
        } else if ( o instanceof javax.servlet.http.HttpServletRequest ) {
            javax.servlet.http.HttpServletRequest req = (javax.servlet.http.HttpServletRequest)o;
            String user = req.getRemoteUser();
            if ( user != null ) {
                info.append(user+",");
            }
            info.append(req.getRemoteAddr());
            info.append("," + req.getHeader("User-Agent"));
            info.append("," + req.getServerPort() + req.getServletPath() );
        } else {
            Class c = o.getClass();
            String fullname = c.getName();
            String name = null;
            int index = fullname.lastIndexOf('.');

            if ( index == -1 ) {
                name = fullname;
            } else {
                name = fullname.substring(index+1);
            }
            info.append(name);
            info.append( ":" + o.hashCode() );
        }

        info.append( "] " );

        return info.toString();
    }
    /**
     * Log������ �ںκп� �� String ���� ����� �ش�.
     * ����� �ƹ����뵵 return���� �ʴ´�.
     *
     * @return "".
     */
    public String postfix() {
        return "";
    }
    /**
     * Log������ �պκп� �� String ���� ����� �ش�.
     * ���� 'yyyyMMdd'�� ������� �Ǿ� �ִ�.
     *
     * @return ��������(yyyyMMdd).
     */
    public String prefix() {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("[ yyyyMMdd HH:mm:ss ]", java.util.Locale.KOREA);
        return dateFormat.format(new java.util.Date()) + ' ';
    }
    /**
     * Object������ �������ڿ� �Բ� ������� ����Ѵ�.
     *
     * @return ��������(yyyyMMdd)�� Object ����.
     */
    public String prefix( Object o ) {
        return prefix() + getObjectInfo( o );
    }


    /**
     * Log������ �ںκп� �� String ���� ����� �ش�.
     * ����� �ƹ����뵵 return���� �ʴ´�.
     *
     * @return "".
     */
    public String stdPostFix() {
		return "";
    }
    /**
     * Log������ �պκп� �� String ���� ����� �ش�.
     * ���� 'yyyyMMdd'�� ������� �Ǿ� �ִ�.
     *
     * @return ��������(yyyyMMdd).
     */
    public String stdPreFix(String mode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("< yyyy. MM. dd. HH:mm:ss >", java.util.Locale.KOREA);

		String rtnStr = dateFormat.format(new java.util.Date()) + "< App Logging ["+mode+ "] > $  ";

        return rtnStr;
    }
    /**
     * Object������ �������ڿ� �Բ� ������� ����Ѵ�.
     *
     * @return ��������(yyyyMMdd)�� Object ����.
     */
    public String stdPreFix( Object o,String mode ) {
        return stdPreFix(mode) + getObjectInfo( o );
    }
}
