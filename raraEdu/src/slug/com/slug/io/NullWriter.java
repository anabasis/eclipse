package com.slug.io;

/**
 * @(#) NullWriter.java
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
 * abstract class인 Writer를 상속받아 아무것도 write하지않는
 * null Writer.
 *
 */
public class NullWriter extends Writer {

    /**
     * Default Constructor로 아무것도 수행하지 않는다.
     */
    public NullWriter() { }
    /**
     * 아무것도 수행하지 않는다.
     */
    public void close() { }
    /**
     * 아무것도 수행하지 않는다.
     */
    public void flush() { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param cbuf char배열.
     */
    public void write(char[] cbuf) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param cbuf char배열.
     * @param off char배열 시작시점에서의 offset.
     * @param len char배열 길이.
     */
    public void write(char[] cbuf, int off, int len) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param c int.
     */
    public void write(int c) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param str String문자열.
     */
    public void write(String str) { }
    /**
     * 아무것도 write하지 않는다.
     *
     * @param str String문자열.
     * @param off String문자열 시작시점에서의 offset.
     * @param len String문자열 길이.
     */
    public void write(String str, int off, int len) { }
}
