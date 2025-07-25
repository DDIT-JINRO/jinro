<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="/css/cdp/imtintrvw/aiimtintrvw/aimitationInterview.css">

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