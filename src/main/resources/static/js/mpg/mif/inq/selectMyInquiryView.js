/**
 * 
 */

document.addEventListener("DOMContentLoaded", () => {
	try {
		if (memId == "anonymousUser") {
			alert("로그인 후 이용 가능한 페이지 입니다.")
			throw new Error("로그인 후 사용 가능한 페이지 입니다.");
		}
	} catch {
		sessionStorage.setItem("redirectUrl", location.href);
		location.href = "/login";
	}

	const moveSubBtn = document.querySelector("#move-sub-btn");
	moveSubBtn.addEventListener("click", () => {
		location.href = "/mpg/pay/selectPaymentView.do";
	});

});

document.addEventListener("DOMContentLoaded", () => {
	const changePhotoBtn = document.querySelector("#change-photo-btn");
	const changePhotoInput = document.querySelector("#change-photo-input");

	changePhotoBtn.addEventListener("click", () => {
		changePhotoInput.click();
	});

	changePhotoInput.addEventListener("change", handleImgFileSelect)
})

const handleImgFileSelect = (event) => {
	const files = event.target.files;

	if (files.length === 0) {
		alert("파일이 선택되지 않았습니다.");
		return;
	}

	const selectedFile = files[0];

	if (selectedFile.type !== "image/jpeg" && selectedFile.type !== "image/png") {
		alert("파일은 png 또는 jpg 형식만 가능합니다.");
		return;
	}

	let formData = new FormData();
	formData.append("profileImg", selectedFile);

	fetch("updateProfileImg.do", {
		method: "POST",
		body: formData
	}).then(response => {
		if (response.ok) {
			return response.json();
		}
	}).then(({ result }) => {
		if (result === "success") {
			alert("정상적으로 프로필 이미지가 변경되었습니다.")
			location.reload();
		}
	}).catch(error => {
		console.error("프로필 이미지 업로드 중 에러 발생 : ", error);
		alert(error.message);
	})

}

document.addEventListener("DOMContentLoaded", () => {
	const updateInputs = document.querySelectorAll(".info-grid input");
	const submitBtn = document.querySelector("#info-update-btn");

	updateInputs.forEach((input) => {
		input.addEventListener("input", () => {
			checkChange(input, submitBtn);
		});
	});

	const modalOverlay = document.querySelector('#password-modal-overlay');
	const closeModalBtn = modalOverlay.querySelector('.modal-close-btn');
	const passwordInput = document.querySelector('#password-check-input');
	const errorMsg = document.querySelector('#modal-error-msg');
	const passwordConfirmBtn = document.querySelector("#password-confirm-btn");

	const openModal = () => {
		modalOverlay.classList.add('show');
	}

	const closeModal = () => {
		modalOverlay.classList.remove('show');
		passwordInput.value = '';
		errorMsg.textContent = '';
	};

	submitBtn.addEventListener('click', function (event) {
		event.preventDefault();
		openModal();
	});

	closeModalBtn.addEventListener('click', closeModal);

	modalOverlay.addEventListener('click', function (event) {
		if (event.target === modalOverlay) {
			closeModal();
		}
	});

	passwordConfirmBtn.addEventListener("click", () => {
		const password = passwordInput.value;

		if (!password) {
			errorMsg.textContent = '비밀번호를 입력해주세요.';
			return;
		}

		passwordCheckAPI(password, errorMsg, passwordInput, closeModal);
	});
});

const checkChange = (input, submitBtn) => {
	let isChanged = false;

	if (input.value !== input.dataset.initValue) {
		isChanged = true;
	}

	submitBtn.disabled = !isChanged;
}

const passwordCheckAPI = (password, errorMsg, passwordInput, closeModal) => {
	const mainForm = document.querySelector('.my-info-card form');

	fetch("checkPassword.do", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({
			password: password
		})
	}).then((response) => {
		if (response.ok) {
			return response.json();
		} else {
			throw new Error("인증 중 오류가 발생했습니다. 다시 시도해 주세요.");
		}
	}).then(({ result }) => {
		if (result === "success") {
			closeModal();
			mainForm.submit();
		} else {
			errorMsg.textContent = result.message || '비밀번호가 일치하지 않습니다.';
			passwordInput.value = ''; // 입력 필드 초기화
			passwordInput.focus();
		}
	}).catch((error) => {
		errorMsg.textContent = error.message || '인증 중 오류가 발생했습니다. 다시 시도해 주세요.';
	});
}

document.addEventListener("DOMContentLoaded", () => {
	const interestsUpdateBtn = document.querySelector("#interests-update-btn");
	const modalOverlay = document.querySelector('#interest-modal-overlay');
	const closeModalBtn = modalOverlay.querySelector('.modal-close-btn');

	const openModal = () => {
		modalOverlay.classList.add('show');
	}

	const closeModal = () => {
		modalOverlay.classList.remove('show');
		passwordInput.value = '';
		errorMsg.textContent = '';
	};

	interestsUpdateBtn.addEventListener('click', function (event) {
		event.preventDefault();
		openModal();
	});

	closeModalBtn.addEventListener('click', closeModal);

	modalOverlay.addEventListener('click', function (event) {
		if (event.target === modalOverlay) {
			closeModal();
		}
	});

});

document.addEventListener('DOMContentLoaded', function () {
	// 필터 키워드
	const filterCheckboxes = document.querySelectorAll('.com-filter-item input[type="checkbox"]');

	// 선택 필터 영역
	const selectedFiltersContainer = document.querySelector('.com-selected-filters');

	// 초기화 버튼
	const resetButton = document.querySelector('.com-filter-reset-btn');

	// 필터 태그 추가
	const createFilterTag = (text) => {
		const filterTag = `<span class="com-selected-filter" data-filter="${text}">${text}</span>`;

		selectedFiltersContainer.innerHTML += filterTag;
	};

	// 필터 태그 삭제
	const removeFilterTag = (text) => {
		const tagToRemove = selectedFiltersContainer.querySelector(`[data-filter="${text}"]`);
		if (tagToRemove) {
			selectedFiltersContainer.removeChild(tagToRemove);
		}
	};

	// 체크박스 변경 시 이벤트 처리
	filterCheckboxes.forEach(checkbox => {
		checkbox.addEventListener('change', (e) => {
			const labelText = e.target.nextElementSibling.textContent;
			if (e.target.checked) {
				createFilterTag(labelText);
			} else {
				removeFilterTag(labelText);
			}
		});
	});

	// 초기화 버튼 클릭 시 이벤트 처리
	if (resetButton) {
		resetButton.addEventListener('click', () => {
			filterCheckboxes.forEach(checkbox => {
				checkbox.checked = false;
			});

			selectedFiltersContainer.innerHTML = '';
		});
	}
});