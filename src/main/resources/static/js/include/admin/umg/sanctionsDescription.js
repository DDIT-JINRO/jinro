
window.currentPage = 1;
openNewPenaltyModalBtn = document.getElementById('openNewPenaltyModalBtn');
cancelBtn = document.getElementById('cancelBtn');
penaltyModal = document.getElementById('penaltyModal');
confirmBtn = document.getElementById('confirmBtn');
reportListCache = [];
fileListContainer = document.getElementById('file-list');
reportModifyBtn = document.getElementById('reportModify');

confirmBtn.addEventListener('click', function() {
	const selectedReportId = document.getElementById('modalMemId').value;
	if (!selectedReportId) {
		alert('신고대상자를 선택해주세요.');
		return;
	}

	const selectedPenaltyTypeEl = document.querySelector('#modalPenaltyType .penalty-type-label.active');
	if (!selectedPenaltyTypeEl) {
		alert('제재 유형을 선택해주세요.');
		return;
	}
	const selectedPenaltyType = selectedPenaltyTypeEl.getAttribute('data-type');

	const reasonInput = document.getElementById('modalReason');
	const reason = reasonInput.value.trim();
	if (!reason) {
		alert('제재 사유를 입력해주세요.');
		reasonInput.focus();
		return;
	}

	const evidenceInput = document.getElementById('evidenceFile');
	const files = evidenceInput.files;
	if (files.length === 0) {
		alert('증빙 자료를 첨부해주세요.');
		return;
	}

	if (confirm('정말로 제재하시겠습니까?')) {
		const formData = new FormData();
		formData.append('reportId', selectedReportId);
		formData.append('mpType', selectedPenaltyType);
		formData.append('mpWarnReason', reason);

		for (let i = 0; i < files.length; i++) {
			formData.append('evidenceFiles', files[i]);
		}

		penaltySubmit(formData);
	}
});

document.querySelectorAll('#modalPenaltyType .penalty-type-label').forEach(el => {
	el.addEventListener('click', () => {

		document.querySelectorAll('#modalPenaltyType .penalty-type-label').forEach(e => e.classList.remove('active'));

		el.classList.add('active');
	});
});

function penaltySubmit(formData) {
	axios.post('/admin/umg/submitPenalty.do', formData, {
		headers: {
			'Content-Type': 'multipart/form-data'
		}
	})
		.then(res => {
			alert('제재가 정상 처리되었습니다.');
			closeModal();
			fetchReportList(1);
			fetchPenaltyList(1);
		})
		.catch(err => {
			console.error(err);
			alert('제재 처리 중 오류가 발생했습니다.');
		});
}


document.getElementById('evidenceFile').addEventListener('change', function() {
	fileListContainer.innerHTML = '';

	const files = this.files;
	if (files.length === 0) {
		fileListContainer.textContent = '선택된 파일이 없습니다.';
		return;
	}

	const ul = document.createElement('ul');
	for (let i = 0; i < files.length; i++) {
		const li = document.createElement('li');
		li.textContent = files[i].name;
		ul.appendChild(li);
	}
	fileListContainer.appendChild(ul);
});

function openModal() {
	const select = document.getElementById('modalMemId');
	const fileListContainer = document.getElementById('file-list');
	fileListContainer.innerHTML = '선택된 파일이 없습니다.';
	select.innerHTML = '<option value="">-- 선택하세요 --</option>';

	reportListCache.forEach((report) => {
		if (report.reportStatus == 'S03001') {
			const option = document.createElement('option');
			option.value = report.reportId;
			option.textContent = `신고대상자 : ${report.reportedName} (신고ID : ${report.reportId})`;
			select.appendChild(option);
		}

	});

	if (penaltyModal) {
		penaltyModal.style = "";
		penaltyModal.classList.add('visible');
	}
}

function closeModal() {
	if (penaltyModal) {
		penaltyModal.classList.remove('visible');
	}

	const evidenceInput = document.getElementById('evidenceFile');
	evidenceInput.value = '';

	const fileListContainer = document.getElementById('file-list');
	fileListContainer.innerHTML = '선택된 파일이 없습니다.';

	document.getElementById('modalReason').value = '';

	const activePenaltyType = document.querySelector('#modalPenaltyType .penalty-type-label.active');
	if (activePenaltyType) {
		activePenaltyType.classList.remove('active');
	}
}

openNewPenaltyModalBtn.addEventListener('click', openModal);
cancelBtn.addEventListener('click', closeModal);


function fetchReportList(page = 1) {
	const pageSize = 10;
	const keyword = document.querySelector('input[name="keywordReport"]').value;
	const status = document.querySelector('select[name="statusReport"]').value;

	axios.get('/admin/umg/getReportList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status
		}
	})
		.then(({ data }) => {
			reportListCache = data.content;
			const countEl = document.getElementById('reportList-count');
			if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();

			const listEl = document.getElementById('reportList');
			if (!listEl) return;

			if (data.content.length < 1 && keyword.trim() !== '') {
				listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;

			} else {
				const rows = data.content.map(item => `
						          <tr>
						            <td>${item.reportId}</td>
						            <td>${item.reporterName}</td>
						            <td>${item.reportedName}</td>
						            <td>${reportStatusCng(item.reportStatus)}</td>
						            <td>${formatDateMMDD(item.reportCreatedAt)}</td>
						           
						          </tr>`).join('');
				listEl.innerHTML = rows;
			}
			renderPagination(data);
		})
		.catch(err => console.error('유저 목록 조회 중 에러:', err));
}

searchReportBtn = document.querySelector(".searchReportBtn");
if (searchReportBtn) {
	searchReportBtn.addEventListener("click", function() {
		window.currentPage = 1;
		fetchReportList(1);
	});
}

searchPenaltyBtn = document.querySelector(".searchPenaltyBtn");
if (searchPenaltyBtn) {
	searchPenaltyBtn.addEventListener("click", function() {
		window.currentPage = 1;
		fetchPenaltyList(1);
	});
}

function reportStatusCng(stat) {
	if (stat === 'S03001') return '접수';
	if (stat === 'S03002') return '반려';
	if (stat === 'S03003') return '승인';
}

function renderPagination({ startPage, endPage, currentPage, totalPages }) {

	let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

	for (let p = startPage; p <= endPage; p++) {
		html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
	}

	html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

	const footer = document.querySelector('.panel-footer.pagination');
	if (footer) footer.innerHTML = html;
}

function renderPaginationPenalty({ startPage, endPage, currentPage, totalPages }) {

	let html = `<a href="#" data-page="${startPage - 1}" class="page-link-penalty ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

	for (let p = startPage; p <= endPage; p++) {
		html += `<a href="#" data-page="${p}" class="page-link-penalty ${p === currentPage ? 'active' : ''}">${p}</a>`;
	}

	html += `<a href="#" data-page="${endPage + 1}" class="page-link-penalty ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

	const footer = document.querySelector('.panel-footer.paginationPenalty');
	if (footer) footer.innerHTML = html;
}

reportPaginationContainer = document.querySelector('.panel-footer.pagination');
if (reportPaginationContainer) {
	reportPaginationContainer.addEventListener('click', e => {

		const link = e.target.closest('a[data-page]');

		if (!link || link.parentElement.classList.contains('disabled')) {
			e.preventDefault();
			return;
		}

		e.preventDefault();
		const page = parseInt(link.dataset.page, 10);

		if (!isNaN(page) && page > 0) {
			fetchReportList(page);
		}
	});
}

penaltyPaginationContainer = document.querySelector('.panel-footer.paginationPenalty');
if (penaltyPaginationContainer) {
	penaltyPaginationContainer.addEventListener('click', e => {

		const link = e.target.closest('a[data-page]');


		if (!link || link.parentElement.classList.contains('disabled')) {
			e.preventDefault();
			return;
		}

		e.preventDefault();
		const page = parseInt(link.dataset.page, 10);

		if (!isNaN(page) && page > 0) {
			fetchPenaltyList(page);
		}
	});
}

document.getElementById("reportList").addEventListener("click", function(e) {
	const tr = e.target.closest("tr");
	if (!tr) return;

	const tds = tr.querySelectorAll("td");
	const id = tds[0].textContent.trim();


	let formData = new FormData();
	formData.set("id", id);

	reportDetail(formData);

});

function reportDetail(formData) {

	axios.post('/admin/umg/getReportDetail.do', formData)
		.then(res => {
			const { reportVO, filePath, fileOrgName } = res.data;
			const fileContainer = document.getElementById('report-detail-file');

			document.getElementById('report-detail-mpId').innerHTML = reportVO.reportId || '-';
			document.getElementById('report-detail-mpType').innerHTML = reportType(reportVO.targetType) || '-';
			document.getElementById('report-detail-memId').innerHTML = reportVO.memId || '-';
			document.getElementById('report-detail-memName').innerHTML = reportVO.reporterName || '-';
			document.getElementById('report-detail-targetId').innerHTML = reportVO.reportedMemId || '-';
			document.getElementById('report-detail-targetName').innerHTML = reportVO.reportedName || '-';
			document.getElementById('report-detail-reason').innerHTML = reportVO.reportReason || '-';
			document.getElementById('report-detail-warnDate').innerHTML = formatDateMMDD(reportVO.reportCreatedAt) || '-';
			const selectElement = document.getElementById("report-detail-status");

			for (let i = 0; i < selectElement.options.length; i++) {
				if (selectElement.options[i].value === reportVO.reportStatus) {
					selectElement.options[i].selected = true;
					break;
				}
			}
			fileContainer.innerHTML = '-';

			if (filePath != null || filePath != undefined) {

				const ext = filePath.split('.').pop().toLowerCase();


				fileContainer.innerHTML = `<a href="${filePath}" download>${fileOrgName}</a>`;

			}

		})
		.catch(error => {
			console.error('회원 정보 불러오기 실패', error);
		});
}



function reportType(stat) {

	if (stat === 'G10001') return '게시글 신고';
	if (stat === 'G10002') return '댓글 신고';


}

function penaltyStatusCng(stat) {
	if (stat === 'G14001') return '경고';
	if (stat === 'G14002') return '정지';
}

function formatDateMMDD(iso) {
	const d = new Date(iso);
	const mm = String(d.getMonth() + 1).padStart(2, '0');
	const dd = String(d.getDate()).padStart(2, '0');
	const fullYear = String(d.getFullYear());
	return `${fullYear}. ${mm}. ${dd}`;
}

function formatDate(iso) {
	const d = new Date(iso);
	const mm = String(d.getMonth() + 1).padStart(2, '0');
	const dd = String(d.getDate()).padStart(2, '0');
	const fullYear = String(d.getFullYear());
	return `${fullYear}-${mm}-${dd}`;
}

function fetchPenaltyList(page = 1) {
	const pageSize = 10;
	const keyword = document.querySelector('input[name="keywordPenalty"]').value;
	const status = document.querySelector('select[name="statusPenalty"]').value;

	axios.get('/admin/umg/getPenaltyList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status
		}
	})
		.then(({ data }) => {

			console.log(data);

			const countEl = document.getElementById('penaltyList-count');
			if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();

			const listEl = document.getElementById('penaltyList');
			if (!listEl) return;

			if (data.content.length < 1 && keyword.trim() !== '') {
				listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;

			} else {
				const rows = data.content.map(item => `
						          <tr>
						            <td>${item.mpId}</td>
						            <td>${item.memId}</td>
						            <td>${item.memName}</td>
						            <td>${penaltyStatusCng(item.mpType)}</td>
						            <td>${formatDateMMDD(item.mpWarnDate)}</td>
						           
						          </tr>`).join('');
				listEl.innerHTML = rows;
			}
			renderPaginationPenalty(data);
		})
		.catch(err => console.error('유저 목록 조회 중 에러:', err));
}

document.getElementById("penaltyList").addEventListener("click", function(e) {
	const tr = e.target.closest("tr");
	if (!tr) return;

	const tds = tr.querySelectorAll("td");
	const id = tds[0].textContent.trim();


	let formData = new FormData();
	formData.set("id", id);

	penaltyDetail(formData);

});

function penaltyDetail(formData) {

	axios.post('/admin/umg/getPenaltyDetail.do', formData)
		.then(res => {
			const fileContainer = document.getElementById('penalty-detail-file');

			document.getElementById('penalty-detail-mpId').innerHTML = '-';
			document.getElementById('penalty-detail-mpType').innerHTML = '-';
			document.getElementById('penalty-detail-memId').innerHTML = '-';
			document.getElementById('penalty-detail-memName').innerHTML = '-';
			document.getElementById('penalty-detail-reason').innerHTML = '-';
			document.getElementById('penalty-detail-warnDate').innerHTML = '-';
			document.getElementById('penalty-detail-startDate').innerHTML = '-';
			document.getElementById('penalty-detail-endDate').innerHTML = '-';
			fileContainer.innerHTML = '-';


			const { penaltyVO, filePath, fileOrgName } = res.data;

			console.log(penaltyVO, filePath, fileOrgName)

			document.getElementById('penalty-detail-mpId').innerHTML = penaltyVO.mpId || '-';
			document.getElementById('penalty-detail-mpType').innerHTML = penaltyStatusCng(penaltyVO.mpType) || '-';
			document.getElementById('penalty-detail-memId').innerHTML = penaltyVO.memId || '-';
			document.getElementById('penalty-detail-memName').innerHTML = penaltyVO.memName || '-';
			document.getElementById('penalty-detail-reason').innerHTML = penaltyVO.mpWarnReason || '-';
			document.getElementById('penalty-detail-warnDate').innerHTML = formatDateMMDD(penaltyVO.mpWarnDate) || '-';
			if (penaltyVO.mpStartedAt != null) document.getElementById('penalty-detail-startDate').innerHTML = formatDateMMDD(penaltyVO.mpStartedAt) || '-';
			if (penaltyVO.mpCompleteAt != null) document.getElementById('penalty-detail-endDate').innerHTML = formatDateMMDD(penaltyVO.mpCompleteAt) || '-';
			fileContainer.innerHTML = '-';

			if (filePath != null || filePath != undefined) {

				const ext = filePath.split('.').pop().toLowerCase();


				fileContainer.innerHTML = `<a href="${filePath}" download>${fileOrgName}</a>`;

			}
		})
		.catch(error => {
			console.error('회원 정보 불러오기 실패', error);
		});
}

reportModifyBtn.addEventListener('click', function() {

	const mpId = document.getElementById('report-detail-mpId').innerText;
	const mpStat = document.getElementById('report-detail-status').value;

	let form = new FormData();
	form.set("reportId", mpId);
	form.set("reportStatus", mpStat);

	if (mpStat === 'S03003') {
		alert('승인은 직접 변경할 수 없습니다. 제재등록 바랍니다.')
		return;
	}

	axios.post('/admin/umg/reportModify.do', form)
		.then(res => {
			if (res.data == 1) {
				alert('수정완료');
				fetchReportList(1);
			} else {
				alert('수정 오류 발생');
			}

		})
})

fetchReportList();
fetchPenaltyList();
