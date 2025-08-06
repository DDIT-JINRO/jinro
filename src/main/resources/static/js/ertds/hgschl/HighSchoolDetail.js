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

	//PDF 기능
	const previewBtn = document.getElementById('pdf-preview-btn');
	    const downloadBtn = document.getElementById('pdf-download-btn');

	    // PDF 내용을 생성하는 함수
	    function generatePdfContent() {
	        // 현재 페이지의 데이터 가져오기
	        const schoolName = document.querySelector('.school-summary-box h2').textContent;
	        const summaryItems = document.querySelectorAll('.summary-info-list li');
	        const detailRows = document.querySelectorAll('.details-table tr');
	        const deptItems = document.querySelectorAll('.dept-list li');
	        
	        let summaryHtml = '';
	        summaryItems.forEach(item => {
	            summaryHtml += `<li>${item.innerHTML}</li>`;
	        });

	        let detailsHtml = '';
	        detailRows.forEach(row => {
	            // innerHTML을 사용하여 링크(<a>) 태그도 그대로 복사
	            detailsHtml += `<tr><th>${row.cells[0].textContent}</th><td>${row.cells[1].innerHTML}</td></tr>`;
	        });
	        
	        let deptsHtml = '';
	        if(deptItems.length > 0) {
	            deptsHtml += '<div class="section"><h2 class="section-title">학과 정보</h2><ul class="dept-list">';
	            deptItems.forEach(item => {
	                deptsHtml += `<li>${item.textContent}</li>`;
	            });
	            deptsHtml += '</ul></div>';
	        }

	        const htmlContent = `
	            <div class="pdf-container">
	                <h1 class="school-name">${schoolName}</h1>
	                <div class="section">
	                    <h2 class="section-title">요약 정보</h2>
	                    <ul class="summary-list">${summaryHtml}</ul>
	                </div>
	                <div class="section">
	                    <h2 class="section-title">상세 정보</h2>
	                    <table class="details-table">${detailsHtml}</table>
	                </div>
	                ${deptsHtml}
	            </div>
	        `;

	        const cssContent = `
	            body { font-family: 'Malgun Gothic', sans-serif; }
	            .pdf-container { padding: 40px; }
	            .school-name { font-size: 28px; font-weight: bold; margin-bottom: 25px; color: #212529; }
	            .section { margin-bottom: 30px; }
	            .section-title { font-size: 20px; font-weight: 600; color: #4f46e5; border-bottom: 2px solid #dee2e6; padding-bottom: 8px; margin-bottom: 15px; }
	            .summary-list, .dept-list { list-style: none; padding: 0; }
	            .summary-list li { margin-bottom: 10px; font-size: 15px; }
	            .details-table { width: 100%; border-collapse: collapse; font-size: 14px; }
	            .details-table th, .details-table td { padding: 12px 8px; text-align: left; border-bottom: 1px solid #f1f3f5; }
	            .details-table th { width: 150px; background-color: #f8f9fa; color: #495057; }
	            .details-table a { text-decoration: none; color: #1971c2; }
	            .dept-list { display: flex; flex-wrap: wrap; gap: 10px; }
	            .dept-list li { background-color: #f1f3f5; padding: 5px 12px; border-radius: 15px; font-size: 13px; }
	        `;
	        
	        return { htmlContent, cssContent };
	    }

	    // '미리보기' 버튼 클릭 이벤트
	    if(previewBtn) {
	        previewBtn.addEventListener('click', () => {
	            const { htmlContent, cssContent } = generatePdfContent();
	            const formData = new FormData();
	            formData.append("htmlContent", htmlContent);
	            formData.append("cssContent", cssContent);

	            fetch("/pdf/preview", { method: "POST", body: formData })
	            .then(response => {
	                if (!response.ok) throw new Error("미리보기 요청 실패");
	                return response.blob();
	            })
	            .then(blob => {
	                const url = window.URL.createObjectURL(blob);
	                window.open(url, '_blank');
	            }).catch(error => console.error('PDF 미리보기 오류:', error));
	        });
	    }

	    // '다운로드' 버튼 클릭 이벤트
	    if(downloadBtn) {
	        downloadBtn.addEventListener('click', () => {
	            const { htmlContent, cssContent } = generatePdfContent();
	            const form = document.createElement('form');
	            form.method = 'POST';
	            form.action = '/pdf/download';
	            form.target = '_blank';

	            const htmlInput = document.createElement('input');
	            htmlInput.type = 'hidden';
	            htmlInput.name = 'htmlContent';
	            htmlInput.value = htmlContent;

	            const cssInput = document.createElement('input');
	            cssInput.type = 'hidden';
	            cssInput.name = 'cssContent';
	            cssInput.value = cssContent;

	            form.appendChild(htmlInput);
	            form.appendChild(cssInput);
	            document.body.appendChild(form);
	            form.submit();
	            document.body.removeChild(form);
	        });
	    }
	});