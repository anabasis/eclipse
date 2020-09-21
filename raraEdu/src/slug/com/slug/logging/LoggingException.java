package com.slug.logging;

/**
 * @(#) LoggingException.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import com.slug.exception.PException;
/**
 * 이 클래스는 FRAME WORK의 Log Component를 사용할때 발생하는
 * Exception을 핸들링하기 위해 사용된다.
 *
 */
public class LoggingException extends PException {
    /**
     * 특정한 메시지 없이 LoggingException을 생성한다.
     */
    public LoggingException() {
        super();
    }
    /**
     * 특정한 메시지를 갖는 LoggingException을 생성한다.
     * @param s 메시지
     */
    public LoggingException(String s) {
        super(s);
    }
    /**
     * 특정한 메시지와 Throwable을 갖는 LoggingException을 생성한다.
     * @param s 메시지
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public LoggingException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    /**
     * 특정한 Throwable을 갖는 LoggingException을 생성한다.
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public LoggingException(Throwable rootCause) {
        super(rootCause);
    }
}
