// adminNoticeList.js
document.addEventListener("DOMContentLoaded", function(){
	console.log("asdf");
})
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




ClassicEditor.create(document.getElementById("noticeContent"), { ckfinder: { uploadUrl: "/image/upload" } })
	.then(editor => { window.editor = editor })
	.catch(err => { console.error(err.stack); })
	;
