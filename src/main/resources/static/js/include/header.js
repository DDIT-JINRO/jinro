function header() {
	// --- 모바일 슬라이드 메뉴 로직 ---
	const mobileMenuTrigger = document.getElementById('mobileMenuTrigger');
	const mobileNavPanel = document.getElementById('mobileNavPanel');
	const mobileNavClose = document.getElementById('mobileNavClose');
	const overlay = document.getElementById('overlay');

	function openMobileMenu() {
		if (mobileNavPanel) mobileNavPanel.classList.add('is-active');
		if (overlay) overlay.classList.add('is-active');
	}

	function closeMobileMenu() {
		if (mobileNavPanel) mobileNavPanel.classList.remove('is-active');
		if (overlay) overlay.classList.remove('is-active');
	}

	if (mobileMenuTrigger && mobileNavPanel && mobileNavClose && overlay) {
		mobileMenuTrigger.addEventListener('click', openMobileMenu);
		mobileNavClose.addEventListener('click', closeMobileMenu);
		overlay.addEventListener('click', closeMobileMenu);
	}

	// --- 데스크톱 메가 메뉴 로직 ---
	const menuIcon = document.getElementById("megaMenuToggle");
	const dropdown = document.getElementById("megaMenu");

	if (menuIcon && dropdown) {
		menuIcon.addEventListener("click", () => {
			dropdown.classList.toggle("mega-menu--hidden");
		});

		document.addEventListener("click", (event) => {
			if (!dropdown.contains(event.target) && !menuIcon.contains(event.target)) {
				dropdown.classList.add("mega-menu--hidden");
			}
		});
	}

	// --- 사이드바 버튼 로직 ---
	const worldcup = document.getElementById("worldcup");
	const roadmap = document.getElementById("roadmap");

	if (roadmap) {
		roadmap.addEventListener("click", () => {
			if (!memId || memId == 'anonymousUser') {
				sessionStorage.setItem("redirectUrl", location.href);
				location.href = "/login";
			} else {
				const roadmapUrl = 'http://localhost:5173/roadmap';
				const width = 1084;
				const height = 736;
				const screenWidth = window.screen.width;
				const screenHeight = window.screen.height;
				const left = Math.floor((screenWidth - width) / 2);
				const top = Math.floor((screenHeight - height) / 2);
				axios.post("/admin/las/roadMapVisitLog.do");
				window.open(roadmapUrl, 'Roadmap', `width=${width}, height=${height}, left=${left}, top=${top}`);
			}
		});
	}

	window.addEventListener("message", (event) => {
		if (event.origin !== 'http://localhost:5173') {
			console.warn(`신뢰할 수 없는 출처(${event.origin})로부터의 메시지를 무시합니다.`);
			return;
		}
		const messageData = event.data;
		if (messageData && messageData.type === 'navigateParent') {
			const targetUrl = messageData.url;
			if (targetUrl) {
				window.location.href = targetUrl;
			} else {
				console.error('메시지에 이동할 URL이 없습니다.');
			}
		}
	});

	if (worldcup) {
		worldcup.addEventListener("click", () => {
			if (!memId || memId == 'anonymousUser') {
				sessionStorage.setItem("redirectUrl", location.href);
				location.href = "/login";
			} else {
				axios.post("/admin/las/worldCupVisitLog.do")
				const worldcupUrl = 'http://localhost:5173/worldcup';
				const width = 1200;
				const height = 800;
				const screenWidth = window.screen.width;
				const screenHeight = window.screen.height;
				const left = Math.floor((screenWidth - width) / 2);
				const top = Math.floor((screenHeight - height) / 2);
				window.open(worldcupUrl, 'worldcup', `width=${width}, height=${height}, left=${left}, top=${top}`);
			}
		});
	}
}