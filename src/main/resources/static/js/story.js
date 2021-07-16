/**
	2. 스토리 페이지
 	(0) 현재 로그인한 사용자 아이디
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */
//(0) 현재 로그인한 사용자 아이디
let principalId = $("#principalId").val();

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
	$.ajax({
		url:`/api/image?page=${page}`,
		dataType:"json"
	}).done(res=>{
		console.log(res.data);

		res.data.content.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});

	}).fail(error=>{
		console.log("오류", error);
	});
}

storyLoad();

// 이미지 템플릿 리턴
function getStoryItem(image) {
	let item = `
		<div class="story-list__item">
			<div class="sl__item__header">
				<div>
					<img class="/upload/${image.user.profileImageUrl}" src="#" onerror="this.src='/images/person.jpeg'" />
				</div>
				<div>${image.user.username}</div>
			</div>
				
			<div class="sl__item__img">
				<img src="/upload/${image.postImageUrl}" />
			</div>
			
			<div class="sl__item__contents">
				<div class="sl__item__contents__icon">
				
					<button>`;

	if(image.likeState) {
		item += `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	} else {
		item += `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}

	item += `
					</button>
				</div>
			
				<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>
			
				<div class="sl__item__contents__content">
					<p>${image.caption}</p>
				</div>
			
				<div id="storyCommentList-${image.id}">`;

	image.comments.forEach((comment) => {
		item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
						<p>
							<b>${comment.user.username} :</b> ${comment.content}
						</p>`;

		// 로그인한 본인의 댓글이면 삭제 버튼 표시
		if(principalId == comment.user.id) {
			item += `
						<button onclick="deleteComment(${comment.id})">
							<i class="fas fa-times"></i>
						</button>`;
		}

		item +=	`</div>`;
	});


				
	item += `</div>
			
				<div class="sl__item__input">
					<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
					<button type="button" onClick="addComment(${image.id})">게시</button>
				</div>
				
			</div>
		</div>`;

	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	// console.log("윈도우 스크롤 탑", $(window).scrollTop());
	// console.log("문서 높이", $(document).height());
	// console.log("윈도우 높이", $(window).height());

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
	// console.log(checkNum);

	if(checkNum < 1 && checkNum > -1){
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 좋아요 취소
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) {

		$.ajax({
			type: "post",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res=>{

			// 좋아요 개수 1 증가
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCountNum = Number(likeCountStr) + 1;
			$(`#storyLikeCount-${imageId}`).text(likeCountNum);

			// 좋아요 빨간 하트로 변경
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("좋아요 실패", error);
		});
	} else {
		$.ajax({
			type: "delete",
			url: `/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res=>{

			// 좋아요 개수 1 감소
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCountNum = Number(likeCountStr) - 1;
			$(`#storyLikeCount-${imageId}`).text(likeCountNum);

			// 좋아요 검은 테두리 하트로 변경
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("좋아요 취소 실패", error);
		});

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	} // 자바스크립트 데이터

	// 공백 유효성 검사
	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}

	// 댓글 추가 요청
	$.ajax({
		type: "post",
		url: "/api/comment",
		data: JSON.stringify(data), // javascript data -> json data
		contentType: "application/json; charset=utf-8",
		dataType: "json"
	}).done(res => {
		let comment = res.data;

		// 댓글 추가
		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}""> 
			    <p>
			      <b>${comment.user.username} :</b>
			      ${comment.content}
			    </p>
			    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
			  </div>`;

		commentList.prepend(content); // prepend : 앞에 넣는 것, append : 뒤에 넣는 것

	}).fail(error => {
		console.log("댓글 작성 실패", error);
		alert(error.responseJSON.data.content);
	});

	commentInput.val(""); // 인풋 필드 지워줌
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		dataType: "json"
	}).done(res => {
		console.log("댓글 삭제 성공", res)
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error => {
		console.log("댓글 삭제 실패", error);
	});
}