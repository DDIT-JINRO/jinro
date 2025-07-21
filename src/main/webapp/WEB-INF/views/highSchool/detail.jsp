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
	
	<!-- 주소 클릭시 지도모달 -->
	<div id="schoolMapModal" class="modal-overlay" style="display: none;">
		<div class="modal-content">
			<h2>고등학교 위치 확인</h2>
			<div id="map" class="modal-map-container"></div>

			<div id="schoolSearchInModal" class="search-container-in-modal"
				style="display: none;">
				<input type="text" id="schoolNameInputInModal"
					placeholder="다른 고등학교 이름 검색" />
				<button id="searchButtonInModal">검색</button>
				<ul id="schoolListInMapModal" class="school-list-in-modal">
				</ul>
			</div>
			<button id="closeMapModalButton" class="modal-close-button">닫기</button>
		</div>
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

    // === 2. 지도 관련 유틸리티 함수 (재활용) ===
    function clearMapMarkersAndInfoWindows() {
        if (currentMarker) {
            currentMarker.setMap(null);
            currentMarker = null;
        }
        if (currentInfoWindow) {
            currentInfoWindow.close();
            currentInfoWindow = null;
        }
    }

    // 마커와 인포윈도우를 생성하고 지도에 표시하는 함수
    function createMarkerAndInfoWindow(school) {
        // 기존 마커/인포윈도우 제거
        clearMapMarkersAndInfoWindows(); 

        if (school.hsLat != null && school.hsLot != null && school.hsLat !== 0 && school.hsLot !== 0) {
            const position = new kakao.maps.LatLng(school.hsLat, school.hsLot);

            const marker = new kakao.maps.Marker({
                map: map,
                position: position,
                title: school.hsName
            });
            currentMarker = marker; // 현재 마커 저장

            const infoContent = `
                <div class="info-window-content">
                    <b>\${school.hsName}</b><br>
                    주소 : \${school.hsAddr || '정보없음'}<br>
                    전화 : \${school.hsTel || '정보없음'}
                </div>
            `;

            const infowindow = new kakao.maps.InfoWindow({
                content: infoContent,
                removable: true
            });
            currentInfoWindow = infowindow; // 현재 인포윈도우 저장

            // 마커 클릭 시 인포윈도우 열기
            kakao.maps.event.addListener(marker, 'click', () => {
                if (currentInfoWindow) currentInfoWindow.close(); // 기존 인포윈도우 닫기
                infowindow.open(map, marker);
                map.setCenter(position); // 클릭한 마커 위치로 지도 중심 이동
                map.setLevel(5); // 적절한 확대 레벨로 변경
            });
            
            // 지도 로드 시 바로 인포윈도우 열기
            infowindow.open(map, marker);

            // 지도 중심 이동 및 확대
            map.setCenter(position);
            map.setLevel(3); // 상세 위치이므로 더 확대
        } else {
            // 위치 정보가 없을 경우 처리
            alert("해당 고등학교의 위치 정보가 없습니다. 지도가 초기 위치로 설정됩니다.");
            map.setCenter(new kakao.maps.LatLng(36.3504, 127.3845)); // 대전 시청 기준
            map.setLevel(9);
        }
    }
    
 	// 모달 닫기 버튼 이벤트 리스너
    closeMapModalButton.addEventListener('click', () => {
        schoolMapModal.style.display = 'none';
        clearMapMarkersAndInfoWindows(); // 모달 닫을 때 마커 및 인포윈도우 제거
       // schoolSearchInModal.style.display = 'none'; // 검색창도 숨기기
        schoolListInMapModal.innerHTML = ''; // 검색 결과 목록도 초기화
    });

    // 모달 외부 클릭 시 닫기
    schoolMapModal.addEventListener('click', (e) => {
        if (e.target === schoolMapModal) {
            schoolMapModal.style.display = 'none';
            clearMapMarkersAndInfoWindows();
          //  schoolSearchInModal.style.display = 'none';
            schoolListInMapModal.innerHTML = '';
        }
    });

    // === 3. 지도 모달 제어 함수 ===
    function openSchoolMapModal(schoolToDisplay) {
        // 모달 표시
        schoolMapModal.style.display = 'flex';

        // Kakao Map SDK가 로드된 후에 지도 초기화
        kakao.maps.load(() => {
            // 지도 객체가 없다면 초기화 (모달이 처음 열릴 때만)
            if (!map) {
                const mapContainer = document.getElementById('map');
                const mapOption = {
                    center: new kakao.maps.LatLng(36.3504, 127.3845), // 초기 중심, 바로 아래서 변경될 예정
                    level: 9
                };
                map = new kakao.maps.Map(mapContainer, mapOption);
            } else {
                // 이미 초기화된 지도의 크기/레이아웃이 변경되었을 수 있으므로 재조정
                map.relayout(); 
            }

            // 전달받은 학교 정보로 마커 표시 및 인포윈도우 열기
            createMarkerAndInfoWindow(schoolToDisplay);
            
         	// 모달이 열릴 때 검색창이 보이도록 설정 (HTML에서 이미 설정되어 있지만, 명시적으로 다시 호출)
            schoolSearchInModal.style.display = 'block'; 
            if (map) {
                 map.relayout(); // 검색창이 보이면서 지도 크기가 변경될 수 있으므로 재조정
                 // 현재 마커가 있다면 다시 중앙으로 이동하여 지도와 검색창 레이아웃 변화에 대응
                 if (currentMarker) {
                    map.setCenter(currentMarker.getPosition());
                 }
            }
            
        });
    }

    // === 4. 모달 내에서 다른 학교 검색 기능 ===
    searchButtonInModal.addEventListener('click', searchHighSchoolInModal);
    schoolNameInputInModal.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            searchHighSchoolInModal();
        }
    });

    async function searchHighSchoolInModal() {
        const schoolName = schoolNameInputInModal.value.trim();
        if (!schoolName) {
            alert('검색할 고등학교 이름을 입력해주세요.');
            return;
        }

        loadingOverlay.style.display = 'flex'; // 로딩 오버레이 표시
        schoolListInMapModal.innerHTML = ''; // 기존 목록 초기화

        try {
            // 백엔드 API 호출 (기존 highSchoolMap.jsp에서 사용하던 검색 API와 동일)
            const response = await fetch(`/highSchool/api/highschools/search?name=\${encodeURIComponent(schoolName)}`);
            if (!response.ok) {
                throw new Error(`HTTP Error! Status: \${response.status}`);
            }
            const foundSchools = await response.json();
            console.log("모달 내 검색 결과:", foundSchools);

            if (foundSchools.length === 0) {
                schoolListInMapModal.innerHTML = '<li style="text-align: center; color: #6c757d;">검색된 고등학교가 없습니다.</li>';
                // 검색 결과가 없으면 지도를 초기 중심으로
                map.setCenter(new kakao.maps.LatLng(36.3504, 127.3845)); 
                map.setLevel(9);
            } else if (foundSchools.length === 1) {
                // 검색 결과가 하나면 바로 지도에 표시
                createMarkerAndInfoWindow(foundSchools[0]);
                map.setCenter(new kakao.maps.LatLng(foundSchools[0].hsLat, foundSchools[0].hsLot));
                map.setLevel(3);
            } else {
                // 검색 결과가 여러 개일 경우 목록으로 표시
                foundSchools.forEach(school => {
                    const listItem = document.createElement('li');
                    listItem.textContent = `\${school.hsName} (\${school.hsAddr || '주소 정보 없음'})`;
                    // 데이터를 dataset에 저장하여 클릭 시 활용
                    listItem.dataset.hsId = school.hsId;
                    listItem.dataset.hsLat = school.hsLat;
                    listItem.dataset.hsLot = school.hsLot;
                    listItem.dataset.hsName = school.hsName;
                    listItem.dataset.hsAddr = school.hsAddr;
                    listItem.dataset.hsTel = school.hsTel;

                    listItem.addEventListener('click', () => {
                        // 클릭된 학교 정보로 지도에 마커 표시
                        const selectedSchool = {
                            hsLat: parseFloat(listItem.dataset.hsLat),
                            hsLot: parseFloat(listItem.dataset.hsLot),
                            hsName: listItem.dataset.hsName,
                            hsAddr: listItem.dataset.hsAddr,
                            hsTel: listItem.dataset.hsTel
                        };
                        createMarkerAndInfoWindow(selectedSchool);
                        map.setCenter(new kakao.maps.LatLng(selectedSchool.hsLat, selectedSchool.hsLot));
                        map.setLevel(3);
                        // 목록을 비워 이전 검색 결과 숨기기 (선택 사항)
                        schoolListInMapModal.innerHTML = ''; 
                        schoolNameInputInModal.value = ''; // 검색창 초기화
                    });
                    schoolListInMapModal.appendChild(listItem);
                });
            }
        } catch (error) {
            console.error('모달 내 고등학교 검색 중 오류 발생:', error);
            alert('모달 내 고등학교 검색 중 오류가 발생했습니다. 콘솔을 확인해주세요.');
            schoolListInMapModal.innerHTML = '<li style="text-align: center; color: red;">검색 오류 발생.</li>';
        } finally {
            loadingOverlay.style.display = 'none'; // 로딩 오버레이 숨기기
        }
    }

 	
    // === 5. 고등학교 목록으로 버튼 이벤트 리스너 추가 ===
    goToListButton.addEventListener('click', () => {
        // 원하는 고등학교 목록 페이지의 URL로 변경해주세요.
        // 예시: window.location.href = '/highschool/list';
        // 예시: window.location.href = '/test/highschools';
        window.location.href = '/highSchool/list'; // 기존 지도 검색 페이지로 이동
    });
		
	</script>
	
</body>
</html>