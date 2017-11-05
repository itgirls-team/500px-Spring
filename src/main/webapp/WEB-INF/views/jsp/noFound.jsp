<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>No result of search</title>
<style>
.form{
text-align: center;
margin:200px;
}
</style>
</head>
<body>
 <div class="form"> 
            <c:if test="${ requestScope.noUser != null }">
			<h4 style="color: red">Sorry, ${requestScope.noUser}. If you want to search user use '@' and if you want to search by tag only write tag!</h4>
			<form  action="/main">
					<input type="submit" value="Try again!"><br>
			</form>
			</c:if>
			
			<c:if test="${ requestScope.noTag != null }">
			<h4 style="color: red">Sorry, ${requestScope.noTag}. If you want to search user use '@' and if you want to search by tag only write tag!</h4>
			<form  action="/main">
					<input type="submit" value="Try again!"><br>
			</form>
			</c:if>
		 	
 </div>
</body>
</html>