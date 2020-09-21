<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.naming.*,javax.rmi.* , java.text.*, java.util.Properties"%>
<%@ page import="example.ejb.*"%>


<%
	try {

		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		p.put(Context.PROVIDER_URL, "t3://localhost:7000");
		InitialContext ctx = new InitialContext(p);
		
		Object obj = (Object) ctx.lookup("GuGuDan");
		//Be good and use RMI remote object narrowing as required by the EJB specification.
		GuGuDanHome ejbHome = (GuGuDanHome) PortableRemoteObject.narrow(obj, GuGuDanHome.class);
		
		//Use the HelloHome to create a HelloObject
	    GuGuDanRemote ejbRemote = ejbHome.create();
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Sample</title>
</head>
<body>
	Sample 1
	<br />
	<%=ejbRemote.getTitle("TITLE") %>
	<%=ejbRemote.getContent("CONTENT") %>
	<%=ejbRemote.calGuGuDan() %>

</body>
</html>
<%
	} catch (Exception e) {
		e.printStackTrace();
	}
%>