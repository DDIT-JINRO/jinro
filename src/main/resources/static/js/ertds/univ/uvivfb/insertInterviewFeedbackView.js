document.addEventListener("DOMContentLoaded", function() {
	document.querySelector('#file-input').addEventListener('change', function(e) {
		const fileName = e.target.files[0]?.name || '';
		const fileNameDisplay = document.querySelector('.txt_filename b');
		const fileNameContainer = document.querySelector('.txt_filename');

		if (fileName) {
			fileNameDisplay.textContent = fileName;
			fileNameContainer.classList.add('show');
		} else {
			fileNameContainer.classList.remove('show');
		}
	});

	document.querySelector("#submit-btn").addEventListener("click", async function() {
		const univNameInput = document.querySelector("#university-name");

		const univId = univNameInput.dataset.univId;
		const interviewPosition = document.querySelector("#interview-position").value.trim();
		const interviewDate = document.querySelector("#interview-date").value.trim();
		const interviewDetail = document.querySelector("#interview-detail").value.trim();
		const interviewRating = window.getInterviewRating();
		const files = document.querySelector("#file-input").files;

		if (!interviewDate) {
			alert("면접 일자를 입력해 주세요.");
			return;
		}

		if (interviewRating === 0) {
			alert("대학 평가를 선택해 주세요.");
			return;
		}

		if (!interviewDetail) {
			alert("면접 후기를 입력해 주세요.");
			return;
		}

		if (files.length === 0) {
			alert("증빙자료를 첨부해 주세요.");
			return;
		}

		if (files.length > 1) {
			alert("증빙자료는 1장만 첨부해 주세요.");
			return;
		}

		// FormData 생성
		const formData = new FormData();
		formData.append('irType', 'G02001')
		formData.append('targetId', univId);
		formData.append('irContent', interviewDetail);
		formData.append('irRating', interviewRating);
		formData.append('irApplication', interviewPosition);
		formData.append('irInterviewAt', new Date(interviewDate));

		// 파일 추가
		formData.append('file', files[0]);

		try {
			const response = await fetch("/ertds/univ/uvivfb/insertInterViewFeedback.do", {
				method: "POST",
				body: formData
			});

			if (response.ok) {
				const result = await response.json();

				if (result.success) {
					alert("후기 등록 요청이 완료되었습니다");
					window.location.href = "/ertds/univ/uvivfb/selectInterviewList.do";
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
		window.location.href = "/ertds/univ/uvivfb/selectInterviewList.do";
	});
});

// 별점 평가 기능
document.addEventListener('DOMContentLoaded', function() {
	const starRating = document.getElementById('university-rating');
	const ratingText = document.getElementById('rating-text');
	const stars = starRating.querySelectorAll('.star');

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
	stars.forEach((star, index) => {
		// 클릭 이벤트
		star.addEventListener('click', function() {
			const rating = parseInt(this.dataset.value);
			setRating(rating);
		});

		// 마우스 호버 이벤트
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
			star.classList.remove('active', 'temp-active');

			if (index < rating) {
				if (isHover) {
					star.classList.add('temp-active');
				} else {
					star.classList.add('active');
				}
			}
		});
	}

	// 평가 텍스트 업데이트 함수
	function updateRatingText(rating, isHover) {
		ratingText.textContent = ratingTexts[rating];

		if (rating > 0) {
			ratingText.classList.add('selected');
		} else {
			ratingText.classList.remove('selected');
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
            <span class="current-count">0</span><span class="unit">자</span>
            <span class="separator">/</span>
            <span>최대&nbsp;</span><span class="max-count">${maxLength}</span><span class="unit">자</span>
        </div>
    `;

	// textarea 부모 요소에 textarea-container 클래스 추가
	const parentDiv = textarea.closest('.input-group');
	if (parentDiv) {
		parentDiv.classList.add('textarea-container');
		// 카운터를 textarea 다음에 추가
		parentDiv.insertAdjacentHTML('beforeend', counterHTML);
	}

	// 글자수 카운터 요소들 선택
	const counter = parentDiv.querySelector('.char-counter');
	const currentCount = counter.querySelector('.current-count');

	// 글자수 업데이트 함수
	function updateCounter() {
		const currentLength = textarea.value.length;
		currentCount.textContent = currentLength;

		// 글자수에 따른 스타일 변경
		counter.classList.remove('warning', 'error');
	}

	// 이벤트 리스너 등록
	textarea.addEventListener('input', updateCounter);
	textarea.addEventListener('paste', function() {
		// paste 이벤트는 약간의 지연 후 실행
		setTimeout(updateCounter, 10);
	});

	// 초기 글자수 설정
	updateCounter();
});

// 기업 검색 모달 관련 JavaScript
document.addEventListener('DOMContentLoaded', function() {
	const url = "/ertds/univ/uvivfb/selectUniversityList.do";

	const modal = document.querySelector('#modal-overlay');
	const searchBtn = document.querySelector('#university-search');
	const closeBtn = document.querySelector('.modal-close-btn');
	const cancelBtn = document.querySelector('#modal-cancel-btn');
	const confirmBtn = document.querySelector('#modal-confirm-btn');
	const searchInput = document.querySelector('#university-search-input');
	const searchButton = document.querySelector('#search-btn');
	const universityList = document.querySelector('#university-list');
	const prevPageBtn = document.querySelector('#prev-page');
	const nextPageBtn = document.querySelector('#next-page');
	const pageInfo = document.querySelector('#page-info');
	const universityNameInput = document.querySelector('#university-name');

	let currentPage = 1;
	let totalPages = 1;
	let selectedUniversity = null;
	let universities = []; // 전체 기업 데이터
	const itemsPerPage = 5;

	// 모달 열기
	searchBtn.addEventListener('click', function() {
		modal.classList.add('show');
		searchInput.focus();
		loadUniversities('');
	});

	// 모달 닫기
	function closeModal() {
		modal.classList.remove('show');
		resetModal();
	}

	closeBtn.addEventListener('click', closeModal);
	cancelBtn.addEventListener('click', closeModal);

	// 모달 초기화
	function resetModal() {
		searchInput.value = '';
		selectedUniversity = null;
		confirmBtn.disabled = true;
		currentPage = 1;
	}

	// 기업 검색
	const searchUniversities = async (keyword) => {
		try {
			const response = await fetch(url + "?univName=" + keyword, {
				method: "GET",
				headers: {
					"Content-Type": "application/json",
				}
			});

			if (!response.ok) {
				throw new Error(`서버 응답 오류: ${response.status}`);
			}

			const result = await response.json();

			if (result.success && Array.isArray(result.universityList)) {
				return result.universityList;
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
	const loadUniversities = async (keyword) => {
		// 로딩 표시
		universityList.innerHTML = '<li class="loading-message">검색 중...</li>';

		universities = await searchUniversities(keyword);

		totalPages = Math.ceil(universities.length / itemsPerPage);
		if (totalPages === 0) totalPages = 1;

		currentPage = 1;
		renderUniversities();
		updatePagination();
	}

	// 기업 목록 렌더링
	function renderUniversities() {
		const startIndex = (currentPage - 1) * itemsPerPage;
		const endIndex = startIndex + itemsPerPage;
		const pageUniversities = universities.slice(startIndex, endIndex);

		if (pageUniversities.length === 0) {
			universityList.innerHTML = '<li class="empty-message">검색 결과가 없습니다.</li>';
			return;
		}

		universityList.innerHTML = pageUniversities.map(university => `
            <li class="university-list-item" data-univ-id="${university.univId}" data-univ-name="${university.univName}">
                <div class="university-name">${university.univName}</div>
                <div class="university-info">${university.univCampus} · ${university.univRegion}</div>
            </li>
        `).join('');

		// 기업 선택 이벤트 추가
		document.querySelectorAll('.university-list-item').forEach(item => {
			item.addEventListener('click', function() {
				// 이전 선택 제거
				document.querySelectorAll('.university-list-item').forEach(i => i.classList.remove('selected'));

				// 현재 항목 선택
				this.classList.add('selected');

				selectedUniversity = {
					univId: this.dataset.univId,
					univName: this.dataset.univName
				};

				confirmBtn.disabled = false;
			});
		});
	}

	// 페이징 업데이트
	function updatePagination() {
		pageInfo.textContent = `${currentPage} / ${totalPages}`;
		prevPageBtn.disabled = currentPage === 1;
		nextPageBtn.disabled = currentPage === totalPages || universities.length === 0;
	}

	// 검색 이벤트
	searchButton.addEventListener('click', function() {
		loadUniversities(searchInput.value.trim());
	});

	searchInput.addEventListener('keypress', function(e) {
		if (e.key === 'Enter') {
			loadUniversities(searchInput.value.trim());
		}
	});

	// 페이징 이벤트
	prevPageBtn.addEventListener('click', function() {
		if (currentPage > 1) {
			currentPage--;
			renderUniversities();
			updatePagination();
		}
	});

	nextPageBtn.addEventListener('click', function() {
		if (currentPage < totalPages) {
			currentPage++;
			renderUniversities();
			updatePagination();
		}
	});

	// 확인 버튼
	confirmBtn.addEventListener('click', function() {
		if (selectedUniversity) {
			universityNameInput.value = selectedUniversity.univName;
			universityNameInput.dataset.univId = selectedUniversity.univId;


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

document.addEventListener('DOMContentLoaded', function() {
	//자동완성 기능 추가
	const autoCompleteBtn = document.getElementById('autoCompleteBtn');
	if (autoCompleteBtn) {
		autoCompleteBtn.addEventListener('click', autoCompleteHandler);
	}
})
// 자동완성 핸들러
function autoCompleteHandler() {
	// 1. '입학지원 대학 검색' 버튼 클릭 
	document.getElementById('university-search').click();

	// 2. 모달이 열리는 것을 기다린 후, 대학을 선택
	setTimeout(function() {
		//모달에서 국립한밭대학교
		const univName = '동원과학기술대학교';
		const univListItems = document.querySelectorAll('.university-list-item');
		let selectedItem = null;

		for (const item of univListItems) {
			if (item.querySelector('.university-name').textContent.trim() === univName) {
				item.click();
				selectedItem = item;
				break;
			}
		}

		if (selectedItem) {
			// '선택' 버튼을 클릭하여 메인 폼에 데이터 적용
			document.getElementById('modal-confirm-btn').click();
		} else {
			console.error('자동완성할 대학을 찾지 못했습니다.');
			alert('자동완성할 대학을 찾지 못했습니다. 목록을 다시 확인해주세요.');
			return;
		}

		setTimeout(async function() {
			//학과 채우기
			document.getElementById('interview-position').value = '컴퓨터공학과';

			//면접일자 채우기
			document.getElementById('interview-date').value = '2025-08-04';

			// 별점 자동 선택 (5점 만점)
			const ratingStars = document.querySelectorAll('#university-rating .star');
			if (ratingStars.length > 4) {
				ratingStars[4].click();
			}

			// 면접 후기 내용 자동 완성
			const reviewContent = `면접관님께서 편안한 분위기를 만들어주셔서 긴장하지 않고 답변할 수 있었습니다. \n특히 전공 관련 질문에 대해 심도 있는 대화를 나누며 제 지식을 어필할 수 있었고, \n대학의 교육 목표와 비전에 대해 자세히 들을 수 있어 매우 유익했습니다. \n면접 이후에도 좋은 인상을 받았습니다.`;
			document.getElementById('interview-detail').value = reviewContent;

			// 파일 첨부
			const fileInput = document.getElementById('file-input');
			const fileNameDisplay = document.querySelector('.txt_filename b');
			const fileNameContainer = document.querySelector('.txt_filename');

			//서버에 있는 더미 파일
			const fileUrl = '/images/main/charactor4.png';

			try {
				// 파일을 가져와서 Blob으로 변환
				const response = await fetch(fileUrl);
				const blob = await response.blob();

				//Blob으로 File 객체 생성
				const file = new File([blob], 'charactor4.png', { type: 'image/png' });

				// DataTransfer 객체를 사용하여 input[type="file"]에 파일 할당
				const dataTransfer = new DataTransfer();
				dataTransfer.items.add(file);
				fileInput.files = dataTransfer.files;

				// 파일 이름 미리보기 UI 업데이트
				fileNameDisplay.textContent = file.name;
				fileNameContainer.style.display = 'block';
			} catch (error) {
				console.error('파일 첨부 자동완성 실패:', error);
				alert('파일 첨부 중 오류가 발생했습니다. 개발자 도구 콘솔을 확인해주세요.');
			}
		}, 500); // 모달 열림 지연 시간
	}, 500); // 모달 열림 지연 시간
}