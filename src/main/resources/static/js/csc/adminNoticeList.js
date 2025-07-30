// ——— helper: ISO문자열 → "MM.DD" 포맷 ———
function formatDateMMDD(iso) {
	const d = new Date(iso);
	const mm = String(d.getMonth() + 1).padStart(2, '0');
	const dd = String(d.getDate()).padStart(2, '0');
	return `${mm}.${dd}`;
}

// ——— 실제 데이터 + 페이징 조회 함수 ———
function fetchNotices(page = 1) {
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
			document.getElementById('notice-count').textContent = parseInt(data.total, 10).toLocaleString();

			if (data.content.length < 1 && keyword != null) {
				document.getElementById('notice-list').innerHTML = `
					<tr><td colspan='4' style="text-align: center;">등록되지 않은 정보입니다.</td></tr>
				`;
			} else {
				const rows = data.content.map(item => `
				  <tr>
				    <td>${item.noticeId}</td>
				    <td><a href="javascript:showDetail(${item.noticeId})" style="cursor: pointer; text-decoration: none; color:black;">${item.noticeTitle}</a></td>
				    <td>${item.noticeCnt}</td>
				    <td>${formatDateMMDD(item.noticeUpdatedAt)}</td>
				  </tr>
				`).join('');
				document.getElementById('notice-list').innerHTML = rows;
				renderPagination(data);
			}
		})
		.catch(err => console.error('공지 목록 조회 중 에러:', err));
}

function renderPagination({ startPage, endPage, currentPage, totalPages }) {
	let html = `
		<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>
	`;

	for (let p = startPage; p <= endPage; p++) {
		html += `
			<a href="#" onclick="page(${p})" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>
		`;
	}

	html += `
		<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>
	`;

	document.querySelector('.panel-footer.pagination').innerHTML = html;
}

window.pageData = 1;
function page(p) {
	window.pageData = p;
}

// 이벤트 바인딩
document.querySelector('.pagination').addEventListener('click', e => {
	e.preventDefault();
	const a = e.target.closest('a[data-page]');
	if (!a) return;
	const page = parseInt(a.dataset.page, 10);
	if (isNaN(page) || page < 1) return;
	fetchNotices(page);
});

document.querySelector('.btn-save').addEventListener('click', () => {
	fetchNotices(1);
});

// 초기 로드
fetchNotices(1);

// 공지사항 클릭 시 초기화
document.getElementById('noticeHeader')?.addEventListener('click', () => {
	const kwInput = document.querySelector('input[name="keyword"]');
	if (kwInput) kwInput.value = '';

	const statusSelect = document.querySelector('select[name="status"]');
	if (statusSelect) statusSelect.selectedIndex = 0;

	fetchNotices(1);
});

// 상세 조회
window.data = null;
function showDetail(no) {
	console.log("no : ", no);
	axios.get('csc/not/admin/noticeDetail.do', {
		params: { no }
	})
		.then(response => {
			window.data = no;
			document.getElementById("btn-delete").style.display = "block";
			document.getElementById("btn-save").innerHTML = "수정";
			document.getElementById('noticeId').value = no;

			const infoTable = document.querySelector('.info-table');
			infoTable.style.display = 'table';

			const resp = response.data;
			document.getElementById("info-table-tbody").innerHTML = `
				<tr>
					<td>${resp.noticeId}</td>
					<td>${resp.noticeTitle}</td>
					<td>${formatDateMMDD(resp.noticeUpdatedAt)}</td>
					<td>${resp.noticeCnt}</td>
				</tr>`;

			document.querySelector('input[name="noticeTitle"]').value = resp.noticeTitle;

			if (window.editor) {
				window.editor.setData(resp.noticeContent);
			} else {
				document.getElementById("noticeContent").value = resp.noticeContent;
			}

			const ul = document.getElementById("existing-files");
			if (!resp.getFileList || resp.getFileList.length === 0) {
				ul.innerHTML = '<li>첨부된 파일이 없습니다.</li>';
			} else {
				document.getElementById('fileGroupNo').value = resp.getFileList[0].fileGroupId;
				document.getElementById("file").style.display = "block";
				ul.innerHTML = resp.getFileList.map(f => `
					<li>
						<div onclick="filedownload('${f.fileGroupId}', ${f.fileSeq})" target="_blank">${f.fileOrgName}</div>&nbsp;&nbsp;
						<button type="button" onclick="deleteExistingFile('${f.fileGroupId}', ${f.fileSeq}, ${no})">삭제</button>
					</li>
				`).join('');
			}
		})
		.catch(console.error);
}

// 파일 다운로드
function filedownload(fileGroupId, fileSeq) {
	axios({
		method: 'get',
		url: `/files/download`,
		params: { groupId: fileGroupId, seq: fileSeq },
		responseType: 'blob'
	})
	.then(response => {
		const blob = new Blob([response.data]);
		const url = window.URL.createObjectURL(blob);
		const a = document.createElement('a');
		a.href = url;
		a.download = `파일명_${fileSeq}.확장자`;
		document.body.appendChild(a);
		a.click();
		document.body.removeChild(a);
		window.URL.revokeObjectURL(url);
	})
	.catch(error => {
		console.error('파일 다운로드 실패:', error);
	});
}

// 파일 삭제
function deleteExistingFile(fileGroupId, seq, no) {
	axios.get('/csc/not/admin/deleteFile', {
		params: { groupId: fileGroupId, seq }
	})
	.then(() => showDetail(no))
	.catch(console.error);
}

// CKEditor 초기화
ClassicEditor.create(document.getElementById("noticeContent"), {
	ckfinder: { uploadUrl: "/image/upload" }
}).then(editor => { window.editor = editor })
  .catch(err => console.error(err.stack));

// 상세 초기화
function resetDetail() {
	document.getElementById("btn-delete").style.display = "none";
	document.getElementById("file").style.display = "none";
	document.getElementById("existing-files").innerHTML = "";
	document.getElementById("btn-save").innerHTML = "등록";
	document.querySelector('.info-table').style.display = 'none';
	document.getElementById('info-table-tbody').innerHTML = '';
	document.querySelector('input[name="noticeTitle"]').value = '';
	if (window.editor) window.editor.setData('');
	else document.getElementById('noticeContent').value = '';
	document.getElementById('noticeFileInput').value = '';
}

// 등록 또는 수정
function insertOrUpdate() {
	const form = document.getElementById('form-data');
	const fd = new FormData(form);
	if (window.editor?.getData) {
		fd.set('noticeContent', window.editor.getData());
	}

	if (document.querySelector('.info-table').style.display === 'none') {
		axios.post('/csc/not/admin/insertNotice', fd)
			.then(res => {
				console.log('등록 성공:', res.data);
				form.reset();
				if (window.editor?.setData) window.editor.setData('');
				fetchNotices(1);
			})
			.catch(err => console.error('등록 실패:', err.response || err));
	} else {
		axios.post('/csc/not/admin/updateNotice', fd)
			.then(res => {
				resetDetail();
				console.log('수정 성공:', res.data);
				if (window.editor?.setData) window.editor.setData('');
				fetchNotices(window.pageData);
				showDetail(window.data);
			})
			.catch(err => console.error('수정 실패:', err.response || err));
	}
}

// 삭제
function deleteNotice() {
	const form = document.getElementById('form-data');
	const fd = new FormData(form);
	if (window.editor?.getData) {
		fd.set('noticeContent', window.editor.getData());
	}
	axios.post('/csc/not/admin/deleteNotice', fd)
		.then(res => {
			console.log('삭제 성공:', res.data);
			fetchNotices(1);
			resetDetail();
		})
		.catch(err => console.error('삭제 실패:', err.response || err));
}
