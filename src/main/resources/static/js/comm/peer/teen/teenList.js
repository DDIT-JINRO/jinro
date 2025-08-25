/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	// 디테일 페이지로 이동 - userCommon.css 클래스명으로 변경
	document.querySelectorAll('.content-list__item').forEach(item => {
		item.addEventListener('click', function() {
			location.href = '/comm/peer/teen/teenDetail.do?boardId=' + this.dataset.tbdId;
		});
	});

	// 글작성 버튼 이벤트
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/comm/peer/teen/teenInsert.do";
		}
	});
	
	const toggleButton = document.querySelector('.search-filter__accordion-header');
	const panel = document.querySelector('.search-filter__accordion-panel');

	if (toggleButton) {
		toggleButton.addEventListener('click', function() {
			this.classList.toggle('is-active');
			panel.classList.toggle('is-open');
		});
	}
});



