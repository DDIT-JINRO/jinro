// 1) 페이지 로드 시 sessionStorage에서 복원
let selectedQuestions = [];

document.addEventListener('DOMContentLoaded', () => {
	const currentRaw = window.currentMemId || '';   // JSP에서 넘어온 문자열
	// Spring Security 익명 사용자는 "anonymousUser"로 찍힐 수 있으므로
	const currentMemId = (currentRaw === 'anonymousUser') ? '' : currentRaw;
	let storedRaw = sessionStorage.getItem('memId');
	const storedMemId = (storedRaw === 'anonymousUser') ? '' : (storedRaw || null);

	// 1) 첫 방문: storedMemId === null
	if (storedMemId === null) {
		sessionStorage.setItem('memId', currentMemId);
	}
	// 2) 로그인 → 로그아웃: storedMemId non-empty && currentMemId empty
	else if (storedMemId !== '' && currentMemId === '') {
		sessionStorage.clear();
		sessionStorage.setItem('memId', '');
	}
	// 3) 익명 → 로그인: storedMemId empty && currentMemId non-empty
	else if (storedMemId === '' && currentMemId !== '') {
		sessionStorage.setItem('memId', currentMemId);
	}
	// 4) 그 외 (익명→익명, 로그인→동일 사용자) → 아무 동작 없음

	// --- 이제 선택 질문 복원 로직 실행 ---
	const saved = sessionStorage.getItem('selectedQuestions');
	if (saved) {
		selectedQuestions = JSON.parse(saved);
		selectedQuestions.forEach(q => {
			const chk = document.querySelector(
				`input[type="checkbox"][data-id="${q.id}"]`
			);
			if (chk) chk.checked = true;
		});
	}
	updateCartSidebar();
	updateQuestionIdsInput();

	const filterCheckboxes = document.querySelectorAll('.filter-checkbox');
	const selectedFiltersContainer = document.getElementById('selected-filters');


	// 체크박스를 클릭할 때마다 필터 조건을 업데이트
	filterCheckboxes.forEach(function(checkbox) {
		checkbox.addEventListener('change', function() {
			const filterName = this.getAttribute('data-name');
			const filterId = this.getAttribute('data-id');

			if (this.checked) {
				addFilterToConditions(filterName, filterId);
			} else {
				removeFilter(filterId);
			}
		});

	});

	document.querySelectorAll('.filter-checkbox').forEach(cb => {
		const parent = cb.closest('.filter-item');
		// 초기 체크 상태 반영 (수정 검색 등)
		if (cb.checked) {
			parent.classList.add('checked');
		}

		cb.addEventListener('change', () => {
			if (cb.checked) {
				parent.classList.add('checked');
			} else {
				parent.classList.remove('checked');
			}
		});
	});


	// 필터를 선택했을 때, 필터 조건에 추가
	function addFilterToConditions(name, id) {
		const filterItem = document.createElement('span');
		filterItem.classList.add('selected-filter');
		filterItem.setAttribute('data-id', id);
		filterItem.innerHTML = `${name} <span class="remove-filter" onclick="removeFilter('${id}')">×</span>`;
		selectedFiltersContainer.appendChild(filterItem);
	}

	// 필터를 제거할 때, 필터 조건에서 삭제
	window.removeFilter = function(id) {
		const filterItem = document.querySelector(`.selected-filter[data-id="${id}"]`);
		if (filterItem) {
			filterItem.remove();

			const checkbox = document.querySelector(`.filter-checkbox[data-id="${id}"]`);
			if (checkbox) {
				checkbox.checked = false; // 체크박스 해제

				// ✅ 상단 필터에서 .checked 클래스 제거
				const parent = checkbox.closest('.filter-item');
				if (parent) {
					parent.classList.remove('checked');
				}
			}
		}
	}
	
	
	const submitCart = document.querySelector(".submitCartForm");
	
	if(!memId || memId =='anonymousUser'){
		if (submitCart) { // 버튼이 존재하는지 확인 (에러 방지)
		    submitCart.textContent = '로그인 하러 가기';
			submitCart.addEventListener('click', function(){
				window.location.href = '/login';
			});
		  }
	}else
	
	// 6) 폼 제출
	submitCart.addEventListener("click",function(){
		
		if (selectedQuestions.length === 0) {
			alert('자기소개서 작성을 위해 질문을 선택해주세요.');
			return;
		}
		// 필요하다면 여기서 sessionStorage.removeItem('selectedQuestions');
		document.getElementById('cartForm').submit();
		sessionStorage.clear();
	}) 
});

// 2) 선택 토글 함수
function toggleQuestion(checkbox, id, content) {
	const existing = selectedQuestions.find(q => q.id === id);
	if (checkbox.checked) {
		if (!existing) selectedQuestions.push({ id, content });
	} else {
		selectedQuestions = selectedQuestions.filter(q => q.id !== id);
	}
	updateCartSidebar();
	updateQuestionIdsInput();
	// 변경된 목록 저장
	sessionStorage.setItem(
		'selectedQuestions',
		JSON.stringify(selectedQuestions)
	);
}

// 3) 사이드바 렌더링
function updateCartSidebar() {
	const cartSidebar = document.getElementById('cartSidebar');
	cartSidebar.innerHTML = '';

	if (selectedQuestions.length === 0) {
		cartSidebar.innerHTML =
			'<div class="empty-cart-message">선택된 질문이 없습니다.</div>';
		return;
	}

	selectedQuestions.forEach(q => {
		const item = document.createElement('div');
		item.className = 'question-panel-item';
		item.setAttribute('data-id', q.id);

		const contentDiv = document.createElement('div');
		contentDiv.className = 'question-panel-content';
		contentDiv.textContent = q.content;

		const btn = document.createElement('button');
		btn.type = 'button';
		btn.className = 'remove-question-btn';
		btn.innerHTML = '&times;';
		btn.addEventListener('click', () => removeQuestionFromCart(q.id));

		item.append(contentDiv, btn);
		cartSidebar.appendChild(item);
	});
}

// 4) × 버튼 클릭 시 제거
function removeQuestionFromCart(id) {
	selectedQuestions = selectedQuestions.filter(q => q.id !== id);
	const chk = document.querySelector(
		`input[type="checkbox"][data-id="${id}"]`
	);
	if (chk) chk.checked = false;
	updateCartSidebar();
	updateQuestionIdsInput();
	sessionStorage.setItem(
		'selectedQuestions',
		JSON.stringify(selectedQuestions)
	);
}

// 5) 숨겨진 input 갱신
function updateQuestionIdsInput() {
	document.getElementById('questionIds').value =
		selectedQuestions.map(q => q.id).join(',');
}



