/**
 * 헤더의 알림 모달을 컨트롤 하기 위한 js
 */
let eventSource = null;

window.addEventListener('beforeunload', function(){
	if(eventSource)
	eventSource.close();
})

document.addEventListener('DOMContentLoaded', function(){
	const alarmBtn    = document.getElementById('alarmBtn');
	const alarmModal  = document.getElementById('alarm-modal');
	const alarmClose	= document.getElementById('alarm-close');

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



	document.addEventListener('click', function(e){
		if(alarmModal.classList.contains('hidden')) return;

		if(!e.target.closest('#alarm-modal') && !e.target.closest("#alarmBtn")){
			alarmModal.classList.add('hidden');
		}
	})

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


