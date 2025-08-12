// /js/cdp/imtintrvw/intrvwqestnlst/intrvwQuestionList.js

let selectedQuestions = [];

// 1) 페이지 로드
document.addEventListener('DOMContentLoaded', () => {
	const memId = (window.currentMemId === 'anonymousUser') ? '' : (window.currentMemId || '');
	const isLoggedIn = !!memId;

	// 선택 복원
	const saved = sessionStorage.getItem('selectedQuestions');
	if (saved) {
		selectedQuestions = JSON.parse(saved);
		selectedQuestions.forEach(q => {
			const chk = document.querySelector(`input[type="checkbox"][data-id="${q.id}"]`);
			if (chk) chk.checked = true;
		});
	}
	updateCartSidebar();
	updateQuestionIdsInput();

	// URL의 필터 복원
	restoreFiltersFromUrl();

	// “면접 질문 작성” 버튼
	const cartForm = document.getElementById('cartForm');
	if (cartForm) {
		cartForm.addEventListener('submit', (event) => {
			// form의 기본 제출 동작을 일단 막습니다. (유효성 검사를 위해)
			event.preventDefault();

			if (!isLoggedIn) {
				alert('로그인이 필요합니다.');
				location.href = '/login';
				return;
			}

			if (selectedQuestions.length === 0) {
				alert('면접 질문을 하나 이상 선택해 주세요.');
				return; // 여기서 return 되면 폼 제출이 중단됨
			}

			// 모든 검증이 통과되면, form의 숨겨진 필드에 값을 채우고 최종적으로 제출합니다.
			document.getElementById('questionIds').value = selectedQuestions.map(q => q.id).join(',');
			sessionStorage.removeItem('selectedQuestions');
			cartForm.submit();
		});
	}

	// 필터 체크박스에 실시간 반응 이벤트 추가
	const selectedFiltersContainer = document.getElementById('selected-filters');
	document.querySelectorAll('.filter-checkbox').forEach(cb => {
		cb.addEventListener('change', function() {
			const filterId = this.dataset.id;
			const filterName = this.dataset.name;
			const parentLabel = this.closest('.filter-item');

			if (this.checked) {
				// 체크 시: 선택된 필터 태그 추가
				const tag = document.createElement('span');
				tag.className = 'selected-filter';
				tag.dataset.id = filterId;
				tag.innerHTML = `${filterName} <span class="remove-filter">×</span>`;

				// 태그의 X 버튼 클릭 시 필터 제거
				tag.querySelector('.remove-filter').addEventListener('click', () => {
					cb.checked = false;
					tag.remove();
					if (parentLabel) parentLabel.classList.remove('checked');
				});

				selectedFiltersContainer.appendChild(tag);
				if (parentLabel) parentLabel.classList.add('checked');
			} else {
				// 체크 해제 시: 선택된 필터 태그 제거
				const tagToRemove = selectedFiltersContainer.querySelector(`.selected-filter[data-id="${filterId}"]`);
				if (tagToRemove) {
					tagToRemove.remove();
				}
				if (parentLabel) parentLabel.classList.remove('checked');
			}
		});
	});
});

// 2) 체크박스 토글
function toggleQuestion(checkbox, id, content) {
	id = String(id);
	const exists = selectedQuestions.find(q => q.id === id);
	if (checkbox.checked) {
		if (!exists) selectedQuestions.push({ id, content });
	} else {
		selectedQuestions = selectedQuestions.filter(q => q.id !== id);
	}
	updateCartSidebar();
	updateQuestionIdsInput();
	sessionStorage.setItem('selectedQuestions', JSON.stringify(selectedQuestions));
}

// 3) 사이드바 렌더
function updateCartSidebar() {
	const wrap = document.getElementById('cartSidebar');
	if (!wrap) return;
	wrap.innerHTML = '';

	if (selectedQuestions.length === 0) {
		wrap.innerHTML = '<div class="empty-cart-message">선택된 질문이 없습니다.</div>';
		return;
	}

	selectedQuestions.forEach(q => {
		const item = document.createElement('div');
		item.className = 'question-panel-item';
		item.dataset.id = q.id;

		const txt = document.createElement('div');
		txt.className = 'question-panel-content';
		txt.textContent = q.content;

		const btn = document.createElement('button');
		btn.type = 'button';
		btn.className = 'remove-question-btn';
		btn.innerHTML = '&times;';
		btn.addEventListener('click', () => removeQuestionFromCart(q.id));

		item.append(txt, btn);
		wrap.appendChild(item);
	});
}

// 4) 카트에서 제거
function removeQuestionFromCart(id) {
	id = String(id);
	selectedQuestions = selectedQuestions.filter(q => q.id !== id);
	const chk = document.querySelector(`input[type="checkbox"][data-id="${id}"]`);
	if (chk) chk.checked = false;

	updateCartSidebar();
	updateQuestionIdsInput();
	sessionStorage.setItem('selectedQuestions', JSON.stringify(selectedQuestions));
}

// 5) 히든 input 갱신
function updateQuestionIdsInput() {
	const hidden = document.getElementById('questionIds');
	if (!hidden) return;
	hidden.value = selectedQuestions.map(q => q.id).join(',');
}

// 6) URL 파라미터에서 필터 복원(선택된 필터 떠주기)
function restoreFiltersFromUrl() {
	const urlParams = new URLSearchParams(window.location.search);
	const selected = urlParams.getAll('siqJobFilter') || [];
	const selectedFiltersContainer = document.getElementById('selected-filters');

	document.querySelectorAll('.filter-checkbox').forEach(cb => {
		if (selected.includes(cb.value)) {
			cb.checked = true;
			const tag = document.createElement('span');
			tag.className = 'selected-filter';
			tag.dataset.id = cb.dataset.id;
			tag.innerHTML = `${cb.dataset.name} <span class="remove-filter">×</span>`;
			selectedFiltersContainer.appendChild(tag);

			const parent = cb.closest('.filter-item');
			if (parent) parent.classList.add('checked');

			tag.querySelector('.remove-filter').addEventListener('click', () => {
				cb.checked = false;
				const p = cb.closest('.filter-item');
				if (p) p.classList.remove('checked');
				tag.remove();
			});
		}
	});
}
