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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import com.slug.config.AppConfigManager;
import com.slug.io.NullWriter;

/**
 * log 문자열을 file에 직접 print하는 역할을 한다.
 * PrintWriter를 상속 받아 println() method들만 overrinding하였다.
 * 또한 시스템 일자가 바뀌면 '20001103sys.log' 같은 형식의 새로운
 * log file로 변경한다.
 *
 */
public class LoggingFileWriter extends PrintWriter {
    /**
     * Log Functionality Mode.
     */
    private int mode;

    /**
     * 현재 셋팅되어 있는 일자 String 객체.
     */
    private String today;
    SimpleDateFormat dateFormat;

    /**
     * LoggingWriter는 PrintWriter를 상속받기 때문에 autoflush를 사용하게 된다. <br>
     * autoFlush를 true로 설정을 하면 라인이 끝나는 시점에서 버퍼의 것을 바로<br>
     * 출력하게된다. 만약 설정을 false로 한다면 버퍼가 다 찰때까지 내용을 <br>
     * 출력하지 않는다. 그러므로, LoggingWriter constructor에서는 autoFlush값으로<br>
     * super()를 호출하고 mode, dateFormat, Writer를 셋팅한다.<br>
     *
     * @param mode Log Functionality Mode.
     * @param autoFlush autoFlush.
     * @throws LoggingException Log error가 발생할 경우.
     */
    LoggingFileWriter(int mode, boolean autoFlush) throws LoggingException {
        super( new NullWriter(), autoFlush );
        this.mode = mode;
        dateFormat = new SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
        out = getWriter();
    }
    /**
     * 현재 셋팅된 일자(today)가 시스템일자인지를 판단하여 일자가
     * 변경되었다면 '20001103sys.log' 같은 형식의 새로운 log file로
     * write하기위해 Writer의 out객체를 새롭게 얻어낸다.
     */
    private void checkDate() {
        String day = dateFormat.format(new java.util.Date());
        if ( day.equals(today) ) {
            return;
        }
        synchronized (lock) {
            try {
                out.flush();
                out.close();
                out = getWriter();
            } catch(Exception e) {
                e.printStackTrace();
                out = new OutputStreamWriter(System.out);
                super.println("Can't open log file : " + e.getMessage());
                super.println("Log will be printed into System.out");
            }
        }
    }
    /**
     * Writer객체가 존재하면 flush해준다.
     */
    public void finalize() {
        try {
            if ( out != null ) {
                super.flush();
            }
        } catch(Exception e) {}
    }
    /**
     * Log파일을 초기화 시켜주고 BufferdWriter를 리턴한다.
     *
     * @return BufferdWriter.
     * @throws LoggingException Log error가 발생할 경우.
     */
    private Writer getWriter() throws LoggingException{
        today = dateFormat.format(new java.util.Date());

        try {
        	AppConfigManager conf = AppConfigManager.getInstance();
            String directory = conf.get("sys.log.dir");
            String logname = today +"["+ Logging.LOG_FILE_EXT[mode] + "].log";
            File file = new java.io.File(directory, logname);
            String filename = file.getAbsolutePath();
            FileWriter fw =  new FileWriter(filename, true);// APPEND MODE
            return new BufferedWriter(fw);
        } catch(Exception e) {
            throw new LoggingException( e );
        }
    }
    // print method는 parent class것을 그대로 사용하였다.
    // jdf에서는 처리를 하였으나 mutithread환경을 생각하면 무의미하다.

    /**
     * 문자열(char[])을 print하고 line을 마친다.
     *
     * @param x[] 출력하고자 하는 문자열.
     */
    public void println(char x[]) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * 문자(char)를 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 문자.
     */
    public void println(char x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * double type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 double.
     */
    public void println(double x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * float type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 float.
     */
    public void println(float x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * int type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 int.
     */
    public void println(int x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * long type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 long.
     */
    public void println(long x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * Object type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 Object
     */
    public void println(Object x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * Object와 String을 print하고 line을 마친다.
     *
     * @param p 출력하고자 하는 대상 Object.
     * @param x 출력하고자 하는 String.
     */
    public void println(Object p, String x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * String type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 String.
     */
    public void println(String x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * boolean type을 print하고 line을 마친다.
     *
     * @param x 출력하고자 하는 boolean.
     */
    public void println(boolean x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
        
      
    }
}
