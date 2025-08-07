// 파일 다운로드
function filedownload(fileGroupId,fileSeq,fileOrgName){
	axios({
	  method: 'get',
	  url: `/files/download`,
	  params: {
		fileGroupId: fileGroupId, 
		seq: fileSeq            
	  },
	  responseType: 'blob' // 중요: 파일 다운로드 시 꼭 필요
	})
	.then(response => {
	  // 브라우저에서 파일 저장 처리
	  const blob = new Blob([response.data]);
	  const url = window.URL.createObjectURL(blob);
	  
	  const a = document.createElement('a');
	  a.href = url;
	  a.download = fileOrgName;
	  document.body.appendChild(a);
	  a.click();
	  document.body.removeChild(a);
	  window.URL.revokeObjectURL(url);
	})
	.catch(error => {
	  console.error('파일 다운로드 실패:', error);
	});
}