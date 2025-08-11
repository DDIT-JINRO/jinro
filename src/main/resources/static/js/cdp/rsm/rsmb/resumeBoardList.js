/**
 * 
 */
document.addEventListener('DOMContentLoaded', function() {
	
	// 모든 북마크 버튼
	const bookmarkButtons = document.querySelectorAll('.bookmark-btn');

	// 이벤트 추가
	bookmarkButtons.forEach(button => {
	    button.addEventListener('click', function(event) {
	        event.preventDefault(); 
	        
	        // 함수 전달
	        handleBookmarkToggle(this);
	    });
	});
	
	const cardList = document.querySelectorAll('.group-card');
	cardList.forEach(card => {
		card.addEventListener('click', function(event) {
			
			if(event.target.closest(".bookmark-btn")) {
				return;
			}
			
			location.href = '/cdp/rsm/rsmb/resumeBoardDetail.do?boardId=' + this.dataset.tbdId;
		})
	})
	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/cdp/rsm/rsmb/resumeBoardInsertView.do";
		}
	})
});

const handleBookmarkToggle = (button) => {
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