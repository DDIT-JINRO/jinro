document.addEventListener('DOMContentLoaded', function () {
	document.querySelector('.btn-temp-save').addEventListener('click', function () {
	  document.getElementById('siStatus').value = '작성중';
	  const form = document.querySelector('.selfintro-write-form form');
	  form.submit();
	});
	
	document.querySelector(".btn-delete")?.addEventListener("click", () => {
		const form = document.querySelector('.selfintro-write-form form');
	    if (confirm("정말 삭제하시겠습니까?")) {
	      form.action = "/sint/sintwrt/delete.do";
	      form.submit();
	    }
	  });
});
function countChars(textarea, index) {
    const length = textarea.value.length;
    const counter = document.getElementById("charCount-" + index);
    if (counter) {
      counter.textContent = length;
    }
  }

  // 초기 렌더링 시 기존 값에 대한 글자 수 세기
  window.addEventListener("DOMContentLoaded", () => {
    const textareas = document.querySelectorAll("textarea[name='sicContentList']");
    textareas.forEach((ta, i) => {
      countChars(ta, i);
    });
  });