/**
 *
 */
document.addEventListener('DOMContentLoaded', function(){
	const cardList = document.querySelectorAll('.group-card');
	cardList.forEach(card =>{
		card.addEventListener('click', function(){
			location.href = '/prg/std/stdGroupDetail.do?stdGroupId='+this.dataset.stdbId;
		})
	})

	document.getElementById('btnWrite').addEventListener('click', function(){
		if(!memId || memId=='anonymousUser'){
			sessionStorage.setItem("redirectUrl", location.href);
			location.href = "/login";
		}else{
			location.href = "/prg/std/createStdGroup.do";
		}
	})

})


