
document.addEventListener('DOMContentLoaded', function () {
	// 🔽 필터 관련 코드
	const toggleButton = document.getElementById('com-accordion-toggle');
	const panel = document.getElementById('com-accordion-panel');
	const filterCheckboxes = document.querySelectorAll('.com-filter-item input[type="checkbox"]');
	const selectedFiltersContainer = document.querySelector('.com-selected-filters');
	const resetButton = document.querySelector('.com-filter-reset-btn');

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

	const createFilterTag = (text) => {
		const filterTag = `<span class="com-selected-filter" data-filter="${text}">${text}<button type="button" class="com-remove-filter">×</button></span>`;
		selectedFiltersContainer.innerHTML += filterTag;
	};

	const removeFilterTag = (text) => {
		const tagToRemove = selectedFiltersContainer.querySelector(`[data-filter="${text}"]`);
		if (tagToRemove) {
			selectedFiltersContainer.removeChild(tagToRemove);
		}
	};

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

	selectedFiltersContainer.addEventListener('click', (e) => {
		if (e.target.classList.contains('com-remove-filter')) {
			const tag = e.target.closest('.com-selected-filter');
			const filterText = tag.dataset.filter;

			const checkboxToUncheck = Array.from(filterCheckboxes).find(
				cb => cb.nextElementSibling.textContent === filterText
			);
			if (checkboxToUncheck) {
				checkboxToUncheck.checked = false;
			}

			tag.remove();
		}
	});

	if (resetButton) {
		resetButton.addEventListener('click', () => {
			filterCheckboxes.forEach(checkbox => checkbox.checked = false);
			selectedFiltersContainer.innerHTML = '';
		});
	}

	// inq 모달창 (질문/답변 열고 닫기)
	document.querySelectorAll(".inq-question").forEach(button => {
		
			const currentMemId = document.getElementById("getMemId").value;
			button.addEventListener("click", () => {
				const parentItem = button.closest(".inq-item");
				const isPublic = parentItem.dataset.isPublic === 'Y'; // 문의의 공개 여부 ('Y' 또는 'N')
				const authorId = parentItem.dataset.authorId;       // 문의 작성자의 ID
				// 조건: 문의가 공개이거나 (AND 문의가 비공개이면서 현재 사용자가 작성자인 경우)
				const canOpen = isPublic || (!isPublic && currentMemId && currentMemId === authorId);

				if (canOpen) {
					// 열람이 허용되면, 기존 열기/닫기 로직 실행
					const content = parentItem.querySelector(".inq-content");
					const answer = parentItem.querySelector(".inq-answer");
					const arrow = button.querySelector(".arrow");
					const isOpen = content && content.classList.contains("active");

					// 다른 문의 모두 닫기
					document.querySelectorAll(".inq-content").forEach(c => c.classList.remove("active"));
					document.querySelectorAll(".inq-answer").forEach(a => a.classList.remove("active"));
					document.querySelectorAll(".arrow").forEach(a => a.classList.remove("rotate"));

					// 현재 문의 열기 (이미 열려있지 않다면)
					if (!isOpen) {
						if (content) content.classList.add("active");
						if (answer) answer.classList.add("active");
						if (arrow) arrow.classList.add("rotate");
					}
				} else {
					// 열람이 허용되지 않으면 경고 메시지 표시
					alert("비공개 문의는 본인만 열람할 수 있습니다.");
				}
			});
		});

	// 🔽 글 작성 버튼
	const writeBtn = document.getElementById('btnWrite');
	if (writeBtn) {
		writeBtn.addEventListener('click', function () {
			if (!memId || memId === 'anonymousUser') {
				sessionStorage.setItem("redirectUrl", location.href);
				location.href = "/login";
			} else {
				location.href = "/csc/inq/insertInq.do";
			}
		});
	}
});
