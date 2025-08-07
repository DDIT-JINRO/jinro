document.addEventListener('DOMContentLoaded', function() {
    // DOM 요소 가져오기
    const deptSearchInput = document.getElementById('deptSearchInput');
    const deptSearchBtn = document.getElementById('deptSearchBtn');
    const deptListContainer = document.getElementById('deptListContainer');

    // 검색 함수
    function searchDepartments() {
        const searchTerm = deptSearchInput.value.toLowerCase().trim();
        const deptItems = deptListContainer.querySelectorAll('.dept-item');
        let visibleCount = 0;

        // 각 학과 아이템에 대해 검색 수행
        deptItems.forEach(item => {
            const deptName = item.dataset.deptName.toLowerCase();

            if (searchTerm === '' || deptName.includes(searchTerm)) {
                item.style.display = 'block';
                visibleCount++;
            } else {
                item.style.display = 'none';
            }
        });

        // 검색 결과가 없을 때 메시지 표시/숨김
        handleSearchResults(visibleCount, searchTerm);
    }

    // 검색 결과 메시지 처리 함수
    function handleSearchResults(visibleCount, searchTerm) {
        let noResultsMessage = deptListContainer.querySelector('.no-search-results');

        if (visibleCount === 0 && searchTerm !== '') {
            // 검색 결과가 없을 때 메시지 생성/표시
            if (!noResultsMessage) {
                noResultsMessage = document.createElement('div');
                noResultsMessage.className = 'no-search-results no-data-message search-no-result';
                noResultsMessage.innerHTML = `<p>"${deptSearchInput.value}"에 대한 검색 결과가 없습니다.</p>`;
                deptListContainer.appendChild(noResultsMessage);
            } else {
                noResultsMessage.innerHTML = `<p>"${deptSearchInput.value}"에 대한 검색 결과가 없습니다.</p>`;
            }
            noResultsMessage.style.display = 'block';
        } else {
            // 검색 결과가 있을 때 메시지 숨김
            if (noResultsMessage) {
                noResultsMessage.style.display = 'none';
            }
        }
    }

    // 검색 이벤트 리스너 등록
    function initializeSearchListeners() {
        // 검색 버튼 클릭 이벤트
        if (deptSearchBtn) {
            deptSearchBtn.addEventListener('click', searchDepartments);
        }

        if (deptSearchInput) {
            // 엔터키 이벤트
            deptSearchInput.addEventListener('keyup', function(e) {
                if (e.key === 'Enter') {
                    searchDepartments();
                }
            });

            // 실시간 검색 (디바운싱 적용)
            let searchTimeout;
            deptSearchInput.addEventListener('input', function() {
                clearTimeout(searchTimeout);
                searchTimeout = setTimeout(searchDepartments, 300);
            });

            // 검색창 포커스 시 전체 텍스트 선택
            deptSearchInput.addEventListener('focus', function() {
                this.select();
            });
        }
    }

    // 북마크 기능 초기화
    document.querySelectorAll('.bookmark-btn').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            handleBookmarkToggle(this);
        });
    });

    // 학과 링크 클릭 추적 (선택사항 - 분석용)
    function initializeDeptLinkListeners() {
        document.querySelectorAll('.dept-link').forEach(link => {
            link.addEventListener('click', function(e) {
                // 학과 클릭 추적 (구글 애널리틱스 등)
                const deptName = this.textContent.trim();
                const uddId = new URL(this.href).searchParams.get('uddId');
                
                console.log('학과 상세페이지 이동:', {
                    deptName: deptName,
                    uddId: uddId
                });

                // 필요시 추가 작업 (예: 분석 데이터 전송)
            });
        });
    }

    // 초기화 함수들 실행
    initializeSearchListeners();
    initializeDeptLinkListeners();

    // 페이지 로드 완료 시 검색창에 포커스 (선택사항)
    if (deptSearchInput) {
        // 약간의 지연을 두어 다른 스크립트와의 충돌 방지
        setTimeout(() => {
            deptSearchInput.focus();
        }, 500);
    }
});

// 북마크 토글 함수
const handleBookmarkToggle = (button) => {
    const bmCategoryId = button.dataset.categoryId;
    const bmTargetId = button.dataset.targetId;
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
        console.log('북마크 응답:', data);
        if (data.success) {
			alert(data.message);
            button.classList.toggle('active');
        } else {
			alert(data.message || '북마크 처리에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('북마크 처리 중 오류 발생:', error);
        showToastMessage('오류가 발생했습니다. 잠시 후 다시 시도해주세요.', 'error');
    });
};

// 토스트 메시지 표시 함수 (alert 대신 사용)
function showToastMessage(message, type = 'info') {
    // 기존 토스트 메시지 제거
    const existingToast = document.querySelector('.toast-message');
    if (existingToast) {
        existingToast.remove();
    }

    // 토스트 메시지 생성
    const toast = document.createElement('div');
    toast.className = `toast-message toast-${type}`;
    toast.textContent = message;
    
    // 스타일 적용
    toast.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background-color: ${type === 'success' ? '#10b981' : type === 'error' ? '#ef4444' : '#3b82f6'};
        color: white;
        padding: 12px 20px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        z-index: 10000;
        font-size: 0.9rem;
        font-weight: 500;
        opacity: 0;
        transform: translateX(100%);
        transition: all 0.3s ease;
        max-width: 300px;
        word-wrap: break-word;
    `;

    document.body.appendChild(toast);

    // 애니메이션으로 표시
    setTimeout(() => {
        toast.style.opacity = '1';
        toast.style.transform = 'translateX(0)';
    }, 100);

    // 3초 후 제거
    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        setTimeout(() => {
            if (toast.parentNode) {
                toast.remove();
            }
        }, 300);
    }, 3000);
}

// 유틸리티 함수들
const utils = {
    // 디바운스 함수
    debounce: function(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    // 요소가 화면에 보이는지 확인
    isElementInViewport: function(el) {
        const rect = el.getBoundingClientRect();
        return (
            rect.top >= 0 &&
            rect.left >= 0 &&
            rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.right <= (window.innerWidth || document.documentElement.clientWidth)
        );
    },

    // 부드러운 스크롤
    smoothScrollTo: function(element, duration = 300) {
        const targetPosition = element.offsetTop;
        const startPosition = window.pageYOffset;
        const distance = targetPosition - startPosition;
        let startTime = null;

        function animation(currentTime) {
            if (startTime === null) startTime = currentTime;
            const timeElapsed = currentTime - startTime;
            const run = ease(timeElapsed, startPosition, distance, duration);
            window.scrollTo(0, run);
            if (timeElapsed < duration) requestAnimationFrame(animation);
        }

        function ease(t, b, c, d) {
            t /= d / 2;
            if (t < 1) return c / 2 * t * t + b;
            t--;
            return -c / 2 * (t * (t - 2) - 1) + b;
        }

        requestAnimationFrame(animation);
    }
};