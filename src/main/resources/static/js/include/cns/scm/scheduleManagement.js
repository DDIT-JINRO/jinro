// FullCalendar가 이미 로드되어 있는지 확인
if (typeof FullCalendar !== 'undefined') {

	let calendarInstance = null; // 전역 변수로 선언
	let selectedDate = null;
	
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
			height: '90%',
	        dateClick: function (info) {
				const prevSelected = document.querySelector('.fc-day.selected');
				if (prevSelected) {
					prevSelected.classList.remove('selected');
				}
				info.dayEl.classList.add('selected');
				
	            let selectedDate = info.dateStr;
	            document.getElementById("selectedDateText").textContent =
	                `${selectedDate} 상담 리스트`;
	            selectCounselingSchedules(selectedDate);
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
					
					selectCounselingSchedules(selectedDate);

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

    // 상담 데이터 불러오기
    function selectCounselingSchedules(date) {
        axios.get('/api/cns/bookedScheduleList.do', {
            params: { counselReqDatetime: date }
        })
        .then(function (response) {
            let data = response.data;
			counselingData = data; 
            let container = document.getElementById('bookedSchedulesContainer');
            container.innerHTML = '';

            if (!data || data.length === 0) {
                container.innerHTML = `<tr><td colspan="6" style="text-align:center;">데이터가 없습니다</td></tr>`;
                document.getElementById('notice-count').textContent = 0;
                return;
            }

            let rows = data.map((item, idx) => `
                <tr onclick="counselDetail(${item.counselId})">
                    <td>${idx + 1}</td>
                    <td>${item.memName}</td>
                    <td>${calculateAge(new Date(item.memBirth))}</td>
                    <td>${item.memEmail}</td>
                    <td>${item.memPhoneNumber}</td>
                    <td>${item.counselStatusStr}</td>
                </tr>
            `).join('');

            container.innerHTML = rows;
            document.getElementById('notice-count').textContent = data.length;
        })
        .catch(function (error) {
            console.error("상담 데이터 로드 실패:", error);
        });
    }

    // 비동기 로딩된 JSP에서도 바로 실행
    initCalendar();

} else {
    console.error("FullCalendar 라이브러리가 로드되지 않았습니다.");
}

// 나이 반환
function calculateAge(birthDate) {
  // 생년월일을 '년', '월', '일'로 분리합니다.
  var birthYear = birthDate.getFullYear();
  var birthMonth = birthDate.getMonth();
  var birthDay = birthDate.getDate();

  // 현재 날짜를 가져옵니다.
  var currentDate = new Date();
  var currentYear = currentDate.getFullYear();
  var currentMonth = currentDate.getMonth();
  var currentDay = currentDate.getDate();

  // 만 나이를 계산합니다.
  var age = currentYear - birthYear;

  // 현재 월과 생일의 월을 비교합니다.
  if (currentMonth < birthMonth) {
    age--;
  }
  // 현재 월과 생일의 월이 같은 경우, 현재 일과 생일의 일을 비교합니다.
  else if (currentMonth === birthMonth && currentDay < birthDay) {
    age--;
  }
console.log("age"+age);
  return age;
}

function renderCounselDetail(counselData) {
    if (!counselData) {
        console.error("렌더링할 상담 데이터가 없습니다.");
        // 데이터가 없을 때 UI를 초기화하는 로직 추가
        document.getElementById('counselCategory').textContent = '';
        document.getElementById('counselMethod').textContent = '';
        document.getElementById('counselReqDate').textContent = '';
        document.getElementById('counselReqTime').textContent = '';
        document.getElementById('counselStatus').textContent = '';
        document.getElementById('counselStatusSelect').value = '';
        document.getElementById('counselDescription').value = '';
        document.getElementById('memName').textContent = '';
        document.getElementById('memAge').textContent = '';
        document.getElementById('memEmail').textContent = '';
        document.getElementById('memPhoneNumber').textContent = '';
        return;
    }

    // 상담 정보 채우기
    document.getElementById('counselCategory').textContent = counselData.counselCategoryStr || '';
    document.getElementById('counselMethod').textContent = counselData.counselMethodStr || '';
    
    // 날짜 및 시간 처리
    if (counselData.counselReqDatetime) {
        const datetime = new Date(counselData.counselReqDatetime);
        const dateStr = datetime.toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }).replace(/\. /g, '-').slice(0, -1);
        const timeStr = datetime.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' });
        
        document.getElementById('counselReqDate').textContent = dateStr;
        document.getElementById('counselReqTime').textContent = timeStr;
    } else {
        document.getElementById('counselReqDate').textContent = '';
        document.getElementById('counselReqTime').textContent = '';
    }

    // 상태 정보 채우기
    document.getElementById('counselStatus').textContent = counselData.counselStatusStr || '';
    document.getElementById('counselStatusSelect').value = counselData.counselStatus;
    
    // 신청 동기 채우기
    document.getElementById('counselDescription').value = counselData.counselDescription || '';

    // 인적 사항 채우기
    document.getElementById('memName').textContent = counselData.memName || '';
    document.getElementById('memAge').textContent = calculateAge(new Date(counselData.memBirth));
    document.getElementById('memEmail').textContent = counselData.memEmail || '';
    document.getElementById('memPhoneNumber').textContent = counselData.memPhoneNumber || '';
}


function counselDetail(counselId) {
    // ID를 이용해 데이터 배열에서 해당 객체를 찾음
    const selectedItem = counselingData.find(item => item.counselId === counselId);
	if (selectedItem) {
		renderCounselDetail(selectedItem)
	} else {
		console.error("해당 ID의 데이터를 찾을 수 없습니다:", counselId);
		// 데이터가 없을 경우 상세 패널 초기화
		renderCounselDetail(null);
	}
}