/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
	let editorInstance;
	const fileInput = document.getElementById('file-input');

	ClassicEditor
		.create(document.querySelector('#editor'))
		.then(editor => {
			editorInstance = editor;
		})
		.catch(error => {
			console.error(error);
		});

	document.getElementById("submitBtn").addEventListener("click", async function() {
		const title = document.getElementById("title").value.trim();
		const content = editorInstance.getData();

		if (!title || !content) {
			alert("제목과 내용을 모두 입력해 주세요.");
			return;
		}

		const formData = new FormData();
		formData.append('title', title);
		formData.append('content', content);
		
		const files = fileInput.files;
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}


		try {
			const response = await axios.post("/cdp/rsm/rsmb/resumeBoardInsert.do", formData, {
				headers: {
					"Content-Type": "multipart/form-data"
				}
			});
			if (response.status === 200) {
				alert("등록 성공");
				window.location.href = "/cdp/rsm/rsmb/resumeBoardList.do";
			}
		} catch (error) {
			console.error("등록 중 오류:", error);
			alert("등록에 실패했습니다.");
		}
	});

	document.getElementById("backBtn").addEventListener("click", function() {
		window.history.back();
	});
});

document.getElementById('file-input').addEventListener('change', function(event) {
    const previewContainer = document.getElementById('preview-container');
    const previewList = document.getElementById('preview-list');
    
    // 이전에 생성된 미리보기가 있다면 모두 삭제
    previewList.innerHTML = '';

    const files = event.target.files;
    let pdfFound = false; // PDF 파일 존재 여부 플래그

    // 선택된 모든 파일을 순회
    for (const file of files) {
        // 파일 타입이 PDF인 경우에만 미리보기 생성
        if (file.type === 'application/pdf') {
            pdfFound = true; // PDF 파일을 찾았다고 표시

            // 1. 미리보기를 감쌀 div 생성
            const previewItem = document.createElement('div');
            previewItem.className = 'pdf-preview-item';

            // 2. 파일 이름을 표시할 h4 태그 생성
            const fileName = document.createElement('h4');
            fileName.className = 'pdf-file-name';
            fileName.textContent = file.name;

            // 3. PDF를 표시할 iframe 생성
            const iframe = document.createElement('iframe');
            iframe.className = 'pdf-iframe';
            iframe.title = file.name + ' 미리보기';
			
            // 4. iframe에 PDF 뷰어 경로 설정
            const fileURL = URL.createObjectURL(file);
            const viewerURL = `/js/pdfjs/web/viewer.html?file=${encodeURIComponent(fileURL)}#zoom=page-fit`;
            iframe.src = viewerURL;

            // 5. 생성된 요소들을 조립하여 preview-list에 추가
            previewItem.appendChild(fileName);
            previewItem.appendChild(iframe);
            previewList.appendChild(previewItem);
        }
    }

    // PDF 파일이 하나라도 있었으면 전체 미리보기 컨테이너를 보여줌
    if (pdfFound) {
        previewContainer.style.display = 'block';
    } else {
        previewContainer.style.display = 'none';
    }
});