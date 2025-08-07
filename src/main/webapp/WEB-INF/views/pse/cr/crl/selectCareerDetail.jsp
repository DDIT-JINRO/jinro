<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/pse/cr/crl/selectCareerDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel" data-error-message="${errorMessage}">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-item">
			<a href="/pse/cat/careerAptitudeTest.do">진로 심리검사</a>
		</div>
		<div class="channel-sub-section-itemIn">
			<a href="/pse/cr/crl/selectCareerList.do">직업백과</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="dept-detail-container">
			<!-- 제목 섹션 -->
			<div class="dept-title-section">
				<div class="dept-header">
					<div class="dept-university-name">${jobs.jobName}</div>
					<div class="item-action">
						<button class="bookmark-btn active" data-category-id="${bookmark.bmCategoryId}" data-target-id="${bookmark.bmTargetId}">
							<span class="icon-active">
								<img src="/images/bookmark-btn-active.png" alt="활성 북마크" width="30" height="30">
							</span>

							<span class="icon-inactive">
								<img src="/images/bookmark-btn-inactive.png" alt="비활성 북마크" width="30" height="30">
							</span>
						</button>
					</div>
				</div>
				<div class="dept-divider"></div>
				<div class="dept-info row">
					<div class="dept-info-item">${jobs.jobLcl}> ${jobs.jobMcl}</div>
					<div class="job-source">[ 정보제공 : 한국고용정보원 고용24 (구 워크넷) ]</div>
				</div>
			</div>

			<!-- 내용 섹션 -->
			<div class="dept-content-section">
				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">하는 일</h3>
					</div>
					<p class="dept-content-text">${jobs.jobMainDuty}</p>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">이 직업을 갖는 방법</h3>
					</div>
					<p class="dept-content-text">${jobs.jobWay}</p>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">임금</h3>
					</div>
					<p class="dept-content-text">${jobs.jobSal}</p>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">직업만족도</h3>
					</div>
					<p class="dept-content-text">${jobs.jobSatis}점</p>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">재직자가 생각하는 일자리 전망</h3>
					</div>
					<div class="dept-chart-container">
						<canvas id="job-prospect-chart" width="400" height="200"></canvas>
					</div>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">관련전공 및 자격</h3>
					</div>
					<p class="dept-content-text">${jobs.jobRelatedMajor}</p>
				</div>

				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">관련직업</h3>
					</div>
					<div class="dept-related-jobs">
						<div class="dept-job-tags">
							<c:forEach var="job" items="${jobs.jobsRelVOList}">
								<a class="dept-job-tag" href="/pse/cr/crl/selectCareerDetail.do?jobCode=${job.jrCode}">${job.jobName}</a>
							</c:forEach>
						</div>
					</div>
				</div>


				<div class="dept-content-item">
					<div class="dept-content-header">
						<div class="dept-asterisk"></div>
						<h3 class="dept-content-title">학력 분포</h3>
					</div>
					<div class="dept-chart-container">
						<canvas id="job-edubg-chart" width="400" height="200"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script src="/js/pse/cr/crl/selectCareerDetail.js"></script>
<!-- Chart.js 라이브러리 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

<!-- 차트 데이터 변수 -->
<script type="text/javascript">
    // 취업 분야 분포 데이터
    
    const jobEdubgData = {
        labels: ['중학교', '고등학교', '전문대학', '대학', '대학원', '박사'],
        data: [
            ${jobs.edubgMgraduUndr != null ? jobs.edubgMgraduUndr : 0},
            ${jobs.edubgHgradu     != null ? jobs.edubgHgradu     : 0},
            ${jobs.edubgCgraduUndr != null ? jobs.edubgCgraduUndr : 0},
            ${jobs.edubgUgradu     != null ? jobs.edubgUgradu     : 0},
            ${jobs.edubgHgradu     != null ? jobs.edubgHgradu     : 0},
            ${jobs.edubgDgradu     != null ? jobs.edubgDgradu     : 0}
        ]
    };
    
    // 성별 입학률 데이터
    const jobProspectData = {
        labels: ['감소', '다소감소', '유지', '다소증가', '증가'],
        data: [
            ${jobs.outlookDecrease       != null ? jobs.outlookDecrease       : 0},
            ${jobs.outlookSlightDecrease != null ? jobs.outlookSlightDecrease : 0},
            ${jobs.outlookStable         != null ? jobs.outlookStable         : 0},
            ${jobs.outlookSlightIncrease != null ? jobs.outlookSlightIncrease : 0},
            ${jobs.outlookIncrease       != null ? jobs.outlookIncrease       : 0}
        ]
    };
</script>

</body>
</html>