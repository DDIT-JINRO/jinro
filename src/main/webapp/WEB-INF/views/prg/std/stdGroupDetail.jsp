<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">프로그램</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/prg/ctt/cttList.do">공모전</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-itemIn"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-item"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
  			스터디그룹 상세
  			${stdGroupId} 번 글
  			<button onclick="getChatRooms()">현재 채팅방 불러오기</button>
  			<br/>
			<a href="/prg/std/stdGroupList.do">목록으로</a>
			<a href="/prg/std/stdChatTest.do">채팅방 테스트</a>
  		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script>
// 현재 참여중인 스터디그룹 채팅방 목록 가져오기
function getChatRooms(){
	fetch('/api/chat/rooms')
	.then(resp =>{
		return resp.json(); 
	})
	.then(data =>{
		console.log(data);
	})
	.catch(err=>{
		console.log(err);
	})
}
</script>
</html>