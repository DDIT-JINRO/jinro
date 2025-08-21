function counselorManagement() {

	const hiddenInput = document.getElementById('comCalendarInput');
	const cnsCateChartDateTypeDaySel = document.getElementById('cnsCateChartDateType');
	cnsCateChartDateTypeDaySel.addEventListener('change', eventDateRangeSelect);

	/*cnsCardStatus();
	

	function cnsCardStatus() {

		axios.get('/admin/csmg/selectMonthlyCounselingStatList.do').then(res => {
			
			console.log(res.data);
			
			const {
				
			} = res.data;

			const monthlyCnsCountEl = document.getElementById('monthlyCnsCount');
			if (monthlyCnsCountEl) {
				animateNumberChange('dailyActiveUsersCount', dailyActiveUsers);
			}
			const dailyUsersRateEl = document.getElementById('dailyActiveUsersRate');
			if (dailyUsersRateEl) {
				const { symbol, className } = getRateStatus(dailyActiveUsersStatus);
				dailyUsersRateEl.textContent = `${symbol} ${dailyActiveUsersRate}%`;
				dailyUsersRateEl.classList.remove('public-span-increase', 'public-span-decrease', 'public-span-equal');
				dailyUsersRateEl.classList.add(className);
			}
		})
	}*/





	function eventDateRangeSelect(e) {
		// 공용으로 사용할 숨겨둔 input. flatpickr가 input 요소인지 점검한다고 함.(select에 못줌)
		const selectEl = e.target.nodeName == "SELECT" ? e.target : e.target.closest('select');
		const dateValue = selectEl.value;

		// 선택 값 (일별&월별&기간선택) 확인하고 무슨차트인지 중첩조건문에서 확인
		if (dateValue == 'daily') {
			if (selectEl.id == 'cnsCateChartDateType') {
				document.getElementById("cnsCateChartStartDay").value = '';
				document.getElementById("cnsCateChartEndDay").value = '';
				/*userOnlineChart("daily");*/
			} else if (selectEl.id == 'pageVisitChartDay') {
				document.getElementById("pageVisitChartStartDay").value = '';
				document.getElementById("pageVisitChartEndDay").value = '';
				/*pageVisitChart("daily");*/
			}
		} else if (dateValue == 'monthly') {
			if (selectEl.id == 'cnsCateChartDateType') {
				document.getElementById("cnsCateChartStartDay").value = '';
				document.getElementById("cnsCateChartEndDay").value = '';
				/*userOnlineChart("monthly");*/
			} else if (selectEl.id == 'pageVisitChartDay') {
				document.getElementById("pageVisitChartStartDay").value = '';
				document.getElementById("pageVisitChartEndDay").value = '';
				/*pageVisitChart("monthly");*/
			}
		} else if (dateValue == 'selectDays') {
			// flatpickr 중복 초기화 방지 (필요시)
			if (hiddenInput._flatpickr) {
				hiddenInput._flatpickr.destroy();
			}

			flatpickr(hiddenInput, {
				mode: "range",
				maxDate: "today",
				disable: [date => date > new Date()],
				positionElement: selectEl,	//open되는 위치는 변경가능. select요소를 넣어줌.
				onChange: function(selectedDates) {
					if (selectedDates.length === 2) {
						const startDate = selectedDates[0];
						const endDate = selectedDates[1];
						// yyyy-mm-dd 형식으로 포맷
						const formattedStartDate = formatDateCal(startDate);
						const formattedEndDate = formatDateCal(endDate);
						if (selectEl.id == 'userOnlineChartDay') {
							// hidden 날짜 input에 데이터 삽입
							document.getElementById('userOnlineChartStartDay').value = formattedStartDate;
							document.getElementById('userOnlineChartEndDay').value = formattedEndDate;
							// 데이터 조회&차트 출력함수 호출
							/*userOnlineChart('selectDays', formatDateRange(formattedStartDate, formattedEndDate));*/
							// 기존 차트가 있으면 삭제
							if (window.userOnlineChartInstance) {
								window.userOnlineChartInstance.destroy();
							}
						} else if (selectEl.id == 'pageVisitChartDay') {
							document.getElementById('pageVisitChartStartDay').value = formattedStartDate;
							document.getElementById('pageVisitChartEndDay').value = formattedEndDate;
							pageVisitChart('selectDays', formatDateRange(formattedStartDate, formattedEndDate));
							if (window.pageVisitChartInstance) {
								window.pageVisitChartInstance.destroy();
							}
						}
					}
				}
			});

			hiddenInput._flatpickr.open();
			hiddenInput._flatpickr.clear();
		}
	}

	function formatDateCal(d) {
		const y = d.getFullYear();
		const m = String(d.getMonth() + 1).padStart(2, '0');
		const day = String(d.getDate()).padStart(2, '0');
		return `${y}-${m}-${day}`;
	}

	function formatDateRange(fS, fE) {
		return `${fS} ~ ${fE} 기간`
	}



































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

				const listEl = document.getElementById('cnsList');
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

	function cnsListPangingFn() {
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
					fetchCnsList(page);
				}
			});
		}
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
	document.getElementById("cnsList").addEventListener("click", function(e) {
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
				const profileImgEl = document.getElementById('cns-profile-img');
				profileImgEl.src = filePath ? filePath : '/images/defaultProfileImg.png';


				document.getElementById('cns-id').value = memberDetail.memId || '-';
				document.getElementById('mem-name').value = memberDetail.memName || '-';
				document.getElementById('cns-nickname').value = memberDetail.memNickname || '-';
				document.getElementById('mem-email').value = memberDetail.memEmail || '-';
				document.getElementById('mem-phone').value = memberDetail.memPhoneNumber || '-';
				document.getElementById('mem-gen').value = memberGender(memberDetail.memGen) || '-';
				document.getElementById('mem-birth').value = formatDate(memberDetail.memBirth) || '-';

				const selectElement = document.getElementById("mem-role");

				for (let i = 0; i < selectElement.options.length; i++) {
					if (selectElement.options[i].value === memberDetail.memRole) {
						selectElement.options[i].selected = true;
						break;
					}
				}


				document.getElementById('mem-warn-count').textContent = `${counseling}회`;
				document.getElementById('mem-ban-count').textContent = `${vacByCns}회`;
				document.getElementById('mem-avg-count').textContent = `${avgRate == 0 ? "후기없음" : avgRate + "점"}`;

			})
			.catch(error => {
				console.error('회원 정보 불러오기 실패', error);
			});
	}

	function searchCnsFn() {
		const searchCnsBtn = document.querySelector(".searchCnsBtn");
		if (searchCnsBtn) {
			searchCnsBtn.addEventListener("click", function() {
				window.currentPage = 1;
				fetchCnsList(1);
			});
		}
	}



	function modifyBtn() {
		const modifyButton = document.getElementById('cnsModify');

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
	}

	searchCnsFn();
	cnsListPangingFn();
	modifyBtn();
	fetchCnsList();

}

counselorManagement();