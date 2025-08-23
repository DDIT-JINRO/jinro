/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	// 디테일 페이지로 이동 - userCommon.css 클래스명으로 변경
	document.querySelectorAll('.content-list__item').forEach(item => {
		item.addEventListener('click', function() {
			if (!memId || memId == 'anonymousUser') {
				showConfirm("로그인 후 이용 가능합니다.", "로그인하시겠습니까?",
					() => {
						sessionStorage.setItem("redirectUrl", location.href);
					},
					() => {

					}
				);
			} else {
				location.href = '/comm/peer/teen/teenDetail.do?boardId=' + this.dataset.tbdId;
			}
		});
	});

	// 글작성 버튼 이벤트
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			showConfirm("로그인 후 이용 가능합니다.", "로그인하시겠습니까?",
				() => {
					sessionStorage.setItem("redirectUrl", location.href);
					location.href = "/login";
				},
				() => {

				}
			);
		} else {
			location.href = "/comm/peer/teen/teenInsert.do";
		}
	});
});



