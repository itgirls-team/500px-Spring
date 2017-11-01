<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="/static/js/posts.js"></script>
<script src="/static/js/likeComment.js"></script>
</head>
<body>	

			<img src="<c:url value = "/postId/${sessionScope.postId}" />">
			<input type="hidden" id="post-id-container" value="${sessionScope.postId}">
			
			<c:set var="isLiked" value="false"/>
			<c:forEach items="${sessionScope.post.usersWhoLike}"  var="liker">
				<c:if test="${liker.id==sessionScope.user.id}">
					<c:set var="isLiked" value="true"/>
				</c:if>
			</c:forEach>
			<c:if test="${isLiked==true}">
				<button style="background-color: grey" id="likebutton" onclick="likePost()">Unlike</button></c:if>
			<c:if test="${isLiked==false}">
				<button style="background-color: blue" id="likebutton" onclick="likePost()">Like</button>
			</c:if>
			
			<c:set var="isDisliked" value="false"/>
			<c:forEach items="${sessionScope.post.usersWhoDislike}"  var="disliker">
				<c:if test="${disliker.id==sessionScope.user.id}">
					<c:set var="isDisliked" value="true"/>
				</c:if>
			</c:forEach>
			<c:if test="${isDisliked==true}">
				<button style="background-color: grey" id="dislikebutton" onclick="disLikePost()">Undislike</button>
			</c:if>
			<c:if test="${isDisliked==false}">
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
			
			<%-- <h5> Comments : </h5> <br>
			<p>Add comment</p><br>
			<textarea id="commentdesc" rows="2"></textarea><br>
			<button onclick="postComment()">Submit comment</button><br>
			<table border="1" id="commentstable"> 
				<c:forEach items="${sessionScope.post.commentsOfPost}" var="comment">
				<tr>
					<td>Username : ${comment.userName} </td> 
					<td>Description : ${comment.description} </td> 
					<td>Date : ${comment.dateAndTimeOfUpload} </td>
					<td>Likes : ${comment.numberOfLikes} </td> 
					<td>Dislikes : ${comment. numberOfDislikes} </td>
				</tr>
			   </c:forEach> 
		   </table>
			 --%>
			 <h5> Comments : </h5> <br>
			<p>Add comment</p><br>
			<textarea id="commentdesc" rows="2"></textarea><br>
			<button onclick="postComment()">Submit comment</button><br>
			<table border="1" id="commentstable"> 
				<c:forEach items="${sessionScope.post.commentsOfPost}" var="comment">
				<input type="hidden" id="comment-id-container" value="${comment.id}"> 
				<tr>
					<td>Username : ${comment.userName} </td> 
					<td>Description : ${comment.description} </td> 
					<td>Date : ${comment.dateAndTimeOfUpload} </td>
					<td><h5>Likes: </h5><h5 id="number-of-commentslikes-container"> ${comment.usersWhoLikeComment.size()} </h5></td>
					<td><h5>Dislikes: </h5><h5 id="number-of-commentsdislikes-container"> ${comment.usersWhoDislikeComment.size()} </h5></td>
					
							<c:set var="isCommentLiked" value="false"/>
					<c:forEach items="${comment.usersWhoLikeComment}"  var="liker">
						<c:if test="${liker.id==sessionScope.user.id}">
							<c:set var="isCommentLiked" value="true"/>
						</c:if>
					</c:forEach>
					<c:if test="${isCommentLiked==true}">
						<td><button style="background-color: grey" id="commentlikebutton" onclick="likeComment()">Unlike</button></td>
					</c:if>
					<c:if test="${isCommentLiked==false}">
						<td><button style="background-color: blue" id="commentlikebutton" onclick="likeComment()">Like</button></td>
					</c:if>
					
					<c:set var="isCommentDisliked" value="false"/>
					<c:forEach items="${comment.usersWhoDislikeComment}"  var="disliker">
						<c:if test="${disliker.id==sessionScope.user.id}">
							<c:set var="isCommentDisliked" value="true"/>
						</c:if>
					</c:forEach>
					<c:if test="${isCommentDisliked==true}">
						<td><button style="background-color: grey" id="commentdislikebutton" onclick="disLikeComment()">Undislike</button></td>
					</c:if>
					<c:if test="${isCommentDisliked==false}">
						<td><button style="background-color: blue" id="commentdislikebutton" onclick="disLikeComment()">Dislike</button></td>
					</c:if>
					
				</tr>
			   </c:forEach>
		   </table> 
</body>
</html>