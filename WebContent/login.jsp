<%@page import="web.SessionConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recipes Login</title>
</head>
<body>

<div align="center">
<form action="LoginServlet">
 <div>
 	<label>Username:</label>
 	<input type="text" name="username"/>
 </div>
 <div>
 	<label> Password:</label>
 	<input type="password" name="password"/>
 </div>
 <input type="submit"/>
</form>
<% if (request.getAttribute(SessionConstants.LOGIN_ERROR) != null){ %>
<div class="error">
	<%= request.getAttribute(SessionConstants.LOGIN_ERROR) %>
</div>
<%}%>
</div>
</body>
</html>