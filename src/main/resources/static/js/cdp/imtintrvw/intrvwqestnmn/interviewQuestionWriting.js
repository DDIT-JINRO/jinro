// /js/cdp/imtintrvw/intrvwqestnmn/interviewQuestionWriting.js

function countChars(textarea, index) {
	const cnt = document.getElementById('charCount-' + index);
	if (cnt) cnt.textContent = textarea.value.length;
}

document.addEventListener('DOMContentLoaded', () => {
	// 초기 글자수 반영
	document.querySelectorAll('textarea[name="idAnswerList"]').forEach((ta, i) => {
		const cnt = document.getElementById('charCount-' + i);
		if (cnt) cnt.textContent = ta.value.length;
	});

	// 임시저장 → 상태만 "작성중"으로 바꾸고 submit
	const btnTemp = document.getElementById('btnTemp') || document.querySelector('.btn-temp-save');
	if (btnTemp) {
		btnTemp.addEventListener('click', function() {
			const status = document.getElementById('idlStatus');
			if (status) status.value = '작성중';
			this.closest('form').submit();
		});
	}

	// 삭제 (수정모드에서만 보임)
	const btnDelete = document.getElementById('btnDelete') || document.querySelector('.btn-delete');
	if (btnDelete) {
		btnDelete.addEventListener('click', function() {
			if (confirm('이 면접 질문 세트를 삭제할까요?')) {
				const delForm = document.getElementById('deleteForm');
				if (delForm) delForm.submit();
			}
		});
	}
});
document.addEventListener('DOMContentLoaded', () => {
	const form = document.querySelector('form');
	if (form) {
		form.addEventListener('submit', () => {
			document.querySelectorAll('button').forEach(b => b.disabled = true);
		});
	}
});
document.addEventListener('DOMContentLoaded', function() {
	const previewBtn = document.querySelector(".btn-preview");
	if (!previewBtn) return;

	previewBtn.addEventListener("click", () => {
		const originalForm = document.querySelector("form[action='/cdp/imtintrvw/intrvwqestnmn/save']");
		const clonedForm = originalForm.cloneNode(true);

		const originalInputs = originalForm.querySelectorAll("input, textarea");
		const clonedInputs = clonedForm.querySelectorAll("input, textarea");

		clonedInputs.forEach((clonedEl, i) => {
			const originalEl = originalInputs[i];
			if (clonedEl.tagName === "TEXTAREA") {
				clonedEl.innerHTML = originalEl.value;
			} else if (clonedEl.type === "checkbox" || clonedEl.type === "radio") {
				clonedEl.checked = originalEl.checked;
			} else {
				clonedEl.setAttribute("value", originalEl.value);
			}
		});

		clonedForm.querySelector(".section-title")?.remove();
		clonedForm.querySelector(".btn-group")?.remove();
		clonedForm.querySelectorAll(".char-count")?.forEach(e => e.remove());

		const xhtmlContent = sanitizeHtmlToXHTML(clonedForm.outerHTML);

		// [수정] selfIntroWriting.css 대신 interviewQuestionWriting.css를 가져옵니다.
		fetch("/css/cdp/imtintrvw/intrvwqestnmn/interviewQuestionWriting.css")
			.then(res => res.text())
			.then(cssContent => {
				const formData = new FormData();
				formData.append("htmlContent", xhtmlContent);
				formData.append("cssContent", cssContent);

				return fetch("/pdf/preview", { method: "POST", body: formData });
			})
			.then(response => {
				if (!response.ok) throw new Error("미리보기 요청 실패");
				return response.blob();
			})
			.then(blob => {
				const url = URL.createObjectURL(blob);
				const pdfUrlWithZoom = url + "#zoom=75";
				const windowFeatures = `width=900,height=700,left=${(screen.width - 900) / 2},top=${(screen.height - 700) / 2},scrollbars=yes,resizable=yes`;
				window.open(pdfUrlWithZoom, "pdfPreview", windowFeatures);
			})
			.catch(err => {
				console.error("미리보기 오류:", err);
				alert("PDF 미리보기 중 오류가 발생했습니다: " + err.message);
			});
	});

	//자동완성 버튼 이벤트 리스너 추가
	const autoCompleteBtn = document.getElementById('autoCompleteBtn');
	if (autoCompleteBtn) {
		autoCompleteBtn.addEventListener('click', autoCompleteHandler);
	}
});

function autoCompleteHandler() {
	//제목 입력 필드 자동완성
	const titleInput = document.querySelector('.section-title .title-input');
	if (titleInput && titleInput.value.trim() == '새 면접 질문' || titleInput.value.trim() == '') {
		titleInput.value = '나의 모의면접 질문 초안';
	}

	// 답변 입력 필드 자동완성
	const sampleAnswers = {
		'경비 또는 청소 업무 수행 시 가장 중요하게 생각하는 점은 무엇인가요?':
			`경비 및 청소 업무는 시설의 안전과 쾌적한 환경을 유지하는 데 필수적이라고 생각합니다. 따라서 저는 **'책임감과 세심함'**을 가장 중요하게 생각합니다. 맡은 구역에 대한 책임감을 가지고 작은 부분도 놓치지 않는 세심함으로 업무를 수행하겠습니다.`,
		'근무 중 긴급 상황을 침착하게 대응한 사례를 설명해주세요.':
			`이전 직장에서 야간 순찰 중 정전이 발생한 적이 있습니다. 당황하지 않고 매뉴얼에 따라 비상 발전기를 가동하고, 입주민들에게 상황을 안내하며 혼란을 최소화했습니다. 이 경험을 통해 어떤 돌발 상황에서도 침착하게 대응하는 능력을 길렀습니다.`,
		'업무 중 맡은 구역을 효율적으로 관리한 경험이 있다면 작성해주세요.':
			`이전 건물에서 경비 업무를 할 때, 순찰 시간을 최적화하기 위해 CCTV 사각지대와 주요 동선을 분석하여 순찰 경로를 재조정했습니다. 그 결과, 순찰 시간을 20% 단축하면서도 보안 수준은 더욱 강화할 수 있었습니다. 이처럼 데이터를 활용하여 효율적인 업무 방식을 찾는 데 강점이 있습니다.`
	};
	
	const qaBlocks = document.querySelectorAll('.qa-block');
	qaBlocks.forEach((block, index) =>{
		const questionTextElement = block.querySelector('.question-text');
		const answerTextarea = block.querySelector('textarea[name="idAnswerList"]');
		
		if(questionTextElement && answerTextarea) {
			const questionText = questionTextElement.textContent.trim();
			const answerContent = sampleAnswers[questionText];
			
			if(answerContent){
				answerTextarea.value = answerContent;

				countChars(answerTextarea, index);
			}
		}
	})
}

function sanitizeHtmlToXHTML(html) {
	return html
		.replace(/<input([^>]*?)(?<!\/)>/gi, '<input$1 />')
		.replace(/<br([^>]*?)(?<!\/)>/gi, '<br$1 />')
		.replace(/<hr([^>]*?)(?<!\/)>/gi, '<hr$1 />')
		.replace(/<img([^>]*?)(?<!\/)>/gi, '<img$1 />');
}