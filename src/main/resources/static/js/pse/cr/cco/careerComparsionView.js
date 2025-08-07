document.addEventListener('DOMContentLoaded', function() {
    // 모든 북마크 버튼
    const bookmarkButtons = document.querySelectorAll('.bookmark-btn');

    // 이벤트 추가
    bookmarkButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); 
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
		console.log(data);
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