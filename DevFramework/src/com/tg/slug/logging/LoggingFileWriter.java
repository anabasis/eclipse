package com.tg.slug.logging;

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

import com.tg.slug.config.AppConfigManager;
import com.tg.slug.io.NullWriter;

/**
 * log ���ڿ��� file�� ���� print�ϴ� ������ �Ѵ�.
 * PrintWriter�� ��� �޾� println() method�鸸 overrinding�Ͽ���.
 * ���� �ý��� ���ڰ� �ٲ�� '20001103sys.log' ���� ����� ���ο�
 * log file�� �����Ѵ�.
 *
 */
public class LoggingFileWriter extends PrintWriter {
    /**
     * Log Functionality Mode.
     */
    private int mode;

    /**
     * ���� ���õǾ� �ִ� ���� String ��ü.
     */
    private String today;
    SimpleDateFormat dateFormat;

    /**
     * LoggingWriter�� PrintWriter�� ��ӹޱ� ������ autoflush�� ����ϰ� �ȴ�. <br>
     * autoFlush�� true�� ������ �ϸ� ������ ������ �������� ������ ���� �ٷ�<br>
     * ����ϰԵȴ�. ���� ������ false�� �Ѵٸ� ���۰� �� ����� ������ <br>
     * ������� �ʴ´�. �׷��Ƿ�, LoggingWriter constructor������ autoFlush������<br>
     * super()�� ȣ���ϰ� mode, dateFormat, Writer�� �����Ѵ�.<br>
     *
     * @param mode Log Functionality Mode.
     * @param autoFlush autoFlush.
     * @throws LoggingException Log error�� �߻��� ���.
     */
    LoggingFileWriter(int mode, boolean autoFlush) throws LoggingException {
        super( new NullWriter(), autoFlush );
        this.mode = mode;
        dateFormat = new SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
        out = getWriter();
    }
    /**
     * ���� ���õ� ����(today)�� �ý������������� �Ǵ��Ͽ� ���ڰ�
     * ����Ǿ�ٸ� '20001103sys.log' ���� ����� ���ο� log file��
     * write�ϱ����� Writer�� out��ü�� ���Ӱ� ����.
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
     * Writer��ü�� �����ϸ� flush���ش�.
     */
    public void finalize() {
        try {
            if ( out != null ) {
                super.flush();
            }
        } catch(Exception e) {}
    }
    /**
     * Log������ �ʱ�ȭ �����ְ� BufferdWriter�� �����Ѵ�.
     *
     * @return BufferdWriter.
     * @throws LoggingException Log error�� �߻��� ���.
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
    // print method�� parent class���� �״�� ����Ͽ���.
    // jdf������ ó���� �Ͽ����� mutithreadȯ���� ���ϸ� ���ǹ��ϴ�.

    /**
     * ���ڿ�(char[])�� print�ϰ� line�� ��ģ��.
     *
     * @param x[] ����ϰ��� �ϴ� ���ڿ�.
     */
    public void println(char x[]) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * ����(char)�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� ����.
     */
    public void println(char x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * double type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� double.
     */
    public void println(double x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * float type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� float.
     */
    public void println(float x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * int type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� int.
     */
    public void println(int x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * long type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� long.
     */
    public void println(long x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * Object type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� Object
     */
    public void println(Object x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * Object�� String�� print�ϰ� line�� ��ģ��.
     *
     * @param p ����ϰ��� �ϴ� ��� Object.
     * @param x ����ϰ��� �ϴ� String.
     */
    public void println(Object p, String x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * String type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� String.
     */
    public void println(String x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
    }
    /**
     * boolean type�� print�ϰ� line�� ��ģ��.
     *
     * @param x ����ϰ��� �ϴ� boolean.
     */
    public void println(boolean x) {
        checkDate();
        super.println(Logging.format.prefix()+x+Logging.format.postfix());
        
      
    }
}
