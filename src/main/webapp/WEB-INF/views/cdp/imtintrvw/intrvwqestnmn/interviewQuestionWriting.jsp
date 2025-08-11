<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/imtintrvw/intrvwqestnmn/interviewQuestionWriting.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">경력관리</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item"><a href="/cdp/rsm/rsm/resumeList.do">이력서</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/sint/qestnlst/questionList.do">자기소개서</a></div>
		<div class="channel-sub-section-itemIn"><a href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">모의면접</a></div>
		<div class="channel-sub-section-item"><a href="/cdp/aifdbck/rsm/aiFeedbackResumeList.do">AI 피드백</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab" href="/cdp/imtintrvw/intrvwitr/interviewIntro.do">면접의 기본</a>
		    <a class="tab" href="/cdp/imtintrvw/intrvwqestnlst/intrvwQuestionList.do">면접 질문 리스트</a>
		    <a class="tab active" href="/cdp/imtintrvw/intrvwqestnmn/interviewQuestionMangementList.do">면접 질문 관리</a>
		    <a class="tab" href="/cdp/imtintrvw/aiimtintrvw/aiImitationInterview.do">AI 모의 면접</a>
  		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		
   <div class="public-wrapper-main">
      <section class="selfintro-write-form">
        <form action="/cdp/imtintrvw/intrvwqestnmn/save" method="post">
          <!-- 제목 -->
          <div class="section-title">
            <input type="text" name="idlTitle" value="${interviewDetailListVO.idlTitle}"
                   placeholder="제목을 입력하세요." class="title-input" required="required" />
            <input type="hidden" name="idlId" value="${empty interviewDetailListVO.idlId ? 0 : interviewDetailListVO.idlId}" />
            <input type="hidden" name="memId" value="${interviewDetailListVO.memId}" />
            <input type="hidden" name="idlStatus" id="idlStatus" value="완료" />
          </div>

          <div id="questionContainer">
            <!-- 신규 작성: 공통 질문 렌더링 -->
            <c:if test="${empty interviewQuestionVOList}">
              <c:forEach var="q" items="${commonQList}" varStatus="st">
                <div class="qa-block">
                  <div class="question-block">
                    <span class="question-number">${st.index + 1}.</span>
                    <span class="question-text">${q.iqContent}</span>
                    <input type="hidden" name="iqIdList" value="${q.iqId}" />
                  </div>
                  <div class="answer-block">
                    <textarea name="idAnswerList" placeholder="답변을 작성해주세요."
                              rows="7" maxlength="2000"
                              oninput="countChars(this, ${st.index})"></textarea>
                    <div class="char-count">글자 수: <span id="charCount-${st.index}">0</span> / 2000</div>
                  </div>
                </div>
              </c:forEach>
            </c:if>

            <!-- 수정 모드: 저장된 질문/답변 렌더링 -->
            <c:if test="${not empty interviewQuestionVOList}">
              <c:forEach var="q" items="${interviewQuestionVOList}" varStatus="st">
                <div class="qa-block">
                  <div class="question-block">
                    <span class="question-number">${st.index + 1}.</span>
                    <span class="question-text">${q.iqContent}</span>
                    <input type="hidden" name="iqIdList" value="${q.iqId}" />
                  </div>
                  <div class="answer-block">
                    <textarea name="idAnswerList" placeholder="답변을 작성해주세요."
                              rows="7" maxlength="2000"
                              oninput="countChars(this, ${st.index})">${interviewDetailVOList[st.index].idAnswer}</textarea>
                    <div class="char-count">글자 수: <span id="charCount-${st.index}">0</span> / 2000</div>
                  </div>
                </div>
              </c:forEach>
            </c:if>
          </div>

          <!-- 버튼 그룹 -->
          <div class="btn-group">
            <div class="btn-left-group">
              <c:if test="${not empty interviewDetailListVO.idlId}">
                <button type="button" class="btn-delete" id="btnDelete">삭제하기</button>
              </c:if>
            </div>
            <div class="btn-right-group">
              <button type="button" class="btn-temp-save" id="btnTemp">임시저장</button>
              <button type="button" class="btn-preview">미리보기</button>
              <button type="submit" class="btn-submit">작성완료</button>
            </div>
          </div>
        </form>

        <!-- 삭제 전송용 폼 -->
        <form id="deleteForm" action="/cdp/imtintrvw/intrvwqestnmn/delete.do" method="post" style="display:none;">
          <input type="hidden" name="idlId" value="${interviewDetailListVO.idlId}" />
        </form>
      </section>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script
	src="/js/cdp/imtintrvw/intrvwqestnmn/interviewQuestionWriting.js">
	// 스크립트 작성 해주시면 됩니다.
</script>