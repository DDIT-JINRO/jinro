/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	// 토글 버튼
	const toggleButton = document.getElementById('com-accordion-toggle');

	// 필터 패널 
	const panel = document.getElementById('com-accordion-panel');

	// 필터 키워드
	const filterCheckboxes = document.querySelectorAll('.com-filter-item input[type="checkbox"]');

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

	// 필터 태그 추가
	const createFilterTag = (text) => {
		const filterTag = `<span class="com-selected-filter" data-filter="${text}">${text}<button type="button" class="com-remove-filter">×</button></span>`;

		selectedFiltersContainer.innerHTML += filterTag;
	};

	// 필터 태그 삭제
	const removeFilterTag = (text) => {
		const tagToRemove = selectedFiltersContainer.querySelector(`[data-filter="${text}"]`);
		if (tagToRemove) {
			selectedFiltersContainer.removeChild(tagToRemove);
		}
	};

	// 체크박스 변경 시 이벤트 처리
	filterCheckboxes.forEach(checkbox => {
		checkbox.addEventListener('change', (e) => {
			const labelText = e.target.nextElementSibling.textContent;
			if (e.target.checked) {
				createFilterTag(labelText);
			} else {
				removeFilterTag(labelText);
			}
		});
	});

	// '선택된 필터' 영역에서 X 버튼 클릭 시 이벤트 처리 (이벤트 위임)
	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('com-remove-filter')) {
			const tag = e.target.closest('.com-selected-filter');
			const filterText = tag.dataset.filter;

			// 연결된 체크박스 찾아서 해제
			const checkboxToUncheck = Array.from(filterCheckboxes).find(
				cb => cb.nextElementSibling.textContent === filterText
			);
			if (checkboxToUncheck) {
				checkboxToUncheck.checked = false;
			}

			// 태그 삭제
			tag.remove();
		}
	});

	// 초기화 버튼 클릭 시 이벤트 처리
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			filterCheckboxes.forEach(checkbox => {
				checkbox.checked = false;
			});

			selectedFiltersContainer.innerHTML = '';
		});

		// 페이지가 로드될 때 URL을 분석하여 필터를 복원하는 함수
		function restoreFiltersFromUrl() {
			// 1. 현재 페이지의 URL에서 모든 파라미터를 읽어옵니다.
			const urlParams = new URLSearchParams(window.location.search);

			// 2. 모든 필터 체크박스를 하나씩 확인합니다.
			filterCheckboxes.forEach(checkbox => {
				const paramName = checkbox.name;   // <input>의 name 속성 (예: contestGubunFilter)
				const paramValue = checkbox.value; // <input>의 value 속성 (예: G32001)

				// 3. URL에서 현재 체크박스의 name과 일치하는 모든 파라미터 값들을 가져옵니다.
				const paramValues = urlParams.getAll(paramName);

				// 4. 만약 URL 파라미터 목록에 현재 체크박스의 value가 포함되어 있다면,
				if (paramValues.includes(paramValue)) {
					checkbox.checked = true; // 해당 체크박스를 체크 상태로 만듭니다.

					// 5. '선택된 필터' 영역에 태그도 함께 생성합니다.
					const labelText = checkbox.nextElementSibling.textContent;
					createFilterTag(labelText);
				}
			});
		}

		// 페이지가 처음 로드될 때 필터 복원 함수를 실행합니다.
		restoreFiltersFromUrl();
	}

	
});