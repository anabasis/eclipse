package com.slug.exception;

/**
 * @(#) PException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Project���� ����ϴ� ��� Exception�� �ֻ��� Ŭ�����̴�.
 * �̴� Exception chaining�� �ʿ��� �⺻���� �۾��� �����Ѵ�.
 *
 */
public class PException extends Exception {
    private java.lang.Throwable rootCause;
    private boolean isFirst = true;

    /**
     * Ư���� �޽��� ���� PException�� ���Ѵ�.
     */
    public PException() {
        super();
    }
    /**
     * Ư���� �޽����� ���� PException�� ���Ѵ�.
     * @param s �޽���
     */
    public PException(String s) {
        super(s);
    }
    /**
     * Ư���� �޽����� Throwable�� ���� PException�� ���Ѵ�.
     * @param s �޽���
     * @param rootCause exception chaining�� �ʿ��� Throwable
     */
    public PException(String message, Throwable rootCause) {
        super(message);
        this.rootCause = rootCause;
        this.isFirst = false;
    }
    /**
     * Ư���� Throwable�� ���� PException�� ���Ѵ�.
     * @param rootCause exception chaining�� �ʿ��� Throwable
     */
    public PException(Throwable rootCause) {
        this();
        this.rootCause = rootCause;
        this.isFirst = false;
    }
    /**
     * PException�� rootCause�� �����ϴ� �޼ҵ��̴�.
     * @return Throwable�� rootCause�� ����
     */
    public Throwable getRootCause() {
        return rootCause;
    }
    /**
     * Stack Trace�� string���·� �����ϴ� �޼ҵ��̴�.
     * @return Stack Trace�� string ���·� ����
     */
    public String getStackTraceString() {
        StringWriter s = new StringWriter();
        printStackTrace( new PrintWriter(s) );
        return s.toString();
    }
    /**
     * Throwable�� exception chaining�� �޽����� stack trace��
     * ������ error output stream�� System.err�� ����Ѵ�.
     * @see java.lang.Throwable
     */
    public void printStackTrace() {
        printStackTrace( System.err );
    }
    /**
     * Throwable�� exception chaining�� �޽����� stack trace��
     * ������ error output stream�� PrintStream�� ����Ѵ�.
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
     * Throwable�� exception chaining�� �޽����� stack trace��
     * ������ error output stream�� PrintWriter�� ����Ѵ�.
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
        SimpleDateFormat dateFormat = new SimpleDateFormat ("<yyyy-MM-dd aaa hh�� mm�� ss�� >", java.util.Locale.KOREA);
		String prfixStr = dateFormat.format(new java.util.Date()) + " ";
        return prfixStr;
    }
}
