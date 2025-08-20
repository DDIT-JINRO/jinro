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
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/cnslt/rvw/insertCnsReviewView.do";
		}
	})
	
	const cardHeaders = document.querySelectorAll(".accordion-list__item-header");
	
	cardHeaders.forEach((cardHeader) => {
		cardHeader.addEventListener("click", function() {
			toggleCard(this);
		})
	})
	
	const deleteBtns = document.querySelectorAll(".card-actions__button--delete");
	
	deleteBtns.forEach((deleteBtn) => {
		deleteBtn.addEventListener("click", function() {
			const crId = this.dataset.crId;
			deleteCounselingReview(crId)
		})
	})
	
	const editBtns = document.querySelectorAll(".card-actions__button--edit");
	
	editBtns.forEach((editBtn) => {
		editBtn.addEventListener("click", function() {
			const dataMemId = this.dataset.memId;
			const crId = this.dataset.crId;
			
			if (dataMemId != memId) {
				alert("허용되지 않은 접근입니다.")
				return;
			}
			
			location.href = `/cnslt/rvw/updateCnsReviewView.do?crId=${crId}` 
		})
	})
});

function toggleCard(header) {
	const currentCard = header.closest('.accordion-list__item');
	const currentContent = currentCard.querySelector('.accordion-list__item-content');
	const currentToggle = header.querySelector('.accordion-list__toggle-icon');
	const isOpening = !currentContent.classList.contains('is-active');

	// 모든 아이템을 먼저 닫음
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
function deleteCounselingReview(crId) {
    if (confirm('정말로 이 상담 후기를 삭제하시겠습니까?\n삭제된 후기는 복구할 수 없습니다.\n또한 해당 상담에 대하여 후기를 다시 작성 할 수 없습니다.')) {
		
        fetch('/cnslt/rvw/deleteCnsReview.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'crId=' + encodeURIComponent(crId)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('삭제 요청이 실패했습니다.');
        })
        .then(data => {
            if (data.success) {
                alert('상담 후기가 성공적으로 삭제되었습니다.');
                location.reload(); // 페이지 새로고침
            } else {
                alert('삭제 중 오류가 발생했습니다: ' + (data.message || '알 수 없는 오류'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('삭제 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
    }
}

// 페이지 로드 시 모든 카드 닫힌 상태로 초기화
document.addEventListener('DOMContentLoaded', function() {
	document.querySelectorAll('.accordion-list__item-content').forEach(content => content.classList.remove('is-active'));
	document.querySelectorAll('.accordion-list__item-header').forEach(header => header.classList.remove('is-active'));
	document.querySelectorAll('.accordion-list__toggle-icon').forEach(toggle => toggle.classList.remove('is-active'));
});

document.addEventListener('DOMContentLoaded', function() {
	// 필터 정렬 순서
	const filterOrder = ['counselCategory', 'counselMethod'];

	// 토글 버튼
	const toggleButton = document.querySelector('.search-filter__accordion-header');

	// 필터 패널 
	const panel = document.querySelector('.search-filter__accordion-panel');

	// 필터 키워드
	const allCheckboxGroups = {
	    counselMethods: document.querySelectorAll('.search-filter__option input[name="counselMethods"]'),
		counselCategorys: document.querySelectorAll('.search-filter__option input[name="counselCategorys"]'),
	};
	
	// 선택 필터 영역
	const selectedFiltersContainer = document.querySelector('.search-filter__selected-tags');

	// 초기화 버튼
	const resetButton = document.querySelector('.search-filter__reset-button');

	// 아코디언 코드
	if (toggleButton && panel) {
		toggleButton.addEventListener('click', function() {
			this.classList.toggle('is-active');
			panel.classList.toggle('is-open');
		});
	}

	// 선택된 필터 업데이트
	const updateSelectedFiltersDisplay = () => {

        selectedFiltersContainer.innerHTML = ''

	    // 정의된 순서(filterOrder)대로 각 필터 그룹을 확인합니다.
	    filterOrder.forEach(groupName => {
	        const checkboxList = allCheckboxGroups[groupName];

	        const selectedCheckboxes = Array.from(checkboxList).filter(checkBox => checkBox.checked);

            // 선택된 모든 체크박스에 대해 반복 실행
            selectedCheckboxes.forEach(checkbox => {
                let text = checkbox.nextElementSibling.textContent;

                if (groupName === "counselCategory") {
                    text = "상담 분류 > " + text;
                } else if (groupName === "counselMethod") {
                    text = "상담 방법 > " + text;
                }
                
                const filterTagHTML = `<span class="search-filter__tag" data-group="${groupName}" data-value="${checkbox.value}">${text}<button type="button" class="com-remove-filter">×</button></span>`;
                selectedFiltersContainer.innerHTML += filterTagHTML;
            });
	    });
	};

	// 체크 박스 선택 시 이벤트
	const handleCheckboxChange = () => {
		updateSelectedFiltersDisplay();
	}

    // 이벤트 등록
    Object.values(allCheckboxGroups).forEach(checkboxList => {
        checkboxList.forEach(checkbox => {
            checkbox.addEventListener('change', handleCheckboxChange);
        });
    });

	// '선택된 필터' 영역에서 X 버튼 클릭 시 이벤트 처리 (이벤트 위임)
	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('search-filter__tag-remove')) {
			const tag = e.target.closest('.search-filter__tag');
			const groupName = tag.dataset.group;
            const value = tag.dataset.value;

	        const checkboxToUncheck = Array.from(allCheckboxGroups[groupName]).find(checkbox => checkbox.value === value);

	        if (checkboxToUncheck) {
	            checkboxToUncheck.checked = false;
	        }

			// 화면을 다시 그립니다.
			updateSelectedFiltersDisplay();
		}
	});

	// 초기화 버튼 클릭 시 이벤트 처리
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			Object.values(allCheckboxGroups).forEach(checkboxList => {
				checkboxList.forEach(checkbox => {
					checkbox.checked = false;
				});
			});

			selectedFiltersContainer.innerHTML = '';
		});
	}
});