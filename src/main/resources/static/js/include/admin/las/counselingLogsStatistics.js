// 모든 초기화 함수를 묶어주는 함수
function initCounselingDashboard() {
    // 각 차트 함수를 호출하기 전에 해당 요소가 존재하는지 확인
    counselingTypeChart();
    counselingMethodChart();
    counselingTimeChart();
    counselorStatsTable();
    aiUsageChart();
}

// 상담 종류별 신청 수 (막대 차트)
function counselingTypeChart() {
    const ctx = document.getElementById('counselingTypeChart');
    if (!ctx) return; // 요소가 없으면 함수 종료

    // 실제로는 axios.get('/admin/las/counselingType.do') 와 같이 호출
    const serverData = [{
        type: '취업',
        count: 85
    }, {
        type: '학업',
        count: 62
    }, {
        type: '심리',
        count: 48
    }, {
        type: '진로',
        count: 75
    }, {
        type: '기타',
        count: 30
    }, ];

    const labels = serverData.map(item => item.type);
    const data = serverData.map(item => item.count);

    const config = {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '신청 수',
                data: data,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: '상담 종류별 신청 수'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        precision: 0
                    }
                }
            }
        }
    };
    new Chart(ctx, config);
}

// 상담 방법별 비율 (도넛 차트)
function counselingMethodChart() {
    const ctx = document.getElementById('counselingMethodChart');
    if (!ctx) return;

    // 실제로는 axios.get('/admin/las/counselingMethod.do') 와 같이 호출
    const serverData = [{
        method: '온라인',
        count: 150
    }, {
        method: '오프라인',
        count: 90
    }, {
        method: '전화',
        count: 60
    }];

    const labels = serverData.map(item => item.method);
    const data = serverData.map(item => item.count);

    const config = {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '상담 건수',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 205, 86, 0.8)'
                ],
                hoverOffset: 4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: '상담 방법별 비율'
                }
            }
        }
    };
    new Chart(ctx, config);
}

// 상담 시간대 통계 (선 차트)
function counselingTimeChart() {
    const ctx = document.getElementById('counselingTimeChart');
    if (!ctx) return;

    // 실제로는 axios.get('/admin/las/counselingTime.do') 와 같이 호출
    const serverData = [{
        time: '9:00',
        count: 5
    }, {
        time: '10:00',
        count: 12
    }, {
        time: '11:00',
        count: 18
    }, {
        time: '12:00',
        count: 15
    }, {
        time: '13:00',
        count: 22
    }, {
        time: '14:00',
        count: 20
    }, {
        time: '15:00',
        count: 17
    }, {
        time: '16:00',
        count: 10
    }];

    const labels = serverData.map(item => item.time);
    const data = serverData.map(item => item.count);

    const config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '상담 건수',
                data: data,
                borderColor: 'rgba(100, 192, 192, 1)',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: '상담 시간대 통계'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        precision: 0
                    }
                }
            }
        }
    };
    new Chart(ctx, config);
}

// 상담사별 처리 건수 및 만족도 (테이블)
function counselorStatsTable() {
    const tableBody = document.getElementById('counselor-stats-list');
    if (!tableBody) return;

    // 실제로는 axios.get('/admin/las/counselorStats.do') 와 같이 호출
    const serverData = [{
        name: '김유진',
        count: 120,
        satisfaction: 4.8
    }, {
        name: '박서준',
        count: 110,
        satisfaction: 4.5
    }, {
        name: '이하나',
        count: 95,
        satisfaction: 4.9
    }, {
        name: '최민혁',
        count: 88,
        satisfaction: 4.2
    }, ];

    tableBody.innerHTML = ''; // 기존 내용 삭제
    serverData.forEach(item => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.name}</td>
            <td>${item.count}건</td>
            <td>${'⭐'.repeat(Math.round(item.satisfaction))} (${item.satisfaction})</td>
        `;
        tableBody.appendChild(row);
    });
}


// 일별 AI 상담 사용량 (선 차트)
function aiUsageChart() {
    const ctx = document.getElementById('aiUsageChart');
    if (!ctx) return;

    // 실제로는 axios.get('/admin/las/aiUsage.do') 와 같이 호출
    const labels = ['2025-07-01', '2025-07-02', '2025-07-03', '2025-07-04', '2025-07-05'];
    const dataPoints = [250, 320, 280, 400, 350];

    const config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'AI 상담 사용 횟수',
                data: dataPoints,
                borderColor: 'rgba(153, 102, 255, 1)',
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                tension: 0.4,
                fill: true
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                },
                title: {
                    display: true,
                    text: '일별 AI 상담 사용량'
                },
                tooltip: {
                    callbacks: {
                        label: function(ctx) {
                            return ` ${ctx.parsed.y}회`;
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
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) {
                            return value + '회';
                        }
                    },
                    grid: {
                        borderDash: [3],
                        color: '#e5e5e5'
                    }
                }
            }
        }
    };
    new Chart(ctx, config);
}

initCounselingDashboard();s