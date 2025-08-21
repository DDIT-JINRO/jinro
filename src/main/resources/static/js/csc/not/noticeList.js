document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.content-list__item').forEach(notice => {
		notice.addEventListener('click', () => {
			location.href = '/csc/not/noticeDetail.do?noticeId=' + notice.dataset.noticeId;
		});
	});
});