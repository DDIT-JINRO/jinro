/**
 *
 */
document.addEventListener('DOMContentLoaded',function(){
	/* ========= State ========= */
	const chat   = document.getElementById('chat');
	const ta     = document.getElementById('ta');
	const send   = document.getElementById('btnSend');
	const errorB = document.getElementById('errorBar');
	const newBtn = document.getElementById('btnNewMsg');
	const topicBadge = document.getElementById('topicBadge');

	const sid = new URLSearchParams(location.search).get('sid');
	if(!sid){ alert('세션 정보가 없습니다. 창을 닫습니다.'); window.close(); }

	let pending = false;
	let autoScroll = true;

	/* ========= Helpers ========= */
	const nowStr = () => {
		const d = new Date();
		const p = n => String(n).padStart(2, '0');
		return `${String(d.getFullYear()).slice(-2)}.${p(d.getMonth() + 1)}.${p(d.getDate())} ${p(d.getHours())}:${p(d.getMinutes())}`;
	};

	const roleIcon = role => role === 'mine' ? '👤' : '🤖';

	function escapeHtml(s) {
		return s.replace(/[&<>"']/g, ch => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#039;' }[ch]));
	}

	function row(role, html, ts) {
		const wrap = document.createElement('div');
		wrap.className = `row ${role}`;
		wrap.innerHTML = `
	    ${role === 'mine' ? '' : `<div class="avatar" aria-hidden="true">${roleIcon(role)}</div>`}
	    <div>
	      <div class="bubble">${html}</div>
	      <div class="meta">
	        <span>${ts || nowStr()}</span>
	        <div class="tools">
	          <button class="tool" title="복사" onclick="navigator.clipboard.writeText(this.closest('.row').querySelector('.bubble').innerText)">복사</button>
	        </div>
	      </div>
	    </div>
	    ${role === 'mine' ? `<div class="avatar" aria-hidden="true">${roleIcon(role)}</div>` : ''}
	  `;
		return wrap;
	}

	function addMine(text) {
		chat.appendChild(row('mine', escapeHtml(text)));
		requestIdleCallback(scrollMaybe, { timeout: 0 });
	}
	function addAI(text) {
		chat.appendChild(row('ai', escapeHtml(text)));
		requestIdleCallback(scrollMaybe, { timeout: 0 });
	}

	function scrollMaybe() {
		if (autoScroll) {
			chat.scrollTop = chat.scrollHeight;
			newBtn.style.display = 'none';
		}
	}

	function addTyping() {
		const w = document.createElement('div');
		w.className = 'row ai';
		w.dataset.typing = '1';
		w.innerHTML = `
	    <div class="avatar" aria-hidden="true">🤖</div>
	    <div>
	      <div class="bubble"><span class="typing"><span class="dot"></span><span class="dot"></span><span class="dot"></span></span></div>
	    </div>
	  `;
		chat.appendChild(w);
		scrollMaybe();
		return w;
	}

	function removeTyping() {
		const t = chat.querySelector('[data-typing="1"]');
		if (t) t.remove();
	}

	/* ========= Composer ========= */
	ta.addEventListener('input', () => {
		ta.style.height = 'auto';
		ta.style.height = Math.min(ta.scrollHeight, 160) + 'px';
		send.disabled = !ta.value.trim() || pending;
	});

	ta.addEventListener('keydown', (e) => {
		if (e.key === 'Enter' && !e.shiftKey) {
			e.preventDefault();
			doSend();
		}
	});

	send.addEventListener('click', doSend);

	async function doSend() {
		const q = ta.value.trim();
		if (!q || pending) return;
		pending = true;
		send.disabled = true;

		addMine(q);
		ta.value = ''; ta.dispatchEvent(new Event('input'));

		try {
			const { data } = await axios.post('/ai/chatbot', { sid, message: q, topic:topicBadge.dataset.topic });
			removeTyping();
			if (data) {
				addAI(data);
			} else {
				showError((data && data.message) || '답변 생성에 실패했습니다.');
			}
		} catch (err) {
			console.error(err);
			removeTyping();
			showError('서버 통신 중 문제가 발생했습니다.');
		} finally {
			pending = false;
			send.disabled = !ta.value.trim();
		}
	}

})