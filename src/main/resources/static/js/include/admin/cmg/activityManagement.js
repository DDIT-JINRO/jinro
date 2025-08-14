/**
 *
 */
function activityManagementInit () {
	function fetchActList(page = 1) {
		
		const pageSize = 10;
		const category = document.querySelector('select[name="act-mng-category"]').value;
		const keyword = document.querySelector('input[name="keyword"]').value;
		
		const paramData = {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			category: category
		}
		
		axios.get('/admin/cmg/selectActList.do', {
			params: paramData
		}).then(({data}) => {
			console.log(data);
			const {articlePage, contestTypeList} = data;
			
			if(data.success) {
				let contestTypeHtml = '';
				contestTypeList.map(contestType => {
					contestTypeHtml += `<option value="${contestType.ccId}">${contestType.ccName}</option>`
				})
				document.getElementById('contestType').innerHTML = contestTypeHtml;
				
				const countEl = document.getElementById('actList-count');
				if (countEl) {
					countEl.textContent = parseInt(articlePage.total, 10).toLocaleString();
				}
					
				const listEl = document.getElementById('actList');
				if (!listEl) return;
				
				if (articlePage.content.length < 1 && keyword.trim() !== '') {
					listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
				} else {
					const rows = articlePage.content.map(item =>{
						
						const formattedDate = formatDateOne(item.contestCreatedAt);
						
						return `<tr>
									<td>${item.contestId}</td>
									<td>${item.contestTitle}</td>
									<td>${formattedDate}</td>
									<td>${item.contestHost}</td>
								</tr>`;
						}).join('');
					listEl.innerHTML = rows;
				}
				renderPaginationAct(data.articlePage);
			}
		})
		.catch(err => console.error('목록 조회 중 에러:', err));
	}
	
	function renderPaginationAct({ startPage, endPage, currentPage, totalPages }) {
		let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

		for (let p = startPage; p <= endPage; p++) {
			html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
		}

		html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

		const footer = document.querySelector('.actListPage');
		if (footer) footer.innerHTML = html;
	}
	
	function actListPangingFn() {
		const actListPaginationContainer = document.querySelector('.panel-footer.pagination');
		if (actListPaginationContainer) {
			
			actListPaginationContainer.addEventListener('click', e => {
				e.preventDefault();
				const link = e.target.closest('a[data-page]');
				
				if (!link || link.parentElement.classList.contains('disabled')) {
					return;
				}

				const page = parseInt(link.dataset.page, 10);

				if (!isNaN(page) && page > 0) {
					fetchActList(page);
				}
			});
		}
	}

	document.getElementById("actList").addEventListener("click", function(e) {
		const tr = e.target.closest("tr");
		if (!tr) return;

		const tds = tr.querySelectorAll("td");
		const id = tds[0].textContent.trim();

		let formData = new FormData();
		formData.set("id", id);

		actDetail(formData);
	});

	
	const formatDateOne = (date) => {
		return date.substring(0, 10).replaceAll('-', '. ');
	}
	
	const formatDateTwo = (date) => {
		return date.substring(0, 10);
	}

	
	/*resetForm();
	entSearchFn();*/
	actSave();
	imgView();
	actListPangingFn();
	fetchActList();
}


activityManagementInit();