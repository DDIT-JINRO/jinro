document.addEventListener('DOMContentLoaded', function() {

	// 디테일 페이지로 이동
	document.querySelectorAll('.content-list__item').forEach(exam => {
		exam.addEventListener('click', () => {
			location.href = '/ertds/qlfexm/selectQlfexmDetail.do?examId=' + exam.dataset.examId;
		});
	});
});
