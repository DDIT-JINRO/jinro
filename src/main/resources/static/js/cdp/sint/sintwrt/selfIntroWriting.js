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
