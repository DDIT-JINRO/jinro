const ctxUser = document.getElementById('lineChart');
const ctxPayment = document.getElementById('revenueChart').getContext('2d');

/************************ 월별 사용자 통계 *******************************/

if (ctxUser) {
	new Chart(ctxUser, {
		type: 'line',
		data: {
			labels: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
			datasets: [{
				label: '',
				data: [1250, 1450, 1380, 1720, 1600, 1950, 2100, 2000, 2050, 900, 750, 2500],
				fill: true,
				borderColor: 'rgb(114, 124, 245)',
				tension: 0.3,
				// --- 여기부터 수정 ---
				backgroundColor: function(context) {
					const chart = context.chart;
					const { ctx, chartArea } = chart;
					
					if (!chartArea) {
						return null;
					}

					let y_start = chart.scales.y.getPixelForValue(Math.max(...context.dataset.data));
					let y_end = chart.scales.y.getPixelForValue(Math.min(...context.dataset.data));

					const gradient = ctx.createLinearGradient(0, y_start, 0, chartArea.bottom);

					gradient.addColorStop(0, 'rgba(114, 124, 245, 0.6)');
					gradient.addColorStop(0.5, 'rgba(114, 124, 245, 0.15)');
					gradient.addColorStop(1, 'rgba(255, 255, 255, 0.05)');

					return gradient;
				}
				// --- 여기까지 수정 ---
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				title: {
					display: false
				},
				legend: {
					display: false
				}
			},
			scales: {
				y: {
					beginAtZero: true
				}
			}
		}
	});
}

/********************* 결제/구독 통계 *******************************/

const data = {
	labels: ['01', '02', '03', '04', '05', '06', '07'], // 월
	datasets: [
		{
			label: '결제 / 구독량',
			data: [18000, 17500, 21000, 25000, 30000, 35000, 28000], // 결제 / 구독량
			borderColor: '#8e6ee4',
			backgroundColor: 'rgba(142, 110, 228, 0.2)',
			fill: false,
			tension: 0.4
		},
		{
			label: '결제 / 구독 취소량',
			data: [17000, 16800, 19000, 23000, 28000, 32000, 30000], // 결제 구독 취소량
			borderColor: '#00e396',
			backgroundColor: 'rgba(0, 227, 150, 0.2)',
			fill: false,
			tension: 0.4
		}
	]
};

new Chart(ctxPayment, {
	type: 'bar',
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

