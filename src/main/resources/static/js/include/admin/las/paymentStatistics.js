function init() {
	// Axios 기본 설정
    axios.defaults.baseURL = '/admin/las/payment';
    
    // 초기 데이터 로드
    loadSubscriberSummary();
    loadRevenueChart();
    loadProductChart();
    loadSubscriberChart();
    loadAiServiceChart();
    
    // 버튼 이벤트 리스너 등록
    setupButtonEvents();
}

// 구독자 요약 정보 로드 (카드 1, 2번)
async function loadSubscriberSummary() {
    try {
        const response = await axios.get('/subscriber-summary');
        const data = response.data;
        
		console.log("data", data);
		
        // 총 구독자 수
        document.getElementById('totalSubscribersCount').textContent = data.totalSubscribers?.toLocaleString() || '0';
        document.getElementById('totalSubscribersRate').textContent = `${data.totalSubscribersStatus === 'increase' ? '+' : data.totalSubscribersStatus === 'decrease' ? '-' : ''}${data.totalSubscribersRate || 0}%`;
        
        // 오늘 구독자 수 (newSubscribersToday로 수정)
        document.getElementById('todaySubscribersCount').textContent = data.newSubscribersToday?.toLocaleString() || '0';
        document.getElementById('todaySubscribersRate').textContent = `${data.newSubscribersTodayStatus === 'increase' ? '+' : data.newSubscribersTodayStatus === 'decrease' ? '-' : ''}${data.newSubscribersTodayRate || 0}%`;
        
        // 상태에 따른 스타일 적용
        updateRateStyleByStatus('totalSubscribersRate', data.totalSubscribersStatus);
        updateRateStyleByStatus('todaySubscribersRate', data.newSubscribersTodayStatus);
        
    } catch (error) {
        console.error('구독자 요약 정보 로드 실패:', error);
        // 에러 시 기본값 표시
        document.getElementById('totalSubscribersCount').textContent = '0';
        document.getElementById('todaySubscribersCount').textContent = '0';
        document.getElementById('totalSubscribersRate').textContent = '0%';
        document.getElementById('todaySubscribersRate').textContent = '0%';
    }
}

// 구독 결제 매출 차트 로드 (왼쪽 큰 div 1)
async function loadRevenueChart(params = {}) {
    try {
        const response = await axios.get('/revenue-stats', { params });
        const data = response.data;
        
        console.log("Revenue chart data:", data); // 디버깅용
        
        // 데이터 변환 (백엔드 응답 구조에 맞게 수정)
        const labels = data.map(item => item.dt); // dt 사용
        const revenues = data.map(item => item.revenue || 0);
        
        const ctx = document.getElementById('revenueChartCanvas').getContext('2d');
        
        // 기존 차트 파괴
        const existingChart = Chart.getChart('revenueChartCanvas');
        if (existingChart) {
            existingChart.destroy();
        }
        
        const revenueChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: '매출 (원)',
                    data: revenues,
                    borderColor: '#727cf5',
                    backgroundColor: 'rgba(114, 124, 245, 0.1)',
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return `매출: ${context.raw.toLocaleString()}원`;
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return value.toLocaleString() + '원';
                            }
                        }
                    }
                }
            }
        });
        
    } catch (error) {
        console.error('매출 차트 로드 실패:', error);
        createErrorChart('revenueChartCanvas', '매출 데이터를 불러올 수 없습니다.');
    }
}

// 상품별 인기 통계 차트 로드 (왼쪽 큰 div 2)
async function loadProductChart(params = {}) {
    try {
        const response = await axios.get('/product-popularity', { params });
        const data = response.data;
        
        console.log("Product chart data:", data); // 디버깅용
        
        // 상품별로 데이터 그룹화 (백엔드 응답 구조에 맞게 수정)
        const products = {};
        data.forEach(item => {
            const productName = item.subName; // subName 사용
            const period = item.dt; // dt 사용
            const count = item.count || 0;
            
            if (!products[productName]) {
                products[productName] = [];
            }
            products[productName].push({
                period: period,
                count: count
            });
        });
        
        // 라벨 추출 (모든 기간)
        const allPeriods = [...new Set(data.map(item => item.dt))].sort();
        
        // 데이터셋 생성
        const datasets = [];
        const colors = ['#2DCF97', '#727cf5', '#FFC75A', '#FF6B6B', '#9C88FF'];
        let colorIndex = 0;
        
        Object.keys(products).forEach(productName => {
            const productData = allPeriods.map(period => {
                const found = products[productName].find(item => item.period === period);
                return found ? found.count : 0;
            });
            
            datasets.push({
                label: productName,
                data: productData,
                backgroundColor: colors[colorIndex % colors.length],
                borderRadius: 4
            });
            colorIndex++;
        });
        
        const ctx = document.getElementById('productChartCanvas').getContext('2d');
        
        // 기존 차트 파괴
        const existingChart = Chart.getChart('productChartCanvas');
        if (existingChart) {
            existingChart.destroy();
        }
        
        const productChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: allPeriods,
                datasets: datasets
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
        
    } catch (error) {
        console.error('상품 인기 차트 로드 실패:', error);
        createErrorChart('productChartCanvas', '상품 통계 데이터를 불러올 수 없습니다.');
    }
}

// 구독자 수 차트 로드 (오른쪽 큰 div 1)
async function loadSubscriberChart(params = {}) {
    try {
        const response = await axios.get('/subscriber-stats', { params });
        const data = response.data;
        
        console.log("Subscriber chart data:", data); // 디버깅용
        
        // 데이터 변환 (백엔드 응답 구조에 맞게 수정)
        const labels = data.map(item => item.dt); // dt 사용
        const subscribers = data.map(item => item.count || 0); // count 사용
        
        const ctx = document.getElementById('subscriberChartCanvas').getContext('2d');
        
        // 기존 차트 파괴
        const existingChart = Chart.getChart('subscriberChartCanvas');
        if (existingChart) {
            existingChart.destroy();
        }
        
        const subscriberChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: '구독자 수',
                    data: subscribers,
                    borderColor: '#2DCF97',
                    backgroundColor: 'rgba(45, 207, 151, 0.1)',
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
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
        
    } catch (error) {
        console.error('구독자 차트 로드 실패:', error);
        createErrorChart('subscriberChartCanvas', '구독자 데이터를 불러올 수 없습니다.');
    }
}

// AI 서비스 이용내역 차트 로드 (오른쪽 큰 div 2)
async function loadAiServiceChart(params = {}) {
    try {
        const response = await axios.get('/ai-service-usage', { params });
        const data = response.data;
        
        console.log("AI Service chart data:", data); // 디버깅용
        
        // 백엔드 응답 구조에 맞게 데이터 변환
        let totalResume = 0;
        let totalCover = 0;
        let totalMock = 0;
        
        data.forEach(item => {
            totalResume += parseInt(item.resumeCnt) || 0;
            totalCover += parseInt(item.coverCnt) || 0;
            totalMock += parseInt(item.mockCnt) || 0;
        });
        
        const labels = ['이력서 AI', '자기소개서 AI', '모의면접 AI'];
        const usageCounts = [totalResume, totalCover, totalMock];
        const colors = ['#2DCF97', '#727cf5', '#FFC75A'];
        
        // 0인 데이터는 제외
        const filteredLabels = [];
        const filteredCounts = [];
        const filteredColors = [];
        
        labels.forEach((label, index) => {
            if (usageCounts[index] > 0) {
                filteredLabels.push(label);
                filteredCounts.push(usageCounts[index]);
                filteredColors.push(colors[index]);
            }
        });
        
        const ctx = document.getElementById('aiServiceChartCanvas').getContext('2d');
        
        // 기존 차트 파괴
        const existingChart = Chart.getChart('aiServiceChartCanvas');
        if (existingChart) {
            existingChart.destroy();
        }
        
        // 데이터가 없는 경우 처리
        if (filteredCounts.length === 0) {
            createErrorChart('aiServiceChartCanvas', 'AI 서비스 이용 데이터가 없습니다.');
            return;
        }
        
        const aiServiceChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: filteredLabels,
                datasets: [{
                    data: filteredCounts,
                    backgroundColor: filteredColors
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        position: 'bottom'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                const total = context.dataset.data.reduce((sum, value) => sum + value, 0);
                                const percentage = ((context.raw / total) * 100).toFixed(1);
                                return `${context.label}: ${context.raw.toLocaleString()}회 (${percentage}%)`;
                            }
                        }
                    }
                }
            }
        });
        
    } catch (error) {
        console.error('AI 서비스 차트 로드 실패:', error);
        createErrorChart('aiServiceChartCanvas', 'AI 서비스 데이터를 불러올 수 없습니다.');
    }
}

// 에러 차트 생성
function createErrorChart(canvasId, message) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['오류'],
            datasets: [{
                label: message,
                data: [0],
                backgroundColor: '#dc3545'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });
}

// 상태에 따른 스타일 업데이트 (백엔드 상태값 사용)
function updateRateStyleByStatus(elementId, status) {
    const element = document.getElementById(elementId);
    if (!element) return;
    
    // 기존 클래스 제거
    element.classList.remove('public-span-increase', 'public-span-decrease', 'public-span-equal', 'public-span-new');
    
    // 상태에 따른 클래스 추가
    switch(status) {
        case 'increase':
            element.classList.add('public-span-increase');
            break;
        case 'decrease':
            element.classList.add('public-span-decrease');
            break;
        case 'equal':
            element.classList.add('public-span-equal');
            break;
        case 'new_entry':
            element.classList.add('public-span-new');
            break;
        default:
            element.classList.add('public-span-equal');
    }
}

// 버튼 이벤트 설정
function setupButtonEvents() {
    // 매출 차트 버튼들
    document.querySelectorAll('.revenueChart .public-toggle-button').forEach(button => {
        button.addEventListener('click', function() {
            updateActiveButton(this);
            const params = getChartParams(this);
            loadRevenueChart(params);
        });
    });
    
    // 상품 차트 버튼들
    document.querySelectorAll('.productChart .public-toggle-button').forEach(button => {
        button.addEventListener('click', function() {
            updateActiveButton(this);
            const params = getChartParams(this);
            loadProductChart(params);
        });
    });
    
    // 구독자 차트 버튼들
    document.querySelectorAll('.subscriberChart .public-toggle-button').forEach(button => {
        button.addEventListener('click', function() {
            updateActiveButton(this);
            const params = getChartParams(this);
            loadSubscriberChart(params);
        });
    });
    
    // AI 서비스 차트 버튼들
    document.querySelectorAll('.aiServiceChart .public-toggle-button').forEach(button => {
        button.addEventListener('click', function() {
            updateActiveButton(this);
            const params = getChartParams(this);
            loadAiServiceChart(params);
        });
    });
}

// 활성 버튼 업데이트
function updateActiveButton(clickedButton) {
    const group = clickedButton.closest('.btn-group');
    if (group) {
        group.querySelectorAll('.public-toggle-button').forEach(btn => {
            btn.classList.remove('active');
        });
        clickedButton.classList.add('active');
    }
}

// 차트 파라미터 추출
function getChartParams(button) {
    const id = button.id;
    const params = {};
    
    if (id.includes('Day')) {
        params.period = 'daily';
    } else if (id.includes('Month') || id.includes('6Month') || id.includes('1Year')) {
        params.period = 'monthly';
    } else if (id.includes('Week')) {
        params.period = 'weekly';
    }
    
    if (id.includes('Male')) {
        params.gender = 'M';
    } else if (id.includes('Female')) {
        params.gender = 'F';
    }
    
    if (id.includes('6Month')) {
        params.months = 6;
    } else if (id.includes('1Year')) {
        params.months = 12;
    }
    
    return params;
}

// 데이터 자동 새로고침 (5분마다)
setInterval(() => {
    loadSubscriberSummary();
}, 5 * 60 * 1000);

init();