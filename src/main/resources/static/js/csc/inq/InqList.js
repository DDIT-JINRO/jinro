
document.addEventListener('DOMContentLoaded', function () {

	// inq 모달창 (질문/답변 열고 닫기)
	document.querySelectorAll(".inq-question").forEach(button => {
		
			button.addEventListener("click", () => {
				const parentItem = button.closest(".inq-item");
					// 열람이 허용되면, 기존 열기/닫기 로직 실행
					const content = parentItem.querySelector(".inq-content");
					const answer = parentItem.querySelector(".inq-answer");
					const arrow = button.querySelector(".arrow");
					const isOpen = content && content.classList.contains("active");

					// 다른 문의 모두 닫기
					document.querySelectorAll(".inq-content").forEach(c => c.classList.remove("active"));
					document.querySelectorAll(".inq-answer").forEach(a => a.classList.remove("active"));
					document.querySelectorAll(".arrow").forEach(a => a.classList.remove("rotate"));

					// 모든 화살표를 '▼'로 초기화합니다.
					document.querySelectorAll(".arrow").forEach(a => a.textContent = '▼');

					// 현재 문의 열기 (이미 열려있지 않다면)
					if (!isOpen) {
						if (content) content.classList.add("active");
						if (answer) answer.classList.add("active");
						if (arrow) arrow.classList.add("rotate");
						// 현재 클릭한 화살표만 '▲'로 변경합니다.
						if (arrow) arrow.textContent = '▲';
					}
			});
		});

	// 🔽 글 작성 버튼
	const writeBtn = document.getElementById('btnWrite');
	if (writeBtn) {
		writeBtn.addEventListener('click', function () {
			if (!memId || memId === 'anonymousUser') {
				sessionStorage.setItem("redirectUrl", location.href);
				location.href = "/login";
			} else {
				location.href = "/csc/inq/insertInq.do";
			}
		});
	}
});
