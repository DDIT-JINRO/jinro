// 모든 초기화 함수를 묶어주는 함수
function initMemberDashboard() {
	memberJoinDeleteChart();
	loginLogoutRatioChart();
	loadPenaltyHistory();
	loadCounselingHistory();
	recommendationChart();
}

// 회원가입 / 탈퇴 이력 (막대 차트)
function memberJoinDeleteChart() {
	const ctx = document.getElementById('memberJoinDeleteChart');
	if (!ctx) return;

	// 예시 데이터 (실제로는 서버에서 가져와야 함)
	const serverData = {
		labels: ['2025-07-01', '2025-07-02', '2025-07-03', '2025-07-04', '2025-07-05'],
		join: [50, 65, 72, 80, 75],
		delete: [5, 8, 4, 10, 7]
	};

	new Chart(ctx, {
		type: 'bar',
		data: {
			labels: serverData.labels,
			datasets: [{
				label: '가입',
				data: serverData.join,
				backgroundColor: 'rgba(54, 162, 235, 0.6)'
			}, {
				label: '탈퇴',
				data: serverData.delete,
				backgroundColor: 'rgba(255, 99, 132, 0.6)'
			}]
		},
		options: {
			responsive: true,
			plugins: {
				title: {
					display: false
				}
			},
			scales: {
				x: {
					stacked: true
				},
				y: {
					stacked: true,
					beginAtZero: true
				}
			}
		}
	});
}

// 기간별 로그인 / 로그아웃 비율 (도넛 차트)
function loginLogoutRatioChart() {
	const ctx = document.getElementById('loginLogoutRatioChart');
	if (!ctx) return;

	// 예시 데이터 (실제로는 서버에서 가져와야 함)
	const loginData = {
		login: 580,
		logout: 220
	};

	new Chart(ctx, {
		type: 'doughnut',
		data: {
			labels: ['로그인', '로그아웃'],
			datasets: [{
				label: '비율',
				data: [loginData.login, loginData.logout],
				backgroundColor: ['rgba(75, 192, 192, 0.8)', 'rgba(255, 159, 64, 0.8)'],
				hoverOffset: 4
			}]
		},
		options: {
			responsive: true,
			plugins: {
				title: {
					display: false
				}
			}
		}
	});
}

// 경고 / 정지 이력 테이블 데이터 로드
function loadPenaltyHistory() {
	const tbody = document.getElementById('penalty-history-list');
	if (!tbody) return;

	// 예시 데이터 (실제로는 서버에서 가져와야 함)
	const serverData = [{
		memId: 'user01',
		type: '경고',
		reason: '부적절한 게시글 작성',
		date: '2025-07-04'
	}, {
		memId: 'user02',
		type: '정지',
		reason: '욕설 및 비방',
		date: '2025-07-02'
	}, {
		memId: 'user03',
		type: '경고',
		reason: '광고성 댓글 작성',
		date: '2025-07-01'
	}, ];

	tbody.innerHTML = '';
	serverData.forEach(item => {
		const row = document.createElement('tr');
		row.innerHTML = `
            <td>${item.memId}</td>
            <td>${item.type}</td>
            <td>${item.reason}</td>
            <td>${item.date}</td>
        `;
		tbody.appendChild(row);
	});
}

// 상담 신청 및 완료 이력 테이블 데이터 로드
function loadCounselingHistory() {
	const tbody = document.getElementById('counseling-history-list');
	if (!tbody) return;

	// 예시 데이터 (실제로는 서버에서 가져와야 함)
	const serverData = [{
		memId: 'user01',
		type: '진로 상담',
		date: '2025-07-01',
		status: '완료'
	}, {
		memId: 'user04',
		type: '심리 상담',
		date: '2025-06-29',
		status: '대기중'
	}, {
		memId: 'user05',
		type: '학업 상담',
		date: '2025-06-28',
		status: '완료'
	}, ];

	tbody.innerHTML = '';
	serverData.forEach(item => {
		const row = document.createElement('tr');
		row.innerHTML = `
            <td>${item.memId}</td>
            <td>${item.type}</td>
            <td>${item.date}</td>
            <td>${item.status}</td>
        `;
		tbody.appendChild(row);
	});
}

// 사용자별 추천 직업 통계 (대분류) (수평 막대 차트)
function recommendationChart() {
	const ctx = document.getElementById('recommendationChart');
	if (!ctx) return;

	// 예시 데이터 (실제로는 서버에서 가져와야 함)
	const serverData = [{
		jobGroup: 'IT',
		count: 250
	}, {
		jobGroup: '경영/사무',
		count: 180
	}, {
		jobGroup: '디자인',
		count: 120
	}, {
		jobGroup: '영업',
		count: 90
	}, {
		jobGroup: '금융',
		count: 70
	}, ];

	const labels = serverData.map(item => item.jobGroup);
	const data = serverData.map(item => item.count);

	new Chart(ctx, {
		type: 'bar',
		data: {
			labels: labels,
			datasets: [{
				label: '추천 수',
				data: data,
				backgroundColor: 'rgba(153, 102, 255, 0.6)'
			}]
		},
		options: {
			indexAxis: 'y',
			responsive: true,
			plugins: {
				legend: {
					display: false
				},
				title: {
					display: false
				}
			},
			scales: {
				x: {
					beginAtZero: true,
					ticks: {
						precision: 0
					}
				}
			}
		}
	});
}
initMemberDashboard();