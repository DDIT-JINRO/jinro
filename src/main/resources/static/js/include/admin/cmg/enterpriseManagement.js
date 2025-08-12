/**
 * 
 */

function fetchEntList(page = 1) {

	const pageSize = 10;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const status = document.querySelector('select[name="status"]').value;

	axios.get('/admin/cmg/getEntList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status

		}
	})
		.then(({ data }) => {

			console.log(data);

			const countEl = document.getElementById('entList-count');
			if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();

			const listEl = document.getElementById('entList');
			if (!listEl) return;

			if (data.content.length < 1 && keyword.trim() !== '') {
				listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
			} else {
				const rows = data.content.map(item => `
								  <tr>
									<td>${item.cpId}</td>
									<td><img class="entImg" src="${item.cpImgUrl}"></img></td>
									<td>${item.cpName}</td>
									<td>${item.cpRegion}</td>
								   
								  </tr>`).join('');
				listEl.innerHTML = rows;
			}
			renderPaginationEnt(data);
		})
		.catch(err => console.error('유저 목록 조회 중 에러:', err));
}


function renderPaginationEnt({ startPage, endPage, currentPage, totalPages }) {
	let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

	for (let p = startPage; p <= endPage; p++) {
		html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
	}

	html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

	const footer = document.querySelector('.entListPage');
	if (footer) footer.innerHTML = html;
}

function entListPangingFn() {
	const cnsListPaginationContainer = document.querySelector('.panel-footer.pagination');
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
				fetchEntList(page);
			}
		});
	}
}

document.getElementById("entList").addEventListener("click", function(e) {
	const tr = e.target.closest("tr");
	if (!tr) return;

	const tds = tr.querySelectorAll("td");
	const id = tds[0].textContent.trim();

	let formData = new FormData();
	formData.set("id", id);

	entDetail(formData);

});


function entDetail(formData) {
	axios.get('/admin/cmg/entDetail.do', formData)
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

		})
		.catch(error => {
			console.error('기업 정보 불러오기 실패', error);
		});
}

function entSearchFn() {

	const searchBtn = document.getElementById('btnSearch');
	if (searchBtn) {
		searchBtn.addEventListener("click", function() {
			window.currentPage = 1;
			fetchEntList(1);
		});
	}

}

document.querySelectorAll(".tab-btn").forEach(btn => {
	btn.addEventListener("click", () => {
		document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
		document.querySelectorAll(".tab-content").forEach(c => c.classList.remove("active"));

		btn.classList.add("active");
		document.getElementById(btn.dataset.tab).classList.add("active");
	});
});

entSearchFn();
fetchEntList();
entListPangingFn();
