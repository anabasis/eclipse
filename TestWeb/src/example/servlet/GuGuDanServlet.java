package example.servlet;

//Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

//Extend HttpServlet class
public class GuGuDanServlet extends HttpServlet {

	private String message =  null;

	public void init() throws ServletException {
		// Do required initialization
		System.out.println("Servlet Init!!!");
		message = "Sevlet Init<br>";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		message += "<h1>doGet</h1><br>";
		message += "<h2>" + request.getParameter("text1") + "</h2><br>";
		message += "<h2>" + request.getParameter("text2") + "</h2><br>";
		message +=  calGuGuDan();

		PrintWriter out = response.getWriter();
		out.println(message);
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		
		message += "<h1>doPost</h1><br>";
		message += "<h2>" + request.getParameter("text1") + "</h2><br>";
		message += "<h2>" + request.getParameter("text2") + "</h2><br>";
		message +=  calGuGuDan();

		PrintWriter out = response.getWriter();
		out.println(message);
		out.close();
		
	}

	
	public void destroy() {
		// do nothing.
		message = null;
		System.out.println("Servlet Destroy!!!");
	}
	
	
	// 구구단 계산 ////////////////////////////////////////////     
	public String calGuGuDan(){
		
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