<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	
		function postComment() {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				//when response is received
				if (this.readyState == 4 && this.status == 200) {
					
					var commenttxt = document.getElementById("commentdesc").value;
					var table = document.getElementById("commentstable");

					// Create an empty <tr> element and add it to the 1st position of the table:
					var row = table.insertRow(0);//<tr></tr>

					// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
					var cell1 = row.insertCell(0);//<td></td>

					// Add some text to the new cells:
					cell1.innerHTML = commenttxt;
					//append first child to table of comments with the value
				}
				else
				if (this.readyState == 4 && this.status == 401) {
					alert("Sorry, you must log in to post a comment");
				}
					
			}
			request.open("put", "comment/save", true);
			request.send();
		}
	
		function handleLike(){
			var button = document.getElementById("likebutton");
			var title = button.innerHTML;
			if(title == "Like"){
				likeVideo();
			}
			else{
				unlikeVideo();
			}
		}
	</script>
</head>
<body>	

			<img src="<c:url value = "/postId/${sessionScope.postId}" />">
			<h5>Description :  ${sessionScope.post.description} </h5><br>
			<h5>Likes : ${sessionScope.post.countsOfLikes} </h5><br>
			<h5>Dislikes : ${sessionScope.post.countsOfDislikes} </h5><br>
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
			<p>Add comment</p><br>
			<textarea id="commentdesc" rows="2"></textarea><br>
			<button onclick="postComment()">Submit comment</button><br>
			<h5> Comments : </h5> <br>
			<table border="1" id="commentstable">
				<c:forEach items="${sessionScope.post.commentsOfPost}" var="comment">
					<h6>Username : ${comment.userId} </h6> <br>
					<h6>Description : ${comment.description} </h6> <br>
					<h6>Date : ${comment.dateAndTimeOfUpload} </h6> <br>
					<h6>Likes : ${comment.numberOfLikes} </h6> <br>
					<h6>Dislikes : ${comment. numberOfDislikes} </h6> <br>
			</c:forEach>
		    </table>
>>>>>>> feature/fix-controllers
</body>
</html>