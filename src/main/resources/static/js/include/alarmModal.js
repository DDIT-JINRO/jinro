/**
 * 헤더의 알림 모달을 컨트롤 하기 위한 js
 */

document.addEventListener('DOMContentLoaded', function(){
	const alarmBtn    = document.getElementById('alarmBtn');
	const alarmModal  = document.getElementById('alarm-modal');
	const alarmClose	= document.getElementById('alarm-close');

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


