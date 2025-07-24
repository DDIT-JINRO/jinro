<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/sint/sintwrt/selfIntroWriting.css">
<!-- 스타일 여기 적어주시면 가능 -->

<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert('${fn:escapeXml(errorMessage)}');
  </script>
</c:if>
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
		<div class="channel-sub-section-itemIn">
			<a href="/sint/qestnlst">자기소개서</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/imtintrvw/bsintrvw">모의면접</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/aifdbck/rsm">AI 피드백</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
			<a class="tab" href="/sint/qestnlst">질문 리스트</a> <a class="tab"
				href="/sint/sintlst">자기소개서 리스트</a> <a class="tab active"
				href="/sint/sintwrt">자기소개서 작성</a>
		</div>
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">

			<section class="selfintro-write-form">
				<form action="/sint/sintwrt/save" method="post">
					<!-- 제목 -->
					<div class="section-title">
						<h2>자기소개서 제목을 입력하세요.</h2>
						<input type="text" name="siTitle" value="${selfIntroVO.siTitle}"
							placeholder="제목을 입력하세요." class="title-input" /> 
							<input type="hidden" name="siId" value="${selfIntroVO.siId}" />
							<input type="text" name="memId" value="${selfIntroVO.memId}" />
					</div>


					<!--  ➤ 질문·답변 블록이 들어갈 컨테이너 -->
					<div id="questionContainer">
						<!-- 1) 공통 질문 -->
						<c:forEach var="q" items="${commonQList}" varStatus="st">
							<div class="qa-block">
								<div class="question-block">
									<span class="question-number">${st.index + 1}.</span> <span
										class="question-text">${q.siqContent}</span><input
											type="hidden" name="siqIdList" value="${q.siqId}" />
								</div>
								<div class="answer-block">
									<textarea name="sicContentList" placeholder="답변을 작성해주세요."></textarea>
								</div>
							</div>
						</c:forEach>

						<!-- 2) 선택된 질문(수정 모드) -->
						<c:if test="${not empty selfIntroQVOList}">
							<c:forEach var="q" items="${selfIntroQVOList}" varStatus="st">
								<div class="qa-block">
									<div class="question-block">
										<!-- commonQList.size() + index 로 번호 매기기 -->
										<span class="question-number"> ${commonQList.size() + st.index + 1}.
										</span> <span class="question-text">${q.siqContent}</span> <input
											type="hidden" name="siqIdList" value="${q.siqId}" />
									</div>
									<div class="answer-block">
										<!-- contentList와 인덱스가 맞도록 -->
										<textarea name="sicContentList" placeholder="답변을 작성해주세요.">${selfIntroContentVOList[st.index].sicContent}</textarea>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>


					<!--  ➤ 버튼 그룹 -->
					<div class="btn-group">
						<button type="button" class="btn-temp-save">임시저장</button>
						<button type="button" class="btn-preview">미리보기</button>
						<button type="submit" class="btn-submit">작성완료</button>
					</div>
				</form>
			</section>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>


</script>