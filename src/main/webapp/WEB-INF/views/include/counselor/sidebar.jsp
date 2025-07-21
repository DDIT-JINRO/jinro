<%@ page contentType="text/html;charset=UTF-8"%>
<!-- 사이드바 -->
<link rel="stylesheet" href="/css/admin/admSideBar.css">
<script>
document.addEventListener('DOMContentLoaded', function () {
	 const sidebar = document.querySelector('.admin-side-sidebar');
	  setTimeout(() => {
	    sidebar.classList.add('active');
	  }, 300);

	  document.querySelectorAll('.admin-side-menu-link').forEach(function (el) {
	    el.addEventListener('click', function (e) {
	      e.preventDefault();

	      const pageUrl = this.dataset.page;

	      console.log(pageUrl);

	      fetch(pageUrl)
	        .then(response => {
	          if (!response.ok) throw new Error("불러오기 실패");
	          return response.text();
	        })
	        .then(html => {
	          document.getElementById('content').innerHTML = html;

	          const scripts = document.getElementById('content').querySelectorAll('script');
	          scripts.forEach(oldScript => {
	            const newScript = document.createElement('script');
	            if (oldScript.src) {

	              newScript.src = oldScript.src;
	            } else {

	              newScript.textContent = oldScript.textContent;
	            }
	            document.body.appendChild(newScript);

	            oldScript.remove();
	          });
	        })
	        .catch(err => {
	          document.getElementById('content').innerHTML = '<p>에러: 페이지를 불러올 수 없습니다.</p>';
	          console.error(err);
	        });
	    });
	  });
});
</script>
<aside class="admin-side-sidebar">
	<div class="admin-side-logo-container">
		<img class="admin-side-logo" alt="logo" src="/images/logoWhite.png">
	</div>
	<div class="admin-side-logo-text">Counselor</div>
	<div class="admin-side-mainMove">
		<a href="/"><img alt="바로가기" src="/images/mainPortal.png">
		<p class="admin-side-portaltext">사이트 바로가기</p></a>
	</div>
	<ul class="admin-side-menu">
		<li class="admin-side-menu-item">
			<a href="/counselor">상담사 대시보드</a>
		</li>

		<!-- 필요한 항목이 있을때 이곳을 복사하거나 수정하면됩니다. -->
		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideUsers')">
			<a href="javascript:void(0)">사용자 관리</a>
			<ul class="admin-side-submenu" id="adminSideUsers">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=umg/memberManagement">회원 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=umg/counselorManagement">상담사 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=umg/sanctionsDescription">제재 내역</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideContens')">
			<a href="javascript:void(0)">콘텐츠 관리</a>
			<ul class="admin-side-submenu" id="adminSideContens">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=cmg/univManagement">대학 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=cmg/enterpriseManagement">기업 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=cmg/contestManagement">공모전 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=cmg/activityManagement">대외활동 관리</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=cmg/bannerManagement">배너 관리</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideInqMenu')">
			<a href="javascript:void(0)">고객 센터</a>
			<ul class="admin-side-submenu" id="adminSideInqMenu">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=csc/faqManagement">FAQ</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=csc/inquiryManagement">1:1 문의</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=csc/noticeManagement">공지사항</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideLogMenu')">
			<a href="javascript:void(0)">로그 및 통계</a>
			<ul class="admin-side-submenu" id="adminSideLogMenu">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=las/accessUsageStatistics">접속/이용 통계</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=las/memberActivityLog">회원 활동 로그</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=las/consultationLogsStatistics">상담 로그 및 통계</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=las/contentUsageStatistics">컨텐츠 이용 통계</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=las/paymentPremiumServiceStatistics">결제/프리미엄 서비스 통계</a>
				</li>
			</ul>
		</li>
	</ul>
</aside>

<script>
function toggleSubmenu(id) {
  const submenu = document.getElementById(id);
  const parentItem = submenu.closest('.admin-side-menu-item');

  const isOpen = submenu.classList.contains('open');

  document.querySelectorAll('.admin-side-submenu').forEach(el => el.classList.remove('open'));
  document.querySelectorAll('.admin-side-menu-item').forEach(el => el.classList.remove('active'));

  if (!isOpen) {
    submenu.classList.add('open');
    parentItem.classList.add('active');
  }
}
</script>
