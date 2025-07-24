<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>YouTube 영상 리스트</title>
<style>
.video-box {
	border-bottom: 1px solid #ccc;
	padding: 15px 0;
}

iframe {
	margin-top: 10px;
}
</style>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script type="text/javascript">

    	document.addEventListener("DOMContentLoaded",()=>{
    		let html = "";
    		
    		axios.get('https://youtube.googleapis.com/youtube/v3/search', {
    		    params: {
    		    	"part":"snippet",
    		    	"maxResults":5,
    		    	"channelId":"UCFCtZJTuJhE18k8IXwmXTYQ",
    			    "q":"적성|취업|적성",
    			    "regionCode":"kr",
    			    "key":"AIzaSyBFbyrGwjuDTxWcD0_Wg9M5WP3vRUz8Xwk"
    		    }
    		  })
    		  .then(function (response) {    		    
    		    const arr = response.data.items;
    		    console.log(arr);
    		    arr.forEach(item => {
    	             let id = item.id.videoId;
    	             console.log("id :", id);
    	             let title = item.snippet.title;
    	             console.log("title : ",title);
    	            
    	            
    	             html += `
    	                 <div class="video-box">
    	                     <h3>\${title}</h3>
    	                     <iframe width="560" height="315"
    	                         src="https://www.youtube.com/embed/\${id}"
    	                         frameborder="0"
    	                         allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
    	                         allowfullscreen>
    	                     </iframe>
    	                 </div>
    	             `;
    		    })
    		    document.getElementById("videoList").innerHTML = html;   		    
    		  })
    		  .catch(function (error) {
    		    console.log(error);
    		  }) 		
    	});
    </script>
</head>
<body>
	<h2>YouTube 검색 결과</h2>
	<div id="videoList"></div>
</body>
</html>
