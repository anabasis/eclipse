package com.slug.web.channel;

/**
 * @(#) BaseMultipartCommand.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slug.web.MultipartRequest;



/**
 * channel-command pattern���� ����� Multipart�� command�� interface�� �����Ѵ�.
 * <BR>
 * <BR>BaseMultipartCommand�� html�� form tag����  enctype="multipart/form-data"�� ����� ��쿡 ����� �� �ִ�
 * command�� interface�� �����Ѵ�. ������ ����Ͽ��ٸ� BaseMultipartCommand�� ����Ͽ��� �Ѵ�.
 * <BR>
 * <BR>���� Ư���� ��쿡 content type�� ������ �������� ���� ��쿡�� ������ command interface�� �����Ͽ��� �ϸ� �̶�
 * �ݵ�� execute() method�� �����ϵ��� interface�� ���� �Ͽ��� �Ѵ�.
 *
 *  @see BaseCommand
 *  @see CommandException
 *  @see com.servlet.channel.DefaultMultipartChannelServlet
 *  @see com.servlet.channel.DefaultMultipartShowGpcCommand
 *
 *
 */


public interface BaseMultipartCommand {
/**
 * command�� main()������ �ϴ� �Լ� servlet���� ȣ��ǰ� �Ǹ�, form-processing �� result redirect�� ����ϱ� ���Ͽ� req, res�� ��� �޾ƾ� �Ѵ�.
 * @param  mReq servlet���� multipart stream�� parsing�� �� ���� LMultipartRequest. form processing�� ���Ͽ� ����Ѵ�.
 * @param  req servlet���� ��޹��� HttpServletRequest. form processing�� ���Ͽ� ����Ѵ�.
 * @param  res servlet���� ��޹��� HttpServletResponse. result redirect�� ���Ͽ� ����Ѵ�.
 * @return  result redirect�� dispatch�� jsp/html/servlet�� uri. ��� ��� Ȥ�� ����η� ������ �� �ִ�.
 */

public String execute(MultipartRequest mReq, HttpServletRequest req, HttpServletResponse res) throws Exception;
}
