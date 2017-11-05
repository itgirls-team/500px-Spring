<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/static/css/register.css">
<title>Register</title>
</head>
<body>
<%-- <c:if test="${ requestScope.error == null }">
			<h1>Welcome, please register!</h1>
</c:if> 
--%>
<c:if test="${ requestScope.error != null }">
			<h1 style="color: red">Sorry, registration unsuccessful. Reason: ${requestScope.error }</h1>
</c:if>

<div id="register">
<form action="/register" method="post" enctype="multipart/form-data" name='form-register'>
	<div style="color: white">Username</div><input type="text" name="username"  required><br>
	<div style="color: white">Password</div><input type="password" name="password"  required ><br>
	<div style="color: white">Confirm password</div><input type="password" name="confpassword"  required ><br>
	<div style="color: white">First Name</div><input type="text" name="firstname"  required><br>
	<div style="color: white">Last Name</div><input type="text" name="lastname"  required><br>
	<div style="color: white">Email</div><input type="email" name="email"  required ><br>
	<div style="color: white">Description</div> <input type="text" name="description"  required><br>
	<div style="color: white">Profile picture</div><input type="file" name="avatar" accept="image/*"><br>
	<br>
	<input type="submit" value="Register">
	If you already have an account, please login <a style="color: white" href="/page/login"> here</a>.
</form>
</div>

</body>
</html>