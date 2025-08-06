/**
 * 
 */
// 전역 변수로 마커와 인포윈도우를 관리
let map = null;
let currentMarker = null;
let currentInfoWindow = null;

// 지도 마커와 인포윈도우를 초기화하는 함수
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

// 마커와 정보창을 생성하는 함수
function createMarkerAndInfoWindow(schoolData) {
    clearMapMarkersAndInfoWindows();

    if (schoolData.hsLat && schoolData.hsLot) {
        const position = new kakao.maps.LatLng(schoolData.hsLat, schoolData.hsLot);
        const marker = new kakao.maps.Marker({
            map: map,
            position: position,
            title: schoolData.hsName
        });
        currentMarker = marker;

        const infoContent = `
            <div class="info-window-content">
                <b>${schoolData.hsName}</b><br>
                주소 : ${schoolData.hsAddr || '정보없음'}<br>
                전화 : ${schoolData.hsTel || '정보없음'}
            </div>
        `;
        const infowindow = new kakao.maps.InfoWindow({
            content: infoContent,
            removable: true
        });
        currentInfoWindow = infowindow;

        kakao.maps.event.addListener(marker, 'click', () => {
            infowindow.open(map, marker);
        });

        infowindow.open(map, marker);
        map.setCenter(position);
        map.setLevel(3);
    } else {
        alert("해당 학교의 위치 정보가 없습니다.");
        map.setCenter(new kakao.maps.LatLng(36.3504, 127.3845)); // 대전 시청 기준
        map.setLevel(9);
    }
}

// 지도 모달을 열고 초기화하는 메인 함수
function openSchoolMapModal(schoolData) {
    const schoolMapModal = document.getElementById('schoolMapModal');
    schoolMapModal.style.display = 'flex';

    kakao.maps.load(() => {
        if (!map) {
            const mapContainer = document.getElementById('map');
            const mapOption = {
                center: new kakao.maps.LatLng(36.3504, 127.3845),
                level: 9
            };
            map = new kakao.maps.Map(mapContainer, mapOption);
        }
        map.relayout();
        createMarkerAndInfoWindow(schoolData);
    });
}