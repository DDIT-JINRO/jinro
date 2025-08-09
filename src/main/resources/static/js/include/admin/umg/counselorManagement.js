/**
 * 
 */
ctxCnsMngMonthChart = document.getElementById('sparklineChart');

new Chart(ctxCnsMngMonthChart, {
	type: 'bar',
	data: {
		labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
		datasets: [{
			data: [15, 20, 5, 65, 55, 22, 68, 95, 85, 25, 20],
			backgroundColor: 'rgba(139, 148, 242, 0.8)',
			borderRadius: 2,
		}]
	},
	options: {
		maintainAspectRatio: false,
		scales: {
			x: {
				display: false
			},
			y: {
				display: false
			}
		},
		plugins: {
			legend: {
				display: false
			},
			tooltip: {
				enabled: false
			}
		}
	}
});