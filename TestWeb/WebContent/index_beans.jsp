<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Sample</title>
</head>
<body>
	<h1>Sample 1</h1>
	<br />
	<%
		example.beans.SampleJavaBean sjb = new example.beans.SampleJavaBean();
		sjb.setTitle("TEST");
		sjb.setContent("CONTENT");
	%>
	<%=sjb.getTitle()%><br />
	<%=sjb.getContent()%><br />
	<%=sjb.calGuGuDan()%><br />

	<h1>Sample 2</h1>
	<br />

	<jsp:useBean id="sjb1" class="example.beans.SampleJavaBean" scope="page" />
	<jsp:setProperty property="title" name="sjb1" value="TEST" />
	<jsp:setProperty property="content" name="sjb1" value="CONTENT" />
	<jsp:getProperty property="title" name="sjb1" /><br />
	<jsp:getProperty property="content" name="sjb1" /><br />
	<jsp:getProperty property="gugudan" name="sjb1" /><br />

	<h1>Sample 3</h1>
	<br />
	<jsp:useBean id="sjb3" class="example.beans.SampleJavaBean" scope="page" />
	<%
		sjb3.setTitle("TEST");
		sjb3.setContent("CONTENT");
	%>
	<%=sjb3.getTitle()%><br />
	<%=sjb3.getContent()%><br />
	<%=sjb3.calGuGuDan()%><br />

</body>
</html>