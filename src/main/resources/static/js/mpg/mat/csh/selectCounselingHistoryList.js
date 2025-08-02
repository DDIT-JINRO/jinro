document.addEventListener('DOMContentLoaded', function() {
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

	// 필터 생성
	const createFilterTag = (text, groupName) => {
		if (groupName === "counselStatus") {
			text = "상담 신청 상태 > " + text;
		} else if (groupName === "counselCategory") {
			text = "상담 분류 > " + text;
		} else if (groupName === "counselMethod") {
			text = "상담 방법 > " + text;
		}

        const filterTagHTML = `<span class="com-selected-filter" data-filter="${text}" data-group="${groupName}">${text}<button type="button" class="com-remove-filter">×</button></span>`;
        selectedFiltersContainer.innerHTML += filterTagHTML;
    };

	// 필터 태그 삭제
	const removeFilterTag = (groupName) => {
        const tagToRemove = selectedFiltersContainer.querySelector(`[data-group="${groupName}"]`);
        if (tagToRemove) {
            tagToRemove.remove();
        }
    };

	const handleRadioChange = (e) => {
		const inputRadio = e.target;
		const groupName = inputRadio.name;
		const labelText = inputRadio.nextElementSibling.textContent;

		removeFilterTag(groupName);

		if (inputRadio.checked) {
			createFilterTag(labelText, groupName);
		}
	}

    Object.values(allRadioGroups).forEach(radioList => {
        radioList.forEach(radio => {
            radio.addEventListener('change', handleRadioChange);
        });
    });

	// '선택된 필터' 영역에서 X 버튼 클릭 시 이벤트 처리 (이벤트 위임)
	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('com-remove-filter')) {
			const tag = e.target.closest('.com-selected-filter');
			const filterText = tag.dataset.filter;
			const groupName = tag.dataset.group;

			const radioBoxes = allRadioGroups[groupName];
			if(radioBoxes) {
				const radioToUncheck = Array.from(radioBoxes).find(
					radiobox => radiobox.nextElementSibling.textContent === filterText
				);
				if(radioToUncheck) {
					radioToUncheck.checked = false;
				}
			}
			
			// 태그 삭제
			tag.remove();
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