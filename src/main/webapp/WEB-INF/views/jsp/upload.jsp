<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<h1>Upload file</h1>
	
	<form  action="/upload" method="POST" enctype="multipart/form-data">
	Description<input type="text" name="description"><br>
	Tags<input type="text" name="tags"><br>
	<input type="file" id="file" name="failche" accept="upload/*">
	<input type="submit" value="Upload now">
	</form>

</body>
</html>