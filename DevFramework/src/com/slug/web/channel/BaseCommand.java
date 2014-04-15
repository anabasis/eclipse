package com.slug.web.channel;

/**
 * @(#) BaseCommand.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import javax.servlet.http.*;
//import com.gauce.io.*;
/**
 * channel-command pattern���� ����� command�� interface�� �����Ѵ�.
 * <BR>
 * <BR>BaseCommand�� html�� form tag���� enctype�� ������� �ʴ� ������ ��쿡 ����� �� �ִ�
 * command�� interface�� �����ϸ� ���� enctype="multipart/form-data"�� ����Ͽ��ٸ� DBaseMultipartCommand�� ����Ͽ��� �Ѵ�.
 * <BR>
 * <BR>���� Ư���� ��쿡 content type�� ������ �������� ���� ��쿡�� ������ command interface�� �����Ͽ��� �ϸ� �̶�
 * �ݵ�� execute() method�� �����ϵ��� interface�� ���� �Ͽ��� �Ѵ�.
 *
 *  @see DBaseMultipartCommand
 *  @see DCommandException
 *  @see com.servlet.channel.DefaultChannelServlet
 *  @see com.servlet.channel.DefaultShowGpcCommand
 *
 *
 */


public interface BaseCommand {
/**
 * command�� main()������ �ϴ� �Լ� servlet���� ȣ��ǰ� �Ǹ�, form-processing �� result redirect�� ����ϱ� ���Ͽ�
 * req, res�� ��� �޾ƾ� �Ѵ�.
 * @param  req servlet���� ��޹��� HttpServletRequest. form processing�� ���Ͽ� ����Ѵ�.
 * @param  res servlet���� ��޹��� HttpServletResponse. result redirect�� ���Ͽ� ����Ѵ�.
 * @return  result redirect�� dispatch�� jsp/html/servlet�� uri. ��� ��� Ȥ�� ����η� ������ �� �ִ�.
 */

public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
