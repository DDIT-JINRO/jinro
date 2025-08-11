// 모든 초기화 함수를 묶어주는 함수
function initDashboard() {
	dailyUserInquiry();
	loadLiveUserList();
	dailyPageVisitCount();
	resetCustomChart();
}
let customChart = null;
let PageVisitCountCalendar = null;
function resetCustomChart(){
	if(customChart != null){
		customChart = null;
	}
	if(PageVisitCountCalendar != null){
		PageVisitCountCalendar = null;
	}
}


// 실시간 사용자 조회
// 초기 페이지 로드 시 호출되는 함수
function loadLiveUserList(page = 1) {
	const recordsPerPage = 10;
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
	loadLiveUserList(1); // 검색 시 1페이지로 초기화
}

// 검색 폼 리셋
function resetData() {
	document.querySelector('select[name="gubun1"]').value = "";
	document.querySelector('select[name="gubun2"]').value = "";
	document.querySelector('input[name="keyword"]').value = "";
	loadLiveUserList(1);
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
		// 이벤트 위임으로 처리하므로, 여기서는 개별 리스너를 추가하지 않습니다.
	}
}

function dailyUserInquiry() {
	let mm = 0;
	document.getElementById("dailyUserInquiry").style.display = "block";
	document.getElementById("monthUserInquiry").style.display = "none";
	document.getElementById("customUserInquiry").style.display = "none";

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
							},
						},
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
	document.getElementById("customUserInquiry").style.display = "none";

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
							},
						},
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

// 달력 사용자 조회
function getCalendar() {

	// 달력을 보여줄 input 요소를 가져옴
	const calendarInput = document.getElementById('getCalendar');

	// flatpickr 인스턴스를 생성하여 달력을 초기화
	flatpickr(calendarInput, {
		mode: "range", // 날짜 범위 선택 모드
		onOpen: function() {
			// 달력이 열릴 때 추가 작업이 필요하다면 여기에 작성
		},
		maxDate: "today", // 오늘 이후 날짜는 선택 불가
		disable: [
			function(date) {
				return date > new Date(); // 오늘 이후 날짜는 비활성화
			}
		],
		onChange: function(selectedDates) {
			// 두 개의 날짜가 선택되었을 때
			if (selectedDates.length === 2) {
				const startDate = selectedDates[0]; // 시작 날짜
				const endDate = selectedDates[1];   // 종료 날짜

				// 날짜를 'YYYY-MM-DD' 형식으로 변환
				const formattedStartDate = startDate.toISOString().split('T')[0];
				const formattedEndDate = endDate.toISOString().split('T')[0];

				// axios를 사용해 서버로 날짜 범위 전송
				axios.post('/admin/las/customUserInquiry.do', null, {
					params: {
						startDate: formattedStartDate,
						endDate: formattedEndDate
					}
				})
					.then(response => {
						
						document.getElementById("dailyUserInquiry").style.display = "none";
						document.getElementById("monthUserInquiry").style.display = "none";
						document.getElementById("customUserInquiry").style.display = "block";
						
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
								label: '해당 기간 접속자 수',
								data: dataPoints,
								borderColor: 'rgba(255, 99, 132, 1)',       
								backgroundColor: 'rgba(255, 99, 132, 0.2)', 
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
										text: [
										    formattedStartDate + ' ~ ' + formattedEndDate+ ' 기간',
										    '일별 사용자 접속 통계'
										]
									},
									tooltip: {
										callbacks: {
											label: function(ctx) {
												return ` ${ctx.parsed.y}명`;
											}
										},
									},
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

						const ctx = document.getElementById('customUserInquiry').getContext('2d');

						// 기존 차트가 있으면 삭제
						if (customChart) {
						  customChart.destroy();
						}

						// 새 차트 생성 후 저장
						customChart = new Chart(ctx, config);
					})
					.catch(error => {
						console.error('서버 오류:', error);
					});
			}
		}
	});
	// 달력을 열기
	calendarInput._flatpickr.open();
	
	// 초기화
	calendarInput._flatpickr.clear();
		
}
// 일일 페이지 방문
function dailyPageVisitCount() {
	axios.get('admin/las/pageVisitCount.do')
		.then(response => {

			document.getElementById("dailyPageVisitCount").style.display = "block";
			document.getElementById("monthlyPageVisitCount").style.display = "none";
			document.getElementById("customPageVisitCount").style.display = "none";

			
			const labels = response.data.map(item => item.plTitle);
			const data = response.data.map(item => item.count);

			const config = {
				type: 'bar', // 차트 유형: 막대 그래프
				data: {
					labels: labels,
					datasets: [{
						label: '일간 페이지 방문자 수 Top 10',
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
							text: '일간 페이지 방문자 수 Top 10'
						}
					},
					scales: {
						x: {
							ticks: {
								callback: function(value) {
									const label = this.getLabelForValue(value);
									if (label.includes(" - ")) {
										return label.split(" - "); // 2줄로 나눔
									} else if (label.length > 6) {
										const words = label.split(' ');
										if (words.length > 1) {
											return words;
										}
									}
									return label;
								},
								font: {
									size: 10 // 원하는 글꼴 크기로 변경 가능
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

			const ctx = document.getElementById('dailyPageVisitCount').getContext('2d');
			new Chart(ctx, config);
		})
		.catch(error => {
			console.error("데이터 가져오기 실패:", error);
		});
}


// 월간 
function monthPageVisitCount(){
	axios.get('admin/las/monthPageVisitCount.do')
		.then(response => {
			const labels = response.data.map(item => item.plTitle);
			const data = response.data.map(item => item.count);

			document.getElementById("dailyPageVisitCount").style.display = "none";
			document.getElementById("monthlyPageVisitCount").style.display = "block";
			document.getElementById("customPageVisitCount").style.display = "none";
			
			const config = {
				type: 'bar', // 차트 유형: 막대 그래프
				data: {
					labels: labels,
					datasets: [{
						label: '월간 페이지 방문자 수 Top 10',
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
							text: '월간 페이지 방문자 수 Top 10'
						}
					},
					scales: {
						x: {
							ticks: {
								callback: function(value) {
									const label = this.getLabelForValue(value);
									if (label.includes(" - ")) {
										return label.split(" - "); // 2줄로 나눔
									} else if (label.length > 6) {
										const words = label.split(' ');
										if (words.length > 1) {
											return words;
										}
									}
									return label;
								},
								font: {
									size: 10 // 원하는 글꼴 크기로 변경 가능
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

			const ctx = document.getElementById('monthlyPageVisitCount').getContext('2d');
			new Chart(ctx, config);
		})
		.catch(error => {
			console.error("데이터 가져오기 실패:", error);
		});
}
// 원하는 기간별
function getPageVisitCountCalendar(){

	// 달력을 보여줄 input 요소를 가져옴
	const calendarVisitInput = document.getElementById('getPageCalendar');

	// flatpickr 인스턴스를 생성하여 달력을 초기화
	flatpickr(calendarVisitInput, {
		mode: "range", // 날짜 범위 선택 모드
		onOpen: function() {
			// 달력이 열릴 때 추가 작업이 필요하다면 여기에 작성
		},
		maxDate: "today", // 오늘 이후 날짜는 선택 불가
		disable: [
			function(date) {
				return date > new Date(); // 오늘 이후 날짜는 비활성화
			}
		],
		onChange: function(selectedDates) {
			// 두 개의 날짜가 선택되었을 때
			if (selectedDates.length === 2) {
				const startDate = selectedDates[0]; // 시작 날짜
				const endDate = selectedDates[1];   // 종료 날짜

				// 날짜를 'YYYY-MM-DD' 형식으로 변환
				const formattedStartDate = startDate.toISOString().split('T')[0];
				const formattedEndDate = endDate.toISOString().split('T')[0];

				// axios를 사용해 서버로 날짜 범위 전송
				axios.post('/admin/las/getPageCalendar.do', null, {
					params: {
						startDate: formattedStartDate,
						endDate: formattedEndDate
					}
				})
					.then(response => {
						document.getElementById("dailyPageVisitCount").style.display = "none";
						document.getElementById("monthlyPageVisitCount").style.display = "none";
						document.getElementById("customPageVisitCount").style.display = "block";
						
						const serverData = response.data;

						const labels = serverData.map(vo=> vo.plTitle);
						const dataPoints = serverData.map(vo => vo.count);

						const config = {
									type: 'bar', // 차트 유형: 막대 그래프
									data: {
										labels: labels,
										datasets: [{
											label: '해당 기간 페이지 방문자 수 Top 10',
											data: dataPoints,
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
												text: [
													formattedStartDate + ' ~ ' + formattedEndDate + ' 기간',
													'페이지 방문자 수 Top 10'
												]
											}
										},
										scales: {
											x: {
												ticks: {
													callback: function(value) {
														const label = this.getLabelForValue(value);
														if (label.includes(" - ")) {
															return label.split(" - "); // 2줄로 나눔
														} else if (label.length > 6) {
															const words = label.split(' ');
															if (words.length > 1) {
																return words;
															}
														}
														return label;
													},
													font: {
														size: 10 // 원하는 글꼴 크기로 변경 가능
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

						const ctx = document.getElementById('customPageVisitCount').getContext('2d');

						// 기존 차트가 있으면 삭제
						if (PageVisitCountCalendar) {
						  PageVisitCountCalendar.destroy();
						}

						// 새 차트 생성 후 저장
						PageVisitCountCalendar = new Chart(ctx, config);
					})
					.catch(error => {
						console.error('서버 오류:', error);
					});
			}
		}
	});
	// 달력을 열기
	calendarVisitInput._flatpickr.open();

	// 초기화
	calendarVisitInput._flatpickr.clear();
}
// 이벤트 위임을 사용하여 동적으로 생성된 요소의 이벤트 처리
document.addEventListener('click', (e) => {
	// 검색 버튼 클릭 처리
	if (e.target.matches('.search-button')) {
		serchList();
	}

	// 페이지네이션 링크 클릭 처리
	if (e.target.matches('.page-link') && !e.target.classList.contains('disabled')) {
		e.preventDefault();
		const page = parseInt(e.target.dataset.page);
		if (!isNaN(page)) {
			loadLiveUserList(page);
		}
	}
	// 검색 폼 리셋 버튼 클릭 처리
	if (e.target.matches('.reset-button')) {
		resetData();
	}
});

// 페이지 로드 시 모든 초기화 함수를 호출
initDashboard();