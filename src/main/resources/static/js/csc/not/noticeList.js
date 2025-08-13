// 게시글 총 건수 정규식 표현(1000 단위 콤마)
const data = document.getElementById("getAllNotice").textContent;
const numberOnly = data.replace(/[^0-9]/g, ""); // 숫자만 추출
const formatted = parseInt(numberOnly, 10).toLocaleString();
document.getElementById("getAllNotice").textContent = `총 ${formatted}건`;

document.addEventListener('DOMContentLoaded', function() {
	var cards = document.querySelectorAll('.group-card');

	for (var i = 0; i < cards.length; i++) {
		cards[i].addEventListener('click', function() {
			var noticeId = this.getAttribute('data-tbd-id');
			window.location.href = '/csc/not/noticeDetail.do?noticeId=' + noticeId;
		});
	}
});