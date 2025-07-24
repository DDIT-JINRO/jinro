<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/account/findIdPage.css">
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
		<div class="public-wrapper-main">
			<div class="findEmail-container">
				<div class="findEmail-container-imgBox">
					<img alt="" src="/images/logo.png">
				</div>
				<label for="findEmail-user-email">이름</label> <input type="email"
					id="findEmail-user-email" placeholder="이름을 입력해주세요."> <label
					for="findEmail-user-password">전화번호</label> <input type="email"
					id="findEmail-user-password" placeholder="전화번호를 입력해주세요.">


				<button class="findEmail-btn" onclick="selectEmailBtn()">이메일
					조회</button>
				<div class="find-links">
					비밀번호를 잊으셨나요? <a href="/lgn/findPw.do">비밀번호 찾기</a>
				</div>
				<div class="findEmailResult" id="findEmailResult">
					<div class="flex">
						해당 정보로 가입된 아이디가 총<span class="resultMessege"></span>있습니다.
					</div>
					<div id="emailList"></div>
				</div>
				<div class="signup-box">
					로그인하실 준비가 되셨나요? <a href="/login">로그인</a>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
	
	function selectEmailBtn() {
		const name = document.getElementById("findEmail-user-email").value
				.trim();
		const phone = document.getElementById("findEmail-user-password").value
				.trim();
		const resultBox = document.getElementById("findEmailResult");
		const resultText = document.querySelector(".resultMessege");

		if (!name || !phone) {
			alert("이름과 전화번호를 모두 입력해주세요.");
			
			return;
		}

		try {
			
			fetch('/lgn/findId.do', {
				method : 'POST',
				headers : {
					'Content-Type' : 'application/json'
				},
				body : JSON.stringify({
					name : name,
					phone : phone
				})
			})
			.then(response => response.json())  
	  		.then(data => {
	  			console.log(data);
	  			findEmailResult.style.display = "block";
	  			resultText.textContent = data.count + "개";
	  			
	  		    const emailList = document.getElementById("emailList");

	  		    if (Array.isArray(data.memList)) {
	  		      data.memList.forEach(member => {
	  		    	const li = document.createElement("li");
	  		      	li.classList.add("email-item");
	  		     	li.textContent = member.memEmail;
	  		      	emailList.appendChild(li);
	  		      });
	  		    }
	  		})
		} catch (error) {
			console.error("에러 발생:", error);
			alert("서버 통신에 실패했습니다.");
			resultBox.style.display = "none";
		}
	}
</script>