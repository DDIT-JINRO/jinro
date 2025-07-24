<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/account/join/joinPage.css">
<!-- 스타일 여기 적어주시면 가능 -->

<div>
	<div class="public-wrapper">
		<!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
		<!-- 		<div class="tab-container" id="tabs"> -->
		<!-- 		    <div class="tab ">대학 검색</div> -->
		<!-- 		    <div class="tab active">학과 정보</div> -->
		<!-- 		    <div class="tab">입시 정보</div> -->
		<!--   		</div> -->
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div id="emailModal" class="modal">
			<div class="modal-content">
				<span class="close">&times;</span>
				<h2>이메일 인증</h2>
				<p id="emailStatusMessage"
					style="color: green; font-weight: 600; margin: 10px 0;">사용 가능한
					이메일입니다.</p>
				<p id="userEmailInfo"></p>
				<br />
				<button id="sendEmailBtn">인증 메일 전송</button>
				<div id="authCodeBox" style="display: none; margin-top: 10px;">
					<input type="text" id="authCodeInput" placeholder="인증코드 입력" />
					<button id="verifyCodeBtn">확인</button>
				</div>
				<p id="timer" style="margin-top: 10px;"></p>
			</div>
		</div>
		<div class="public-wrapper-main">
			<div class="dpfx2">
				<div class="signup-container2">
					<div class="top-text">
						<span>이미 커리어패스 회원이신가요?</span> <a href="/login">로그인</a>
					</div>
				</div>
			</div>
			<div class="dpfx">
				<div class="signup-container">
					<form>
						<div class="input-row">
							<label for="email">이메일</label> <input class="inputWt"
								type="email" id="email" placeholder="이메일을 입력해주세요." />
							<button type="button" onclick="emailCheck()">이메일 인증</button>
						</div>
						<p id="emailError"></p>

						<div class="input-row">
							<label for="nickname">닉네임</label> <input type="text"
								class="inputWt" id="nickname" placeholder="닉네임을 입력해주세요." />

							<button id="nicknameCheck" type="button">중복 확인</button>
						</div>
						<p id="nicknameError"></p>

						<div class="input-row2">
							<label for="password">비밀번호</label> <input type="password"
								class="inputWt2" class="line-flex" id="password"
								placeholder="비밀번호를 입력해주세요." />
						</div>
						<div class="input-row2">
							<label for="passwordConfirm">비밀번호 확인</label> <input
								class="inputWt2" type="password" id="passwordConfirm"
								placeholder="비밀번호를 한 번 더 입력해주세요." />
						</div>
						<p id="passwordError"></p>
						<div class="input-row2">
							<label for="name">이름</label> <input class="inputWt2" type="text"
								id="name" placeholder="이름을 입력해주세요." />
						</div>
						<p id="nameError"></p>
						<div class="input-row">
							<label for="phone">핸드폰 번호</label> <input type="tel"
								class="inputWt" id="phone" placeholder="핸드폰 번호를 입력해주세요." />
							<button type="button">전화번호 인증</button>
						</div>

						<!-- 						<div class="input-gen"> -->
						<!-- 							<div class="gender-wt">성별</div> -->
						<!-- 							<div class="gender-row"> -->
						<!-- 								<label><input type="radio" name="gender" value="male" -->
						<!-- 									checked /> 남</label> <label><input type="radio" name="gender" -->
						<!-- 									value="female" /> 여</label> -->
						<!-- 							</div> -->
						<!-- 						</div> -->

						<!-- 						<label for="birth">생년월일</label> <input type="date" id="birth" -->
						<!-- 							placeholder="생년월일" /> -->

						<div class="checkboxes">
							<div class="reqAgree">
								<label><input id="reqchk" type="checkbox" /> 이용약관 동의 <span
									class="minimal" style="color: red;">필수</span></label>
								<div class="agreeBorder">내용보기</div>
							</div>

							<div class="solAgree">
								<label><input id="infochk" type="checkbox" /> 개인정보 수집 및 이용 동의 <span
									class="minimal" style="color: red;">필수</span></label>
								<div class="agreeBorder">내용보기</div>
							</div>
							<div class="eventAgree">
								<label><input type="checkbox" /> 이벤트 등 프로모션 메일 수신 동의 <span
									class="minimal">선택</span></label>
								<div class="agreeBorder">내용보기</div>
							</div>
							<div class="eventAgree">
								<label><input type="checkbox" /> 카카오톡 수신 동의 <span
									class="minimal">선택</span></label>
								<div class="agreeBorder">내용보기</div>
							</div>
						</div>
						<div class="tooltip-wrapper">
							<button class="btn-signup" type="submit" disabled="disabled">회원가입</button>
							<span class="tooltip-text">모든 필수사항을 입력해주세요</span>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	let isEmailVerified = false;
	let isNicknameValid = false;
	let isPasswordValid = false;
	let isNameValid = false;
// 	let isPhoneValid = false;
	let isReqCheck = false;
	let isInfoCheck = false;

	const submitBtn = document.querySelector(".btn-signup");

	function updateSubmitButtonState() {
	  
      console.log(isEmailVerified);
      console.log(isNicknameValid);
      console.log(isPasswordValid);
      console.log(isNameValid);
      console.log(isReqCheck);
      console.log(isInfoCheck);
	  
	  const allValid = isEmailVerified && isNicknameValid && isPasswordValid && isNameValid && isReqCheck && isInfoCheck;
	  
	  submitBtn.disabled = !allValid;
	  
	}
	
	const chkTerms = document.getElementById("reqchk");
	const chkPrivacy = document.getElementById("infochk");

	chkTerms.addEventListener("change", () => {
	  isReqCheck = chkTerms.checked;
	  updateSubmitButtonState();
	});

	chkPrivacy.addEventListener("change", () => {
	  isInfoCheck = chkPrivacy.checked;
	  updateSubmitButtonState();
	});
	
	const nameInput = document.getElementById("name");
	const nameError = document.getElementById("nameError");

	nameInput.addEventListener("input", () => {
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
	});
	
	
	const passwordInput = document.getElementById("password");
	const passwordConfirmInput = document.getElementById("passwordConfirm");
	const errorMsg = document.getElementById("pwMismatchMsg");

	passwordConfirmInput.addEventListener("input", function () {
		
	  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/;
		
	  const pw = passwordInput.value;
	  const pwConfirm = passwordConfirmInput.value;
	  
	  if (pw === "" && pwConfirm === ""){
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
	  document.getElementById("userEmailInfo").textContent = `\${email}`;
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
	    timerEl.textContent = `인증 제한시간: \${minutes}:\${seconds}`;

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
</script>