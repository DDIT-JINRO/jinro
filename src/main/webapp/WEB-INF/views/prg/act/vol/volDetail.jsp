<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/act/vol/volDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-item"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<div class="tab-container" id="tabs">
		    <a class="tab active" href="/prg/act/vol/volList.do">봉사활동</a>
		    <a class="tab" href="/prg/act/cr/crList.do">직업체험</a>
		    <a class="tab" href="/prg/act/sup/supList.do">서포터즈</a>
  		</div>
  		<div class="public-wrapper-main">
		    <div class="detail-container">
		
		        <%-- 1. 최상단 제목 --%>
		        <div class="detail-title-wrapper">
		            <h1>${volDetail.contestTitle}</h1>
		            <div class="meta-info">
		                <span>조회수: ${volDetail.contestRecruitCount}</span>
		                <span class="divider">|</span>
		                <span>게시일: <fmt:formatDate value="${volDetail.contestCreatedAt}" pattern="yyyy.MM.dd"/></span>
		            </div>
		        </div>
		
		        <%-- 2. 포스터(좌) 및 요약 정보(우) 섹션 --%>
		        <div class="summary-layout">
		            <div class="poster-wrapper">
		                <img src="/files/download?fileGroupId=${volDetail.fileGroupId}&seq=1" alt="${cttDetail.contestTitle} 포스터" class="poster-image" id="poster-modal-trigger">
		            </div>
		            <!-- 이미지 모달 -->
		            <div id="poster-modal" class="modal">
					    <span class="close-button">&times;</span>
					    <img class="modal-content" id="modal-image">
					</div>
		            <div class="summary-wrapper">
		                <table class="info-table">
		                    <tbody>
		                        <tr><th>주최</th><td>${volDetail.contestHost}</td></tr>
		                        <tr><th>주관</th><td>${volDetail.contestOrganizer}</td></tr>
		                        <tr><th>접수기간</th>
		                            <td>
		                                <fmt:formatDate value="${volDetail.contestStartDate}" pattern="yyyy.MM.dd"/> ~ 
		                                <fmt:formatDate value="${volDetail.contestEndDate}" pattern="yyyy.MM.dd"/>
		                            </td>
		                        </tr>
		                        <tr><th>참가자격</th><td>${volDetail.contestTargetName}</td></tr>
		                        <tr><th>접수방법</th><td>${volDetail.applicationMethod}</td></tr>
					            <tr><th>시상종류</th><td>${volDetail.awardType}</td></tr>
					            <tr><th>홈페이지</th><td><a href="${volDetail.contestUrl}" target="_blank">바로가기</a></td></tr>
		                    </tbody>
		                </table>
		            </div>
		        </div>
		
		        <%-- 3. 상세 내용 섹션 --%>
		        <div class="content-wrapper">
		            <div class="tabs">
		                <div class="tab-item active">공모요강</div>
		            </div>
		            <div class="tab-content">
		                <c:forEach var="section" items="${volDetail.descriptionSections}">
					        <div class="description-section">
					            <p class="description-text">${section}</p>
					        </div>
					    </c:forEach>
		            </div>
		        </div>
		        
		        <%-- 4. 하단 목록 버튼 --%>
		        <div class="bottom-button-wrapper">
		            <a href="/prg/act/vol/volList.do" class="btn-list-bottom">목록</a>
		        </div>
		
		    </div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script src="/js/prg/act/vol/volDetail.js">

</script>