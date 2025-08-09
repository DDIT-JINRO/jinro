/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	
	document.getElementById('btnWrite').addEventListener('click', function() {
		if (!memId || memId == 'anonymousUser') {
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		} else {
			location.href = "/empt/ivfb/insertInterViewFeedbackView.do";
		}
	})
	
});




