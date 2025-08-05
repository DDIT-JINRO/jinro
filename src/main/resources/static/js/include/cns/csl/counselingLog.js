// 전역 상태
window.currentPage = 1;
window.currentNoticeId = null;

// helper: ISO 문자열 → "MM.DD" 포맷
function formatDateMMDD(iso) {
	const d = new Date(iso);
	const mm = String(d.getMonth() + 1).padStart(2, '0');
	const dd = String(d.getDate()).padStart(2, '0');
	const fullYear = String(d.getFullYear());
	return `${fullYear}. ${mm}. ${dd}`;
}

// 실제 데이터 + 페이징 조회
function fetchCounselingLog(page = 1) {
	const pageSize = 5;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const status = document.querySelector('select[name="status"]').value;

	axios.get('/csc/not/admin/noticeList.do', {
			params: {
				currentPage: page,
				size: pageSize,
				keyword: keyword,
				status: status
			}
		})
		.then(({ data }) => {
			const countEl = document.getElementById('notice-count');
			if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();

			const listEl = document.getElementById('notice-list');
			if (!listEl) return;

			if (data.content.length < 1 && keyword.trim() !== '') {
				listEl.innerHTML = `<tr><td colspan='4' style="text-align: center;">등록되지 않은 정보입니다.</td></tr>`;
			} else {
				const rows = data.content.map(item => `
          <tr>
            <td>${item.noticeId}</td>
            <td><a href="javascript:showDetail(${item.noticeId})" style="cursor: pointer; text-decoration: none; color:black;">${item.noticeTitle}</a></td>
            <td>${item.noticeCnt}</td>
            <td>${formatDateMMDD(item.noticeUpdatedAt)}</td>
          </tr>`).join('');
				listEl.innerHTML = rows;
				renderPagination(data);
			}
		})
		.catch(err => console.error('공지 목록 조회 중 에러:', err));
}