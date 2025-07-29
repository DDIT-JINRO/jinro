/**
 * 
 */
const cardData = {
	student: [{
		title: "직업 흥미 검사(K)",
		desc: "다양한 직업에서 이루어지는 활동들에 대해 얼마나 흥미를 느끼고 있는가를 알아봄으로써, 장래에 자신에게 알맞은<br/>직업군을 탐색하는 데 도움을 주기 위한 것입니다.",
		time: "15~20분",
		img: "/images/main/readingGlasses.png",
		type: "1"
	}, {
		title: "직업 흥미 검사(H)",
		desc: "나의 흥미유형 및 세부 직업과 관련하여 어떤 흥미를<br/> 가지고 있는지 알아볼 수 있습니다.",
		time: "15~20분",
		img: "/images/main/readingGlasses.png",
		type: "2"
	}, {
		title: "직업 가치관 검사",
		desc: "직업과 관련된 다양한 가치 중, 어떤 가치를 중요하게<br/> 여기는지 알아볼 수 있습니다.",
		time: "15~20분",
		img: "/images/main/readingGlasses.png",
		type: "3"
	}, {
		title: "직업 적성 검사",
		desc: "직업과 관련된 다양한 능력을 어느정도로 갖추고 있는지<br/> 알아볼 수 있습니다.",
		time: "중학생 20분, 고등학생 30분",
		img: "/images/main/readingGlasses.png",
		type: "4"
	}],
	adult: [{
		title: "직업 가치관 검사",
		desc: "직업과 관련된 다양한 가치 중, 어떤 가치를 중요하게<br/> 여기는지 알아볼 수 있습니다.",
		time: "20분",
		img: "/images/main/readingGlasses.png",
		type: "5"
	}, {
		title: "진로 개발 준비도 검사",
		desc: "진로목표 달성을 위해 필요한 사항들에 대한 준비 정도를 알아볼 수 있습니다.",
		time: "25분~30분",
		img: "/images/main/readingGlasses.png",
		type: "6"
	}, {
		title: "이공계 전공 적합도 검사",
		desc: "이공계 대학생이 전공을 선택하고자 할 때, 전공교과별 자신과 관련 직업에 대한 흥미를 기초로 전공군별 상대적인 적합도를 평가해 볼 수 있도록 도와주는 검사입니다.",
		time: "10분",
		img: "/images/main/readingGlasses.png",
		type: "7"
	}, {
		title: "주요 능력 효능감 검사",
		desc: "특정 능력에 대한 자신감을 측정하는 심리 검사입니다.<br/> 이 검사는 대학생 및 일반인들이 자신의 능력에 대한<br/> 믿음을 파악하고, 직업 적합성을 판단하는 데<br/> 도움을 줄 수 있습니다.",
		time: "10분",
		img: "/images/main/readingGlasses.png",
		type: "8"
	}]
};

const tabs = document.querySelectorAll(".pse-cat-tab");
const cardGrid = document.getElementById("cardGrid");

function renderCards(type) {
	cardGrid.innerHTML = "";
	const cards = cardData[type];
	cards.forEach(card => {
		const buttonHTML = window.isLoggedIn
			? `<button onclick="startTest(${card.type}, '${card.title}')">검사 시작</button>`
			: `<button onclick="logReq()">검사 시작</button>`;

		const cardHTML = `
		    <div class="pse-cat-card">
		      <div class="pse-cat-card-icon"><img alt="" src="${card.img}"></div>
		      <div class="pse-cat-card-title">${card.title}</div>
		      <div class="pse-cat-card-desc">${card.desc}</div>
		      <div class="pse-cat-card-time">평균 소요시간 : ${card.time}</div>
		      ${buttonHTML}
		    </div>
		  `;

		cardGrid.insertAdjacentHTML("beforeend", cardHTML);
	});

	tabs.forEach(tab => {
		tab.addEventListener("click", () => {

			tabs.forEach(t => t.classList.remove("active"));
			tab.classList.add("active");


			const type = tab.dataset.type;
			renderCards(type);
		});
	});
}
renderCards("student");

function startTest(type, title) {
	
	console.log(title);
	
	axios.post("/pse/cat/test/start", {
		type: type
	})
		.then(response => {
			
			const width = 1000;
			const height = 800;
			const left = window.screenX + (window.outerWidth - width) / 2;
			const top = window.screenY + (window.outerHeight - height) / 2;

			console.log("서버 응답:", type);
			window.open(`http://localhost:5173/aptiTest/${type}`, title, `width=${width},height=${height},left=${left},top=${top},resizable=no`);
		})
		.catch(error => {
			console.error("요청 실패:", error);
			alert("검사 시작에 실패했습니다.");
		});

}
function logReq() {

	location.href = "/error/logReq";

}
