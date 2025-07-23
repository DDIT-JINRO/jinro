<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
</head>
<body>

<h2>YouTube 검색 결과</h2>



<div id="videoList">

</div>

<script>
    try {
        const rawJson = ${youtubeJson};
        console.log(rawJson); // 확인
        console.log("===================");
        
        const arr = rawJson.items;
        console.log("arr : ",arr);

        let html = "";
        
        arr.forEach(item => {
            let id = item.id.videoId;
            id = id.replace(" ", "");
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

        });

        document.getElementById("videoList").innerHTML = html;
    } catch (e) {
        console.error("JSON 파싱 오류:", e);
        document.getElementById("videoList").innerHTML =
            "<p style='color:red;'>유튜브 데이터를 불러오는 데 실패했습니다.</p>";
    }
</script>

</body>
</html>
