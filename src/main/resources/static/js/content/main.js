/**
 * 
 */

const fn_init = () => {
	const banner = document.querySelector('.main-loadmap-banner');
	banner.classList.add('animate-in');
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