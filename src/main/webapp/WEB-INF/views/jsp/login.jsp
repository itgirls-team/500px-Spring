<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" href="/static/css/login.css">
<title>Login</title>
</head>
<body>
<%-- <c:if test="${ requestScope.error == null }">
			<h1  style="color: white">Welcome, please login!</h1>
</c:if> --%>
<c:if test="${ requestScope.error != null }">
			<h4 style="color: red">Sorry, Reason: ${requestScope.error }.Please try again!</h4>
</c:if>

 <div id="login">	
	<form  action="/login" name='form-login' method="post">
		<h1 style="color: white">Login Form</h1>
				<span class="fontawesome-user"></span>
	          <input type="text" name="username" placeholder="Username">
	       
	        <span class="fontawesome-lock"></span>
	          <input type="password" name="password" placeholder="Password">
	        
	        <input type="submit" value="Login">
	        <br>
			If you do not have an account, please register <a  style="color: white" href="/page/register"> here</a>
</form>
</div>
</body>
</html>