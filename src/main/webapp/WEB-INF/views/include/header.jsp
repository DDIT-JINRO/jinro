<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="/images/crppvc.png">
<link rel="stylesheet" href="/css/header.css">
<link rel="stylesheet" href="/css/footer.css">
<link rel="stylesheet" href="/css/channel.css">
<link rel="stylesheet" href="/css/pagenation.css">
<link rel="stylesheet" href="/css/chatModal.css">
<script src="/js/axios.min.js"></script>
<script src="/js/com/sockjs.min.js"></script>
<script src="/js/com/stomp.min.js"></script>
<meta charset="UTF-8">
<title>CareerPath</title>
<script src="/js/include/header.js"></script>
<script>
	document.addEventListener("DOMContentLoaded",() => {
      const menuIcon = document.getElementById("menuToggle");
      const dropdown = document.getElementById("dropdownMenu");
      const roadmap  = document.getElementById("roadmap");
	  header();

      menuIcon.addEventListener("click",() => {
        dropdown.classList.toggle("hidden");
      });

      document.addEventListener("click",(event) => {
            if (!dropdown.contains(event.target) && !menuIcon.contains(event.target)) {
               dropdown.classList.add("hidden");
            }
          });
      
      roadmap.addEventListener("click", () => {
         const roadmapUrl = 'http://localhost:5173/roadmap';
         
         const width  = 1084;
         const height = 736;
         const screenWidth  = window.screen.width;
         const screenHeight = window.screen.height;
            const left = Math.floor((screenWidth - width) / 2);
            const top  = Math.floor((screenHeight - height) / 2);
         
         window.open(roadmapUrl, 'Roadmap', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
      });
      
      worldcup.addEventListener("click", () => {
          const worldcupUrl = 'http://localhost:5173/worldcup';
          
          const width  = 1084;
          const height = 736;
          const screenWidth  = window.screen.width;
          const screenHeight = window.screen.height;
             const left = Math.floor((screenWidth - width) / 2);
             const top  = Math.floor((screenHeight - height) / 2);
          
          window.open(worldcupUrl, 'worldcup', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
       });
       
   	});
	const memId = '<sec:authentication property="name" />'
</script>
</head>

<div class="public-topbar">
	<div class="public-topbar-left">
		<a href="/"><img src="/images/logo.png" alt="로고" class="logo"
			id="logo" /></a>
	</div>

	<div class="public-topbar-center">
		<ul class="public-nav-menu">
			<li><img src="/images/menuAll.png" alt="메뉴 아이콘"
				class="public-menu-icon" id="menuToggle" /></li>
			<li><a href="/pse/cat/careerAptitudeTest.do">진로</a></li>
			<li><a href="/univ/selectUnivList.do">진학</a></li>
			<li><a href="/empt/ema/employmentAdvertisement.do">취업</a></li>
			<li><a href="/rsm/rsm">경력관리</a></li>
			<li><a href="/cnslt/off/offlineReservation.do">상담</a></li>
			<li><a href="/prg/ctt/cttList.do">프로그램</a></li>
			<li><a href="/comm/peer/teen/teenList.do">커뮤니티</a></li>
			<li><a href="/csc/noticeList.do">고객센터</a></li>
		</ul>
	</div>

	<div class="public-topbar-right">
		<sec:authorize access="hasRole('ROLE_CNSLEADER')">
			<a href="/cnsLeader"><img src="/images/cnsLeader.png" alt="상담센터장" class="icon-btn"></a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_COUNSEL')">
			<a href="/cns"><img src="/images/counselor.png" alt="상담사" class="icon-btn"></a>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<a href="/admin"><img src="/images/manager.png" alt="관리자" class="icon-btn"></a>
		</sec:authorize>
		<a href=""><img src="/images/profile.png" alt="프로필" class="icon-btn" /></a>
		<a href=""><img src="/images/alarm.png" alt="알림" class="icon-btn" /></a>
		<sec:authorize access="!isAuthenticated()">
		<a href="/login"><img src="/images/login.png" alt="로그인" class="icon-btn" /></a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
		<a href="/logout"><img src="/images/logout.png" alt="로그아웃" class="icon-btn" /></a>
		</sec:authorize>
	</div>
</div>

<div id="dropdownMenu" class="dropdown-menu hidden">
	<ul>
		<li></li>
		<li></li>
		<li></li>
		<li></li>
	</ul>
</div>

<div class="right-fixed-bar">
    <button class="right-fixed-btn">
		<img src="/images/worldCup.png" id="worldcup" alt="월드컵">
	</button>
	<button class="right-fixed-btn" id="chatRooms">
		<img src="/images/chaticon.png" alt="채팅">
		<span id="chatFloatingBadge" class="chat-unread-badge" style="position: absolute; top: -5px; right: -10px; display: none;">0</span>
	</button>
	<button class="right-fixed-btn">
		<img src="/images/roadmapicon.png" alt="진로탐색">
	</button>
</div>
<%@ include file="/WEB-INF/views/include/chatModal.jsp"%>
<body>
