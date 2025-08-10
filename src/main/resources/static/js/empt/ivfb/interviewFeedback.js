/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/empt/ivfb/insertInterViewFeedbackView.do";
		}
	})
	
});

function toggleCard(header) {
    const card = header.parentElement;
    const content = card.querySelector('.card-content');
    const toggle = header.querySelector('.card-toggle');
    
    // 현재 카드의 활성 상태 토글
    const isActive = content.classList.contains('active');
    
    if (isActive) {
        content.classList.remove('active');
        header.classList.remove('active');
        toggle.classList.remove('active');
    } else {
        content.classList.add('active');
        header.classList.add('active');
        toggle.classList.add('active');
    }
}

// 페이지 로드 시 모든 카드 닫힌 상태로 초기화
document.addEventListener('DOMContentLoaded', function() {
    const allContents = document.querySelectorAll('.card-content');
    const allHeaders = document.querySelectorAll('.card-header');
    const allToggles = document.querySelectorAll('.card-toggle');
    
    allContents.forEach(content => content.classList.remove('active'));
    allHeaders.forEach(header => header.classList.remove('active'));
    allToggles.forEach(toggle => toggle.classList.remove('active'));
});