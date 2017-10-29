<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Posts</h1>
	
	<select onchange="location = this.value;">
			 <option value="<c:url value="/${currentPage}/date" />" <c:if test="${sessionScope.sort eq \"date\" }"> selected </c:if>>SortByDate</option>
			 <option value="<c:url value="/${currentPage}/like" />" <c:if test="${sessionScope.sort eq \"like\" }"> selected </c:if>>SortByLikes</option>
	</select>
	
	 <c:forEach items="${posts}" var="post">
	 	<a href="/post?postId=${post.id}"><img width="300" src="/showPosts?postId=${post.id}"></a>
	 </c:forEach>
	
	<c:if test = "${hideUploadPost != null && !hideUploadPost}">
	<form action="/upload" method="get">
		<input type="submit" value="Upload Post">
	</form>
	</c:if>
</body>
</html>