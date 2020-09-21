package example.framework;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public abstract class FrameworkServlet extends HttpServlet {

	public FrameworkServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("doGet ó�� �� ����ó�� ");
		execute(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("doPost ó�� �� ����ó�� ");
		execute(req, res);
	}

	protected abstract void execute(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException;

}