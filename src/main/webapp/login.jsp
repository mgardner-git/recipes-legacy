<%@page import="org.recipes.util.SessionStuff"%>
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
<form action="doLogin" method="POST" >
 <div>
 	<label>Email Address:</label>
 	<input type="text" name="username"/>
 </div>
 <div>
 	<label> Password:</label>
 	<input type="password" name="password"/>
 </div>
 <input type="submit"/>
</form>

<% if (request.getAttribute(SessionStuff.LOGIN_ERROR) != null){ %>
<div class="error">
	<%= request.getAttribute(SessionStuff.LOGIN_ERROR) %>
</div>
<%}%>

</div>
</body>
</html>