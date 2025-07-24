/**
 *
 */

document.addEventListener('DOMContentLoaded', function(){
	document.getElementById('enterChatBtn').addEventListener('click', function(){
		if(!memId || memId =='anonymousUser'){
			console.log("로그인필요");
			sessionStorage.setItem('redirectUrl', location.href);
			location.href="/login";
		}
		const data = {crId, memId};
		fetch('/prg/std/enterStdGroup',{
			method : "POST",
			headers : {"Content-Type" : "application/json;charset=utf-8"},
			body : JSON.stringify(data),
		})
		.then(async resp=> {
			if(resp.ok){
				// 입장버튼 UI 변경
				// 모달 열기
				await openChatModal();
				// 해당 채팅방 찾아서 클릭해주기
				const enteredChatRoom = document.querySelector(`.chat-room-entry[data-cr-id="${crId}`);
				enteredChatRoom.click();
			}
		})
	})
})