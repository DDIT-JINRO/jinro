/**
 * 
 */
// 비밀번호 적합 여부
document.addEventListener("DOMContentLoaded", () => {
    let isNewValidate = false;
    let isConfirmValidate = false;

    const submitBtn = document.querySelector("#submit-btn");

    const oldPasswordInput = document.querySelector("#old-password");
    const oldErrorMsg = oldPasswordInput.nextElementSibling;
    const newPasswordInput = document.querySelector("#new-password");
    const newErrorMsg = newPasswordInput.nextElementSibling;
    const confirmPasswordInput = document.querySelector("#confirm-password");
    const confirmErrorMsg = confirmPasswordInput.nextElementSibling;

    oldPasswordInput.addEventListener("input", () => {
        oldErrorMsg.textContent = "";
    })

    newPasswordInput.addEventListener("input", () => {
        validateNewPassword();
        validateConfirmPassword();
    });

    confirmPasswordInput.addEventListener("input", () => {
        validateConfirmPassword();
    })

    submitBtn.addEventListener("click", () => {
        if (isNewValidate && isConfirmValidate) {
            fetch("updatePassword.do", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    "password": oldPasswordInput.value,
                    "newPassword": newPasswordInput.value
                })
            }).then(response => {
                return response.text();
            }).then(data => {
                if ("success" === data ) {
                    alert("비밀번호가 정상적으로 변경되었습니다.");
                    location.href = "/";
                } else {
                    oldErrorMsg.textContent = data;
                }
            });
        }
    })


    const validateNewPassword = () => {
        const newPassword = newPasswordInput.value;
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/;

        if (newPassword === "" && newPassword === null) {
            newErrorMsg.textContent = "비밀번호를 입력해주세요.";
            isNewValidate = false;
            return;
        } else if (!passwordRegex.test(newPassword)) {
            newErrorMsg.textContent = "비밀번호는 최소 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.";
            isNewValidate = false;
            return;
        } else {
            newErrorMsg.textContent = "사용가능한 비밀번호 입니다.";
            newErrorMsg.classList.add("success");
            isNewValidate = true;
        }
    }

    const validateConfirmPassword = () => {
        const confirmPassword = confirmPasswordInput.value;
        const newPassword = newPasswordInput.value;

        if (confirmPassword === "" || confirmPassword === null || confirmPassword === undefined) {
            confirmErrorMsg.textContent = "";
            isConfirmValidate = false;
            return;
        } else if (newPassword !== confirmPassword) {
            confirmErrorMsg.textContent = "비밀번호가 일치하지 않습니다.";
            isConfirmValidate = false;
            return;
        } else {
            confirmErrorMsg.textContent = "비밀번호가 일치합니다.";
            confirmErrorMsg.classList.add("success");
            isConfirmValidate = true;
        }
    }


})