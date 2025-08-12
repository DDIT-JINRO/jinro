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
	
	const cardHeaders = document.querySelectorAll(".card-header");
	
	cardHeaders.forEach((cardHeader) => {
		cardHeader.addEventListener("click", function() {
			toggleCard(this);
		})
	})
	
	const deleteBtns = document.querySelectorAll(".delete-btn");
	
	deleteBtns.forEach((deleteBtn) => {
		deleteBtn.addEventListener("click", function() {
			const crId = this.dataset.crId;
			deleteCounselingReview(crId)
		})
	})
	
	const editBtns = document.querySelectorAll(".edit-btn");
	
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
    const card = header.parentElement;
    const content = card.querySelector('.card-content');
    const toggle = header.querySelector('.card-toggle');
    
    // 현재 카드의 활성 상태 토글
    const isActive = content.classList.contains('active');
    
    if (isActive) {
        content.classList.remove('active');
        header.classList.remove('active');
        toggle.classList.remove('active');
    } else {
        content.classList.add('active');
        header.classList.add('active');
        toggle.classList.add('active');
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
    const allContents = document.querySelectorAll('.card-content');
    const allHeaders = document.querySelectorAll('.card-header');
    const allToggles = document.querySelectorAll('.card-toggle');
    
    allContents.forEach(content => content.classList.remove('active'));
    allHeaders.forEach(header => header.classList.remove('active'));
    allToggles.forEach(toggle => toggle.classList.remove('active'));
});

document.addEventListener('DOMContentLoaded', function() {
	// 필터 정렬 순서
	const filterOrder = ['counselCategory', 'counselMethod'];

	// 토글 버튼
	const toggleButton = document.getElementById('com-accordion-toggle');

	// 필터 패널 
	const panel = document.getElementById('com-accordion-panel');

	// 필터 키워드
	const allCheckboxGroups = {
	    counselCategory: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="counselCategory"]'),
	    counselMethod: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="counselMethod"]'),
	};
	
	// 선택 필터 영역
	const selectedFiltersContainer = document.querySelector('.com-selected-filters');
	
	// 초기화 버튼
	const resetButton = document.querySelector('.com-filter-reset-btn');

	// 아코디언 코드
	if (toggleButton && panel) {
		toggleButton.addEventListener('click', function() {
			this.classList.toggle('active');
			panel.classList.toggle('open');
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
                
                const filterTagHTML = `<span class="com-selected-filter" data-group="${groupName}" data-value="${checkbox.value}">${text}<button type="button" class="com-remove-filter">×</button></span>`;
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
		if (e.target.classList.contains('com-remove-filter')) {
			const tag = e.target.closest('.com-selected-filter');
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