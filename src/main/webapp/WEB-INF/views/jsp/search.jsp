<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<c:choose>
		<c:when test="${posts == null }">
			<c:choose>
				<c:when test="${searchUser != null }">
					<h1>User found :</h1>
					<a href="/profile?searchUsername=${searchUser.userName}">${searchUser.userName}</a>
				</c:when>
				<c:otherwise>
					<h1>no results found</h1>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
		<c:forEach items="${posts}" var="post">
			<a href="post?postId=${post.id}"><img width="100"
				src="/showPosts?postId=${post.id}"></a>
		</c:forEach>
		</c:otherwise>
	</c:choose>
	<br>
	
</body>
</html>