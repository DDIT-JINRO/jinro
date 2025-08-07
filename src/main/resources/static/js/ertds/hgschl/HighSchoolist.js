document.addEventListener('DOMContentLoaded', () => {
	const filterToggleButton = document.getElementById('filter-toggle-btn');
	const filterContent = document.getElementById('filter-content');
	const filterInputs = document.querySelectorAll('.filter-item input');
	const selectedFiltersContainer = document.getElementById('selected-filters-container');

	// 1. 상세검색 토글 기능
	if (filterToggleButton) {
		filterToggleButton.addEventListener('click', () => {
			if (filterContent.style.display === 'block') {
				filterContent.style.display = 'none';
			} else {
				filterContent.style.display = 'block';
			}
		});
	}

	// 2. 선택된 필터 태그를 업데이트하는 함수
	const updateSelectedFilters = () => {
		selectedFiltersContainer.innerHTML = ''; // 기존 태그 비우기

		filterInputs.forEach(input => {
			if (input.checked) {
				const label = input.dataset.label;
				const name = input.dataset.name;
				const value = input.value;

				const tag = document.createElement('div');
				tag.className = 'selected-tag';
				tag.innerHTML = `
                    ${label}: ${name}
                    <button type="button" class="remove-tag-btn" data-value="${value}" data-type="${input.type}">&times;</button>
                `;
				selectedFiltersContainer.appendChild(tag);
			}
		});
	};

	// 3. 필터 input에 변경 이벤트 리스너 추가
	filterInputs.forEach(input => {
		input.addEventListener('change', updateSelectedFilters);
	});

	// 4. 생성된 태그의 'x' 버튼 클릭 이벤트 처리 (이벤트 위임)
	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('remove-tag-btn')) {
			const valueToRemove = e.target.dataset.value;
			const typeToRemove = e.target.dataset.type;

			// 해당하는 input 찾아서 체크 해제
			filterInputs.forEach(input => {
				if (input.value === valueToRemove && input.type === typeToRemove) {
					input.checked = false;
				}
			});
			updateSelectedFilters(); // 태그 목록 다시 그리기
		}
	});

	// 5. 페이지 로드 시 초기 선택된 필터 표시
	updateSelectedFilters();
});