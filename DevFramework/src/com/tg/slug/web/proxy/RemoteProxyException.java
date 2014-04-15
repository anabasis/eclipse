package com.tg.slug.web.proxy;

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
public class RemoteProxyException extends com.tg.slug.exception.PException {
	/**
	 * Ư���� �޽��� ���� RemoteProxyException�� ���Ѵ�.
	 */
	public RemoteProxyException() {
		super();
	}
	/**
	 * Ư���� �޽����� ���� RemoteProxyException�� ���Ѵ�.
	 * @param s �޽���
	 */
	public RemoteProxyException(String s) {
		super(s);
	}
	/**
	 * Ư���� �޽����� Throwable�� ���� RemoteProxyException�� ���Ѵ�.
	 * @param message �޽���
	 * @param rootCause exception chaining�� �ʿ��� Throwable
	 */
	public RemoteProxyException(String message, Throwable rootCause) {
		super(message, rootCause);
	}
	/**
	 * Ư���� Throwable�� ���� RemoteProxyException�� ���Ѵ�.
	 * @param rootCause exception chaining�� �ʿ��� Throwable
	 */
	public RemoteProxyException(Throwable rootCause) {
		super(rootCause);
	}
}
