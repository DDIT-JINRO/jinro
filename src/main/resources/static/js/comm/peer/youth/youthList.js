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
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/comm/peer/youth/youthInsert.do";
		}
	})
	
	const toggleButton = document.querySelector('.search-filter__accordion-header');
	const panel = document.querySelector('.search-filter__accordion-panel');

	if (toggleButton) {
		toggleButton.addEventListener('click', function() {
			this.classList.toggle('is-active');
			panel.classList.toggle('is-open');
		});
	}
	
});




