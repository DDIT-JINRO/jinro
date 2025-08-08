/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
	let editorInstance;
	const fileInput = document.getElementById('fileInput');
	const boardId = document.getElementById('boardId').value;
	
	ClassicEditor
		.create(document.querySelector('#editor'))
		.then(editor => {
			editorInstance = editor;

			if (typeof boardContent !== 'undefined') {
				editor.setData(boardContent);
			}
		})
		.catch(error => {
			console.error(error);
		});

	document.getElementById("submitBtn").addEventListener("click", async function () {
		const title = document.getElementById("title").value.trim();
		const content = editorInstance.getData();

		if (!title || !content) {
			alert("제목과 내용을 모두 입력해 주세요.");
			return;
		}

		const formData = new FormData();
		formData.append('boardId', boardId);
		formData.append('title', title);
		formData.append('content', content);

		const files = fileInput.files;
		for (let i = 0; i < files.length; i++) {
			formData.append('files', files[i]);
		}

		try {
			const response = await axios.post("/cdp/rsm/rsmb/resumeBoardUpdate.do", formData, {
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

	document.getElementById("backBtn").addEventListener("click", function () {
		window.history.back();
	});
});

document.addEventListener("DOMContentLoaded", function() {
    // --- Part 1: 기존 파일 미리보기 로직 (상세보기 페이지와 동일) ---
    const existingFileContainer = document.querySelector('.fileClass');
    if (existingFileContainer) {
        existingFileContainer.addEventListener('click', function(event) {
            if (event.target.classList.contains('btn-pdf-preview')) {
                const button = event.target;
                const pdfUrl = button.dataset.pdfUrl;
                const targetId = button.dataset.targetId;
                const previewContainer = document.getElementById(targetId);
				
				let cleanPdfUrl = pdfUrl.replace(/\\/g, '/'); // 모든 '\'를 '/'로 변경
				if (!cleanPdfUrl.startsWith('/')) {
				    cleanPdfUrl = '/' + cleanPdfUrl; // 경로가 '/'로 시작하지 않으면 추가
				}

                if (previewContainer.style.display !== 'block') {
                    if (previewContainer.innerHTML === '') {
                        const iframe = document.createElement('iframe');
                        const viewerURL = `/js/pdfjs/web/viewer.html?file=${encodeURIComponent(cleanPdfUrl)}#zoom=page-fit`;
                        iframe.src = viewerURL;
                        iframe.title = "PDF 미리보기";
                        previewContainer.appendChild(iframe);
                    }
                    previewContainer.style.display = 'block';
                    button.textContent = '미리보기 닫기';
                } else {
                    previewContainer.style.display = 'none';
                    button.textContent = '미리보기';
                }
            }
        });
    }

    // --- Part 2: 새로 첨부하는 파일 미리보기 로직 (글쓰기 페이지와 동일) ---
    const fileInput = document.getElementById('fileInput');
    const newPreviewSection = document.getElementById('new-preview-section');
    const newPreviewList = document.getElementById('new-preview-list');

    if(fileInput) {
        fileInput.addEventListener('change', function(event) {
            newPreviewList.innerHTML = ''; // 새 파일을 선택할 때마다 기존 미리보기 초기화

            const files = event.target.files;
            let pdfFound = false;

            for (const file of files) {
                if (file.type === 'application/pdf') {
                    pdfFound = true;
                    
                    const previewItem = document.createElement('div');
                    previewItem.className = 'pdf-preview-item';

                    const fileName = document.createElement('h4');
                    fileName.className = 'pdf-file-name';
                    fileName.textContent = file.name;

                    const iframe = document.createElement('iframe');
                    iframe.className = 'pdf-iframe';
                    iframe.title = file.name + ' 미리보기';

                    // 테마 설정 (예: 다크모드)
                    iframe.addEventListener('load', function() {
                        const theme = 2; // 2: 다크
                        const interval = setInterval(() => {
                            const iframeDoc = this.contentDocument;
                            if (iframeDoc && iframeDoc.getElementById('viewerContainer')) {
                                clearInterval(interval);
                                const html = iframeDoc.documentElement;
                                html.classList.remove('light', 'dark');
                                if (theme === 2) html.classList.add('dark');
                            }
                        }, 100);
                        setTimeout(() => clearInterval(interval), 10000);
                    });

                    const fileURL = URL.createObjectURL(file);
                    const viewerURL = `/js/pdfjs/web/viewer.html?file=${encodeURIComponent(fileURL)}#zoom=page-fit`;
                    iframe.src = viewerURL;

                    previewItem.appendChild(fileName);
                    previewItem.appendChild(iframe);

                    newPreviewList.appendChild(previewItem);
                }
            }

            // PDF 파일이 하나라도 있으면 '새 파일 미리보기' 섹션을 보여줌
            if (pdfFound) {
                newPreviewSection.style.display = 'block';
            } else {
                newPreviewSection.style.display = 'none';
            }
        });
    }
});