<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet"
	href="/css/cdp/aifdbck/sint/aiFeedbackSelfIntro.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">경력관리</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/rsm/rsm">이력서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/sint/qestnlst">자기소개서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/imtintrvw/bsintrvw">모의면접</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">이력서</a>
			<a class="tab active"
				href="/cdp/aifdbck/sint/aiFeedbackSelfIntroList.do">자기소개서</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<!-- 자기소개서 목록 드롭다운 -->
			<select class="self-intro-select" id="selfIntroList">
				<option value="">내가 작성한 자기소개서 리스트를 선택하세요.</option>
				<c:forEach var="intro" items="${selfIntroList}">
					<option value="${intro.siId}">${intro.siTitle}</option>
				</c:forEach>
			</select> <br /> <br />
			<div class="aifb-container">
				<!-- 자기소개서 원본 입력 영역 -->
				<h1 class="aifb-title">자기소개서 제목</h1>
				<div class="aifb-content-wrapper">
					<div class="aifb-section">
						<div class="aifb-section-header">
							<h2>자기소개서</h2>
						</div>
						<div class="aifb-questions-wrapper" id="questionsWrapper">
							AI의 피드백 내용이 출력될 공간입니다</div>
					</div>

					<!-- AI 피드백 출력 영역 -->
					<div class="aifb-feedback-wrapper">
						<div class="aifb-feedback-header">
							<h2>AI 피드백</h2>
						</div>
						<div class="aifb-feedback-area" id="feedbackArea">AI의 피드백
							내용이 출력될 공간입니다</div>
					</div>
				</div>

				<div class="aifb-button-wrapper">
					<button class="aifb-button back" onclick="history.back()">뒤로가기</button>
					<button class="aifb-button proofread" onclick="requestProofread()">내
						자기소개서 수정하러 가기</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
	document.addEventListener('DOMContentLoaded', function(){
			const selfIntroList = document.getElementById('selfIntroList');
			selfIntroList.addEventListener('change', loadSelfIntroDetail);
		})
		
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
	
	function loadSelfIntroDetail(){
		const selectedSiId = document.getElementById('selfIntroList').value;
		//loadSelfIntroDetail 호출됨, selectedSiId: 1
		
		if(!selectedSiId){
			//선택된 항목이 없으면 내용 초기화
			document.querySelector('.aifb-title').innerHTML = '자기소개서 제목';
			document.getElementById('questionsWrapper').innerHTML = '자기소개서 내용이 출력될 공간입니다';
			document.getElementById('feedbackArea').innerHTML = 'AI의 피드백 내용이 출력될 공간입니다';
			
			return;
		}

	    // AI 피드백 영역을 로딩 상태로 변경
	    const feedbackArea = document.getElementById('feedbackArea');
	    feedbackArea.textContent = 'AI가 피드백을 생성 중입니다... 잠시만 기다려주세요.';
	    
		fetch(`/cdp/aifdbck/sint/getSelfIntroDetail.do?siId=\${selectedSiId}`)
		.then(response => {
			return response.json()
		})
		.then(data => {
			if(data) {
				//1. 자기소개서 제목 업데이트
				document.querySelector('.aifb-title').textContent = data.title;
				
				//2. 질문/답변 영역 업데이트
				let questionsHtml = '';
				data.questions.forEach((qvo, index) =>{
					const cvo = data.contents[index]; 
					
					questionsHtml += `
						<div class="qa-block">
							<div class="question-block">
								<span class="question-number">\${index +1}.</span>
								<span class="question-text">\${qvo.siqContent}</span>
							</div>
							<div class="answer-block">
								<p>\${cvo.sicContent}</p>
								<div class="char-count">
					                글자 수: <span>\${cvo.sicContent.length}</span> / 2000
					            </div>
							</div>
						</div>
					`					
				})
				document.getElementById('questionsWrapper').innerHTML = questionsHtml;
				
				//3. ai 첨삭 요청 및 결과 출력
				const sections = data.questions.map((qvo, index) =>{
					const cvo = data.contents[index]; 
					return {
						"question_title" : qvo.siqContent,
						"original_content" : cvo.sicContent
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
		.then(response =>{
			if (!response.ok) throw new Error('AI 첨삭 요청 실패');
			return response.text();
		})
		.then(aiResponseText =>{
		    // JSON 문자열을 정리하는 함수 호출
		    const cleanedText = cleanAiResponse(aiResponseText);
		    
			// JSON 문자열을 JavaScript 객체로 변환
			const aiResponse = JSON.parse(cleanedText);
			
			//ai 피드백 내용을 화면에 표시
			const feddbackArea = document.getElementById('feedbackArea');
			let feedbackHtml = '';
			
			//1. 전체 요약 (overall_summary)
			feedbackHtml += '<h3>전체 요약</h3>';
			feedbackHtml += '<p><strong>강점</strong> ' + aiResponse.overall_summary.strengths.join(', ') + '</p>' 
			feedbackHtml += '<p><strong>개선점</strong> ' + aiResponse.overall_summary.areas_for_improvement.join(', ') + '</p>';
			feedbackHtml += '<p><strong>최종 조언</strong> ' + aiResponse.overall_summary.final_advice + '</p>';
			
			//2. 각 색션별 피드백 (sections_feedback) 출력
			feedbackHtml += '<h3>섹션별 피드백</h3>';
			if(aiResponse.sections_feedback && aiResponse.sections_feedback.length >0){
				aiResponse.sections_feedback.forEach((section, index) =>{
					feedbackHtml += `<h4>\${index + 1}. \${section.question_title}</h4>`;
		            feedbackHtml += `<p><strong>원문:</strong> \${section.original_content}</p>`;
		            feedbackHtml += `<p><strong>섹션 팁:</strong> \${section.overall_section_tip}</p>`;
					
		            //3. 각 피드백 항목 (feedback_items) 출력
		            if(section.feedback_items && section.feedback_items.length >0){
		            	feedbackHtml += '<h5>상세 피드백</h5>';
		            	section.feedback_items.forEach(item =>{
		            		feedbackHtml += `
		            			<div class="feedback-item">
		            				<p><strong>유형:</strong>\${item.type}</p>
		            				<p><strong>문제 요약:</strong>\${item.issue_summary}</p>
		            				<p><strong>상세 이유:</strong>\${item.detailed_reason}</p>
		            				<p><strong>수정 제한:</strong>\${item.suggested_revision}</p>
		            			</div>
		            		`;
		            	});
		            }
		            feedbackHtml += '<hr>'; // 섹션 구분선
				})
			} else {
		        feedbackHtml += '<p>제공된 피드백이 없습니다.</p>';
		    }

		    feedbackArea.innerHTML = feedbackHtml;
		})
		.catch(error => {
            console.error('Error fetching self-intro details:', error);
            alert('데이터를 불러오는 데 실패했습니다.');
        });
	}
	
	function requestProofread() {
		const selectedSiId = document.getElementById('selfIntroList').value;
		if (selectedSiId) {
			window.location.href = `/sint/sintwrt?siId=\${selectedSiId}`;
		} else {
			alert('먼저 자기소개서를 선택해주세요.');
		}
	}
</script>