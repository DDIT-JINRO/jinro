<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/admin/umg/memberManagement.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<h2 style="color: gray; font-size: 18px; margin: 0; line-height: 75px;">회원 관리</h2>

<div class="main-container" style="display: flex !important; flex-direction: row !important; gap: 20px; width: 100%;">
    <!-- 왼쪽 컬럼 -->
    <div class="left-column" style="flex: 1; display: flex; flex-direction: column; gap: 20px; width: 50%;">
        <!-- 상단 카드 2개 -->
        <div class="cards-row" style="display: flex; flex-direction: row; gap: 20px; height: 120px;">
            <div class="template-panel public-countCard">
                <div class="public-card-title">총 구독자 수</div>
                <img class="public-card-icon" alt="" src="/images/admin/admin-image1.png">
                <div class="public-card-count" id="totalSubscribersCount">0</div>
                <div class="public-span-space">
                    <span id="totalSubscribersRate" class="public-span-increase">+0%</span>
                    <div class="public-span-since">Since last month</div>
                </div>
            </div>
            
            <div class="template-panel public-countCard back-color-green">
                <div class="public-card-title color-white">오늘 구독자 수</div>
                <img class="public-card-icon" alt="" src="/images/admin/admin-image4.png">
                <div class="public-card-count color-white" id="todaySubscribersCount">0</div>
                <div class="public-span-space">
                    <span id="todaySubscribersRate" class="public-span-increase color-white">+0%</span>
                    <div class="public-span-since color-white">Since yesterday</div>
                </div>
            </div>
        </div>
        
        <!-- 첫 번째 큰 div - 구독 결제 매출 -->
        <div class="template-panel large-panel">
            <div class="middleTitle">구독 결제 매출</div>
            <div class="btn-group flex gap5 revenueChart">
                <button class="public-toggle-button active" id="revenueChartDayBtn">일별</button>
                <button class="public-toggle-button" id="revenueChartMonthBtn">월별</button>
                <button class="public-toggle-button" id="revenueChartMaleBtn">남성</button>
                <button class="public-toggle-button" id="revenueChartFemaleBtn">여성</button>
                <button class="public-toggle-button" id="revenueChartCalender">
                    <img alt="" src="/images/admin/admin-calender.png">
                </button>
            </div>
            <div class="chart-container">
                <canvas id="revenueChartCanvas"></canvas>
            </div>
        </div>
        
        <!-- 두 번째 큰 div - 상품별 인기 통계 -->
        <div class="template-panel large-panel">
            <div class="middleTitle">상품별 인기 통계</div>
            <div class="btn-group flex gap5 productChart">
                <button class="public-toggle-button active" id="productChart6MonthBtn">6개월</button>
                <button class="public-toggle-button" id="productChart1YearBtn">1년</button>
                <button class="public-toggle-button" id="productChartMaleBtn">남성</button>
                <button class="public-toggle-button" id="productChartFemaleBtn">여성</button>
                <button class="public-toggle-button" id="productChartCalender">
                    <img alt="" src="/images/admin/admin-calender.png">
                </button>
            </div>
            <div class="chart-container">
                <canvas id="productChartCanvas"></canvas>
            </div>
        </div>
    </div>
    
    <!-- 오른쪽 컬럼 -->
    <div class="right-column" style="flex: 1; display: flex; flex-direction: column; gap: 20px; width: 50%;">
        <!-- 첫 번째 큰 div - 구독자 수 통계 -->
        <div class="template-panel large-panel">
            <div class="middleTitle">구독자 수 통계</div>
            <div class="btn-group flex gap5 subscriberChart">
                <button class="public-toggle-button active" id="subscriberChartDayBtn">일별</button>
                <button class="public-toggle-button" id="subscriberChartMonthBtn">월별</button>
                <button class="public-toggle-button" id="subscriberChartMaleBtn">남성</button>
                <button class="public-toggle-button" id="subscriberChartFemaleBtn">여성</button>
                <button class="public-toggle-button" id="subscriberChartCalender">
                    <img alt="" src="/images/admin/admin-calender.png">
                </button>
            </div>
            <div class="chart-container">
                <canvas id="subscriberChartCanvas"></canvas>
            </div>
        </div>
        
        <!-- 두 번째 큰 div - 유료 컨텐츠 이용내역 -->
        <div class="template-panel large-panel">
            <div class="middleTitle">유료 컨텐츠 이용내역</div>
            <div class="btn-group flex gap5 aiServiceChart">
                <button class="public-toggle-button active" id="aiServiceChartWeekBtn">주간</button>
                <button class="public-toggle-button" id="aiServiceChartMonthBtn">월간</button>
                <button class="public-toggle-button" id="aiServiceChartActiveBtn">활성</button>
                <button class="public-toggle-button" id="aiServiceChartInactiveBtn">비활성</button>
                <button class="public-toggle-button" id="aiServiceChartCalender">
                    <img alt="" src="/images/admin/admin-calender.png">
                </button>
            </div>
            <div class="chart-container">
                <canvas id="aiServiceChartCanvas"></canvas>
            </div>
        </div>
    </div>
</div>
<script src="/js/include/admin/las/paymentStatistics.js"></script>
