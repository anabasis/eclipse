package com.slug.exception;

/**
 * @(#) PException.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Project에서 사용하는 모든 Exception의 최상위 클래스이다.
 * 이는 Exception chaining에 필요한 기본적인 작업을 수행한다.
 *
 */
public class PException extends Exception {
    private java.lang.Throwable rootCause;
    private boolean isFirst = true;

    /**
     * 특정한 메시지 없이 PException을 생성한다.
     */
    public PException() {
        super();
    }
    /**
     * 특정한 메시지를 갖는 PException을 생성한다.
     * @param s 메시지
     */
    public PException(String s) {
        super(s);
    }
    /**
     * 특정한 메시지와 Throwable을 갖는 PException을 생성한다.
     * @param s 메시지
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public PException(String message, Throwable rootCause) {
        super(message);
        this.rootCause = rootCause;
        this.isFirst = false;
    }
    /**
     * 특정한 Throwable을 갖는 PException을 생성한다.
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public PException(Throwable rootCause) {
        this();
        this.rootCause = rootCause;
        this.isFirst = false;
    }
    /**
     * PException의 rootCause를 리턴하는 메소드이다.
     * @return Throwable인 rootCause를 리턴
     */
    public Throwable getRootCause() {
        return rootCause;
    }
    /**
     * Stack Trace를 string형태로 리턴하는 메소드이다.
     * @return Stack Trace를 string 형태로 리턴
     */
    public String getStackTraceString() {
        StringWriter s = new StringWriter();
        printStackTrace( new PrintWriter(s) );
        return s.toString();
    }
    /**
     * Throwable과 exception chaining한 메시지의 stack trace를
     * 지정한 error output stream인 System.err에 출력한다.
     * @see java.lang.Throwable
     */
    public void printStackTrace() {
        printStackTrace( System.err );
    }
    /**
     * Throwable과 exception chaining한 메시지의 stack trace를
     * 지정한 error output stream인 PrintStream에 출력한다.
     * @param PrintStream
     * @see java.lang.Throwable
     */
    public void printStackTrace(java.io.PrintStream s) {
        synchronized (s) {
            if (isFirst || !(rootCause instanceof PException)  ) {
                s.print(prefix()+"<Application Error>  < Catching PException > ");
            }

			super.printStackTrace(s);
            if (rootCause != null) {
                rootCause.printStackTrace(s);
            }
//			s.println(">");
        }
    }
    /**
     * Throwable과 exception chaining한 메시지의 stack trace를
     * 지정한 error output stream인 PrintWriter에 출력한다.
     * @param s PrintWriter
     * @see java.lang.Throwable
     */
    public void printStackTrace(java.io.PrintWriter s) {
        synchronized (s) {
            if (isFirst || !(rootCause instanceof PException)  ) {
                s.print(prefix()+"<Application Error>  < Catching PException > ");
            }

			super.printStackTrace(s);
            if (rootCause != null) {
                rootCause.printStackTrace(s);
            }
			s.println(">");
        }
    }
	public String prefix() {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("<yyyy-MM-dd aaa hh시 mm분 ss초 >", java.util.Locale.KOREA);
		String prfixStr = dateFormat.format(new java.util.Date()) + " ";
        return prfixStr;
    }
}
