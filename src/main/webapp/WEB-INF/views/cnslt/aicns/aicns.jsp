<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cnslt/aicns/aicns.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">상담</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/cnslt/resve/crsv/reservation.do">상담 예약</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/cnslt/rvw/cnsReview.do">상담 후기</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/cnslt/aicns/aicns.do">AI 상담</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="ai-wrap">

				<!-- Hero / 설명 -->
				<section class="ai-hero">
					<h1>AI 상담</h1>

					<!-- 1. 서비스 목적/가치 -->
					<h2 class="ai-subtitle">서비스 소개</h2>
					<p>
						CareerPath AI 상담은 진로·학업·심리 영역에 대해 즉각적이고 맞춤형 조언을 제공합니다.<br /> 시간·장소
						제약 없이 언제든 접근할 수 있는 <b>24시간 상담 창구</b>입니다.
					</p>

					<!-- 2. 주제별 안내 -->
					<h2 class="ai-subtitle">상담 주제</h2>
					<p>
						채용·진로, 학업 설계, 마음 건강—세 가지 주제 중 하나를 선택하고 지금 바로 AI와 대화를 시작하세요.<br />
						상담은 <b>팝업창에서만</b> 진행되며, <b>운영 측에 대화가 저장되지 않는 휘발성</b> 방식으로 제공됩니다.
					</p>
					<ul class="ai-hero-list">
						<li><b>취업 상담</b>: 이력서/자소서 피드백, 면접 대비, 포지션 매칭 아이디어, 커리어 로드맵 제안</li>
						<li><b>학업 상담</b>: 전공/교과 선택, 학습 전략 수립, 진학 계획, 과목별 학습 루틴 설계</li>
						<li><b>심리 상담</b>: 스트레스·불안 등 마음 건강에 대한 1차적 셀프케어 가이드 (의료·치료 행위
							아님)</li>
					</ul>

					<!-- 3. 이용 방식 -->
					<h2 class="ai-subtitle">이용 방식</h2>
					<ul class="ai-hero-list">
						<li>주제를 선택하고 “상담 시작하기”를 누르면 새로운 팝업 창이 열립니다.</li>
						<li>상담은 질문–답변 형식으로 자유롭게 이어갈 수 있습니다.</li>
						<li>시작 시 <b>이용권 1회</b>가 차감되며, 선택한 주제는 상담 중 변경할 수 없습니다.
						</li>
						<li>팝업이 닫히면 대화 내용은 즉시 종료되며 복구되지 않습니다.</li>
					</ul>

					<!-- 4. 주의/책임 고지 -->
					<h2 class="ai-subtitle">주의 및 책임 고지</h2>
					<ul class="ai-hero-list">
						<li>AI 상담은 참고용으로 제공되며, 모든 상황에 100% 일치하지 않을 수 있습니다.</li>
						<li>법률·의료·재정 등 전문 분야의 최종 의사결정에는 반드시 전문가 확인이 필요합니다.</li>
						<li>심리 상담은 치료·진단 목적이 아니며, 심각한 문제가 있는 경우 전문 상담기관을 이용해 주세요.</li>
					</ul>

					<!-- 5. 활용 팁 -->
					<h2 class="ai-subtitle">활용 팁</h2>
					<ul class="ai-hero-list">
						<li>상담 중 나온 유용한 답변은 바로 메모해 두세요.</li>
						<li>비슷한 질문을 다른 표현으로 시도하면 다양한 관점의 답변을 얻을 수 있습니다.</li>
						<li>목표·조건(예: “서울 지역 대학 위주”, “3년 이내 취업”)을 구체적으로 제시하면 더 정확한 조언이
							제공됩니다.</li>
					</ul>
					<br/>
					<br/>
					<!-- 기존 공지 -->
					<div class="ai-notice">
						<span class="dot"></span> 구독 콘텐츠로 상담 1회 시작 시 <b>이용권(횟수)</b>이
						차감됩니다.
					</div>
					<div class="ai-notice">
						<span class="dot"></span> 팝업이 닫히면 대화 내용은 복구되지 않습니다. 필요한 답변은 즉시
						메모해두세요.
					</div>
				</section>

				<!-- 카테고리 선택 카드 -->
				<section class="ai-grid" id="aiCardGrid" role="radiogroup"
					aria-label="AI 상담 주제 선택">
					<article class="ai-card" data-type="JOB" tabindex="0" role="radio" aria-checked="false">
						<div class="ai-icon" aria-hidden="true">💼</div>
						<h3>취업 상담</h3>
						<p>이력서·자소서 피드백, 면접 준비, 포지션 매칭 등 커리어 전반에 대한 코칭.</p>
					</article>

					<article class="ai-card" data-type="STUDY" tabindex="0"
						role="radio" aria-checked="false">
						<div class="ai-icon" aria-hidden="true">📚</div>
						<h3>학업 상담</h3>
						<p>전공/교과 선택, 학습 계획, 진학 전략 수립을 위한 맞춤형 가이드.</p>
					</article>

					<article class="ai-card" data-type="MIND" tabindex="0" role="radio" aria-checked="false">
						<div class="ai-icon" aria-hidden="true">🧠</div>
						<h3>심리 상담</h3>
						<p>스트레스·불안 등 마음 건강 관련 1차적 셀프 케어 가이드(의료 행위 아님).</p>
					</article>
				</section>

				<!-- 하단: 배지 + 시작버튼 -->
				<div class="ai-bottom">
					<span class="ai-badge">선택한 주제는 상담 종료까지 변경할 수 없습니다. 시작 전 주제를 꼭 확인해 주세요.</span>
					<div class="ai-start">
						<button type="button" class="btn" id="btnStart" disabled>상담시작하기</button>
					</div>
				</div>
			</div>

			<!-- 모달 -->
			<div class="ai-modal-backdrop" id="modalBg" aria-hidden="true"></div>
			<div class="ai-modal" id="confirmModal" role="dialog"
				aria-modal="true" aria-labelledby="confirmTitle">
				<h4 id="confirmTitle">상담 시작을 확인해 주세요</h4>
				<div class="ai-kv">
					<div>
						<b>선택 주제</b> : <span id="kvType">-</span>
					</div>
					<div>
						<b>차감 횟수</b> : <span id="kvUse">1회</span>
					</div>
				</div>
				<p>확인을 누르면 이용권이 차감되며, 새 팝업 창에서 상담이 시작됩니다.</p>
				<div class="row">
					<button type="button" class="btn secondary" id="btnCancel">취소</button>
					<button type="button" class="btn" id="btnConfirm">확인하고 시작</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/cnslt/aicns/aicns.js"></script>
</body>
</html>