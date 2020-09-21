package com.slug.web.channel;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slug.config.AppConfigException;
import com.slug.config.AppConfigManager;
import com.slug.logging.Logging;
import com.slug.web.BaseAbstractServlet;
import com.slug.web.MultipartRequest;

/**
 * @(#) BaseMultipartChannelServlet.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author 
 *
 */




/**
 * DBaseServlet를 상속받아 구현된 Base Mutltipart Channel Servlet.
 * <BR> channel-command 방식을 지원하도록 구성되어 있으며, Multipart전용 servlet이다.
 * <BR> form processing에서 enctype을 multipart/form-data로 지정하는 경우 이때 사용할 수 있는 공통 servlet이다.
 * <BR> form processing에서 enctype을 별도로 지정하지 않으면 content-type은 application/x-www-form-urlencoded으로 지정되는데 이때는 BaseChannelServlet을 사용해야 한다.
 * <BR>
 * <BR> BaseChannelServlet은 아래와 같은 순서로 진행된다.
 * <BR> 1. http request를 검사하여 multipart인지를 판단하고 multipart가 아닌 경우 DContentTypeException을 발생시킨다.
 * <BR> 2. multipart request에서 upload된 파일들을 지정된 directory에 저장한다. 이때 파일이 저장될 directory는 properties.conf 파일에 "properties.upload.dir"로서 지정되어 있어야 한다.
 * <BR> 3. multipart request에서 debug=true로 지정되어 있는지 찾고 지정되어 있다면 command를 찾지 않고 내부적으로 BaseMultipartShowGpcCommand를 사용하여 전달된 form의 모든 내용을 보여주는 page로 분기한다.
 * <BR> 4. 지정되어 있지 않다면 multipart request에서 target_command를 찾아 해당 command를 수행할 실제 class를 찾는다.
 * <BR> 5. command를  execute()한다.
 * <BR> 6. 다음 page로 dispatch한다.
 * <BR>
 * <BR> 현재 command를 찾는 방식은 form에서 target_command의 값으로 command 클래스의 package포함한 class 이름을 전달받아 이를 Class.forName(className).newInstance() 하는 방식을 취한다.
 * <BR> BaseChannelServlet으로 사용될 command는 반드시 com.solupia.jfound.servlet.channel.DBaseMultipartCommand를 implements 한 class이어야 한다.
 * <BR>
 * <BR> 상속관계상 BaseMultipartChannelServlet는 아래의 method들을 구현하였다.
 * <BR> protected void catchService(HttpServletRequest req, HttpServletResponse res)
 * <BR> protected DBaseMultipartCommand getCommand(com.solupia.jfound.servlet.MultipartRequest mReq) throws CommandException
 * <BR>
 * <BR> (참고) DBaseServlet를 상속받는 servelt을 굳이 BaseChannelServlet과 BaseMultipartChannelServlet으로 나눈이유는
 *      단지 성능적 측면 때문이다. 사실 DBaseServlet에서 제공하는 protected boolean isMultipart(HttpServletRequest req) method를 응용하면
 *      단 하나의 servlet에서 multipart를 처리하도록 구현할 수 있으나, 전체 project로 생각할때 Multipart를 사용하는 form processing은 전체의 극히 일부분이라고 생각된다.
 *      또한 thread로 동작하는 servlet입장에서 대용량의 upload가 생길 경우, 해당 servlet의 부하를 조금이라도 분산하기 위하여 이러한 방식을 사용하였다.
 *      Project에서 file의 upload가 매우 빈번하게 있다거나, 두개의 servlet을 사용할 이유가 없다거나 하는 경우라면 1개의 servlet으로 처리할 수 있을것이다.
 *
 *  @see BaseChannelServlet
 *  @see BaseMultipartShowGpcCommand
 *
 *  @see com.solupia.jfound.servlet.DBaseServlet
 *  @see com.solupia.jfound.servlet.channel.DBaseMultipartCommand
 *
 *
 */


public class BaseMultipartChannelServlet extends BaseAbstractServlet {
/**
 * BaseMultipartChannelServlet의 기본 생성자.
 */
public BaseMultipartChannelServlet() {
    super();
}
/**
 * DBaseServlet을 상속받은 servlet들의 entry-point. main()역할을 하는 함수.
 * <BR> DBaseServlet를 상속받으면 반드시 구현하여야 한다. 실제로 servlet에서 할 일에 관련된 logic을 구성한다.
 * <BR> 대부분 form processing을 위한 준비작업 / 실제 logic 호출 / result page로 redirect정도의 로직으로 구성된다.
 * @param req servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
 * @param res servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
 * @return void
 */
protected void catchService(HttpServletRequest req, HttpServletResponse res) {
    String rtnUri = "/";
    try {

        MultipartRequest mReq = saveFiles(req);

        BaseMultipartCommand cmd = getCommand(mReq);

        rtnUri = cmd.execute(mReq,req,res);

        printJsp(req, res, rtnUri);

    } catch(CommandException be) {
        Logging.err.println("CommandException occurred - " + be);
        this.printErr(req,res,"CommandException occurred - " + be, be);
    } catch(ContentTypeException ce) {
        Logging.err.println("DContentTypeException occurred - " + ce);
        this.printErr(req,res,"DContentTypeException occurred - " + ce, ce);
    } catch(RemoteException re) {
        Logging.err.println("SysException occurred - " + re);
        this.printErr(req,res,"SysException occurred - " + re, re);
    } catch(Exception e) {
        Logging.err.println("Unexpected Exception occurred - " + e);
        this.printErr(req,res,"Unexpected Exception occurred - " + e, e);
    }
}
/**
 * MultipartRequest를 분석하여 command class를 찾는다.
 * <BR> form에서 target_command의 값으로 command 클래스의 package포함한 class 이름을 전달받아 이를 이용하여 지정된 command class 찾고 그 instance를 반환한다.
 * <BR> debug=true로 지정된경우는 target_command에 의존하지 않고 webApp.conf에 "com.solupia.jfound.servlet.channel.BaseMultipartShowGpcCommand" 로 지정되어 있는 showGpc용 command의 instance를 반환한다.
 * @param mReq 분석할 MultipartRequest
 * @return 찾아진 command class의 instance
 */

protected BaseMultipartCommand getCommand(MultipartRequest mReq) throws CommandException {
    Object cmd = null;
    String showGpcCommandClassName = "servlet.channel.BaseMultipartShowGpcCommand";
    String className = "";

    try {

        if (mReq.getParameter("debug") != null && "true".equals(mReq.getParameter("debug").toString()))
            className = showGpcCommandClassName;
        else if (mReq.getParameter("target_command") != null )
            className = mReq.getParameter("target_command").toString();
        else
            throw new CommandException("fail in BaseMultipartChannelServlet : target_command Not Found ");

        cmd = Class.forName(className).newInstance();

    } catch (ClassNotFoundException e) {
        throw new CommandException("fail in BaseMultipartChannelServlet : command Class Not Found [" + className +"] : " + e, e);
    } catch (InstantiationException e) {
        throw new CommandException("fail in BaseMultipartChannelServlet : command Class Could not Instantiation [" + className +"] : " + e, e);
    } catch (IllegalAccessException e) {
        throw new CommandException("fail in BaseMultipartChannelServlet : command Class Illegaliy Access [" + className +"] : " + e, e);
    }

    return (BaseMultipartCommand)cmd;
}
/**
 * multipart로 전송된 HttpServletRequest를 분석하여 upload된 file을 저장하고, form data들을 MultipartRequest로 변환한다.
 * <BR> upload된 파일이 저장될 directory는 webApp.conf에 "webApp.upload.dir"로서 지정되어 있어야 한다.
 * @param req 분석할 HttpServletRequest
 * @return 변환된 MultipartRequest
 */
protected MultipartRequest saveFiles(javax.servlet.http.HttpServletRequest req) throws CommandException, ContentTypeException{
    String targetDir = "";
    String targetDirConf = "sys.upload.dir";

    MultipartRequest mReq = null;

    try {
            if (!isMultipart(req))
                throw new ContentTypeException("fail in BaseMultipartChannelServlet : ContentType Incorrect. Use normal Servlet not Multipart servlet");

            AppConfigManager conf = AppConfigManager.getInstance();
            targetDir = conf.get(targetDirConf);

            mReq = new MultipartRequest(req, targetDir);

    } catch (AppConfigException le) {
        Logging.err.println("fail in BaseMultipartChannelServlet : Fail to get upload directory from Configuration [" + targetDirConf +"] : " +le);
    } catch (java.io.IOException ie) {
        Logging.err.println("fail in BaseMultipartChannelServlet : Fail to upload files. IO Exception Occured : " +ie);
    } catch (Exception ex) {
        Logging.err.println("fail in BaseMultipartChannelServlet : Fail to upload files. Unknown Exception Occured - May be no multipart data submited! : " +ex);
    }
    return mReq;
}
}
