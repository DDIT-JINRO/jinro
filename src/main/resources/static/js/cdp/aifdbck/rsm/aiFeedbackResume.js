/**
 * 이력서 AI 피드백 화면을 위한 자바스크립트
 */

let aiFeedbackData = null;
let originalData = null;

const resumeList = document.getElementById('resumeList');
resumeList.addEventListener('change', loadResumeDetail);

const requestAiFeedbackBtn = document.getElementById('requestAiFeedback');
requestAiFeedbackBtn.addEventListener('click', requestAiFeedback);

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


	// 아직 구현되지 않았기 때문에 예시 주석
	fetch(`/cdp/aifdbck/rsm/getResumeDetail.do?resumeId=${selectedResumeId}`)
		.then(response => {
			if (!response.ok) throw new Error('이력서 상세 정보 요청 실패');
			return response.json();
		})
		.then(data => {
					if (data) {
						originalData = data;

						document.querySelector('.aifb-title').textContent = data.title;

						// 질문-답변 영역을 렌더링합니다.
						let questionsHtml = '';
						data.questions.forEach((qvo, index) => {
							const cvo = originalData.contents[index];
							questionsHtml += `
		                    <div class="qa-block" data-index="${index}" onclick="displayFeedback(${index}, this)">
		                        <div class="question-block">
		                            <span class="question-number">${index + 1}.</span>
		                            <span class="question-text">${qvo.siqContent}</span>
		                        </div>
		                        <div class="answer-block">
		                            <p>${cvo.sicContent}</p>
		                            <div class="char-count">
		                                글자 수: <span>${cvo.sicContent.length}</span> / 2000
		                            </div>
		                        </div>
		                    </div>
		                `;
						});
						document.getElementById('questionsWrapper').innerHTML = questionsHtml;

						//피드백 영역 초기화
						document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';
					}
				})
				.catch(error => {
					console.error('자기소개서 불러오기 오류:', error);
					alert('자기소개서 데이터를 불러오는 데 실패했습니다.');
				});
		}

		function requestAiFeedback() {
			if (!originalData) {
				alert('먼저 자기소개서를 선택해주세요.');
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

			// AI 첨삭을 위한 페이로드를 준비
			const sections = originalData.questions.map((qvo, index) => {
				const cvo = originalData.contents[index];
				return {
					"question_title": qvo.siqContent,
					"original_content": cvo.sicContent,
				};
			});

			// AI 첨삭 요청 (단일 요청으로 변경)
			fetch('/ai/proofread/coverletter', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({ "sections": sections })
			})
				.then(response => {
					if (!response.ok) throw new Error('AI 첨삭 요청 실패');
					return response.text();
				})
				.then(aiResponseText => {
					// AI 응답 텍스트를 정리하고 파싱
					const cleanedText = cleanAiResponse(aiResponseText);

					// ---로 구분된 피드백을 파싱
					const sections = cleanedText.split('---').map(section => section.trim()).filter(section => section.length > 0);

					// aiFeedbackData에 저장
					aiFeedbackData = {
						sections_feedback: sections,
						questions: originalData.questions.map(qvo => qvo.siqContent)
					};

					displayAllFeedback();
				})
				.catch(error => {
					console.error('Error fetching self-intro details:', error);
					const feedbackArea = document.getElementById('feedbackArea');
					feedbackArea.textContent = '데이터를 불러오는 데 실패했습니다.';
					alert('데이터를 불러오는 데 실패했습니다.');
				});
		}


		function displayAllFeedback() {
			if (!aiFeedbackData) return;
			const feedbackArea = document.getElementById('feedbackArea');

			let feedbackHtml = '';

			// 섹션별 피드백 출력 (핵심 내용만 간결하게)
			if (aiFeedbackData.sections_feedback && aiFeedbackData.sections_feedback.length > 0) {
				aiFeedbackData.sections_feedback.forEach((feedbackText, index) => {
					feedbackHtml += `<div class="feedback-section" id="feedback-section-${index}">`;

					// 질문을 제목으로 출력
					const questionTitle = aiFeedbackData.questions?.[index] || `문항 ${index + 1}`;
					feedbackHtml += `<h4>${index + 1}. ${questionTitle}</h4>`;

					// "[문항 N번 - AI 피드백]" 제거 후 줄바꿈 처리
					const cleanedText = feedbackText.replace(/\[문항 \d+번 - AI 피드백\]/, '').trim();
					feedbackHtml += `<p>${cleanedText.replace(/\n/g, '<br>')}</p>`;

					feedbackHtml += `</div>`;
				});
			} else {
				feedbackHtml += '<p>제공된 피드백이 없습니다.</p>';
			}

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
