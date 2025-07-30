// ——— helper: ISO문자열 → "MM.DD" 포맷 ———
function formatDateMMDD(iso) {
	const d = new Date(iso);
	const mm = String(d.getMonth() + 1).padStart(2, '0');
	const dd = String(d.getDate()).padStart(2, '0');
	return `${mm}.${dd}`;
}

// ——— 실제 데이터 + 페이징 조회 함수 ———
const pageSize = 5;  // 한 페이지에 몇 개 보여줄지

function fetchNotices(page = 1) {
	const keyword = document.querySelector('input[name="keyword"]').value;
	const status = document.querySelector('select[name="status"]').value;

	axios.get('/csc/admin/noticeList.do', {
		params: {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
			status: status
		}
	})
		.then(({ data }) => {
			// 1) 총 건수 표시
			document.getElementById('notice-count').textContent = parseInt(data.total, 10).toLocaleString();

			console.log("data : ", data);

			if (data.content.length < 1 && keyword != null) {
				const rows = `
			<tr>
			  <td colspan='4' style="text-align: center;">등록되지 않은 정보입니다.</td>
			</tr>
		`;
				console.log("rows : ", rows);
				document.getElementById('notice-list').innerHTML = rows;
			} else {
				// 2) 리스트 렌더링
				const rows = data.content.map(item => `
		  <tr>
		    <td>${item.noticeId}</td>
		    <td><a href="javascript:showDetail(${item.noticeId})" style="cursor: pointer; text-decoration: none; color:black;">${item.noticeTitle}</a></td>
		    <td>${item.noticeCnt}</td>
		    <td>${formatDateMMDD(item.noticeUpdatedAt)}</td>
		  </tr>
		`).join('');
				console.log("rows : ", rows);
				document.getElementById('notice-list').innerHTML = rows;

				// 3) 페이징 렌더링
				renderPagination(data);
			}

		})
		.catch(err => console.error('공지 목록 조회 중 에러:', err));
}

// ——— 페이징 UI 생성 ———
function renderPagination({ startPage, endPage, currentPage, totalPages }) {
	let html = '';

	// ← Previous
	html += `
    <a href="#" data-page="${startPage - 1}"
       class="page-link ${startPage <= 1 ? 'disabled' : ''}">
      ← Previous
    </a>`;

	// 페이지 번호
	for (let p = startPage; p <= endPage; p++) {
		html += `
      <a href="#"
	  	 onclick="page(${p})"
         data-page="${p}"
         class="page-link ${p === currentPage ? 'active' : ''}">
        ${p}
      </a>`;
	}

	// Next →
	html += `
    <a href="#" data-page="${endPage + 1}"
       class="page-link ${endPage >= totalPages ? 'disabled' : ''}">
      Next →
    </a>`;

	// .panel-footer.pagination 안에 직접 <a> 나열
	document.querySelector('.panel-footer.pagination').innerHTML = html;
}
let pageData;
function page(page){
	pageData = page;
}

// ——— 이벤트 바인딩 ———
// 1) 페이지 번호 클릭
document.querySelector('.pagination').addEventListener('click', e => {
	e.preventDefault();
	const a = e.target.closest('a[data-page]');
	if (!a) return;
	const page = parseInt(a.dataset.page, 10);
	if (isNaN(page) || page < 1) return;
	fetchNotices(page);
});

// 2) 검색(조회) 버튼 클릭
document.querySelector('.btn-save').addEventListener('click', () => {
	fetchNotices(1);
});

// ——— 초기 로드 ———
fetchNotices(1);

// 공지사항 클릭 시 초기화
document.getElementById('noticeHeader')
	.addEventListener('click', () => {
		// 1) 검색어 초기화
		const kwInput = document.querySelector('input[name="keyword"]');
		if (kwInput) kwInput.value = '';

		// 2) 연도(상태) select 초기화
		const statusSelect = document.querySelector('select[name="status"]');
		if (statusSelect) {
			// 기본 옵션이 첫 번째라면 selectedIndex = 0
			statusSelect.selectedIndex = 0;
			// 또는 value 속성이 정해져 있으면
			// statusSelect.value = '2025';
		}

		// 3) 1페이지 데이터 비동기 재조회
		fetchNotices(1);
	});

// 3) 상세 조회
function showDetail(no) {
	console.log("no : ", no);
	axios.get('csc/admin/noticeDetail.do', {
		params: {
			no: no
		}
	})
		.then(response => {
			data = no;
			document.getElementById("btn-delete").style.display = "block";
			document.getElementById("btn-save").innerHTML = "수정";
			document.getElementById('noticeId').value = no;
			const infoTable = document.querySelector('.info-table');
			infoTable.style.display = 'table';

			// 사용자 기본 정보
			console.log("response : ", response.data);
			const resp = response.data;
			let html = "";
			html += `<tr>
			<td>${resp.noticeId}</td>
			<td>${resp.noticeTitle}</td>
			<td>${formatDateMMDD(resp.noticeUpdatedAt)}</td>
			<td>${resp.noticeCnt}</td>
			</tr>`
			document.getElementById("info-table-tbody").innerHTML = html;
			
			const titleInput = document.querySelector('input[name="noticeTitle"]');
			titleInput.value = resp.noticeTitle;


			console.log("resp.noticeContent : ", resp.noticeContent);
			if (window.editor) {
				window.editor.setData(resp.noticeContent);
			} else {
				// 에디터가 아직 초기화 전일 경우 textarea fallback
				document.getElementById("noticeContent").value = resp.noticeContent;
			}

			console.log("getFileList : ", resp.getFileList);
			const ul = document.getElementById("existing-files");
			if (!resp.getFileList || resp.getFileList.length === 0) {
				ul.innerHTML = '<li>첨부된 파일이 없습니다.</li>';
			} else {
				document.getElementById('fileGroupNo').value = resp.getFileList[0].fileGroupId;
				
				
				document.getElementById("file").style.display = "block";
				ul.innerHTML = resp.getFileList.map(f => `
			       <li>
			         <div onclick="filedownload('${f.fileGroupId}', ${f.fileSeq})" target="_blank">${f.fileOrgName}</div>&nbsp&nbsp
					 <button type="button" onclick="deleteExistingFile('${f.fileGroupId}', ${f.fileSeq},${no}  )">
			           삭제
			         </button>
			       </li>
			     `).join('');
			}

		})
		.catch(error => {
			console.log(error);
		})
}

/*
responseType: 'blob'
→ 서버로부터 파일 바이너리를 받기 위해 반드시 필요합니다.

.download = '파일명.확장자'
→ 이 부분을 서버에서 파일명과 확장자를 받아 설정하고 싶다면,
응답의 Content-Disposition 헤더에서 추출하는 방식도 가능합니다.
*/
function filedownload(fileGroupId, fileSeq) {
  axios({
    method: 'get',
    url: `/files/download`,
    params: {
		groupId: fileGroupId, 
		seq: fileSeq            
    },
    responseType: 'blob' // 중요: 파일 다운로드 시 꼭 필요
  })
  .then(response => {
    // 브라우저에서 파일 저장 처리
    const blob = new Blob([response.data]);
    const url = window.URL.createObjectURL(blob);
    
    // 파일 이름을 지정하고 다운로드 트리거
    const a = document.createElement('a');
    a.href = url;
    a.download = `파일명_${fileSeq}.확장자`; // 필요 시 서버에서 받아오도록 수정 가능
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  })
  .catch(error => {
    console.error('파일 다운로드 실패:', error);
  });
}

//파일 삭제 및 파일 그룹 삭제
function deleteExistingFile(fileGroupId, seq, no) {
	axios({
		method: 'get',
		url: `/csc/admin/deleteFile`,
		params: {
			groupId: fileGroupId,
			seq: seq
		}
	})
	.then(response=>{
		console.log(response);
		showDetail(no);
	})

}

// CKEditor
ClassicEditor.create(document.getElementById("noticeContent"), {
	ckfinder: { uploadUrl: "/image/upload" }
})
	.then(editor => { window.editor = editor })
	.catch(err => { console.error(err.stack); })
	;

// 만일 상세 조회 시 데이터를 초기화하고 싶다면
function resetDetail() {
	
	document.getElementById("btn-delete").style.display = "none";
	document.getElementById("file").style.display = "none";
	document.getElementById("existing-files").innerHTML="";
	document.getElementById("btn-save").innerHTML="등록";
	// 1) 상세 테이블 숨기기 + 내용 비우기
	const infoTable = document.querySelector('.info-table');
	if (infoTable) {
		infoTable.style.display = 'none';
		document.getElementById('info-table-tbody').innerHTML = '';
	}

	// 2) 제목 input 비우기
	const titleInput = document.querySelector('input[name="noticeTitle"]');
	if (titleInput) titleInput.value = '';

	// 3) CKEditor5 비우기
	if (window.editor) {
		window.editor.setData('');
	} else {
		const ta = document.getElementById('noticeContent');
		if (ta) ta.value = '';
	}

	// 4) 파일 input 초기화
	const fileInput = document.getElementById('noticeFileInput');
	if (fileInput) fileInput.value = '';
}

let data; 

// 파일 등록 혹은 수정
function insertOrUpdate() {

	const infoTable = document.querySelector('.info-table');
	if (infoTable.style.display == 'none') {
		console.log("이건 등록");

		const form = document.getElementById('form-data');
		const fd = new FormData(form);

		// CKEditor 쓰시면 textarea 대신 아래처럼 덮어쓰기
		if (window.editor?.getData) {
			fd.set('noticeContent', window.editor.getData());
		}
		console.log(fd);
		// **절대로** 헤더를 직접 지정하지 말고…
		axios.post('/csc/admin/insertNotice', fd)
			.then(res => {
				console.log('등록 성공:', res.data);
				form.reset();
				if (window.editor?.setData) window.editor.setData('');
				fetchNotices(1);
			})
			.catch(err => {
				console.error('등록 실패:', err.response || err);
			});

	} else {
		
		console.log("이건 수정");
		console.log("data",data);		

		const form = document.getElementById('form-data');
		const fd = new FormData(form);
		// CKEditor 쓰시면 textarea 대신 아래처럼 덮어쓰기
		if (window.editor?.getData) {
			fd.set('noticeContent', window.editor.getData());
		}
		console.log(fd);
		axios.post('/csc/admin/updateNotice', fd)
			 .then(res => {
				console.log('수정 성공:', res.data);
				if (window.editor?.setData) window.editor.setData('');
				fetchNotices(pageData);
				showDetail(data);
			 })
			 .catch(err => {
				console.error('수정 실패:', err.response || err);
			 });
	}
}


//페이지 삭제
function deleteNotice() {

	const form = document.getElementById('form-data');
	const fd = new FormData(form);
	
	if (window.editor?.getData) {
		fd.set('noticeContent', window.editor.getData());
	}
	console.log(fd);
	
	axios.post('/csc/admin/deleteNotice', fd)
		.then(res => {
			console.log('삭제 성공:', res.data);
			fetchNotices(1);
			resetDetail();
		})
		.catch(err => {
			console.error('삭제 실패:', err.response || err);
		});
}