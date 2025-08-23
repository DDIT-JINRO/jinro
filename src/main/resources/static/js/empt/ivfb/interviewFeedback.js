/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	const channelSection = document.querySelector(".channel");
	if (channelSection) {
	    const errorMessage = channelSection.dataset.errorMessage;
	    if (errorMessage) {
			alert(errorMessage);
			history.back();
		}
	}
	
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
			location.href = "/empt/ivfb/insertInterViewFeedbackView.do";
		}
	})
	
	const cardHeaders = document.querySelectorAll(".accordion-list__item-header");
	cardHeaders.forEach((cardHeader) => {
		cardHeader.addEventListener("click", function() {
			toggleCard(this);
		});
	});
	
	const deleteBtns = document.querySelectorAll(".card-actions__button--delete");
	deleteBtns.forEach((deleteBtn) => {
		deleteBtn.addEventListener("click", function() {
			const irId = this.dataset.irId;
			deleteInterviewFeedback(irId);
		});
	});
	
	const editBtns = document.querySelectorAll(".card-actions__button--edit");
	editBtns.forEach((editBtn) => {
		editBtn.addEventListener("click", function() {
			const dataMemId = this.dataset.memId;
			const irId = this.dataset.irId;
			if (dataMemId != memId) {
				showConfirm2("허용되지 않은 접근입니다.",
					() => {
					},
					() => {

					}
				);
				return;
			}
			location.href = `/empt/ivfb/updateInterviewFeedbackView.do?irId=${irId}`; 
		});
	});
});

function toggleCard(header) {
	const currentCard = header.closest('.accordion-list__item');
	const currentContent = currentCard.querySelector('.accordion-list__item-content');
	const currentToggle = header.querySelector('.accordion-list__toggle-icon');
	const isOpening = !currentContent.classList.contains('is-active');

	document.querySelectorAll('.accordion-list__item').forEach(item => {
	    if (item !== currentCard) {
	        item.querySelector('.accordion-list__item-content').classList.remove('is-active');
	        item.querySelector('.accordion-list__item-header').classList.remove('is-active');
	        item.querySelector('.accordion-list__toggle-icon').classList.remove('is-active');
	    }
	});

	if (isOpening) {
	    currentContent.classList.add('is-active');
	    header.classList.add('is-active');
	    currentToggle.classList.add('is-active');
	} else {
	    currentContent.classList.remove('is-active');
	    header.classList.remove('is-active');
	    currentToggle.classList.remove('is-active');
	}
}

// 면접 후기 삭제 기능
function deleteInterviewFeedback(irId) {
	

	showConfirm("정말로 이 면접 후기를 삭제하시겠습니까?","삭제된 후기는 복구할 수 없습니다.", 
	    () => {
			fetch('/empt/ivfb/deleteInterviewFeedback.do', {
			    method: 'POST',
			    headers: {
			        'Content-Type': 'application/x-www-form-urlencoded',
			    },
			    body: 'irId=' + encodeURIComponent(irId)
			})
			.then(response => {
			    if (response.ok) {
			        return response.json();
			    }
			    throw new Error('삭제 요청이 실패했습니다.');
			})
			.then(data => {
			    if (data.success) {
					showConfirm2('면접 후기가 성공적으로 삭제되었습니다.',
						() => {
						},
						() => {

						}
					);
			        location.reload(); // 페이지 새로고침
			    } else {
			        alert('삭제 중 오류가 발생했습니다: ' + (data.message || '알 수 없는 오류'));
			    }
			})
			.catch(error => {
			    console.error('Error:', error);
			    alert('삭제 중 오류가 발생했습니다. 다시 시도해주세요.');
			});
	    },
	    () => {
	        
	    }
	);

}

// 페이지 로드 시 모든 카드 닫힌 상태로 초기화
document.addEventListener('DOMContentLoaded', function() {
	const allContents = document.querySelectorAll('.accordion-list__item-content');
	const allHeaders = document.querySelectorAll('.accordion-list__item-header');
	const allToggles = document.querySelectorAll('.accordion-list__toggle-icon');

	allContents.forEach(content => content.classList.remove('is-active'));
	allHeaders.forEach(header => header.classList.remove('is-active'));
	allToggles.forEach(toggle => toggle.classList.remove('is-active'));
});