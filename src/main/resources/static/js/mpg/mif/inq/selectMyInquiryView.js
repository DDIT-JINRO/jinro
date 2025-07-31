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