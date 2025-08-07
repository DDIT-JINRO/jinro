document.addEventListener('DOMContentLoaded', function() {
	const channelSection = document.querySelector(".channel");
	if (channelSection) {
	    const errorMessage = channelSection.dataset.errorMessage;
	    if (errorMessage) {
			alert(errorMessage);
			window.location.href="/"
		}
	}

	// 필터 정렬 순서
	const filterOrder = ['jobLclCategory', 'jobSalCategory'];

	// 토글 버튼
	const toggleButton = document.getElementById('com-accordion-toggle');

	// 필터 패널 
	const panel = document.getElementById('com-accordion-panel');

	// 필터 키워드
    const allCheckboxGroups = {
        jobLclCategory: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="jobLcls"]'),
        jobSalCategory: document.querySelectorAll('.com-filter-item input[type="checkbox"][name="jobSals"]'),
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

// 비교 팝업
document.addEventListener('DOMContentLoaded', function() {
    const popup = document.querySelector(".job-compare-popup");
    const compareListContainer = document.querySelector(".compare-list");
    const closeBtn = document.querySelector(".btn-close-popup");
    const resetBtn = document.querySelector(".btn-clear-all");
    const submitBtn = document.querySelector(".btn-view-results");
    const selectButtons = document.querySelectorAll(".select-btn input");

    const floatBtnContainer = document.querySelector(".right-fixed-bar");

    const popupOpenBtn = `
        <button type="button" class="open-popup-btn">비교</button>
    `;

    floatBtnContainer.innerHTML += popupOpenBtn;

    // 기존 데이터 가져오기
    const initialCompareList = getCompareList();

    // 다시 출력
    if (Object.keys(initialCompareList).length > 0) {
        compareListContainer.innerHTML = '';
        for (const jobCode in initialCompareList) {
            const jobData = initialCompareList[jobCode];
            renderCompareItem(jobData, compareListContainer);
            const checkbox = document.querySelector(`#compare-btn${jobCode}`);
            if (checkbox) {
                checkbox.checked = true;
            }
        }
    }

    // 비교 체크 박스 클릭
    selectButtons.forEach(button => {
        button.addEventListener('change', function(event) {
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
            const jobCode = target.dataset.removeItem;
            deleteCompareCard(jobCode);
        }
    })

    // 초기화 버튼
    resetBtn.addEventListener('click', function() {
        const currentCompareList = getCompareList();
        for (const jobCode in currentCompareList)  {
            const checkbox = document.querySelector(`#compare-btn${jobCode}`);
            if (checkbox) {
                checkbox.checked = false;
            }
        }
        compareListContainer.innerHTML = "";
        sessionStorage.removeItem("compareList")
        popup.classList.remove('is-open');
    });

    // 비교 페이지 이동
    submitBtn.addEventListener('click', function() {
        const currentCompareList = getCompareList();
        const jobCodes = Object.keys(currentCompareList);

        if (jobCodes.length < 2) {
            alert("비교할 직업을 2개 이상 선택해주세요.");
            return;
        }

        const queryString = jobCodes.map(code => `jobCodes=${code}`).join('&');

        window.location.href = `/pse/cr/cco/careerComparisonView.do?${queryString}`;
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
    const test = getCompareList();
}

// 비교 카드 생성하기
const createCompareCard = (button, compareListContainer) => {
    const popup = document.querySelector(".job-compare-popup");

    const jobData = {
        jobCode: button.value,
        jobName: button.dataset.jobName,
        jobSalaly: button.dataset.jobSal,
        jobProspect: button.dataset.jobProspect,
        jobSatis: button.dataset.jobSatis
    }
    
    const compareList = getCompareList();

    if (Object.keys(compareList).length >= 5) {
        alert("직업 비교는 최대 5개 까지만 가능합니다.");
        button.checked = false;
        return;
    }

    compareList[jobData.jobCode] = jobData;
    saveCompareList(compareList);

    renderCompareItem(jobData, compareListContainer);

    if(!popup.classList.contains('is-open')) {
        popup.classList.add('is-open');
    }
}

const renderCompareItem = (jobData, container) => {
    const compareItemHtml = `
        <div class="compare-item-card" id="compare-card-${jobData.jobCode}" data-job-code=${jobData.jobCode}>
            <div class="card-header">
                <h3 class="card-title">${jobData.jobName}</h3>
                <button type="button" class="btn-remove-item" aria-label="삭제" data-remove-item="${jobData.jobCode}">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" fill="currentColor" width="16" height="16">
                        <path d="M2.22 2.22a.75.75 0 0 1 1.06 0L8 6.94l4.72-4.72a.75.75 0 1 1 1.06 1.06L9.06 8l4.72 4.72a.75.75 0 1 1-1.06 1.06L8 9.06l-4.72 4.72a.75.75 0 0 1-1.06-1.06L6.94 8 2.22 3.28a.75.75 0 0 1 0-1.06Z" />
                    </svg>
                </button>
            </div>
            <div class="card-metrics">
                <div class="metric">
                    <img src="/images/jobAverageImg.png" alt="연봉 아이콘" class="metric-icon">
                    <div class="metric-text">
                        <span class="metric-label">평균 연봉</span>
                        <span class="metric-value">${jobData.jobSalaly}</span>
                    </div>
                </div>
                <div class="metric">
                    <img src="/images/jobProspectImg.png" alt="전망 아이콘" class="metric-icon">
                    <div class="metric-text">
                        <span class="metric-label">미래 전망</span>
                        <span class="metric-value">${jobData.jobProspect}</span>
                    </div>
                </div>
                <div class="metric">
                    <img src="/images/jobSatisImg.png" alt="만족도 아이콘" class="metric-icon">
                    <div class="metric-text">
                        <span class="metric-label">만족도</span>
                        <span class="metric-value">${jobData.jobSatis}점</span>
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
        document.querySelector(".job-compare-popup").classList.remove('is-open');
    };
}