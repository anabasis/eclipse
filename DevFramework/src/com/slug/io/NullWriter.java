package com.slug.io;

/**
 * @(#) NullWriter.java
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
 * abstract class�� Writer�� ��ӹ޾� �ƹ��͵� write�����ʴ�
 * null Writer.
 *
 */
public class NullWriter extends Writer {

    /**
     * Default Constructor�� �ƹ��͵� �������� �ʴ´�.
     */
    public NullWriter() { }
    /**
     * �ƹ��͵� �������� �ʴ´�.
     */
    public void close() { }
    /**
     * �ƹ��͵� �������� �ʴ´�.
     */
    public void flush() { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param cbuf char�迭.
     */
    public void write(char[] cbuf) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param cbuf char�迭.
     * @param off char�迭 ���۽��������� offset.
     * @param len char�迭 ����.
     */
    public void write(char[] cbuf, int off, int len) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param c int.
     */
    public void write(int c) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param str String���ڿ�.
     */
    public void write(String str) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param str String���ڿ�.
     * @param off String���ڿ� ���۽��������� offset.
     * @param len String���ڿ� ����.
     */
    public void write(String str, int off, int len) { }
}
