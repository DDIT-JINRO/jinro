document.addEventListener("DOMContentLoaded", function() {
	//이미지 미리보기
	let lastFile = null;  // 마지막 파일을 저장할 변수

	// 파일 선택 시
	document.querySelector("#photo-upload").addEventListener("input", function (event) {
	    const file = event.target.files[0];  // 선택된 파일
	    const preview = document.querySelector("#photo-preview");
	    const placeholder = document.querySelector(".upload-placeholder");
	    // 파일이 선택된 경우에만 처리
		
	        // 이전에 선택된 파일과 비교
	        if (file != null && file !== lastFile) {
	            const reader = new FileReader();
	            reader.onload = function (e) {
	                preview.src = e.target.result;  // 미리보기 이미지
	                preview.style.display = "block";  // 이미지 표시

	                // 아이콘/텍스트 숨기기
	                if (placeholder) {
	                    placeholder.classList.add("hidden");
	                }
	            };
	            reader.readAsDataURL(file);

	            lastFile = file;  // 마지막 파일 업데이트
	        }
	});

	document.querySelector("#photo-delete-btn").addEventListener("click",function(event){
		let file = document.querySelector("#photo-upload");
		const preview = document.querySelector("#photo-preview");
		const placeholder = document.querySelector(".upload-placeholder");
        preview.src = "";
        preview.style.display = "none";  // 미리보기 숨기기
        if (placeholder) {
            placeholder.classList.remove("hidden");  // 아이콘/텍스트 다시 보이기
        }
		file = null;
	})
	
	// 파일 입력 취소 시 기존 파일 값 유지
	document.querySelector("#photo-upload").addEventListener("click", function () {
	    // 클릭 시 파일 값이 null로 초기화되는 문제 해결
	    const photoInput = document.querySelector("#photo-upload");
	    if (!photoInput.files[0] && lastFile) {
	        // 취소 후 기존 파일 유지
	        const dataTransfer = new DataTransfer();
	        dataTransfer.items.add(lastFile);
	        photoInput.files = dataTransfer.files;
	    }
	});

	
    const loadButtons = document.querySelectorAll(".load-button-group button");

    // 버튼 클릭 시 항목 추가
	loadButtons.forEach(button => {
        button.addEventListener("click", function(e) {
            // Your code to handle the click event
            let rsId = e.target.dataset.id;

            // 콘텐츠가 이미 추가되어 있는지 확인
            const form = document.querySelector(".personal-info-form");
            const existingElement = form.querySelector(`[data-id="${rsId}"]`);

            if (existingElement) {
                // 이미 추가된 콘텐츠가 있으면 해당 콘텐츠를 삭제
                existingElement.remove();
            } else {
                // 추가된 콘텐츠가 없으면 서버에서 HTML 데이터를 받아옴
                axios.get('/rsm/rsm/getElement?rsId=' + rsId)
                    .then(function(response) {
                        const lastDiv = form.lastElementChild; // 폼의 마지막 div를 찾음

                        // 새로 받은 HTML 데이터를 마지막 div 밑에 추가
                        lastDiv.insertAdjacentHTML('afterend', response.data);

                        // 추가된 콘텐츠에 data-id를 추가하여 나중에 확인할 수 있도록 설정
                        const newElement = form.lastElementChild;
                        newElement.setAttribute("data-id", rsId);

                        // DOM이 추가된 후, 이벤트 리스너를 한 번만 등록하도록 처리
                        addEventListeners();
                    });
            }
        });
	});



    document.getElementById("add-job").addEventListener("click", function() {
        // 'job-input-group' div를 찾기
        const jobInputGroup = document.querySelector(".job-input-group");

        // 새로운 input 요소 생성
        const newInput = document.createElement('input');
        newInput.type = 'text';
        newInput.name = 'desired-job';
        newInput.className = 'desired-job';
        newInput.placeholder = '희망 직무를 입력하세요..';
        newInput.value='';
        newInput.required = true;

        // 삭제 버튼 생성
        const deleteButton = createDeleteButton(newInput);

        // 새로 생성된 input과 삭제 버튼을 div에 추가
        const inputContainer = document.createElement('div');
        inputContainer.classList.add('input-container');
        inputContainer.appendChild(newInput);
        inputContainer.appendChild(deleteButton);

        // 새로 생성된 input과 버튼을 기존 input 아래에 추가
        jobInputGroup.appendChild(inputContainer);
    });

    document.getElementById("add-skill").addEventListener("click", function() {
        const skillsInputGroup = document.querySelector(".skills-input-group");

        // 새로운 input 요소 생성
        const newInput = document.createElement('input');
        newInput.type = 'text';
        newInput.name = 'skills';
        newInput.placeholder = '스킬을 입력하세요';
        newInput.value='';
        newInput.required = true;

        // 삭제 버튼 생성
        const deleteButton = createDeleteButton(newInput);

        // 새로 생성된 input과 삭제 버튼을 div에 추가
        const inputContainer = document.createElement('div');
        inputContainer.classList.add('input-container');
        inputContainer.appendChild(newInput);
        inputContainer.appendChild(deleteButton);

        // 새로 생성된 input과 버튼을 기존 input 아래에 추가
        skillsInputGroup.appendChild(inputContainer);
    });
	
	const deleteButton = document.querySelector("#btn-resume-delete");
	if (deleteButton) {
		deleteButton.addEventListener("click",function(){
		const resumeId = document.querySelector("#resumeId").value;
		console.log(resumeId)
		
		if (!resumeId || resumeId === "0") {
		   alert("삭제할 이력서가 없습니다.");
		   return;
		 }

		 if (!confirm("정말 삭제하시겠습니까?")) return;

		 axios.post("/rsm/rsm/deleteResume.do",resumeId)
		   .then(response => {
		     if (response.data.status === 'success') {
		       alert("이력서가 삭제되었습니다.");
		       location.href = "/rsm/rsm"; // 삭제 후 목록 페이지 등으로 이동
		     } else {
		       alert("삭제에 실패했습니다.");
		     }
		   })
		   .catch(err => {
		     console.error("삭제 중 오류 발생:", err);
		   });
	})
}
    document.querySelectorAll("#btn-submit-Temp, #btn-submit")
		.forEach(button => {button.addEventListener("click",function (event){
			const target = event.target;
	        const allElementDiv = document.querySelector(".personal-info-form");
			const resumeTitle = document.querySelector("#resumeTitle");
			const resumeTitleVal = document.querySelector("#resumeTitle").value;
			const resumeId = document.querySelector("#resumeId").value;
			const fileGroupId = document.querySelector("#fileGroupId").value;
	        const objs = allElementDiv.querySelectorAll('input, select, textarea');  // 모든 입력 요소를 선택
	
			//제목 검사
			if(resumeTitle.hasAttribute("required") && !resumeTitleVal.trim()){
				alert("제목을 입력해주세요.");
		        resumeTitle.focus();
		        return; 
			}
			
	    	for (let i = 0; i < objs.length; i++) {
	            
	            // 'value'가 반영된 상태로 outerHTML을 가져오기
	            const updatedInput = objs[i];  // 해당 input 요소
				const value = updatedInput.value.trim();
				
	            // 동적으로 업데이트된 값을 반영
	            if (updatedInput.tagName === "INPUT" || updatedInput.tagName === "TEXTAREA"
	                || updatedInput.tagName === "SELECT") {
	                updatedInput.setAttribute("value", updatedInput.value);  // 동적으로 변경된 값을 반영
	            }
				
				// 'required'가 있고 값이 비어 있다면
			    if (updatedInput.hasAttribute("required") && !value) {
			        alert("필수 입력 항목을 입력해주세요.");
			        updatedInput.focus();
			        return; // 함수 종료하여 submit 중단
			    }
	
				
				// 포맷 검사
				if (updatedInput.name === "email") {
				    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
				    if (!emailRegex.test(value)) {
				        alert("이메일 형식이 올바르지 않습니다.");
				        updatedInput.focus();
				        return;
				    }
				}
	
				if (updatedInput.name === "phone" || updatedInput.name === "mobile-phone") {
				    const phoneRegex = /^010\d{4}\d{4}$/; // 예: 010-1234-5678
				    if (value && !phoneRegex.test(value)) {
				        alert("전화번호 형식이 올바르지 않습니다. 예: 01012345678");
				        updatedInput.focus();
				        return;
				    }
				}
	
	        }
			let resumeIsTemp = null
			
			if (target.id === "btn-submit-Temp") {
			  resumeIsTemp = 'Y'
			  // 임시 저장 로직 실행
			} else if (target.id === "btn-submit") {
			  resumeIsTemp = 'N'
			  // 정식 저장 로직 실행
			}
			
			// 실제 이미지 input에서 파일 추출
			const photoInput = document.querySelector("#photo-upload");
			const photoFile = photoInput.files[0];
			
			applySelectedToOptions();
	        resumeContent = allElementDiv.outerHTML;  // value가 반영된 상태의 outerHTML

			submitResume(resumeTitleVal, resumeContent, resumeId,photoFile,fileGroupId,resumeIsTemp);

	    })
	
	    const delectBtn = document.getElementsByClassName("delete-button");
	
	    for (let i = 0; i < delectBtn.length; i++) {
	        delectBtn[i].addEventListener("click", function(e) {
	            e.target.parentElement.remove();
	        });
	    }
	    addEventListeners();
	});
	
	/*--미리보기*/
	document.querySelector("#btn-preview").addEventListener("click", () => {
	    const originalForm = document.querySelector(".personal-info-section"); // 이력서의 주요 내용이 담긴 섹션
	    const clonedForm = originalForm.cloneNode(true); // 깊은 복사

	    const originalInputs = originalForm.querySelectorAll("input, textarea, select");
	    const clonedInputs = clonedForm.querySelectorAll("input, textarea, select");

	    clonedInputs.forEach((clonedEl, i) => {
	        const originalEl = originalInputs[i];
	        if (clonedEl.tagName === "TEXTAREA") {
	            clonedEl.innerHTML = originalEl.value;
	        } else if (clonedEl.type === "checkbox" || clonedEl.type === "radio") {
	            clonedEl.checked = originalEl.checked;
	        } else if (clonedEl.tagName === "SELECT") { // select 요소의 value도 반영
	            clonedEl.value = originalEl.value;
	        }
			else {
			       // ⭐ 이 부분을 수정합니다. input 태그의 value 프로퍼티에 직접 값을 할당합니다.
			       clonedEl.value = originalEl.value; // input의 현재 값을 반영
			   }
	    });

	    // ⭐ 이미지 src가 없으면 base64 미리보기 반영
	    // photo-preview는 clonedForm 내부에 있는 요소를 선택해야 합니다.
	    const previewImg = clonedForm.querySelector("#photo-preview");
	    const realImg = document.querySelector("#photo-preview"); // 실제 폼의 이미지 요소

	    if (realImg && realImg.src && realImg.src.startsWith("data:image")) {
	        previewImg.src = realImg.src;
	        previewImg.style.display = "block";
	    } else {
	        // ⭐ 일반 이미지의 경우 절대 경로 또는 웹 URL로 변경 시도
	        if (previewImg && previewImg.src && previewImg.src.startsWith("/upload/")) {
	            previewImg.src = window.location.origin + previewImg.src; // 현재 웹사이트의 도메인을 붙여 완전한 URL로 만듬
	        }
	    }

	    // 기존 버튼 그룹 제거 (클론된 폼에서 제거)
	    const btnGroup = clonedForm.querySelector(".btn-group");
	    if (btnGroup) {
	        btnGroup.remove();
	    }
	    
	    // 이력서 제목 제거 (만약 `.resume-title`이 버튼 그룹 안에 있다면)
	    const resumeTitleInput = clonedForm.querySelector(".resume-title");
	    if(resumeTitleInput) {
	        // resume-title 안에 있는 input만 제거할 수도 있고, section-title 처럼 전체 div를 제거할 수도 있습니다.
	        // 현재 코드에서는 `.resume-title` div 통째로 제거하는게 자연스러워 보입니다.
	        resumeTitleInput.remove(); 
	    }

	    // 폼 내용만 XHTML로 정제합니다. (전체 HTML 문서 구조는 포함하지 않음)
	    const xhtmlContent = sanitizeHtmlToXHTML(clonedForm.outerHTML);

	    // 2. 필요한 모든 CSS 파일들을 비동기로 불러옵니다.
	    Promise.all([
	            fetch("/css/cdp/rsm/rsm/resumeWriter.css").then(res => {
	                if (!res.ok) throw new Error(`Failed to load resumeWriter.css: ${res.status} ${res.statusText}`);
	                return res.text();
	            }),
	            fetch("/css/header.css").then(res => { // ⭐ header.css의 실제 경로로 수정하세요! ⭐
	                if (!res.ok) throw new Error(`Failed to load header.css: ${res.status} ${res.statusText}`);
	                return res.text();
	            })
	        ])
	        .then(([resumeWriterCss, headerCss]) => {
	            // 불러온 모든 CSS 내용을 합칩니다.
	            const combinedCss = `${resumeWriterCss}\n${headerCss}`;

	            // 3. FormData 구성
	            const formData = new FormData();
	            formData.append("htmlContent", xhtmlContent); // 정제된 HTML 내용만
	            formData.append("cssContent", combinedCss);   // 합쳐진 CSS 내용

	            // 4. 미리보기 요청
	            return fetch("/pdf/preview", {
	                method: "POST",
	                body: formData
	            });
	        })
	        .then(response => {
	            if (!response.ok) throw new Error("미리보기 요청 실패");
	            return response.blob();
	        })
	        .then(blob => {
	            const url = URL.createObjectURL(blob);
	            const pdfUrlWithZoom = url + "#zoom=75";

	            const windowWidth = 900;
	            const windowHeight = 700;
	            const left = (screen.width - windowWidth) / 2;
	            const top = (screen.height - windowHeight) / 2;
	            const windowFeatures = `width=${windowWidth},height=${windowHeight},left=${left},top=${top},scrollbars=yes,resizable=yes,toolbar=no,location=no,status=no`;

	            const previewWindow = window.open(pdfUrlWithZoom, "pdfPreview", windowFeatures);
	            if (!previewWindow) window.open(pdfUrlWithZoom, "_blank"); // 팝업 차단 시 대비
	        })
	        .catch(err => {
	            console.error("PDF 미리보기 오류:", err);
	            alert("PDF 미리보기 중 오류가 발생했습니다: " + err.message);
	        });
	});
	
});


	function sanitizeHtmlToXHTML(html) {
	  return html
	    .replace(/<meta([^>]*?)(?<!\/)>/gi, '<meta$1 />')
	    .replace(/<link([^>]*?)(?<!\/)>/gi, '<link$1 />')
	    .replace(/<input([^>]*?)(?<!\/)>/gi, '<input$1 />')
	    .replace(/<br([^>]*?)(?<!\/)>/gi, '<br$1 />')
	    .replace(/<hr([^>]*?)(?<!\/)>/gi, '<hr$1 />')
	    .replace(/<img([^>]*?)(?<!\/)>/gi, '<img$1 />');
	}

	
	//select selected 해주는 함수
	function applySelectedToOptions() {
	  const selects = document.querySelectorAll("select");
	
	  selects.forEach(select => {
	    const selectedValue = select.value;
	
	    Array.from(select.options).forEach(option => {
	      option.removeAttribute("selected");
	    });
	
	    const selectedOption = Array.from(select.options).find(
	      option => option.value === selectedValue
	    );
	    if (selectedOption) {
	      selectedOption.setAttribute("selected", "selected");
	    }
	  });
	}


	//form형태로 전송하는 함수
	function submitResume(resumeTitle, resumeContent, resumeId, photoFile, fileGroupId,resumeIsTemp) {
		const formData = new FormData();
		  formData.append('resumeTitle', resumeTitle);
		  formData.append('resumeContent', resumeContent);
		  formData.append('fileGroupId',fileGroupId);
		  formData.append('resumeIsTemp',resumeIsTemp);
		  if (resumeId !== undefined && resumeId !== null) {
		    formData.append('resumeId', resumeId);
		  }
		  if (photoFile) {
		    formData.append('files', photoFile);
		  }

		  // FormData는 form.submit과 달리 비동기 전송
		  fetch('/rsm/rsm/insertResume', {
		    method: 'POST',
		    body: formData,
		  })
		    .then(response => response.json())
		    .then(data => {
		      if (data.status === 'success') {
		        location.href = `/rsm/rsm`;//resumeWriter?resumeId=${data.resumeId}`;
		      } else {
		        location.href = '/login';
		      }
		    })
		    .catch(err => console.error('에러 발생:', err));
	}

    // 이벤트 리스너 추가하는 함수
    function addEventListeners() {
        // 학력 항목 추가 이벤트
        const containerEducation = document.querySelector('.form-Education');
        if (containerEducation) {  // 요소가 존재할 때만 이벤트 리스너를 추가
            containerEducation.removeEventListener('click', handleEducationClick);  // 기존 이벤트 리스너 제거
            containerEducation.addEventListener('click', handleEducationClick); // 이벤트 리스너 추가
        }

        // 자격증 항목 추가 이벤트
        const containerCertificate = document.querySelector('.form-certificate');
        if (containerCertificate) {  // 요소가 존재할 때만 이벤트 리스너를 추가
            containerCertificate.removeEventListener('click', handleCertificateClick);  // 기존 이벤트 리스너 제거
            containerCertificate.addEventListener('click', handleCertificateClick);  // 이벤트 리스너 추가
        }

        // 대외 활동 항목 추가 이벤트
        const containerActivities = document.querySelector('.form-activities');
        if (containerActivities) {  // 요소가 존재할 때만 이벤트 리스너를 추가
            containerActivities.removeEventListener('click', handleActivitiesClick);  // 기존 이벤트 리스너 제거
            containerActivities.addEventListener('click', handleActivitiesClick); // 이벤트 리스너 추가
        }
    }

    // 학력 항목 클릭 이벤트 처리
    function handleEducationClick(event) {
        if (event.target && event.target.closest('.form-Education #add-education')) {
            const educationInputGroupContainer = event.target.closest('.form-Education').querySelector('.education-input-container');

            // 1개씩만 추가
            const newEducationInputGroup = document.createElement('div');
            newEducationInputGroup.classList.add('education-input-group');

            const newSelect = document.createElement('select');
            newSelect.name = 'education-level';
            newSelect.innerHTML = `
                <option value="">학력 종류 선택</option>
                <option value="중학교">중학교</option>
                <option value="고등학교">고등학교</option>
                <option value="대학교">대학교</option>
                <option value="대학원">대학원</option>
            `;

            const newInput = document.createElement('input');
            newInput.type = 'text';
            newInput.name = 'education';
            newInput.placeholder = '학교명을 입력하세요';
            newInput.value='';
            newInput.required = true;

            // 삭제 버튼 생성
            const deleteButton = createDeleteButton(newInput);

            // 새로 생성된 input과 삭제 버튼을 함께 추가
            const inputContainer = document.createElement('div');
            inputContainer.classList.add('input-container');
            inputContainer.appendChild(newSelect);
            inputContainer.appendChild(newInput);
            inputContainer.appendChild(deleteButton);

            educationInputGroupContainer.appendChild(inputContainer);
        }
    }

    // 자격증 항목 클릭 이벤트 처리
    function handleCertificateClick(event) {
        if (event.target && event.target.closest('.form-certificate #add-certificate')) {
            const certificateInputGroup = event.target.closest('.form-certificate').querySelector('.certificate-input-container');

            // 1개씩만 추가
            const newInput = document.createElement('input');
            newInput.type = 'text';
            newInput.name = 'certificate';
            newInput.classList.add('certificate-input');
            newInput.placeholder = '자격증을 입력하세요';
            newInput.value='';

            // 삭제 버튼 생성
            const deleteButton = createDeleteButton(newInput);

            // 새로 생성된 input과 삭제 버튼을 함께 추가
            const inputContainer = document.createElement('div');
            inputContainer.classList.add('input-container');
            inputContainer.appendChild(newInput);
            inputContainer.appendChild(deleteButton);

            certificateInputGroup.appendChild(inputContainer);
        }
    }

    // 대외활동 항목 클릭 이벤트 처리
    function handleActivitiesClick(event) {
        if (event.target && event.target.closest('.form-activities #add-activities')) {
            const activitiesInputGroup = event.target.closest('.form-activities').querySelector('.activities-input-container');

            // 1개씩만 추가
            const newInput = document.createElement('input');
            newInput.type = 'text';
            newInput.name = 'activities';
            newInput.classList.add('activities-input');
            newInput.placeholder = '활동 내용';
            newInput.value='';

            // 삭제 버튼 생성
            const deleteButton = createDeleteButton(newInput);

            // 새로 생성된 input과 삭제 버튼을 함께 추가
            const inputContainer = document.createElement('div');
            inputContainer.classList.add('input-container');
            inputContainer.appendChild(newInput);
            inputContainer.appendChild(deleteButton);

            activitiesInputGroup.appendChild(inputContainer);
        }
    }

    // 삭제 버튼 생성 함수
    function createDeleteButton(inputElement) {
        const deleteButton = document.createElement('button');
        deleteButton.textContent = '삭제';
        deleteButton.type = 'button';
        deleteButton.classList.add('delete-button');

        // 삭제 버튼 클릭 시 해당 input 필드를 삭제
        deleteButton.addEventListener('click', function() {
            inputElement.parentElement.remove(); // 해당 input과 삭제 버튼을 포함하는 div를 삭제
        });

        return deleteButton;
    }

