let doughnutChart = null;

document.addEventListener('DOMContentLoaded', function() {

	/************************ 월별 사용자 통계 및 카드 데이터 *******************************/
	axios.get('/admin/chart/getAdminDashboard.do').then(res => {
		const chartData = res.data;
		console.log(chartData);

		// --- 카드 데이터 UI 업데이트 ---
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

		// --- 월별 사용자 통계 차트 생성 ---
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
					datasets: [{
						label: '총 활성 사용자',
						data: totalUsersData,
						fill: true,
						borderColor: 'rgb(114, 124, 245)',
						tension: 0.4,
						pointRadius: 4,
						pointBackgroundColor: 'rgb(114, 124, 245)',
						backgroundColor: function(context) {
							const { ctx, chartArea } = context.chart;
							if (!chartArea) { return null; }
							const gradient = ctx.createLinearGradient(0, chartArea.top, 0, chartArea.bottom);
							gradient.addColorStop(0, 'rgba(114, 124, 245, 0.5)');
							gradient.addColorStop(1, 'rgba(114, 124, 245, 0)');
							return gradient;
						}
					}, {
						label: '신규 유입',
						data: newUsersData,
						fill: true,
						borderColor: 'rgb(0, 200, 150)',
						backgroundColor: 'rgba(0, 200, 150, 0.2)',
						tension: 0.4,
						pointRadius: 4,
						pointBackgroundColor: 'rgb(0, 200, 150)'
					}, {
						label: '이탈자',
						data: secessionUsersData,
						fill: true,
						borderColor: 'rgb(255, 99, 132)',
						backgroundColor: 'rgba(255, 99, 132, 0.2)',
						tension: 0.4,
						pointRadius: 4,
						pointBackgroundColor: 'rgb(255, 99, 132)'
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					plugins: {
						title: { display: false },
						legend: { display: true, position: 'bottom', labels: { padding: 20 } }
					},
					scales: {
						y: { beginAtZero: true, grid: { color: 'rgba(0, 0, 0, 0.05)', drawBorder: false } },
						x: { grid: { display: false, drawBorder: false } }
					}
				}
			});
		}
	});

	/********************* 결제/구독 통계 *******************************/
	axios.get('/admin/las/payment/monthly-users').then(res => {
		const responseData = res.data;
		const labels = responseData.map(item => item.month + '월');
		const totalUsersData = responseData.map(item => item.totalUsers);
		const subscriberData = responseData.map(item => item.subscribers);
		const ctxPayment = document.getElementById('revenueChart')?.getContext('2d');
		if (ctxPayment) {
			new Chart(ctxPayment, {
				type: 'line',
				data: {
					labels: labels,
					datasets: [{
						label: '전체 사용자수',
						data: totalUsersData,
						borderColor: '#8e6ee4',
						backgroundColor: 'rgba(142, 110, 228, 0.2)',
						fill: true,
						tension: 0.4
					}, {

						label: '구독자 수',
						data: subscriberData,
						borderColor: '#00e396',
						backgroundColor: 'rgba(0, 227, 150, 0.2)',
						fill: true,
						tension: 0.4
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					plugins: {
						title: { display: false },
						legend: { position: 'top' },
						tooltip: {
							mode: 'index',
							intersect: false,
							callbacks: {
								label: function(context) {
									let label = context.dataset.label || '';
									if (label) { label += ': '; }
									if (context.parsed.y !== null) {
										label += context.parsed.y.toLocaleString() + '명';
									}
									return label;
								}
							}
						}
					},
					scales: {
						x: { grid: { display: false } },
						y: { beginAtZero: true }
					}
				}
			});
		}
	});

	/********************* 컨텐츠 이용 통계 (도넛) *******************************/
	const youthBtn = document.getElementById('youthBtn');
	const teenBtn = document.getElementById('teenBtn');

	function updateDoughnutChart(param) {
		const ctxDoughnut = document.getElementById('nestedDoughnutChart');
		if (!ctxDoughnut) return;
		if (doughnutChart) {
			doughnutChart.destroy();
		}

		axios.get(`/admin/chart/getContentsUseChart.do?param=${param}`).then(res => {
			const contentData = res.data;
			const doughnutLabels = contentData.map(item => item.title);
			const doughnutDataValues = contentData.map(item => item.cnt);
			const purplePalette = [
				'#F56F36', '#EBEFF2', '#0ACF97', '#FA5C7C', '#727CF5'
			];

			doughnutChart = new Chart(ctxDoughnut, {
				type: 'doughnut',
				data: {
					labels: doughnutLabels,
					datasets: [{
						label: '컨텐츠 이용 현황',
						data: doughnutDataValues,
						backgroundColor: purplePalette.slice(0, doughnutDataValues.length),
						borderWidth: 0,
						hoverOffset: 20
					}]
				},
				options: {
					responsive: true,
					maintainAspectRatio: false,
					cutout: '60%',
					layout: { padding: { top: 25, bottom: 25, left: 25, right: 25 } },
					plugins: {
						legend: {
							display: true,
							position: 'bottom',
							labels: { color: '#555', padding: 25, font: { size: 14 } }
						}
					}
				}
			});
		});
	}

	axios.get('/admin/las/payment/revenue-summary').then(res => {
		const avgPreviousRevenue = res.data.avgPreviousRevenue;
		const estimatedCurrentRevenue = res.data.estimatedCurrentRevenue;
		const spanAvgPreviousRevenue = document.getElementById('avgPreviousRevenue');
		const spanEstimatedCurrentRevenue = document.getElementById('estimatedCurrentRevenue');

		spanAvgPreviousRevenue.textContent = `${formatNumberWithCommasByAdminDashboard(avgPreviousRevenue)}원`;

		const valueWrapper = spanEstimatedCurrentRevenue.parentElement;

		const existingArrow = valueWrapper.querySelector('.arrow');
		if (existingArrow) {
			existingArrow.remove();
		}

		spanEstimatedCurrentRevenue.textContent = `${formatNumberWithCommasByAdminDashboard(estimatedCurrentRevenue)}원`;

		if (estimatedCurrentRevenue > avgPreviousRevenue) {
			const arrowSpan = document.createElement('span');
			arrowSpan.className = 'arrow increase';
			arrowSpan.innerHTML = '&#9650;'; // 상승 화살표
			// --- 수정: 숫자(span) 뒤에 화살표를 추가합니다. ---
			valueWrapper.appendChild(arrowSpan);

		} else if (estimatedCurrentRevenue < avgPreviousRevenue) {
			const arrowSpan = document.createElement('span');
			arrowSpan.className = 'arrow decrease';
			arrowSpan.innerHTML = '&#9660;'; // 하락 화살표
			// --- 수정: 숫자(span) 뒤에 화살표를 추가합니다. ---
			valueWrapper.appendChild(arrowSpan);
		}
	});


	updateDoughnutChart('teen');
	setActiveButton(teenBtn);


	youthBtn.addEventListener('click', function() {
		updateDoughnutChart('youth');
		setActiveButton(this);
	});

	teenBtn.addEventListener('click', function() {
		updateDoughnutChart('teen');
		setActiveButton(this);
	});

	
	
});

// 공통 함수
function formatNumberWithCommasByAdminDashboard(num) {
	if (isNaN(num)) { return "유효하지 않은 숫자입니다."; }
	return num.toLocaleString();
}

function setActiveButton(activeBtn) {
		const buttons = activeBtn.parentElement.querySelectorAll('.public-toggle-button');
		buttons.forEach(btn => btn.classList.remove('active'));
		activeBtn.classList.add('active');
	}