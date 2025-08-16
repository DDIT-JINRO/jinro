/**
 * 
 */

const text = "관심 진로와 관련된 유튜브 콘텐츠를 한눈에 확인해보세요.";
	const target = document.getElementById("typing-js");

	let index = 0;
	let isDeleting = false;

	function typingLoop() {
		if (isDeleting) {
			target.textContent = text.substring(0, index--);
		} else {
			target.textContent = text.substring(0, index++);
		}

		if (!isDeleting && index === text.length + 1) {
			isDeleting = true;
			setTimeout(typingLoop, 1500); // 타이핑 다 끝난 후 멈추는 시간
			return;
		}

		if (isDeleting && index === 0) {
			isDeleting = false;
		}

		const speed = isDeleting ? 50 : 100; // 지울 때는 더 빠르게
		setTimeout(typingLoop, speed);
	}

	typingLoop();

document.addEventListener('DOMContentLoaded', function() {
	fn_init();
    if(memId && memId !='anonymousUser') {
    	roadmapPopup();
	}
});

const fn_init = () => {
	const banner = document.querySelector('.main-loadmap-banner');
	banner.classList.add('animate-in');
	
	// 로드맵 바로가기 버튼
	const roadmap = document.querySelector('.roadmapBtn');
	roadmap.addEventListener("click", () => {
		if(!memId || memId=='anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			const roadmapUrl = 'http://localhost:5173/roadmap';
			
			const width  = 1084;
			const height = 736;
			const screenWidth  = window.screen.width;
			const screenHeight = window.screen.height;
            const left = Math.floor((screenWidth - width) / 2);
            const top  = Math.floor((screenHeight - height) / 2);
			
            axios.post("/admin/las/roadMapVisitLog.do");
            
			window.open(roadmapUrl, 'Roadmap', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
		}
	});
	
	const worldcup = document.getElementById('worldcupBtn')
	worldcup.addEventListener("click", () => {
		if(!memId || memId=='anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			axios.post("/admin/las/worldCupVisitLog.do")
			const worldcupUrl = 'http://localhost:5173/worldcup';
			
			const width  = 1200;
			const height = 800;
			const screenWidth  = window.screen.width;
			const screenHeight = window.screen.height;
			const left = Math.floor((screenWidth - width) / 2);
			const top  = Math.floor((screenHeight - height) / 2);
			
			window.open(worldcupUrl, 'worldcup', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
		}
	});
}

const roadmapPopup = () => {
	const popupCookie = getCookie('popup');
	
	if(popupCookie != 'done') {
		const roadmapUrl = 'http://localhost:5173/roadmap';
	
		const width  = 1084;
		const height = 736;
		const screenWidth  = window.screen.width;
		const screenHeight = window.screen.height;
		const left = Math.floor((screenWidth - width) / 2);
		const top  = Math.floor((screenHeight - height) / 2);
	
		window.open(roadmapUrl, 'Roadmap', `width=${width}, height=${height}, left=${left}, top=${top}`);
	}
};

const getCookie = (name) => {
	const nameOfCookie = name + "="
	const cookieArray = document.cookie.split('; ');

	for (const cookie of cookieArray) {
		if (cookie.startsWith(nameOfCookie)) {
			return decodeURIComponent(cookie.substring(nameOfCookie.length));
		}
	}

	return null;
};