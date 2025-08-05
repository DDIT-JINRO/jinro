<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/cns/csl/counselingLog.css">
<!-- 스크립트 작성해주시면 됩니다 (유의점 : DOMContentLoaded x) -->
<script>
</script>
<!-- 제목입니다 -->
<h3>상담 일지 작성</h3>
<div class="template-container">
	<!-- 리스트 패널 -->
	<div class="template-panel" style="flex: 1">
		<div class="panel-header" id="noticeHeader" style="cursor: pointer; text-decoration: none">상담 리스트</div>

		<!-- 리스트 패널 상단: 필터 영역 -->
		<div class="filter-box">
			<form action="/csc/admin/noticeList.do" method="get">
				<select name="status">
				    <option value="2025">2025</option>
				    <!-- … -->
				  </select>
  		 		  <input type="text" name="keyword" placeholder="회원명을 입력하세요" />
 				  <button type="button" class="btn-save">조회</button>
			</form>
		</div>
		<p>
			총 <span id="notice-count"></span>건
		</p>
		<div class="table-wrapper">
			<table>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 15%;">
					<col style="width: 10%;">
					<col style="width: 30%;">
					<col style="width: 25%;">
					<col style="width: 10%;">
				</colgroup>
				<thead>
					<tr>
						<th>번 호</th>
						<th>회원명</th>
						<th>나 이</th>
						<th>이메일</th>
						<th>연락처</th>
						<th>상 태</th>
					</tr>
				</thead>
				<tbody id="notice-list">
					<!-- Java 백엔드 렌더링용 -->
				</tbody>
			</table>
		</div>

		<div style="margin-top: 10px; text-align: center;">
			<!-- 페이지네이션 자리 -->
			<div class="card-footer clearfix">
				 <div class="panel-footer pagination">

				</div>
			</div>
		</div>
	</div>

	<!-- 상세/작성 패널 -->
	<div class="template-panel" style="flex: 1.5">
		<div class="panel-header">상담 상세정보</div>
		<h3>상담일지 작성</h3>
		<table class="info-table" style="display: none;">
			<thead id="info-table-thead">
				<tr>
					<th>번 호</th>
					<th>제 목</th>
					<th>생성일(수정일)</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody id="info-table-tbody">

			</tbody>
		</table>

<form class="noticeFormGroup" id="form-data" enctype="multipart/form-data">
			<div>
				<label>제 목</label>
				<input type="text" name="noticeTitle" placeholder="제목을 입력하세요">
			</div>

			<input type="hidden" name="fileGroupNo" id="fileGroupNo">
			<input type="hidden" name="noticeId" id="noticeId" value="0">
			<div class="ck-row">
			  <label>공지 내용</label>
			  <div class="ck-editor">
			    <!-- CKEditor가 여기에 mount됨 -->
			    <textarea name="noticeContent" id="noticeContent" style="display: none;"></textarea><div class="ck ck-reset ck-editor ck-rounded-corners" role="application" dir="ltr" lang="en" aria-labelledby="ck-editor__label_e93713563be28ec29fe4ea9359f579ad3"><label class="ck ck-label ck-voice-label" id="ck-editor__label_e93713563be28ec29fe4ea9359f579ad3">Rich Text Editor</label><div class="ck ck-editor__top ck-reset_all" role="presentation"><div class="ck ck-sticky-panel"><div class="ck ck-sticky-panel__placeholder" style="display: none;"></div><div class="ck ck-sticky-panel__content"><div class="ck ck-toolbar ck-toolbar_grouping" role="toolbar" aria-label="Editor toolbar" tabindex="-1"><div class="ck ck-toolbar__items"><button class="ck ck-button ck-disabled ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_eccf65ab914d132836cf1068c1553e785" aria-disabled="true" data-cke-tooltip-text="Undo (Ctrl+Z)" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="m5.042 9.367 2.189 1.837a.75.75 0 0 1-.965 1.149l-3.788-3.18a.747.747 0 0 1-.21-.284.75.75 0 0 1 .17-.945L6.23 4.762a.75.75 0 1 1 .964 1.15L4.863 7.866h8.917A.75.75 0 0 1 14 7.9a4 4 0 1 1-1.477 7.718l.344-1.489a2.5 2.5 0 1 0 1.094-4.73l.008-.032H5.042z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_eccf65ab914d132836cf1068c1553e785">Undo</span></button><button class="ck ck-button ck-disabled ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_efe118b4d9780262b16a0b46dc28e4b48" aria-disabled="true" data-cke-tooltip-text="Redo (Ctrl+Y)" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="m14.958 9.367-2.189 1.837a.75.75 0 0 0 .965 1.149l3.788-3.18a.747.747 0 0 0 .21-.284.75.75 0 0 0-.17-.945L13.77 4.762a.75.75 0 1 0-.964 1.15l2.331 1.955H6.22A.75.75 0 0 0 6 7.9a4 4 0 1 0 1.477 7.718l-.344-1.489A2.5 2.5 0 1 1 6.039 9.4l-.008-.032h8.927z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_efe118b4d9780262b16a0b46dc28e4b48">Redo</span></button><span class="ck ck-toolbar__separator"></span><div class="ck ck-dropdown ck-heading-dropdown"><button class="ck ck-button ck-off ck-button_with-text ck-dropdown__button" type="button" tabindex="-1" aria-label="Heading" data-cke-tooltip-text="Heading" data-cke-tooltip-position="s" aria-haspopup="true" aria-expanded="false"><span class="ck ck-button__label" id="ck-editor__aria-label_e07f3a4a5ee50cbc235a4b97d32737626">Paragraph</span><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-dropdown__arrow" viewBox="0 0 10 10"><path d="M.941 4.523a.75.75 0 1 1 1.06-1.06l3.006 3.005 3.005-3.005a.75.75 0 1 1 1.06 1.06l-3.549 3.55a.75.75 0 0 1-1.168-.136L.941 4.523z"></path></svg></button><div class="ck ck-reset ck-dropdown__panel ck-dropdown__panel_se" tabindex="-1"></div></div><span class="ck ck-toolbar__separator"></span><button class="ck ck-button ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_ea7e4271bdb9083fa6fb577564c15c00a" aria-pressed="false" data-cke-tooltip-text="Bold (Ctrl+B)" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="M10.187 17H5.773c-.637 0-1.092-.138-1.364-.415-.273-.277-.409-.718-.409-1.323V4.738c0-.617.14-1.062.419-1.332.279-.27.73-.406 1.354-.406h4.68c.69 0 1.288.041 1.793.124.506.083.96.242 1.36.478.341.197.644.447.906.75a3.262 3.262 0 0 1 .808 2.162c0 1.401-.722 2.426-2.167 3.075C15.05 10.175 16 11.315 16 13.01a3.756 3.756 0 0 1-2.296 3.504 6.1 6.1 0 0 1-1.517.377c-.571.073-1.238.11-2 .11zm-.217-6.217H7v4.087h3.069c1.977 0 2.965-.69 2.965-2.072 0-.707-.256-1.22-.768-1.537-.512-.319-1.277-.478-2.296-.478zM7 5.13v3.619h2.606c.729 0 1.292-.067 1.69-.2a1.6 1.6 0 0 0 .91-.765c.165-.267.247-.566.247-.897 0-.707-.26-1.176-.778-1.409-.519-.232-1.31-.348-2.375-.348H7z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_ea7e4271bdb9083fa6fb577564c15c00a">Bold</span></button><button class="ck ck-button ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_e96543c9feea3cc46fe10f4a686937123" aria-pressed="false" data-cke-tooltip-text="Italic (Ctrl+I)" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="m9.586 14.633.021.004c-.036.335.095.655.393.962.082.083.173.15.274.201h1.474a.6.6 0 1 1 0 1.2H5.304a.6.6 0 0 1 0-1.2h1.15c.474-.07.809-.182 1.005-.334.157-.122.291-.32.404-.597l2.416-9.55a1.053 1.053 0 0 0-.281-.823 1.12 1.12 0 0 0-.442-.296H8.15a.6.6 0 0 1 0-1.2h6.443a.6.6 0 1 1 0 1.2h-1.195c-.376.056-.65.155-.823.296-.215.175-.423.439-.623.79l-2.366 9.347z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_e96543c9feea3cc46fe10f4a686937123">Italic</span></button><span class="ck ck-toolbar__separator"></span><button class="ck ck-button ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_e1bf018c9007c6c6ba9c6fcf659695231" aria-pressed="false" data-cke-tooltip-text="Link (Ctrl+K)" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="m11.077 15 .991-1.416a.75.75 0 1 1 1.229.86l-1.148 1.64a.748.748 0 0 1-.217.206 5.251 5.251 0 0 1-8.503-5.955.741.741 0 0 1 .12-.274l1.147-1.639a.75.75 0 1 1 1.228.86L4.933 10.7l.006.003a3.75 3.75 0 0 0 6.132 4.294l.006.004zm5.494-5.335a.748.748 0 0 1-.12.274l-1.147 1.639a.75.75 0 1 1-1.228-.86l.86-1.23a3.75 3.75 0 0 0-6.144-4.301l-.86 1.229a.75.75 0 0 1-1.229-.86l1.148-1.64a.748.748 0 0 1 .217-.206 5.251 5.251 0 0 1 8.503 5.955zm-4.563-2.532a.75.75 0 0 1 .184 1.045l-3.155 4.505a.75.75 0 1 1-1.229-.86l3.155-4.506a.75.75 0 0 1 1.045-.184z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_e1bf018c9007c6c6ba9c6fcf659695231">Link</span></button><span class="ck-file-dialog-button"><button class="ck ck-button ck-off" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_eeed1d5aaa02b5058712075ca32cf100b" data-cke-tooltip-text="Insert image" data-cke-tooltip-position="s"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="M6.91 10.54c.26-.23.64-.21.88.03l3.36 3.14 2.23-2.06a.64.64 0 0 1 .87 0l2.52 2.97V4.5H3.2v10.12l3.71-4.08zm10.27-7.51c.6 0 1.09.47 1.09 1.05v11.84c0 .59-.49 1.06-1.09 1.06H2.79c-.6 0-1.09-.47-1.09-1.06V4.08c0-.58.49-1.05 1.1-1.05h14.38zm-5.22 5.56a1.96 1.96 0 1 1 3.4-1.96 1.96 1.96 0 0 1-3.4 1.96z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_eeed1d5aaa02b5058712075ca32cf100b">Insert image</span></button><input class="ck-hidden" type="file" tabindex="-1" accept="image/jpeg,image/png,image/gif,image/bmp,image/webp,image/tiff" multiple="true"></span><div class="ck ck-dropdown"><button class="ck ck-button ck-off ck-dropdown__button" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_e717fd9df6fbd959b96cb2f8f437e4b35" data-cke-tooltip-text="Insert table" data-cke-tooltip-position="s" aria-haspopup="true" aria-expanded="false"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><path d="M3 6v3h4V6H3zm0 4v3h4v-3H3zm0 4v3h4v-3H3zm5 3h4v-3H8v3zm5 0h4v-3h-4v3zm4-4v-3h-4v3h4zm0-4V6h-4v3h4zm1.5 8a1.5 1.5 0 0 1-1.5 1.5H3A1.5 1.5 0 0 1 1.5 17V4c.222-.863 1.068-1.5 2-1.5h13c.932 0 1.778.637 2 1.5v13zM12 13v-3H8v3h4zm0-4V6H8v3h4z"></path></svg><span class="ck ck-button__label" id="ck-editor__aria-label_e717fd9df6fbd959b96cb2f8f437e4b35">Insert table</span><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-dropdown__arrow" viewBox="0 0 10 10"><path d="M.941 4.523a.75.75 0 1 1 1.06-1.06l3.006 3.005 3.005-3.005a.75.75 0 1 1 1.06 1.06l-3.549 3.55a.75.75 0 0 1-1.168-.136L.941 4.523z"></path></svg></button><div class="ck ck-reset ck-dropdown__panel ck-dropdown__panel_se" tabindex="-1"></div></div></div><span class="ck ck-toolbar__separator"></span><div class="ck ck-dropdown ck-toolbar__grouped-dropdown ck-toolbar-dropdown"><button class="ck ck-button ck-off ck-dropdown__button" type="button" tabindex="-1" aria-labelledby="ck-editor__aria-label_e610fd01a34f9f99788e407c95dfc4570" data-cke-tooltip-text="Show more items" data-cke-tooltip-position="sw" aria-haspopup="true" aria-expanded="false"><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-button__icon" viewBox="0 0 20 20"><circle cx="9.5" cy="4.5" r="1.5"></circle><circle cx="9.5" cy="10.5" r="1.5"></circle><circle cx="9.5" cy="16.5" r="1.5"></circle></svg><span class="ck ck-button__label" id="ck-editor__aria-label_e610fd01a34f9f99788e407c95dfc4570">Show more items</span><svg class="ck ck-icon ck-reset_all-excluded ck-icon_inherit-color ck-dropdown__arrow" viewBox="0 0 10 10"><path d="M.941 4.523a.75.75 0 1 1 1.06-1.06l3.006 3.005 3.005-3.005a.75.75 0 1 1 1.06 1.06l-3.549 3.55a.75.75 0 0 1-1.168-.136L.941 4.523z"></path></svg></button><div class="ck ck-reset ck-dropdown__panel ck-dropdown__panel_se" tabindex="-1"></div></div></div></div></div></div><div class="ck ck-editor__main" role="presentation"><div class="ck-blurred ck ck-content ck-editor__editable ck-rounded-corners ck-editor__editable_inline" lang="en" dir="ltr" role="textbox" aria-label="Editor editing area: main" contenteditable="true"><p><br data-cke-filler="true"></p></div></div></div>
			  </div>
			</div>

			<div class="noticeFormGroup">
				<label for="noticeFileInput">첨부 파일</label>
				<input id="noticeFileInput" type="file" name="files" multiple="">
			</div>


			<!-- 기존에 업로드된 파일을 뿌릴 곳 -->
			<div class="noticeFormGroup" id="file" style="display: none;">
				<div style="float:left;">
					<label>기존 첨부파일</label>
				</div>
				<div id="existing-files" class="existing-files">

				</div>
			</div>

			<div class="panel-footer button-group">
				<button type="button" onclick="resetDetail()" class="btn btn-reset">초기화</button>
				<button  type="button" onclick="insertOrUpdate()" class="btn btn-save"  id="btn-sav">임시저장</button>
				<button  type="button" onclick="insertOrUpdate()" class="btn btn-confirm"  id="btn-save">제출</button>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript" src="/js/include/cns/csl/counselingLog.js"></script>