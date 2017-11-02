<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Home</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
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
		<li><a href = "/main"><img src="/avatar"  style="width: 50px; height: 50px; border-radius: 50%;" ></a></li>
       <li><a href="/logout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
     
      </ul>
    
    </div>
  </div>
</nav>
<div class="container-fluid">
  <div class="row content">
   	 <div class="col-sm-3 sidenav">
 		<h2>My profile</h2><br>	
    		
      <ul class="nav nav-pills nav-stacked">
      		
       			<li>
       			<form action="/main">
       			<input  class="btn btn-d btn-circle btn-block" type="submit" value="View Profile">
       			</form></li>
       			
       			
				<li><form action="/followers">
				<input type="hidden" name="pageToRedirect" value="followers" />	
				<input  class="btn btn-d btn-circle btn-block" type="submit" value="Followers">
				</form></li>
				
       			<li><form action="/following">
				<input type="hidden" name="pageToRedirect" value="following" /> 	
				<input  class="btn btn-d btn-circle btn-block" type="submit" value="Following">
				</form></li>
	
        		<li><form action="/albums">
				<input class="btn btn-d btn-circle btn-block" type="submit" value="Albums">
				</form></li>
     			  
      </ul><br>
     </div>
    
   <div class="col-sm-9">
   
     <section class="mainContent"> 
	  <section class="section1">
	    <h2 class="sectionTitle">View profile</h2>
	    <hr class="sectionTitleRule">
	    <hr class="sectionTitleRule2">
	    <div class="section1Content">
          <img src="/avatar"  style="width: 140px; height: 140px; border-radius: 50%;" >
	      <p><span>Username :</span><c:out value="${user.userName}"/></p>		
	      <p><span>Email :</span> <c:out value="${user.email}"/></p>
	      <p><span>First Name :</span><c:out value="${user.firstName}"/></p>
	      <p><span>Last Name :</span><c:out value="${user.lastName}"/></p>
	      <p><span>Description :</span><c:out value="${user.description}"/></p>
	   </div>
	  </section>        
    </section>
     </div>
	 </div>
  </div>
  <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>