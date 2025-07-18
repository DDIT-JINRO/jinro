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

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1881066df7ed9e16e4315953d2419995"></script>
	<script>
	// 전역 변수 선언
    let map; // 카카오 지도 객체
    let currentMarker; // 현재 지도에 표시된 단일 마커
    let currentInfoWindow; // 현재 열린 인포윈도우

    // DOM 요소 참조
    // 로딩 오버레이 요소
    const loadingOverlay = document.getElementById('loadingOverlay');
 	// 학교 지도 모달 요소
    const schoolMapModal = document.getElementById('schoolMapModal');
 	// 지도 모달 닫기 버튼 요소
    const closeMapModalButton = document.getElementById('closeMapModalButton');
    // 학교 주소 표시 요소
    const schoolAddressElement = document.getElementById('schoolAddress');
 	// 상세 정보 페이지 학교 이름 요소
    const detailSchoolNameElement = document.getElementById('detailSchoolName');
 	// 학교 전화번호 표시 요소
    const schoolTelElement = document.getElementById('schoolTel');
 	// 모달 내 학교 검색 섹션 요소
    const schoolSearchInModal = document.getElementById('schoolSearchInModal');
 	// 모달 내 학교 이름 검색 입력 필드 요소
    const schoolNameInputInModal = document.getElementById('schoolNameInputInModal');
 	// 모달 내 검색 버튼 요소
    const searchButtonInModal = document.getElementById('searchButtonInModal');
 	// 모달 내 검색 결과 목록 요소
    const schoolListInMapModal = document.getElementById('schoolListInMapModal');

    let currentSchoolData = null; // 현재 상세 정보를 보고 있는 학교 데이터

    // === 1. 페이지 로드 시 상세 정보 불러오기 ===
    document.addEventListener('DOMContentLoaded', async () => {
        const urlParams = new URLSearchParams(window.location.search);
        const hsId = urlParams.get('hsId'); // URL에서 고등학교 ID 가져오기

        if (!hsId) {
            alert('고등학교 정보가 없습니다.');
            // 목록 페이지로 리다이렉션 또는 에러 메시지 표시
            return;
        }

        loadingOverlay.style.display = 'flex'; // 로딩 오버레이 표시
        try {
            // 백엔드에서 특정 ID의 고등학교 상세 정보를 불러오는 API 호출
            // ** 이 부분은 백엔드에 `/highSchool/api/highschools/{hsId}` 형태의 API가 있어야 합니다.
            // ** 백엔드 매퍼에 selectHighSchoolById(String hsId) 쿼리를 추가해야 함.
            const response = await fetch(`/highSchool/api/highschools/\${hsId}`); 
            if (!response.ok) {
                throw new Error(`HTTP Error! Status: \${response.status}`);
            }
            const school = await response.json();
            console.log("가져온 고등학교 상세 정보:", school);

            if (school) {
                currentSchoolData = school; // 현재 학교 데이터 저장
                detailSchoolNameElement.textContent = school.hsName;
                schoolAddressElement.textContent = school.hsAddr || '정보없음';
                schoolTelElement.textContent = school.hsTel || '정보없음';

                // 주소 클릭 시 지도 모달 열기 이벤트 리스너 추가
                schoolAddressElement.addEventListener('click', () => {
                    openSchoolMapModal(currentSchoolData);
                });
            } else {
                alert('고등학교 정보를 찾을 수 없습니다.');
            }
        } catch (error) {
            console.error('고등학교 상세 정보를 불러오는 중 오류 발생:', error);
            alert('고등학교 상세 정보를 불러오는데 실패했습니다. 콘솔을 확인해주세요.');
        } finally {
            loadingOverlay.style.display = 'none'; // 로딩 오버레이 숨기기
        }
    });

    
		
	</script>
	
</body>
</html>