document.addEventListener("DOMContentLoaded", () => {
    const selectLabel = document.getElementById("selectLabel");
    const selectOptions = document.getElementById("selectOptions");
    const hiddenSelect = document.getElementById("publicOrNot");

    // 토글 열기/닫기
    selectLabel.addEventListener("click", () => {
        const isVisible = selectOptions.style.display === "block";
        selectOptions.style.display = isVisible ? "none" : "block";
    });

    // 옵션 클릭 시 값 설정
    selectOptions.querySelectorAll("li").forEach(option => {
        option.addEventListener("click", () => {
            const label = option.textContent;
            const dataValue = option.getAttribute("data-value"); // "공개" 또는 "비공개"

            // UI 텍스트 반영
            selectLabel.textContent = label;

            // 내부 값 Y/N로 매핑
            hiddenSelect.value = dataValue === "공개" ? "Y" : "N";

            // 옵션 숨김
            selectOptions.style.display = "none";
        });
    });

    // 바깥 클릭 시 닫기
    document.addEventListener("click", (e) => {
        if (!e.target.closest(".custom-select")) {
            selectOptions.style.display = "none";
        }
    });
});

function insertInq() {
    const contactIsPublic = document.getElementById('publicOrNot').value;
    const title = document.getElementById('post-title').value.trim();
    const content = document.getElementById('description').value.trim();

    console.log("contactIsPublic:", contactIsPublic);

    // 유효성 검사
    if (!contactIsPublic) {
        alert("공개 여부를 선택해주세요.");
        return;
    }
    if (!title) {
        alert("제목을 입력해주세요.");
        return;
    }
    if (!content) {
        alert("문의 내용을 입력해주세요.");
        return;
    }

    // 전송
    axios.post('/csc/inq/insertInqData.do', {
        contactIsPublic,
        contactTitle: title,
        contactContent: content
    })
    .then(res => {
        if (res.data === 1) {
            location.href = "/csc/inq/inqryList.do";
        } else {
            alert("등록에 실패했습니다.");
        }
    })
    .catch(err => {
        console.error(err);
        alert("서버 오류가 발생했습니다.");
    });
}
