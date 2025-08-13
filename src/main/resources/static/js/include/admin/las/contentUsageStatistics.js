/* contentUsageStatistics.js — accessUsageStatistics.js 스타일 재작성 (덮어쓰기용) */

/* ========================= 공통 유틸 ========================= */
( function(){

function toArray(data) {
  // 서버가 [ ... ] 대신 { list:[...]} / {rows:[...]} / {...} 등으로 줄 때 대비
  if (Array.isArray(data)) return data;
  if (data && Array.isArray(data.list)) return data.list;
  if (data && Array.isArray(data.rows)) return data.rows;
  if (data && Array.isArray(data.items)) return data.items;
  return []; // 배열 아니면 안전하게 빈배열
}

function getEl(id) { return document.getElementById(id); }

function destroyIfExists(canvasId) {
  const el = getEl(canvasId);
  if (!el) return;
  const inst = Chart.getChart(el);
  if (inst) inst.destroy();
}

function getFilters() {
  // 기간 라벨에서 from/to 읽기 (flatpickr에서 두 날짜 선택되면 라벨에 들어감)
  const label = getEl('cusDateLabel');
  const range = (label && label.dataset.range) ? label.dataset.range : '';
  const [from, to] = range.split('~').map(s => s && s.trim());

  return {
    from: from || '',
    to: to || '',
    gender:  (getEl('cusGender')  && getEl('cusGender').value)  || 'ALL',
    ageBand: (getEl('cusAgeBand') && getEl('cusAgeBand').value) || 'ALL',
    ccId:    (getEl('cusCcId')    && getEl('cusCcId').value)    || 'ALL',
  };
}

function apiGet(url, paramsObj) {
  // access 스타일: axios.get(url, { params })
  return axios.get(url, { params: paramsObj }).then(res => res.data).catch(err => {
    console.error('API 실패:', url, err);
    return []; // 실패 시에도 안전하게 배열 반환
  });
}

/* ========================= API 엔드포인트 ========================= */
const API = {
  bmStacked:   '/admin/las/cont/bookmark/category-stacked',  // categoryId,categoryName,maleCnt,femaleCnt
  bmTopN:      '/admin/las/cont/bookmark/top',               // categoryId,categoryName,targetId,targetName,cnt
  commPosts:   '/admin/las/cont/community/postDaily',        // dt,ccId,ccName,postCnt,uniqueAuthors
  commReact:   '/admin/las/cont/community/reactionDaily',    // dt,ccId,ccName,type,cnt
  wrSummary:   '/admin/las/cont/worldcup-roadmap/summary',
  wrTrend:     '/admin/las/cont/worldcup-roadmap/daily',    // dt,feature,cnt
  commTopMembers: '/admin/las/cont/community/top-members',   // nickname,postCnt,replyCnt,likeCnt,score
  commTopPosts:   '/admin/las/cont/community/top-posts',     // title,ccName,replyCnt,likeCnt,engageCnt
  worldcupTopJobs:'/admin/las/cont/worldcup/top-jobs',        // jobName,jobCode,cnt
  roadmapCreateCompleteSummary: '/admin/las/cont/roadmap/create-complete/summary',
  roadmapCreateCompleteDaily:   '/admin/las/cont/roadmap/create-complete/daily'
};

/* ========================= 차트/뷰 렌더 ========================= */

// 좌상단: 북마크 카테고리별 (남/여 스택, 또는 단일 성별)
function drawBookmarkCategoryStacked() {
	const from 	= document.getElementById('fromBMC').value;
	const to 	= document.getElementById('toBMC').value;
	const ageBand 	= document.getElementById('cusAgeBand').value;
	const gender = document.querySelector('#bmGenderGroup .btn-gender.active').dataset.gender;
	const filters = {
		from : from ? from : '',
		to	 : to ? to : '',
		ageBand : ageBand,
		gender : gender? gender : 'ALL'
	};
  return apiGet(API.bmStacked, filters).then(data => {
    const rows = toArray(data);
    const labels = rows.map(r => r.categoryName);
    const male   = rows.map(r => r.maleCnt || 0);
    const female = rows.map(r => r.femaleCnt || 0);

    destroyIfExists('bmCategoryChart');
    const ctx = getEl('bmCategoryChart').getContext('2d');

    const datasets = [];
    if (filters.gender === 'ALL') {
      datasets.push({ label:'남', data: male,   backgroundColor:'#01AEBC' });
      datasets.push({ label:'여', data: female, backgroundColor:'#FF6B8A' });
    } else if (filters.gender === 'G11001') {
      datasets.push({ label:'남', data: male,   backgroundColor:'#01AEBC' });
    } else {
      datasets.push({ label:'여', data: female, backgroundColor:'#FF6B8A' });
    }

    new Chart(ctx, {
      type: 'bar',
      data: { labels, datasets },
      options: {
        indexAxis:'y',
        responsive:true,
        plugins:{ legend:{ display:true }},
        scales:{ x:{ stacked:true, beginAtZero:true }, y:{ stacked:true } }
      }
    });
  });
}

// 좌하단: 북마크 상세 TOP N
function drawBookmarkTopN() {
  	const category = document.getElementById('bmTopCategory').value;
  	const limit = document.getElementById('bmTopLimit').value;
	const gender = document.querySelector('#bmkGroup .btn-gender.active').dataset.gender;
	const from 	= document.getElementById('fromBMT').value;
	const to 	= document.getElementById('toBMT').value;
	const ageBand = document.getElementById('bmtAgeBand').value;

	const filters = {
		limit : limit,
		categoryId : category,
		gender:gender,
		from : from ? from : '',
		to : to ? to : '',
		ageBand : ageBand ? ageBand : '',
	};

	console.log(filters);

  return apiGet(API.bmTopN, filters).then(data => {
	console.log("bmTOP ", data);
    const rows = toArray(data);
    const labels = rows.map(r => `(${r.CATEGORYNAME})${r.TARGETNAME}`);
    const values = rows.map(r => `${r.CNT || 0}`);

    destroyIfExists('bmTopChart');
    const ctx = getEl('bmTopChart').getContext('2d');
    let myChart = new Chart(ctx, {
      type:'bar',
      data:{ labels, datasets:[{ label:'북마크 수', data: values, backgroundColor:'#07223D' }]},
      options:{
		 indexAxis:'y',
		 responsive:true,
		 plugins:{ legend:{ display:false }},
		 scales:{ x:{ beginAtZero:true }},
		 onClick : function(event, elements) {
			if (elements.length > 0) {
				const idx =  elements[0].index;
				const label = myChart.data.labels[idx];
				const dataTable = document.getElementById('bmTopTable');
				const hiddenData =dataTable.querySelector(`tr td[data-label="${label}"`);
				openURL(hiddenData.textContent.trim());
            }
		 }
	 }
    });

    const tbody = getEl('bmTopTable');
    if (tbody) {
      tbody.innerHTML = rows.map(r => `<tr><td data-label='(${r.CATEGORYNAME})${r.TARGETNAME}'>${r.TARGET_URL}</td><td>${r.CNT || 0}</td></tr>`).join('');
    }
  });
}

// 우상단: 커뮤니티 작성 추이
function drawCommunityPostDaily() {
  const filters = getFilters();
  // 선택된 게시판
  filters.ccId = (getEl('cusCcId') && getEl('cusCcId').value) || 'ALL';

  return apiGet(API.commPosts, filters).then(data => {
    const rows = toArray(data);
    const grouped = {}; // dt -> {postCnt, uniqueAuthors}
    rows.forEach(r => {
      const d = (r.dt && String(r.dt).slice(0,10)) || r.dt || '';
      if (!grouped[d]) grouped[d] = { postCnt:0, uniqueAuthors:0 };
      grouped[d].postCnt += (r.postCnt || 0);
      grouped[d].uniqueAuthors += (r.uniqueAuthors || 0);
    });

    const labels = Object.keys(grouped).sort();
    const post = labels.map(d => grouped[d].postCnt);
    const uniq = labels.map(d => grouped[d].uniqueAuthors);

    destroyIfExists('communityPostChart');
	getEl('communityPostWrap').style.display = 'block';
    getEl('communityReactWrap').style.display = 'none';
    getEl('communityTopMembersWrap').style.display = 'none';
    getEl('communityTopPostsWrap').style.display = 'none';

    const ctx = getEl('communityPostChart').getContext('2d');
    new Chart(ctx, {
      type:'line',
      data:{ labels, datasets:[
        { label:'게시글 수',   data: post, borderColor:'#07223D', backgroundColor:'rgba(7,34,61,.12)', tension:.35, fill:true },
        { label:'고유 작성자', data: uniq, borderColor:'#01AEBC', backgroundColor:'rgba(1,174,188,.15)', tension:.35, fill:true }
      ]},
      options:{ responsive:true, plugins:{ legend:{ position:'top' }}, scales:{ y:{ beginAtZero:true, ticks:{ precision:0 }}} }
    });
  });
}

// 우상단: 커뮤니티 반응 추이
function drawCommunityReactionDaily() {
  const filters = getFilters();
  filters.ccId = (getEl('cusCcId') && getEl('cusCcId').value) || 'ALL';

  return apiGet(API.commReact, filters).then(data => {
    const rows = toArray(data);
    const byType = { REPLY:{}, LIKE:{} }; // dt -> cnt

    rows.forEach(r => {
      const t = r.type || 'REPLY';
      const d = (r.dt && String(r.dt).slice(0,10)) || r.dt || '';
      if (!byType[t][d]) byType[t][d] = 0;
      byType[t][d] += (r.cnt || 0);
    });

    const labels = Array.from(new Set([...Object.keys(byType.REPLY), ...Object.keys(byType.LIKE)])).sort();
    const reply = labels.map(d => byType.REPLY[d] || 0);
    const like  = labels.map(d => byType.LIKE[d]  || 0);

    destroyIfExists('communityReactChart');
	getEl('communityPostWrap').style.display = 'none';
	getEl('communityReactWrap').style.display = 'block';
	getEl('communityTopMembersWrap').style.display = 'none';
	getEl('communityTopPostsWrap').style.display = 'none';

    const ctx = getEl('communityReactChart').getContext('2d');
    new Chart(ctx, {
      type:'line',
      data:{ labels, datasets:[
        { label:'댓글',  data: reply, borderColor:'#01AEBC', backgroundColor:'rgba(1,174,188,.15)', tension:.35, fill:true },
        { label:'좋아요', data: like,  borderColor:'#FFC107', backgroundColor:'rgba(255,193,7,.18)', tension:.35, fill:true }
      ]},
      options:{ responsive:true, plugins:{ legend:{ position:'top' }}, scales:{ y:{ beginAtZero:true, ticks:{ precision:0 }}} }
    });
  });
}

// 우상단: 활동 회원 TOP
function drawCommunityTopMembers() {
  const filters = getFilters();
  filters.ccId = (getEl('cusCcId') && getEl('cusCcId').value) || 'ALL';
  filters.limit = parseInt((getEl('topMembersLimit') && getEl('topMembersLimit').value) || '5', 10);

  return apiGet(API.commTopMembers, filters).then(data => {
    const rows = toArray(data);

    getEl('communityPostWrap').style.display = 'none';
    getEl('communityReactWrap').style.display = 'none';
    getEl('communityTopMembersWrap').style.display = 'block';
    getEl('communityTopPostsWrap').style.display = 'none';

    const tb = getEl('communityTopMembersBody');
    tb.innerHTML = rows.map(r =>
      `<tr><td>${r.nickname || '-'}</td><td>${r.postCnt || 0}</td><td>${r.replyCnt || 0}</td><td>${r.likeCnt || 0}</td><td>${r.score || 0}</td></tr>`
    ).join('');
  });
}

// 우상단: 반응 글 TOP
function drawCommunityTopPosts() {
  const filters = getFilters();
  filters.ccId = (getEl('cusCcId') && getEl('cusCcId').value) || 'ALL';
  filters.limit = parseInt((getEl('topPostsLimit') && getEl('topPostsLimit').value) || '5', 10);

  return apiGet(API.commTopPosts, filters).then(data => {
    const rows = toArray(data);

    getEl('communityPostWrap').style.display = 'none';
    getEl('communityReactWrap').style.display = 'none';
    getEl('communityTopMembersWrap').style.display = 'none';
    getEl('communityTopPostsWrap').style.display = 'block';

    const tb = getEl('communityTopPostsBody');
    tb.innerHTML = rows.map(r =>
      `<tr><td>${r.title || '-'}</td><td>${r.ccName || '-'}</td><td>${r.replyCnt || 0}</td><td>${r.likeCnt || 0}</td><td>${r.engageCnt || 0}</td></tr>`
    ).join('');
  });
}

// 우하단: 요약 도넛 (BOOKMARK vs WORLDCUP)
function drawWRSummary() {
  const filters = getFilters();
  return apiGet(API.wrSummary, filters).then(data => {
    const rows = toArray(data);
    const labels = rows.map(r => (r.feature === 'WORLDCUP' ? '월드컵' : '로드맵'));
    const values = rows.map(r => r.cnt || 0);

    destroyIfExists('bwSummaryChart');
    const ctx = getEl('bwSummaryChart').getContext('2d');
    new Chart(ctx, {
      type:'doughnut',
      data:{ labels, datasets:[{ data: values, backgroundColor:['#01AEBC', '#07223D'] }]}, // 월드컵=teal, 로드맵=navy
      options:{ responsive:true, plugins:{ legend:{ position:'bottom' }}}
    });
  });
}

// 우하단: 일자별 추이
function drawWRTrend() {
  const filters = getFilters();
  return apiGet(API.wrTrend, filters).then(data => {
    const rows = toArray(data);
    const byFeat = { WORLDCUP:{}, ROADMAP:{} };

    rows.forEach(r => {
      const feat = (r.feature === 'ROADMAP' ? 'ROADMAP' : 'WORLDCUP');
      const d = (r.dt && String(r.dt).slice(0,10)) || r.dt || '';
      if (!byFeat[feat][d]) byFeat[feat][d] = 0;
      byFeat[feat][d] += (r.cnt || 0);
    });

    const labels = Array.from(new Set([...Object.keys(byFeat.WORLDCUP), ...Object.keys(byFeat.ROADMAP)])).sort();
    const wc = labels.map(d => byFeat.WORLDCUP[d] || 0);
    const rm = labels.map(d => byFeat.ROADMAP[d]  || 0);

    destroyIfExists('bwTrendChart');
    const ctx = getEl('bwTrendChart').getContext('2d');
    new Chart(ctx, {
      type:'line',
      data:{ labels, datasets:[
        { label:'월드컵', data: wc, borderColor:'#01AEBC', backgroundColor:'rgba(1,174,188,.15)', tension:.35, fill:true },
        { label:'로드맵', data: rm, borderColor:'#07223D', backgroundColor:'rgba(7,34,61,.12)',  tension:.35, fill:true }
      ]},
      options:{ responsive:true, plugins:{ legend:{ position:'top' }}, scales:{ y:{ beginAtZero:true, ticks:{ precision:0 }}} }
    });
  });
}

// 우하단: 월드컵 인기 직업 TOP
function drawWorldcupTop() {
  const filters = getFilters();
  filters.limit = parseInt((getEl('worldcupTopLimit') && getEl('worldcupTopLimit').value) || '5', 10);

  return apiGet(API.worldcupTopJobs, filters).then(data => {
    const rows = toArray(data);
    const labels = rows.map(r => r.jobName || r.jobCode);
    const values = rows.map(r => r.cnt || 0);

    destroyIfExists('worldcupTopChart');
    getEl('worldcupTopWrap').style.display = 'block';

    const ctx = getEl('worldcupTopChart').getContext('2d');
    new Chart(ctx, {
      type:'bar',
      data:{ labels, datasets:[{ label:'참여 수', data: values, backgroundColor:'#01AEBC' }]},
      options:{ indexAxis:'y', responsive:true, plugins:{ legend:{ display:false }}, scales:{ x:{ beginAtZero:true }} }
    });
  });
}

/* ========================= 초기 바인딩/초기화 ========================= */

function openURL(url){
	const aEl =document.createElement('a');
	aEl.href = url;
	aEl.target = "_blank";
	document.body.appendChild(aEl);
	aEl.click();
	aEl.remove();
}

function refreshAll() {
  // 순서 무관; 각 함수 내부에서 기존 차트 destroy 처리
  drawBookmarkCategoryStacked();
  drawBookmarkTopN();

  // 커뮤니티: 현재 활성 탭 감지
  const activeTab = document.querySelector('.btn-tab.active')?.dataset.tab || 'post';
  if (activeTab === 'react')      drawCommunityReactionDaily();
  else if (activeTab === 'topMembers') drawCommunityTopMembers();
  else if (activeTab === 'topPosts')   drawCommunityTopPosts();
  else drawCommunityPostDaily();

  drawWRSummary();
  drawWRTrend();
}

function bindEvents() {
  // 중복 바인딩 방지 (사이드바가 같은 스크립트 여러 번 append할 수 있어서)
  if (window.__CUS_BOUND__) return;
  window.__CUS_BOUND__ = true;

  // 기간/성별·연령 각각 적용
  getEl('cusDateApply')?.addEventListener('click', refreshAll);
  getEl('cusDemoApply')?.addEventListener('click', refreshAll);

  // 성별 토글(해당 패널 내부 버튼만)
  document.addEventListener('click', (e) => {
    const bmcbtn = e.target.closest('#bmGenderGroup .btn-gender');
	const bmkbtn = e.target.closest('#bmkGroup .btn-gender');
    if (bmcbtn) {
	    document.querySelectorAll('#bmGenderGroup .btn-gender').forEach(b => b.classList.remove('active'));
	    bmcbtn.classList.add('active');
		const __bmGender = bmcbtn.dataset.gender;
	    drawBookmarkCategoryStacked(__bmGender);
	}

	if(bmkbtn){
		document.querySelectorAll('#bmkGroup .btn-gender').forEach(b => b.classList.remove('active'));
	    bmkbtn.classList.add('active');
		// 함수 호출
		drawBookmarkTopN();
	}


  });



  // 연력 셀렉트 이벤트
  document.getElementById('cusAgeBand').addEventListener('change', drawBookmarkCategoryStacked);
  document.getElementById('bmtAgeBand').addEventListener('change', drawBookmarkTopN);

  // 커뮤니티 탭 전환
  document.addEventListener('click', (e) => {
    const t = e.target.closest('.btn-tab');
    if (!t) return;
    document.querySelectorAll('.btn-tab').forEach(b => b.classList.remove('active'));
    t.classList.add('active');
    const tab = t.dataset.tab;
    if (tab === 'react')      drawCommunityReactionDaily();
    else if (tab === 'topMembers') drawCommunityTopMembers();
    else if (tab === 'topPosts')   drawCommunityTopPosts();
    else drawCommunityPostDaily();
  });

  // 좌하단: 카테고리/limit 변경 즉시 반영
  getEl('bmTopCategory')?.addEventListener('change', drawBookmarkTopN);
  getEl('bmTopLimit')?.addEventListener('change', drawBookmarkTopN);

  // 우상단 랭킹 limit 변경
  getEl('topMembersLimit')?.addEventListener('change', drawCommunityTopMembers);
  getEl('topPostsLimit')?.addEventListener('change', drawCommunityTopPosts);

  // 우상단 게시판 선택 즉시 반영
  getEl('cusCcId')?.addEventListener('change', () => {
    const tab = document.querySelector('.btn-tab.active')?.dataset.tab || 'post';
    if (tab === 'react')      drawCommunityReactionDaily();
    else if (tab === 'topMembers') drawCommunityTopMembers();
    else if (tab === 'topPosts')   drawCommunityTopPosts();
    else drawCommunityPostDaily();
  });

  // 우하단 월드컵 인기 직업
  getEl('btnWorldcupTop')?.addEventListener('click', drawWorldcupTop);
  getEl('worldcupTopLimit')?.addEventListener('change', drawWorldcupTop);
}

// 초기화 (access 스타일과 동일하게 마지막에 1회 호출)
function initContentUsageStatistics() {
  // 이 페이지에만 존재하는 루트가 없으면 스킵
  if (!document.getElementById('cus-root')) return;
  bindEvents();
  refreshAll();
}

//========달력 동작==========
// 북마크 카테고리
const calBtnBMC = document.getElementById('calBtnBMC');
calBtnBMC.addEventListener('click', function(){
	flatpickr(calBtnBMC, {
			mode: "range",
			maxDate: "today",
			disable: [date => date > new Date()],
			onChange: function(selectedDates) {
				if (selectedDates.length === 2) {
					const values = calBtnBMC.value.split(' to ');
					document.getElementById('fromBMC').value = values[0].trim();
					document.getElementById('toBMC').value = values[1].trim();

					const gender = document.querySelector('#bmGenderGroup .btn-gender.active').dataset.gender;
					drawBookmarkCategoryStacked(gender);
				}
			}
		});

	calBtnBMC._flatpickr.open();
	calBtnBMC._flatpickr.clear();
})
const calBtnBMT = document.getElementById('calBtnBMT');
calBtnBMT.addEventListener('click', function(){
	flatpickr(calBtnBMT, {
			mode: "range",
			maxDate: "today",
			disable: [date => date > new Date()],
			onChange: function(selectedDates) {
				if (selectedDates.length === 2) {
					const values = calBtnBMT.value.split(' to ');
					document.getElementById('fromBMT').value = values[0].trim();
					document.getElementById('toBMT').value = values[1].trim();
					drawBookmarkTopN();
				}
			}
		});

	calBtnBMT._flatpickr.open();
	calBtnBMT._flatpickr.clear();
})
//=========================


initContentUsageStatistics();

})();