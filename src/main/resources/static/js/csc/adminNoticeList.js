// adminNoticeList.js

console.log("asdasdas");
// 1) 리스트 조회 AJAX  
axios.post('/csc/admin/noticeList.do')
	.then(response => {
		const list = response.data.getList;
		const total = response.data.getAllNotice;
		document.getElementById("notice-count").textContent = total;

		let html = "";
		list.forEach(item => {
			html += `
          <tr>
            <td>${item.noticeId}</td>
            <td>${item.noticeTitle}</td>
            <td>${item.noticeCnt}</td>
            <td>${item.noticeUpdatedAt}</td>
          </tr>`;
		});
		document.getElementById("notice-list").innerHTML = html;
	})
	.catch(error => console.error("에러:", error));

// 2) CKEditor 초기화
if (typeof CKEDITOR !== 'undefined') {
    CKEDITOR.replace('noticeContent', {
      height: 300,
      toolbarGroups: [
        { name: 'clipboard',    groups: ['clipboard','undo'] },
        { name: 'basicstyles',  groups: ['basicstyles','cleanup'] },
        { name: 'paragraph',    groups: ['list','indent','blocks','align'] },
        { name: 'links' },
        { name: 'insert' },
        { name: 'styles' },
        { name: 'colors' },
        { name: 'tools' }
      ],
      removeButtons: 'Save,NewPage,Print,Preview,Scayt,Templates'
    });
  } else {
    console.error("CKEDITOR is undefined. CDN 스크립트가 제대로 로드되었는지 확인하세요.");
  }

