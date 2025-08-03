/**
 * 
 */

document.addEventListener("DOMContentLoaded", () => {
    // 페이지 로드 시 메시지 처리
    const channelSection = document.querySelector(".channel");
    if (channelSection) {
        const successMessage = channelSection.dataset.successMessage;
        const errorMessage = channelSection.dataset.errorMessage;
        if (successMessage) alert(successMessage);
        if (errorMessage) alert(errorMessage);
    }

    const withdrawalForm = document.querySelector(".withdrawal-form");

    withdrawalForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const data = {
            password : document.querySelector("#password").value,
            category : document.querySelector("#reason-select").value,
            reason : document.querySelector("#reason-text").value,
        }

        fetch("/mpg/mif/whdwl/insertMemDelete.do", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(response => {
			return response.json();
		}).then(result => {
			alert(result.message);
		    window.location.href = "/logout"; 
		}).catch(error => {
			console.error("회원 탈퇴 중 에러 발생 :", error);
			alert("회원 탈퇴 처리 중 오류가 발생했습니다.");
		})
    });
});
