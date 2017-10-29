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
				row.insertCell(0).innerHTML = 'Username : '+newComment.userId;
				row.insertCell(1).innerHTML = 'Description : '+newComment.description;
				row.insertCell(2).innerHTML = 'Date : '+fomatDate(newComment.dateAndTimeOfUpload);
				row.insertCell(3).innerHTML = 'Likes : '+newComment.numberOfLikes;
				row.insertCell(4).innerHTML = 'Deslikes : '+newComment.numberOfDislikes;
				//append first child to table of comments with the value
				
				document.getElementById("commentdesc").value='';
			}
			else
			if (this.readyState == 4 && this.status == 401) {
				alert("Sorry, you must log in to post a comment");
			}		
		}
		request.open("POST", "/comment/addComment?postId="+postId+"&commenttxt="+commentText);
		request.send(); 
}

function fomatDate(date) {
	var dateAsString = '';
	dateAsString+=date.year+'-'+date.monthValue+'-'+ date.dayOfMonth;
	dateAsString+='T'+date.hour+':'+date.minute+':'+date.second;
	return dateAsString;
}