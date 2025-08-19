var calendarInstance = null; // 전역 변수로 선언
(function() {

	var selectedDate = null;


	document.getElementById("btn-search").addEventListener("click", function() {
		const selectedDate = document.querySelector('.fc-day.selected')?.dataset.date || new Date().toISOString().slice(0, 10);
		selectCounselSchedules(selectedDate);
	});

	function initCalendar() {
		var calendarEl = document.getElementById('calendar');
		if (!calendarEl) {
			console.warn("캘린더 엘리먼트를 찾을 수 없습니다.");
			return;
		}

		// 이미 캘린더 인스턴스가 존재하면 중단
		if (calendarInstance) {
			console.log("이미 캘린더가 초기화되어 있습니다.");
			return;
		}

		calendarInstance = new FullCalendar.Calendar(calendarEl, {
			locale: 'ko',
			initialView: 'dayGridMonth',
			displayEventTime: false,
			// 헤더 툴바의 버튼 위치 조정
			headerToolbar: {
				left: 'prev', // 이전 달 버튼을 왼쪽 끝으로
				center: 'title', // 제목(날짜)을 중앙으로
				right: 'next' // '오늘' 버튼과 다음 달 버튼을 오른쪽 끝으로
			},
			height: parent,
			dateClick: function(info) {
				const prevSelected = document.querySelector('.fc-day.selected');
				if (prevSelected) {
					prevSelected.classList.remove('selected');
				}
				info.dayEl.classList.add('selected');

				let selectedDate = info.dateStr;
				// 모든 상담 스케줄		
				document.getElementById("selectedDateText").textContent = `${selectedDate} 상담 리스트`;
				selectCounselSchedules(selectedDate);
			},
			// 월이 변경될 때마다 호출되는 이벤트
			datesSet: function(info) {
				const colorPalette = [
				    '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF',
				    '#FF9F40', '#E7E9ED', '#A0A2A6', '#C751A5', '#7F3C8D'
				];
				// 각 상담사에게 할당된 색상을 저장할 객체
				const assignedColors = {};
				let colorIndex = 0;
				
				const title = info.view.title;
				    const match = title.match(/(\d+)년 (\d+)월/);
				    if (!match) {
				        console.error("캘린더 제목에서 날짜를 파싱할 수 없습니다:", title);
				        return;
				    }
				    const viewyear = match[1];
				    const viewmonth = match[2].padStart(2, '0');
				    const currentViewMonth = `${viewyear}-${viewmonth}`;

				    // 캘린더에 표시할 상담사별 월별 건수를 비동기로 불러옵니다.
				    axios.get('/api/cnsld/counselingLd/monthly-counts.do', {
				        params: {
				            counselReqDatetime: `${currentViewMonth}-01`
				        }
				    })
				    .then(response => {
				        const data = response.data;
				        const events = [];
						
				        if (data && data.length > 0) {
				            data.forEach(item => {
								
								const counselorName = item.counselName
								
								if (!assignedColors[counselorName]) {
								    assignedColors[counselorName] = colorPalette[colorIndex % colorPalette.length];
								    colorIndex++;
								}
				                events.push({
				                    title: `${item.counselName}: ${item.count}건`,
				                    start: item.counselReqDatetime,
				                    color: assignedColors[counselorName],
				                    textColor: 'black',
									display: 'block' // 배경색으로 표시
				                });
				            });
				        }
				        calendarInstance.setOption('events', events);
						
				    })
				    .catch(error => {
				        console.error("월별 상담 데이터 로드 실패:", error);
				        calendarInstance.setOption('events', []);
				    });
				
				let currentDate = new Date();
				let year = currentDate.getFullYear();
				let month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
				let day = currentDate.getDate().toString().padStart(2, '0');

				let todayStr = `${year}-${month}-${day}`;

				// 캘린더가 렌더링될 때, 오늘 날짜로 예약 가능 시간을 바로 불러오도록 수정
				// 선택된 날짜가 없을 경우에만 실행
				if (!selectedDate) {
					selectedDate = todayStr;

					document.getElementById('selectedDateText').textContent = selectedDate + "의 상담리스트";

					// 모든 상담 스케줄	
					selectCounselSchedules(selectedDate);

					// 캘린더 초기 로드 시 오늘 날짜에 시각적 효과 추가
					const todayEl = document.querySelector(`.fc-day[data-date="${todayStr}"]`);
					if (todayEl) {
						todayEl.classList.add('selected');
					}
				}
			}
		});
		calendarInstance.render();
	}

	// 전역 변수로 페이지 정보와 항목 수를 관리합니다.
	let currentPage = 1;
	const pageSize = 5;

	/**
	 * 특정 날짜의 상담 스케줄을 비동기로 불러와 테이블에 표시하고 페이징을 처리합니다.
	 * @param {string} date - 상담 스케줄을 조회할 날짜 (YYYY-MM-DD 형식).
	 * @param {number} page - 현재 페이지 번호.
	 */
	function selectCounselSchedules(counselReqDatetime, page = 1) {
		const keyword = document.getElementById("keyword").value;

		// API 호출 시 페이지 정보와 날짜를 파라미터로 보냅니다.
		axios.get('/api/cnsld/counselScheduleList.do', {
			params: { // 여기에 쿼리 파라미터로 보낼 데이터를 객체로 묶어줍니다.
				keyword: keyword,
				counselReqDatetime: counselReqDatetime,
				currentPage: page,
				size: pageSize
			}
		})
			.then(({ data }) => { // ES6 구조분해 할당으로 response.data를 바로 data 변수로 받습니다.
				// `fetchCounselingLog`처럼 백엔드 응답이 `total`과 `content`를 포함한다고 가정
				const totalCount = data.total || 0;
				const schedules = data.content || [];

				// 현재 페이지 번호를 전역 변수에 업데이트
				currentPage = page;

				let container = document.getElementById('bookedSchedulesContainer');
				let countEl = document.getElementById('schedule-count');
				container.innerHTML = ''; // 기존 내용 초기화

				if (schedules.length === 0) {
					container.innerHTML = `<tr><td colspan="5" style="text-align:center;">데이터가 없습니다</td></tr>`;
					if (countEl) countEl.textContent = 0;
				} else {
					// `fetchCounselingLog`처럼 시작 번호를 계산
					// 번호는 시작 번호부터 1씩 증가
					let cnt = (page-1) * pageSize +1;

					let rows = schedules.map((item, idx) => {
						const counselReqDate = new Date(item.counselReqDatetime);
						const formattedDate = `${counselReqDate.getFullYear()}-${(counselReqDate.getMonth() + 1).toString().padStart(2, '0')}-${counselReqDate.getDate().toString().padStart(2, '0')} ${counselReqDate.getHours().toString().padStart(2, '0')}:${counselReqDate.getMinutes().toString().padStart(2, '0')}`;


						return `
	                    <tr onclick="counselDetail(${item.counselId})">
	                        <td>${cnt++}</td>
	                        <td>${item.memName}</td>
	                        <td>${formattedDate}</td>
	                        <td>${item.counselName || '미정'}</td>
	                        <td>${item.counselStatusStr}</td>
	                    </tr>
	                `;
					}).join('');

					container.innerHTML = rows;
					if (countEl) countEl.textContent = totalCount;
				}
				data.counselReqDatetime = counselReqDatetime;
				// 페이징 버튼 렌더링 함수 호출

				renderPagination(data);
			})
			.catch(function(error) {
				console.error("상담 데이터 로드 실패:", error);
				let container = document.getElementById('bookedSchedulesContainer');
				container.innerHTML = `<tr><td colspan="5" style="text-align:center; color: red;">데이터 로드에 실패했습니다. 잠시 후 다시 시도해주세요.</td></tr>`;

				// 오류 발생 시 총 갯수 0으로 표시
				document.getElementById('schedule-count').textContent = 0;
				// 페이징 버튼도 초기화
				renderPagination(0, pageSize, 1);
			});

	}
	window.selectCounselSchedules = selectCounselSchedules;
	// 비동기 로딩된 JSP에서도 바로 실행
	setTimeout(function() {
		initCalendar();
	}, 100);


})();
	function renderPagination({ startPage, endPage, currentPage, totalPages, counselReqDatetime }) {
		let html = `<a href="#" data-page="${startPage - 1}" onclick="selectCounselSchedules('${counselReqDatetime}',${startPage - 1})" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

		for (let p = startPage; p <= endPage; p++) {
			html += `<a href="#" onclick="selectCounselSchedules('${counselReqDatetime}',${p})" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
		}

		html += `<a href="#" data-page="${endPage + 1}" onclick="selectCounselSchedules('${counselReqDatetime}',${endPage + 1})" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

		const footer = document.querySelector('.panel-footer.pagination');
		if (footer) footer.innerHTML = html;
	}

// 상담 상세 정보 조회 함수
function counselDetail(counselId) {
	if (!counselId) return;

	axios.get('/api/cnsld/counselDetail.do', { params: { counselId } })
		.then(response => {
			const data = response.data;

			// 1. 상담사 정보
			document.querySelector('.info-value-counselName').textContent = data.counselName || "-";

			// 2. 상담 신청 기본정보
			document.querySelector('.info-value-memName').textContent = data.memName || "-"; // 이름
			document.querySelector('.info-value-memBirth').textContent = calculateAge(new Date(data.memBirth)) || "-"; // 나이
			document.querySelector('.info-value-memGenStr').textContent = getGenText(data.memGen) || "-"; // 성별
			document.querySelector('.info-value-memEmail').textContent = data.memEmail || "-"; // 이메일
			document.querySelector('.info-value-memPhoneNumber').textContent = data.memPhoneNumber || "-"; // 연락처


			// 3. 상담 신청 정보
			document.querySelector('.info-value-counselCategoryStr').textContent = data.counselCategoryStr || "-"; // 상담 분야
			document.querySelector('.info-value-counselMethodStr').textContent = data.counselMethodStr || "-"; // 상담 방법

			const counselDate = new Date(data.counselReqDatetime);
			document.querySelector('.info-value-counselReqDate').textContent =
				`${counselDate.getFullYear()}. ${(counselDate.getMonth() + 1).toString().padStart(2, '0')}. ${counselDate.getDate().toString().padStart(2, '0')}`;

			document.querySelector('.info-value-counselReqtime').textContent =
				`${counselDate.getHours().toString().padStart(2, '0')}:${counselDate.getMinutes().toString().padStart(2, '0')}`;
			document.querySelector('.info-value-counselStatusStr').textContent = data.counselStatusStr || "-"; // 상태


			// 4. 신청동기
			//신청동기 타이틀
			const descriptionTitle = document.querySelector(".panel-section-title.description")
			const counselDescription = document.querySelector('.info-label-counselDescription');
			if (data.crContent != null) {
				descriptionTitle.textContent = '상담리뷰';
				counselDescription.textContent = data.crContent || "리뷰 정보가 없습니다.";
			} else {
				descriptionTitle.textContent = '신청동기';
				counselDescription.textContent = data.counselDescription || "신청동기 정보가 없습니다.";
			}
		})
		.catch(error => {
			console.error('상담 상세 정보 조회 실패:', error);
			alert('상담 정보를 불러오는 데 실패했습니다.');
		});
}


// 나이 계산 함수 (기존 코드에 추가)
function calculateAge(birthDate) {
	if (!birthDate) return "-";
	const today = new Date();
	const birth = new Date(birthDate);
	let age = today.getFullYear() - birth.getFullYear();
	const m = today.getMonth() - birth.getMonth();
	if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) {
		age--;
	}
	return age;
}

// 성별 코드 변환 함수 (기존 코드에 추가)
function getGenText(genCode) {
	switch (genCode) {
		case 'G11001': return '남';
		case 'G11002': return '여';
		default: return '-';
	}
}