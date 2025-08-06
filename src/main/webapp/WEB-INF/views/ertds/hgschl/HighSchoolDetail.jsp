<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/ertds/hgschl/HighSchoolDetail.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진학 정보</div>
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item">
			<a href="/ertds/univ/uvsrch/selectUnivList.do">대학교 정보</a>
		</div>
		<!-- 중분류 -->
		<div class="channel-sub-section-itemIn">
			<a href="/ertds/hgschl/selectHgschList.do">고등학교 정보</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/ertds/qlfexm/selectQlfexmList.do">검정고시</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<!--
			<div class="tab-container" id="tabs">
	 		    <div class="tab active ">고등학교 검색</div>
				<div class="tab">학과 정보</div>
	  		</div>
  		-->
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main" id="highSchoolDetailContainer"
			data-hs-name="${highSchool.hsName}" data-hs-addr="${highSchool.hsAddr}" data-hs-tel="${highSchool.hsTel}"
			data-hs-lat="${highSchool.hsLat}" data-hs-lot="${highSchool.hsLot}">

			<div class="detail-card">
				<div class="card-header">
					<h2>${highSchool.hsName}</h2>
					<a href="/ertds/hgschl/selectHgschList.do" class="btn-list">목록으로</a>
				</div>
				<div class="card-body">
					<div class="info-grid">
						<div class="info-item">
							<span class="label">지역</span> <span class="value">${highSchool.hsRegion}</span>
						</div>
						<div class="info-item">
							<span class="label">설립유형</span> <span class="value">${highSchool.hsFoundType}</span>
						</div>
						<div class="info-item">
							<span class="label">학교유형</span> <span class="value">${highSchool.hsTypeName}</span>
						</div>
						<div class="info-item">
							<span class="label">공학여부</span> <span class="value">${highSchool.hsCoeduType}</span>
						</div>
						<div class="info-item full-width">
							<span class="label">주소</span> <span class="value clickable"
								id="schoolAddress">${highSchool.hsAddr}</span>
						</div>
						<div class="info-item">
							<span class="label">연락처</span> <span class="value">${highSchool.hsTel}</span>
						</div>
						<div class="info-item">
							<span class="label">홈페이지</span> <span class="value"><a
								href="${highSchool.hsHomepage}" target="_blank">${highSchool.hsHomepage}</a></span>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 지도가 그려지는 공간(Map Canvas) -->
			<div class="map-modal" id="schoolMapModal">
				<div class="modal-content">
					<span class="modal-close-btn" id="closeMapModalButton">&times;</span>
					<div id="map" style="width: 100%; height: 100%;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1881066df7ed9e16e4315953d2419995&libraries=services,clusterer,drawing"></script>

<script src="/js/util/mapUtil.js"></script>
<script src="/js/ertds/hgschl/HighSchoolDetail.js"></script>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
</script>