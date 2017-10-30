<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="/static/js/posts.js"></script>
</head>
<body>	

			<img  style = "width: 300px ; heigth : 100px;" src="<c:url value = "/postId/${sessionScope.postId}" />">
			<input type="hidden" id="post-id-container" value="${sessionScope.postId}">
			<c:if test="${sessionScope.Liked}">
				<button style="background-color: grey" id="likebutton" onclick="likePost()">Unlike</button>
			</c:if>
			<c:if test="${!sessionScope.Liked}">
				<button style="background-color: blue" id="likebutton" onclick="likePost()">Like</button>
			</c:if>
			<c:if test="${sessionScope.Disliked}">
				<button style="background-color: grey" id="dislikebutton" onclick="disLikePost()">Undislike</button>
			</c:if>
			<c:if test="${!sessionScope.Disliked}">
				<button style="background-color: blue" id="dislikebutton" onclick="disLikePost()">Dislike</button>
			</c:if>
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
			<h5>Who like post : </h5>  <br>
			<c:forEach items="${sessionScope.post.usersWhoLike}" var="user">
					<h6>${user.userName}</h6> 
			</c:forEach>
			<br>
			<h5>Who dislike post  : </h5> <br>
			<c:forEach items="${sessionScope.post.usersWhoDislike}" var="user">
					<h6> ${user.userName} </h6> 
			</c:forEach>
			<br>
			
			<h5> Comments : </h5> <br>
			<p>Add comment</p><br>
			<textarea id="commentdesc" rows="2"></textarea><br>
			<button onclick="postComment()">Submit comment</button><br>
			<table border="1" id="commentstable"> 
				<c:forEach items="${sessionScope.post.commentsOfPost}" var="comment">
				<tr>
					<td>Username : ${comment.userId} </td> 
					<td>Description : ${comment.description} </td> 
					<td>Date : ${comment.dateAndTimeOfUpload} </td>
					<td>Likes : ${comment.numberOfLikes} </td> 
					<td>Dislikes : ${comment. numberOfDislikes} </td>
				</tr>
			   </c:forEach> 
		   </table>
		   
		   <!--
		   <form action="/deletePost/postId" method="GET">
		   <input type="submit" value="Delete Post">
		   </form>
		   -->
</body>
</html>