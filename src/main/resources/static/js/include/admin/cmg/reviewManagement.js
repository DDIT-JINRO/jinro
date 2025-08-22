function reviewManagement() {
	let irStatusObj = "";
	const selectReviewList = (page = 1) => {
		const status = document.querySelector("select[name='status']").value;
		const keyword = document.querySelector("input[name='keyword']").value;
		const order = document.querySelector("select[name='order']").value;
		const irStatus = document.querySelector("select[name='irStatus']").value;

		axios.get("/admin/cmg/selectReviewList", {
			params: {
				size: 10,
				currentPage: page,
				status: status,
				keyword: keyword,
				order: order,
				irStatus: irStatus
			}
		}).then(({ data }) => {
			const reviewList = document.querySelector("#reviewList");
			const reviewListCount = document.querySelector("#reviewList-count");
			const irStatusSelect = document.querySelector("select[name='irStatus']");

			const articlePage = data.articlePage;
			const contentList = articlePage.content;
			irStatusObj = data.irStatus;

			let reviewListHtml = "";

			if (contentList.length < 1 && keyword.trim() !== '') {
				reviewList.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
			}

			const optionsHtml = Object.entries(irStatusObj).map(([key, value]) => {
				return `<option value="${key}">${value}</option>`;
			}).join('');

			irStatusSelect.innerHTML = optionsHtml;

			contentList.forEach(value => {
				const date = new Date(value.irCreatedAt)
				const formattedDate = formatDate(date);
				const irStatus = irStatusObj[value.irStatus]
				let irType = ""

				switch (value.irType) {
					case "G02001":
						irType = "대학";
						break;
					case "G02002":
						irType = "기업";
						break;
				}

				reviewListHtml += `
					<tr class="review-item" data-ir-id="${value.irId}">
						<td>${value.irId}</td>
						<td>${value.memName}</td>
						<td>${irType}</td>
						<td>${value.targetName}</td>
						<td>${value.irApplication}</td>
						<td>${irStatus}</td>
						<td>${formattedDate}</td>
					</tr>
				`;
			});

			reviewListCount.innerHTML = articlePage.total;
			reviewList.innerHTML = reviewListHtml;
			renderPaginationReview(data.articlePage);
		}).catch((err) => {
			console.error(err);
		});
	}

	const renderPaginationReview = ({ startPage, endPage, currentPage, totalPages }) => {
		let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

		for (let p = startPage; p <= endPage; p++) {
			html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
		}

		html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

		const footer = document.querySelector('.reviewListPageination');
		if (footer) footer.innerHTML = html;
	}

	const filterData = () => {
		const sortButtons = document.querySelectorAll(".sort-button");

		if (sortButtons) {
			sortButtons.forEach(button => {
				button.addEventListener("click", function() {
					setActiveButton(this);
				});
			})
		}
	}

	const reviewListPangingFn = () => {
		const reviewListPaginationContainer = document.querySelector('.panel-footer.pagination');
		if (reviewListPaginationContainer) {

			reviewListPaginationContainer.addEventListener('click', e => {
				e.preventDefault();
				const link = e.target.closest('.page-link');

				if (!link || link.parentElement.classList.contains('disabled')) {
					return;
				}

				const page = parseInt(link.dataset.page, 10);

				if (!isNaN(page) && page > 0) {
					selectReviewList(page);
				}
			});
		}
	}
	const formatDate = (date) => {
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');

		return `${year}-${month}-${day}`;
	}

	filterData();
	reviewListPangingFn();
	selectReviewList();
}

reviewManagement();