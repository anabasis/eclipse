package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestJavaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException{

		httpServletResponse.setContentType("text/plain");
		PrintWriter out = httpServletResponse.getWriter();
		out.println("Hello Get World!!");
		out.close();
	}
	
	protected void doPost(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws IOException{

		httpServletResponse.setContentType("text/plain");
		PrintWriter out = httpServletResponse.getWriter();
		out.println("Hello Post World!!");
		out.close();
	}
}
