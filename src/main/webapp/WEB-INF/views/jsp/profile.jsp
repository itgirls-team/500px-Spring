<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Profile</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <link rel="apple-touch-icon" sizes="57x57" href="assets/images/favicons/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="assets/images/favicons/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="assets/images/favicons/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="assets/images/favicons/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="assets/images/favicons/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="assets/images/favicons/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="assets/images/favicons/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="assets/images/favicons/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="assets/images/favicons/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192" href="assets/images/favicons/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/images/favicons/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="assets/images/favicons/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicons/favicon-16x16.png">
    <link rel="manifest" href="/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="assets/images/favicons/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  	<style>
input[type=text] {
    width: 130px;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    background-color: white;
    background-image: url('searchicon.png');
    background-position: 10px 10px; 
    background-repeat: no-repeat;
    padding: 12px 20px 12px 40px;
    -webkit-transition: width 0.4s ease-in-out;
    transition: width 0.4s ease-in-out;
}
input[type=text]:focus {
    width: 100%;
}
    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: 1200px}
    
    /* Set gray background color and 100% height */
    .sidenav {
      background-color:  #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #666;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;} 
    }
	
.mainContent .section1 .section1Content {
	font-family: ProximaNova;
	font-size: 14px;
	font-weight: 100;
	color:  rgba(80,80,80,1.00);
}
.mainContent .section1 .section1Content span {
	color: rgba(50,50,50,1.00);
	font-family: sans-serif;
}

</style>
</head>
<body>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand">500px</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
       <li><a href="/newsfeed">Newsfeed</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
       <li><form action="<c:url value="/search" />" >
		<input type="search" name="search" placeholder="Search.."  style="width: 170px; height: 20px; pading: 20px; margin: 20px; border-radius: 20%;"></form></li>
		<li><img src="/avatar"  style="width: 50px; height: 50px; border-radius: 50%;" ></li>
       <li><a href="/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
     
      </ul>
    
    </div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row content">
   	 <div class="col-sm-3 sidenav">
 		<h2>Welcome <c:out value="${searchUser.userName}"/> </h2><br>	
    		
      <ul class="nav nav-pills nav-stacked">
       			<li><form action="albums">
				<input type="hidden" name="searchUser" value="${searchUser.userName}" />
				<input class="btn btn-d btn-circle btn-block" type="submit" value="Albums" />
				</form></li>		    
      </ul><br>
     </div>
    
   <div class="col-sm-9">
   
     <section class="mainContent"> 
	  <section class="section1">
	    <hr class="sectionTitleRule">
	    <hr class="sectionTitleRule2">
	    <div class="section1Content">
	    <img id="profilePicture"
		src="/avatar?profilePicture=${searchUser.profilePicture}"
		style="width: 140px; height: 140px; border-radius: 50%;">
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
  </div>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>