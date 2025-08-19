/**
 * */

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
// --- 슬라이더 스크립트 시작 ---
const slidesContainer = document.querySelector('.slides');
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');
const nextBtn = document.getElementById("next-btn");
const prevBtn = document.getElementById("prev-btn");

let currentSlide = 1; // 클론 때문에 1번부터 시작
const totalSlides = slides.length;

// --- 클론 추가 ---
const firstClone = slides[0].cloneNode(true);
const lastClone = slides[totalSlides - 1].cloneNode(true);
firstClone.id = "first-clone";
lastClone.id = "last-clone";

slidesContainer.appendChild(firstClone);
slidesContainer.insertBefore(lastClone, slidesContainer.firstChild);

const allSlides = document.querySelectorAll('.slide');
const totalAllSlides = allSlides.length;

// 초기 위치
slidesContainer.style.transform = `translateX(-${currentSlide * 100}%)`;

// --- 슬라이드 이동 함수 ---
function showSlide(index) {
    slidesContainer.style.transition = "transform 0.5s ease-in-out";
    slidesContainer.style.transform = `translateX(-${index * 100}%)`;
    currentSlide = index;

    // dot 업데이트
    dots.forEach(dot => dot.classList.remove('active'));
    let dotIndex = index - 1; // 클론 때문에 보정
    if (dotIndex < 0) dotIndex = totalSlides - 1;
    if (dotIndex >= totalSlides) dotIndex = 0;
    if (dots[dotIndex]) dots[dotIndex].classList.add("active");
}

// --- transition 끝나면 클론 보정 ---
slidesContainer.addEventListener("transitionend", () => {
    if (allSlides[currentSlide].id === "first-clone") {
        slidesContainer.style.transition = "none";
        currentSlide = 1;
        slidesContainer.style.transform = `translateX(-${currentSlide * 100}%)`;
    }
    if (allSlides[currentSlide].id === "last-clone") {
        slidesContainer.style.transition = "none";
        currentSlide = totalSlides;
        slidesContainer.style.transform = `translateX(-${currentSlide * 100}%)`;
    }
});

// --- 다음/이전 버튼 ---
function nextSlide() {
    if (currentSlide >= totalAllSlides - 1) return;
    currentSlide++;
    showSlide(currentSlide);
}

function prevSlide() {
    if (currentSlide <= 0) return;
    currentSlide--;
    showSlide(currentSlide);
}

if (nextBtn) nextBtn.addEventListener("click", nextSlide);
if (prevBtn) prevBtn.addEventListener("click", prevSlide);

// --- dot 클릭 이벤트 ---
if (dots) {
    dots.forEach(dot => {
        dot.addEventListener("click", e => {
            const slideIndex = parseInt(e.target.dataset.slideIndex);
            currentSlide = slideIndex + 1; // 클론 때문에 +1
            showSlide(currentSlide);
        });
    });
}

// --- 자동 슬라이드 ---
let slideInterval = setInterval(nextSlide, 5000);
function pauseSlide() { clearInterval(slideInterval); }
function resumeSlide() { slideInterval = setInterval(nextSlide, 5000); }

if (nextBtn) {
    nextBtn.addEventListener("mouseenter", pauseSlide);
    nextBtn.addEventListener("mouseleave", resumeSlide);
}
if (prevBtn) {
    prevBtn.addEventListener("mouseenter", pauseSlide);
    prevBtn.addEventListener("mouseleave", resumeSlide);
}

// 초기 슬라이드 표시
showSlide(currentSlide);
// --- 슬라이더 스크립트 끝 ---


document.addEventListener('DOMContentLoaded', function() {
	fn_init();
	fn_TopsWidget();
    if(memId && memId !='anonymousUser') {
    	roadmapPopup();
	}
});

const fn_init = () => {
	const banner = document.querySelector('.main-loadmap-banner');
	// banner.classList.add('animate-in'); // 이 부분을 주석 처리하거나 삭제하여 슬라이더와 충돌하지 않도록 했습니다.
	
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

const fn_TopsWidget = () =>{
	// 컨텐츠 4종류 요소 배열로 받음
	const widgets = Array.from(document.querySelectorAll('.trend-widget'));

	// fetch 함수화
	const fetchJSON = async (url) => {
	  try{
	    const res = await fetch(url, { headers: { 'Accept': 'application/json' }});
	    if(!res.ok) return [];
	    return await res.json();
	  }catch(e){ return []; }
	};

	// 위젯 로딩 함수
	const initWidget = async (section) => {
	  const endpoint = section.dataset.endpoint;	// jsp 요소에 삽입해둔 fetch url
	  const roller  = section.querySelector('.trend-roller');	// 순위별로 번갈아가며 출력될 요소
	  const viewport= section.querySelector('.trend-viewport'); // 순위별로 번갈아가며 출력될 요소 컨테이너
	  const panel   = section.querySelector('.trend-panel');	// 순위전체 목록이 들어갈 요소

	  let items = (await fetchJSON(endpoint)).slice(0,5).map((x,i)=>({
	    rank: i+1,
	    text: x.TARGETNAME || '제목 없음',
	    href: x.TARGET_URL  || '#'
	  }));

	  // 데이터 없을 때 기본 표시 방지
	  if(items.length === 0){
	    roller.innerHTML = `<li class="trend-item"><span class="trend-text">데이터가 없습니다</span></li>`;
	    button.disabled = true;
	    return;
	  }

	  // ① 롤러(한 줄) 렌더
	  let idx = 0;	// setTimeout에서 idx 값 변화 시키면서 렌더링함
	  const renderRoller = () => {
	    const it = items[idx % items.length];
	    roller.innerHTML = `
	      <li class="trend-item">
	        <span class="trend-rank">${it.rank}</span>
	        <a class="trend-text" href="${it.href}" title="${it.text}">${it.text}</a>
	      </li>`;
	  };
	  renderRoller();

	  // ② 패널(전체) 렌더
	  const renderPanel = () => {
	    panel.innerHTML = items.map(it => `
	      <a class="panel-item" href="${it.href}">
	        <span class="panel-rank">${it.rank}</span>
	        <span class="panel-text">${it.text}</span>
	      </a>`).join('');
	  };
	  renderPanel();

	  // ③ 순환 타이머
	  let hovering = false, focused = false, timer = null;
	  const tick = () => {
	    if(hovering || focused || items.length === 0) return;
	    idx = (idx + 1) % items.length;
	    renderRoller();
	  };
	  const start = () => { stop(); timer = setInterval(tick, 2300); };	// 순위변화 시간 조절
	  const stop  = () => { if(timer) clearInterval(timer); timer = null; };
	  start();

	  // ④ 인터랙션 (hover/버튼/포커스)
	  const openPanel = () => { panel.classList.remove('hidden'); };
	  const closePanel= () => { panel.classList.add('hidden'); };

	  section.addEventListener('mouseenter', ()=>{ hovering = true; openPanel(); });
	  section.addEventListener('mouseleave', ()=>{ hovering = false; closePanel(); });
	  viewport.addEventListener('focusin',  ()=>{ focused = true; });
	  viewport.addEventListener('focusout', ()=>{ focused = false; });

	  // 페이지 이탈 시 정리
	  window.addEventListener('beforeunload', stop);
	};

	widgets.forEach(initWidget);
}