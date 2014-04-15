package com.tg.slug.web.channel;

/**
 * @(#) ContentTypeException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

/**
 * Servelt�� Form Processing �������� Content-Type�� ���� ���� ��쿡 �߻��ϴ� Excption�̴�.
 * <BR>
 * <BR>���� ��� html�� form tag���� enctype="multipart/form-data" ���� �����Ͽ��ٸ� �� form�� Action ����� �Ǵ�
 * Servlet�� �ݵ�� multipart form data�� processing�� �� �ִ� logic�� ������ �־�� �Ѵ�.
 * <BR>Action Target�� �Ǵ� Servlet�� ������ �̷��� content-type�� �����Ͽ� ������ Excption�� handling�� �� �־�� �ϸ�
 * �̷��� content-type�� ���� ���� ��� error handling �ÿ� ContentTypeException�� �̿��Ѵ�.
 *
 *  @see com.exception.LException
 *  @see com.servlet.channel.DefaultChannelServlet
 *  @see com.servlet.channel.DefaultMultipartChannelServlet
 *
 *
 */

public class ContentTypeException extends com.tg.slug.exception.PException {
/**
 * Ư���� �޽��� ���� ContentTypeException�� ���Ѵ�.
 */
public ContentTypeException() {
    super();
}
/**
 * Ư���� �޽����� ���� ContentTypeException�� ���Ѵ�.
 * @param s  �޽���
 */
public ContentTypeException(String s) {
    super(s);
}
/**
 * Ư���� �޽����� Throwable�� ���� ContentTypeException�� ���Ѵ�.
 * @param message �޽���
 * @param rootCause  exception chaining�� �ʿ��� Throwable exception
 */

public ContentTypeException(String message, Throwable rootCause) {
    super(message, rootCause);
}
/**
 * Ư���� Throwable�� ���� ContentTypeException�� ���Ѵ�.
 * @param rootCause exception chaining�� �ʿ��� Throwable exception
 */

public ContentTypeException(Throwable rootCause) {
    super(rootCause);
}
}
