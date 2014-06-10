package com.slug.logging;

/**
 * @(#) LoggingWriter.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.*;


/**
 * log 문자열을 file에 직접 print하는 역할을 한다.
 * PrintWriter를 상속 받아 println() method들만 overrinding하였다.
 * 또한 시스템 일자가 바뀌면 '20001103sys.log' 같은 형식의 새로운
 * log file로 변경한다.
 *
 */
public class LoggingDebugWriter extends PrintWriter {
    /**
     * Log Functionality Mode.
     */
    //private int mode;

    /**
     * LoggingFileWriter는 PrintWriter를 상속받기 때문에 autoflush를 사용하게 된다. <br>
     * autoFlush를 true로 설정을 하면 라인이 끝나는 시점에서 버퍼의 것을 바로<br>
     * 출력하게된다. 만약 설정을 false로 한다면 버퍼가 다 찰때까지 내용을 <br>
     * 출력하지 않는다. 그러므로, LoggingWriter constructor에서는 autoFlush값으로<br>
     * super()를 호출하고 mode, dateFormat, Writer를 셋팅한다.<br>
     *
     * @param mode Log Functionality Mode.
     * @param autoFlush autoFlush.
     * @throws LoggingException Log error가 발생할 경우.
     */
    LoggingDebugWriter(PrintStream ps, boolean autoFlush) throws LoggingException {
        super( ps, autoFlush );
    }

    // print method는 parent class것을 그대로 사용하였다.
    // jdf에서는 처리를 하였으나 mutithread환경을 생각하면 무의미하다.

     /**
     * 문자열(char[])을 print하고 line을 마친다.
     *
     * @param x[] 출력하고자 하는 문자열.
     */
    public void println(char x[]) {

        
         super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * 문자(char)를 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 문자.
     */
    public void println(char x) {

      super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * double type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 double.
     */
    public void println(double x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * float type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 float.
     */
    public void println(float x) {

      super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * int type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 int.
     */
    public void println(int x) {

        super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * long type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 long.
     */
    public void println(long x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * Object type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 Object
     */
    public void println(Object x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * Object와 String을 print하고 line을 마친다.
     *
     * @param p 출력하고자 하는 대상 Object.
     * @param x 출력하고자 하는 String.
     */
    public void println(Object p, String x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * String type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 String.
     */
    public void println(String x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }
    /**
     * boolean type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 boolean.
     */
    public void println(boolean x) {

       super.println( Logging.format.stdPreFix("DBG")+x+Logging.format.stdPostFix() );
    }


}
