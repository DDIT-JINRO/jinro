/**
 *
 */
document.addEventListener('DOMContentLoaded', function(){
	const grid = document.getElementById('aiCardGrid');
	const btnStart = document.getElementById('btnStart');
	let selectedType = null;

	// 카드 선택 처리 (키보드 접근 포함)
	grid.addEventListener('click', (e) => {
	  const card = e.target.closest('.ai-card');
	  if (!card) return;
	  selectCard(card);
	});
	grid.addEventListener('keydown', (e) => {
	  if (e.key === 'Enter' || e.key === ' ') {
	    const card = e.target.closest('.ai-card');
	    if (!card) return;
	    e.preventDefault();
	    selectCard(card);
	  }
	});

	function selectCard(card) {
	  grid.querySelectorAll('.ai-card').forEach(el => {
	    el.classList.remove('selected');
	    el.setAttribute('aria-checked', 'false');
	  });
	  card.classList.add('selected');
	  card.setAttribute('aria-checked', 'true');
	  selectedType = card.dataset.type; // JOB | STUDY | MIND
	  btnStart.disabled = false;
	}

	// 모달 열기/닫기
	const bg = document.getElementById('modalBg');
	const modal = document.getElementById('confirmModal');
	const kvType = document.getElementById('kvType');
	const btnCancel = document.getElementById('btnCancel');
	const btnConfirm = document.getElementById('btnConfirm');

	btnStart.addEventListener('click', () => {
		if(memId == null || 'anonymousUser' == memId){
			alert('로그인이 필요한 서비스입니다');
			sessionStorage.setItem('redirectUrl', location.href);
			location.href = '/login';
			return;
		}

		if (!selectedType) return;
		switch(selectedType){
			case 'JOB' :
				kvType.textContent = '취업';
				break;
			case 'STUDY':
				kvType.textContent = '학업';
				break;
			case 'MIND':
				kvType.textContent = '심리';
				break;
			default: return;
		}
		bg.style.display = 'block';
		modal.style.display = 'block';
		modal.focus();
	});

	function closeModal() {
	  modal.style.display = 'none';
	  bg.style.display = 'none';
	}
	btnCancel.addEventListener('click', closeModal);
	bg.addEventListener('click', closeModal);
	window.addEventListener('keydown', (e) => { if (e.key === 'Escape' && modal.style.display === 'block') closeModal(); });

	// 팝업 열기 (이용권 차감 + 세션 생성 → 팝업 URL 반환 가정)
	btnConfirm.addEventListener('click', async () => {
	  if (!selectedType) return;

	  try {
	    // ★ 서버 엔드포인트에 맞춰 변경하세요.
	    // 기대 응답 예: { ok:true, sessionId:"...", popupUrl:"/counsel/ai/chat?sid=..."}
	    const res = await axios.post('/cnslt/aicns/aicnsPopUpStart', { topic: selectedType });

	    if (!res.data || !res.data.ok) {
	      alert((res.data && res.data.message) ? res.data.message : '시작에 실패했습니다.');
	      return;
	    }

	    closeModal();

	    // 팝업 중앙 정렬
	    const w = 960, h = 720;
	    const y = window.top.outerHeight / 2 + window.top.screenY - (h/2);
	    const x = window.top.outerWidth  / 2 + window.top.screenX - (w/2);

	    const popup = window.open(res.data.popupUrl || `/counsel/ai/chat?sid=${res.data.sessionId}`,
	      'aiCounselWindow',
	      `popup=yes, width=${w}, height=${h}, top=${y}, left=${x},
	       resizable=yes, scrollbars=yes, menubar=no, toolbar=no, location=no, status=no`);

	    if (!popup || popup.closed) {
	      alert('팝업이 차단되었습니다. 브라우저의 팝업 차단을 해제해 주세요.');
	      return;
	    }

	    // 팝업이 닫히면 필요한 후처리(예: 세션 정리) 호출 가능
	    const closeWatcher = setInterval(() => {
	      if (popup.closed) {
	        clearInterval(closeWatcher);
	        // 선택: 세션 정리 API
	        // axios.post('/counsel/ai/session/close', { sessionId: res.data.sessionId }).catch(()=>{});
	      }
	    }, 800);

	  } catch (err) {
	    console.error(err);
	    alert('서버 통신 중 문제가 발생했습니다.');
	  }
	});
})