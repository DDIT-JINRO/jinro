/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	//자동완성 기능 추가
	const autoCompleteBtn = document.getElementById('autoCompleteBtn');
	if (autoCompleteBtn) {
		autoCompleteBtn.addEventListener('click', autoCompleteHandler);
	}
})


// 자동완성 핸들러
function autoCompleteHandler(){
	
	const descriptionTextarea = document.querySelector('textarea[name="counselDescription"]');
	
	if(descriptionTextarea){
		const sampleText =`안녕하세요, 진로 고민 때문에 상담을 신청합니다. \n앞으로 어떤 직업을 선택해야 할지 막막한 상태입니다. 제 적성에 맞는 직업을 찾고, 구체적인 진학 계획을 세우는 데 도움을 받고 싶습니다.`;
		
		descriptionTextarea.value = sampleText;
	}
}