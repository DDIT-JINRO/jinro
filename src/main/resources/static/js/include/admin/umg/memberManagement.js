function memberManagement() {


	let currentSortOrder = 'asc';
	let currentSortBy = 'id';
	let currentPage = 1;
	// userListInFilter 변수를 제거하고, fetchUserList 호출 시점에 직접 값을 가져오도록 변경

	const userListIdBtn = document.getElementById('userListId');
	const userListNameBtn = document.getElementById('userListName');
	const userListEmailBtn = document.getElementById('userListEmail');

	userListIdBtn.addEventListener('click', function() {
		handleSortClick("id");
		setActiveButton(this);
	})
	userListNameBtn.addEventListener('click', function() {
		handleSortClick("name");
		setActiveButton(this);
	})
	userListEmailBtn.addEventListener('click', function() {
		handleSortClick("email");
		setActiveButton(this);
	})

	// 1. change 이벤트에서 fetchUserList를 호출하도록 수정
	document.getElementById('userListStatus').addEventListener('change', function() {
		// 상태 필터가 변경되면 1페이지로 돌아가서 새로운 목록을 불러옴
		fetchUserList(1);
	});

	function handleSortClick(sortBy) {

		if (currentSortBy !== sortBy) {
			currentSortBy = sortBy;
			currentSortOrder = 'asc';
		} else {

			currentSortOrder = currentSortOrder === 'asc' ? 'desc' : 'asc';
		}

		// 2. handleSortClick에서 fetchUserList를 호출할 때 inFilter 값을 가져와서 전달
		const userListInFilter = document.getElementById('userListStatus').value;
		fetchUserList(currentPage, currentSortBy, currentSortOrder, userListInFilter);
	}

	function profileUploadFn() {
		const profileUpload = document.getElementById('profileUpload');
		const profileImage = document.querySelector('.profile-wrapper img');

		profileUpload.addEventListener('change', function(event) {
			const file = event.target.files[0];
			if (file.type !== "image/jpeg" && file.type !== "image/png") {
				alert("파일은 png 또는 jpg 형식만 가능합니다.");
				return;
			}
			if (!file) {
				return;
			}
			const reader = new FileReader();
			reader.onload = function(e) {
				profileImage.src = e.target.result;
			};
			reader.readAsDataURL(file);
		});
	}

	// 3. fetchUserList 함수의 inFilter 파라미터를 추가하고, 함수 시작 시점에 값을 가져오도록 수정
	function fetchUserList(page = 1, sortBy = 'id', sortOrder = 'asc', inFilter = '') {
		currentPage = page;

		const pageSize = 10;
		const keyword = document.querySelector('input[name="keyword"]').value;
		const status = document.querySelector('select[name="status"]').value;
		// fetchUserList 함수 내에서 userListStatus의 최신 값을 가져오도록 수정
		const userListInFilter = document.getElementById('userListStatus').value;

		axios.get('/admin/umg/getMemberActivityList.do', {
			params: {
				currentPage: page,
				size: pageSize,
				keyword: keyword,
				status: status, // 이 status는 activityFilter의 값일 수 있으므로 확인 필요
				sortBy: sortBy,
				sortOrder: sortOrder,
				inFilter: userListInFilter // 수정된 inFilter 값을 전달
			}
		})
			.then(({ data }) => {

				const countEl = document.getElementById('userList-count');
				if (countEl) countEl.textContent = parseInt(data.total, 10).toLocaleString();
				const listEl = document.getElementById('userList');
				if (!listEl) return;
				if (data.content.length < 1 && keyword.trim() !== '') {
					listEl.innerHTML = `<tr><td colspan='4' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
				} else {
					const rows = data.content.map(item => `
					<tr>
						<td>${item.memId}</td>
						<td>${item.memName}</td>
						<td>${item.memEmail}</td>
						<td>${renderStatus(item.activityStatus)}</td>
					</tr>`).join('');
					listEl.innerHTML = rows;
				}
				renderPagination(data);
			})
			.catch(err => console.error('유저 목록 조회 중 에러:', err));
	}

	function renderStatus(status) {
		let statusClass;
		let statusText;

		switch (status) {
			case 'ONLINE':
				statusClass = 'dot-status-green';
				statusText = '활동중';
				break;
			case 'OFFLINE':
				statusClass = 'dot-status-gray';
				statusText = '비활동';
				break;
			case 'SUSPENDED':
				statusClass = 'dot-status-red';
				statusText = '정지상태';
				break;
			case 'NEVER_LOGIN':
				statusClass = 'dot-status-orange';
				statusText = '신규 가입자';
				break;
			default:
				statusClass = '';
				statusText = '';
		}

		return `
	        <span class="dot-status ${statusClass}"></span>
	        ${statusText}
	    `;
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

	userListPaginationContainer = document.querySelector('.panel-footer.pagination');
	if (userListPaginationContainer) {
		userListPaginationContainer.addEventListener('click', e => {
			const link = e.target.closest('a[data-page]');
			if (!link || link.parentElement.classList.contains('disabled')) {
				e.preventDefault();
				return;
			}
			e.preventDefault();
			const page = parseInt(link.dataset.page, 10);
			if (!isNaN(page) && page > 0) {
				const userListInFilter = document.getElementById('userListStatus').value;
				fetchUserList(page, currentSortBy, currentSortOrder, userListInFilter);
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
		userDetail(formData);
		userDetailBoardList(id);
	});


	function userDetail(formData) {
		axios.post('/admin/umg/getMemberDetail.do', formData)
			.then(res => {
				const { memberDetail, filePath, countVO, interestCn } = res.data;
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
				document.getElementById('mem-warn-count').value = `${countVO.warnCount}회`;
				document.getElementById('mem-ban-count').value = `${countVO.banCount}회`;
				const interests = interestCn && interestCn.length > 0 ? interestCn.join(', ') : '없음';
				document.getElementById('mem-interests').innerHTML = `<span class="keyword-badge">${interests}</span>`;
			})
			.catch(error => {
				console.error('회원 정보 불러오기 실패', error);
			});
	}

	function addBtnFn() {
		const addButton = document.querySelector('.add-btn');
		addButton.addEventListener('click', function() {
			const formData = new FormData();
			const email = document.getElementById('insertEmail').value.trim();
			const name = document.getElementById('insertName').value.trim();
			const nickname = document.getElementById('insertNickname').value.trim();
			const password = document.getElementById('insertPassword').value;
			const phone = document.getElementById('insertPhone').value.trim();
			const role = document.getElementById('insertRole').value.trim();
			const birth = document.getElementById('insertBirth').value.trim();
			const gender = document.getElementById('insertGen').value.trim();
			const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
			const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/;
			const phoneRegex = /^010-\d{4}-\d{4}$/;
			const nameRegex = /^[가-힣a-zA-Z]{2,20}$/;
			const nicknameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;
			if (!emailRegex.test(email)) {
				alert('올바른 이메일 형식을 입력해주세요.');
				return;
			}
			if (!nameRegex.test(name)) {
				alert('이름은 공백 없이 한글 또는 영문 2~20자로 입력해주세요.');
				return;
			}
			if (!nickname) {
				alert('닉네임을 입력해주세요.');
				return;
			}
			if (!nicknameRegex.test(nickname)) {
				alert('닉네임은 한글, 영문, 숫자 조합 2~10자로 입력해주세요.');
				return;
			}
			if (!passwordRegex.test(password)) {
				alert('비밀번호는 8~16자의 영문, 숫자, 특수문자를 포함해야 합니다.');
				return;
			}
			if (!phoneRegex.test(phone)) {
				alert('연락처는 010-XXXX-XXXX 형식으로 입력해주세요.');
				return;
			}
			formData.append('memRole', role);
			formData.append('memName', name);
			formData.append('memNickname', nickname);
			formData.append('memEmail', email);
			formData.append('memPhoneNumber', phone);
			formData.append('memGen', gender);
			formData.append('memBirth', birth);
			formData.append('memPassword', password);
			const profileFile = document.getElementById('profileUpload').files[0];
			if (profileFile) {
				if (profileFile.type !== "image/jpeg" && profileFile.type !== "image/png") {
					alert("파일은 png 또는 jpg 형식만 가능합니다.");
					return;
				}
			}
			if (profileFile) {
				formData.append('profileImage', profileFile);
			} else {
				formData.append('profileImage', null);
			}
			axios.post('/admin/umg/insertUserByAdmin.do', formData).then(res => {
				if (res.data == 'success') {
					alert('유저 등록 성공');
				} else {
					alert('등록 중 오류 발생');
				}
			})
		});
	}


	function modifyFn() {
		const modifyButton = document.getElementById('userModify');
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
							fetchUserList();
						}
					})
				alert('수정 완료');
			}
		})
	}


	function emailDbCkFn() {
		const emailDoubleCheckBtn = document.getElementById('emailDoubleCheck');
		emailDoubleCheckBtn.addEventListener('click', function() {
			const email = document.getElementById('insertEmail').value;
			const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
			if (email == null || email == "") {
				alert('이메일을 입력하세요.')
				return;
			} else if (!emailRegex.test(email)) {
				alert("올바른 이메일 형식을 입력하세요.")
				return;
			}
			let formData = new FormData();
			formData.set("email", email);
			axios.post('/admin/umg/selectEmailByAdmin.do', formData)
				.then(res => {
					alert(res.data);
				})
		})
	}


	function nicknameDbCkFn() {
		const nicknameDoubleCheckBtn = document.getElementById('nicknameDoubleCheck');
		nicknameDoubleCheckBtn.addEventListener('click', function() {
			const nickname = document.getElementById('insertNickname').value;
			const nicknameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;
			if (nickname == null || nickname == "") {
				alert('닉네임을 입력하세요.')
				return;
			} else if (!nicknameRegex.test(nickname)) {
				alert("닉네임은 한글, 영문, 숫자 조합 2~10자로 입력해주세요.")
				return;
			}
			let formData = new FormData();
			formData.set("nickname", nickname);
			axios.post('/admin/umg/selectNicknameByAdmin.do', formData)
				.then(res => {
					alert(res.data);
				})
		})
	}

	function searchUserFn() {
		const searchCnsBtn = document.querySelector(".searchUserBtn");
		if (searchCnsBtn) {
			searchCnsBtn.addEventListener("click", function() {
				window.currentPage = 1;
				fetchUserList(1);
			});
		}
	}

	function userDetailBoardList(userId, page = 1, sortBy = 'id', sortOrder = 'asc') {
		
		const pageSize = 5;
		
		axios.get(`/admin/umg/getMemberDetailBoardList.do`, {
			params: {
				currentPage: page,
				size: pageSize,
				userId: userId,
				sortBy: sortBy,
				sortOrder: sortOrder,
				
			}
		}).then(res => {
			console.log(res.data);
		})

	}

	searchUserFn();
	modifyFn();
	addBtnFn();
	profileUploadFn();
	fetchUserList();
}

memberManagement();