package com.tg.slug.exception;

/**
 * @(#) SysException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

    /**
     * Insert the type's description here.
     * Creation date: (2002-01-30 ���� 12:34:49)
     * @author: Administrator
     */
    public class SysException extends PException {
    /**
     * SysException constructor comment.
     */
    public SysException() {
        super();
    }
    /**
     * SysException constructor comment.
     * @param s java.lang.String
     */
    public SysException(String s) {
        super(s);
    }
    /**
     * SysException constructor comment.
     * @param message java.lang.String
     * @param rootCause java.lang.Throwable
     */
    public SysException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    /**
     * SysException constructor comment.
     * @param rootCause java.lang.Throwable
     */
    public SysException(Throwable rootCause) {
        super(rootCause);
    }
}