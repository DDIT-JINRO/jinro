/**
 * 
 */

async function naverLogin(){
	let CLIENT_ID = "";
	const CALLBACK_URL = 'http://localhost/lgn/naverCallback.do';	
	
	await axios.post('/lgn/naverClientKey.do')
	  .then(response => {
	    CLIENT_ID = response.data // 성공 시 응답 데이터
	  })
	  .catch(error => {
	    console.error(error); // 에러 처리
	  });
	
		
	const naverAuthURL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${CLIENT_ID}&state=STATE_STRING&redirect_uri=${CALLBACK_URL}`
	console.log(naverAuthURL);
	window.location.href = naverAuthURL;
}
	
async function kakaoLogin() {
  let REST_API_KEY = "";
  const REDIRECT_URI = 'http://localhost/lgn/kakaoCallback.do';
	
  await axios.post('/lgn/kakaoRestApiKey.do')
  	  .then(response => {
  	    REST_API_KEY = response.data // 성공 시 응답 데이터
  	  })
  	  .catch(error => {
  	    console.error(error); // 에러 처리
  	  });
  
  const kakaoAuthURL = 
    `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&prompt=login`;

  window.location.href = kakaoAuthURL;
}

function loginBtn(){
	
	const getUserId = document.getElementById('login-user-email').value
	const getUserPw = document.getElementById('login-user-password').value
		
	  fetch('/memberLogin',{
		  method: "POST",
		  headers :  {
			  "Content-Type": "application/json"
			  },
		  body: JSON.stringify({
			  
			  memEmail : getUserId,
			  memPassword : getUserPw,
			  loginType : "normal"
			  
		  })
	  }) 
	  .then(response => response.json())  
	  .then(data => {
	    if(data.status=='success'){
			const redirectUrl = sessionStorage.getItem("redirectUrl");
			if (redirectUrl) {
				sessionStorage.removeItem("redirectUrl");
				location.href = redirectUrl;
			} else {
				location.href = "/";
			}
	    }
	  })
	  .catch(error => {
	    console.error('에러 발생:', error);
	  });
 	}