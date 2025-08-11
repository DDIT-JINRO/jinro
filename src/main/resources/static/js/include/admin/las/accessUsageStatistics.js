// 모든 초기화 함수를 묶어주는 함수
function initDashboard() {
	dailyUserInquiry();
	loadLiveUserList();
	dailyPageVisitCount();

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
			
			const existingChart = Chart.getChart(document.getElementById('dailyUserInquiry'));
			if (existingChart) {
			    existingChart.destroy();
			}

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
			
			const existingChart = Chart.getChart(document.getElementById('monthUserInquiry'));
			if (existingChart) {
			    existingChart.destroy();
			}
			
			const ctx = document.getElementById('monthUserInquiry').getContext('2d');
			new Chart(ctx, config);
		})
		.catch(error => {
			console.error("데이터를 가져오는 중 오류 발생: ", error);
		});
}

function getCalendar() {
	const calendarInput = document.getElementById('getCalendar');
	const canvas = document.getElementById('customUserInquiry');

	// flatpickr 중복 초기화 방지 (필요시)
	if (calendarInput._flatpickr) {
		calendarInput._flatpickr.destroy();
	}

	flatpickr(calendarInput, {
		mode: "range",
		maxDate: "today",
		disable: [date => date > new Date()],
		onChange: function(selectedDates) {
			if (selectedDates.length === 2) {
				const startDate = selectedDates[0];
				const endDate = selectedDates[1];

				const formattedStartDate = startDate.toISOString().split('T')[0];
				const formattedEndDate = endDate.toISOString().split('T')[0];

				axios.post('/admin/las/customUserInquiry.do', null, {
					params: { startDate: formattedStartDate, endDate: formattedEndDate }
				})
					.then(response => {
						document.getElementById("dailyUserInquiry").style.display = "none";
						document.getElementById("monthUserInquiry").style.display = "none";
						document.getElementById("customUserInquiry").style.display = "block";

						const serverData = response.data;

						const labels = serverData.map(vo => {
							const date = new Date(vo.loginDate);
							return String(date.getDate()).padStart(2, '0');
						});
						const dataPoints = serverData.map(vo => vo.userCount);

						const data = {
							labels,
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
							data,
							options: {
								responsive: true,
								interaction: { mode: 'index', intersect: false },
								plugins: {
									legend: { position: 'top' },
									title: {
										display: true,
										text: [
											`${formattedStartDate} ~ ${formattedEndDate} 기간`,
											'일별 사용자 접속 통계'
										]
									},
									tooltip: {
										callbacks: {
											label: ctx => ` ${ctx.parsed.y}명`
										}
									}
								},
								scales: {
									x: { grid: { display: false } },
									y: {
										beginAtZero: true,
										ticks: {
											stepSize: 1,
											callback: value => value + '명'
										},
										grid: {
											borderDash: [3],
											color: '#e5e5e5'
										}
									}
								}
							}
						};

						// 기존 차트가 있으면 삭제
						if (canvas.chartInstance) {
							canvas.chartInstance.destroy();
						}

						// 새 차트 생성 및 캔버스에 저장
						canvas.chartInstance = new Chart(canvas.getContext('2d'), config);
					})
					.catch(error => console.error('서버 오류:', error));
			}
		}
	});

	calendarInput._flatpickr.open();
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

			const existingChart = Chart.getChart(document.getElementById('dailyPageVisitCount'));
			if (existingChart) {
			    existingChart.destroy();
			}
			
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

			const existingChart = Chart.getChart(document.getElementById('monthlyPageVisitCount'));
			if (existingChart) {
			    existingChart.destroy();
			}
			
			const ctx = document.getElementById('monthlyPageVisitCount').getContext('2d');
			new Chart(ctx, config);
		})
		.catch(error => {
			console.error("데이터 가져오기 실패:", error);
		});
}
function getPageVisitCountCalendar() {
	const calendarVisitInput = document.getElementById('getPageCalendar');
	const canvas = document.getElementById('customPageVisitCount');

	// flatpickr 중복 초기화 방지
	if (calendarVisitInput._flatpickr) {
		calendarVisitInput._flatpickr.destroy();
	}

	flatpickr(calendarVisitInput, {
		mode: "range",
		maxDate: "today",
		disable: [date => date > new Date()],
		onChange: function(selectedDates) {
			if (selectedDates.length === 2) {
				const startDate = selectedDates[0];
				const endDate = selectedDates[1];

				const formattedStartDate = startDate.toISOString().split('T')[0];
				const formattedEndDate = endDate.toISOString().split('T')[0];

				axios.post('/admin/las/getPageCalendar.do', null, {
					params: { startDate: formattedStartDate, endDate: formattedEndDate }
				})
					.then(response => {
						document.getElementById("dailyPageVisitCount").style.display = "none";
						document.getElementById("monthlyPageVisitCount").style.display = "none";
						document.getElementById("customPageVisitCount").style.display = "block";

						const serverData = response.data;
						const labels = serverData.map(vo => vo.plTitle);
						const dataPoints = serverData.map(vo => vo.count);

						const config = {
							type: 'bar',
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
									legend: { display: false },
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
											font: { size: 10 }
										}
									},
									y: { beginAtZero: true, ticks: { precision: 0 } }
								}
							}
						};

						const ctx = canvas.getContext('2d');

						// 기존 차트가 있으면 삭제
						if (canvas.chartInstance) {
							canvas.chartInstance.destroy();
						}

						// 새 차트 생성 및 캔버스에 저장
						canvas.chartInstance = new Chart(ctx, config);
					})
					.catch(error => {
						console.error('서버 오류:', error);
					});
			}
		}
	});

	calendarVisitInput._flatpickr.open();
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