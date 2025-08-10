/**
 * 
 */
ctxCnsMngMonthChart = document.getElementById('sparklineChart');

new Chart(ctxCnsMngMonthChart, {
	type: 'bar',
	data: {
		labels: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
		datasets: [{
			data: [15, 20, 5, 65, 55, 22, 68, 95, 85, 25, 20],
			backgroundColor: 'rgba(139, 148, 242, 0.8)',
			borderRadius: 2,
		}]
	},
	options: {
		maintainAspectRatio: false,
		scales: {
			x: {
				display: false
			},
			y: {
				display: false
			}
		},
		plugins: {
			legend: {
				display: false
			},
			tooltip: {
				enabled: false
			}
		}
	}
});

function fetchUserList(page = 1) {
	const pageSize = 10;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const status = document.querySelector('select[name="status"]').value;

	axios.get('/admin/umg/getMemberList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status,
			memRole: 'R01003'
		}
	})
		.then(({ data }) => {
			const countEl = document.getElementById('userList-count');
			if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();

			const listEl = document.getElementById('userList');
			if (!listEl) return;

			if (data.content.length < 1 && keyword.trim() !== '') {
				listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
			} else {
				const rows = data.content.map(item => `
			          <tr>
			            <td>${item.memId}</td>
			            <td>${item.memName}</td>
			            <td>${item.memEmail}</td>
			            <td>${item.memNickname}</td>
			           
			          </tr>`).join('');
				listEl.innerHTML = rows;
				renderPagination(data);
			}
		})
		.catch(err => console.error('유저 목록 조회 중 에러:', err));
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

if (!window._paginationDelegated) {
	window._paginationDelegated = true;

	document.addEventListener('click', e => {
		const a = e.target.closest('.pagination a[data-page]');
		if (!a) return;

		e.preventDefault();
		const page = parseInt(a.dataset.page, 10);
		if (isNaN(page) || page < 1) return;

		window.currentPage = page;
		fetchUserList(page);
	});
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

function convertLoginType(code) {
	switch (code) {
		case 'G33001': return '일반';
		case 'G33002': return '카카오';
		case 'G33003': return '네이버';
		default: return code;
	}
}
function memberGender(code) {
	switch (code) {
		case 'G11001': return '남자';
		case 'G11002': return '여자';
		default: return code;
	}
}
document.getElementById("userList").addEventListener("click", function(e) {
	const tr = e.target.closest("tr");
	if (!tr) return;

	const tds = tr.querySelectorAll("td");
	const id = tds[0].textContent.trim();

	let formData = new FormData();
	formData.set("id", id);

	userDetail(formData);

});


function userDetail(formData) {
	axios.post('/admin/umg/getMemberDetail.do', formData)
		.then(res => {
			const { memberDetail, filePath, countVO, interestCn } = res.data;

			console.log(memberDetail)

			const profileImgEl = document.getElementById('member-profile-img');
			profileImgEl.src = filePath ? filePath : '/images/defaultProfileImg.png';


			document.getElementById('mem-id').value = memberDetail.memId || '-';
			document.getElementById('mem-name').value = memberDetail.memName || '-';
			document.getElementById('mem-nickname').value = memberDetail.memNickname || '-';
			document.getElementById('mem-email').value = memberDetail.memEmail || '-';
			document.getElementById('mem-phone').value = memberDetail.memPhoneNumber || '-';
			document.getElementById('mem-gen').value = memberGender(memberDetail.memGen) || '-';
			document.getElementById('mem-birth').value = formatDate(memberDetail.memBirth) || '-';
			document.getElementById('mem-logType').value = convertLoginType(memberDetail.loginType) || '-';


			const selectElement = document.getElementById("mem-role");

			for (let i = 0; i < selectElement.options.length; i++) {
				if (selectElement.options[i].value === memberDetail.memRole) {
					selectElement.options[i].selected = true;
					break;
				}
			}


			document.getElementById('mem-warn-count').textContent = `${countVO.warnCount}회`;
			document.getElementById('mem-ban-count').textContent = `${countVO.banCount}회`;


			const interests = interestCn && interestCn.length > 0 ? interestCn.join(', ') : '없음';
			document.getElementById('mem-interests').textContent = interests;

		})
		.catch(error => {
			console.error('회원 정보 불러오기 실패', error);
		});
}

fetchUserList();