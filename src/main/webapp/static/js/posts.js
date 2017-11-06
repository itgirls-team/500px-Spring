function fomatDate(date) {
	var dateAsString = '';
	dateAsString+=date.year+'-'+date.monthValue+'-'+ date.dayOfMonth;
	dateAsString+='T'+date.hour+':'+date.minute+':'+date.second;
	return dateAsString;
}

function likePost() {
	var postId=document.getElementById("post-id-container").value;
	var request = new XMLHttpRequest();
	request.onload = function() {
		//when response is received
		if (this.readyState == 4 && this.status == 200) {
			var newPost = JSON.parse(this.response);
			var button = document.getElementById("likebutton");
			var dislikeBtn=document.getElementById("dislikebutton");
			var likers = newPost.usersWhoLike;
			var dislikers=newPost.usersWhoDislike;
			var userId=newPost.userDto.userId;
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
			document.getElementById("number-of-likes-container").innerHTML=likers.length;
			document.getElementById("number-of-dislikes-container").innerHTML=dislikers.length;
		}
		else {
			var mesage = this.response;
			alert(mesage);
		}	
	}
	request.open("POST", "likePost?postId="+postId, true);
	request.send();
}

function disLikePost() {
	var postId=document.getElementById("post-id-container").value;
	var request = new XMLHttpRequest();
	request.onload = function() {
		//when response is received
		if (this.readyState == 4 && this.status == 200) {
			var newPost = JSON.parse(this.response);
			var button = document.getElementById("likebutton");
			var dislikeBtn=document.getElementById("dislikebutton");
			var likers = newPost.usersWhoLike;
			var dislikers=newPost.usersWhoDislike;
			var userId=newPost.userDto.userId;
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
			document.getElementById("number-of-likes-container").innerHTML=likers.length;
			document.getElementById("number-of-dislikes-container").innerHTML=dislikers.length;
			
		}
		else {
			var mesage = this.response;
			alert(mesage);
		}	
	}
	request.open("POST", "disLikePost?postId="+postId, true);
	request.send();
}