<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/univ/dpsrch/deptDetail.css">

<section class="channel">
    <!-- 여기가 네비게이션 역할을 합니다. -->
    <div class="channel-title">
        <!-- 대분류 -->
        <div class="channel-title-text">진학 정보</div> 
    </div>
    <div class="channel-sub-sections">
        <!-- 중분류 -->
        <div class="channel-sub-section-itemIn"><a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a></div>
        <div class="channel-sub-section-item"><a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a></div>
        <div class="channel-sub-section-item"><a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a></div>
    </div>
</section>

<div>
    <div class="public-wrapper">
        <!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
        <div class="tab-container" id="tabs">
            <a class="tab" href="/ertds/univ/uvsrch/selectUnivList.do">대학 검색</a>
            <a class="tab active" href="/ertds/univ/dpsrch/selectDeptList.do">학과 정보</a>
        </div>
    </div>
</div>

<div>
    <div class="public-wrapper">
        <div class="dept-detail-container">
            <!-- 제목 섹션 -->
            <div class="dept-title-section">
                <div class="dept-header">
                    <div class="dept-university-name">${deptDetail.uddMClass}</div>
                    <div class="item-action">
						<c:set var="isBookmarked" value="false" />

						<c:forEach var="bookmark" items="${bookMarkVOList}">
							<c:if test="${deptDetail.uddId eq bookmark.bmTargetId}">
								<c:set var="isBookmarked" value="true" />
							</c:if>
						</c:forEach>

						<button class="bookmark-btn ${isBookmarked ? 'active' : ''}"
							data-category-id="G03005"
							data-target-id="${fn:escapeXml(deptDetail.uddId)}">
							<span class="icon-active"> <img
								src="/images/bookmark-btn-active.png" alt="활성 북마크">
							</span> <span class="icon-inactive"> <img
								src="/images/bookmark-btn-inactive.png" alt="비활성 북마크">
							</span>
						</button>
					</div>
                </div>
                <div class="dept-divider"></div>
                <div class="dept-info">
                    <div class="dept-info-item">계열 | ${deptDetail.uddLClass}</div>
                    <div class="dept-info-item">입학경쟁률 | ${deptDetail.admissionRateFormatted}</div>
                    <div class="dept-info-item">취업률 | ${deptDetail.employmentRatePercent}</div>
                </div>
            </div>

            <!-- 내용 섹션 -->
            <div class="dept-content-section">
                <!-- 학과 개요 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">학과 개요</h3>
                    </div>
                    <p class="dept-content-text">${deptDetail.uddSum}</p>
                </div>

                <!-- 흥미, 적성 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">흥미, 적성</h3>
                    </div>
                    <p class="dept-content-text">${deptDetail.uddInterest}</p>
                </div>

                <!-- 학과 특성 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">학과 특성</h3>
                    </div>
                    <p class="dept-content-text">${deptDetail.uddProperty}</p>
                </div>
                
                
                <!-- 성별 입학률 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">성별 입학률</h3>
                    </div>
                    <div class="dept-chart-container">
                        <canvas id="genderEmploymentChart" width="400" height="200"></canvas>
                    </div>
                </div>

                <!-- 임금 분포 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">취업 후 첫 임금 분포</h3>
                    </div>
                    <div class="dept-chart-container">
                        <canvas id="salaryChart" width="400" height="200"></canvas>
                    </div>
                </div>

                <!-- 직업만족도 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">직업만족도</h3>
                    </div>
                    <div class="dept-chart-container">
                        <canvas id="satisfactionChart" width="400" height="200"></canvas>
                    </div>
                </div>

                <!-- 취업 분야 분포 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">취업 분야 분포</h3>
                    </div>
                    <div class="dept-chart-container">
                        <canvas id="employmentFieldChart" width="400" height="300"></canvas>
                    </div>
                </div>

                <!-- 관련 자격 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">관련 자격</h3>
                    </div>
                    <div class="dept-related-section">
                        <div class="dept-text-content">
                            <p>${deptDetail.uddLiList}</p>
                        </div>
                    </div>
                </div>

                <!-- 관련직업 -->
                <div class="dept-content-item">
                    <div class="dept-content-header">
                        <div class="dept-asterisk"></div>
                        <h3 class="dept-content-title">관련직업</h3>
                    </div>
                    <div class="dept-related-jobs">
                        <div class="dept-job-tags">
                            <c:forEach var="job" items="${deptDetail.jobListArray}">
                                <span class="dept-job-tag">${job}</span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<!-- Chart.js 라이브러리 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>

<!-- 차트 데이터 변수 -->
<script type="text/javascript">
    // 임금 분포 데이터
    const salaryData = {
        labels: ['0~150만원', '151~200만원', '201~250만원', '251~300만원', '301만원 이상'],
        data: [
            ${deptDetail.udsSalary0150Rate != null ? deptDetail.udsSalary0150Rate : 0},
            ${deptDetail.udsSalary151200Rate != null ? deptDetail.udsSalary151200Rate : 0},
            ${deptDetail.udsSalary201250Rate != null ? deptDetail.udsSalary201250Rate : 0},
            ${deptDetail.udsSalary251300Rate != null ? deptDetail.udsSalary251300Rate : 0},
            ${deptDetail.udsSalary301PlusRate != null ? deptDetail.udsSalary301PlusRate : 0}
        ]
    };

    // 직업만족도 데이터
    const satisfactionData = {
        labels: ['매우 불만족', '불만족', '보통', '만족', '매우 만족'],
        data: [
            ${deptDetail.udsSatisfactionVeryDissatisfied != null ? deptDetail.udsSatisfactionVeryDissatisfied : 0},
            ${deptDetail.udsSatisfactionDissatisfied != null ? deptDetail.udsSatisfactionDissatisfied : 0},
            ${deptDetail.udsSatisfactionNeutral != null ? deptDetail.udsSatisfactionNeutral : 0},
            ${deptDetail.udsSatisfactionSatisfied != null ? deptDetail.udsSatisfactionSatisfied : 0},
            ${deptDetail.udsSatisfactionVerySatisfied != null ? deptDetail.udsSatisfactionVerySatisfied : 0}
        ]
    };

    // 취업 분야 분포 데이터
    const employmentFieldData = {
        labels: ['건설', '경영관리', '교육', '미용', '복지', '연구', '운송', '예술', '생산', '농업'],
        data: [
            ${deptDetail.udsFieldConstructionRate != null ? deptDetail.udsFieldConstructionRate : 0},
            ${deptDetail.udsFieldManagementRate != null ? deptDetail.udsFieldManagementRate : 0},
            ${deptDetail.udsFieldEducationRate != null ? deptDetail.udsFieldEducationRate : 0},
            ${deptDetail.udsFieldBeautyRate != null ? deptDetail.udsFieldBeautyRate : 0},
            ${deptDetail.udsFieldWelfareRate != null ? deptDetail.udsFieldWelfareRate : 0},
            ${deptDetail.udsFieldResearchRate != null ? deptDetail.udsFieldResearchRate : 0},
            ${deptDetail.udsFieldTransportRate != null ? deptDetail.udsFieldTransportRate : 0},
            ${deptDetail.udsFieldArtRate != null ? deptDetail.udsFieldArtRate : 0},
            ${deptDetail.udsFieldProductRate != null ? deptDetail.udsFieldProductRate : 0},
            ${deptDetail.udsFieldFarmerRate != null ? deptDetail.udsFieldFarmerRate : 0}
        ]
    };

    // 성별 입학률 데이터
    const genderEmploymentData = {
        labels: ['남성', '여성'],
        data: [
            ${deptDetail.udsMaleAdmissionRate != null ? deptDetail.udsMaleAdmissionRate : 0},
            ${deptDetail.udsFemaleAdmissionRate != null ? deptDetail.udsFemaleAdmissionRate : 0}
        ]
    };
</script>

<script type="text/javascript" src="/js/ertds/univ/dpsrch/deptDetail.js"></script>
</body>
</html>