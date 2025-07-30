document.addEventListener("DOMContentLoaded", function() {
    const loadButtons = document.querySelectorAll(".button-group button");

    // 버튼 클릭 시 항목 추가
	loadButtons.forEach(button => {
        button.addEventListener("click", function(e) {
            // Your code to handle the click event
            let rsId = e.target.dataset.id;
            console.log("rsId : " + rsId);

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

    document.getElementById("btn-submit").addEventListener("click",function(){
        const allElementDiv = document.querySelector(".personal-info-form");

        const objs = allElementDiv.querySelectorAll('input, select, textarea');  // 모든 입력 요소를 선택

        for (let i = 0; i < objs.length; i++) {
            //console.log(objs[i].value);  // 사용자가 입력한 값
            
            // 'value'가 반영된 상태로 outerHTML을 가져오기
            const updatedInput = objs[i];  // 해당 input 요소

            // 동적으로 업데이트된 값을 반영
            if (updatedInput.tagName === "INPUT" || updatedInput.tagName === "TEXTAREA"
                || updatedInput.tagName === "SELECT") {
                updatedInput.setAttribute("value", updatedInput.value);  // 동적으로 변경된 값을 반영
            }

        }
        allElementHtml = allElementDiv.outerHTML;  // value가 반영된 상태의 outerHTML
        axios.post('/rsm/rsm/insertElement',allElementHtml)


    })

    const delectBtn = document.getElementsByClassName("delete-button");

    for (let i = 0; i < delectBtn.length; i++) {
        delectBtn[i].addEventListener("click", function(e) {
            e.target.parentElement.remove();
        });
    }
    addEventListeners();
});
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

