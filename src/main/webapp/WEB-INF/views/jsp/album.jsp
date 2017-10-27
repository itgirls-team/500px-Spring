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
 		<h1>Albums</h1>
 		<form action="/page/addNewAlbum">
				<input type="submit" value="Create new album">
		</form>
		<c:forEach items="${sessionScope.user.albumsOfUser}"  var="a">	
		<div id="albums">
			<img id="albumCover" src="cover">
			<a href = "/posts?albumId=${a.id}">${a.category}</a>
		</div>
		</c:forEach>	
</body>
</html>