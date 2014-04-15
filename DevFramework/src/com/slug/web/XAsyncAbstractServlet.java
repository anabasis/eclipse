package com.slug.web;

/**
 * @(#) BaseAbstractServlet.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author 김동식, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slug.logging.Logging;

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

public abstract class XAsyncAbstractServlet extends HttpServlet {

	private final List nodes = new ArrayList();

	private final String encode;

	/**
	 * BaseAbstractServlet 생성자
	 */
	public XAsyncAbstractServlet() {
		super();
		this.encode = "UTF-8";

	}

	public XAsyncAbstractServlet(String encode) {
		super();
		this.encode = encode;
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
	protected abstract void procXAsyncService(HttpServletRequest req,
			HttpServletResponse res);

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
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		procXAsyncService(req, res);
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

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		procXAsyncService(req, res);
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
	protected boolean isMultipart(HttpServletRequest req) {
		String contentType = null;
		String multipartContentType = "multipart/form-data";
		contentType = req.getContentType();

		return (contentType != null && contentType.length() > 19 && multipartContentType
				.equals(contentType.substring(0, 19))) ? true : false;
	}

	protected void printResponseWrite(HttpServletRequest req,
			HttpServletResponse res, String resStr) {

		res.setHeader("Cache-Control", "no-cache");
		res.setContentType("application/json; charset=" + this.encode);

		PrintWriter pw = null;
		OutputStream os = null;// res.getOutputStream();
		OutputStreamWriter osw = null;// new OutputStreamWriter(os ,
										// this.encode);

		try {
			os = res.getOutputStream();
			osw = new OutputStreamWriter(os, this.encode);
			pw = new PrintWriter(osw);
			pw.write(resStr);
			// Logging.debug.println(resStr);

		} catch (Exception e) {
			resStr = "{\"success\":false,\"responseMsg\":\"" + e.getMessage()
					+ "\"}";
			// "success":true,"data":{"responseMsg":"Sucess"},"trxResultCode":"Y","trxResultMsg":"Success To Request!"}
			e.printStackTrace(Logging.err);
			pw.write(resStr);
		} finally {
			pw.flush();
			pw.close();
		}

	}

	protected void printErrorWrite(HttpServletRequest req,
			HttpServletResponse res, String customMsg, Exception e) {

		res.setHeader("Cache-Control", "no-cache");
		res.setContentType("application/json; charset=" + this.encode);

		PrintWriter pw = null;
		OutputStream os = null;// res.getOutputStream();
		OutputStreamWriter osw = null;// new OutputStreamWriter(os ,
										// this.encode);

		StringBuffer buf = new StringBuffer();
		try {

			buf.append("{\"success\":false,\"responseMsg\":\"");
			buf.append(customMsg + "<br>");
			buf.append(e.getMessage() + "<br>");
			// buf.append("\"},\"DATA_LIST\":[{\"responseMsg\":\"");
			// buf.append(customMsg+"<br>");
			// buf.append(e.getMessage()+"<br>");
			// buf.append("\"}], \"TOTAL_COUNT\":1}");
			buf.append("\"}");

			os = res.getOutputStream();
			osw = new OutputStreamWriter(os, this.encode);
			pw = new PrintWriter(osw);
			pw.write(buf.toString());

			Logging.err.println(buf.toString());
			e.printStackTrace(Logging.err);

		} catch (Exception ex) {
			// buf.append("{\"success\":false,\"data\":{\"responseMsg\":\""+ex.getMessage()+"\"}}");
			buf.append("{\"success\":false,\"responseMsg\":\"" + e.getMessage()
					+ "\"}");
			e.printStackTrace(Logging.err);
			pw.write(buf.toString());
		} finally {
			pw.flush();
			pw.close();
		}

	}
}
