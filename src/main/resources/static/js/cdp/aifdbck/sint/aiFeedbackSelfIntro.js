/**
 * 
 */
	const selfIntroList = document.getElementById('selfIntroList');
	selfIntroList.addEventListener('change', loadSelfIntroDetail);

let originalQuestions = [];

function loadSelfIntroDetail() {

	const selectedSiId = document.getElementById('selfIntroList').value;
	//loadSelfIntroDetail 호출됨, selectedSiId: 1

	if (!selectedSiId) {
		//선택된 항목이 없으면 내용 초기화
		document.querySelector('.aifb-title').innerHTML = '자기소개서 제목';
		document.getElementById('questionsWrapper').innerHTML = '자기소개서 내용이 출력될 공간입니다';
		document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';

		return;
	}

	// AI 피드백 영역을 로딩 상태로 변경
	const feedbackArea = document.getElementById('feedbackArea');
	feedbackArea.innerHTML = `
		  <div class="d-flex justify-content-center align-items-center" style="height: 100px;">
		    <div class="spinner-border text-primary" role="status">
		      <span class="visually-hidden">AI가 피드백을 생성 중입니다...</span>
		    </div>
		  </div>
		`;

	fetch(`/cdp/aifdbck/sint/getSelfIntroDetail.do?siId=${selectedSiId}`)
		.then(response => {
			return response.json()
		})
		.then(data => {
			if (data) {
				//1. 자기소개서 제목 업데이트
				document.querySelector('.aifb-title').textContent = data.title;

				//2. 질문/답변 영역 업데이트
				let questionsHtml = '';
				data.questions.forEach((qvo, index) => {
					const cvo = data.contents[index];

					originalQuestions = data.questions.map(qvo => qvo.siqContent);

					questionsHtml += `
						<div class="qa-block">
							<div class="question-block">
								<span class="question-number">${index +1}.</span>
								<span class="question-text">${qvo.siqContent}</span>
							</div>
							<div class="answer-block">
								<p>${cvo.sicContent}</p>
								<div class="char-count">
					                글자 수: <span>${cvo.sicContent.length}</span> / 2000
					            </div>
							</div>
						</div>
					`
				})
				document.getElementById('questionsWrapper').innerHTML = questionsHtml;

				//3. ai 첨삭 요청 및 결과 출력
				const sections = data.questions.map((qvo, index) => {
					const cvo = data.contents[index];
					return {
						"question_title": qvo.siqContent,
						"original_content": cvo.sicContent
					};

				})

				return fetch('/ai/proofread/coverletter', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({
						"sections": sections
					})
				});
			}
		})
		.then(response => {
			if (!response.ok) throw new Error('AI 첨삭 요청 실패');
			return response.text();
		})
		.then(aiResponseText => {
			const feedbackArea = document.getElementById('feedbackArea');
			const sections = aiResponseText.trim().split('---').filter(script => script.trim() != '');
			// 안내문 형식의 첫 섹션을 제거하는 코드 추가
			if (sections.length > originalQuestions.length) {
				sections.shift();  // 첫 번째 안내 문장 제거
			}
			// 첫 문장이 의미 없는 경우 제거
			const filteredSections = sections.filter(section =>
				!section.includes('자기소개서 첨삭을 시작하겠습니다.') &&
				section.trim().length > 20
			);

			const trimmedSections = filteredSections.slice(0, originalQuestions.length);

			let html = '';
			trimmedSections.forEach((section, index) => {
				const questionTitle = originalQuestions[index] || `질문 ${index + 1}`;
				const cleandText = section.replace(new RegExp(`^\\s*${questionTitle}\\s*`, 'g'), '').trim();

				if (!cleandText || cleandText === '[]') return; // 빈 내용은 렌더링 제외

				html += `
			    <div class="feedback-block">
			      <div class="feedback-title question-block"><h4>${index + 1}. ${questionTitle}</h4></div>
			      <div class="feedback-content">${cleandText.trim().replace(/\n/g, '<br>')}</div>
			    </div>
			  `;
			});

			feedbackArea.innerHTML = html;

			// 높이 맞춤 (질문 영역과 피드백 블록 동일하게)
			/* 	setTimeout(() => {
					const questions = document.querySelectorAll('.qa-block');
					const feedbacks = document.querySelectorAll('.feedback-block');
					
					for (let i = 0; i < Math.max(questions.length, feedbacks.length); i++) {
						const q = questions[i];
						const f = feedbacks[i];
						if (!q || !f) continue;
						
						const qHeight = q.offsetHeight;
						const fHeight = f.offsetHeight;
						const maxHeight = Math.max(qHeight, fHeight);
						
						q.style.minHeight = maxHeight + 'px';
						f.style.minHeight = maxHeight + 'px';
					}
				}, 100); */


		})
		.catch(error => {
			console.error('Error fetching self-intro details:', error);
			alert('데이터를 불러오는 데 실패했습니다.');
		});
}

function requestProofread() {
	const selectedSiId = document.getElementById('selfIntroList').value;
	if (selectedSiId) {
		window.location.href = `/sint/sintwrt?siId=${selectedSiId}`;
	} else {
		alert('먼저 자기소개서를 선택해주세요.');
	}
}