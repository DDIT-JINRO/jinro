/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	const cardList = document.querySelectorAll('.group-card');
	cardList.forEach(card => {
		card.addEventListener('click', function() {
			
			location.href = '/comm/peer/youth/youthDetail.do?boardId=' + this.dataset.tbdId;
		})
	})

	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/comm/peer/youth/youthInsert.do";
		}
	})
	
});




