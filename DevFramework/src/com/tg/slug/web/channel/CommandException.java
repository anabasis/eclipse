package com.tg.slug.web.channel;

/**
 * @(#) CommandException.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

/**
 * channel-command pattern�� Servelt-command ��������,
 * command���� �߻��� Excption�� command�� call�� servlet���� throw�� ��� ����ϴ� Excption�̴�.
 * <BR>
 * <BR>command���� �ɷ��� �ϴ� ��κ��� Excption���� ������ command���� ó���� �Ͽ��� �ϴ� ���� �⺻���� ���̵��̸�,
 * ���� project���� �������� error-handling�� �ʿ��� ��쿡 ������ Servlet�� ���� Excption handling�� ������� ����
 * abstract command �ϳ� ���� ���� ���� Excption handling�� �ϰ� �̸� �ٽ� ��ӹ޾Ƽ� �����ڵ鿡�� �����ϴ°��� ����.
 * <BR>�̰��� servlet�� �ܼ��� form-porcess / jsp result redirect ��ɸ��� �����ϰ�
 * ���� data handling�� command���� �ϴ� channel-command�� �� role�� �� ����Ͽ� �����Ͽ��� �� ���̴�.
 * <BR>�ٽ� ���ؼ� Excption�� command���� ó���ϰ� �׷��� ���� excption�� command�� call�� servlet���� throw�� ��쿡
 * ����� �� �ִ� Excption�� �ٷ� CommandException�̴�.
 *
 *  @see com.exception.LException
 *  @see CommandException
 *  @see BaseMultipartCommand
 *  @see com.servlet.channel.DefaultShowGpcCommand
 *  @see com.servlet.channel.DefaultMultipartShowGpcCommand
 *
 *
 */
public class CommandException extends com.tg.slug.exception.PException {
/**
 * Ư���� �޽��� ���� CommandException�� ���Ѵ�.
 */
public CommandException() {
    super();
}
/**
 * Ư���� �޽����� ���� CommandException�� ���Ѵ�.
 * @param s �޽���
 */
public CommandException(String s) {
    super(s);
}
/**
 * Ư���� �޽����� Throwable�� ���� CommandException�� ���Ѵ�.
 * @param message  �޽���
 * @param rootCause exception chaining�� �ʿ��� Throwable exception
 */
public CommandException(String message, Throwable rootCause) {
    super(message, rootCause);
}
/**
 * Ư���� Throwable�� ���� CommandException�� ���Ѵ�.
 * @param rootCause exception chaining�� �ʿ��� Throwable exception
 */

public CommandException(Throwable rootCause) {
    super(rootCause);
}
}
