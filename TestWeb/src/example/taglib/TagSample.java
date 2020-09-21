package example.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class TagSample	extends TagSupport {

	private static final long serialVersionUID = 1L;

	public int doStartTag()
		throws JspException
    {
        try {
            pageContext.getOut().print(calGuGuDan());
        } catch(IOException ioe) {
          throw new JspTagException("Error:  IOException while writing to the user");
        }
        return SKIP_BODY;
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