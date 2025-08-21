/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const cardList = document.querySelectorAll('.content-list__item');
	cardList.forEach(card => {
		card.addEventListener('click', function() {
			
			location.href = '/comm/path/pathDetail.do?boardId=' + this.dataset.tbdId;
		})
	})

	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/comm/path/pathInsert.do";
		}
	})
	
});




