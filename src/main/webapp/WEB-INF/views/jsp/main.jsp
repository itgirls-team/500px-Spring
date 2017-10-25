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
			<c:redirect url="login.jsp"></c:redirect>
</c:if>
<jsp:include page="header.jsp"></jsp:include>
<img id="profilePicture" src="avatar">
<div class="main">
	<form action="/page/description">
				<input type="submit" value="Description">
	</form>
	<form action="/page/contact">
				<input type="submit" value="Contacts">
	</form>
	<form action="/page/albums">
			<input type="submit" value="Albums">
	</form>

	<form action="/page/following">
				<input type="hidden" name="pageToRedirect" value="/page/following" /> 	
				<input type="submit" value="Following">
	</form>
	
	<form action="/page/followers">
				<input type="hidden" name="pageToRedirect" value="/page/followers" />	
				<input type="submit" value="Followers">
	</form>
</div>
				
</body>	
</body>
</html>