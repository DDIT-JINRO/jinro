document.addEventListener("DOMContentLoaded", function() {
	const channelSection = document.querySelector(".channel");
	if (channelSection) {
	    const errorMessage = channelSection.dataset.errorMessage;
	    if (errorMessage) {
			alert(errorMessage);
			history.back();
		}
	}

	document.querySelector("#submit-btn").addEventListener("click", async function() {
	    const crId = document.querySelector("#cr-id").value.trim();
	    const crContent = document.querySelector("#cr-content").value.trim();
		const crRate = window.getCrRate();
		const crPublic = document.querySelector("input[name='cr-public']:checked").value.trim();
		
		if (crRate === 0) {
			alert("상담 평가를 선택해 주세요.");
			return;
		}
	    
	    if (!crContent) {
	        alert("상담 후기를 입력해 주세요.");
	        return;
	    }

	    // FormData 생성
	    const formData = new FormData();
		formData.append('crId', crId)
	    formData.append('crContent', crContent);
		formData.append('crRate', crRate);
		formData.append('crPublic', crPublic);

	    try {
	        const response = await fetch("/cnslt/rvw/updateCnsReview.do", {
	            method: "POST",
	            body: formData
	        });

	        if (response.ok) {
	            const result = await response.json();
	            
	            if (result.success) {
	                alert("후기 수정이 완료되었습니다");
	                window.location.href = "/cnslt/rvw/cnsReview.do";
	            } else {
	                alert(result.message || "수정에 실패했습니다.");
	            }
	        } else {
	            throw new Error(`서버 응답 오류: ${response.status}`);
	        }
	    } catch (error) {
	        console.error("수정 중 오류:", error);
	        alert("수정에 실패했습니다.");
	    }
	});

	document.querySelector("#back-btn").addEventListener("click", function() {
		window.location.href = "/cnslt/rvw/cnsReview.do";
	});
});

// 별점 평가 기능
document.addEventListener('DOMContentLoaded', function() {
    const starRating = document.getElementById('cr-rate');
    const ratingText = document.getElementById('rating-text');
    const stars = starRating.querySelectorAll('.star');
    
    // 별점 설명 텍스트
    const ratingTexts = {
        0: '평가해주세요',
        1: '매우 불만족',
        2: '불만족',
        3: '보통',
        4: '만족',
        5: '매우 만족'
    };
    
    let currentRating = parseInt(starRating.dataset.rating) || 0;
	
	// 페이지 로드 시 기존 별점 표시
	if (currentRating > 0) {
	    setRating(currentRating);
	}
    
    // 별 클릭 이벤트
    stars.forEach((star, index) => {
        // 클릭 이벤트
        star.addEventListener('click', function() {
            const rating = parseInt(this.dataset.value);
            setRating(rating);
        });
        
        // 마우스 호버 이벤트
        star.addEventListener('mouseenter', function() {
            const rating = parseInt(this.dataset.value);
            highlightStars(rating, true);
            updateRatingText(rating, true);
        });
    });
    
    // 별점 컨테이너에서 마우스가 나갔을 때
    starRating.addEventListener('mouseleave', function() {
        highlightStars(currentRating, false);
        updateRatingText(currentRating, false);
    });
    
    // 별점 설정 함수
    function setRating(rating) {
        currentRating = rating;
        starRating.dataset.rating = rating;
        highlightStars(rating, false);
        updateRatingText(rating, false);
    }
    
    // 별 하이라이트 함수
    function highlightStars(rating, isHover) {
        stars.forEach((star, index) => {
            star.classList.remove('active', 'temp-active');
            
            if (index < rating) {
                if (isHover) {
                    star.classList.add('temp-active');
                } else {
                    star.classList.add('active');
                }
            }
        });
    }
    
    // 평가 텍스트 업데이트 함수
    function updateRatingText(rating, isHover) {
        ratingText.textContent = ratingTexts[rating];
        
        if (rating > 0) {
            ratingText.classList.add('selected');
        } else {
            ratingText.classList.remove('selected');
        }
    }
    
    // 별점 값을 가져오는 함수 (폼 제출 시 사용)
    window.getCrRate = function() {
        return parseInt(currentRating) || 0;
    };
});

// textarea 글자수 카운터 기능
document.addEventListener('DOMContentLoaded', function() {
    const textarea = document.getElementById('cr-content');
    const maxLength = 300; // 최대 글자수 설정
    
    // 글자수 카운터 HTML 생성
    const counterHTML = `
        <div class="char-counter">
            <span class="current-count">0</span><span class="unit">자</span>
            <span class="separator">/</span>
            <span>최대&nbsp;</span><span class="max-count">${maxLength}</span><span class="unit">자</span>
        </div>
    `;
    
    // textarea 부모 요소에 textarea-container 클래스 추가
    const parentDiv = textarea.closest('.input-group');
    if (parentDiv) {
        parentDiv.classList.add('textarea-container');
        // 카운터를 textarea 다음에 추가
        parentDiv.insertAdjacentHTML('beforeend', counterHTML);
    }
    
    // 글자수 카운터 요소들 선택
    const counter = parentDiv.querySelector('.char-counter');
    const currentCount = counter.querySelector('.current-count');
    
    // 글자수 업데이트 함수
    function updateCounter() {
        const currentLength = textarea.value.length;
        currentCount.textContent = currentLength;
        
        // 글자수에 따른 스타일 변경
        counter.classList.remove('warning', 'error');
    }
    
    // 이벤트 리스너 등록
    textarea.addEventListener('input', updateCounter);
    textarea.addEventListener('paste', function() {
        // paste 이벤트는 약간의 지연 후 실행
        setTimeout(updateCounter, 10);
    });
    
    // 초기 글자수 설정
    updateCounter();
});