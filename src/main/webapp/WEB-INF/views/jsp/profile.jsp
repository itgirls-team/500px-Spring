<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	<img id="profilePicture"
		src="/avatar?profilePicture=${searchUser.profilePicture}"
		style="width: 300px; heigth: 100px;">
	<div class="main">
		<p>${searchUser.userName}</p>
		<p>${searchUser.firstName}</p>
		<p>${searchUser.lastName}</p>
		<p>${searchUser.description}</p>
		<p>${searchUser.email}</p>

		<form action="/albums">
			<input type="hidden" name="searchUser" value="${searchUser.userName}" />
			<input type="submit" value="Albums" />
		</form>

        <c:if test = "${noFollowed}">
		<form action="/followInAnotherPage" method="POST">
			<input style="background-color: green" type="submit" value="Follow" />
		</form>
		</c:if>
		
		<c:if test = "${!noFollowed}">
		<form action="/unfollowInAnotherPage" method="POST">
			<input style="background-color: red" type="submit" value="Unfollow" />
		</form>
		</c:if>
		

	</div>
</body>
</body>
</html>