// 전역 변수 (함수에서 공통으로 사용하는 변수)
let calendar;
let selectedDate = null;
let selectedTime = null;
let selectedCounselorId; // 이 변수에는 DOM 로드 후 값이 할당될 것입니다.


document.addEventListener('DOMContentLoaded', function() {
    // 이 안의 코드는 HTML 문서가 완전히 로드된 후에 실행됩니다.

    // 1. HTML 요소에 접근해서 변수에 할당
    let counselorSelect = document.getElementById('counselorSelect');
    selectedCounselorId = counselorSelect.value;

    var calendarEl = document.getElementById('calendar');
    calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'ko',
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        
        // 날짜를 클릭했을 때 호출되는 이벤트
        dateClick: function(info) {
            selectedDate = info.dateStr;
            document.getElementById('selectedDateText').textContent = selectedDate + "의 예약 가능한 시간";
            fetchAvailableTimes(selectedCounselorId, selectedDate);
        },
        
        // 월이 변경될 때마다 호출되는 이벤트
        datesSet: function(info) {
            let currentDate = new Date();
            let year = currentDate.getFullYear();
            let month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
            let day = currentDate.getDate().toString().padStart(2, '0');
            
            fetchAvailableTimes(selectedCounselorId, `${year}-${month}-${day}`);
        }
    });
    calendar.render();

    // 2. HTML 요소에 이벤트 리스너 추가
    counselorSelect.addEventListener('change', function() {
        selectedCounselorId = this.value;
        calendar.today();
    });

    // 이 코드를 DOMContentLoaded 안으로 옮겼습니다.
    document.getElementById('timeSlotButtons').addEventListener('click', function(event) {
        if (event.target && event.target.matches('.time-slot-btn.available')) {
            document.querySelectorAll('.time-slot-btn.available.selected').forEach(btn => {
                btn.classList.remove('selected');
            });
            event.target.classList.add('selected');
            selectedTime = event.target.dataset.time;
        }
    });

    // 이 코드를 DOMContentLoaded 안으로 옮겼습니다.
    document.getElementById('nextBtn').addEventListener('click', function() {
        if (selectedDate && selectedTime) {
            alert("상담사 : "+selectedCounselorId+"선택된 날짜: " + selectedDate + ", 시간: " + selectedTime);
			handleNextButtonClick(selectedCounselorId,selectedDate,selectedTime);
        } else {
            alert("날짜와 시간을 선택해주세요.");
        }
    });

    // 기타 함수들도 필요에 따라 여기에 넣을 수 있습니다.
});


// 백엔드 API를 호출하여 예약 가능 시간 목록을 가져오는 함수
function fetchAvailableTimes(counselId, date) {
    axios.get('/cnslt/resve/availableTimes', {
        params: {
            counsel: counselId,
            counselReqDatetime: date
        }
    })
    .then(function (response) {
        renderTimeSlots(response.data);
    })
    .catch(function (error) {
        console.error("예약 가능 시간을 불러오는 데 실패했습니다.", error);
        alert("예약 정보를 불러올 수 없습니다. 다시 시도해 주세요.");
    });
}

// 예약 가능한 시간 슬롯을 화면에 그리는 함수
function renderTimeSlots(availableTimes) {
    let timeSlotButtonsHtml = '';
    const timeSlotButtonsContainer = document.getElementById('timeSlotButtons');
    timeSlotButtonsContainer.innerHTML = '';

    if (availableTimes.length > 0) {
        availableTimes.forEach(function(time) {
            const button = document.createElement('button');
            button.className = 'time-slot-btn available';
            button.dataset.time = time;
            button.textContent = time;
            timeSlotButtonsContainer.appendChild(button);
        });
    } else {
        timeSlotButtonsContainer.innerHTML = '<div>예약 가능한 시간이 없습니다.</div>';
    }
}

// counseling-reservation-page.js 파일
// 임시 락
// '다음' 버튼 클릭 시 호출될 함수 
async function handleNextButtonClick(counsel,date,time) {

    if (!memId|| memId== 'anonymousUser') {
        alert('로그인해주세요.');
        return;
    }
	
    if (!date || !time || !counsel) {
        alert('모든 필수 정보를 선택해주세요.');
        return;
    }
	//상담방법
	const counselMethodElement = document.getElementById('counselMethodSelect');
	const counselMethodValue = counselMethodElement.value;
	//상담목적
	const counselCategoryElement = document.getElementById('counselCategorySelect');
	const counselCategoryValue = counselCategoryElement.value;
	

    // 날짜와 시간을 합쳐서 ISO 8601 형식으로 만듭니다.
    const combinedDateTime = `${date} ${time}`;
	
	
    try {
        const response = await axios.post('/cnslt/resve/holdAndRedirect', {
            counsel: counsel,
            counselReqDatetime: combinedDateTime,
			counselCategory: counselCategoryValue,
			counselMethod: counselMethodValue
        });
		
		if (response.data && response.data.redirectUrl) {
		    window.location.href = response.data.redirectUrl;
		}


    } catch (error) {
		// 실패 시 로직
        if (error.response) {
            const data = error.response.data;
            if (data.errorMessage) {
                alert(data.errorMessage);
            }
            if (data.redirectUrl) {
                window.location.href = data.redirectUrl;
            }
        } else {
            console.error('락 획득 중 오류 발생:', error);
            alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
    }
}

// 최종 예약 로직 (예시)
// counseling-detail-form-page.js 파일 (다음 페이지)

/*async function submitReservationForm() {
    const form = document.getElementById('reservationForm');
    const formData = new FormData(form);
    
    // 폼 데이터와 URL 파라미터에서 정보들을 취합합니다.
    const counselorId = formData.get('counselorId');
    const selectedDate = formData.get('counselReqDate');
    const selectedTime = formData.get('counselReqTime');
    const memId = formData.get('memId');

    // 날짜와 시간을 합칩니다.
    const combinedDateTime = `${selectedDate} ${selectedTime}`;

    // VO 객체에 맞게 데이터 객체 생성
    const counselingVO = {
        counsel: counselorId,
        counselReqDatetime: combinedDateTime,
        memId: memId,
        counselCategory: formData.get('counselCategory'),
        counselMethod: formData.get('counselMethod'),
        counselTitle: formData.get('counselTitle'),
        counselDescription: formData.get('counselDescription'),
        counselUrl: formData.get('counselUrl'),
        counselReqReason: formData.get('counselReqReason')
    };

    try {
        // [수정] 최종 /reserve API 호출
        const response = await axios.post('/counseling/reservation/reserve', counselingVO);
        
        if (response.status === 200) {
            alert('상담 예약이 완료되었습니다!');
            window.location.href = '/counseling/reservation/success';
        }

    } catch (error) {
        if (error.response && error.response.status === 410) {
            // 락이 만료된 경우
            alert('예약 시간이 초과되었습니다. 다시 예약해주세요.');
        } else {
            console.error('예약 처리 중 오류 발생:', error);
            alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
    }
}*/