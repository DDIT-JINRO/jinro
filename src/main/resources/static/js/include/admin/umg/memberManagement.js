
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
			fetchUserList(page);
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


			document.getElementById('mem-warn-count').textContent = `${countVO.warnCount}회`;
			document.getElementById('mem-ban-count').textContent = `${countVO.banCount}회`;


			const interests = interestCn && interestCn.length > 0 ? interestCn.join(', ') : '없음';
			document.getElementById('mem-interests').textContent = interests;

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



modifyFn();
addBtnFn();
profileUploadFn();
fetchUserList();