<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/csc/inq/insertInq.css">
<!-- 스타일 여기 적어주시면 가능 -->
<section class="channel">
	<!-- 	여기가 네비게이션 역할을 합니다.  -->
	<div class="channel-title">
		<!-- 대분류 -->
		<div class="channel-title-text">고객센터</div> 
	</div>
	<div class="channel-sub-sections">
		<!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/csc/not/noticeList.do">공지사항</a></div> <!-- 중분류 -->
		<div class="channel-sub-section-item"><a href="/csc/faq/faqList.do">FAQ</a></div>
		<div class="channel-sub-section-itemIn"><a href="/csc/inq/inqryList.do">1:1문의</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
  		<div class="public-wrapper-main">
			1:1 문의 게시글 작성<br />
			<div class="inq-post-wrapper">
				<div class="form-group">
					<div class="inqinfo-grid">
						<div class="custom-select">
						    <label for="publicOrNot">공개 여부</label>
						    <div class="custom-select__label" id="selectLabel">선택해주세요.</div>
						    <ul class="custom-select__options" id="selectOptions">
						        <li data-value="공개">공개</li>
						        <li data-value="비공개">비공개</li>
						    </ul>
						    <select name="publicOrNot" class="visually-hidden" id="publicOrNot">
						        <option value="Y">공개</option>
						        <option value="N">비공개</option>
						    </select>
						</div>
					</div>
					<label for="post-title">1:1 문의 제목</label>
					<input type="text" placeholder="제목을 입력하세요" class="title-input" id="post-title"/>
					<label for="description">1:1 문의 내용</label>
					<textarea class="desc-textarea" placeholder="문의 내용을 작성하세요" id="description"></textarea>
				</div>
				<div class="btn-area">
					<button id="btnCancel" class="btn-cancel" onclick="location.href='/csc/inq/inqryList.do'">취소</button>
					<button id="btnSubmit" class="btn-submit" onclick="insertInq()">등록</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript" src="/js/csc/inq/insertInq.js"></script>
</body>
</html>