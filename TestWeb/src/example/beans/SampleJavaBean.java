package example.beans;

public class SampleJavaBean {

	String title = "";

	String content = "";

	String gugudan = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setGugudan(String gugudan) {
		this.gugudan = gugudan;
	}

	public String getGugudan() {
		gugudan = calGuGuDan();
		return gugudan;
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
