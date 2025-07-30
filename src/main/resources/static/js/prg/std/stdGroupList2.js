// stdGroupList2.js
document.addEventListener('DOMContentLoaded', function() {
  // ─── 1) 카드 클릭 이동 & 글쓰기 버튼 ───
  document.querySelectorAll('.group-card').forEach(card => {
    card.addEventListener('click', () => {
      location.href = '/prg/std/stdGroupDetail.do?stdGroupId=' + card.dataset.stdbId;
    });
  });
  const btnWrite = document.getElementById('btnWrite');
  if (btnWrite) {
    btnWrite.addEventListener('click', () => {
      if (!memId || memId === 'anonymousUser') {
        sessionStorage.setItem('redirectUrl', location.href);
        location.href = '/login';
      } else {
        location.href = '/prg/std/createStdGroup.do';
      }
    });
  }

  // ─── 2) 아코디언 토글 ───
  const toggleButton = document.getElementById('com-accordion-toggle');
  const panel = document.getElementById('com-accordion-panel');
  if (toggleButton && panel) {
    toggleButton.addEventListener('click', function() {
      this.classList.toggle('active');
      panel.classList.toggle('open');
      if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = panel.scrollHeight + 'px';
      }
    });
  }

  // ─── 3) 필터 로직 ───
  const selectedContainer = document.querySelector('.com-selected-filters');
  const resetButton     = document.querySelector('.com-filter-reset-btn');
  // 모든 필터 input (radio/checkbox) 모아두기
  const filterInputs = Array.from(document.querySelectorAll('.filter-item input'));
  // 각 그룹 이름(region, gender, interest, maxPeople 등)
  const groupNames = [...new Set(filterInputs.map(i => i.name))];
  /** 선택된 항목으로 태그를 갱신 */

  function updateTags() {
    selectedContainer.innerHTML = '';
    groupNames.forEach(group => {
      // 해당 그룹에서 체크된 input 찾기
      const sel = filterInputs.find(i =>
        i.name === group && i.checked && i.value !== ''
      );
	  console.log(sel);
      if (sel) {
        const txt = sel.nextElementSibling.textContent.trim();
        const tag = document.createElement('span');
		let filterName = "";
		switch(sel.name){
			case "region":
				filterName = "지역";
				break;
			case "gender":
				filterName = "성별";
				break;
			case "maxPeople":
				filterName = "인원제한";
				break;
			case "interest":
				filterName = "관심사";
				break;
		}
        tag.className = 'com-selected-filter';
        tag.dataset.filter = txt;
        tag.innerHTML = `
          ${filterName} » ${txt}
          <button type="button" class="com-remove-filter" aria-label="remove ${txt}">×</button>
        `;
        selectedContainer.appendChild(tag);
      }
    });
  }

  /** 필터 input 변경 시: 같은 그룹 다른 input 해제 + 태그 갱신 */
  filterInputs.forEach(input => {
    input.addEventListener('change', function() {
      if (this.checked) {
        // 같은 그룹의 나머지는 해제
        filterInputs.forEach(other => {
          if (other !== this && other.name === this.name) {
            other.checked = false;
          }
        });
      }
      updateTags();
    });
  });

  /** 태그 내 × 클릭 시: 해당 input 해제(또는 'all' 체크) + 태그 갱신 */
  selectedContainer.addEventListener('click', function(e) {
    if (e.target.classList.contains('com-remove-filter')) {
      const tag = e.target.closest('.com-selected-filter');
      const txt = tag.dataset.filter;
      // 같은 텍스트를 가진 input 찾기
      filterInputs.forEach(input => {
        if (input.nextElementSibling.textContent.trim() === txt) {
          input.checked = false;
        }
        // 그룹에 'all' 또는 기본값(input.value=='' or 'all') 이 있으면 체크
        if (input.name === tag.dataset.filter && (input.value === '' || input.value === 'all')) {
          input.checked = true;
        }
      });
      updateTags();
    }
  });

  /** 초기화 버튼 클릭 시: 모든 그룹 기본값 체크 + 태그 클리어 */
  if (resetButton) {
    resetButton.addEventListener('click', () => {
      filterInputs.forEach(input => {
        if (input.value === '') {
          input.checked = true;
        } else {
          input.checked = false;
        }
      });
      updateTags();
    });
  }

  // 초기 로드 시 태그 세팅
  updateTags();
});