package com.slug.io;

/**
 * @(#) NullPrintWriter.java
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
 * UNIX의 /dev/null의 java version이다.
 * 출력방향을 NullPrintWriter로 하면 실재로는
 * 아무일도 일어나지 않는다. 즉, 불필요한 출력을
 * 없에고자 할 때 사용할 수 있다.
 *
 */
public class NullPrintWriter extends PrintWriter {

    /**
     * PrintWriter에 인자 없는 constructor가 없기 때문에 임의로
     * super( new LNullWriter() )을 호출해준다.
     */
    public NullPrintWriter() {
        super( new NullWriter() );
    }
    /**
     * false를 return한다.
     *
     * @return false.
     */
    public boolean checkError() {
        return false;
    }
    /**
     * 아무것도 수행하지 않는다.
     */
    public void close() { }
    /**
     * 아무것도 수행하지 않는다.
     */
    public void flush() { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param s char배열.
     */
    public void print(char[] s) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param c char.
     */
    public void print(char c) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param d double.
     */
    public void print(double d) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param f float.
     */
    public void print(float f) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param i int.
     */
    public void print(int i) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param l long.
     */
    public void print(long l) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param obj Object.
     */
    public void print(Object obj) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param s String문자열.
     */
    public void print(String s) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param b boolean.
     */
    public void print(boolean b) { }
    /**
     * 아무것도 print하지 않는다.
     */
    public void println() { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x char문자열.
     */
    public void println(char[] x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x char.
     */
    public void println(char x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x double.
     */
    public void println(double x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x float.
     */
    public void println(float x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x int.
     */
    public void println(int x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x long.
     */
    public void println(long x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x Object.
     */
    public void println(Object x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x String문자열.
     */
    public void println(String x) { }
    /**
     * 아무것도 print하지 않는다.
     *
     * @param x boolean.
     */
    public void println(boolean x) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param buf char배열.
     */
    public void write(char[] buf) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param buf char배열.
     * @param off char배열 시작시점에서의 offset.
     * @param len char배열 길이.
     */
    public void write(char[] buf, int off, int len) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param b int.
     */
    public void write(int b) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param s String문자열.
     */
    public void write(String s) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param s String문자열.
     * @param off String문자열 시작시점에서의 offset.
     * @param len String문자열 길이.
     */
    public void write(String s, int off, int len) { }
}
