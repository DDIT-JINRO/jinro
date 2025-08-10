/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/empt/ivfb/insertInterViewFeedbackView.do";
		}
	})
	
	const cardHeaders = document.querySelectorAll(".card-header");
	
	cardHeaders.forEach((cardHeader) => {
		cardHeader.addEventListener("click", function() {
			toggleCard(this);
		})
	})
	
	const deleteBtns = document.querySelectorAll(".delete-btn");
	
	deleteBtns.forEach((deleteBtn) => {
		deleteBtn.addEventListener("click", function() {
			const irId = this.dataset.irId;
			deleteInterviewFeedback(irId)
		})
	})
	
	const editBtns = document.querySelectorAll(".edit-btn");
	
	editBtns.forEach((editBtn) => {
		editBtn.addEventListener("click", function() {
			const dataMemId = this.dataset.memId;
			const irId = this.dataset.irId;
			
			if (dataMemId != memId) {
				alert("허용되지 않은 접근입니다.")
				return;
			}
			
			location.href = `/empt/ivfb/updateInterviewFeedbackView.do?id=${irId}` 
		})
	})
});

function toggleCard(header) {
    const card = header.parentElement;
    const content = card.querySelector('.card-content');
    const toggle = header.querySelector('.card-toggle');
    
    // 현재 카드의 활성 상태 토글
    const isActive = content.classList.contains('active');
    
    if (isActive) {
        content.classList.remove('active');
        header.classList.remove('active');
        toggle.classList.remove('active');
    } else {
        content.classList.add('active');
        header.classList.add('active');
        toggle.classList.add('active');
    }
}

// 면접 후기 삭제 기능
function deleteInterviewFeedback(irId) {
    if (confirm('정말로 이 면접 후기를 삭제하시겠습니까?\n삭제된 후기는 복구할 수 없습니다.')) {
        fetch('/empt/ivfb/deleteInterviewFeedback.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'irId=' + encodeURIComponent(irId)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('삭제 요청이 실패했습니다.');
        })
        .then(data => {
            if (data.success) {
                alert('면접 후기가 성공적으로 삭제되었습니다.');
                location.reload(); // 페이지 새로고침
            } else {
                alert('삭제 중 오류가 발생했습니다: ' + (data.message || '알 수 없는 오류'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('삭제 중 오류가 발생했습니다. 다시 시도해주세요.');
        });
    }
}

// 페이지 로드 시 모든 카드 닫힌 상태로 초기화
document.addEventListener('DOMContentLoaded', function() {
    const allContents = document.querySelectorAll('.card-content');
    const allHeaders = document.querySelectorAll('.card-header');
    const allToggles = document.querySelectorAll('.card-toggle');
    
    allContents.forEach(content => content.classList.remove('active'));
    allHeaders.forEach(header => header.classList.remove('active'));
    allToggles.forEach(toggle => toggle.classList.remove('active'));
});