/**
 * 
 */

function sidebar(){
	const sidebar = document.querySelector('.admin-side-sidebar');
		  setTimeout(() => {
		    sidebar.classList.add('active');
		  }, 300);

		  document.querySelectorAll('.admin-side-menu-link').forEach(function (el) {
		    el.addEventListener('click', function (e) {
		      e.preventDefault();

		      const pageUrl = this.dataset.page;

		      console.log(pageUrl);

		      fetch(pageUrl)
		        .then(response => {
		          if (!response.ok) throw new Error("불러오기 실패");
		          return response.text();
		        })
		        .then(html => {
		          document.getElementById('content').innerHTML = html;

		          const scripts = document.getElementById('content').querySelectorAll('script');
		          scripts.forEach(oldScript => {
		            const newScript = document.createElement('script');
		            if (oldScript.src) {

		              newScript.src = oldScript.src;
		            } else {

		              newScript.textContent = oldScript.textContent;
		            }
		            document.body.appendChild(newScript);

		            oldScript.remove();
		          });
		        })
		        .catch(err => {
		          document.getElementById('content').innerHTML = '<p>에러: 페이지를 불러올 수 없습니다.</p>';
		          console.error(err);
		        });
		    });
		  });
}