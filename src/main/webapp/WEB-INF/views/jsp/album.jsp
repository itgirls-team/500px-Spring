<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style>
	 .bg-img-c {
	  -webkit-background-size: cover;
	  background-size: cover;
	  background-position: center;
	 }
	 .img {
	  display: inline-block;
	 }
	 .img:before {
	  content: '';
	  display: block;
	  padding-top: 75%;
	 }
</style>

</head>
<body>
 		<h1>Albums</h1>
		<form action="/page/addNewAlbum">
				<input type="submit" value="Create new album">
		</form>
		<c:forEach items="${sessionScope.user.albumsOfUser}"  var="album">	
		<span>
			<div id="albumCover" class="img bg-img-c" style="width: 400px; background-image: url(/fetch-cover?id=${album.id})"></div>
			<a href = "/posts?albumId=${album.id}">${album.category}</a>
		</span>
		</c:forEach>	
</body>
</html>