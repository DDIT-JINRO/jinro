document.addEventListener('DOMContentLoaded', () => {
	
	document.querySelectorAll('.content-list__item').forEach(univ => {
		univ.addEventListener('click', (e) => {
			location.href = '/ertds/hgschl/selectHgschDetail.do?hsId=' + univ.dataset.hsId;
		});
	});
	
	const toggleButton = document.querySelector('.search-filter__accordion-header');
	const panel = document.querySelector('.search-filter__accordion-panel');
	const allCheckboxes = document.querySelectorAll('.search-filter__option input[type="checkbox"]');
	const selectedFiltersContainer = document.querySelector('.search-filter__selected-tags');
	const resetButton = document.querySelector('.search-filter__reset-button');

	// 1. 상세검색 토글 기능
	toggleButton.addEventListener('click', function() {
		this.classList.toggle('is-active');
		panel.classList.toggle('is-open');
	});

	// 2. 선택된 필터 태그를 업데이트하는 함수
	const updateSelectedFiltersDisplay = () => {
		if (!selectedFiltersContainer) return;
	    selectedFiltersContainer.innerHTML = '';
	    
	    const filterGroups = {
	        regionFilter: { label: '지역' },
	        schoolType: { label: '학교 유형' },
	        coedTypeFilter: { label: '공학 여부' }
	    };
	
		// 3. 필터 input에 변경 이벤트 리스너 추가
		allCheckboxes.forEach(checkbox => {
		    if (checkbox.checked) {
		        const groupName = checkbox.name;
		        const groupLabel = filterGroups[groupName]?.label || '';
		        const labelText = checkbox.nextElementSibling.textContent;
		        
		        const tagHTML = `<span class="search-filter__tag" data-name="${groupName}" data-value="${checkbox.value}">${groupLabel} > ${labelText}<button type="button" class="search-filter__tag-remove">×</button></span>`;
		        selectedFiltersContainer.insertAdjacentHTML('beforeend', tagHTML);
		    }
		});
	};

	if (selectedFiltersContainer) {
		selectedFiltersContainer.addEventListener('click', (e) => {
			if (e.target.classList.contains('search-filter__tag-remove')) {
				const tag = e.target.closest('.search-filter__tag');
				const checkbox = document.querySelector(`input[name="${tag.dataset.name}"][value="${tag.dataset.value}"]`);
				if (checkbox) checkbox.checked = false;
				updateSelectedFiltersDisplay();
			}
		});
	}

	// 체크박스 변경 이벤트
	allCheckboxes.forEach(checkbox => checkbox.addEventListener('change', updateSelectedFiltersDisplay));

	// 초기화 버튼 이벤트
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			allCheckboxes.forEach(checkbox => checkbox.checked = false);
			updateSelectedFiltersDisplay();
		});
	}

	// 페이지 로드 시 초기 태그 표시
	updateSelectedFiltersDisplay();
});