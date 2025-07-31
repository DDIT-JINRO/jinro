/**
 *
 */
document.addEventListener('DOMContentLoaded', function(){
	const enterChatBtn = document.getElementById('enterChatBtn');
	if(enterChatBtn){
		enterChatBtn.addEventListener('click', function(){
			if(this.classList.contains('disabled')) return;
			if(!confirm("입장하시겠습니까?")) return;

			if(!memId || memId =='anonymousUser'){
				console.log("로그인필요");
				sessionStorage.setItem('redirectUrl', location.href);
				location.href="/login";
			}
			const data = {crId, memId};
			fetch('/prg/std/api/enterStdGroup',{
				method : "POST",
				headers : {"Content-Type" : "application/json;charset=utf-8"},
				body : JSON.stringify(data),
			})
			.then(async resp=> {
				if(resp.ok){
					location.reload();
				}
			})
		})
	}
	const exitChatBtn = document.getElementById('exitChatBtn');
	if(exitChatBtn){
		exitChatBtn.addEventListener('click',function(){
			if(!confirm("퇴장하시겠습니까?")) return;

			const data = {crId, memId};
			fetch('/api/chat/exit', {
				method : "POST",
				headers : {"Content-Type":"application/json"},
				body:JSON.stringify(data)
			})
			.then(resp =>{
				if(!resp.ok) throw new Error('에러');
				return resp.json();
			})
			.then(result =>{
				if(result){
					location.reload();
				}
			})
		})
	}


	// 동적요소 바인딩
	const commentSection = document.querySelector('.comment-section');

	document.addEventListener('submit', submitCreateReply);
	document.addEventListener('input', eventReplyInput);
	document.addEventListener('click', closeEtcBtn);
	commentSection.addEventListener('click', closeReplyBtn);
	commentSection.addEventListener('click', eventReplyToggle);
	commentSection.addEventListener('click', toggleEtcBtn);
	commentSection.addEventListener('click', eventEtcContainerClicked);

})

function createParentReply(replyVO, e){

	const div = document.createElement('div');
	div.classList.add('reply-box');
	const createdTime = new Date(replyVO.replyCreatedAt);
	const createdTimeFormat = `${createdTime.getFullYear()}. ${("0"+(createdTime.getMonth()+1)).slice(-2)}. ${createdTime.getDate()}. ${createdTime.getHours()}:${createdTime.getMinutes()}`;
	div.id = `reply-${replyVO.boardId}-${replyVO.replyId }`;
	div.innerHTML = `
	<span class="etcBtn">…</span>
	<div class="etc-container">삭제</div>
	<div class="reply-profile">
	  <div class="user-profile">
	    <img class="badge-frame" src="${replyVO.fileBadge ? replyVO.fileBadge : '/images/defaultBorderImg.png'}" alt="badge"/>

	    <img class="profile-image" src="${replyVO.fileProfile ? replyVO.fileProfile : '/images/defaultProfileImg.png' }" alt="profile"/>
	  </div>
	  <div class="writer-info">
	    <div class="reply-nickname">${replyVO.memNickname}</div>
	    <div class="reply-date">${createdTimeFormat}</div>
	  </div>
	</div>
	  <div class="reply-content">${replyVO.replyContent }</div>
	  <div>
	  	<button class="reply-child-btn" id="reply-${replyVO.replyId }">답글</button>
	  	<span class="child-count"></span>
	  </div>
	`;

	const childReplyContainer = document.createElement('div');
	childReplyContainer.classList.add('reply-child-container');
	childReplyContainer.dataset.parentId = replyVO.replyId;
	childReplyContainer.innerHTML = `
		<form action="/prg/std/createStdReply.do" method="post" class="comment-form child-form">
		  <input type="hidden" name="boardId" value="${replyVO.boardId}" />
		  <input type="hidden" name="replyParentId" value="${replyVO.replyId }" />
		  <textarea name="replyContent" maxlength="300" placeholder="댓글을 입력하세요."></textarea>
		  <div class="comment-footer">
		    <span class="char-count">0 / 300</span>
		    <button type="submit" class="btn-submit">등록</button>
		  </div>
		  <br/>
		<div class="closeReplyBtn"><span>답글접기 ▲</span></div>
		</form>
	`;

	document.querySelector('.comment-section').prepend(childReplyContainer);
	document.querySelector('.comment-section').prepend(div);
	e.target.querySelector('textarea').value='';
}
/* Frame 278 */

function createChildReply(replyVO, e){
	const childReply = document.createElement('div');
	childReply.classList.add('reply-box');
	childReply.classList.add('reply-child');
	childReply.dataset.replyMem=replyVO.memId;
	childReply.id=`reply-${replyVO.boardId}-${replyVO.replyId}`;

	const createdTime = new Date(replyVO.replyCreatedAt);
	const createdTimeFormat = `${createdTime.getFullYear()}. ${("0"+(createdTime.getMonth()+1)).slice(-2)}. ${createdTime.getDate()}. ${createdTime.getHours()}:${createdTime.getMinutes()}`;
	childReply.innerHTML = `
		<span class="etcBtn">…</span>
		<div class="etc-container">삭제</div>
		<div class="reply-profile">
		  <div class="user-profile">
		    <img class="badge-frame" src="${replyVO.fileBadge ? replyVO.fileBadge : '/images/defaultBorderImg.png' }" />
		    <img class="profile-image" src="${replyVO.fileProfile ? replyVO.fileProfile : '/images/defaultProfileImg.png' }"/>" />
		  </div>
		  <div class="writer-info">
		    <div class="reply-nickname">${replyVO.memNickname}</div>
		    <div class="reply-date">${createdTimeFormat}</div>
		  </div>
		</div>
		<div class="reply-content">${replyVO.replyContent}</div>
	`;

	e.target.before(childReply);

	const containerEl = e.target.closest('.reply-child-container');
	const childContainer = document.querySelector(`.reply-child-container[data-parent-id="${replyVO.replyParentId}"]`);
	containerEl.style.maxHeight = childContainer.scrollHeight + 'px';
	e.target.querySelector('textarea').value='';
}

// 이벤트 함수 1. 답글버튼 토글 ; click
function eventReplyToggle(e){
	if(!e.target.classList.contains('reply-child-btn')) return;

	const replyId = e.target.id.substring("reply-".length);
	const childContainer = document.querySelector(`.reply-child-container[data-parent-id="${replyId}"]`);
	if (childContainer.classList.toggle('open')) {
	  childContainer.style.maxHeight = childContainer.scrollHeight + 'px';
	} else {
	  childContainer.style.maxHeight = '0';
	}
}
// 이벤트 함수 2 댓글입력 글자수체크 ; input
function eventReplyInput(e){
	if(e.target.nodeName!='TEXTAREA') return;

	const curLength = e.target.value.length;
	const commentFooter = e.target.nextElementSibling;
	const charCountEl = commentFooter.querySelector('.char-count');

	const charCountArr = charCountEl.textContent.split(' / ');
	charCountArr[0] = curLength;

	charCountEl.textContent = charCountArr.join(' / ');
}

// 이벤트 함수 3 답글닫기 버튼 이벤트 ; click
function closeReplyBtn(e){
	if(!e.target.closest('.closeReplyBtn')) return;

	const containerEl = e.target.closest('.reply-child-container');
	if(containerEl.classList.contains("open")){
		if(containerEl.classList.toggle("open")){
			containerEl.style.maxHeight = childContainer.scrollHeight + 'px';
		}else{
			containerEl.style.maxHeight = '0';
		}
	}
}

// 이벤트 함수 4 댓글,답글 작성 비동기 호출 이벤트 ; submit
function submitCreateReply(e){
	e.preventDefault();
	console.log(e.target);
	if(!e.target.classList.contains('comment-form')) return false;
	const formData = new FormData(e.target);
	fetch(e.target.action,{
		method : "POST",
		headers : {},
		body : formData,
	})
	.then(resp =>{
		if(!resp.ok) throw new Error();
		return resp.json();
	})
	.then(data =>{
		console.log(data);
		if(data.replyParentId>0){
			createChildReply(data, e);
		}else{
			createParentReply(data, e);
		}
	})
	.catch(err =>{
		console.log(err);
	})
}

// 이벤트 함수 5 ...버튼 클릭시 박스 표시
function toggleEtcBtn(e){
	if(!e.target.classList.contains('etcBtn')) return;

	const etcContainer = e.target.nextElementSibling;
	if(etcContainer) etcContainer.classList.toggle('etc-open');
}

// 이벤트 함수 6 ...버튼 바깥 클릭시 박스 제거
function closeEtcBtn(e){
	if(e.target.classList.contains('etc-container')) return;
	if(!e.target.classList.contains('etcBtn')){
		const etcContainerList = document.querySelectorAll('.etc-container');
		etcContainerList.forEach(ec =>{
			if(ec.classList.contains('etc-open')) ec.classList.remove('etc-open');
		})
	}
}

// 이벤트 함수 7 container 버튼 클릭시
function eventEtcContainerClicked(e){
	if(!e.target.classList.contains('etc-container')) return;
	const el = e.target;
	const action = el.textContent.trim();
	if(!confirm(`이 댓글을 정말로 ${action} 하시겠습니까?`)) return;
	if(!memId || memId == 'anonymousUser'){
		alert('로그인이 필요합니다');
		return;
	}
	const targetReply = el.closest('.reply-box');
	const targetReplyChildBox = targetReply.nextElementSibling;
	console.log(targetReplyChildBox);
	const targetReplyId = targetReply.id.split('-')[2];
	const data = {"replyId":targetReplyId};
	console.log(data);
	if(action == '삭제'){
		console.log("삭제 fetch")
	fetch('/prg/std/deleteStdReply.do',{
			method:"POST",
			headers:{
				"Content-Type":"application/json"
			},
			body:JSON.stringify(data)
		})
		.then(resp =>{
			if(!resp.ok) throw new Error('에라');
			return resp.json();
		})
		.then(result =>{
			if(result){
				targetReply.remove();
				targetReplyChildBox.remove();
				setTimeout(()=>{alert('삭제되었습니다')})
			}
		})
		.catch(err =>{
			console.log(err);
		})
	}
	if(action == '신고'){
		console.log("신고 fetch")
	}


}

