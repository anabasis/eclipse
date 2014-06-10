package com.slug.logging;

/**
 * @(#) LoggingFormat.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.text.SimpleDateFormat;

/**
 * 기본적으로 제공되는 Log format에 관한 Class이다.
 * Log 사용시 출력하고자 하는 문자열 앞에 prefix가 붙고 뒤에
 * postfix가 붙는다. 기본으로 제시되는 모습은 '[일자][내용]'
 * 이고 일자는 'yyyyMMdd'의 prefix이고postfix는 없다.
 *
 */
public class LoggingFormat {

	String rtnStr = "";

    public LoggingFormat() { }
    /**
     * HttpServletRequest와 일반 Object를 구분하여 정보를
     * String으로 만들어 리턴한다
     *
     * @param o 정보를 얻고자 하는 Object.
     * @return object의 information.
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
     * Log내용의 뒤부분에 들어갈 String 값을 만들어 준다.
     * 현재는 아무내용도 return하지 않는다.
     *
     * @return "".
     */
    public String postfix() {
        return "";
    }
    /**
     * Log내용의 앞부분에 들어갈 String 값을 만들어 준다.
     * 현재 'yyyyMMdd'의 형식으로 되어 있다.
     *
     * @return 현재일자(yyyyMMdd).
     */
    public String prefix() {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("[ yyyyMMdd HH:mm:ss ]", java.util.Locale.KOREA);
        return dateFormat.format(new java.util.Date()) + ' ';
    }
    /**
     * Object정보를 현재일자와 함께 얻을경우 사용한다.
     *
     * @return 현재일자(yyyyMMdd)와 Object 정보.
     */
    public String prefix( Object o ) {
        return prefix() + getObjectInfo( o );
    }


    /**
     * Log내용의 뒤부분에 들어갈 String 값을 만들어 준다.
     * 현재는 아무내용도 return하지 않는다.
     *
     * @return "".
     */
    public String stdPostFix() {
		return "";
    }
    /**
     * Log내용의 앞부분에 들어갈 String 값을 만들어 준다.
     * 현재 'yyyyMMdd'의 형식으로 되어 있다.
     *
     * @return 현재일자(yyyyMMdd).
     */
    public String stdPreFix(String mode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("< yyyy. MM. dd. HH:mm:ss >", java.util.Locale.KOREA);

		String rtnStr = dateFormat.format(new java.util.Date()) + "< App Logging ["+mode+ "] > $  ";

        return rtnStr;
    }
    /**
     * Object정보를 현재일자와 함께 얻을경우 사용한다.
     *
     * @return 현재일자(yyyyMMdd)와 Object 정보.
     */
    public String stdPreFix( Object o,String mode ) {
        return stdPreFix(mode) + getObjectInfo( o );
    }
}
