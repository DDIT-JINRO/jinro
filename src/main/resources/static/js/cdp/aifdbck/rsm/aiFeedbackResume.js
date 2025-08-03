/**
 * 이력서 AI 피드백 화면을 위한 자바스크립트
 */

let aiFeedbackData = null;
let originalSections = [];

const resumeList = document.getElementById('resumeList');
resumeList.addEventListener('change', loadResumeDetail);

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
		document.getElementById('questionsWrapper').innerHTML = '이력서 내용이 출력될 공간입니다';
		document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';
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

	let originalData = null;

	// 아직 구현되지 않았기 때문에 예시 주석
	fetch(`/cdp/aifdbck/rsm/getResumeDetail.do?resumeId=${selectedResumeId}`)
		.then(response => {
			if (!response.ok) throw new Error('이력서 상세 정보 요청 실패');
			return response.json();
		})
		.then(data => {
			if (data) {
				originalData = data;

				document.querySelector('.aifb-title').textContent = data.title || '이력서 제목';

				let questionsHtml = '';
				data.sections.forEach((section, index) => {
					questionsHtml += `
						<div class="qa-block" data-index="${index}" onclick="displayFeedback(${index}, this)">
							<div class="question-block">
								<span class="question-number">${index + 1}.</span>
								<span class="question-text">${section.sectionTitle}</span>
							</div>
							<div class="answer-block">
								<p>${section.sectionContent}</p>
								<div class="char-count">글자 수: ${section.sectionContent.length} / 2000</div>
							</div>
						</div>
					`;
				});
				document.getElementById('questionsWrapper').innerHTML = questionsHtml;

				// AI 첨삭용 데이터 구성
				const sections = data.sections.map(section => ({
					question_title: section.sectionTitle,
					original_content: section.sectionContent
				}));

				// AI 첨삭 요청
				return fetch('/ai/proofread/resume', {
					method: 'POST',
					headers: { 'Content-Type': 'application/json' },
					body: JSON.stringify({ sections })
				});
			}
		})
		.then(response => {
			if (!response.ok) throw new Error('AI 피드백 요청 실패');
			return response.text();
		})
		.then(aiResponseText => {
			const cleanedText = cleanAiResponse(aiResponseText);
			const sections = cleanedText.split('---').map(s => s.trim()).filter(Boolean);

			aiFeedbackData = {
				sections_feedback: sections,
				questions: originalData.sections.map(sec => sec.sectionTitle)
			};

			displayAllFeedback();
		})
		.catch(error => {
			console.error('이력서 데이터를 불러오는 중 오류 발생:', error);
			document.getElementById('feedbackArea').innerHTML = '데이터를 불러오는 데 실패했습니다.';
			alert('이력서 데이터를 불러오는 데 실패했습니다.');
		});
}

function displayAllFeedback() {
	if (!aiFeedbackData) return;
	const feedbackArea = document.getElementById('feedbackArea');

	let feedbackHtml = '';
	aiFeedbackData.sections_feedback.forEach((feedbackText, index) => {
		const questionTitle = aiFeedbackData.questions?.[index] || `항목 ${index + 1}`;
		feedbackHtml += `
			<div class="feedback-section" id="feedback-section-${index}">
				<h4>${index + 1}. ${questionTitle}</h4>
				<p>${feedbackText.replace(/\[문항 \d+번 - AI 피드백\]/, '').replace(/\n/g, '<br>')}</p>
			</div>
		`;
	});

	feedbackArea.innerHTML = feedbackHtml;
	feedbackArea.scrollTop = 0;
}

function displayFeedback(index, clickedElement) {
	document.querySelectorAll('.qa-block').forEach(el => el.classList.remove('active'));
	clickedElement.classList.add('active');

	const feedbackSection = document.getElementById(`feedback-section-${index}`);
	if (feedbackSection) {
		const feedbackArea = document.getElementById('feedbackArea');
		const offset = feedbackSection.offsetTop - feedbackArea.offsetTop;
		feedbackArea.scrollTop = offset;
	}
}

function requestProofread() {
	const selectedResumeId = document.getElementById('resumeList').value;
	if (selectedResumeId) {
		// 아직 미구현이므로 주석
		// window.location.href = `/rsm/rsmwrt?resumeId=${selectedResumeId}`;
		alert('이력서 수정 기능은 아직 구현되지 않았습니다.');
	} else {
		alert('먼저 이력서를 선택해주세요.');
	}
}
