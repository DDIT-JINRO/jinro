document.addEventListener('DOMContentLoaded', function () {
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
        toggleButton.addEventListener('click', function () {
            this.classList.toggle('active');
            panel.classList.toggle('open');

            if (panel.style.maxHeight) {
                panel.style.maxHeight = null;
            } else {
                panel.style.maxHeight = panel.scrollHeight + 'px';
            }
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
	if(resetButton) {
	    resetButton.addEventListener('click', () => {
	        filterCheckboxes.forEach(checkbox => {
	            checkbox.checked = false;
	        });
			
	        selectedFiltersContainer.innerHTML = '';
	    });
	}
});