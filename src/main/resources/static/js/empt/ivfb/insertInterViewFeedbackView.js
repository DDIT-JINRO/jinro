document.addEventListener("DOMContentLoaded", function() {
	document.querySelector('#file-input').addEventListener('change', function(e) {
		const fileName = e.target.files[0]?.name || '';
		const fileNameDisplay = document.querySelector('.file-uploader__filename b');
		const fileNameContainer = document.querySelector('.file-uploader__filename');

		if (fileName) {
			fileNameDisplay.textContent = fileName;
			fileNameContainer.classList.add('is-active');
		} else {
			fileNameContainer.classList.remove('is-active');
		}
	});

	document.querySelector("#submit-btn").addEventListener("click", async function() {
		const cpNameInput = document.querySelector("#company-name");

		const cpId = cpNameInput.dataset.cpId;
		const interviewPosition = document.querySelector("#interview-position").value.trim();
		const interviewDate = document.querySelector("#interview-date").value.trim();
		const interviewDetail = document.querySelector("#interview-detail").value.trim();
		const interviewRating = window.getInterviewRating();
		const files = document.querySelector("#file-input").files;

		if (!interviewDate) {
			showConfirm2("면접 일자를 입력해 주세요.", 
			    () => {
					
			    },
			    () => {
			        
			    }
			);
			return;
		}

		if (interviewRating === 0) {
			showConfirm2("기업 평가를 선택해 주세요.", 
			    () => {
					
			    },
			    () => {
			        
			    }
			);
			return;
		}

		if (!interviewDetail) {
			showConfirm2("면접 후기를 입력해 주세요.", 
			    () => {
					
			    },
			    () => {
			        
			    }
			);
			return;
		}

		if (files.length === 0) {
			showConfirm2("증빙자료를 첨부해 주세요.", 
			    () => {
					
			    },
			    () => {
			        
			    }
			);
			return;
		}

		if (files.length > 1) {
			showConfirm2("증빙자료는 1장만 첨부해 주세요.", 
			    () => {
					
			    },
			    () => {
			        
			    }
			);
			return;
		}

		// FormData 생성
		const formData = new FormData();
		formData.append('irType', 'G02002')
		formData.append('targetId', cpId);
		formData.append('irContent', interviewDetail);
		formData.append('irRating', interviewRating);
		formData.append('irApplication', interviewPosition);
		formData.append('irInterviewAt', new Date(interviewDate));

		// 파일 추가
		formData.append('file', files[0]);

		try {
			const response = await fetch("/empt/ivfb/insertInterViewFeedback.do", {
				method: "POST",
				body: formData
			});

			if (response.ok) {
				const result = await response.json();

				if (result.success) {
					showConfirm2("후기 등록 요청이 완료되었습니다", 
					    () => {
							window.location.href = "/empt/ivfb/interViewFeedback.do";
					    },
					    () => {
					        
					    }
					);
				} else {
					alert(result.message || "등록에 실패했습니다.");
				}
			} else {
				throw new Error(`서버 응답 오류: ${response.status}`);
			}
		} catch (error) {
			console.error("등록 중 오류:", error);
			alert("등록에 실패했습니다.");
		}
	});

	document.querySelector("#back-btn").addEventListener("click", function() {
		window.location.href = "/empt/ivfb/interViewFeedback.do";
	});
});

// 별점 평가 기능
document.addEventListener('DOMContentLoaded', function() {
	const starRating = document.getElementById('company-rating');
	const ratingText = document.getElementById('rating-text');
	const stars = starRating.querySelectorAll('.rating-input__star');

	// 별점 설명 텍스트
	const ratingTexts = {
		0: '평가해주세요',
		1: '매우 불만족',
		2: '불만족',
		3: '보통',
		4: '만족',
		5: '매우 만족'
	};

	let currentRating = 0;

	// 별 클릭 이벤트
	stars.forEach((star) => {
		star.addEventListener('click', function() {
			const rating = parseInt(this.dataset.value);
			setRating(rating);
		});
		star.addEventListener('mouseenter', function() {
			const rating = parseInt(this.dataset.value);
			highlightStars(rating, true);
			updateRatingText(rating, true);
		});
	});

	// 별점 컨테이너에서 마우스가 나갔을 때
	starRating.addEventListener('mouseleave', function() {
		highlightStars(currentRating, false);
		updateRatingText(currentRating, false);
	});

	// 별점 설정 함수
	function setRating(rating) {
		currentRating = rating;
		starRating.dataset.rating = rating;
		highlightStars(rating, false);
		updateRatingText(rating, false);
	}

	// 별 하이라이트 함수
	function highlightStars(rating, isHover) {
		stars.forEach((star, index) => {
			star.classList.remove('is-active', 'is-hover');
			if (index < rating) {
				if (isHover) {
					star.classList.add('is-hover');
				} else {
					star.classList.add('is-active');
				}
			}
		});
	}

	// 평가 텍스트 업데이트 함수
	function updateRatingText(rating, isHover) {
		ratingText.textContent = ratingTexts[rating];

		if (rating > 0) {
			ratingText.classList.add('is-selected');
		} else {
			ratingText.classList.remove('is-selected');
		}
	}

	// 별점 값을 가져오는 함수 (폼 제출 시 사용)
	window.getInterviewRating = function() {
		return currentRating;
	};
});

// textarea 글자수 카운터 기능
document.addEventListener('DOMContentLoaded', function() {
	const textarea = document.getElementById('interview-detail');
	const maxLength = 300; // 최대 글자수 설정

	// 글자수 카운터 HTML 생성
	const counterHTML = `
        <div class="char-counter">
            <span class="char-counter__current">0</span><span class="unit">자</span>
            <span class="separator">/</span>
            <span>최대&nbsp;</span><span class="max-count">${maxLength}</span><span class="unit">자</span>
        </div>
    `;

	// textarea 부모 요소에 textarea-container 클래스 추가
	const parentDiv = textarea.closest('.input-group--textarea');
	if (parentDiv) {
		parentDiv.insertAdjacentHTML('beforeend', counterHTML);
	}

	// 글자수 카운터 요소들 선택
	const counter = parentDiv.querySelector('.char-counter');
	const currentCount = counter.querySelector('.char-counter__current');

	// 글자수 업데이트 함수
	function updateCounter() {
		const currentLength = textarea.value.length;
		currentCount.textContent = currentLength;
	}

	// 이벤트 리스너 등록
	textarea.addEventListener('input', updateCounter);
	textarea.addEventListener('paste', () => setTimeout(updateCounter, 10));
	updateCounter();
});

// 기업 검색 모달 관련 JavaScript
document.addEventListener('DOMContentLoaded', function() {
	const url = "/empt/ivfb/selectCompanyList.do";

	const modal            = document.querySelector('#search-modal');
	const searchBtn        = document.querySelector('#company-search');
	const closeBtn         = modal.querySelector('.search-modal__close-button');
	const cancelBtn        = modal.querySelector('#modal-cancel-btn');
	const confirmBtn       = modal.querySelector('#modal-confirm-btn');
	const searchInput      = modal.querySelector('#company-search-input');
	const searchButton     = modal.querySelector('#search-btn');
	const companyList      = modal.querySelector('#company-list');
	const prevPageBtn      = modal.querySelector('#prev-page');
	const nextPageBtn      = modal.querySelector('#next-page');
	const pageInfo         = modal.querySelector('#page-info');
	const companyNameInput = document.querySelector('#company-name');

	let currentPage = 1;
	let totalPages = 1;
	let selectedCompany = null;
	let companies = []; // 전체 기업 데이터
	const itemsPerPage = 5;

	// 모달 열기
	searchBtn.addEventListener('click', function() {
		modal.classList.add('is-active');
		searchInput.focus();
		loadCompanies('');
	});

	// 모달 닫기
	function closeModal() {
		modal.classList.remove('is-active');
		resetModal();
	}

	closeBtn.addEventListener('click', closeModal);
	cancelBtn.addEventListener('click', closeModal);

	// 모달 초기화
	function resetModal() {
		searchInput.value = '';
		selectedCompany = null;
		confirmBtn.disabled = true;
		currentPage = 1;
	}

	// 기업 검색
	const searchCompanies = async (keyword) => {
		try {
			const response = await fetch(url + "?cpName=" + keyword, {
				method: "GET",
				headers: {
					"Content-Type": "application/json",
				}
			});

			if (!response.ok) {
				throw new Error(`서버 응답 오류: ${response.status}`);
			}

			const result = await response.json();

			if (result.success && Array.isArray(result.companyList)) {
				return result.companyList;
			} else {
				console.error("API 응답에 문제가 있습니다.", result.message);
				return [];
			}
		} catch (error) {
			console.error("기업 정보를 불러오는 중 에러가 발생하였습니다.", error.message);
			return [];
		}
	}

	// 기업 목록 로드
	const loadCompanies = async (keyword) => {
		// 로딩 표시
		companyList.innerHTML = '<li class="loading-message">검색 중...</li>';

		companies = await searchCompanies(keyword);

		totalPages = Math.ceil(companies.length / itemsPerPage);
		if (totalPages === 0) totalPages = 1;

		currentPage = 1;
		renderCompanies();
		updatePagination();
	}

	// 기업 목록 렌더링
	function renderCompanies() {
		const startIndex = (currentPage - 1) * itemsPerPage;
		const endIndex = startIndex + itemsPerPage;
		const pageCompanies = companies.slice(startIndex, endIndex);

		if (pageCompanies.length === 0) {
			companyList.innerHTML = '<li class="empty-message">검색 결과가 없습니다.</li>';
			return;
		}

		companyList.innerHTML = pageCompanies.map(company => `
			<li class="search-modal__list-item" data-company-id="${company.cpId}" data-company-name="${company.cpName}">
			    <div class="search-modal__list-item-name">${company.cpName}</div>
			    <div class="search-modal__list-item-info">${company.cpScale} · ${company.cpRegion}</div>
			</li>
        `).join('');

		// 기업 선택 이벤트 추가
		document.querySelectorAll('.search-modal__list-item').forEach(item => {
			item.addEventListener('click', function() {
				document.querySelectorAll('.search-modal__list-item').forEach(i => i.classList.remove('is-selected'));
				this.classList.add('is-selected');
				
				selectedCompany = {
					cpId: this.dataset.companyId,
					cpName: this.dataset.companyName
				};
				confirmBtn.disabled = false;
			});
		});
	}

	// 페이징 업데이트
	function updatePagination() {
		pageInfo.textContent = `${currentPage} / ${totalPages}`;
		prevPageBtn.disabled = currentPage === 1;
		nextPageBtn.disabled = currentPage === totalPages || companies.length === 0;
	}

	// 검색 이벤트
	searchButton.addEventListener('click', function() {
		loadCompanies(searchInput.value.trim());
	});

	searchInput.addEventListener('keypress', function(e) {
		if (e.key === 'Enter') {
			loadCompanies(searchInput.value.trim());
		}
	});

	// 페이징 이벤트
	prevPageBtn.addEventListener('click', function() {
		if (currentPage > 1) {
			currentPage--;
			renderCompanies();
			updatePagination();
		}
	});

	nextPageBtn.addEventListener('click', function() {
		if (currentPage < totalPages) {
			currentPage++;
			renderCompanies();
			updatePagination();
		}
	});

	// 확인 버튼
	confirmBtn.addEventListener('click', function() {
		if (selectedCompany) {
			companyNameInput.value = selectedCompany.cpName;
			companyNameInput.dataset.cpId = selectedCompany.cpId;
			closeModal();
		}
	});

	// 모달 외부 클릭시 닫기
	modal.addEventListener('click', function(e) {
		if (e.target === modal) {
			closeModal();
		}
	});
});