<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="WEB-INF/tld/custom.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Sample</title>
</head>
<body>
	Sample 1(Java Standard Tag Library)
	<br />

	<table>
		<tr>
			<th>Value</th>
			<th>Square</th>
		</tr>
		<c:forEach var="x" begin="1" end="9" step="1">
			<tr>
				<c:forEach var="y" begin="1" end="9" step="1">
					<td>
					<c:out value="${x}" /> *
					<c:out value="${y}" /> =
					<c:out value="${x * y}" />
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>


	<br /> Sample 2 (Java Custom Tag Library)
	<br />
	
	<ct:GuGuDan />
	
	<br /> Sample 2 (Java Custom Tag Library)
	<br />
	<ct:BodyGuGuDan >
	Body Message
	</ct:BodyGuGuDan>


</body>
</html>