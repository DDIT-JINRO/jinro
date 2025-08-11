// 모든 초기화 함수를 묶어주는 함수
function initContentDashboard() {
    mostViewedChart();
    noticeClickChart();
    communityStatsChart();
    reportStatusChart();
}

// 가장 많이 본 게시글 (수평 막대 차트)
function mostViewedChart() {
    const ctx = document.getElementById('mostViewedChart');
    if (!ctx) return;

    const serverData = [{
        title: '2024년 2학기 장학금 신청 안내',
        views: 1250
    }, {
        title: '커뮤니티 이용 수칙 변경 공지',
        views: 1120
    }, {
        title: 'AI 학습 도구 활용법 가이드',
        views: 980
    }, {
        title: '신입생을 위한 자주 묻는 질문',
        views: 870
    }, {
        title: '웹 개발 스터디 모집',
        views: 750
    }, ];

    const labels = serverData.map(item => item.title);
    const data = serverData.map(item => item.views);

    const config = {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '조회수',
                data: data,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
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
                    display: true,
                    text: '가장 많이 본 게시글'
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
    };
    new Chart(ctx, config);
}

// 공지사항 클릭 수 TOP 10 (막대 차트)
function noticeClickChart() {
    const ctx = document.getElementById('noticeClickChart');
    if (!ctx) return;

    const serverData = [{
        title: '시스템 점검 안내',
        clicks: 850
    }, {
        title: '휴강 안내',
        clicks: 720
    }, {
        title: '도서관 이용 시간 변경',
        clicks: 680
    }, {
        title: '장학금 신청 기간 연장',
        clicks: 650
    }, {
        title: '수강신청 일정 공지',
        clicks: 580
    }, ];

    const labels = serverData.map(item => item.title);
    const data = serverData.map(item => item.clicks);

    const config = {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '클릭 수',
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
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
                    text: '공지사항 클릭 수 TOP 10'
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

// 커뮤니티 게시글 수 및 댓글 수 (선 차트)
function communityStatsChart() {
    const ctx = document.getElementById('communityStatsChart');
    if (!ctx) return;

    const postData = [{
        date: '2025-07-01',
        count: 55
    }, {
        date: '2025-07-02',
        count: 62
    }, {
        date: '2025-07-03',
        count: 59
    }, {
        date: '2025-07-04',
        count: 70
    }, {
        date: '2025-07-05',
        count: 75
    }];
    const commentData = [{
        date: '2025-07-01',
        count: 120
    }, {
        date: '2025-07-02',
        count: 140
    }, {
        date: '2025-07-03',
        count: 130
    }, {
        date: '2025-07-04',
        count: 160
    }, {
        date: '2025-07-05',
        count: 180
    }];

    const labels = postData.map(item => item.date.substring(5));

    const config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '게시글 수',
                data: postData.map(item => item.count),
                borderColor: 'rgba(255, 99, 132, 1)',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                tension: 0.4,
                fill: false
            }, {
                label: '댓글 수',
                data: commentData.map(item => item.count),
                borderColor: 'rgba(54, 162, 235, 1)',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                tension: 0.4,
                fill: false
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: '커뮤니티 게시글 및 댓글 수 추이'
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

// 신고된 게시글 유형 및 처리 현황 (도넛 차트)
function reportStatusChart() {
    const ctx = document.getElementById('reportStatusChart');
    if (!ctx) return;

    const serverData = [{
        type: '욕설/비방',
        count: 45
    }, {
        type: '음란물',
        count: 12
    }, {
        type: '개인정보 유출',
        count: 8
    }, {
        type: '기타',
        count: 25
    }];

    const labels = serverData.map(item => item.type);
    const data = serverData.map(item => item.count);

    const config = {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '신고 건수',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 205, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)'
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
                    text: '신고된 게시글 유형'
                }
            }
        }
    };
    new Chart(ctx, config);
}

// 페이지 로드 시 차트를 초기화합니다.
 initContentDashboard();