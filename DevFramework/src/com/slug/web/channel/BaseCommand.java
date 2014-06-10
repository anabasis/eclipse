package com.slug.web.channel;

/**
 * @(#) BaseCommand.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import javax.servlet.http.*;
//import com.gauce.io.*;
/**
 * channel-command pattern에서 사용할 command의 interface를 정의한다.
 * <BR>
 * <BR>BaseCommand는 html의 form tag에서 enctype을 사용하지 않는 보통의 경우에 사용할 수 있는
 * command의 interface를 정의하며 만약 enctype="multipart/form-data"을 사용하였다면 DBaseMultipartCommand를 사용하여야 한다.
 * <BR>
 * <BR>또한 특수한 경우에 content type을 별도로 가져가고 싶을 경우에는 별도의 command interface를 구성하여야 하며 이때
 * 반드시 execute() method를 구현하도록 interface를 구성 하여야 한다.
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
 * command의 main()역할을 하는 함수 servlet에서 호출되게 되며, form-processing 및 result redirect를 사용하기 위하여
 * req, res를 전달 받아야 한다.
 * @param  req servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
 * @param  res servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
 * @return  result redirect로 dispatch될 jsp/html/servlet의 uri. 상대 경로 혹은 절대경로로 구성할 수 있다.
 */

public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
