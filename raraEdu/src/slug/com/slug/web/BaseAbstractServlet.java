package com.slug.web;

/**
 * @(#) BaseAbstractServlet.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.slug.logging.*;

/**
 * FRAME WORK에서 제공되는 최상위 abstract generic servlet 이다. <BR>
 * <BR>
 * 이 servlet은 form processing이 post방식이든지 get 방식이든지 상관없이 catchService() method를
 * 호출하도록 되어 있다. <BR>
 * 실제적인 catchService()는 Project의 prototype team에서 구현하여야 할것이나 channel-command
 * pattern을 사용한 defaultServet을 com.servet.channel package에 작성해 두었으니 참고하라.
 * (DefaultChannelServelt, DefaultMultipartChannelServlet) <BR>
 * 이 generic servlet은 jsp dispatch를 위한 method와 최상위 html error page method,
 * multipart 검사 method등의 기능을 갖추고 있으며 <BR>
 * channel-command pattern을 위한 method나 multipart request를 구분하기 위한 기능은 없다는 점에
 * 유의하여야 한다. <BR>
 * <BR>
 * 참고로 몇몇 개발툴(IBM Visual Age for Java)에서는 이 LAbstractServet을 상속받으면 service()
 * method가 생성되기도 하는데, <BR>
 * 이 service() method는 override하면 안되고 반드시 지워주어야 한다.
 * 
 * @see servlet.channel.DefaultChannelServlet
 * @see servlet.channel.DefaultMultipartChannelServlet
 * 
 */

public abstract class BaseAbstractServlet extends HttpServlet{

    /**
     * BaseAbstractServlet 생성자
     */
    public BaseAbstractServlet(){
        super();
    }

    /**
     * BaseAbstractServlet을 상속받은 servlet들의 entry-point. main()역할을 하는 함수. <BR>
     * BaseAbstractServlet를 상속받으면 반드시 구현하여야 한다. 실제로 servlet에서 할 일에 관련된 logic을
     * 구성한다. <BR>
     * 대부분 form processing을 위한 준비작업 / 실제 logic 호출 / result page로 redirect정도의
     * 로직으로 구성된다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @return void
     */
    protected abstract void catchService(HttpServletRequest req, HttpServletResponse res);

    /**
     * get 방식의 form processing시에 entry-point로서 작동한다. 현재는 catchService() method로
     * redirect한다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @return void
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
                                                                         IOException{

        catchService(req,res);
    }

    /**
     * post 방식의 form processing시에 entry-point로서 작동한다. 현재는 catchService() method로
     * redirect한다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @return void
     */

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
                                                                          IOException{

        catchService(req,res);
    }

    /**
     * content-type이 "multipart/form-data"인지를 검사한다. <BR>
     * request.getContentType() method를 사용하며 이때 구해지는 문자열을 적절한 크기로 잘라서 비교하는
     * logic을 사용한다. <BR>
     * (참고) html의 form에서 특별하게 enctype을 지정하지 않는경우 content-Type은
     * application/x-www-form-urlencoded이다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. 실제의 content type을 구하기 위해
     *            사용한다.
     * @return boolean "multipart/form-data"인경우 true, 그렇지 않을경우 false.
     */
    protected boolean isMultipart(HttpServletRequest req){
        String contentType = null;
        String multipartContentType = "multipart/form-data";
        contentType = req.getContentType();

        return (contentType != null && contentType.length() > 19 && multipartContentType
                .equals(contentType.substring(0,19))) ? true : false;
    }

    /**
     * 결과화면(html/jsp등)을 dispatch한다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @param jspfile
     *            dispatch할 html/jsp의 uri. 상대경로 혹은 절대경로를 모두 모두 사용할 수 있다.
     * @return void
     */
    protected void printJsp(HttpServletRequest req, HttpServletResponse res, String jspfile){
        RequestDispatcher dispatcher = null;

        try{
        	//Logging.dev.println("<BaseAbstractServlet> jspfile ;;>>"+jspfile+"<<");

            dispatcher = getServletContext().getRequestDispatcher(jspfile);
            dispatcher.forward(req,res);

        }catch(Exception e){
            this.printErr(req,res, "Default Error Dispatching JSP Page in BaseAbstractServlet.printJsp()",e);
        }
    }

    /**
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @param customMsg
     *            커스텀 에러 메세지
     * @param e
     *            발생한 에러객체
     * @return void
     */
    protected void printErrorPage(HttpServletRequest req, HttpServletResponse res,
                                  String customMsg, String errorPage, Exception e){

        RequestDispatcher dispatcher = null;

        try{
            e.printStackTrace(Logging.err);
            req.setAttribute("ex",e);

            dispatcher = getServletContext().getRequestDispatcher(errorPage);
            dispatcher.forward(req,res);

        }catch(Exception ex){
            this.printErr( req, res, "printErrorPage() Error Dispatching JSP Page in BaseAbstractServlet printErrorPage()", ex);
        }

    }

    /**
     * generic한 html error page를 생성/표시한다. BaseAbstractServlet을 상속받은 Servlet이나
     * command에서 handling되지 않은 exception이 발생하거나 System exception이 발생했을때 사용할 수 있는
     * plain text type의 error page를 생성한다. <BR>
     * 주로 개발시에 사용할 수 있을것이다.
     * 
     * @param req
     *            servlet에서 전달받은 HttpServletRequest. form processing을 위하여 사용한다.
     * @param res
     *            servlet에서 전달받은 HttpServletResponse. result redirect을 위하여 사용한다.
     * @param customMsg
     *            커스텀 에러 메세지
     * @param e
     *            발생한 에러객체
     * @return void
     */

    protected void printErr(HttpServletRequest req, HttpServletResponse res, String customMsg,
                            Exception e){

        StringBuffer buf = new StringBuffer();
        try{
            buf.append("JSP/Servlet Error (Catched by BaseChannelServlet)\n");
            buf.append(customMsg);
            buf.append("\nRequest URI: " + req.getRequestURI());
            String user = req.getRemoteUser();
            if(user != null){
                buf.append("\nUser: " + user);
            }

            buf.append("\nUser Location: " + req.getRemoteHost() + "(" + req.getRemoteAddr() + ")");

            Logging.err.println(buf);
            e.printStackTrace(Logging.err);

            java.io.PrintWriter out = res.getWriter();
            res.setContentType("text/html;charset=EUC_KR");
            out.println("<html><head><title>BaseAbstractServlet Default Error Dispatched Page</title></head><body bgcolor=white><xmp>");
            out.println(buf.toString());
            e.printStackTrace(out);
            out.println("</xmp></body></html>");
            out.close();

        }catch(Exception ex){
        }
    }
}
