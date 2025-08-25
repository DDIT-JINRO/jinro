document.addEventListener('DOMContentLoaded', function() {
	const filterOrder = ['counselStatus', 'counselCategory', 'counselMethod'];
	const toggleButton = document.getElementById('search-filter-toggle');
	const panel = document.getElementById('search-filter-panel');
	const allRadioGroups = {
	    counselStatus: document.querySelectorAll('.search-filter__option input[type="radio"][name="counselStatus"]'),
	    counselCategory: document.querySelectorAll('.search-filter__option input[type="radio"][name="counselCategory"]'),
	    counselMethod: document.querySelectorAll('.search-filter__option input[type="radio"][name="counselMethod"]'),
	    sortOrder: document.querySelectorAll('.search-filter__option input[type="radio"][name="sortOrder"]')
	};
	const selectedFiltersContainer = document.getElementById('selected-filters');
	const resetButton = document.querySelector('.search-filter__reset-button');

	// 아코디언 기능
	if (toggleButton && panel) {
		toggleButton.addEventListener('click', function() {
			const isOpen = this.classList.toggle('is-active');
			panel.classList.toggle('is-open', isOpen);
		});
	}

	// 선택된 필터 태그를 업데이트하는 함수
	const updateSelectedFiltersDisplay = () => {
		if (!selectedFiltersContainer) return;
		selectedFiltersContainer.innerHTML = '';

		filterOrder.forEach(groupName => {
			const radioList = allRadioGroups[groupName];
			const selectedRadio = Array.from(radioList).find(radio => radio.checked);

			if (selectedRadio) {
				let text = selectedRadio.nextElementSibling.textContent;
				let groupText = '';

				if (groupName === "counselStatus") groupText = "상담 신청 상태";
				else if (groupName === "counselCategory") groupText = "상담 분류";
				else if (groupName === "counselMethod") groupText = "상담 방법";

				// [수정] 생성되는 태그의 클래스명을 변경
				const filterTagHTML = `
	                <span class="search-filter__tag" data-group="${groupName}">
	                    ${groupText} > ${text}
	                    <button type="button" class="search-filter__tag-remove">×</button>
	                </span>`;
				selectedFiltersContainer.innerHTML += filterTagHTML;
			}
		});
		
		const sortRadioList = allRadioGroups['sortOrder'];
		if (sortRadioList) {
		    const selectedSortRadio = Array.from(sortRadioList).find(radio => radio.checked);
		    if (selectedSortRadio) {
		        const text = selectedSortRadio.nextElementSibling.textContent;
		        const groupText = '정렬';

		        const filterTagHTML = `
		            <span class="search-filter__tag" data-group="sortOrder">
		                ${groupText} > ${text}
		                <button type="button" class="search-filter__tag-remove">×</button>
		            </span>`;
		        selectedFiltersContainer.innerHTML += filterTagHTML;
		    }
		}
	};

	// 라디오 버튼 변경 시 이벤트 핸들러
	Object.values(allRadioGroups).forEach(radioList => {
		radioList.forEach(radio => {
			radio.addEventListener('change', updateSelectedFiltersDisplay);
		});
	});

	// '선택된 필터' 영역의 X 버튼 클릭 이벤트 (이벤트 위임)
	if (selectedFiltersContainer) {
		selectedFiltersContainer.addEventListener('click', (e) => {
			// [수정] 클릭된 요소의 클래스명 변경
			if (e.target.classList.contains('search-filter__tag-remove')) {
				const tag = e.target.closest('.search-filter__tag');
				const groupName = tag.dataset.group;

				const radioToUncheck = Array.from(allRadioGroups[groupName]).find(radio => radio.checked);
				if (radioToUncheck) {
					radioToUncheck.checked = false;
				}
				updateSelectedFiltersDisplay();
			}
		});
	}

	// 초기화 버튼 클릭 이벤트
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			Object.values(allRadioGroups).forEach(radioList => {
				radioList.forEach(radio => {
					radio.checked = false;
				});
			});
			updateSelectedFiltersDisplay();
		});
	}

	// 페이지 로드 시 초기 필터 상태 표시
	updateSelectedFiltersDisplay();
});