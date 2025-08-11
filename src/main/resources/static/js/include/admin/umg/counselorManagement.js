/**
 * 
 */
ctxCnsMngMonthChart = document.getElementById('sparklineChart');
modifyButton = document.getElementById('userModify');

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

function fetchCnsList(page = 1) {
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
			}
				renderPagination(data);
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

cnsListPaginationContainer = document.querySelector('.panel-footer.pagination');
if (cnsListPaginationContainer) {
    cnsListPaginationContainer.addEventListener('click', e => {
        
        const link = e.target.closest('a[data-page]');
        
        if (!link || link.parentElement.classList.contains('disabled')) {
            e.preventDefault();
            return;
        }

        e.preventDefault();
        const page = parseInt(link.dataset.page, 10);

        
        if (!isNaN(page) && page > 0) {
            fetchCnsList(page);
        }
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
			const { memberDetail, filePath, countVO, interestCn, vacByCns, counseling, avgRate } = res.data;
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


			document.getElementById('mem-warn-count').textContent = `${counseling}회`;
			document.getElementById('mem-ban-count').textContent = `${vacByCns}회`;
			document.getElementById('mem-avg-count').textContent = `${avgRate == 0 ? "후기없음" : avgRate+"점"}`;

		})
		.catch(error => {
			console.error('회원 정보 불러오기 실패', error);
		});
}

searchCnsBtn = document.querySelector(".searchCnsBtn");
if (searchCnsBtn) {
	searchCnsBtn.addEventListener("click", function() {
		window.currentPage = 1;
		fetchCnsList(1);
	});
}

modifyButton.addEventListener('click', function() {

	if (confirm('정말로 수정하시겠습니까?')) {
		const memId = document.getElementById('mem-id').value;

		if (memId == null || memId == "") {
			alert('수정할 대상이 없습니다.');
			return;
		}


		const memName = document.getElementById('mem-name').value;
		const memNickname = document.getElementById('mem-nickname').value;
		const memEmail = document.getElementById('mem-email').value;
		const memPhone = document.getElementById('mem-phone').value;
		const memRole = document.getElementById('mem-role').value;

		let formData = new FormData();
		formData.set("memId", memId);
		formData.set("memName", memName);
		formData.set("memNickname", memNickname);
		formData.set("memRole", memRole);

		axios.post('/admin/umg/updateMemberInfo.do', formData)
			.then(res => {
				if (res.data != 1) {
					alert('수정 실패')
					return;
				} else {
					let formId = new FormData();
					formId.set("id", memId);
					userDetail(formId);
					fetchCnsList();
				}
			})
		alert('수정 완료');
	}

})

fetchCnsList();