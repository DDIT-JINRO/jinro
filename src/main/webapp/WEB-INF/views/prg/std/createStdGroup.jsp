<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/prg/std/createStdGroup.css">
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
		<div class="channel-sub-section-item"><a href="/prg/act/vol/volList.do">대외활동</a></div>
		<div class="channel-sub-section-itemIn"><a href="/prg/std/stdGroupList.do">스터디그룹</a></div>
	</div>
</section>
<div>
	<div class="public-wrapper">
		<div class="public-wrapper-main">
			스터디그룹 게시글 작성 <br />
			<div class="study-post-wrapper">
				<div class="form-group">
					<label for="post-title">게시글 제목</label>
					<input type="text" placeholder="제목을 입력하세요" class="title-input" id="post-title"/>

					<div class="study-info-grid">
						<div class="custom-select">
						<label for="gender">성별 제한</label>
							<div class="custom-select__label">성별제한 없음</div>
						  	<ul class="custom-select__options">
						    	<li data-value="all">성별제한 없음</li>
						    	<li data-value="men">남자만</li>
						    	<li data-value="women">여자만</li>
						  	</ul>
						  	<select id="gender" name="gender" class="visually-hidden">
						    	<option value="all">성별제한 없음</option>
						    	<option value="men">남자만</option>
						    	<option value="women">여자만</option>
						  	</select>
						</div>
						<div class="custom-select">
							<label for="region">지역 선택</label>
							<div class="custom-select__label">지역 선택</div>
						  	<ul class="custom-select__options">
						    	<li data-value="G23001">서울</li>
						    	<li data-value="G23002">부산</li>
						    	<li data-value="G23003">대구</li>
						    	<li data-value="G23004">인천</li>
						    	<li data-value="G23005">광주</li>
						    	<li data-value="G23006">대전</li>
						    	<li data-value="G23007">울산</li>
						    	<li data-value="G23008">세종</li>
						    	<li data-value="G23009">경기</li>
						    	<li data-value="G23010">강원</li>
						    	<li data-value="G23011">충북</li>
						    	<li data-value="G23012">충남</li>
						    	<li data-value="G23013">전북</li>
						    	<li data-value="G23014">전남</li>
						    	<li data-value="G23015">경북</li>
						    	<li data-value="G23016">경남</li>
						    	<li data-value="G23017">제주</li>
						  	</ul>
							<select name="region" class="visually-hidden" id="region">
								<option value="">지역 선택</option>
								<option value="G23001">서울</option>
								<option value="G23002">부산</option>
								<option value="G23003">대구</option>
								<option value="G23004">인천</option>
								<option value="G23005">광주</option>
								<option value="G23006">대전</option>
								<option value="G23007">울산</option>
								<option value="G23008">세종</option>
								<option value="G23009">경기</option>
								<option value="G23010">강원</option>
								<option value="G23011">충북</option>
								<option value="G23012">충남</option>
								<option value="G23013">전북</option>
								<option value="G23014">전남</option>
								<option value="G23015">경북</option>
								<option value="G23016">경남</option>
								<option value="G23017">제주</option>
							</select>
						</div> 
						<div class="custom-select">
							<label for="capacity">인원 제한</label>
							<div class="custom-select__label">인원 선택</div>
						  	<ul class="custom-select__options">
						    	<li data-value="2">2명</li>
						    	<li data-value="3">3명</li>
						    	<li data-value="5">5명</li>
						    	<li data-value="10">10명</li>
						    	<li data-value="15">15명</li>
						    	<li data-value="20">20명</li>
						  	</ul>
							<select name="capacity" class="visually-hidden" id="capacity">
								<option value="">인원 선택</option>
								<option value="2">2명</option>
								<option value="3">3명</option>
								<option value="5">5명</option>
								<option value="10">10명</option>
								<option value="15">15명</option>
								<option value="20">20명</option>
								<!-- ... -->
							</select> 
						</div>
						<div class="custom-select">
							<label for="interest">관심 분야</label>
							<div class="custom-select__label">선택하세요</div>
						  	<ul class="custom-select__options">
							    <!-- optgroup 루프 -->
							    <li class="optgroup-label">학업</li>
							    <li data-value="study.general">공부</li>   
							    <li data-value="study.exam">수능준비</li>    
							    <li data-value="study.assignment">과제</li>
							    <li data-value="study.etc">기타</li>       
							
							    <li class="optgroup-label">진로</li>
							    <li data-value="career.path">진로</li>     
							    <li data-value="career.admission">진학</li>
							    <li data-value="career.etc">기타</li>      
							    
							    <li class="optgroup-label">취업</li>
							    <li data-value="job.prepare">취업준비</li> 
							    <li data-value="job.concern">취업고민</li> 
							    <li data-value="job.etc">기타</li>       
							    
							    <li class="optgroup-label">기타</li>
							    <li data-value="social.neighbor">동네친구</li> 
							    <li data-value="social.talk">잡담</li>       
							    <li data-value="social.etc">기타</li>        
						  	</ul>
						
						  	<!-- 실제 폼 전송용 select -->
						  	<select name="tool" class="visually-hidden" id="interest">
							    <optgroup label="학업">
							    	<option value="study.general">공부</option>
							      	<option value="study.exam">수능준비</option>
							      	<option value="study.assignment">과제</option>
							      	<option value="study.etc">기타</option>
							    </optgroup>
						    	<optgroup label="진로">
							      	<option value="career.path">진로</option>       
							      	<option value="career.admission">진학</option>  
							      	<option value="career.etc">기타</option>        
							    </optgroup>
						    	<optgroup label="취업">
							      	<option value="job.prepare">취업준비</option>  
							      	<option value="job.concern">취업고민</option>  
							      	<option value="job.etc">기타</option>        
							    </optgroup>
						    	<optgroup label="기타">
							      	<option value="social.neighbor">동네친구</option>   
							      	<option value="social.talk">잡담</option>         
							      	<option value="social.etc">기타</option>          
							    </optgroup>
						  	</select>
						</div>
					</div>
					<label for="chat-title">채팅방 제목</label>
					<input type="text" placeholder="채팅방 제목을 입력하세요"	class="chat-title-input" id="chat-title"/>
					<label for="description">스터디 소개글</label>
					<textarea class="desc-textarea" placeholder="스터디 소개글을 작성하세요" id="description"></textarea>
				</div>
				<div class="btn-area">
					<button id="btnCancel" class="btn-cancel">취소</button>
					<button id="btnSubmit" class="btn-submit">등록</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
<script>
const crId = "${stdBoardVO.chatRoomVO.crId}";
const memId = "<sec:authentication property="name" />";
</script>
<script src="/js/prg/std/createStdGroup.js"></script>
</html>