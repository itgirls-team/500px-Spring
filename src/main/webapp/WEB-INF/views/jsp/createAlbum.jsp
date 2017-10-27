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
<c:if test="${ requestScope.albumAlreadyExists != null }">
			<h4 style="color: red">Sorry, Reason: ${requestScope.albumAlreadyExists }.Please create new album!</h4>
</c:if>
<c:if test="${ requestScope.emptyCategoryField != null }">
			<h1 style="color: red">Sorry, create new album unsuccessful. Reason: ${requestScope.emptyCategoryField }</h1>
</c:if>
	<form action="/createAlbum" method="post" >
			Add category for your album<br>
			<input type="text" name="category"><br>
			<input type="submit" value="Create Album"><br>
	</form>
	
</body>
</html>