document.addEventListener('DOMContentLoaded', function() {
	document.querySelector('.btn-temp-save').addEventListener('click', function() {
		document.getElementById('siStatus').value = '작성중';
		const form = document.querySelector('.selfintro-write-form form');
		form.submit();
	});

	document.querySelector(".btn-delete")?.addEventListener("click", () => {
		const form = document.querySelector('.selfintro-write-form form');
		if (confirm("정말 삭제하시겠습니까?")) {
			form.action = "/sint/sintwrt/delete.do";
			form.submit();
		}
	});



	document.querySelector(".btn-preview").addEventListener("click", () => {
		// 1. form 요소 클론
		const originalForm = document.querySelector("form");
		const clonedForm = originalForm.cloneNode(true); // 깊은 복사

		const originalInputs = originalForm.querySelectorAll("input, textarea");
		const clonedInputs = clonedForm.querySelectorAll("input, textarea");

		clonedInputs.forEach((clonedEl, i) => {
			const originalEl = originalInputs[i];
			if (clonedEl.tagName === "TEXTAREA") {
				clonedEl.innerHTML = originalEl.value; // textarea는 innerHTML에 입력된 내용 반영
			} else if (clonedEl.type === "checkbox" || clonedEl.type === "radio") {
				clonedEl.checked = originalEl.checked;
			} else {
				clonedEl.setAttribute("value", originalEl.value);
			}
		});


		const title = clonedForm.querySelector(".section-title");
		if (title) {
			title.remove();
		}

		// 2. 버튼 그룹 제거
		const btnGroup = clonedForm.querySelector(".btn-group");
		if (btnGroup) {
			btnGroup.remove();
		}

		//글자수 제거
		clonedForm.querySelectorAll(".char-count")?.forEach(e => e.remove());


		const xhtmlContent = sanitizeHtmlToXHTML(clonedForm.outerHTML);
		// 3. 스타일 가져오기 (예: /css/cdp/sint/sintwrt/selfIntroWriting.css)
		fetch("/css/cdp/sint/sintwrt/selfIntroWriting.css")
			.then(res => res.text())
			.then(cssContent => {
				// 4. FormData 구성
				const formData = new FormData();
				formData.append("htmlContent", xhtmlContent);  // HTML
				formData.append("cssContent", cssContent);              // CSS

				// 5. 미리보기 요청
				return fetch("/pdf/preview", {
					method: "POST",
					body: formData
				});
			})
			.then(response => {
				if (!response.ok) throw new Error("미리보기 요청 실패");
				return response.blob();
			})
			.then(blob => {
				const url = URL.createObjectURL(blob);
				const pdfUrlWithZoom = url + "#zoom=75";

				const windowWidth = 900;
				const windowHeight = 700;
				const left = (screen.width - windowWidth) / 2;
				const top = (screen.height - windowHeight) / 2;
				const windowFeatures = `width=${windowWidth},height=${windowHeight},left=${left},top=${top},scrollbars=yes,resizable=yes,toolbar=no,location=no,status=no`;

				const previewWindow = window.open(pdfUrlWithZoom, "pdfPreview", windowFeatures);
				if (!previewWindow) window.open(pdfUrlWithZoom, "_blank");
			})
			.catch(err => {
				console.error("미리보기 오류:", err);
				alert("PDF 미리보기 중 오류가 발생했습니다: " + err.message);
			});
	});

});

function sanitizeHtmlToXHTML(html) {
	return html
		.replace(/<input([^>]*?)(?<!\/)>/gi, '<input$1 />')
		.replace(/<br([^>]*?)(?<!\/)>/gi, '<br$1 />')
		.replace(/<hr([^>]*?)(?<!\/)>/gi, '<hr$1 />')
		.replace(/<img([^>]*?)(?<!\/)>/gi, '<img$1 />');
}

function countChars(textarea, index) {
	const length = textarea.value.length;
	const counter = document.getElementById("charCount-" + index);
	if (counter) {
		counter.textContent = length;
	}
}

// 초기 렌더링 시 기존 값에 대한 글자 수 세기
window.addEventListener("DOMContentLoaded", () => {
	const textareas = document.querySelectorAll("textarea[name='sicContentList']");
	textareas.forEach((ta, i) => {
		countChars(ta, i);
	});
});