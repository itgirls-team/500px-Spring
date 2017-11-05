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
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
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
<title>User profile</title>
<style>
	 .bg-img-c {
	  -webkit-background-size: cover;
	  background-size: cover;
	  background-position: center;
	 }
	 .img {
	  display: inline-block;
	 }
	 #profilePicture{
		 display: block;
	   	 margin: 0 auto;
		 border-radius: 50px;
		 margin-top: 200px;
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
.posts-Btn{
	position:absolute;
	top:30px;
	right:400px;
	font-size: 20px;
	color:white;
	font-weight: bold;
}
.profilePic{
   display: block;
   margin: 0 auto;
   border-radius: 50px;
}
.likeBtn{
text-align:center;
}
.dislikeBtn{
text-align:center;
}
.col-sm-9{
text-align:center;
left:190px;
}
.forms{
 margin-bottom: 10px;
 padding:8px;
 text-align:center;
 width: 50px;
 top:20px;
}
.main{
background-color:#B8B1A7;
position: absolute;
top: 100px;
left: 0px;
width: 300px;
height:150%;
}
</style>
</head>
<body data-spy="scroll" data-target=".onpage-navigation" data-offset="60">
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
			</div>
			 <!-- <h1 style="color:white;">500px</h1> -->
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
	    <img id="profilePicture"
		src="/avatar?profilePicture=${searchUser.profilePicture}"
		style="width: 140px; height: 140px; border-radius: 50%;">
		<div class="col-sm-9">
     <section class="mainContent"> 
	  <section class="section1">
	    <h2 class="sectionTitle">Profile</h2>
	    <hr class="sectionTitleRule">
	    <hr class="sectionTitleRule2">
	    <div class="section1Content">
	     <c:if test = "${!isFollowing}">
		<form action="/followInAnotherPage" method="POST">
			<!-- <input style=" font-size : 10px; width: 70px; height: 30px; border-radius: 50%; background-color: #6495ED;"  type="submit"  value="Follow" /> -->
			<button style="font-size : 10px; width: 70px; height: 30px; border-radius: 50%; background-color: #6495ED;" type="submit" value="Unfollow"> Follow </button>
		</form>
		</c:if>
		
		<c:if test = "${isFollowing}">
		<form action="/unfollowInAnotherPage" method="POST">
			 <button style="font-size : 10px; width: 70px; height: 30px; border-radius: 50%; background-color: #FF0000;" type="submit" value="Unfollow"> Unfollow </button>
		</form>
		</c:if>
		
		<div class="likeBtn">
				<c:if test="${isLiked==true}">
					<button style="background-color: grey" id="likebutton" onclick="likePost()">Unlike</button></c:if>
				<c:if test="${isLiked==false}">
					<button style="background-color: blue" id="likebutton" onclick="likePost()">Like</button>
				</c:if>
		</div>
	      <p><span>Username :</span><c:out value="${searchUser.userName}"/></p>		
	      <p><span>Email :</span> <c:out value="${searchUser.email}"/></p>
	      <p><span>First Name :</span><c:out value="${searchUser.firstName}"/></p>
	      <p><span>Last Name :</span><c:out value="${searchUser.lastName}"/></p>
	      <p><span>Description :</span><c:out value="${searchUser.description}"/></p>
	   </div>
	  </section>        
    </section>
     </div>
	 </div>
 <div class="main">
	<div id="inner">
		<form class="forms" action="/albums">
					<input type="hidden" name="searchUser" value="${searchUser.userName}" />
					<input class="btn btn-d btn-circle" type="submit" value="Albums" />
		</form>
		</div>
		</div>
       <div class="scroll-up"><a href="#totop"><i class="fa fa-angle-double-up"></i></a></div>
</body>
</html>