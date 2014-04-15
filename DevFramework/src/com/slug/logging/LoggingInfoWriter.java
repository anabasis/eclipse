package com.slug.logging;

/**
 * @(#) LoggingWriter.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.*;


/**
 * log ���ڿ��� file�� ���� print�ϴ� ������ �Ѵ�.
 * PrintWriter�� ��� �޾� println() method�鸸 overrinding�Ͽ���.
 * ���� �ý��� ���ڰ� �ٲ�� '20001103sys.log' ���� ����� ���ο�
 * log file�� �����Ѵ�.
 *
 */
public class LoggingInfoWriter extends PrintWriter {
    /**
     * Log Functionality Mode.
     */
    //private int mode;

    /**
     * LoggingFileWriter�� PrintWriter�� ��ӹޱ� ������ autoflush�� ����ϰ� �ȴ�. <br>
     * autoFlush�� true�� ������ �ϸ� ������ ������ �������� ������ ���� �ٷ�<br>
     * ����ϰԵȴ�. ���� ������ false�� �Ѵٸ� ���۰� �� ����� ������ <br>
     * ������� �ʴ´�. �׷��Ƿ�, LoggingWriter constructor������ autoFlush������<br>
     * super()�� ȣ���ϰ� mode, dateFormat, Writer�� �����Ѵ�.<br>
     *
     * @param mode Log Functionality Mode.
     * @param autoFlush autoFlush.
     * @throws LoggingException Log error�� �߻��� ���.
     */
    LoggingInfoWriter(PrintStream ps, boolean autoFlush) throws LoggingException {
        super( ps, autoFlush );
    }

    // print method�� parent class���� �״�� ����Ͽ���.
    // jdf������ ó���� �Ͽ����� mutithreadȯ���� ���ϸ� ���ǹ��ϴ�.

    /**
     * ���ڿ�(char[])�� print�ϰ� line�� ��ģ��.
     *
     * @param x[] ����ϰ��� �ϴ� ���ڿ�.
     */
    public void println(char x[]) {

        
         super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * ����(char)�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� ����.
     */
    public void println(char x) {

      super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * double type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� double.
     */
    public void println(double x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * float type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� float.
     */
    public void println(float x) {

      super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * int type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� int.
     */
    public void println(int x) {

        super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * long type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� long.
     */
    public void println(long x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * Object type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� Object
     */
    public void println(Object x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * Object�� String�� print�ϰ� line�� ��ģ��.
     *
     * @param p ����ϰ��� �ϴ� ��� Object.
     * @param x ����ϰ��� �ϴ� String.
     */
    public void println(Object p, String x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * String type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� String.
     */
    public void println(String x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }
    /**
     * boolean type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� boolean.
     */
    public void println(boolean x) {

       super.println( Logging.format.stdPreFix("INF")+x+Logging.format.stdPostFix() );
    }

}
