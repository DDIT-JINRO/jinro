document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.content-list__item').forEach(notice => {
		notice.addEventListener('click', () => {
			location.href = '/csc/not/noticeDetail.do?noticeId=' + notice.dataset.noticeId;
		});
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