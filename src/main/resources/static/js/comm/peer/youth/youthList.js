/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const cardList = document.querySelectorAll('.content-list__item');
	cardList.forEach(card => {
		card.addEventListener('click', function() {
			
			location.href = '/comm/peer/youth/youthDetail.do?boardId=' + this.dataset.tbdId;
		})
	})

	
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
			location.href = "/comm/peer/youth/youthInsert.do";
		}
	})
	
});




