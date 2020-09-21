package example.taglib;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

public class BodyTagSample extends SimpleTagSupport {

	StringWriter sw = new StringWriter();

	public void doTag() throws JspException, IOException {
		getJspBody().invoke(sw);
		getJspContext().getOut().println(sw.toString() + "<br/>");
		getJspContext().getOut().println(calGuGuDan());
	}

	// 구구단 계산 ////////////////////////////////////////////
	public String calGuGuDan() {

		String message = "<table>";
		for (int i = 1; i <= 9; i++) {
			message += "<tr>";
			for (int j = 1; j <= 9; j++)
				message += ("<td>" + i + " * " + j + " = " + (i * j) + "</td>");

			message += "</tr>";
		}
		message += "</table>";

		return message;

	}
}