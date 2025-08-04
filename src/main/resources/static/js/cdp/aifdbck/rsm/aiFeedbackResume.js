/**
 * 이력서 AI 피드백 화면을 위한 자바스크립트
 */

let aiFeedbackData = null;
let originalData = null;

const resumeList = document.getElementById('resumeList');
resumeList.addEventListener('change', loadResumeDetail);

const requestAiFeedbackBtn = document.getElementById('requestAiFeedback');
if (requestAiFeedbackBtn) {
	requestAiFeedbackBtn.addEventListener('click', requestAiFeedback);
}

function cleanAiResponse(text) {
	let cleanedText = text.trim();
	if (cleanedText.startsWith('```json')) {
		cleanedText = cleanedText.substring('```json'.length);
	}

	if (cleanedText.endsWith('```')) {
		cleanedText = cleanedText.substring(0, cleanedText.length - '```'.length);
	}
	return cleanedText.trim();
}

function loadResumeDetail() {
	const selectedResumeId = resumeList.value;

	if (!selectedResumeId) {
		document.querySelector('.aifb-title').textContent = '이력서 제목';
		document.getElementById('questionsWrapper').innerHTML = '이력서의 내용이 출력될 공간입니다';
		document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';
		return;
	}


	fetch(`/cdp/aifdbck/rsm/getResumeDetail.do?resumeId=${selectedResumeId}`)
		.then(response => {
			if (!response.ok) throw new Error('이력서 상세 정보 요청 실패');
			return response.json();
		})
		.then(data => {
			if (data) {
				// 1️⃣ 이미지 경로 정리
				data.resumeContent = data.resumeContent.replace(/\\+/g, '/');
				originalData = data;

				document.querySelector('.aifb-title').textContent = data.resumeTitle
					;

				// HTML 내용을 파싱하여 불필요한 요소 제거
				const parser = new DOMParser();
				const doc = parser.parseFromString(data.resumeContent, 'text/html');

				// '필수 입력 정보입니다.' 문구 제거
				const requiredInfo = doc.querySelector('.required-info');
				if (requiredInfo) {
					requiredInfo.remove();
				}

				// 이력서의 HTML 내용을 innerHTML로 삽입
				document.getElementById('questionsWrapper').innerHTML = data.resumeContent;
				document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';
			}
		})
		.catch(error => {
			console.error('이력서 불러오기 오류:', error);
			alert('이력서 데이터를 불러오는 데 실패했습니다.');
			document.querySelector('.aifb-title').textContent = '오류 발생';
			document.getElementById('questionsWrapper').innerHTML = '데이터를 불러오는 데 실패했습니다.';
		});
}

function requestAiFeedback() {
	if (!originalData) {
		alert('먼저 이력서를 선택해주세요.');
		return;
	}

	const feedbackArea = document.getElementById('feedbackArea');
	feedbackArea.innerHTML = `
		<div class="spinner-wrapper">
			<div class="spinner-border text-primary" role="status">
				<span class="visually-hidden">Loading...</span>
			</div>
			<div class="text-center mt-2">AI가 피드백을 생성 중입니다...<br>잠시만 기다려주세요.</div>
		</div>
	`;

	const parser = new DOMParser();
	const doc = parser.parseFromString(originalData.resumeContent, 'text/html');

	// 이미지 제거
	doc.querySelectorAll('img').forEach(img => img.remove());

	// 제거된 HTML을 다시 HTML 문자열로 변환
	const cleanedHtml = doc.body.innerHTML;

	fetch('/ai/proofread/resume', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ "html": cleanedHtml })
	})
		.then(response => {
			if (!response.ok) throw new Error('AI 첨삭 요청 실패');
			return response.text();
		})
		.then(aiResponseText => {
			console.log("🔍 AI 응답 원문:", aiResponseText);
			const cleanedText = cleanAiResponse(aiResponseText);

			console.log("🧼 정리된 텍스트:", cleanedText);
			feedbackArea.innerHTML = cleanedText.replace(/\n/g, '<br>');
		})
		.catch(error => {
			console.error('AI 피드백 요청 오류:', error);
			feedbackArea.textContent = 'AI 피드백을 불러오는 데 실패했습니다.';
			alert('AI 피드백을 불러오는 데 실패했습니다.');
		});
}



function requestProofread() {
	const selectedResumeId = document.getElementById('resumeList').value;
	if (selectedResumeId) {
		window.location.href = `/cdp/rsm/rsm/resumeWriter.do?resumeId=${selectedResumeId}`;
	} else {
		alert('먼저 이력서를 선택해주세요.');
	}
}

//jsp 미리보기/다운로드
function generateHtmlFromFeedbackForResume(feedbackHtml) {
  return `
    <div class="pdf-feedback">
      <h1>AI 이력서 피드백</h1>
      <div class="feedback-content">
        ${feedbackHtml}
      </div>
    </div>
  `;
}

function getFeedbackPdfCssForResume() {
  return `
    .pdf-feedback {
      width: 100%;
      font-family: 'NanumGothic', sans-serif;
    }
    .pdf-feedback h1 {
      font-size: 24pt;
      text-align: center;
      margin-bottom: 30px;
    }
    .feedback-content {
      font-size: 12pt;
      line-height: 1.6;
      color: #333;
    }
  `;
}
//미리보기
function previewPdfFromAI() {
  const feedbackArea = document.getElementById("feedbackArea");
  if (!feedbackArea || feedbackArea.innerHTML.trim() === '' || feedbackArea.innerText.includes('출력될 공간')) {
    alert("AI 피드백 결과가 없습니다. 먼저 피드백을 요청하세요.");
    return;
  }

  const htmlContent = generateHtmlFromFeedbackForResume();
  const cssContent = getFeedbackPdfCssForResume();

  const formData = new FormData();
  formData.append("htmlContent", htmlContent);
  formData.append("cssContent", cssContent);

  fetch("/pdf/preview", {
    method: "POST",
    body: formData
  })
    .then(response => {
      if (!response.ok) throw new Error("미리보기 요청 실패");
      return response.blob();
    })
    .then(blob => {
      const url = window.URL.createObjectURL(blob);
      const pdfUrlWithZoom = url + "#zoom=75";
      const width = 900, height = 700;
      const left = (screen.width - width) / 2;
      const top = (screen.height - height) / 2;
      const windowFeatures = `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`;
      const previewWindow = window.open(pdfUrlWithZoom, "pdfPreview", windowFeatures);
      if (!previewWindow) window.open(pdfUrlWithZoom, "_blank");
    })
    .catch(err => {
      console.error("PDF 미리보기 오류:", err);
      alert("PDF 미리보기 실패: " + err.message);
    });
}

//다운로드
function downloadPdfFromAI() {
  const feedbackArea = document.getElementById("feedbackArea");
  if (!feedbackArea || feedbackArea.innerHTML.trim() === '' || feedbackArea.innerText.includes('출력될 공간')) {
    alert("AI 피드백 결과가 없습니다. 먼저 피드백을 요청하세요.");
    return;
  }

  const htmlContent = generateHtmlFromFeedbackForResume();
  const cssContent = getFeedbackPdfCssForResume();

  const form = document.createElement("form");
  form.method = "POST";
  form.action = "/pdf/download";
  form.target = "_blank";
  form.style.display = "none";

  const htmlInput = document.createElement("input");
  htmlInput.type = "hidden";
  htmlInput.name = "htmlContent";
  htmlInput.value = htmlContent;

  const cssInput = document.createElement("input");
  cssInput.type = "hidden";
  cssInput.name = "cssContent";
  cssInput.value = cssContent;

  form.appendChild(htmlInput);
  form.appendChild(cssInput);
  document.body.appendChild(form);
  form.submit();
  document.body.removeChild(form);
}
//이벤트 리스너
document.getElementById("previewPdfBtn")?.addEventListener("click", previewPdfFromAI);
document.getElementById("downloadPdfBtn")?.addEventListener("click", downloadPdfFromAI);

