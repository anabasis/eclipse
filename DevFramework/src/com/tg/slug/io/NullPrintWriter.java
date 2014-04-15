package com.tg.slug.io;

/**
 * @(#) NullPrintWriter.java
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
 * UNIX�� /dev/null�� java version�̴�.
 * ��¹����� NullPrintWriter�� �ϸ� ����δ�
 * �ƹ��ϵ� �Ͼ�� �ʴ´�. ��, ���ʿ��� �����
 * ����� �� �� ����� �� �ִ�.
 *
 */
public class NullPrintWriter extends PrintWriter {

    /**
     * PrintWriter�� ���� ��� constructor�� ��� ������ ���Ƿ�
     * super( new LNullWriter() )�� ȣ�����ش�.
     */
    public NullPrintWriter() {
        super( new NullWriter() );
    }
    /**
     * false�� return�Ѵ�.
     *
     * @return false.
     */
    public boolean checkError() {
        return false;
    }
    /**
     * �ƹ��͵� �������� �ʴ´�.
     */
    public void close() { }
    /**
     * �ƹ��͵� �������� �ʴ´�.
     */
    public void flush() { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param s char�迭.
     */
    public void print(char[] s) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param c char.
     */
    public void print(char c) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param d double.
     */
    public void print(double d) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param f float.
     */
    public void print(float f) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param i int.
     */
    public void print(int i) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param l long.
     */
    public void print(long l) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param obj Object.
     */
    public void print(Object obj) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param s String���ڿ�.
     */
    public void print(String s) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param b boolean.
     */
    public void print(boolean b) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     */
    public void println() { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x char���ڿ�.
     */
    public void println(char[] x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x char.
     */
    public void println(char x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x double.
     */
    public void println(double x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x float.
     */
    public void println(float x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x int.
     */
    public void println(int x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x long.
     */
    public void println(long x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x Object.
     */
    public void println(Object x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x String���ڿ�.
     */
    public void println(String x) { }
    /**
     * �ƹ��͵� print���� �ʴ´�.
     *
     * @param x boolean.
     */
    public void println(boolean x) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param buf char�迭.
     */
    public void write(char[] buf) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param buf char�迭.
     * @param off char�迭 ���۽��������� offset.
     * @param len char�迭 ����.
     */
    public void write(char[] buf, int off, int len) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param b int.
     */
    public void write(int b) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param s String���ڿ�.
     */
    public void write(String s) { }
    /**
     * �ƹ��͵� write���� �ʴ´�.
     *
     * @param s String���ڿ�.
     * @param off String���ڿ� ���۽��������� offset.
     * @param len String���ڿ� ����.
     */
    public void write(String s, int off, int len) { }
}
