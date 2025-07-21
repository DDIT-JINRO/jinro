<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="#">
<link rel="stylesheet" href="/css/header.css">
<link rel="stylesheet" href="/css/footer.css">
<link rel="stylesheet" href="/css/channel.css">
<meta charset="UTF-8">
<title>CareerPath</title>
<script>
	document.addEventListener("DOMContentLoaded",() => {
		const menuIcon = document.getElementById("menuToggle");
		const dropdown = document.getElementById("dropdownMenu");

		menuIcon.addEventListener("click",() => {
			dropdown.classList.toggle("hidden");
		});

		document.addEventListener("click",(event) => {
		      if (!dropdown.contains(event.target) && !menuIcon.contains(event.target)) {
		    	  dropdown.classList.add("hidden");
		      }
		    });
	});
</script>
</head>

<div class="public-topbar">
	<div class="public-topbar-left">
		<a href="/main"><img src="/images/logo.png" alt="로고" class="logo"
			id="logo" /></a>
	</div>

	<div class="public-topbar-center">
		<ul class="public-nav-menu">
			<li><img src="/images/menuAll.png" alt="메뉴 아이콘"
				class="public-menu-icon" id="menuToggle" /></li>
			<li><a href="/test">진로</a></li>
			<li><a href="/test">진학</a></li>
			<li><a href="/test">취업</a></li>
			<li><a href="/rsm/rsm">경력관리</a></li>
			<li><a href="/InsertCareerCounselingRESVE.do">상담</a></li>
			<li><a href="/test">프로그램</a></li>
			<li><a href="/test">커뮤니티</a></li>
			<li><a href="/csc/noticeList.do">고객센터</a></li>
		</ul>
	</div>

	<div class="public-topbar-right">
		<a href="/counselor"><img src="/images/counselor.png" alt="상담사" class="icon-btn"></a>
		<a href="/admin"><img src="/images/manager.png" alt="관리자" class="icon-btn"></a>
		<a href=""><img src="/images/profile.png" alt="프로필" class="icon-btn" /></a>
		<a href=""><img src="/images/alarm.png" alt="알림" class="icon-btn" /></a>
		<a href=""><img src="/images/login.png" alt="로그인" class="icon-btn" /></a>
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
  <ul>
    <li><img src="" alt="홈" /></li>
    <li><img src="" alt="로드맵" /></li>
    <li><img src="" alt="위로가기" /></li>
  </ul>
</div>
<body>