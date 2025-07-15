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
	<div class="admin-side-logo-text">Admin Page</div>
	<div class="admin-side-mainMove">
		<a href="/"><img alt="바로가기" src="/images/mainPortal.png">
		<p class="admin-side-portaltext">사이트 바로가기</p></a>
	</div>
	<ul class="admin-side-menu">
		<li class="admin-side-menu-item">
			<a href="/admin"">관리자 대시보드</a>
		</li>

		<!-- 필요한 항목이 있을때 이곳을 복사하거나 수정하면됩니다. -->
		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideUsers')">
			<a href="javascript:void(0)">사용자 관리</a>
			<ul class="admin-side-submenu" id="adminSideUsers">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univAdd">테스트</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideContens')">
			<a href="javascript:void(0)">콘텐츠 관리</a>
			<ul class="admin-side-submenu" id="adminSideContens">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideInqMenu')">
			<a href="javascript:void(0)">고객 센터</a>
			<ul class="admin-side-submenu" id="adminSideInqMenu">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
			</ul>
		</li>

		<li class="admin-side-menu-item" onclick="toggleSubmenu('adminSideLogMenu')">
			<a href="javascript:void(0)">로그 및 통계</a>
			<ul class="admin-side-submenu" id="adminSideLogMenu">
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
				</li>
				<li>
					<a href="#" class="admin-side-menu-link" data-page="<%=request.getContextPath()%>/admin/adminMoveController.do?target=univList">테스트</a>
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
