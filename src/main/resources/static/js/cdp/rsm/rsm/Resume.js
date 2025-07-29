document.addEventListener("DOMContentLoaded", function() {
    const loadButtons = document.querySelector(".button-group");

    // 버튼 클릭 시 학력 항목 추가
    loadButtons.addEventListener("click", function(e) {
        let rsId = e.target.dataset.id;
        console.log("rsId : " + rsId);

        axios.get('/rsm/rsm/getElement?rsId=' + rsId)
            .then(function(response) {
                const form = document.querySelector(".personal-info-form");
                const lastDiv = form.lastElementChild; // 폼의 마지막 div를 찾음

                // 새로 받은 HTML 데이터를 마지막 div 밑에 추가
                lastDiv.insertAdjacentHTML('afterend', response.data);

                // DOM이 추가된 후, 이벤트 리스너를 한 번만 등록하도록 처리
                addEventListeners();
            });
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
        const containerCertificate = document.querySelector('.personal-info-section');
        if (containerCertificate) {  // 요소가 존재할 때만 이벤트 리스너를 추가
            containerCertificate.removeEventListener('click', handleCertificateClick);  // 기존 이벤트 리스너 제거
            containerCertificate.addEventListener('click', handleCertificateClick);  // 이벤트 리스너 추가
        }
    }

    // 학력 항목 클릭 이벤트 처리
    function handleEducationClick(event) {
        if (event.target && event.target.closest('.form-Education button')) {
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
            newInput.required = true;

            newEducationInputGroup.appendChild(newSelect);
            newEducationInputGroup.appendChild(newInput);

            educationInputGroupContainer.appendChild(newEducationInputGroup);
        }
    }

    // 자격증 항목 클릭 이벤트 처리
    function handleCertificateClick(event) {
        if (event.target && event.target.closest('.certificate-form button')) {
            const certificateInputGroup = event.target.closest('.certificate-form').querySelector('.certificate-input-container');

            // 1개씩만 추가
            const newInput = document.createElement('input');
            newInput.type = 'text';
            newInput.name = 'certificate';
            newInput.classList.add('certificate-input');
            newInput.placeholder = '자격증을 입력하세요';
            certificateInputGroup.appendChild(newInput);
        }
    }

});
