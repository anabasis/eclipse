<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.naming.*,javax.rmi.* , java.text.*, java.util.Properties"%>
<%@ page import="example.ejb.*"%>
<%@ page import="test.ejb.*"%>

<%
	int servercnt = 40;
int callcnt   = 10;

TestEjbClient client =new TestEjbClient();


System.out.println("EJB NoCache Start >>>>>>>>>");		

for(int i=1;i<=servercnt;i++) 	client.callEJB(i, callcnt);


System.out.println("EJB Cache Start >>>>>>>>>");	



for(int i=1;i<=servercnt;i++)   client.callEJBCache(i, callcnt);		

System.out.println("test End >>>>>>>>>");
System.out.println("##################");
%>