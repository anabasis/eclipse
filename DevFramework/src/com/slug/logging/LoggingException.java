package com.slug.logging;

/**
 * @(#) LoggingException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import com.slug.exception.PException;
/**
 * �� Ŭ������ FRAME WORK�� Log Component�� ����Ҷ� �߻��ϴ�
 * Exception�� �ڵ鸵�ϱ� ���� ���ȴ�.
 *
 */
public class LoggingException extends PException {
    /**
     * Ư���� �޽��� ���� LoggingException�� ���Ѵ�.
     */
    public LoggingException() {
        super();
    }
    /**
     * Ư���� �޽����� ���� LoggingException�� ���Ѵ�.
     * @param s �޽���
     */
    public LoggingException(String s) {
        super(s);
    }
    /**
     * Ư���� �޽����� Throwable�� ���� LoggingException�� ���Ѵ�.
     * @param s �޽���
     * @param rootCause exception chaining�� �ʿ��� Throwable
     */
    public LoggingException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    /**
     * Ư���� Throwable�� ���� LoggingException�� ���Ѵ�.
     * @param rootCause exception chaining�� �ʿ��� Throwable
     */
    public LoggingException(Throwable rootCause) {
        super(rootCause);
    }
}
