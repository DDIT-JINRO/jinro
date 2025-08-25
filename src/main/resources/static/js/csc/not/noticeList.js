document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.content-list__item').forEach(notice => {
		notice.addEventListener('click', () => {
			location.href = '/csc/not/noticeDetail.do?noticeId=' + notice.dataset.noticeId;
		});
	});
});

document.addEventListener("DOMContentLoaded", () => {
    const goToInq = document.getElementById("goToInq");

    if (goToInq) {
        goToInq.addEventListener("click", (e) => {
            if (!memId || memId === 'anonymousUser') {
                e.preventDefault(); // 링크 기본 이동 막기
                showConfirm(
                    "로그인 후 이용 가능합니다.",
                    "로그인하시겠습니까?",
                    () => {
                        sessionStorage.setItem("redirectUrl", location.href);
                        location.href = "/login"; // 확인 시 로그인 페이지로 이동
                    },
                    () => {
                        // 취소 시 아무 동작 안 함
                    }
                );
            }
            // 로그인 상태면 기본 링크 (/csc/inq/inqryList.do)로 이동
        });
    }
});