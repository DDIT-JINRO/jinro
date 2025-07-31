/**
 * 
 */

document.addEventListener("DOMContentLoaded", () => {
    const withdrawalForm = document.querySelector(".withdrawal-form");
    const passwordCheckMessage = document.querySelector("#password-check-message")

    withdrawalForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const data = {
            password : document.querySelector("#password").value,
            category : document.querySelector("#reason-select").value,
            reason : document.querySelector("#reason-text").value,
        }

        fetch("insertMemDelete.do", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }).then(response => {
            return response.text();
        }).then(res => {
            if ("success" !== res) {
                passwordCheckMessage.textContent = res;
                return;
            }
            alert("회원탈퇴가 완료되었습니다.");
            location.href = "/logout";
        }).catch(error => {
            console.log(error);
        })

    })

});