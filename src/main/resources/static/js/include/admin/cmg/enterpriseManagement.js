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
									<td>${item.memEmail}</td>
									<td>${item.memNickname}</td>
								   
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

fetchEntList();
entListPangingFn();

/*function fetchCnsList(page = 1) {
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
}*/