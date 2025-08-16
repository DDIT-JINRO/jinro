
function insertInq() {
    const title = document.getElementById('post-title').value.trim();
    const content = document.getElementById('description').value.trim();

    // 유효성 검사
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
        contactIsPublic: 'N',
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
