document.addEventListener('DOMContentLoaded', function() {
	// 필터 정렬 순서
	const filterOrder = ['jobLclCategory', 'jobSalCategory'];

	// 토글 버튼
	const toggleButton = document.getElementById('com-accordion-toggle');

	// 필터 패널 
	const panel = document.getElementById('com-accordion-panel');

	// 필터 키워드
    const allCheckboxGroups = {
        jobLclCategory: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="jobLcl"]'),
        jobSalCategory: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="jobSal"]'),
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

                if (groupName === "jobSalCategory") {
                    text = "연봉 > " + text;
                } else if (groupName === "jobLclCategory") {
                    text = "직업 대분류 > " + text;
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

document.addEventListener('DOMContentLoaded', function() {

    // 모든 북마크 버튼
    const bookmarkButtons = document.querySelectorAll('.bookmark-btn');

    // 이벤트 추가
    bookmarkButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); 
            
            // 함수 전달
            handleBookmarkToggle(this);
        });
    });

});

const handleBookmarkToggle = (button) => {
    const bmCategoryId = button.dataset.categoryId;
    const bmTargetId = button.dataset.targetId;

    // 현재 버튼이 'active' 클래스를 가지고 있는지 확인
    const isBookmarked = button.classList.contains('active');
    
    const data = {
        bmCategoryId: bmCategoryId,
		bmTargetId: bmTargetId
    };

    const apiUrl = isBookmarked ? '/mpg/mat/bmk/deleteBookmark.do' : '/mpg/mat/bmk/insertBookmark.do';

    fetch(apiUrl, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('서버 응답에 실패했습니다.');
        }
        return response.json();
    })
    .then(data => {
		console.log(data);
        if (data.success) {
			alert(data.message);
            button.classList.toggle('active');
        } else {
            alert(data.message || '북마크 처리에 실패했습니다.');
        }
    })
    .catch(error => {
        // 네트워크 오류나 서버 응답 실패 시
        console.error('북마크 처리 중 오류 발생:', error);
        alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
    });
}