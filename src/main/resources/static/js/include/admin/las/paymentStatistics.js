// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function() {
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
});

// 구독자 요약 정보 로드 (카드 1, 2번)
async function loadSubscriberSummary() {
    try {
        const response = await axios.get('/subscriber-summary');
        const data = response.data;
        
		console.log("data", data);
		
        // 총 구독자 수
        document.getElementById('totalSubscribersCount').textContent = data.totalSubscribers?.toLocaleString() || '0';
        document.getElementById('totalSubscribersRate').textContent = `${data.totalSubscribersRate > 0 ? '+' : ''}${data.totalSubscribersRate}%`;
        
        // 오늘 구독자 수
        document.getElementById('todaySubscribersCount').textContent = data.todaySubscribers?.toLocaleString() || '0';
        document.getElementById('todaySubscribersRate').textContent = `${data.todaySubscribersRate > 0 ? '+' : ''}${data.todaySubscribersRate}%`;
        
        // 증감률에 따른 스타일 적용
        updateRateStyle('totalSubscribersRate', data.totalSubscribersRate);
        updateRateStyle('todaySubscribersRate', data.todaySubscribersRate);
        
    } catch (error) {
        console.error('구독자 요약 정보 로드 실패:', error);
        // 에러 시 기본값 표시
        document.getElementById('totalSubscribersCount').textContent = '0';
        document.getElementById('todaySubscribersCount').textContent = '0';
    }
}

// 구독 결제 매출 차트 로드 (왼쪽 큰 div 1)
async function loadRevenueChart(params = {}) {
    try {
        const response = await axios.get('/revenue-stats', { params });
        const data = response.data;
        
        // 데이터 변환
        const labels = data.map(item => item.period || item.date);
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
        
        // 상품별로 데이터 그룹화
        const products = {};
        data.forEach(item => {
            if (!products[item.productName]) {
                products[item.productName] = [];
            }
            products[item.productName].push({
                period: item.period,
                count: item.count || 0
            });
        });
        
        // 라벨 추출 (모든 기간)
        const allPeriods = [...new Set(data.map(item => item.period))].sort();
        
        // 데이터셋 생성
        const datasets = [];
        const colors = ['#2DCF97', '#727cf5', '#FFC75A'];
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
        
        const labels = data.map(item => item.period || item.date);
        const subscribers = data.map(item => item.subscriberCount || 0);
        
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
        
        // 서비스별로 데이터 그룹화
        const services = {};
        data.forEach(item => {
            if (!services[item.serviceName]) {
                services[item.serviceName] = 0;
            }
            services[item.serviceName] += item.usageCount || 0;
        });
        
        const labels = Object.keys(services);
        const usageCounts = Object.values(services);
        const colors = ['#2DCF97', '#727cf5', '#FFC75A', '#FF6B6B', '#E9ECEF'];
        
        const ctx = document.getElementById('aiServiceChartCanvas').getContext('2d');
        
        // 기존 차트 파괴
        const existingChart = Chart.getChart('aiServiceChartCanvas');
        if (existingChart) {
            existingChart.destroy();
        }
        
        const aiServiceChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [{
                    data: usageCounts,
                    backgroundColor: colors.slice(0, labels.length)
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        position: 'bottom'
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

// 증감률 스타일 업데이트
function updateRateStyle(elementId, rate) {
    const element = document.getElementById(elementId);
    if (rate > 0) {
        element.className = element.className.replace('public-span-decrease', 'public-span-increase');
    } else if (rate < 0) {
        element.className = element.className.replace('public-span-increase', 'public-span-decrease');
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