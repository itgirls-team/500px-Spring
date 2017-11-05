function postComment() {
	var postId=document.getElementById("post-id-container").value;
	var commentText= document.getElementById("commentdesc").value;
	
	 var request = new XMLHttpRequest();
	 if (commentText==null || commentText == '') {
			alert('Please enter text for your comment!');
			return;
		}
	 
		request.onload = function() {
			//when response is received
			if (this.readyState == 4 && this.status == 200) {
				var newComment = JSON.parse(this.response);
				var table = document.getElementById("commentstable");
				// Create an empty <tr> element and add it to the 1st position of the table:
				var row = table.insertRow(0);

				// Insert new cells (<td> elements) at the 1st and 2nd position of the "new" <tr> element:
				row.insertCell(0).innerHTML = 'Username : '+newComment.userName;
				row.insertCell(1).innerHTML = 'Description : '+newComment.description;
				row.insertCell(2).innerHTML = 'Date : '+fomatDate(newComment.dateAndTimeOfUpload);
				row.insertCell(3).innerHTML = '<h5>Likes : </h5>' + '<h5 id="number-of-commentslikes-container-'+newComment.id+'">0</h5>';
				row.insertCell(4).innerHTML = '<h5>Dislikes : </h5>' + '<h5 id="number-of-commentsdislikes-container-'+newComment.id+'">0</h5>';
				row.insertCell(5).innerHTML = '<button style="background-color: #6CD7FE" id="commentlikebutton-'+newComment.id+'" onclick="likeComment('+newComment.id+')">Like</button>';
				row.insertCell(6).innerHTML = '<button style="background-color: #6CD7FE" id="commentdislikebutton-'+newComment.id+'" onclick="disLikeComment('+newComment.id+')">Dislike</button>';
				
				document.getElementById("commentdesc").value='';
			}
			else {
				var mesage = this.response;
				alert(mesage);
			}		
		}
		request.open("POST", "/comment/addComment?postId="+postId+"&commenttxt="+commentText);
		request.send(); 
}

function likeComment(commentId) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		//when response is received
		if (this.readyState == 4 && this.status == 200) {
			renderNewCommentInTable(this, commentId);
		}
		else if (this.readyState == 4 && this.status == 401) {
			alert("Sorry, you must log in to like this comment!");
		}	
	}
	request.open("POST", "/comment/likeComment?commentId="+commentId, true);
	request.send();
}
function disLikeComment(commentId) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function() {
		//when response is received
		if (this.readyState == 4 && this.status == 200) {
			renderNewCommentInTable(this, commentId);
		}
		else
		if (this.readyState == 4 && this.status == 401) {
			alert("Sorry, you must log in to like this comment!");
		}	
	}
	request.open("POST", "/comment/dislikeComment?commentId="+commentId, true);
	request.send();
}

function renderNewCommentInTable(result, commentId){
	var newComment = JSON.parse(result.response);
	var button = document.getElementById("commentlikebutton-"+commentId);
	var dislikeBtn=document.getElementById("commentdislikebutton-"+commentId);
	var likers = newComment.usersWhoLike;
	var dislikers=newComment.usersWhoDislike;
	var userId=newComment.userDto.userId;
	var userIsLiker=false;
	for(i in likers){
		var liker=likers[i];
		if(liker.userId==userId){
			//like btn active cuknato e
			button.setAttribute("class", "liked");
			button.innerHTML = "Unlike";
			button.style.background='grey';
			userIsLiker=true;
			break;
		}
	}
	
	if(!userIsLiker){
		//like btn non active ne go e like-nal unlike
		button.setAttribute("class", "not-liked");
		button.innerHTML = "Like";
		button.style.background='#6CD7FE';
	}
	var userIsDisliker=false;
	for(i in dislikers){
		var disliker=dislikers[i];
		if(disliker.userId==userId){
			//like btn active
			dislikeBtn.setAttribute("class", "disliked");
			dislikeBtn.innerHTML = "Undislike";
			dislikeBtn.style.background='grey';
			userIsDisliker=true;
			break;
		}
	}
	if(!userIsDisliker){
		dislikeBtn.setAttribute("class", "not-disliked");
		dislikeBtn.innerHTML = "Dislike";
		dislikeBtn.style.background='#6CD7FE';
	}
	document.getElementById("number-of-commentslikes-container-"+commentId).innerHTML=likers.length;
	document.getElementById("number-of-commentsdislikes-container-"+commentId).innerHTML=dislikers.length;
}