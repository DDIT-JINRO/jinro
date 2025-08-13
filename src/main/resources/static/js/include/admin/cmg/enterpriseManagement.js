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
	axios.post('/admin/cmg/entDetail.do', formData)
		.then(res => {
			const { companyVO, filePath } = res.data;

			console.log(res.data);

			const detailAbout = document.getElementById('entDetailAbout');

			detailAbout.innerHTML = `${companyVO.cpDescription == null ? '기업정보가 없습니다.' : companyVO.cpDescription}`;

			const linkElement = document.getElementById('companyWebsiteLink');


			const companyWebsiteUrl = companyVO.cpWebsite;


			if (companyWebsiteUrl) {
				linkElement.href = companyWebsiteUrl;
			} else {
				linkElement.addEventListener('click', (e) => e.preventDefault());
			}

			if (filePath != null || filePath != "") {
				document.getElementById('entLogo').src = filePath || '-';
			} else {
				document.getElementById('entLogo').src = companyVO.cpImgUrl || '-';
			}

			document.getElementById('entName').innerHTML = companyVO.cpName || '-';
			document.getElementById('gubun').innerHTML = companyVO.cpScale || '-';
			document.getElementById('gubunName').innerHTML = companyVO.ccName || '-';
			document.getElementById('entId').innerHTML = `NO.${companyVO.cpId}` || '-';
			document.getElementById('entName2').innerText = companyVO.cpName || '-';
			document.getElementById('entAddress').innerText = companyVO.cpRegion || '-';

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

function imgView() {
	const cpLogoFile = document.getElementById('cpLogoFile');
	const cpLogoPreview = document.getElementById('cpLogoPreview');
	const btnChangeLogo = document.getElementById('btnChangeLogo');
	const imageUploadBox = document.getElementById('imageUploadBox');


	cpLogoFile.addEventListener('change', (event) => {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();

			reader.onload = (e) => {
				cpLogoPreview.src = e.target.result;
				cpLogoPreview.style.display = 'block';
				imageUploadBox.querySelector('i').style.display = 'none';
				imageUploadBox.querySelector('p').style.display = 'none';
			};
			reader.readAsDataURL(file);
		}
	});

	btnChangeLogo.addEventListener('click', () => {
		cpLogoFile.click();
	});
}

function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById('jibunAddress').value = data.jibunAddress;
		}
	}).open();
}

function save() {
	const saveBtn = document.getElementById('btnRegister');
	
	saveBtn.addEventListener('click', function() {
		const cpName = document.getElementById('cpName').value;
		
		let form = new FormData();
		form.set("cpName", cpName);
		axios.post('/empt/enp/enterprisePostingUpdate.do', form).then(res => {
			console.log(res);
		})


	})
}
save();
imgView();
entSearchFn();
fetchEntList();
entListPangingFn();
