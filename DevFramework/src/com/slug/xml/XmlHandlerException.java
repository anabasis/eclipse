package com.slug.xml;

import com.slug.exception.PException;

/**
 * @(#) RemoteProxyException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

/**
 * �̴� Remote Handle�� ����Ͽ� �߻��ϴ� Exception�� �ڵ鸵�ϱ� ����
 * LException�� ��ӹ��� Ŭ�����̴�.
 *
 */
public class XmlHandlerException extends PException{
	
	/**
	 * Ư���� �޽��� ���� RemoteProxyException�� ���Ѵ�.
	 */
	public XmlHandlerException() {
		super();
	}
	/**
	 * Ư���� �޽����� ���� RemoteProxyException�� ���Ѵ�.
	 * @param s �޽���
	 */
	public XmlHandlerException(String s) {
		super(s);
	}
	/**
	 * Ư���� �޽����� Throwable�� ���� RemoteProxyException�� ���Ѵ�.
	 * @param message �޽���
	 * @param rootCause exception chaining�� �ʿ��� Throwable
	 */
	public XmlHandlerException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	/**
	 * Ư���� Throwable�� ���� RemoteProxyException�� ���Ѵ�.
	 * @param rootCause exception chaining�� �ʿ��� Throwable
	 */
	public XmlHandlerException(Throwable rootCause) {
		super(rootCause);
	}
}
