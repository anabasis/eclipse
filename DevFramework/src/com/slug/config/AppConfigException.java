package com.slug.config;

/**
 * @(#) ConfigureException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author 김동식, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import com.slug.exception.PException;
/**
 * Configuration Component를 사용할때 발생하는
 * Exception을 핸들링하기 위해 사용된다.
 *
 */
public class AppConfigException extends PException {
    /**
     * 특정한 메시지 없이 ConfigureException을 생성한다.
     */
    public AppConfigException() {
        super();
    }
    /**
     * 특정한 메시지를 갖는 AppConfigException을 생성한다.
     * @param s 메시지
     */
    public AppConfigException(String s) {
        super(s);
    }
    /**
     * 특정한 메시지와 Throwable을 갖는 AppConfigException을 생성한다.
     * @param s 메시지
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public AppConfigException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    /**
     * 특정한 Throwable을 갖는 AppConfigException을 생성한다.
     * @param rootCause exception chaining에 필요한 Throwable
     */
    public AppConfigException(Throwable rootCause) {
        super(rootCause);
    }
}
