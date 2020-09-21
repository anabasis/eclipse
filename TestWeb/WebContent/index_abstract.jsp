<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
	function goSubmit(str) {
		alert(str);
		var frm = document.form1;
		if (str == "POST")
			frm.method = "post";
		else
			frm.method = "get";
		frm.action = "AbstractGuGuDanServlet.tg";
		frm.submit();
		return;
	}
</script>
</head>
<body>

	Abstract Excute 실행
	<br />
	<table>
		<tr>
			<td>

				<form name=form1 id=form1>
					<input type="button" name="doGet" value="GET"
						onClick="javascript:goSubmit(this.value);" /> <input
						type="button" name="doPost" value="POST"
						onClick="javascript:goSubmit(this.value);" /><br /> <input
						type="text" name="text1" value="text1" readonly /> </br> <input
						type="text" name="text2" value="text2" readonly />
				</form>

			</td>
		</tr>
	</table>

</body>
</html>