<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
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
		<div id="customAlert" class="custom-alert hidden">
			<div class="custom-alert-content">
				<p id="alertMessage">알림 내용입니다.</p>
				<button onclick="closeCustomAlert()">확인</button>
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
								type="email" id="email" placeholder="이메일을 입력해주세요." required />
							<button type="button" onclick="emailCheck()">이메일 인증</button>
						</div>

						<div class="input-row">
							<label for="nickname">닉네임</label> <input type="text"
								class="inputWt" id="nickname" placeholder="닉네임을 입력해주세요."
								required />
							
							<button type="button">중복 확인</button>
						</div>
							<p id="nicknameError"></p>

						<div class="input-row2">
							<label for="password">비밀번호</label> <input type="password"
								class="inputWt2" class="line-flex" id="password"
								placeholder="비밀번호를 입력해주세요." required />
						</div>
						<div class="input-row2">
							<label for="passwordConfirm">비밀번호 확인</label> <input
								class="inputWt2" type="password" id="passwordConfirm"
								placeholder="비밀번호를 한 번 더 입력해주세요." required />
						</div>
						<div class="input-row2">
							<label for="name">이름</label> <input class="inputWt2" type="text"
								id="name" placeholder="이름을 입력해주세요." required />
						</div>
						<div class="input-row">
							<label for="phone">핸드폰 번호</label> <input type="tel"
								class="inputWt" id="phone" placeholder="핸드폰 번호를 입력해주세요."
								required />
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
						<!-- 							placeholder="생년월일" required /> -->

						<div class="checkboxes">
							<div class="reqAgree">
								<label><input id="chk" type="checkbox" required /> 이용약관
									동의 <span class="minimal" style="color: red;">필수</span></label>
								<div class="agreeBorder">내용보기</div>
							</div>

							<div class="solAgree">
								<label><input type="checkbox" required /> 개인정보 수집 및 이용
									동의 <span class="minimal" style="color: red;">필수</span></label>
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

						<button class="btn-signup" type="submit">회원가입</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
<script>
	let isEmailVerified = false;
	
	function showCustomAlert(message) {
		document.getElementById("alertMessage").innerText = message;
		document.getElementById("customAlert").classList.remove("hidden");
	}
	function closeCustomAlert() {
		document.getElementById("customAlert").classList.add("hidden");
	}

	function emailCheck() {
		const emailInput = document.getElementById("email");
		const email = emailInput.value.trim();

		// 이메일 형식 정규표현식
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

		if (email === "") {
			showCustomAlert("이메일을 입력해주세요.");
			emailInput.focus();
			return;
		}

		if (!emailRegex.test(email)) {
			showCustomAlert("올바른 이메일 형식이 아닙니다.");
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
				  showCustomAlert("중복된 이메일입니다.");
					emailInput.focus();
			  } else if (result === "access") {
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
	        // 요청 끝나면 버튼 활성화 (필요하면 그대로 비활성화 할 수도 있음)
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
		      showCustomAlert("이메일 인증 성공!");
		      modal.style.display = "none";
		      clearInterval(timerInterval);
		      isEmailVerified = true;
		    } else {
		      showCustomAlert("인증 실패! 코드를 다시 확인해주세요.");
		    }
		  });
		};
		
	const form = document.querySelector("form");

	form.addEventListener("submit", function(e) {
	 if (!isEmailVerified) {
	   	e.preventDefault();
       	showCustomAlert("이메일 인증이 필요합니다.");
       	
       	document.getElementById("email").focus();
      }
	   
	});
	
	const nicknameInput = document.getElementById("nickname");
	const nicknameError = document.getElementById("nicknameError");

	nicknameInput.addEventListener("blur", () => {
	  const nickname = nicknameInput.value.trim();

	  const nicknameRegex = /^[가-힣a-zA-Z0-9]{2,10}$/;

	  if (!nickname) {
	    nicknameError.textContent = "닉네임을 입력해주세요.";
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
		    if (result.exists) {
		      nicknameError.className = "nickname-message error";
		      nicknameError.textContent = "이미 사용 중인 닉네임입니다.";
		    } else {
		      nicknameError.className = "nickname-message success";
		      nicknameError.textContent = "사용 가능한 닉네임입니다.";
		    }
		  })
		  .catch(err => {
		    console.error(err);
		    nicknameError.textContent = "닉네임 중복 확인 중 오류 발생";
		  });
	});
</script>