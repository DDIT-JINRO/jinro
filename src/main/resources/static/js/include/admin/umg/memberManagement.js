window.currentPage = 1;
window.currentNoticeId = null;

ctx = document.getElementById("memberChart").getContext("2d");
memberChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월'],
			datasets: [
				{
					label: '신규 가입자',
					data: [20, 35, 28, 40, 22, 55, 33, 45],
					backgroundColor: 'rgba(75, 192, 192, 0.6)',
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 1
				},
				{
					label: '탈퇴자',
					data: [5, 8, 6, 10, 4, 12, 7, 9],
					backgroundColor: 'rgba(255, 99, 132, 0.6)',
					borderColor: 'rgba(255, 99, 132, 1)',
					borderWidth: 1
				}
			]
		},
		options: {
			responsive: true,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: '2025년 월별 회원 이용 통계'
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: '회원 수'
					}
				}
			}
		}
	});

function fetchUserList(page = 1) {
	const pageSize = 5;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const status = document.querySelector('select[name="status"]').value;

	axios.get('/admin/umg/getMemberList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status,
			memRole: 'R01001'
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
					/*<td>${item.memPhoneNumber}</td>
		            <td>${formatDateMMDD(item.memBirth)}</td>
		            <td>${convertLoginType(item.loginType)}</td>
		            <td>${item.memPoint}</td>*/

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

searchBtn = document.querySelector(".btn-save");
if (searchBtn) {
	searchBtn.addEventListener("click", function() {
		window.currentPage = 1;
		fetchUserList(1);
	});
}

document.getElementById("userList").addEventListener("click", function(e) {
	const tr = e.target.closest("tr");
	if (!tr) return;

	const tds = tr.querySelectorAll("td");
	const id = tds[0].textContent.trim();

	let formData = new FormData();
	formData.set("id", id);

	axios.post('/admin/umg/getMemberDetail.do', formData)
		.then(res => {
			const { memberDetail, filePath, countVO, interestCn } = res.data;

			// 프로필 이미지
			const profileImgEl = document.getElementById('member-profile-img');
			profileImgEl.src = filePath ? filePath : '/images/defaultProfileImg.png';

			// 기본 정보
			document.getElementById('mem-name').value = memberDetail.memName || '-';
			document.getElementById('mem-nickname').value = memberDetail.memNickname || '-';
			document.getElementById('mem-email').value = memberDetail.memEmail || '-';
			document.getElementById('mem-phone').value = memberDetail.memPhoneNumber || '-';
			document.getElementById('mem-gen').value = memberGender(memberDetail.memGen) || '-';
			document.getElementById('mem-birth').value = formatDateMMDD(memberDetail.memBirth) || '-';
			/*document.getElementById('mem-points').textContent = memberDetail.memPoint + '점';*/

			// 패널티 정보
			document.getElementById('mem-warn-count').textContent = `${countVO.warnCount}회`;
			document.getElementById('mem-ban-count').textContent = `${countVO.banCount}회`;

			// 관심 분야 (배열 -> 문자열)
			const interests = interestCn && interestCn.length > 0 ? interestCn.join(', ') : '없음';
			document.getElementById('mem-interests').textContent  = interests;

		})
		.catch(error => {
			console.error('회원 정보 불러오기 실패', error);
		});
});



fetchUserList();