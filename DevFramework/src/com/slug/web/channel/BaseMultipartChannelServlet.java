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
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */




/**
 * DBaseServlet�� ��ӹ޾� ������ Base Mutltipart Channel Servlet.
 * <BR> channel-command ����� �����ϵ��� �����Ǿ� ������, Multipart��� servlet�̴�.
 * <BR> form processing���� enctype�� multipart/form-data�� �����ϴ� ��� �̶� ����� �� �ִ� ���� servlet�̴�.
 * <BR> form processing���� enctype�� ������ �������� ������ content-type�� application/x-www-form-urlencoded���� �����Ǵµ� �̶��� BaseChannelServlet�� ����ؾ� �Ѵ�.
 * <BR>
 * <BR> BaseChannelServlet�� �Ʒ��� ���� ��� ����ȴ�.
 * <BR> 1. http request�� �˻��Ͽ� multipart������ �Ǵ��ϰ� multipart�� �ƴ� ��� DContentTypeException�� �߻��Ų��.
 * <BR> 2. multipart request���� upload�� ���ϵ��� ������ directory�� �����Ѵ�. �̶� ������ ����� directory�� properties.conf ���Ͽ� "properties.upload.dir"�μ� �����Ǿ� �־�� �Ѵ�.
 * <BR> 3. multipart request���� debug=true�� �����Ǿ� �ִ��� ã�� �����Ǿ� �ִٸ� command�� ã�� �ʰ� ���������� BaseMultipartShowGpcCommand�� ����Ͽ� ��޵� form�� ��� ������ �����ִ� page�� �б��Ѵ�.
 * <BR> 4. �����Ǿ� ���� �ʴٸ� multipart request���� target_command�� ã�� �ش� command�� ������ ���� class�� ã�´�.
 * <BR> 5. command��  execute()�Ѵ�.
 * <BR> 6. ���� page�� dispatch�Ѵ�.
 * <BR>
 * <BR> ���� command�� ã�� ����� form���� target_command�� ������ command Ŭ������ package������ class �̸��� ��޹޾� �̸� Class.forName(className).newInstance() �ϴ� ����� ���Ѵ�.
 * <BR> BaseChannelServlet���� ���� command�� �ݵ�� com.solupia.jfound.servlet.channel.DBaseMultipartCommand�� implements �� class�̾�� �Ѵ�.
 * <BR>
 * <BR> ��Ӱ��� BaseMultipartChannelServlet�� �Ʒ��� method���� �����Ͽ���.
 * <BR> protected void catchService(HttpServletRequest req, HttpServletResponse res)
 * <BR> protected DBaseMultipartCommand getCommand(com.solupia.jfound.servlet.MultipartRequest mReq) throws CommandException
 * <BR>
 * <BR> (���) DBaseServlet�� ��ӹ޴� servelt�� ���� BaseChannelServlet�� BaseMultipartChannelServlet���� ����������
 *      ���� ������ ��� �����̴�. ��� DBaseServlet���� �����ϴ� protected boolean isMultipart(HttpServletRequest req) method�� �����ϸ�
 *      �� �ϳ��� servlet���� multipart�� ó���ϵ��� ������ �� ������, ��ü project�� ���Ҷ� Multipart�� ����ϴ� form processing�� ��ü�� ���� �Ϻκ��̶�� ��ȴ�.
 *      ���� thread�� �����ϴ� servlet���忡�� ��뷮�� upload�� ��� ���, �ش� servlet�� ���ϸ� �����̶� �л��ϱ� ���Ͽ� �̷��� ����� ����Ͽ���.
 *      Project���� file�� upload�� �ſ� ����ϰ� �ִٰų�, �ΰ��� servlet�� ����� ������ ��ٰų� �ϴ� ����� 1���� servlet���� ó���� �� �������̴�.
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
 * BaseMultipartChannelServlet�� �⺻ ����.
 */
public BaseMultipartChannelServlet() {
    super();
}
/**
 * DBaseServlet�� ��ӹ��� servlet���� entry-point. main()������ �ϴ� �Լ�.
 * <BR> DBaseServlet�� ��ӹ����� �ݵ�� �����Ͽ��� �Ѵ�. ������ servlet���� �� �Ͽ� ��õ� logic�� �����Ѵ�.
 * <BR> ��κ� form processing�� ���� �غ��۾� / ���� logic ȣ�� / result page�� redirect������ �������� �����ȴ�.
 * @param req servlet���� ��޹��� HttpServletRequest. form processing�� ���Ͽ� ����Ѵ�.
 * @param res servlet���� ��޹��� HttpServletResponse. result redirect�� ���Ͽ� ����Ѵ�.
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
 * MultipartRequest�� �м��Ͽ� command class�� ã�´�.
 * <BR> form���� target_command�� ������ command Ŭ������ package������ class �̸��� ��޹޾� �̸� �̿��Ͽ� ������ command class ã�� �� instance�� ��ȯ�Ѵ�.
 * <BR> debug=true�� �����Ȱ��� target_command�� �������� �ʰ� webApp.conf�� "com.solupia.jfound.servlet.channel.BaseMultipartShowGpcCommand" �� �����Ǿ� �ִ� showGpc�� command�� instance�� ��ȯ�Ѵ�.
 * @param mReq �м��� MultipartRequest
 * @return ã���� command class�� instance
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
 * multipart�� ��۵� HttpServletRequest�� �м��Ͽ� upload�� file�� �����ϰ�, form data���� MultipartRequest�� ��ȯ�Ѵ�.
 * <BR> upload�� ������ ����� directory�� webApp.conf�� "webApp.upload.dir"�μ� �����Ǿ� �־�� �Ѵ�.
 * @param req �м��� HttpServletRequest
 * @return ��ȯ�� MultipartRequest
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
