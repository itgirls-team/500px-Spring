function likeComment(commentId) {
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
		button.style.background='blue';
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
		dislikeBtn.style.background='blue';
	}
	document.getElementById("number-of-commentslikes-container-"+commentId).innerHTML=likers.length;
	document.getElementById("number-of-commentsdislikes-container-"+commentId).innerHTML=dislikers.length;
}