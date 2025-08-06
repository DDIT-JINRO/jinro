/**
 * 
 */
// 페이지가 모두 로드되면 실행
document.addEventListener('DOMContentLoaded', () => {
    
    // 1. JSP의 data-* 속성에서 데이터 읽어오기
    const container = document.getElementById('highSchoolDetailContainer');
    const highSchoolData = {
        hsName: container.dataset.hsName,
        hsAddr: container.dataset.hsAddr,
        hsTel: container.dataset.hsTel,
        hsLat: parseFloat(container.dataset.hsLat), // 숫자로 변환
        hsLot: parseFloat(container.dataset.hsLot)  // 숫자로 변환
    };

    // 2. 필요한 DOM 요소들 찾기
    const schoolAddressElement = document.getElementById('schoolAddress');
    const closeMapModalButton = document.getElementById('closeMapModalButton');
    const schoolMapModal = document.getElementById('schoolMapModal');

    // 3. 이벤트 리스너 등록 (위치 정보가 있을 때만)
    if (highSchoolData.hsLat && highSchoolData.hsLot) {
        // 주소 클릭 시 지도 모달 열기
        schoolAddressElement.addEventListener('click', () => {
            // mapUtil.js의 함수 호출
            openSchoolMapModal(highSchoolData);
        });
    }

    // 모달 닫기 버튼
    closeMapModalButton.addEventListener('click', () => {
        schoolMapModal.style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    schoolMapModal.addEventListener('click', (e) => {
        if (e.target === schoolMapModal) {
            schoolMapModal.style.display = 'none';
        }
    });
});