// 게시글 총 건수 정규식 표현(1000 단위 콤마)
const data = document.getElementById("getAllNotice").textContent;
console.log(data);
const numberOnly = data.replace(/[^0-9]/g, ""); // 숫자만 추출
const formatted = parseInt(numberOnly, 10).toLocaleString();
document.getElementById("getAllNotice").textContent = `총 ${formatted}건`;
