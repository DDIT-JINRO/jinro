	var calendarInstance = null; // 전역 변수로 선언
	var selectedDate = null;

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

			// 헤더 툴바의 버튼 위치 조정
			headerToolbar: {
				left: 'prev', // 이전 달 버튼을 왼쪽 끝으로
				center: 'title', // 제목(날짜)을 중앙으로
				right: 'next' // '오늘' 버튼과 다음 달 버튼을 오른쪽 끝으로
			},
			height : parent,
			dateClick: function(info) {
				const prevSelected = document.querySelector('.fc-day.selected');
				if (prevSelected) {
					prevSelected.classList.remove('selected');
				}
				info.dayEl.classList.add('selected');

				let selectedDate = info.dateStr;
				document.getElementById("selectedDateText").textContent =`${selectedDate} 상담 리스트`;
			},
			// 월이 변경될 때마다 호출되는 이벤트
			datesSet: function(info) {
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
	

	// 비동기 로딩된 JSP에서도 바로 실행
	initCalendar();
