<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
<c:if test="${ sessionScope.user == null }">
			<c:redirect url="/page/login"></c:redirect>
</c:if>

<img id="profilePicture" src="/avatar?profilePicture=${searchUser.profilePicture}" style = "width: 100px ; heigth : 100px; ">
<div class="main">
	<p>${searchUser.userName}</p>
	<p>${searchUser.firstName}</p>
	<p>${searchUser.lastName}</p>
	<p>${searchUser.description}</p>
	<p>${searchUser.email}</p>
	
	<form action="/albums?searchUser=${searchUser.userName}">
			<input type="submit" value="Albums">
	</form>

	<form action="/following?searchUser=${searchUser.userName}">
				<input type="hidden" name="pageToRedirect" value="following" /> 	
				<input type="submit" value="Following">
	</form>
	
	<form action="/followers">
				<input type="hidden" name="pageToRedirect" value="followers" />	
				<input type="submit" value="Followers">
	</form>
</div>
				
</body>	
</body>
</html>