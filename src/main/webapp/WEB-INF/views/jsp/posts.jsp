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

<<<<<<< HEAD
	<h1>Posts</h1>

	
	 <c:forEach items="${posts}" var="post">
	 	<a href="/post?postId=${post.id}"><img src="/showPosts?postId=${post.id}"  style = "width: 300px ; heigth : 100px; " ></a>
	 </c:forEach>
	
	<%--  <c:if test = "${hideUploadPost != null && !hideUploadPost}"> --%>
	<form action="/upload" method="get">
		<input type="submit" value="Upload Post">
	</form>
	
	<select onchange="location = this.value;">
			 <option value="<c:url value="/${currentPage}/date" />" <c:if test="${sessionScope.sort eq \"date\" }"> selected </c:if>>SortByDate</option>
			 <option value="<c:url value="/${currentPage}/like" />" <c:if test="${sessionScope.sort eq \"like\" }"> selected </c:if>>SortByLikes</option>
	</select>
	
=======
<title>My albums</title>

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
	 .home-Btn{
	position:absolute;
	top:30px;
	right:530px;
	font-size: 20px;
	color:white;
	font-weight: bold;
}
.albums-Btn{
	position:absolute;
	top:30px;
	right:400px;
	font-size: 20px;
	color:white;
	font-weight: bold;
}
.sort{
	margin: 15px;
	display: inline-block;
	font-size: 20px;
}
.upload{
	margin: 15px;
	display: inline-block;
	font-size: 20px;
}
</style>
</head>
<body data-spy="scroll" data-target=".onpage-navigation" data-offset="60">
    <main>
      <div class="page-loader">
        <div class="loader">Loading...</div>
      </div>
      <nav class="navbar navbar-custom navbar-fixed-top navbar-transparent" role="navigation">
        <div class="container" style="background-color: black; width: 100%;">
          <div class="navbar-header">
           <div class="container-up-right">
	           <jsp:include page="headerNew.jsp"></jsp:include>
				<a class="home-Btn" style="color:white;" href="/page/main">Home</a>
				<a class="albums-Btn" style="color:white;" href="/page/albums">Albums</a>	
			</div>
			 <h1 style="color:white;">500px</h1>
		</div>
        </div>
      </nav>
        <div class="hero-slider">
            <li class="bg-dark">
              <div class="container">
              </div>
            </li>
        </div>
      <div class="main">
        <section class="module-medium">
          <div class="container">
          <h1>Posts</h1>	
          <select class="sort" onchange="location = this.value;">
			 <option value="<c:url value="/${currentPage}/date" />" <c:if test="${sessionScope.sort eq \"date\" }"> selected </c:if>>SortByDate</option>
			 <option value="<c:url value="/${currentPage}/like" />" <c:if test="${sessionScope.sort eq \"like\" }"> selected </c:if>>SortByLikes</option>
		  </select>
		  <c:if test = "${hideUploadPost != null && !hideUploadPost}">
			<form class="upload" action="/upload" method="get">
				<input type="submit" value="Upload Post">
			</form>
		  </c:if>
		  
          <c:forEach items="${posts}" var="post">
            <ul class="works-grid works-grid-gut works-hover-d" id="works-grid">
              <li class="work-item illustration webdesign">
              	<a href="/post?postId=${post.id}">
                  	<div id="albumCover" class="work-image">
                  	 <img width="300" src="/showPosts?postId=${post.id}" alt="Portfolio Item"/>
            		 </div>
                  	 <div class="work-caption font-alt">
                    	<h3 class="work-title" style="font-weight: bold; font-size:50px"></h3>
                  	</div> 
                 </a>
              </li>
            </ul>
           </c:forEach>	 
          </div>
        </section>
        <div class="scroll-up"><a href="#totop"><i class="fa fa-angle-double-up"></i></a></div>
      </div>
    </main>   
>>>>>>> feature/fix-controllers
</body>
</html>