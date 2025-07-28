/**
 * 헤더의 알림 모달을 컨트롤 하기 위한 js
 */
// 비정상적인 종료 같은 상황시에 eventSource를 해제할 수 있도록 전역객체에 대입
let eventSource = null;

// 비정상적인 종료 같은 상황시에 eventSource해제
window.addEventListener('beforeunload', function(){
	if(eventSource)
	eventSource.close();
})

// 동적으로 생성되는 알림에 대해서 이벤트 위임
document.addEventListener('click', function(){

})

document.addEventListener('DOMContentLoaded', function(){
	const alarmBtn    = document.getElementById('alarmBtn');
	const alarmModal  = document.getElementById('alarm-modal');
	const alarmClose	= document.getElementById('alarm-close');
	const alarmDeleteAll = document.getElementById('alarm-delete-all');
	const alarmBody = document.getElementById('alarm-body');

	// 로그인이 되어있는 유저한테만 sse 연결 및 알림 정보 받아오기
	// 로그인이 되어있는 유저한테만 알림 전체 삭제 활성화
	// 로그인이 되어있는 유저한테 알림에 마우스 hover시 read업데이트
	if(memId && memId != 'anonymousUser'){

		// 초기에 알림 내용 세팅
		fetch(`/api/alarm/getAlarms`,{
			method : "GET",
		})
		.then(resp =>{
			if(!resp.ok) throw new Error('에러 발생');
			return resp.json();
		})
		.then(data =>{
			console.log(data);
			if(data.length >= 1){
				alarmBody.innerHTML = '';
				data.forEach(alarm =>{
					addAlarmItem(alarm);
				})
			}
		})
		.catch(err =>{
			console.log(err);
		})

		// SSE 연결
		eventSource = new EventSource('/api/alarm/sub?memId='+memId);
		eventSource.addEventListener('alarm',function(e){
			const alarmVO = JSON.parse(e.data);
			console.log(alarmVO);
		})

		eventSource.addEventListener('connected', function(e){
			console.log("connected 이벤트 확인");
			console.log(e.data);
		})

		eventSource.onopen = () =>{
			console.log("sse연결됨");
		}

		eventSource.onerror = (e) =>{
			console.log("e :", e);
		}

		// 알림 전체삭제 요청 버튼 이벤트 추가
		alarmDeleteAll.addEventListener('click', function(){
			console.log('버튼 작동 테스트');
		})

		// 모달에 띄울 알림 내용 출력하는 함수
		function addAlarmItem(alarm){
			const item = document.createElement('div');
			item.classList.add('alarm-item');
			item.classList.add(alarm.alarmIsRead == 'Y' ? 'read' : 'unread');
			item.dataset.id = alarm.alarmId;
			item.onclick = (e)=>{
				if(e.target.closest('.alarm-delete-btn')) return;
				location.href = alarm.alarmTargetUrl;
			}
			// 알림 내용, 취소버튼 추가
			item.innerHTML = `	<span class="alarm-content">${alarm.alarmContent}</span>
			      				<button class="alarm-delete-btn">&times;</button>`;
			alarmBody.prepend(item);
		}

		alarmModal.addEventListener('mouseover', updateReadAlarm);
	}

	// 모달 바깥 클릭 시 모달 닫기
	document.addEventListener('click', function(e){
		if(alarmModal.classList.contains('hidden')) return;

		if(!e.target.closest('#alarm-modal') && !e.target.closest("#alarmBtn")){
			alarmModal.classList.add('hidden');
		}
	})

	// 모달 닫기버튼 이벤트 등록
	alarmClose.addEventListener('click', (e) =>{
		e.preventDefault();
		alarmModal.classList.add('hidden');
	})

	// 모달 토글
	alarmBtn.addEventListener('click', (e) => {
		e.preventDefault();
		alarmModal.classList.toggle('hidden');
	});
})


// 모달에서 마우스를 올릴경우 읽음처리 하는 함수
function updateReadAlarm(e){
	const unreadAlarmItem = e.target.closest(".alarm-item.unread");
	if(!unreadAlarmItem) return;

	let alarmId = unreadAlarmItem.dataset.id;
	fetch(`/api/alarm/updateRead`,{
		method : "POST",
		headers : {"Content-Type":"application/json"},
		body : JSON.stringify({alarmId:alarmId})
	})
	.then(resp =>{
		if(!resp.ok) throw new Error("에러 발생");
		console.log("정상완료");
		// 뱃지제거
		unreadAlarmItem.classList.remove('unread');
		unreadAlarmItem.classList.add('read');
	})
	.catch(err =>{
		console.log(err);
	})
}

// 모달에서 삭제 버튼 누를 시 삭제처리 하는 함수


