package com.tg.slug.io;

/**
 * @(#) TeePrintWriter.java
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
 * �� ���� ����Ʈ�ϸ� �� ������ ����ϴ� Class�� Unix�� 'tee'���α׷���
 * java version�̴�.  TeePrintWriter�� ���� �� �ΰ��� PrintWriter��
 * parameter�� �ָ� �־��� �ΰ��� PrintWriter�� ���ÿ� ����ϴ� Class��.
 *
 */
public class TeePrintWriter extends PrintWriter {
    /**
     * PrintWriter 1.
     */
    private PrintWriter out1;

    /**
     * PrintWriter 2.
     */
    private PrintWriter out2;

    /**
     * PrintWriter�� ���� ��� constructor�� ��� ������ ���Ƿ�
     * super( new DNullWriter() )�� ȣ�����ְ� parameter�� �����Ѵ�.
     *
     * @param out1 PrintWriter 1.
     * @param out2 PrintWriter 2.
     */
    public TeePrintWriter(PrintWriter out1, PrintWriter out2 ) {
        super( new NullWriter() );
        this.out1 = out1;
        this.out2 = out2;
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� error state�� üũ�Ѵ�.
     *
     * @return PrintWriter 1�� error state Ȥ��
     * PrintWriter 2�� error state.
     */
    public boolean checkError() {
        return out1.checkError() || out2.checkError();
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� close()��Ų��.
     */
    public synchronized void close() {
        out1.close();
        out2.close();
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� flush�ϰ� close�Ѵ�.
     */
    public void finalize() {
        if ( out1 != null ) {
            out1.flush();
            out1.close();
            out1 = null;
        }
        if ( out2 != null ) {
            out2.flush();
            out2.close();
            out2 = null;
        }
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� ���� flush() ���ش�.
     */
    public synchronized void flush() {
        out1.flush();
        out2.flush();
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char�迭�� print�Ѵ�.
     *
     * @param s print�ϰ��� �ϴ� char�迭.
     */
    public synchronized void print(char[] s) {
        out1.print( s );
        out2.print( s );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char�� print�Ѵ�.
     *
     * @param c print�ϰ��� �ϴ� char.
     */
    public synchronized void print(char c) {
        out1.print( c );
        out2.print( c );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� double�� print�Ѵ�.
     *
     * @param d print�ϰ��� �ϴ� double.
     */
    public synchronized void print(double d) {
        out1.print( d );
        out2.print( d );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� float�� print�Ѵ�.
     *
     * @param f print�ϰ��� �ϴ� float.
     */
    public synchronized void print(float f) {
        out1.print( f );
        out2.print( f );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� int�� print�Ѵ�.
     *
     * @param i print�ϰ��� �ϴ� int.
     */
    public synchronized void print(int i) {
        out1.print( i );
        out2.print( i );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� long�� print�Ѵ�.
     *
     * @param l print�ϰ��� �ϴ� long.
     */
    public synchronized void print(long l) {
        out1.print( l );
        out2.print( l );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� Object�� print�Ѵ�.
     *
     * @param obj print�ϰ��� �ϴ� Object.
     */
    public synchronized void print(Object obj) {
        out1.print( obj );
        out2.print( obj );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� String���ڿ��� print�Ѵ�.
     *
     * @param s print�ϰ��� �ϴ� String���ڿ�.
     */
    public synchronized void print(String s) {
        out1.print( s );
        out2.print( s );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� boolean�� print�Ѵ�.
     *
     * @param b print�ϰ��� �ϴ� boolean.
     */
    public synchronized void print(boolean b) {
        out1.print( b );
        out2.print( b );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� print�ϰ�
     * line�� ��ģ��.
     */
    public synchronized void println() {
        out1.println();
        out2.println();
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char�迭��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� char�迭.
     */
    public synchronized void println(char[] x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� char.
     */
    public synchronized void println(char x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� double��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� double.
     */
    public synchronized void println(double x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� float��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� float.
     */
    public synchronized void println(float x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� int��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� int.
     */
    public synchronized void println(int x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� long��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� long.
     */
    public synchronized void println(long x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� Object��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� Object.
     */
    public synchronized void println(Object x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� String���ڿ���
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� String���ڿ�.
     */
    public synchronized void println(String x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� boolean��
     * print�ϰ� line�� ��ģ��.
     *
     * @param x print�ϰ��� �ϴ� boolean.
     */
    public synchronized void println(boolean x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char�迭�� write�Ѵ�.
     *
     * @param buf writer�ϰ��� �ϴ� char�迭.
     */
    public synchronized void write(char[] buf) {
        out1.write( buf );
        out2.write( buf );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� char�迭�� ����
     * ��ġ���� write�Ѵ�.
     *
     * @param buf writer�ϰ��� �ϴ� char�迭.
     * @param off char�迭 ���۽��������� offset.
     * @param len char�迭 ����.
     */
    public synchronized void write(char[] buf, int off, int len) {
        out1.write( buf, off, len );
        out2.write( buf, off, len );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� single���ڸ� write�Ѵ�.
     *
     * @param b writer�ϰ��� �ϴ� ����.
     */
    public synchronized void write(int b) {
        out1.write( b );
        out2.write( b );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� String���ڿ��� write�Ѵ�.
     *
     * @param s writer�ϰ��� �ϴ� String���ڿ�.
     */
    public synchronized void write(String s) {
        out1.write( s );
        out2.write( s );
    }
    /**
     * PrintWriter 1�� PrintWriter 2�� String���ڿ��� ����
     * ��ġ���� write�Ѵ�.
     *
     * @param s writer�ϰ��� �ϴ� String���ڿ�.
     * @param off ���ڿ� ���۽��������� offset.
     * @param len ���ڿ� ����.
     */
    public synchronized void write(String s, int off, int len) {
        out1.write( s, off, len );
        out2.write( s, off, len );
    }
}
