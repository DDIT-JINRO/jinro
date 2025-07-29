{
	axios.get('/csc/downloadFile?fileGroupNo=fileGroupId')
		.then(response => {
			console.log(response);
		})
		.catch(error => {
			console.log(error);
		});
}
