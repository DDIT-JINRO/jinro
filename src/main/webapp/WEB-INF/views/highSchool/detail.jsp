<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고등학교 상세 정보</title>
<!-- CSS 임포트 -->
    <link rel="stylesheet" href="/css/highSchool/detail.css">
</head>
<body>
	<h1>고등학교 상세 정보</h1>

	<div class="school-detail-container">
		<h2 id="detailSchoolName"></h2>
		<div class="school-info">
			<p>
				<b>주소:</b> <span id="schoolAddress">정보 불러오는 중...</span>
			</p>
			<p>
				<b>전화번호:</b> <span id="schoolTel">정보 불러오는 중...</span>
			</p>
		</div>
		<button id="goToListButton" class="go-to-list-button">고등학교
			목록으로</button>
	</div>

	<div id="loadingOverlay" class="loading-overlay" style="display: none;">
		<p>정보를 불러오는 중입니다</p>
	</div>


	
</body>
</html>