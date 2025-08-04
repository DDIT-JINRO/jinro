/**
 * 
 */
function header(){
	
	const menuIcon = document.getElementById("menuToggle");
	const dropdown = document.getElementById("dropdownMenu-tg");
	const worldcup = document.getElementById("worldcup");
	const roadmap  = document.getElementById("roadmap");
	
	menuIcon.addEventListener("click",() => {
		dropdown.classList.toggle("hidden");
	});
	
	document.addEventListener("click",(event) => {
	      if (!dropdown.contains(event.target) && !menuIcon.contains(event.target)) {
	    	  dropdown.classList.add("hidden");
	      }
	});
	
	if(roadmap) {
				roadmap.addEventListener("click", () => {
					if(!memId || memId=='anonymousUser') {
						sessionStorage.setItem("redirectUrl", location.href);
						location.href = "/login";
					} else {
						const roadmapUrl = 'http://localhost:5173/roadmap';
						
						const width  = 1084;
						const height = 736;
						const screenWidth  = window.screen.width;
						const screenHeight = window.screen.height;
			            const left = Math.floor((screenWidth - width) / 2);
			            const top  = Math.floor((screenHeight - height) / 2);
						
			            axios.post("/admin/las/roadMapVisitLog.do");
			            
						window.open(roadmapUrl, 'Roadmap', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
					}
				});
			}
			
			window.addEventListener("message", (event) => {
			    
			    if (event.origin !== 'http://localhost:5173') {
			        console.warn(`신뢰할 수 없는 출처(${event.origin})로부터의 메시지를 무시합니다.`);
			        return;
			    }

			    const messageData = event.data;

			    if (messageData && messageData.type === 'navigateParent') {
			        
			        const targetUrl = messageData.url;
			        if (targetUrl) {
			            window.location.href = targetUrl;
			        } else {
			            console.error('메시지에 이동할 URL이 없습니다.');
			        }
			    }
			});
			
			worldcup.addEventListener("click", () => {
				if(!memId || memId=='anonymousUser') {
					sessionStorage.setItem("redirectUrl", location.href);
					location.href = "/login";
				} else {
					axios.post("/admin/las/worldCupVisitLog.do")
					const worldcupUrl = 'http://localhost:5173/worldcup';
					
					const width  = 1200;
					const height = 800;
					const screenWidth  = window.screen.width;
					const screenHeight = window.screen.height;
					const left = Math.floor((screenWidth - width) / 2);
					const top  = Math.floor((screenHeight - height) / 2);
					
					window.open(worldcupUrl, 'worldcup', `width=\${width}, height=\${height}, left=\${left}, top=\${top}`);
				}
			});
}