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
	
	//페이지 리로드 후에도 필터 유지
	const urlParams = new URLSearchParams(window.location.search);

	  // 모든 필터 체크박스를 순회하며 URL 파라미터와 매칭
	  filterCheckboxes.forEach(checkbox => {
	      const paramName = checkbox.name; // 예: scaleId, regionId, hiringStatus
	      const paramValue = checkbox.value; // 예: CC001, 1, Y

	      // 'hiringStatus'는 단일 선택일 가능성이 높으므로 다르게 처리 (현재 JSP에서는 복수 선택 가능하도록 되어있음)
	      // 여기서는 모든 name에 대해 `getAll`을 사용하여 여러 값을 가져오도록 처리합니다.
	      const paramValues = urlParams.getAll(paramName);

	      if (paramValues.includes(paramValue)) {
	          checkbox.checked = true;
	          // 체크박스가 체크되면 해당 필터 태그를 생성
	          const labelText = checkbox.nextElementSibling.textContent;
	          createFilterTag(labelText, paramName, paramValue);
	      }
	  });

	  // 검색창 키워드 자동 채우기
	  const keywordInput = document.querySelector('input[name="keyword"]');
	  if (keywordInput) {
	      const keyword = urlParams.get('keyword');
	      if (keyword) {
	          keywordInput.value = decodeURIComponent(keyword);
	      }
	  }
	
	
	//내용 아코디언
	const accordionHeaders = document.querySelectorAll('.accordion-item .accordion-header');
	
	    accordionHeaders.forEach(header => {
	        header.addEventListener('click', function() {
	            const accordionItem = this.closest('.accordion-item');
	            const toggleIcon = this.querySelector('.toggle-icon');
	
	            // 이미 열려있는 다른 아코디언 닫기
	            document.querySelectorAll('.accordion-item.open').forEach(item => {
	                if (item !== accordionItem) {
	                    item.classList.remove('open');
	                    item.querySelector('.toggle-icon').textContent = '+';
	                }
	            });
	
	            // 현재 아코디언 열거나 닫기
	            accordionItem.classList.toggle('open');
	            if (accordionItem.classList.contains('open')) {
	                toggleIcon.textContent = '-';
	            } else {
	                toggleIcon.textContent = '+';
	            }
	        });
	    });
		
	//북마크
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
//북마크
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