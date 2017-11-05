<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Post</title>
	<script src="/static/js/posts.js"></script>
	<script src="/static/js/likeComment.js"></script>
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
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
    <style>
	 .bg-img-c {
	  -webkit-background-size: cover;
	  background-size: cover;
	  background-position: center;
	 }
	 .img {
	  display: inline-block;
	 }
	 .postPhoto{
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
.postDesc{
text-align: center;
}
.commentTable{
 margin: 0 auto;
}
button{
font-weight: bold;
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
      <div class="main">
        	<img class="postPhoto" src="<c:url value = "/postId/${sessionScope.postId}"/>" class="profilePic">

			<input type="hidden" id="post-id-container" value="${sessionScope.postId}">
			
			<c:set var="isLiked" value="false"/>
			<c:forEach items="${sessionScope.post.usersWhoLike}"  var="liker">
				<c:if test="${liker.id==sessionScope.user.id}">
					<c:set var="isLiked" value="true"/>
				</c:if>
			</c:forEach>
			
			<div class="likeBtn">
				<c:if test="${isLiked==true}">
					<button style="background-color: grey" id="likebutton" onclick="likePost()">Unlike</button></c:if>
				<c:if test="${isLiked==false}">
					<button style="background-color: #6CD7FE" id="likebutton" onclick="likePost()">Like</button>
				</c:if>
			</div>
			
			<c:set var="isDisliked" value="false"/>
			<c:forEach items="${sessionScope.post.usersWhoDislike}"  var="disliker">
				<c:if test="${disliker.id==sessionScope.user.id}">
					<c:set var="isDisliked" value="true"/>
				</c:if>
			</c:forEach>
			
			<div class="dislikeBtn">
			<c:if test="${isDisliked==true}">
				<button style="background-color: grey" id="dislikebutton" onclick="disLikePost()">Undislike</button>
			</c:if>
			<c:if test="${isDisliked==false}">
				<button style="background-color: #6CD7FE" id="dislikebutton" onclick="disLikePost()">Dislike</button>
			</c:if>
			</div>
		<div class="postDesc">	
			<h5>Description :  ${sessionScope.post.description} </h5><br>
			<h5>Likes: </h5><h5 id="number-of-likes-container"> ${sessionScope.post.usersWhoLike.size()} </h5>
			<h5>Dislikes: </h5><h5 id="number-of-dislikes-container"> ${sessionScope.post.usersWhoDislike.size()} </h5>
			<h5>Date : ${sessionScope.post.dateOfUpload} </h5><br>
			<br>
			<h5>Tags : </h5>
			<c:forEach items="${sessionScope.post.tagsOfPost}" var="tag">
					<h6>${tag.title}</h6>
			</c:forEach>
			
			<br>
			<h5>Who like post: </h5><br>
			<c:forEach items="${sessionScope.post.usersWhoLike}" var="user">
				<h5 id="people-who-liked-container"> ${user.userName} </h5>	
			</c:forEach>
			
			<br>
			<h5>Who dislike post: </h5><br>
			<c:forEach items="${sessionScope.post.usersWhoDislike}" var="user">
					<h5 id="people-who-disliked-container"> ${user.userName} </h5>	
			</c:forEach>
			<br>
			 <h5> Comments : </h5> <br>
			<p>Add comment</p><br>
			<textarea id="commentdesc" rows="2" required></textarea><br>
			<button onclick="postComment()">Submit comment</button><br>
			<table class="commentTable" border="1" id="commentstable"> 
				<c:forEach items="${sessionScope.post.commentsOfPost}" var="comment">
				<input type="hidden" id="comment-id-container" value="${comment.id}"> 
				<tr>
					<td>Username : ${comment.userName} </td> 
					<td>Description : ${comment.description} </td> 
					<td>Date : ${comment.dateAndTimeOfUpload} </td>
					<td><h5>Likes: </h5><h5 id="number-of-commentslikes-container-${comment.id}"> ${comment.usersWhoLikeComment.size()} </h5></td>
					<td><h5>Dislikes: </h5><h5 id="number-of-commentsdislikes-container-${comment.id}"> ${comment.usersWhoDislikeComment.size()} </h5></td>
					
							<c:set var="isCommentLiked" value="false"/>
					<c:forEach items="${comment.usersWhoLikeComment}"  var="liker">
						<c:if test="${liker.id==sessionScope.user.id}">
							<c:set var="isCommentLiked" value="true"/>
						</c:if>
					</c:forEach>
					<c:if test="${isCommentLiked==true}">
						<td><button style="background-color: grey" id="commentlikebutton-${comment.id}" onclick="likeComment( ${comment.id} )">Unlike</button></td>
					</c:if>
					<c:if test="${isCommentLiked==false}">
						<td><button style="background-color: #6CD7FE" id="commentlikebutton-${comment.id}" onclick="likeComment( ${comment.id} )">Like</button></td>
					</c:if>
					
					<c:set var="isCommentDisliked" value="false"/>
					<c:forEach items="${comment.usersWhoDislikeComment}"  var="disliker">
						<c:if test="${disliker.id==sessionScope.user.id}">
							<c:set var="isCommentDisliked" value="true"/>
						</c:if>
					</c:forEach>
					<c:if test="${isCommentDisliked==true}">
						<td><button style="background-color: grey" id="commentdislikebutton-${comment.id}" onclick="disLikeComment(${comment.id})">Undislike</button></td>
					</c:if>
					<c:if test="${isCommentDisliked==false}">
						<td><button style="background-color: #6CD7FE" id="commentdislikebutton-${comment.id}" onclick="disLikeComment(${comment.id})">Dislike</button></td>
					</c:if>
					
				</tr>
			   </c:forEach>
		   </table> 
	</div>
       <div class="scroll-up"><a href="#totop"><i class="fa fa-angle-double-up"></i></a></div>
      </div>
    </main>	
</body>
</html>