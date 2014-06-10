package com.slug.web.channel;

/**
 * @(#) ContentTypeException.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

/**
 * Servelt의 Form Processing 과정에서 Content-Type이 맞지 않을 경우에 발생하는 Excption이다.
 * <BR>
 * <BR>예를 들어 html의 form tag에서 enctype="multipart/form-data" 으로 선언하였다면 이 form의 Action 대상이 되는
 * Servlet은 반드시 multipart form data를 processing할 수 있는 logic을 가지고 있어야 한다.
 * <BR>Action Target이 되는 Servlet을 구현시 이러한 content-type을 구분하여 적절한 Excption을 handling할 수 있어야 하며
 * 이러한 content-type이 맞지 않을 경우 error handling 시에 ContentTypeException을 이용한다.
 *
 *  @see com.exception.LException
 *  @see com.servlet.channel.DefaultChannelServlet
 *  @see com.servlet.channel.DefaultMultipartChannelServlet
 *
 *
 */

public class ContentTypeException extends com.slug.exception.PException {
/**
 * 특정한 메시지 없이 ContentTypeException을 생성한다.
 */
public ContentTypeException() {
    super();
}
/**
 * 특정한 메시지를 갖는 ContentTypeException을 생성한다.
 * @param s  메시지
 */
public ContentTypeException(String s) {
    super(s);
}
/**
 * 특정한 메시지와 Throwable을 갖는 ContentTypeException을 생성한다.
 * @param message 메시지
 * @param rootCause  exception chaining에 필요한 Throwable exception
 */

public ContentTypeException(String message, Throwable rootCause) {
    super(message, rootCause);
}
/**
 * 특정한 Throwable을 갖는 ContentTypeException을 생성한다.
 * @param rootCause exception chaining에 필요한 Throwable exception
 */

public ContentTypeException(Throwable rootCause) {
    super(rootCause);
}
}
