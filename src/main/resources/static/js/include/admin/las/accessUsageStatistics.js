// 페이지네이션 관련 전역 변수
let currentPage = 1;      // 현재 페이지
const recordsPerPage = 10; // 한 페이지당 보여줄 레코드 수

// 페이지 로드 시 함수 호출
dailyUserInquiry();
loadLiveUserList();
pageVisitCount();

function dailyUserInquiry() {
    let mm = 0;
    document.getElementById("dailyUserInquiry").style.display = "block";
    document.getElementById("monthUserInquiry").style.display = "none";

    axios.get('/admin/las/dailyUserInquiry.do')
        .then(response => {
            const serverData = response.data;

            const labels = serverData.map(vo => {
                const date = new Date(vo.loginDate);
                mm = String(date.getMonth() + 1).padStart(2, '0');
                const dd = String(date.getDate()).padStart(2, '0');
                return `${dd}`;
            });

            const dataPoints = serverData.map(vo => vo.userCount);

            const data = {
                labels: labels,
                datasets: [{
                    label: '일별 접속자 수',
                    data: dataPoints,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                    tension: 0.4,
                    pointRadius: 3,
                    pointHoverRadius: 6,
                    fill: true
                }]
            };

            const config = {
                type: 'line',
                data: data,
                options: {
                    responsive: true,
                    interaction: {
                        mode: 'index',
                        intersect: false
                    },
                    plugins: {
                        legend: {
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: `${mm}월 일별 사용자 접속 통계`
                        },
                        tooltip: {
                            callbacks: {
                                label: function(ctx) {
                                    return ` ${ctx.parsed.y}명`;
                                }
                            }
                        }
                    },
                    scales: {
                        x: {
                            grid: {
                                display: false
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1,
                                callback: function(value) {
                                    return value + '명';
                                }
                            },
                            grid: {
                                borderDash: [3],
                                color: '#e5e5e5'
                            }
                        }
                    }
                }
            };

            const ctx = document.getElementById('dailyUserInquiry').getContext('2d');
            new Chart(ctx, config);
        })
        .catch(error => {
            console.error("데이터를 가져오는 중 오류 발생: ", error);
        });
}
function monthUserInquiry() {
    let yy = 0;
    document.getElementById("dailyUserInquiry").style.display = "none";
    document.getElementById("monthUserInquiry").style.display = "block";

    axios.get('/admin/las/monthlyUserInquiry.do')
        .then(response => {
            const serverData = response.data;

            const labels = serverData.map(vo => {
                const date = new Date(vo.loginMonth);
                yy = String(date.getFullYear());
                const mm = String(date.getMonth() + 1).padStart(2, '0');
                return `${mm}`;
            });

            const dataPoints = serverData.map(vo => vo.userCount);

            const data = {
                labels: labels,
                datasets: [{
                    label: '월별 접속자 수',
                    data: dataPoints,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    tension: 0.4,
                    pointRadius: 3,
                    pointHoverRadius: 6,
                    fill: true
                }]
            };

            const config = {
                type: 'line',
                data: data,
                options: {
                    responsive: true,
                    interaction: {
                        mode: 'index',
                        intersect: false
                    },
                    plugins: {
                        legend: {
                            position: 'top'
                        },
                        title: {
                            display: true,
                            text: `${yy}년 월별 사용자 접속 통계`
                        },
                        tooltip: {
                            callbacks: {
                                label: function(ctx) {
                                    return ` ${ctx.parsed.y}명`;
                                }
                            }
                        }
                    },
                    scales: {
                        x: {
                            grid: {
                                display: false
                            }
                        },
                        y: {
                            beginAtZero: true,
                            ticks: {
                                stepSize: 1,
                                callback: function(value) {
                                    return value + '명';
                                }
                            },
                            grid: {
                                borderDash: [3],
                                color: '#e5e5e5'
                            }
                        }
                    }
                }
            };

            const ctx = document.getElementById('monthUserInquiry').getContext('2d');
            new Chart(ctx, config);
        })
        .catch(error => {
            console.error("데이터를 가져오는 중 오류 발생: ", error);
        });
}

function pageVisitCount() {
  axios.get('admin/las/pageVisitCount.do')
    .then(response => {
      console.log("검색 결과:", response.data);

      const labels = response.data.map(item => item.plTitle);
      const data = response.data.map(item => item.count);

      const config = {
        type: 'bar', // 차트 유형: 막대 그래프
        data: {
          labels: labels,
          datasets: [{
            label: '일별 페이지 방문자 수 Top 10',
            data: data,
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(255, 159, 64, 0.2)',
              'rgba(255, 205, 86, 0.2)',
              'rgba(75, 192, 192, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(153, 102, 255, 0.2)',
              'rgba(201, 203, 207, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 99, 132, 0.2)',
              'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
              'rgb(255, 99, 132)',
              'rgb(255, 159, 64)',
              'rgb(255, 205, 86)',
              'rgb(75, 192, 192)',
              'rgb(54, 162, 235)',
              'rgb(153, 102, 255)',
              'rgb(201, 203, 207)',
              'rgb(54, 162, 235)',
              'rgb(255, 99, 132)',
              'rgb(255, 159, 64)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              display: false
            },
            title: {
              display: true,
              text: '일별 페이지 방문자 수 Top 10'
            }
          },
          scales: {
			x: {
			  ticks: {
			    callback: function(value) {
			      const label = this.getLabelForValue(value);
			      if (label.includes(" - ")) {
			        return label.split(" - "); // 2줄로 나눔
			      }else if(label.length > 6) {
					const words = label.split(' ');
				  if (words.length > 1) {
				    return words;
				  }
				}
			      return label;
			    }
			  }
			},
            y: {
              beginAtZero: true,
              ticks: { precision: 0 }
            }
          }
        }
      };

      const ctx = document.getElementById('pageVisitCount').getContext('2d');
      new Chart(ctx, config);
    })
    .catch(error => {
      console.error("데이터 가져오기 실패:", error);
    });
}


// 실시간 사용자 조회
// 초기 페이지 로드 시 호출되는 함수
function loadLiveUserList(page = 1) {
    const gen = document.querySelector('select[name="gubun1"]').value;
    const loginType = document.querySelector('select[name="gubun2"]').value;
    const keyword = document.querySelector('input[name="keyword"]').value;

    axios({
        method: 'get',
        url: `/admin/las/liveUserList.do`,
        params: {
            currentPage: page,
            size: recordsPerPage,
            keyword: keyword,
            gen: gen,
            loginType: loginType
        }
    }).then(response => {
        const articlePage = response.data;
        const liveUsers = articlePage.content;
		console.log("articlePage : ",articlePage);
		console.log("liveUsers : ",liveUsers);
        
        let memList = "";
        if (liveUsers && liveUsers.length > 0) {
            liveUsers.forEach(user => {
                memList += `
                    <tr>
                        <td>${user.MEM_ID}</td>
                        <td>${user.MEM_NAME}</td>
                        <td>${user.MEM_GEN_NAME}</td>
                        <td>${user.LOGIN_TYPE_NAME}</td>
                        <td>${user.MEM_STUDENT_NAME}</td>
                    </tr>
                `;
            });
        } else {
            memList = `<tr><td colspan="5">데이터가 없습니다.</td></tr>`;
        }

        document.getElementById("memberCount").innerHTML = articlePage.total;
        document.getElementById("mem-list").innerHTML = memList;
        
        renderPagination(articlePage);
    }).catch(error => {
        console.log("error : ", error);
    });
}

// 검색 조회
function serchList() {
    currentPage = 1; // 검색 시 1페이지로 초기화
    loadLiveUserList(currentPage);
}

// 페이지네이션(하단 페이지 버튼들)을 렌더링
function renderPagination({ startPage, endPage, currentPage, totalPages }) {
    let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

    for (let p = startPage; p <= endPage; p++) {
        html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
    }

    html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

    const footer = document.querySelector('.panel-footer.pagination');
    if (footer) {
        footer.innerHTML = html;
        // 페이지 링크 클릭 이벤트 리스너 추가
        footer.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const page = parseInt(e.target.dataset.page);
                if (!isNaN(page) && !e.target.classList.contains('disabled')) {
                    loadLiveUserList(page);
                }
            });
        });
    }
}

// 검색 폼 리셋
function resetData(){
	document.querySelector('select[name="gubun1"]').value="";
	document.querySelector('select[name="gubun2"]').value="";
	document.querySelector('input[name="keyword"]').value="";
	loadLiveUserList(1);
}


