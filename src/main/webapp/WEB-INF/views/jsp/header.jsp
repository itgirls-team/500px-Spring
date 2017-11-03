
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<style type="text/css">
	 .img{
		position:absolute;
	    top:0;
	    right:5px;
	 }
	 .search{
	 	position:absolute;
	    top:5px;
	    right:200px;
	 }
	  .welcome{
	 	position:absolute;
	    top:30px;
	    right:50px;
	    font-size: 10px;
	    color:white;
	 }
	  .logout{
	 	position:absolute;
	    top:50px;
	    right:330px;
	 }
	</style>
	</head>
	<body>
	
		<div class="header">
		   <%--  <form action="<c:url value="/search" />" method="get">
				<div class="search"><h5 style="color:white;">Search : <input type="search" name="search" placeholder="Search..."> </h5></div>
			</form> 
			
			<br>
			<form  action="/logout" method="post">
				<input class="logout" type="submit" value="Logout">
			</form>
			<img class="img" id="avatar" src="/avatar">
			<h3 class="welcome">Welcome, ${ sessionScope.user.userName }</h3> --%>
			
			
			 <div class="collapse navbar-collapse" id="myNavbar">
			      <ul class="nav navbar-nav">
			       <li><a href="/newsfeed">Newsfeed</a></li>
			      </ul>
			      <ul class="nav navbar-nav navbar-right">
			       <li><form action="<c:url value="/search" />" >
					<input type="search" name="search" placeholder="Search.."  style="width: 170px; height: 20px; pading: 20px; margin: 20px; border-radius: 20%;"></form></li>
					<li><a href="/page/main"><img src="/avatar"  style="width: 50px; height: 50px; border-radius: 50%;" ></a></li>
			       <li><a href="/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
			      </ul>
    		</div>
		</div>
	</body>
</html>
