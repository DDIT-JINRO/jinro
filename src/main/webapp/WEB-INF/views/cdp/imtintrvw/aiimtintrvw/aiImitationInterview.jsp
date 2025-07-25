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

<script src="/js/cdp/imtintrvw/aiimtintrvw/aimitationInterview.js"></script>

</body>
</html>