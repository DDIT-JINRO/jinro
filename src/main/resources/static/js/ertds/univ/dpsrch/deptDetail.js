// 학과 디테일 페이지 JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // 상세보기 버튼 이벤트 리스너
    const detailBtn = document.querySelector('.dept-detail-btn');
    if (detailBtn) {
        detailBtn.addEventListener('click', handleDetailClick);
    }

    // 이미지 로드 에러 처리
    const chartImages = document.querySelectorAll('.dept-chart-image');
    chartImages.forEach(img => {
        img.addEventListener('error', handleImageError);
    });

    // 스크롤 애니메이션 초기화
    initScrollAnimations();

    // 차트 초기화
    initCharts();
	
	// 이벤트 추가
	document.querySelectorAll('.bookmark-btn').forEach(button => {
		button.addEventListener('click', function(event) {
			event.preventDefault();
			// 함수 전달
			handleBookmarkToggle(this);
		});
	});
});

const handleBookmarkToggle = (button) => {
	if (memId == "" || memId == "anonymousUser") {
        alert("북마크는 로그인 후 이용 하실 수 있습니다.");
        return;
    }
	
	const bmCategoryId = button.dataset.categoryId;
	const bmTargetId = button.dataset.targetId;

	// 현재 버튼이 'active' 클래스를 가지고 있는지 확인
	const isBookmarked = button.classList.contains('active');
	const data = {
		bmCategoryId: bmCategoryId,
		bmTargetId: bmTargetId
	};

	const apiUrl = isBookmarked ? '/mpg/mat/bmk/deleteBookmark.do' : '/mpg/mat/bmk/insertBookmark.do';

	fetch(apiUrl, {
		method: "POST",
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify(data),
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('서버 응답에 실패했습니다.');
			}
			return response.json();
		})
		.then(data => {
			if (data.success) {
				alert(data.message);
				button.classList.toggle('active');
			} else {
				alert(data.message || '북마크 처리에 실패했습니다.');
			}
		})
		.catch(error => {
			// 네트워크 오류나 서버 응답 실패 시
			console.error('북마크 처리 중 오류 발생:', error);
			alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
		});
}

/**
 * 이미지 로드 에러 처리
 */
function handleImageError(event) {
    const img = event.target;
    img.style.display = 'none';
    
    // 대체 텍스트 표시
    const container = img.parentElement;
    const fallbackText = document.createElement('div');
    fallbackText.className = 'chart-fallback';
    fallbackText.textContent = '차트를 불러올 수 없습니다.';
    fallbackText.style.cssText = `
        display: flex;
        justify-content: center;
        align-items: center;
        height: 200px;
        background: #f5f5f5;
        color: #666;
        border-radius: 8px;
        font-size: 16px;
    `;
    container.appendChild(fallbackText);
}

/**
 * 토스트 메시지 표시
 */
function showToast(message, type = 'info') {
    // 기존 토스트 제거
    const existingToast = document.querySelector('.toast-message');
    if (existingToast) {
        existingToast.remove();
    }
    
    // 새 토스트 생성
    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.textContent = message;
    
    // 스타일 적용
    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 12px 20px;
        border-radius: 6px;
        color: white;
        font-weight: 500;
        z-index: 10000;
        animation: slideInRight 0.3s ease-out;
    `;
    
    // 타입별 배경색
    const colors = {
        success: '#4CAF50',
        error: '#F44336',
        info: '#2196F3',
        warning: '#FF9800'
    };
    toast.style.backgroundColor = colors[type] || colors.info;
    
    // DOM에 추가
    document.body.appendChild(toast);
    
    // 3초 후 제거
    setTimeout(() => {
        if (toast.parentElement) {
            toast.style.animation = 'slideOutRight 0.3s ease-in';
            setTimeout(() => toast.remove(), 300);
        }
    }, 3000);
}

/**
 * 차트 초기화
 */
function initCharts() {
    // Chart.js 로드 확인
    if (typeof Chart === 'undefined') {
        console.error('Chart.js 라이브러리가 로드되지 않았습니다.');
        return;
    }

    try {
        createSalaryChart();
        createSatisfactionChart();
        createEmploymentFieldChart();
        createGenderEmploymentChart();
    } catch (error) {
        console.error('차트 생성 중 오류가 발생했습니다:', error);
    }
}

/**
 * 임금 분포 막대 차트 생성
 */
function createSalaryChart() {
    const ctx = document.getElementById('salaryChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: salaryData.labels,
            datasets: [{
                label: '비율 (%)',
                data: salaryData.data,
                backgroundColor: [
                    '#FF6B8A',  // 연분홍
                    '#4ECDC4',  // 민트
                    '#FFE66D',  // 연노랑
                    '#A8E6CF',  // 연초록
                    '#C7CEEA'   // 연보라
                ],
                borderColor: '#FFFFFF',
                borderWidth: 2,
                borderRadius: 4,
                borderSkipped: false,
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
                            return context.parsed.y.toFixed(1) + '%';
                        }
                    },
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    titleColor: '#fff',
                    bodyColor: '#fff'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(0,0,0,0.1)'
                    },
                    ticks: {
                        callback: function(value) {
                            return value + '%';
                        },
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                }
            },
            animation: {
                duration: 500,
                easing: 'easeOutQuart'
            }
        }
    });
}

/**
 * 직업만족도 막대 차트 생성
 */
function createSatisfactionChart() {
    const ctx = document.getElementById('satisfactionChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: satisfactionData.labels,
            datasets: [{
                label: '비율 (%)',
                data: satisfactionData.data,
                backgroundColor: [
                    '#FF4757',  // 매우 불만족 - 빨강
                    '#FF7675',  // 불만족 - 연빨강
                    '#FDCB6E',  // 보통 - 노랑
                    '#6C5CE7',  // 만족 - 보라
                    '#00B894'   // 매우 만족 - 초록
                ],
                borderColor: '#FFFFFF',
                borderWidth: 2,
                borderRadius: 4,
                borderSkipped: false,
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
                            return context.parsed.y.toFixed(1) + '%';
                        }
                    },
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    titleColor: '#fff',
                    bodyColor: '#fff'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(0,0,0,0.1)'
                    },
                    ticks: {
                        callback: function(value) {
                            return value + '%';
                        },
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                }
            },
            animation: {
                duration: 500,
                easing: 'easeOutQuart'
            }
        }
    });
}

/**
 * 취업 분야 분포 막대 차트 생성
 */
function createEmploymentFieldChart() {
    const ctx = document.getElementById('employmentFieldChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: employmentFieldData.labels,
            datasets: [{
                label: '비율 (%)',
                data: employmentFieldData.data,
                backgroundColor: [
                    '#FF6B8A', '#4ECDC4', '#FFE66D', '#A8E6CF', '#C7CEEA',
                    '#FECA57', '#48CAE4', '#F38BA8', '#A8DADC', '#E9C46A'
                ],
                borderColor: '#FFFFFF',
                borderWidth: 2,
                borderRadius: 4,
                borderSkipped: false,
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
                            return context.parsed.y.toFixed(1) + '%';
                        }
                    },
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    titleColor: '#fff',
                    bodyColor: '#fff'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(0,0,0,0.1)'
                    },
                    ticks: {
                        callback: function(value) {
                            return value + '%';
                        },
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                },
                x: {
                    grid: {
                        display: false
                    },
                    ticks: {
                        maxRotation: 45,
                        minRotation: 45,
                        font: {
                            family: 'Noto Sans KR'
                        }
                    }
                }
            },
            animation: {
                duration: 500,
                easing: 'easeOutQuart'
            }
        }
    });
}

/**
 * 성별 취업률 원형 차트 생성
 */
function createGenderEmploymentChart() {
    const ctx = document.getElementById('genderEmploymentChart');
    if (!ctx) return;

    new Chart(ctx, {
        type: 'doughnut',  // 도넛 차트로 변경하여 더 현대적으로
        data: {
            labels: genderEmploymentData.labels,
            datasets: [{
                data: genderEmploymentData.data,
                backgroundColor: [
                    '#4A90E2',  // 남성 - 차분한 파랑
                    '#E24A90'   // 여성 - 차분한 분홍
                ],
                borderColor: '#FFFFFF',
                borderWidth: 3,
                hoverBorderWidth: 5
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            cutout: '50%',  // 도넛 차트의 중앙 홀 크기
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        padding: 20,
                        usePointStyle: true,
                        font: {
                            size: 14,
                            family: 'Noto Sans KR'
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.label + ': ' + context.parsed.toFixed(1) + '%';
                        }
                    },
                    backgroundColor: 'rgba(0,0,0,0.8)',
                    titleColor: '#fff',
                    bodyColor: '#fff',
                    borderColor: '#ddd',
                    borderWidth: 1
                }
            },
            animation: {
                animateRotate: true,
                duration: 500
            }
        }
    });
}
function initScrollAnimations() {
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, observerOptions);
    
    // 애니메이션 대상 요소들
    const animateElements = document.querySelectorAll('.dept-content-item');
    animateElements.forEach((el, index) => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(30px)';
        el.style.transition = `opacity 0.6s ease ${index * 0.1}s, transform 0.6s ease ${index * 0.1}s`;
        observer.observe(el);
    });
}

/**
 * 유틸리티: 부드러운 스크롤
 */
function smoothScrollTo(element) {
    if (element) {
        element.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
    }
}

// CSS 애니메이션 정의 (동적 추가)
const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            transform: translateX(300px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOutRight {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(300px);
            opacity: 0;
        }
    }
    
    .dept-tag:hover,
    
    .dept-detail-btn:active,
    .dept-bookmark-btn:active {
        transform: scale(0.95);
    }
`;
document.head.appendChild(style);