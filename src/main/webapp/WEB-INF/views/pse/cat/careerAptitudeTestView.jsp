<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/pse/cat/careerAptitudeTestView.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">진로 탐색</div>
	</div>
	<!-- 중분류 -->
	<div class="channel-sub-sections">
		<div class="channel-sub-section-itemIn">
			<a href="/pse/cat/careerAptitudeTest.do">진로 심리검사</a>
		</div>
		<div class="channel-sub-section-item">
			<a href="/pse/cr/crl/selectCareerList.do">직업백과</a>
		</div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<!-- 여기부터 작성해 주시면 됩니다 -->
		<div class="public-wrapper-main">
			<div class="pse-cat-container">

				<h2 class="pse-cat-main-title">진로 심리검사 안내</h2>
				<p class="pse-cat-sub-title">
					나를 바로 알면 <strong>성공한 미래</strong>가 보인다!
				</p>

				<p class="pse-cat-description">
					진로심리검사는 자신의 흥미와 성격, 가치관을 기반으로 진로를 탐색할 수 있도록 도와줍니다.<br> 다른
					검사결과는 진단 후 결과만 제공하지만, 진로심리검사는 검사 후 진로 정보와 직업 정보를 함께 제공함으로써 실제 진로로
					발전할 수 있는 방향까지 안내합니다.<br> 자신에게 맞는 심리를 선택하여 검사하고, 검사 결과를 바탕으로
					진로를 개발하기 위한 첫 걸음을 내딛기 바랍니다.
				</p>

				<div class="pse-cat-icon-section">
					<div class="pse-cat-icon-item pink">
						진로목표가<br>나는 누구인가<br>어떤꿈인가?
					</div>
					<div class="pse-cat-icon-item blue">
						진로에서 필요한<br>어떤 성격과 태도가<br>나와 적합한 역할?
					</div>
					<div class="pse-cat-icon-item green">
						진로특성, 진로정보<br>무슨 직무가<br>발달할까?
					</div>
				</div>

				<div class="pse-cat-tab-wrapper">
					<button class="pse-cat-tab active" data-type="student">중·고등학생용</button>
					<button class="pse-cat-tab" data-type="adult">대학·성인용</button>
				</div>

				<div class="pse-cat-card-grid" id="cardGrid"></div>

			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>
<script>
	// 스크립트 작성 해주시면 됩니다.
	const cardData = {
		student : [ {
			title : "직업 흥미 검사(K)",
			desc : "다양한 직업에서 이루어지는 활동들에 대해 얼마나 흥미를 느끼고 있는가를 알아봄으로써, 장래에 자신에게 알맞은<br/>직업군을 탐색하는 데 도움을 주기 위한 것입니다.",
			time : "15~20분",
			img : "/images/main/readingGlasses.png",
			type : "1"
		}, {
			title : "직업 흥미 검사(H)",
			desc : "나의 흥미유형 및 세부 직업과 관련하여 어떤 흥미를<br/> 가지고 있는지 알아볼 수 있습니다.",
			time : "15~20분",
			img : "/images/main/readingGlasses.png",
			type : "2"
		}, {
			title : "직업 가치관 검사",
			desc : "직업과 관련된 다양한 가치 중, 어떤 가치를 중요하게<br/> 여기는지 알아볼 수 있습니다.",
			time : "15~20분",
			img : "/images/main/readingGlasses.png",
			type : "3"
		}, {
			title : "직업적성검사",
			desc : "직업과 관련된 다양한 능력을 어느정도로 갖추고 있는지<br/> 알아볼 수 있습니다.",
			time : "중학생 20분, 고등학생 30분",
			img : "/images/main/readingGlasses.png",
			type : "4"
		}],
		adult : [ {
			title : "성인용 직업흥미검사",
			desc : "성인 대상의 진로 전환 및 직업 탐색을 위한 검사입니다.",
			time : "20분",
			img : "/images/main/readingGlasses.png",
			type : "5"
		}, {
			title : "직무 적성 검사",
			desc : "직무에 필요한 능력과 적성 요소를 평가합니다.",
			time : "25분",
			img : "/images/main/readingGlasses.png",
			type : "6"
		}, {
			title : "진로 가치관 검사",
			desc : "학생들의 진로 선택에 있어 중요하게 여기는 가치관을 파악합니다.",
			time : "10분",
			img : "/images/main/readingGlasses.png",
			type : "7"
		}, {
			title : "진로 가치관 검사",
			desc : "학생들의 진로 선택에 있어 중요하게 여기는 가치관을 파악합니다.",
			time : "10분",
			img : "/images/main/readingGlasses.png",
			type : "8"
		} ]
	};
	
	  const tabs = document.querySelectorAll(".pse-cat-tab");
	  const cardGrid = document.getElementById("cardGrid");

	  function renderCards(type) {
	    cardGrid.innerHTML = "";
	    const cards = cardData[type];

	    cards.forEach(card => {
	      const cardHTML = `
	        <div class="pse-cat-card">
	          <div class="pse-cat-card-icon"><img alt="" src="\${card.img}"></div>
	          <div class="pse-cat-card-title">\${card.title}</div>
	          <div class="pse-cat-card-desc">\${card.desc}</div>
	          <div class="pse-cat-card-time">평균 소요시간 : \${card.time}</div>
	          <sec:authorize access="hasRole('ROLE_USER')">
	          	<button onclick="startTest(\${card.type})">검사 시작</button>
	          </sec:authorize>
	          <sec:authorize access='isAnonymous()'>
	          	<button onclick="logReq()">검사 시작</button>
	          </sec:authorize>
	          
	        </div>
	      `;
	      cardGrid.insertAdjacentHTML("beforeend", cardHTML);
	    });
	  }

	  tabs.forEach(tab => {
	    tab.addEventListener("click", () => {
	      
	      tabs.forEach(t => t.classList.remove("active"));
	      tab.classList.add("active");

	      
	      const type = tab.dataset.type;
	      renderCards(type);
	    });
	  });

	  renderCards("student");
	  
	  function startTest(type){
		  
		  axios.post("/pse/cat/test/start", {
		        type: type
		      })
		      .then(response => {
		        console.log("서버 응답:", response.data);
		 
// 		        window.location.href = `/test/progress?testId=${response.data.testId}`;
		      })
		      .catch(error => {
		        console.error("요청 실패:", error);
		        alert("검사 시작에 실패했습니다.");
		      });
		  
	  }
	  function logReq(){
		  
	  	location.href = "/error/logReq";
		  
	  }
</script>