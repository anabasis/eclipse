package example.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class SampleFilter implements Filter {
	private FilterConfig filterConfig;

	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws ServletException, IOException {
		String userAgent = "";
		// Chrome vs MSIE

		HttpServletRequest httpReq = (HttpServletRequest) req;
		userAgent = httpReq.getHeader("User-Agent");
		/*
		for (Enumeration en = httpReq.getHeaderNames(); en.hasMoreElements();) {
			String name = (String) en.nextElement();
			System.out.println(name + "--> " + httpReq.getHeader(name));
		}
		*/

		if (userAgent.indexOf("Chrome") > 0) {
			// 여기 들어왔다면 접근 가능
			chain.doFilter(req, resp);
		} else {

			// 여기 들어왔다면 접근 불가
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html; charset=euc-kr");

			out.println("<HTML>");
			out.println("<HEAD><TITLE>Error!!!!!</TITLE></HEAD>");
			out.println("<BODY>");
			out.println("<H3>Do Not Have Access to he Page</H3>");
			out.println("<H3>Web Browser is : Not Chrome</H3>");
			out.println("</BODY>");
			out.println("</HTML>");
			
			out.flush();
			out.close();
		}

	}

	public void destroy() {

	}
}
