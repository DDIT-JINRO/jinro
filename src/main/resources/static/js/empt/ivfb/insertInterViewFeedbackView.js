document.addEventListener("DOMContentLoaded", function() {

	document.getElementById("submitBtn").addEventListener("click", async function() {
		const title = document.getElementById("title").value.trim();
		const content = editorInstance.getData();

		if (!title || !content) {
			alert("제목과 내용을 모두 입력해 주세요.");
			return;
		}

		const formData = new FormData();
		formData.append('title', title);
		formData.append('content', content);

		const files = fileInput.files;
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}


		try {
			const response = await axios.post("/empt/ivfb/insertInterViewFeedbackView.do", formData, {
				headers: {
					"Content-Type": "multipart/form-data"
				}
			});
			if (response.status === 200) {
				alert("등록 성공");
				window.location.href = "/empt/ivfb/interviewFeedback.do";
			}
		} catch (error) {
			console.error("등록 중 오류:", error);
			alert("등록에 실패했습니다.");
		}
	});

	document.querySelector("#back-btn").addEventListener("click", function() {
		window.location.href = "/empt/ivfb/interviewFeedback.do";
	});
});

// textarea 글자수 카운터 기능
document.addEventListener('DOMContentLoaded', function() {
    const textarea = document.getElementById('interview-detail');
    const maxLength = 100; // 최대 글자수 설정
    
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
	const url = "/empt/ivfb/selectCompanyList.do";
	
	const modal            = document.querySelector('#modal-overlay');
	const searchBtn        = document.querySelector('#company-search');
	const closeBtn         = document.querySelector('.modal-close-btn');
	const cancelBtn        = document.querySelector('#modal-cancel-btn');
	const confirmBtn       = document.querySelector('#modal-confirm-btn');
	const searchInput      = document.querySelector('#company-search-input');
	const searchButton     = document.querySelector('#search-btn');
	const companyList      = document.querySelector('#company-list');
	const prevPageBtn      = document.querySelector('#prev-page');
	const nextPageBtn      = document.querySelector('#next-page');
	const pageInfo         = document.querySelector('#page-info');
	const companyNameInput = document.querySelector('#companyName');

	let currentPage = 1;
	let totalPages = 1;
	let selectedCompany = null;
	let companies = []; // 전체 기업 데이터
	const itemsPerPage = 5;

	// 모달 열기
	searchBtn.addEventListener('click', function() {
		modal.classList.add('show');
		searchInput.focus();
		loadCompanies('');
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
            <li class="company-list-item" data-company-id="${company.cpId}" data-company-name="${company.cpName}">
                <div class="company-name">${company.cpName}</div>
                <div class="company-info">${company.cpScale} · ${company.cpRegion}</div>
            </li>
        `).join('');

		// 기업 선택 이벤트 추가
		document.querySelectorAll('.company-list-item').forEach(item => {
			item.addEventListener('click', function() {
				// 이전 선택 제거
				document.querySelectorAll('.company-list-item').forEach(i => i.classList.remove('selected'));

				// 현재 항목 선택
				this.classList.add('selected');
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