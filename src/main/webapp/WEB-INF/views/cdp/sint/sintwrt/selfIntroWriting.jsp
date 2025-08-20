<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/sint/sintwrt/selfIntroWriting.css">

<c:if test="${not empty errorMessage}">
	<script type="text/javascript">
		alert('${fn:escapeXml(errorMessage)}');
	</script>
</c:if>

<section class="channel">
	<div class="channel-title">
		<div class="channel-title-text">경력관리</div>
	</div>
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/cdp/rsm/rsm/resumeList.do">이력서</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">모의면접</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a>
		</div>
	</div>
</section>

<div class="public-wrapper">
	<div class="tab-container" id="tabs">
		<a class="tab" href="/cdp/sint/qestnlst/questionList.do">질문 리스트</a>
		<a class="tab" href="/cdp/sint/sintlst/selfIntroList.do">자기소개서 리스트</a>
		<a class="tab active" href="/cdp/sint/sintwrt/selfIntroWriting.do">자기소개서 작성</a>
	</div>

	<div class="public-wrapper-main">
		<div class="selfintro-write-container">
			<form action="/cdp/sint/sintwrt/save" method="post" class="selfintro-write-form">
				<!-- 제목 입력 섹션 -->
				<div class="form-section">
					<label class="form-label">자기소개서 제목</label>
					<input type="text" name="siTitle" value="${selfIntroVO.siTitle}" placeholder="자기소개서 제목을 입력하세요" class="form-input form-input--large" required />
					<input type="hidden" name="siId" value="${selfIntroVO.siId}" />
					<input type="hidden" name="memId" value="${selfIntroVO.memId}" />
					<input type="hidden" name="siStatus" id="siStatus" value="완료" />
				</div>

				<!-- 질문·답변 컨테이너 -->
				<div class="qa-container" id="questionContainer">
					<!-- 공통 질문 -->
					<c:forEach var="q" items="${commonQList}" varStatus="st">
						<div class="qa-card">
							<div class="qa-card__header">
								<div class="qa-card__number">${st.index + 1}</div>
								<h3 class="qa-card__question">${q.siqContent}</h3>
								<input type="hidden" name="siqIdList" value="${q.siqId}" />
							</div>
							<div class="qa-card__body">
								<div class="form-group">
									<textarea name="sicContentList" placeholder="답변을 작성해주세요" rows="8" maxlength="2000" class="form-textarea" oninput="countChars(this, ${st.index})"></textarea>
									<div class="char-counter">
										<span id="charCount-${st.index}">0</span>
										/ 2000자
									</div>
								</div>
							</div>
						</div>
					</c:forEach>

					<!-- 선택된 질문(수정 모드) -->
					<c:if test="${not empty selfIntroQVOList}">
						<c:forEach var="q" items="${selfIntroQVOList}" varStatus="st">
							<c:set var="globalIndex" value="${commonQList.size() + st.index}" />
							<div class="qa-card">
								<div class="qa-card__header">
									<div class="qa-card__number">${globalIndex + 1}</div>
									<h3 class="qa-card__question">${q.siqContent}</h3>
									<input type="hidden" name="siqIdList" value="${q.siqId}" />
								</div>
								<div class="qa-card__body">
									<div class="form-group">
										<textarea name="sicContentList" placeholder="답변을 작성해주세요" rows="8" maxlength="2000" class="form-textarea" oninput="countChars(this, ${globalIndex})">${selfIntroContentVOList[st.index].sicContent}</textarea>
										<div class="char-counter">
											<span id="charCount-${globalIndex}">0</span>
											/ 2000자
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>

				<!-- 액션 버튼 그룹 -->
				<div class="form-actions">
					<div class="form-actions__left">
						<c:if test="${selfIntroVO.siId != 0 && not empty selfIntroVO.siId}">
							<button type="button" class="btn btn--danger btn--outline">
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="16" height="16">
									<path fill-rule="evenodd" d="M8.75 1A2.75 2.75 0 0 0 6 3.75v.443c-.795.077-1.584.176-2.365.298a.75.75 0 1 0 .23 1.482l.149-.022.841 10.518A2.75 2.75 0 0 0 7.596 19h4.807a2.75 2.75 0 0 0 2.742-2.53l.841-10.52.149.023a.75.75 0 0 0 .23-1.482A41.03 41.03 0 0 0 14 4.193V3.75A2.75 2.75 0 0 0 11.25 1h-2.5ZM10 4c.84 0 1.673.025 2.5.075V3.75c0-.69-.56-1.25-1.25-1.25h-2.5c-.69 0-1.25.56-1.25 1.25v.325C8.327 4.025 9.16 4 10 4ZM8.58 7.72a.75.75 0 0 0-1.5.06l.3 7.5a.75.75 0 1 0 1.5-.06l-.3-7.5Zm4.34.06a.75.75 0 1 0-1.5-.06l-.3 7.5a.75.75 0 1 0 1.5.06l.3-7.5Z" clip-rule="evenodd" />
								</svg>
								삭제하기
							</button>
						</c:if>
					</div>
					<div class="form-actions__right">
						<button type="button" class="btn btn--secondary btn-temp-save">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="16" height="16">
								<path d="M10.75 2.75a.75.75 0 0 0-1.5 0v8.614L6.295 8.235a.75.75 0 1 0-1.09 1.03l4.25 4.5a.75.75 0 0 0 1.09 0l4.25-4.5a.75.75 0 0 0-1.09-1.03l-2.955 3.129V2.75Z" />
								<path d="M3.5 12.75a.75.75 0 0 0-1.5 0v2.5A2.75 2.75 0 0 0 4.75 18h10.5A2.75 2.75 0 0 0 18 15.25v-2.5a.75.75 0 0 0-1.5 0v2.5c0 .69-.56 1.25-1.25 1.25H4.75c-.69 0-1.25-.56-1.25-1.25v-2.5Z" />
							</svg>
							임시저장
						</button>
						<button type="button" class="btn btn--outline btn-preview">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="16" height="16">
								<path d="M10 12.5a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z" />
								<path fill-rule="evenodd" d="M.664 10.59a1.651 1.651 0 0 1 0-1.186A10.004 10.004 0 0 1 10 3c4.257 0 7.893 2.66 9.336 6.41.147.381.147.804 0 1.186A10.004 10.004 0 0 1 10 17c-4.257 0-7.893-2.66-9.336-6.41ZM14 10a4 4 0 1 1-8 0 4 4 0 0 1 8 0Z" clip-rule="evenodd" />
							</svg>
							미리보기
						</button>
						<button type="button" class="btn-autocomplete" id="autoCompleteBtn">자동완성</button>
						<button type="submit" class="btn btn--primary">
							<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" width="16" height="16">
								<path fill-rule="evenodd" d="M16.704 4.153a.75.75 0 0 1 .143 1.052l-8 10.5a.75.75 0 0 1-1.127.075l-4.5-4.5a.75.75 0 0 1 1.06-1.06l3.894 3.893 7.48-9.817a.75.75 0 0 1 1.05-.143Z" clip-rule="evenodd" />
							</svg>
							작성완료
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript" src="/js/cdp/sint/sintwrt/selfIntroWriting.js"></script>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>