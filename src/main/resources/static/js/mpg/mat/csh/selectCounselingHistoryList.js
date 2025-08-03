document.addEventListener('DOMContentLoaded', function() {
	// 필터 정렬 순서
	const filterOrder = ['counselStatus', 'counselCategory', 'counselMethod'];
	
	// 토글 버튼
	const toggleButton = document.getElementById('com-accordion-toggle');

	// 필터 패널 
	const panel = document.getElementById('com-accordion-panel');

	// 필터 키워드
    const allRadioGroups = {
        counselStatus: document.querySelectorAll('.com-filter-item input[type="radio"][name="counselStatus"]'),
        counselCategory: document.querySelectorAll('.com-filter-item input[type="radio"][name="counselCategory"]'),
        counselMethod: document.querySelectorAll('.com-filter-item input[type="radio"][name="counselMethod"]')
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

			if (panel.style.maxHeight) {
				panel.style.maxHeight = null;
			} else {
				panel.style.maxHeight = panel.scrollHeight + 'px';
			}
		});
	}
	
	// 선택된 필터 업데이트
	const updateSelectedFiltersDisplay = () => {
	    // 일단 기존 태그를 모두 비웁니다.
	    selectedFiltersContainer.innerHTML = '';

	    // 정의된 순서(filterOrder)대로 각 필터 그룹을 확인합니다.
	    filterOrder.forEach(groupName => {
	        const radioList = allRadioGroups[groupName];
	        const selectedRadio = Array.from(radioList).find(radio => radio.checked);

	        // 해당 그룹에서 선택된 라디오 버튼이 있다면 태그를 생성합니다.
	        if (selectedRadio) {
	            let text = selectedRadio.nextElementSibling.textContent;

	            // 그룹 이름에 따라 텍스트를 포맷합니다.
	            if (groupName === "counselStatus") {
	                text = "상담 신청 상태 > " + text;
	            } else if (groupName === "counselCategory") {
	                text = "상담 분류 > " + text;
	            } else if (groupName === "counselMethod") {
	                text = "상담 방법 > " + text;
	            }

	            const filterTagHTML = `<span class="com-selected-filter" data-group="${groupName}">${text}<button type="button" class="com-remove-filter">×</button></span>`;
	            selectedFiltersContainer.innerHTML += filterTagHTML;
	        }
	    });
	};

	// 라디오 선택 시 이벤트
	const handleRadioChange = () => {
		updateSelectedFiltersDisplay();
	}

	// 이벤트 등록
    Object.values(allRadioGroups).forEach(radioList => {
        radioList.forEach(radio => {
            radio.addEventListener('change', handleRadioChange);
        });
    });

	// '선택된 필터' 영역에서 X 버튼 클릭 시 이벤트 처리 (이벤트 위임)
	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('com-remove-filter')) {
			const tag = e.target.closest('.com-selected-filter');
			const groupName = tag.dataset.group;

	        // 해당하는 라디오 버튼을 찾아서 체크 해제합니다.
			const radioToUncheck = Array.from(allRadioGroups[groupName]).find(radio => radio.checked);
	        if (radioToUncheck) {
	            radioToUncheck.checked = false;
	        }

			// 화면을 다시 그립니다.
			updateSelectedFiltersDisplay();
		}
	});

	// 초기화 버튼 클릭 시 이벤트 처리
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			Object.values(allRadioGroups).forEach(radioList => {
				radioList.forEach(radio => {
					radio.checked = false;
				});
			});

			selectedFiltersContainer.innerHTML = '';
		});
	}
});