document.addEventListener('DOMContentLoaded', () => {
    // 이 스크립트에서 사용할 지도와 마커 변수
    let map;
    let currentMarker = null;
    let currentInfoWindow = null;

    // 1. JSP의 data-* 속성에서 데이터 읽어오기
    const container = document.getElementById('highSchoolDetailContainer');
    const highSchoolData = {
        hsName: container.dataset.hsName,
        hsAddr: container.dataset.hsAddr,
        hsTel: container.dataset.hsTel,
        hsLat: parseFloat(container.dataset.hsLat), // 숫자로 변환
        hsLot: parseFloat(container.dataset.hsLot)  // 숫자로 변환
    };
    
    const mapContainer = document.getElementById('map');
    const schoolAddressElement = document.getElementById('schoolAddress');

    // 2. 페이지 로드 시, 지도를 기본 위치(대전 시청)로 초기화
    kakao.maps.load(() => {
        const initialPosition = new kakao.maps.LatLng(36.3504, 127.3845);
        const mapOption = { 
            center: initialPosition,
            level: 7
        };
        map = new kakao.maps.Map(mapContainer, mapOption);
    });

    // 3. 주소에 '클릭' 이벤트 리스너 추가
    if (schoolAddressElement) {
        schoolAddressElement.addEventListener('click', () => {
            if (highSchoolData.hsLat && highSchoolData.hsLot) {
                showSchoolOnMap(highSchoolData);
            } else {
                alert('이 학교의 위치 정보가 없습니다.');
            }
        });
    }

    // 지도에 특정 학교를 표시하는 함수
    function showSchoolOnMap(school) {
        if (currentMarker) currentMarker.setMap(null);
        if (currentInfoWindow) currentInfoWindow.close();

        const schoolPosition = new kakao.maps.LatLng(school.hsLat, school.hsLot);
        map.panTo(schoolPosition);
        
        currentMarker = new kakao.maps.Marker({ position: schoolPosition });
        currentMarker.setMap(map);

        const iwContent = `<div style="padding:5px; font-size:14px;"><b>${school.hsName}</b></div>`;
        currentInfoWindow = new kakao.maps.InfoWindow({ content: iwContent });
        currentInfoWindow.open(map, currentMarker);
        
        map.setLevel(3, { animate: true });
    }
});