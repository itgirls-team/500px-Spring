<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="/static/js/jquery.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/wow.js"></script>
    <script src="/static/js/jquery.mb.YTPlayer.js"></script>
    <script src="/static/js/isotope.pkgd.js"></script>
    <script src="/static/js/imagesloaded.pkgd.js"></script>
    <script src="/static/js/jquery.flexslider.js"></script>
    <script src="/static/js/owl.carousel.min.js"></script>
    <script src="/static/js/smoothscroll.js"></script>
    <script src="/static/js/jquery.magnific-popup.js"></script>
    <script src="/static/js/jquery.simple-text-rotator.min.js"></script>
    <script src="/static/js/plugins.js"></script>
    <script src="/static/js/main.js"></script>
      <!-- Default stylesheets-->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Template specific stylesheets-->
    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/font-awesome.min.css" rel="stylesheet">
    <link href="/static/css/et-line-font.css" rel="stylesheet">
    <link href="/static/css/flexslider.css" rel="stylesheet">
    <link href="/static/css//owl.carousel.min.css" rel="stylesheet">
    <link href="/static/css/owl.theme.default.min.css" rel="stylesheet">
    <link href="/static/css/magnific-popup.css" rel="stylesheet">
    <link href="/static/css/simpletextrotator.css" rel="stylesheet">
    <!-- Main stylesheet and color file-->
    <link href="/static/css/styleNew.css" rel="stylesheet">
        <link href="/static/css/styles.css" rel="stylesheet">
    <link id="color-scheme" href="/static/css/default.css" rel="stylesheet"> 
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="static/js/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
<title>Upload post</title>
<style type="text/css">
 .home-Btn{
	position:absolute;
	top:30px;
	right:530px;
	font-size: 20px;
	color:white;
	font-weight: bold;
}
.posts-Btn{
	position:absolute;
	top:30px;
	right:400px;
	font-size: 20px;
	color:white;
	font-weight: bold;
}
.form{
text-align: center;
margin:200px;
}
</style>
</head>
<body data-spy="scroll" data-target=".onpage-navigation" data-offset="60">
	 <main>
	  <c:if test="${ sessionScope.user == null }">
			<c:redirect url="/page/login"></c:redirect>
	 </c:if>
      <div class="page-loader">
        <div class="loader">Loading...</div>
      </div>
      <nav class="navbar navbar-custom navbar-fixed-top navbar-transparent" role="navigation">
        <div class="container" style="background-color: black; width: 100%;">
          <div class="navbar-header">
           <div class="container-up-right">
	           <jsp:include page="header.jsp"></jsp:include>
				<a class="home-Btn" style="color:white;" href="/page/main">Home</a>
				<a class="posts-Btn" style="color:white;" href="/page/posts">Posts</a>	
			</div>
			  <a href="/newsfeed"><h1 style="color:white;">500px</h1></a>
		</div>
        </div>
      </nav>
        <div class="hero-slider">
            <li class="bg-dark">
              <div class="container">
              </div>
            </li>
        </div>
        
         <div class="form"> 
           	<h1>Upload file</h1>
           	<c:if test="${ requestScope.error != null }">
			<h1 style="color: red">Sorry, upload of post unsuccessful. Reason: ${requestScope.error }</h1>
			</c:if>
           	<c:if test="${ requestScope.emptyDescriptionField != null }">
					<h1 style="color: red">Sorry, upload new post unsuccessful. Reason: ${requestScope.emptyDescriptionField }</h1>
			</c:if>
			<form  class="upload" action="/upload" method="POST" enctype="multipart/form-data">
			Description<input type="text" name="description" required><br>
			Tags<input type="text" name="tags"><br>
			<input type="file" id="file" name="failche" required accept="upload/*">
			<input type="submit" value="Upload now">
			</form>
		</div>
        <div class="scroll-up"><a href="#totop"><i class="fa fa-angle-double-up"></i></a></div>
    </main>   

</body>
</html>