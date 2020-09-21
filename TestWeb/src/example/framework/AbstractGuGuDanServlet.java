package example.framework;

//Import required java libraries
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

//Extend HttpServlet class
public class AbstractGuGuDanServlet extends FrameworkServlet {

	private String message =  null;

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		message = "<h2>" + request.getParameter("text1") + "</h2><br>";
		message += "<h2>" + request.getParameter("text2") + "</h2><br>";
		message +=  calGuGuDan();

		PrintWriter out = response.getWriter();
		out.println(message);
		out.close();
	}
	
	// 구구단 계산 ////////////////////////////////////////////     
	private String calGuGuDan(){
		
		String message =  "<table>";
		for(int i= 1; i <=9 ; i++){
			message += "<tr>";
			for(int j = 1; j <=9 ; j++)
				message += ("<td>" + i + " * " + j + " = " + (i*j) + "</td>") ;
			
			message += "</tr>";
		}
		message += "</table>";
		
		return message;
			
	}
}