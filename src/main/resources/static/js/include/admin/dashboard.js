/************************ 데이터 가져오기 & 차트 생성 *******************************/


document.addEventListener('DOMContentLoaded', function() {

	const buttonGroups = document.querySelectorAll('.btn-group');

	buttonGroups.forEach(group => {
		const buttons = group.querySelectorAll('.public-toggle-button');

		buttons.forEach(button => {
			button.addEventListener('click', function() {
				
				buttons.forEach(btn => btn.classList.remove('active'));
				this.classList.add('active');
			});
		});

		if (buttons.length > 0) {
			buttons[0].classList.add('active');
		}
	});

	axios.get('/admin/chart/getAdminDashboard.do').then(res => {
		const chartData = res.data;

		console.log(chartData);

		const liveUserCount = document.getElementById('liveUserCount');
		const monthUserCount = document.getElementById('monthUserCount');
		const allUserCount = document.getElementById('allUserCount');

		liveUserCount.innerHTML = formatNumberWithCommasByAdminDashboard(chartData.liveUserCount);
		monthUserCount.innerHTML = formatNumberWithCommasByAdminDashboard(chartData.monthUserCount);
		allUserCount.innerHTML = formatNumberWithCommasByAdminDashboard(chartData.allUserCount);

		const monthUserRate = document.getElementById('monthUserRate');
		const allUserRate = document.getElementById('allUserRate');

		if (chartData.monthUserCountStatus === "increase") {
			monthUserRate.innerHTML = `&#9650;&nbsp;${chartData.monthUserCountRate}%`;
			monthUserRate.classList.add('public-span-increase');
			monthUserRate.classList.remove('public-span-decrease');
		} else if (chartData.monthUserCountStatus === "decrease") {
			monthUserRate.innerHTML = `&#9660;&nbsp;${chartData.monthUserCountRate}%`;
			monthUserRate.classList.add('public-span-decrease');
			monthUserRate.classList.remove('public-span-increase');
		} else {
			monthUserRate.innerHTML = `${chartData.monthUserCountRate}%`;
			monthUserRate.classList.remove('public-span-increase', 'public-span-decrease');
		}

		if (chartData.allUserCountStatus === "increase") {
			allUserRate.innerHTML = `&#9650;&nbsp;${chartData.allUserCountRate}%`;
			allUserRate.classList.add('public-span-increase');
			allUserRate.classList.remove('public-span-decrease');
		} else if (chartData.allUserCountStatus === "decrease") {
			allUserRate.innerHTML = `&#9660;&nbsp;${chartData.allUserCountRate}%`;
			allUserRate.classList.add('public-span-decrease');
			allUserRate.classList.remove('public-span-increase');
		} else {
			allUserRate.innerHTML = `${chartData.allUserCountRate}%`;
			allUserRate.classList.remove('public-span-increase', 'public-span-decrease');
		}

		
		const totalUsersData = chartData.monthlyChart.map(item => item.CUMULATIVE_USER_COUNT);
		const newUsersData = chartData.newUserChart.map(item => item.MONTHLY_USER_COUNT);
		const secessionUsersData = chartData.secessionChart.map(item => item.MONTHLY_DELETION_COUNT);

		
		const monthLabels = chartData.monthlyChart.map(item => item.MONTH);

		const ctxUser = document.getElementById('lineChart');
		if (ctxUser) {
		    new Chart(ctxUser, {
		        type: 'line',
		        data: {
		            labels: monthLabels,
		            datasets: [
		                {
		                    label: '총 활성 사용자',
		                    data: totalUsersData,
		                    fill: true,
		                    borderColor: 'rgb(114, 124, 245)',
							
		                    tension: 0.4,
		                    pointRadius: 4,
		                    pointBackgroundColor: 'rgb(114, 124, 245)',
		                    // --- 이 부분을 그라데이션 함수로 다시 수정 ---
		                    backgroundColor: function(context) {
		                        const chart = context.chart;
		                        const { ctx, chartArea } = chart;
		                        if (!chartArea) {
		                            return null;
		                        }
		                        const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom);
		                        gradient.addColorStop(0, 'rgba(114, 124, 245, 0.5)'); // 위쪽 색상
		                        gradient.addColorStop(1, 'rgba(114, 124, 245, 0)');  // 아래쪽 색상 (투명)
		                        return gradient;
		                    }
		                    // --- 여기까지 수정 ---
		                },
		                {
		                    label: '신규 유입',
		                    data: newUsersData,
		                    fill: true,
		                    borderColor: 'rgb(0, 200, 150)',
		                    backgroundColor: 'rgba(0, 200, 150, 0.2)',
		                    tension: 0.4,
		                    pointRadius: 4,
		                    pointBackgroundColor: 'rgb(0, 200, 150)'
		                },
		                {
		                    label: '이탈자',
		                    data: secessionUsersData,
		                    fill: true,
		                    borderColor: 'rgb(255, 99, 132)',
		                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
		                    tension: 0.4,
		                    pointRadius: 4,
		                    pointBackgroundColor: 'rgb(255, 99, 132)'
		                }
		            ]
		        },
		        options: {
		            responsive: true,
		            maintainAspectRatio: false,
		            plugins: {
		                title: { display: false },
		                legend: {
		                    display: true,
		                    position: 'bottom',
		                    labels: { padding: 20 }
		                }
		            },
		            scales: {
		                y: {
		                    beginAtZero: true,
		                    grid: { color: 'rgba(0, 0, 0, 0.05)', drawBorder: false }
		                },
		                x: {
		                    grid: { display: false, drawBorder: false }
		                }
		            }
		        }
		    });
		} // 월별 이용자 통계 종료 괄호
	}) // 카운트카드 데이터, 월별 이용자 통계 axios 종료 괄호
	
	axios.get('/admin/chart/getContentsUseChart.do').then(res => {
		console.log("contentChart : ", res);
	}) // 컨텐츠 이용 통계 axois 종료 괄호
	
})

function formatNumberWithCommasByAdminDashboard(num) {
	if (isNaN(num)) {
		return "유효하지 않은 숫자입니다.";
	}
	return num.toLocaleString();
}

// 아래는 결제/구독 통계이므로 위쪽과 별개로 실행됩니다.
const ctxPayment = document.getElementById('revenueChart').getContext('2d');
const data = {
	labels: ['01', '02', '03', '04', '05', '06', '07'],
	datasets: [
		{
			label: '결제 / 구독량',
			data: [18000, 17500, 21000, 25000, 30000, 35000, 28000],
			borderColor: '#8e6ee4',
			backgroundColor: 'rgba(142, 110, 228, 0.2)',
			fill: false,
			tension: 0.4
		},
		{
			label: '결제 / 구독 취소량',
			data: [17000, 16800, 19000, 23000, 28000, 32000, 30000],
			borderColor: '#00e396',
			backgroundColor: 'rgba(0, 227, 150, 0.2)',
			fill: false,
			tension: 0.4
		}
	]
};

new Chart(ctxPayment, {
	type: 'line',
	data: data,
	options: {
		responsive: true,
		maintainAspectRatio: false,
		plugins: {
			title: {
				display: false,
			},
			legend: {
				position: 'top',
			},
			tooltip: {
				mode: 'index',
				intersect: false,
				callbacks: {
					label: function(context) {
						let label = context.dataset.label || '';
						if (label) {
							label += ': ';
						}
						if (context.parsed.y !== null) {
							label += new Intl.NumberFormat('ko-KR', { style: 'currency', currency: 'KRW' }).format(context.parsed.y);
						}
						return label;
					}
				}
			}
		},
		scales: {
			x: {
				grid: {
					display: false
				}
			},
			y: {
				beginAtZero: false,
				ticks: {
					maxTicksLimit: 5,
					callback: function(value) {
						if (value >= 1000) {
							return value / 1000 + 'k';
						}
						return value;
					}
				}
			}
		}
	}
});

const ctxDoughnut = document.getElementById('nestedDoughnutChart');
if (ctxDoughnut) {
    new Chart(ctxDoughnut, {
        type: 'doughnut',
        data: {
            labels: ['월드컵', '로드맵', '심리검사', '모의면접', 'AI 피드백'],
            datasets: [{
                label: '컨텐츠 이용 현황',
                data: [320, 210, 450, 180, 290],
                backgroundColor: [
                    'rgba(128, 90, 213, 0.8)',
                    'rgba(157, 128, 228, 0.8)',
                    'rgba(106, 90, 205, 0.8)',
                    'rgba(183, 161, 237, 0.8)',
                    'rgba(85, 60, 180, 0.8)'
                ],
                borderWidth: 0,
                hoverOffset: 20
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '60%',
            
            // --- 여기에 layout.padding 추가 ---
            layout: {
                padding: {
                    top: 25,
                    bottom: 25,
                    left: 25,
                    right: 25
                }
            },
            // --- 여기까지 ---

            plugins: {
                legend: {
                    display: true,
                    position: 'bottom',
                    labels: {
                        color: '#555',
                        padding: 25,
                        font: { size: 14 }
                    }
                }
            }
        }
    });
}