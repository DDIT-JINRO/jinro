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
    btnTemp.addEventListener('click', function () {
      const status = document.getElementById('idlStatus');
      if (status) status.value = '작성중';
      this.closest('form').submit();
    });
  }

  // 삭제 (수정모드에서만 보임)
  const btnDelete = document.getElementById('btnDelete') || document.querySelector('.btn-delete');
  if (btnDelete) {
    btnDelete.addEventListener('click', function () {
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
    if(!previewBtn) return;

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
});

function sanitizeHtmlToXHTML(html) {
    return html
        .replace(/<input([^>]*?)(?<!\/)>/gi, '<input$1 />')
        .replace(/<br([^>]*?)(?<!\/)>/gi, '<br$1 />')
        .replace(/<hr([^>]*?)(?<!\/)>/gi, '<hr$1 />')
        .replace(/<img([^>]*?)(?<!\/)>/gi, '<img$1 />');
}