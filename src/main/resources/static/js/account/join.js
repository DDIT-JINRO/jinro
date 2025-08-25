/**
 * 
 */

let isEmailVerified = false;
let isNicknameValid = false;
let isPasswordValid = false;
/*let isNameValid = false;*/
let isReqCheck = false;
let isPhoneValid = false;
let isInfoReqCheck = false;

const joinBtn = document.getElementById('joinBtn');
const phoneAccessBtn = document.getElementById('phoneAccess');

const submitBtn = document.getElementById('joinBtn');

phoneAccessBtn.addEventListener('click', function() {
	phoneAccess();
})


function phoneAccess() {
	console.log("phoneAccess 진입");

	IMP.init("imp52856231");

	IMP.certification(
		{
			// param
			channelKey: "channel-key-bf054284-0b27-4e7e-b48e-49e954bef4dc",
			// 주문 번호
			merchant_uid: "ORD20180131-0000011",
			// PC환경에서는 popup 파라미터가 무시되고 항상 true 로 적용됨
			popup: false,
		},
		function(rsp) {
			// callback
			if (rsp.success) {
				axios.post('/join/identityCheck.do', {

					imp_uid: rsp.imp_uid

				}).then(res => {
					console.log(res.data);
					const data = res.data.data;
					const userName = document.getElementById('name');
					const userPhone = document.getElementById('phone');
					const userBirth = document.getElementById('birth');
					const userGen = document.getElementById('gen');

					userName.value = data.name;
					userPhone.value = data.phone;
					userBirth.value = data.birthday;
					userGen.value = convertGenderToCode(data.gender);
					isPhoneValid = true;

				})

			} else {
				showConfirm2('본인인증에 실패하였습니다.',
					() => {
						return;
					},
					() => {

					}
				);
				return;
			}
		},
	);
}

function convertGenderToCode(gender) {

	const lowerCaseGender = gender.toLowerCase();
	if (lowerCaseGender === 'male') {
		return 'G11001';
	} else if (lowerCaseGender === 'female') {
		return 'G11002';
	} else {
		return null;
	}
}
function updateSubmitButtonState() {

	console.log("이메일", isEmailVerified);
	console.log("닉네임", isNicknameValid);
	console.log("비밀번호", isPasswordValid);
	console.log("이용약관", isReqCheck);
	console.log("전화번호", isPhoneValid);

	const allValid = isEmailVerified && isNicknameValid && isPasswordValid && isReqCheck && isPhoneValid && isInfoReqCheck;

	submitBtn.disabled = !allValid;

}

const chkTerms = document.getElementById("reqchk");
const chkPrivacy = document.getElementById("infochk");

chkTerms.addEventListener("change", () => {
	isReqCheck = chkTerms.checked;
	updateSubmitButtonState();
});

chkPrivacy.addEventListener("change", () => {
	isInfoReqCheck = chkPrivacy.checked;
	updateSubmitButtonState();
});

const nameInput = document.getElementById("name");
const nameError = document.getElementById("nameError");

/*nameInput.addEventListener("input", () => {
	const name = nameInput.value.trim();
	const nameRegex = /^[가-힣a-zA-Z]{2,20}$/;

	if (name === "") {
		nameError.className = "name-message error";
		nameError.textContent = "이름을 입력해주세요.";
		isNameValid = false;
	} else if (!nameRegex.test(name)) {
		nameError.className = "name-message error";
		nameError.textContent = "이름은 공백 없이 한글 또는 영문 2~20자로 입력해주세요.";
		isNameValid = false;
	} else {
		nameError.className = "name-message success";
		nameError.textContent = "사용 가능한 이름입니다.";
		isNameValid = true;
	}
	updateSubmitButtonState();
});*/


const passwordInput = document.getElementById("password");
const passwordConfirmInput = document.getElementById("passwordConfirm");
const errorMsg = document.getElementById("pwMismatchMsg");

passwordConfirmInput.addEventListener("input", function() {

	const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/;

	const pw = passwordInput.value;
	const pwConfirm = passwordConfirmInput.value;

	if (pw === "" && pwConfirm === "") {
		passwordError.className = "password-message error";
		passwordError.textContent = "";
		isPasswordValid = false;
	}
	else if (!passwordRegex.test(pw)) {
		passwordError.className = "password-message error";
		passwordError.textContent = "비밀번호는 최소 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.";
		isPasswordValid = false;
	}
	else if (pw !== pwConfirm) {
		passwordError.className = "password-message error";
		passwordError.textContent = "비밀번호가 일치하지않습니다.";
		isPasswordValid = false;
	}
	else {
		passwordError.className = "password-message success";
		passwordError.textContent = "사용 가능한 비밀번호 입니다.";
		isPasswordValid = true;
	}
	updateSubmitButtonState();

});

function emailCheck() {
	const emailInput = document.getElementById("email");
	const email = emailInput.value.trim();

	const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	if (email === "") {
		emailError.className = "email-message error";
		emailError.textContent = "이메일을 입력해주세요.";
		emailInput.focus();
		return;
	}

	if (!emailRegex.test(email)) {
		emailError.className = "email-message error";
		emailError.textContent = "올바른 이메일 형식이 아닙니다.";
		emailInput.focus();
		return;
	}

	fetch("/join/emailCheck.do", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify({ email: email })
	})
		.then(response => response.text())
		.then(result => {
			console.log(result)
			if (result === "failed") {
				emailError.className = "email-message error";
				emailError.textContent = "중복된 이메일입니다.";
				emailInput.focus();
			} else if (result === "access") {
				emailError.className = "email-message success";
				emailError.textContent = " ";
				showEmailModal(email);
			}
		})
		.catch(error => {
			console.error("이메일 중복 확인 오류:", error);
			message.textContent = "서버 오류가 발생했습니다.";
			message.style.color = "red";
		});
}

const modal = document.getElementById("emailModal");
const closeBtn = document.querySelector(".close");
const sendBtn = document.getElementById("sendEmailBtn");
const timerEl = document.getElementById("timer");

let timerInterval;

function showEmailModal(email) {
	document.getElementById("userEmailInfo").textContent = `${email}`;
	modal.style.display = "block";

	sendBtn.onclick = () => {
		sendBtn.disabled = true;
		sendBtn.textContent = "전송 중...";

		fetch("/join/sendMail.do", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ email: email })
		})
			.then(res => res.text())
			.then(result => {
				console.log(result);
				if (result === "sendOk") {
					startTimer(180); // 3분
					document.getElementById("authCodeBox").style.display = "block";
				} else {
					alert("메일 전송 실패");
				}
			})
			.catch(err => {
				alert("서버 오류가 발생했습니다.");
			})
			.finally(() => {
				sendBtn.disabled = false;
				sendBtn.textContent = "인증 메일 전송";
			})
	};
}

function startTimer(duration) {
	let time = duration;
	clearInterval(timerInterval);

	timerInterval = setInterval(() => {
		const minutes = String(Math.floor(time / 60)).padStart(2, '0');
		const seconds = String(time % 60).padStart(2, '0');
		timerEl.textContent = `인증 제한시간: ${minutes}:${seconds}`;

		if (--time < 0) {
			clearInterval(timerInterval);
			timerEl.textContent = "인증 시간이 만료되었습니다.";
		}
	}, 1000);
}

closeBtn.onclick = () => {
	modal.style.display = "none";
	clearInterval(timerInterval);
};
document.getElementById("verifyCodeBtn").onclick = () => {
	const code = document.getElementById("authCodeInput").value.trim();
	if (!code) {
		showCustomAlert("인증코드를 입력해주세요.");
		return;
	}

	fetch("/join/verify.do", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ email: email, code: code })
	})
		.then(res => res.text())
		.then(result => {
			if (result === "success") {
				alert("인증 완료");
				modal.style.display = "none";
				clearInterval(timerInterval);
				isEmailVerified = true;
			} else {
				showCustomAlert("인증 실패! 코드를 다시 확인해주세요.");
			}
		});
};

const nicknameCheck = document.getElementById("nicknameCheck");
const nicknameInput = document.getElementById("nickname");
const nicknameError = document.getElementById("nicknameError");

nicknameCheck.addEventListener("click", () => {
	const nickname = nicknameInput.value.trim();

	const nicknameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;

	if (!nickname) {
		nicknameError.textContent = "닉네임을 입력해주세요.";
		nicknameError.className = "nickname-message error";

		return;
	}

	if (!nicknameRegex.test(nickname)) {
		nicknameError.className = "nickname-message error";
		nicknameError.textContent = "닉네임은 한글, 영문, 숫자 조합 2~10자로 입력해주세요.";
		return;
	}

	fetch("/join/checkNickname.do", {
		method: "POST",
		headers: { "Content-Type": "application/json" },
		body: JSON.stringify({ nickname })
	})
		.then(res => res.json())
		.then(result => {
			if (result.duplicate) {
				nicknameError.className = "nickname-message error";
				nicknameError.textContent = "이미 사용 중인 닉네임입니다.";
			} else {
				nicknameError.className = "nickname-message success";
				nicknameError.textContent = "사용 가능한 닉네임입니다.";
				isNicknameValid = true;
			}
		})
		.catch(err => {
			console.error(err);
			nicknameError.textContent = "닉네임 중복 확인 중 오류 발생";
		});


});