document.addEventListener('DOMContentLoaded', () => {
	// 이 스크립트에서 사용할 지도와 마커 변수
	let map;
	let currentMarker = null;
	let currentInfoWindow = null;

	const container = document.getElementById('highSchoolDetailContainer');
	if (!container) return;

	const highSchoolData = {
		hsName: container.dataset.hsName,
		hsAddr: container.dataset.hsAddr,
		hsTel: container.dataset.hsTel,
		hsLat: parseFloat(container.dataset.hsLat),
		hsLot: parseFloat(container.dataset.hsLot)
	};

	const mapContainer = document.getElementById('map');

	// 2. 페이지 로드 시, 지도를 기본 위치(학교 위치)로 초기화
	if (mapContainer) {
		kakao.maps.load(() => {
			let initialPosition, initialLevel;
			if (highSchoolData.hsLat && highSchoolData.hsLot) {
				initialPosition = new kakao.maps.LatLng(highSchoolData.hsLat, highSchoolData.hsLot);
				initialLevel = 3;
			} else {
				initialPosition = new kakao.maps.LatLng(36.3504, 127.3845); // 대전 시청
				initialLevel = 7;
				mapContainer.innerHTML = '<div style="text-align:center; padding: 120px 0; color: #6c757d;">위치 정보가 없습니다.</div>';
			}

			const mapOption = { center: initialPosition, level: initialLevel };
			map = new kakao.maps.Map(mapContainer, mapOption);

			if (highSchoolData.hsLat && highSchoolData.hsLot) {
				currentMarker = new kakao.maps.Marker({ position: initialPosition });
				currentMarker.setMap(map);
				const iwContent = `<div style="padding:5px; font-size:14px;"><b>${highSchoolData.hsName}</b></div>`;
				currentInfoWindow = new kakao.maps.InfoWindow({ content: iwContent });
				currentInfoWindow.open(map, currentMarker);
			}
		});
	}

	//PDF 기능
	const previewBtn = document.getElementById('pdf-preview-btn');
	const downloadBtn = document.getElementById('pdf-download-btn');

	// PDF 내용을 생성하는 함수
	function generatePdfContent() {
		// 현재 페이지의 데이터 가져오기
		const schoolName = document.querySelector('.detail-header__title').textContent;
		const summaryItems = document.querySelectorAll('.detail-header__meta-item');
		const detailRows = document.querySelectorAll('.info-table tr');
		const deptItems = document.querySelectorAll('.tag-list__item');

		let summaryHtml = '';
		summaryItems.forEach(item => {
			summaryHtml += `<li>${item.innerHTML}</li>`;
		});

		let detailsHtml = '';
		detailRows.forEach(row => {
			detailsHtml += `<tr><th>${row.cells[0].textContent}</th><td>${row.cells[1].innerHTML}</td></tr>`;
		});

		let deptsHtml = '';
		if (deptItems.length > 0) {
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
	if (previewBtn) {
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
	if (downloadBtn) {
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

	//화면그대로 미리보기
	const screenPreviewBtn = document.getElementById('pdf-screen-preview-btn');
	// 화면 상단 채널 섹션(캡처에서만 제외할 대상)
	const channel = document.querySelector('.channel');
	const actions = document.querySelector('.page-actions');

	function beforeCapture() {
		channel?.setAttribute('data-html2canvas-ignore', 'true');
		actions?.setAttribute('data-html2canvas-ignore', 'true');
		channel?.classList.add('h2c-hide');   // ← 레이아웃 제거
		actions?.classList.add('h2c-hide');   // ← 레이아웃 제거
	}
	function afterCapture() {
		channel?.removeAttribute('data-html2canvas-ignore');
		actions?.removeAttribute('data-html2canvas-ignore');
		channel?.classList.remove('h2c-hide');
		actions?.classList.remove('h2c-hide');
	}


	if (screenPreviewBtn) {

		screenPreviewBtn.addEventListener('click', async () => {
			const root = document.getElementById('highSchoolDetailContainer');

			console.log('root.scrollWidth:', root.scrollWidth);
			console.log('root.offsetWidth:', root.offsetWidth);
			const schoolName = document.querySelector('.detail-header__title')?.textContent?.trim() || 'document';

			if (document.fonts?.ready) { try { await document.fonts.ready; } catch (_) { } }

			const swap = swapMapToStatic(root, true); // 지도 치환
			await waitImageLoaded(swap.placeholder);

			await new Promise(r => requestAnimationFrame(() => requestAnimationFrame(r)));

			beforeCapture();

			await new Promise(r => requestAnimationFrame(r));

			try {
				const opt = makePdfOptions(root, `${schoolName}.pdf`);
				const worker = html2pdf().set(opt).from(root).toPdf();
				const pdf = await worker.get('pdf');
				const url = pdf.output('bloburl');
				const win = window.open(url, '_blank');
				if (!win) alert('팝업이 차단되었습니다. 팝업 허용 후 다시 시도하세요.');
			} finally {
				afterCapture();
				// 원복
				if (swap.placeholder && swap.backup) swap.placeholder.replaceWith(swap.backup);
			}
		});
	}



	//화면그대로 pdf 다운로드
	const screenBtn = document.getElementById('pdf-screen-btn');

	if (screenBtn) {
		screenBtn.addEventListener('click', async () => {
			const root = document.getElementById('highSchoolDetailContainer');
			const schoolName = document.querySelector('.detail-header__title')?.textContent?.trim() || 'document';

			if (document.fonts?.ready) { try { await document.fonts.ready; } catch (_) { } }

			const swap = swapMapToStatic(root, true);

			await new Promise(r => requestAnimationFrame(() => requestAnimationFrame(r)));

			beforeCapture();

			await new Promise(r => requestAnimationFrame(r));

			try {
				const opt = makePdfOptions(root, `${schoolName}.pdf`);
				await html2pdf().set(opt).from(root).save();
			} finally {
				afterCapture();
				if (swap.placeholder && swap.backup) swap.placeholder.replaceWith(swap.backup);
			}
		});
	}



	// 1) 공통: 지도 치환/원복
	function swapMapToStatic(root, toStatic = true) {
		const mapEl = document.getElementById('map');
		if (!mapEl) return { backup: null, placeholder: null };

		const lat = parseFloat(root.dataset.hsLat);
		const lon = parseFloat(root.dataset.hsLot);
		if (!toStatic || Number.isNaN(lat) || Number.isNaN(lon)) {
			return { backup: null, placeholder: null };
		}

		const backup = mapEl.cloneNode(true);
		const name = (root.dataset.hsName || '').trim();
		const shortLabel = name.length > 2 ? name.substring(0, 2) : name; // ← 2글자 이하로 제한

		const img = document.createElement('img');
		img.setAttribute('crossorigin', 'anonymous'); // 동일 출처라 상관없지만 안전하게
		img.style.cssText = 'width:100%;height:350px;object-fit:cover;border:1px solid #dee2e6;border-radius:8px;';
		img.src = `/ertds/hgschl/static-map?lat=${lat}&lon=${lon}&w=800&h=350&level=3`;


		mapEl.replaceWith(img);
		return { backup, placeholder: img };
	}

	// 캡처 전에 이미지 로딩 대기 (빈칸 방지)
	function waitImageLoaded(img) {
		return new Promise((res) => {
			if (!img || img.complete) return res();
			img.addEventListener('load', res, { once: true });
			img.addEventListener('error', res, { once: true });
		});
	}

	function makePdfOptions(root, filename) {
		// 스크롤 0으로 (상단 여백 방지)
		window.scrollTo(0, 0);

		const rect = root.getBoundingClientRect();
		const x = rect.left + window.scrollX;
		const y = rect.top + window.scrollY;
		const width = root.scrollWidth;
		const height = root.scrollHeight;

		return {
			margin: [10, 20, 10, 20],                         // 좌우 잘림 방지용 여백
			filename,
			image: { type: 'jpeg', quality: 0.95 },
			html2canvas: {
				scale: 2,
				useCORS: true,
				allowTaint: false,
				logging: false,
				backgroundColor: '#ffffff',
				scrollX: 0,
				scrollY: 0,
				width: root.scrollWidth,
				height: root.scrollHeight
			},
			jsPDF: { unit: 'mm', format: [300, 400], orientation: 'portrait' },
			pagebreak: { mode: ['avoid-all', 'css', 'legacy'] }
		};
	}

	// 2) 공통: html2pdf 실행 래퍼
	async function buildPdfBlobUrl(root, filename) {
		// 스크롤 0 보정
		window.scrollTo(0, 0);
		await new Promise(r => requestAnimationFrame(r));

		// 루트의 실제 좌표/크기 계산
		const rect = root.getBoundingClientRect();
		const x = rect.left + window.scrollX;
		const y = rect.top + window.scrollY;
		const width = root.scrollWidth;
		const height = root.scrollHeight;

		const opt = {
			margin: [10, 20, 10, 20],
			filename,
			image: { type: 'jpeg', quality: 0.95 },
			html2canvas: {
				scale: 2,
				useCORS: true,
				allowTaint: false,
				logging: false,
				// 스크롤 오프셋 제거 (큰 상단 여백 방지)
				scrollX: 0,
				scrollY: 0,
				width: root.scrollWidth,
				height: root.scrollHeight
			},
			jsPDF: { unit: 'mm', format: [300, 400], orientation: 'portrait' },
			pagebreak: { mode: ['avoid-all', 'css', 'legacy'] }
		};
		const worker = html2pdf().set(opt).from(root).toPdf();
		const pdf = await worker.get('pdf');
		return pdf.output('bloburl');
	}


});