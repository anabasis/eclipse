package com.slug.io;

/**
 * @(#) TeePrintWriter.java
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
 * 한 곳에 프린트하면 두 곳으로 출력하는 Class로 Unix의 'tee'프로그램의
 * java version이다.  TeePrintWriter를 생성할 때 두개의 PrintWriter을
 * parameter로 주면 주어진 두개의 PrintWriter에 동시에 출력하는 Class다.
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
     * PrintWriter에 인자 없는 constructor가 없기 때문에 임의로
     * super( new DNullWriter() )을 호출해주고 parameter를 셋팅한다.
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
     * PrintWriter 1과 PrintWriter 2의 error state를 체크한다.
     *
     * @return PrintWriter 1의 error state 혹은
     * PrintWriter 2의 error state.
     */
    public boolean checkError() {
        return out1.checkError() || out2.checkError();
    }
    /**
     * PrintWriter 1과 PrintWriter 2를 close()시킨다.
     */
    public synchronized void close() {
        out1.close();
        out2.close();
    }
    /**
     * PrintWriter 1과 PrintWriter 2를 flush하고 close한다.
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
     * PrintWriter 1과 PrintWriter 2를 각각 flush() 해준다.
     */
    public synchronized void flush() {
        out1.flush();
        out2.flush();
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char배열을 print한다.
     *
     * @param s print하고자 하는 char배열.
     */
    public synchronized void print(char[] s) {
        out1.print( s );
        out2.print( s );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char를 print한다.
     *
     * @param c print하고자 하는 char.
     */
    public synchronized void print(char c) {
        out1.print( c );
        out2.print( c );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 double를 print한다.
     *
     * @param d print하고자 하는 double.
     */
    public synchronized void print(double d) {
        out1.print( d );
        out2.print( d );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 float를 print한다.
     *
     * @param f print하고자 하는 float.
     */
    public synchronized void print(float f) {
        out1.print( f );
        out2.print( f );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 int를 print한다.
     *
     * @param i print하고자 하는 int.
     */
    public synchronized void print(int i) {
        out1.print( i );
        out2.print( i );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 long를 print한다.
     *
     * @param l print하고자 하는 long.
     */
    public synchronized void print(long l) {
        out1.print( l );
        out2.print( l );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 Object를 print한다.
     *
     * @param obj print하고자 하는 Object.
     */
    public synchronized void print(Object obj) {
        out1.print( obj );
        out2.print( obj );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 String문자열을 print한다.
     *
     * @param s print하고자 하는 String문자열.
     */
    public synchronized void print(String s) {
        out1.print( s );
        out2.print( s );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 boolean을 print한다.
     *
     * @param b print하고자 하는 boolean.
     */
    public synchronized void print(boolean b) {
        out1.print( b );
        out2.print( b );
    }
    /**
     * PrintWriter 1과 PrintWriter 2를 print하고
     * line을 마친다.
     */
    public synchronized void println() {
        out1.println();
        out2.println();
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char배열을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 char배열.
     */
    public synchronized void println(char[] x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 char.
     */
    public synchronized void println(char x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 double을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 double.
     */
    public synchronized void println(double x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 float을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 float.
     */
    public synchronized void println(float x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 int을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 int.
     */
    public synchronized void println(int x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 long을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 long.
     */
    public synchronized void println(long x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 Object를
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 Object.
     */
    public synchronized void println(Object x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 String문자열을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 String문자열.
     */
    public synchronized void println(String x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 boolean을
     * print하고 line을 마친다.
     *
     * @param x print하고자 하는 boolean.
     */
    public synchronized void println(boolean x) {
        out1.println( x );
        out2.println( x );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char배열을 write한다.
     *
     * @param buf writer하고자 하는 char배열.
     */
    public synchronized void write(char[] buf) {
        out1.write( buf );
        out2.write( buf );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 char배열을 일정
     * 위치에서 write한다.
     *
     * @param buf writer하고자 하는 char배열.
     * @param off char배열 시작시점에서의 offset.
     * @param len char배열 길이.
     */
    public synchronized void write(char[] buf, int off, int len) {
        out1.write( buf, off, len );
        out2.write( buf, off, len );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 single문자를 write한다.
     *
     * @param b writer하고자 하는 문자.
     */
    public synchronized void write(int b) {
        out1.write( b );
        out2.write( b );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 String문자열을 write한다.
     *
     * @param s writer하고자 하는 String문자열.
     */
    public synchronized void write(String s) {
        out1.write( s );
        out2.write( s );
    }
    /**
     * PrintWriter 1과 PrintWriter 2의 String문자열을 일정
     * 위치에서 write한다.
     *
     * @param s writer하고자 하는 String문자열.
     * @param off 문자열 시작시점에서의 offset.
     * @param len 문자열 길이.
     */
    public synchronized void write(String s, int off, int len) {
        out1.write( s, off, len );
        out2.write( s, off, len );
    }
}
