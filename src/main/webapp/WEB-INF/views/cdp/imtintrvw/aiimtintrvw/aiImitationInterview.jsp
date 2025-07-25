<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<style>
/* Figma 디자인 기반 AI 모의면접 스타일 */
.ai-interview-section {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    padding: 0px;
    gap: 20px;
    
    position: relative;
    width: 1160px;
    margin: 0 auto;
    margin-top: 50px;
}

/* AI 모의 면접 제목 */
.ai-interview-title {
    width: 100%;
    height: 50px;
    
    font-family: 'Do Hyeon', sans-serif;
    font-style: normal;
    font-weight: 600;
    font-size: 40px;
    line-height: 50px;
    display: flex;
    align-items: center;
    letter-spacing: 0.01em;
    
    color: #000000;
    
    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
}

/* 태그 컨테이너 */
.tag-container {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 0px;
    gap: 33px;
    
    width: 100%;
    height: 37px;
    
    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
}

/* 활성 태그 */
.tag.active {
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 5px 7px;
    gap: 10px;
    
    width: 260px;
    height: 37px;
    
    background: #FFFFFF;
    border: 2px solid #848EFB;
    border-radius: 10px;
    
    flex: none;
    order: 0;
    flex-grow: 0;
}

.tag.active .tag-text {
    font-family: 'IBM Plex Sans KR', sans-serif;
    font-style: normal;
    font-weight: 700;
    font-size: 18px;
    line-height: 27px;
    display: flex;
    align-items: center;
    text-align: center;
    color: #848EFB;
}

/* 일반 태그 */
.tag {
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 5px 7px;
    gap: 10px;
    
    width: 260px;
    min-width: 90px;
    height: 37px;
    
    background: #FFFFFF;
    border: 2px solid #D8D8D8;
    border-radius: 10px;
    
    flex: none;
    flex-grow: 0;
    cursor: pointer;
    transition: all 0.3s ease;
}

.tag .tag-text {
    font-family: 'IBM Plex Sans KR', sans-serif;
    font-style: normal;
    font-weight: 400;
    font-size: 18px;
    line-height: 27px;
    display: flex;
    align-items: center;
    text-align: center;
    color: #000000;
}

/* 메인 콘텐츠 영역 */
.main-content {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;
    gap: 50px;
    
    width: 100%;
    
    flex: none;
    order: 2;
    align-self: stretch;
    flex-grow: 0;
}

/* 질문 리스트 섹션 */
.question-list-section {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 0px;
    gap: 20px;
    
    width: 100%;
    
    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
}

.section-header {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: flex-start;
    padding: 0px;
    gap: 10px;
    
    width: 100%;
    height: 35px;
    
    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
}

.asterisk-icon {
    width: 35px;
    height: 35px;
    background: url('/images/imtintrvw/asterisk.png') no-repeat center;
    background-size: contain;
    
    flex: none;
    order: 0;
    flex-grow: 0;
}

.section-title {
    height: 35px;
    
    font-family: 'Noto Sans KR', sans-serif;
    font-style: normal;
    font-weight: 700;
    font-size: 24px;
    line-height: 29px;
    display: flex;
    align-items: center;
    
    color: #000000;
    
    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 1;
}

/* Select 박스 스타일링 */
.question-select {
    box-sizing: border-box;
    width: 100%;
    height: 49px;
    padding: 12px 12px;
    
    background: #FFFFFF;
    border: 1px solid #D8D8D8;
    border-radius: 5px;
    
    font-family: 'Noto Sans KR', sans-serif;
    font-style: normal;
    font-weight: 500;
    font-size: 16px;
    line-height: 19px;
    color: #000000;
    
    cursor: pointer;
    outline: none;
    transition: all 0.3s ease;
    
    /* 기본 select 화살표 숨기기 */
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    
    /* 커스텀 화살표 추가 */
    background-image: url('/images/imtintrvw/polygon.png');
    background-repeat: no-repeat;
    background-position: right 14px center;
    background-size: 13px 12px;
    
    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
}

.question-select:focus {
    border-color: #848EFB;
    box-shadow: 0 0 0 2px rgba(132, 142, 251, 0.2);
}

.question-select option {
    font-family: 'Noto Sans KR', sans-serif;
    font-size: 16px;
    padding: 10px;
    color: #000000;
    background: #FFFFFF;
}

/* placeholder 스타일 */
.question-select option:disabled {
    color: #9D9D9D;
    font-style: italic;
}

/* 로딩 상태 표시 */
.question-select.loading {
    background-image: none;
    cursor: wait;
}

.loading-text {
    color: #9D9D9D;
    font-style: italic;
}

/* 시작 전 확인사항 섹션 */
.checklist-section {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0px;
    gap: 15px;
    
    width: 100%;
    
    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
}

.checklist-container {
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 30px;
    gap: 30px;
    
    width: 100%;
    
    border: 2px solid #848EFB;
    border-radius: 10px;
    
    flex: none;
    order: 1;
    align-self: stretch;
    flex-grow: 0;
}

.checklist-item {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0px;
    gap: 15px;
    
    width: 100%;
    height: 22px;
    
    flex: none;
    align-self: stretch;
    flex-grow: 0;
}

.checklist-text {
    font-family: 'Noto Sans KR', sans-serif;
    font-style: normal;
    font-weight: 500;
    font-size: 16px;
    line-height: 19px;
    display: flex;
    align-items: center;
    text-align: center;
    
    color: #000000;
    
    flex: none;
    order: 0;
    flex-grow: 0;
}

.checkbox-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 0px;
    gap: 12px;
    
    width: 22px;
    height: 22px;
    
    flex: none;
    order: 1;
    flex-grow: 0;
}

.checkbox {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 0px;
    gap: 10px;
    
    width: 22px;
    height: 22px;
    
    background: #FFFFFF;
    border: 2px solid #D8D8D8;
    border-radius: 4px;
    
    flex: none;
    order: 0;
    flex-grow: 0;
    cursor: pointer;
    transition: all 0.3s ease;
}

/* 체크된 상태 */
.checkbox.checked {
    background: #FFFFFF url('/images/imtintrvw/checkbox.png') no-repeat center;
    background-size: 24px 24px;
    border: 2px solid transparent;
}

.checkbox-icon,
.checkbox-check {
    display: none;
}

/* 시작하기 섹션 */
.start-section {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0px;
    gap: 30px;
    
    width: 100%;
    
    flex: none;
    order: 2;
    align-self: stretch;
    flex-grow: 0;
}

.start-button {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0px;
    gap: 20px;
    
    width: 500px;
    height: 60px;
    
    background: #848EFB;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    transition: all 0.3s ease;
    
    flex: none;
    order: 1;
    flex-grow: 0;
}

.start-button:hover {
    background: #6B7EFC;
    transform: translateY(-2px);
}

.start-button:active {
    transform: translateY(0);
}

.start-button-text {
    width: 100%;
    height: 36px;
    
    font-family: 'IBM Plex Sans KR', sans-serif;
    font-style: normal;
    font-weight: 700;
    font-size: 24px;
    line-height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    text-align: center;
    
    color: #FFFFFF;
    
    flex: none;
    order: 0;
    align-self: stretch;
    flex-grow: 0;
}

.start-button.disabled {
    background: #D8D8D8;
    cursor: not-allowed;
    opacity: 0.6;
}

.start-button.disabled:hover {
    background: #D8D8D8;
    transform: none;
}

.start-button.disabled .start-button-text {
    color: #9D9D9D;
}

/* 로딩 스피너 */
.loading-spinner {
    display: none;
    width: 20px;
    height: 20px;
    border: 2px solid #ffffff;
    border-top: 2px solid transparent;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

/* 반응형 디자인 */
@media (max-width: 1200px) {
    .ai-interview-section {
        width: 90%;
        max-width: 1160px;
    }
    
    .tag-container {
        gap: 15px;
    }
    
    .tag {
        width: 200px;
        min-width: 150px;
    }
}

@media (max-width: 768px) {
    .ai-interview-section {
        width: 95%;
        padding: 0 10px;
    }
    
    .ai-interview-title {
        font-size: 28px;
        line-height: 35px;
        height: 35px;
    }
    
    .tag-container {
        flex-direction: column;
        gap: 10px;
        height: auto;
    }
    
    .tag {
        width: 100%;
    }
    
    .start-button {
        width: 100%;
        max-width: 400px;
    }
    
    .checklist-text {
        font-size: 14px;
        text-align: left;
    }
}
</style>

<section class="channel">
    <!-- 여기가 네비게이션 역할을 합니다. -->
    <div class="channel-title">
        <!-- 대분류 -->
        <div class="channel-title-text">경력관리</div>
    </div>
    <!-- 중분류 -->
    <div class="channel-sub-sections">
        <div class="channel-sub-section-item"><a href="/rsm/rsm">이력서</a></div>
        <div class="channel-sub-section-item"><a href="/sint/qestnlst">자기소개서</a></div>
        <div class="channel-sub-section-itemIn"><a href="/imtintrvw/bsintrvw">모의면접</a></div>
        <div class="channel-sub-section-item"><a href="/aifdbck/rsm">AI 피드백</a></div>
    </div>
</section>

<div>
    <div class="public-wrapper">
        <!-- 여기는 소분류(tab이라 명칭지음)인데 사용안하는곳은 주석처리 하면됩니다 -->
        <div class="tab-container" id="tabs">
            <a class="tab" href="/imtintrvw/bsintrvw">면접의 기본</a>
            <a class="tab" href="/imtintrvw/intrvwqestnlst">면접 질문 리스트</a>
            <a class="tab" href="/imtintrvw/intrvwqestnmn">면접 질문 관리</a>
            <a class="tab active" href="/imtintrvw/aiimtintrvw">AI 모의 면접</a>
        </div>
        
        <!-- 메인 컨텐츠 -->
        <div class="public-wrapper-main">
            <!-- Figma 디자인 기반 AI 모의면접 섹션 -->
            <div class="ai-interview-section">
                <!-- AI 모의 면접 제목 -->
                <div class="ai-interview-title">AI 모의 면접</div>
                
                <!-- 태그 영역 -->
                <div class="tag-container">
                    <div class="tag active" data-type="saved">
                        <div class="tag-text">저장 질문 면접</div>
                    </div>
                    <div class="tag" data-type="random">
                        <div class="tag-text">랜덤 질문 면접</div>
                    </div>
                </div>
                
                <!-- 메인 콘텐츠 -->
                <div class="main-content">
                    <!-- 사용 질문 리스트 섹션 -->
                    <div class="question-list-section">
                        <div class="section-header">
                            <div class="asterisk-icon"></div>
                            <div class="section-title">사용 질문 리스트</div>
                        </div>
                        <!-- dropdown-box를 select 요소로 변경 -->
                        <select class="question-select" id="questionSelect">
                            <option value="" disabled selected>면접 질문 리스트를 선택하세요.</option>
                        </select>
                    </div>
                    
                    <!-- 시작 전 확인사항 섹션 -->
                    <div class="checklist-section">
                        <div class="section-header">
                            <div class="asterisk-icon"></div>
                            <div class="section-title">시작 전 확인사항</div>
                        </div>
                        <div class="checklist-container">
                            <div class="checklist-item">
                                <div class="checklist-text">얼굴 전체가 화면에 들어오게 캠을 조정하세요, 너무 가깝거나 멀다면 얼굴 식별이 힘들 수 있어요!</div>
                                <div class="checkbox-container">
                                    <div class="checkbox">
                                        <div class="checkbox-icon">
                                            <div class="checkbox-check"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checklist-item">
                                <div class="checklist-text">측면을 비추는 방향으로 캠을 두지 마세요, 면접이 진행되는 중에도 얼굴의 측면이 아닌 정면을 잘 비출 수 있도록 캠을 신경써주세요!</div>
                                <div class="checkbox-container">
                                    <div class="checkbox">
                                        <div class="checkbox-icon">
                                            <div class="checkbox-check"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="checklist-item">
                                <div class="checklist-text">질문에 대한 답변이 잘 녹화, 녹음될 수 있도록 유의하세요 잡음이 발생할 수 있는 주변 환경을 피하고 큰 목소리를 유지해 주세요!</div>
                                <div class="checkbox-container">
                                    <div class="checkbox">
                                        <div class="checkbox-icon">
                                            <div class="checkbox-check"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 시작하기 섹션 -->
                    <div class="start-section">
                        <div class="section-header">
                            <div class="asterisk-icon"></div>
                            <div class="section-title">시작하기</div>
                        </div>
                        <button class="start-button" onclick="startMockInterview()" id="startButton">
                            <div class="start-button-text">
                                모의면접 시작하기
                                <div class="loading-spinner" id="loadingSpinner"></div>
                            </div>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script>
//전역 변수
let selectedInterviewType = 'saved'; // 기본값: 저장 질문 면접
let questionList = []; // 서버에서 가져온 질문 리스트

// 서버에서 질문 리스트를 가져오는 함수
function loadCustomQuestionList() {
    const select = document.getElementById('questionSelect');
    
    // 로딩 상태 표시
    select.classList.add('loading');
    select.innerHTML = '<option value="" disabled selected class="loading-text">질문 리스트를 불러오는 중...</option>';
    
    // AJAX 요청
    fetch('/imtintrvw/aiimtintrvw/getCustomQuestionList', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        questionList = data;
        populateQuestionSelect(data, 'custom');
        select.classList.remove('loading');
    })
    .catch(error => {
        console.error('질문 리스트 로딩 오류:', error);
        select.classList.remove('loading');
        select.innerHTML = '<option value="" disabled selected>질문 리스트를 불러올 수 없습니다. 새로고침 후 다시 시도해주세요.</option>';
    });
}

function loadIndustryList() {
    const select = document.getElementById('questionSelect');
    
    // 로딩 상태 표시
    select.classList.add('loading');
    select.innerHTML = '<option value="" disabled selected class="loading-text">업종 리스트를 불러오는 중...</option>';
    
    // AJAX 요청
    fetch('/imtintrvw/aiimtintrvw/getIndustryList', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        questionList = data;
        populateQuestionSelect(data, 'industry');
        select.classList.remove('loading');
    })
    .catch(error => {
        console.error('업종 리스트 로딩 오류:', error);
        select.classList.remove('loading');
        select.innerHTML = '<option value="" disabled selected>업종 리스트를 불러올 수 없습니다. 새로고침 후 다시 시도해주세요.</option>';
    });
}

// Select 옵션 채우기
function populateQuestionSelect(data, type) {
    const select = document.getElementById('questionSelect');
    
    // 기존 옵션 제거 (placeholder 제외)
    const placeholder = type === 'industry' ? '업종을 선택하세요.' : '면접 질문 리스트를 선택하세요.';
    select.innerHTML = `<option value="" disabled selected>${placeholder}</option>`;
    
    // 데이터가 없는 경우
    if (!data || data.length === 0) {
        const noDataText = type === 'industry' ? '등록된 업종이 없습니다.' : '등록된 질문 리스트가 없습니다.';
        select.innerHTML = `<option value="" disabled selected>${noDataText}</option>`;
        return;
    }
    
    // 데이터로 옵션 생성
    data.forEach(item => {
        const option = document.createElement('option');
        
        if (type === 'industry') {
            // 업종 리스트의 경우
            option.value = item.iqGubun;
            option.textContent = item.industryName;
        } else {
            // 커스텀 질문 리스트의 경우
            option.value = item.idlId;
            option.textContent = item.idlTitle;
            
            if (item.questionCount) {
                option.textContent += ` (${item.questionCount}개 질문)`;
            }
        }
        
        select.appendChild(option);
    });
}

// 면접 타입에 따른 질문 리스트 업데이트
function updateQuestionListByType(type) {
    selectedInterviewType = type;
    const select = document.getElementById('questionSelect');
    const sectionTitle = document.querySelector('.question-list-section .section-title');
    
    if (type === 'random') {
        // 랜덤 질문 면접의 경우 업종별 리스트 로드
        select.disabled = false;
        sectionTitle.textContent = '업종 선택';
        loadIndustryList();
    } else {
        // 저장 질문 면접의 경우 질문 리스트 로드
        select.disabled = false;
        sectionTitle.textContent = '사용 질문 리스트';
        loadCustomQuestionList();
    }
    
    // 버튼 상태 업데이트
    updateStartButton();
}

// 모든 체크박스가 체크되었는지 확인하는 함수
function checkAllChecked() {
    const checkboxes = document.querySelectorAll('.checkbox');
    let allChecked = true;
    
    checkboxes.forEach(function(checkbox) {
        if (!checkbox.classList.contains('checked')) {
            allChecked = false;
        }
    });
    
    return allChecked;
}

// 질문 리스트가 선택되었는지 확인하는 함수
function checkQuestionListSelected() {
    const select = document.getElementById('questionSelect');
    return select.value !== '' && select.value !== null && !select.disabled;
}

// 시작 버튼 상태 업데이트 함수
function updateStartButton() {
    const button = document.getElementById('startButton');
    const allChecked = checkAllChecked();
    const questionSelected = checkQuestionListSelected();
    
    if (allChecked && questionSelected) {
        // 모든 조건이 만족됨 - 버튼 활성화
        button.classList.remove('disabled');
        button.disabled = false;
    } else {
        // 조건이 만족되지 않음 - 버튼 비활성화
        button.classList.add('disabled');
        button.disabled = true;
    }
}

//면접 질문 데이터 가져오기 (단순 검증용 - React에서 실제 데이터 로드)
function validateInterviewSettings(selectedValue) {
    return new Promise((resolve, reject) => {
        console.log('=== 면접 설정 검증 시작 ===');
        console.log('selectedValue:', selectedValue);
        console.log('selectedInterviewType:', selectedInterviewType);
        
        const params = new URLSearchParams({
            type: selectedInterviewType
        });
        
        if (selectedInterviewType === 'saved') {
            if (!selectedValue) {
                reject(new Error('질문 리스트를 선택해주세요.'));
                return;
            }
            params.append('questionListId', selectedValue);
        } else {
            if (!selectedValue) {
                reject(new Error('업종을 선택해주세요.'));
                return;
            }
            params.append('industryCode', selectedValue);
            params.append('questionCount', '10');
        }
        
        let url = "/imtintrvw/aiimtintrvw/getInterviewQuestions?" + params.toString();
        console.log("🔍 검증 URL:", url);
        
        // 간단한 검증만 수행 (실제 데이터는 React에서 로드)
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            console.log('📡 검증 응답:', response.status);
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('✅ 검증 성공:', data);
            if (data.success && data.questions && data.questions.length > 0) {
                resolve({
                    type: selectedInterviewType,
                    selectedValue: selectedValue,
                    totalCount: data.totalCount
                });
            } else {
                reject(new Error(data.message || '질문 데이터가 없습니다.'));
            }
        })
        .catch(error => {
            console.error('❌ 검증 실패:', error);
            reject(error);
        });
    });
}

// 모의면접 시작 함수
function startMockInterview() {
    // 버튼이 비활성화 상태면 실행하지 않음
    if (document.getElementById('startButton').classList.contains('disabled')) {
        return;
    }
    
    const select = document.getElementById('questionSelect');
    const selectedValue = select.value;
    
    const button = document.getElementById('startButton');
    const spinner = document.getElementById('loadingSpinner');
    
    // 버튼 로딩 상태로 변경
    button.disabled = true;
    button.style.opacity = '0.7';
    spinner.style.display = 'block';
    
    // 미디어 장치 확인
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        alert('이 브라우저는 카메라/마이크 기능을 지원하지 않습니다. 최신 브라우저를 사용해주세요.');
        resetButton();
        return;
    }
    
    // 카메라/마이크 권한 확인
    navigator.mediaDevices.getUserMedia({ video: true, audio: true })
        .then(function(stream) {
            // 권한 허용됨 - 스트림 정리
            stream.getTracks().forEach(track => track.stop());
            
            // 간단한 설정 검증 후 팝업 열기
            validateInterviewSettings(selectedValue)
                .then(interviewSettings => {
                    console.log('✅ 면접 설정 검증 완료:', interviewSettings);
                    openMockInterviewPopup(interviewSettings);
                })
                .catch(error => {
                    console.error('❌ 면접 설정 검증 실패:', error);
                    alert(error.message || '면접 설정을 확인할 수 없습니다.');
                    resetButton();
                });
        })
        .catch(function(error) {
            console.error('미디어 장치 접근 오류:', error);
            
            let errorMessage = '카메라와 마이크 접근 권한이 필요합니다.\n\n';
            
            if (error.name === 'NotAllowedError') {
                errorMessage += '브라우저에서 카메라/마이크 권한을 허용해주세요.';
            } else if (error.name === 'NotFoundError') {
                errorMessage += '카메라 또는 마이크가 연결되어 있지 않습니다.';
            } else if (error.name === 'NotReadableError') {
                errorMessage += '카메라 또는 마이크가 다른 애플리케이션에서 사용 중입니다.';
            } else {
                errorMessage += '미디어 장치에 접근할 수 없습니다.';
            }
            
            alert(errorMessage);
            resetButton();
        });
}

// 면접 질문 데이터 가져오기
function fetchInterviewQuestions(selectedValue) {
    return new Promise((resolve, reject) => {
        const params = new URLSearchParams({
            type: selectedInterviewType
        });
        
        if (selectedInterviewType === 'saved') {
            params.append('questionListId', selectedValue);
        } else {
            params.append('industryCode', selectedValue);
            params.append('questionCount', '10'); // 기본 10개 질문
        }
        
        let url = "/imtintrvw/aiimtintrvw/getInterviewQuestions?" + params.toString();
        console.log("url : ", url);
        
        fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                resolve(data);
            } else {
                reject(new Error(data.message || '질문 데이터를 가져오는데 실패했습니다.'));
            }
        })
        .catch(error => {
            reject(error);
        });
    });
}

// Mock Interview 팝업 열기 (React로 데이터 전달)
function openMockInterviewPopup(interviewSettings) {
    console.log('=== openMockInterviewPopup (단순화 버전) ===');
    console.log('📦 interviewSettings:', interviewSettings);
    
    try {
        // React 애플리케이션 URL 생성
        let popupUrl = 'http://localhost:5173/mock-interview';
        const params = new URLSearchParams({
            type: interviewSettings.type,
            totalCount: interviewSettings.totalCount
        });
        
        if (interviewSettings.type === 'saved') {
            params.append('questionListId', interviewSettings.selectedValue);
        } else {
            params.append('industryCode', interviewSettings.selectedValue);
            params.append('questionCount', '10');
        }
        
        popupUrl += '?' + params.toString();
        console.log('🚀 팝업 URL:', popupUrl);
        
        // 팝업 창 열기
        const popup = window.open(
            popupUrl,
            'mockInterview',
            'width=1400,height=900,scrollbars=yes,resizable=yes,location=no,menubar=no,toolbar=no'
        );
        
        if (!popup) {
            alert('팝업이 차단되었습니다. 팝업 차단을 해제한 후 다시 시도해주세요.');
            resetButton();
            return;
        }
        
        console.log('✅ 팝업 창 열기 성공');
        popup.focus();
        
        // 팝업 창 모니터링
        const checkClosed = setInterval(function() {
            if (popup.closed) {
                clearInterval(checkClosed);
                console.log('🔄 모의면접 완료');
                resetButton();
            }
        }, 1000);
        
        resetButton();
        
    } catch (error) {
        console.error('❌ 팝업 열기 실패:', error);
        alert('면접을 시작할 수 없습니다. 다시 시도해주세요.');
        resetButton();
    }
}

// 버튼 상태 리셋 함수
function resetButton() {
    const button = document.getElementById('startButton');
    const spinner = document.getElementById('loadingSpinner');
    
    updateStartButton();
    button.style.opacity = '1';
    spinner.style.display = 'none';
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function() {
    // 미디어 장치 지원 여부 확인
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        const button = document.getElementById('startButton');
        button.disabled = true;
        button.querySelector('.start-button-text').innerHTML = '이 브라우저는 모의면접을 지원하지 않습니다';
        button.style.background = '#6b7280';
        button.style.cursor = 'not-allowed';
        return;
    }
    
    // 초기 질문 리스트 로드
    loadCustomQuestionList();
    
    // 초기 버튼 상태 설정 (비활성화)
    updateStartButton();
    
    // 체크박스 클릭 이벤트
    const checkboxes = document.querySelectorAll('.checkbox');
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('click', function() {
            // 체크 상태 토글
            if (this.classList.contains('checked')) {
                // 체크 해제
                this.classList.remove('checked');
            } else {
                // 체크 설정
                this.classList.add('checked');
            }
            
            // 버튼 상태 업데이트
            updateStartButton();
        });
    });
    
    // 태그 클릭 이벤트
    const tags = document.querySelectorAll('.tag');
    tags.forEach(function(tag) {
        tag.addEventListener('click', function() {
            // 모든 태그에서 active 클래스 제거
            tags.forEach(t => t.classList.remove('active'));
            // 클릭한 태그에 active 클래스 추가
            this.classList.add('active');
            
            // 면접 타입 변경
            const type = this.getAttribute('data-type');
            updateQuestionListByType(type);
        });
    });
    
    // Select 변경 이벤트
    document.getElementById('questionSelect').addEventListener('change', function() {
        updateStartButton();
    });
});
</script>

</body>
</html>