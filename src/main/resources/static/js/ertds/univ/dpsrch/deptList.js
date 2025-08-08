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

	// 디테일 페이지로 이동
	document.querySelectorAll('.univDept-item').forEach(dept => {
		dept.addEventListener('click', (e) => {
			// 북마크 버튼 눌렀을 때에는 디테일 페이지로 넘어가지 않도록 방지
			if (e.target.closest('.bookmark-btn') || e.target.closest('.select-btn')) {
				return;
			}
			location.href = '/ertds/univ/dpsrch/selectDetail.do?uddId=' + dept.dataset.univdeptId;
		});
	});
	
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
	}
	
	// 이벤트 추가
	document.querySelectorAll('.bookmark-btn').forEach(button => {
		button.addEventListener('click', function(event) {
			event.preventDefault();
			// 함수 전달
			handleBookmarkToggle(this);
		});
	});
});

const handleBookmarkToggle = (button) => {
	if (memId == "" || memId == "anonymousUser") {
        alert("북마크는 로그인 후 이용 하실 수 있습니다.");
        return;
    }
	
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

// 비교 팝업
document.addEventListener('DOMContentLoaded', function() {
    const popup = document.querySelector(".dept-compare-popup");
    const compareListContainer = document.querySelector(".compare-list");
    const closeBtn = document.querySelector(".btn-close-popup");
    const resetBtn = document.querySelector(".btn-clear-all");
    const submitBtn = document.querySelector(".btn-view-results");
    const selectButtons = document.querySelectorAll(".select-btn input");

    const floatBtnContainer = document.querySelector(".right-fixed-bar");

    const popupOpenBtn = `
        <button type="button" class="open-popup-btn">비교</button>
    `;

    floatBtnContainer.insertAdjacentHTML('beforeend', popupOpenBtn);

    // 기존 데이터 가져오기
    const initialCompareList = getCompareList();

    // 다시 출력
    if (Object.keys(initialCompareList).length > 0) {
        compareListContainer.innerHTML = '';
        for (const uddId in initialCompareList) {
            const deptData = initialCompareList[uddId];
            renderCompareItem(deptData, compareListContainer);
            const checkbox = document.querySelector(`#compare-btn${uddId}`);
            if (checkbox) {
                checkbox.checked = true;
            }
        }
    }

    // 비교 체크 박스 클릭
    selectButtons.forEach(button => {
        button.addEventListener('change', function(event) {
			event.preventDefault();
            if (event.target.checked) {
                createCompareCard(this, compareListContainer);
            } else {
                deleteCompareCard(this.value);
            }
        });
    })

    // 팝업 닫기
    closeBtn.addEventListener('click', function() {
        popup.classList.remove('is-open');
    });

    // 직업 카드 삭제
    compareListContainer.addEventListener('click', function(event) {
        const target = event.target.closest(".btn-remove-item");
        if (target) {
            const uddId = target.dataset.removeItem;
            deleteCompareCard(uddId);
        }
    })

    // 초기화 버튼
    resetBtn.addEventListener('click', function() {
        const currentCompareList = getCompareList();
        for (const uddId in currentCompareList)  {
            const checkbox = document.querySelector(`#compare-btn${uddId}`);
            if (checkbox) {
                checkbox.checked = false;
            }
        }
        compareListContainer.innerHTML = "";
        sessionStorage.removeItem("compareList");
        popup.classList.remove('is-open');
    });

    // 비교 페이지 이동
    submitBtn.addEventListener('click', function() {
        const currentCompareList = getCompareList();
        const uddIds = Object.keys(currentCompareList);

        if (uddIds.length < 2) {
            alert("비교할 학과를 2개 이상 선택해주세요.");
            return;
        }
		
		for (const uddId in currentCompareList)  {
            const checkbox = document.querySelector(`#compare-btn${uddId}`);
            if (checkbox) {
                checkbox.checked = false;
            }
        }
		
		sessionStorage.removeItem("compareList");
        const queryString = uddIds.map(id => `uddIds=${id}`).join('&');
        window.location.href = `/ertds/univ/dpsrch/selectCompare.do?${queryString}`;
    });

    // 팝업 열기 버튼
    document.addEventListener('click', function(event) {
        const target = event.target.closest(".open-popup-btn");

        if (target) {
            popup.classList.add('is-open');
        }
    })
});

// 세션에 비교 목록 가져오기
const getCompareList = () => {
    const compareList = sessionStorage.getItem('compareList');
    return compareList ? JSON.parse(compareList) : {};
}

// 세션에 저장하기
const saveCompareList = (compareList) => {
    sessionStorage.setItem('compareList', JSON.stringify(compareList));
}

// 비교 카드 생성하기
const createCompareCard = (button, compareListContainer) => {
    const popup = document.querySelector(".dept-compare-popup");

    const deptData = {
        uddId: button.value,
        deptName: button.dataset.deptName,
        deptSalary: button.dataset.deptSal,
        deptEmp: button.dataset.deptEmp,
        deptAdmission: button.dataset.deptAdmission
    }
    
    const compareList = getCompareList();

    if (Object.keys(compareList).length >= 5) {
        alert("직업 비교는 최대 5개 까지만 가능합니다.");
        button.checked = false;
        return;
    }

    compareList[deptData.uddId] = deptData;
    saveCompareList(compareList);

    renderCompareItem(deptData, compareListContainer);

    if(!popup.classList.contains('is-open')) {
        popup.classList.add('is-open');
    }
}

const renderCompareItem = (deptData, container) => {
    const compareItemHtml = `
        <div class="compare-item-card" id="compare-card-${deptData.uddId}" data-job-code=${deptData.uddId}>
            <div class="card-header">
                <h3 class="card-title">${deptData.deptName}</h3>
                <button type="button" class="btn-remove-item" aria-label="삭제" data-remove-item="${deptData.uddId}">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" width="16" height="16">
                        <path d="M2.22 2.22a.75.75 0 0 1 1.06 0L8 6.94l4.72-4.72a.75.75 0 1 1 1.06 1.06L9.06 8l4.72 4.72a.75.75 0 1 1-1.06 1.06L8 9.06l-4.72 4.72a.75.75 0 0 1-1.06-1.06L6.94 8 2.22 3.28a.75.75 0 0 1 0-1.06Z" />
                    </svg>
                </button>
            </div>
            <div class="card-metrics">
                <div class="metric">
                    <div class="metric-text">
                        <span class="metric-label">입학경쟁률</span>
                        <span class="metric-value">${deptData.deptAdmission}</span>
                    </div>
                </div>
                <div class="metric">
                    <div class="metric-text">
                        <span class="metric-label">취업률</span>
                        <span class="metric-value">${deptData.deptEmp}%</span>
                    </div>
                </div>
                <div class="metric">
                    <div class="metric-text">
                        <span class="metric-label">첫월급 평균</span>
                        <span class="metric-value">${deptData.deptSalary}만원</span>
                    </div>
                </div>
            </div>
        </div>
    `;
    container.innerHTML += compareItemHtml;
}

const deleteCompareCard = (itemId) => {
    const removeItem = document.querySelector(`#compare-card-${itemId}`);
    if (removeItem) {
        removeItem.remove();
    }
    
    const removeItemCheckbox = document.querySelector(`#compare-btn${itemId}`);
    if (removeItemCheckbox) {
        removeItemCheckbox.checked = false;
    }

    const compareList = getCompareList();
    delete compareList[itemId];
    saveCompareList(compareList);

    if (Object.keys(compareList).length === 0) {
        document.querySelector(".dept-compare-popup").classList.remove('is-open');
    };
}