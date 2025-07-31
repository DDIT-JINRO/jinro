/**
 * 
 */

document.addEventListener("DOMContentLoaded", () => {
	try {
		if (memId == "anonymousUser") {
			alert("로그인 후 이용 가능한 페이지 입니다.")
			throw new Error("로그인 후 사용 가능한 페이지 입니다.");
		}
	} catch {
		sessionStorage.setItem("redirectUrl", location.href);
		location.href = "/login";
	}

	const moveSubBtn = document.querySelector("#move-sub-btn");
	moveSubBtn.addEventListener("click", () => {
		location.href = "/mpg/pay/selectPaymentView.do";
	});

});
