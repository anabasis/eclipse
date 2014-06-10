package com.slug.xml;

import com.slug.exception.PException;

/**
 * @(#) RemoteProxyException.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

/**
 * 이는 Remote Handle과 관련하여 발생하는 Exception을 핸들링하기 위해
 * LException을 상속받은 클래스이다.
 *
 */
public class XmlHandlerException extends PException{
	
	/**
	 * 특정한 메시지 없이 RemoteProxyException을 생성한다.
	 */
	public XmlHandlerException() {
		super();
	}
	/**
	 * 특정한 메시지를 갖는 RemoteProxyException을 생성한다.
	 * @param s 메시지
	 */
	public XmlHandlerException(String s) {
		super(s);
	}
	/**
	 * 특정한 메시지와 Throwable을 갖는 RemoteProxyException을 생성한다.
	 * @param message 메시지
	 * @param rootCause exception chaining에 필요한 Throwable
	 */
	public XmlHandlerException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	/**
	 * 특정한 Throwable을 갖는 RemoteProxyException을 생성한다.
	 * @param rootCause exception chaining에 필요한 Throwable
	 */
	public XmlHandlerException(Throwable rootCause) {
		super(rootCause);
	}
}
