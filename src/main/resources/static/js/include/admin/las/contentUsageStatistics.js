/* ========================= 공통 유틸 ========================= */

// formatNumberWithCommasByAdminDashboard -> 숫자로 된 값 , 붙여주는거
// setActiveButton -> 버튼그룹 활성화 시켜주는거
// 

(function() {

	//  params 필터 조건 (period, startDate, endDate, gender, ageGroup, serviceType)
	axios.get('/admin/las/cont/worldcup-roadmap/usage-stats', {
		params: {
			
		}
	}).then(res => {
		console.log("worldcup-roadmap/usage-stats",res.data);
	})

	axios.get('/admin/las/cont/daily-summary')
		.then(res => {
			const chartData = res.data;

			updateDailySummary(chartData);
		})


	axios.get('/admin/las/cont/roadmap/step-distribution').then(res => {

		displayStepPercentages(res.data);

		const worldcupTopJobs = res.data.worldcupTopJobs;
		displayTopJobs(worldcupTopJobs);
	})




	function fetchAndDisplayCommunityStats(period) {
		const params = {};
		if (period) {
			params.period = period;
		}
		axios.get('/admin/las/cont/community/activity-stats', { params })
			.then(res => {

				updateCommunityStats(res.data.stats, period || 'week');
			});
	}


	fetchAndDisplayCommunityStats();


	const weekBtn = document.getElementById('commOnceView-weekBtn');
	const monthBtn = document.getElementById('commOnceView-monthBtn');

	if (weekBtn) {
		weekBtn.addEventListener('click', function() {
			setActiveButton(this);
			fetchAndDisplayCommunityStats('week');
		});
	}

	if (monthBtn) {
		monthBtn.addEventListener('click', function() {
			setActiveButton(this);
			fetchAndDisplayCommunityStats('month');
		});
	}












	function updateDailySummary(chartData) {

		const summaryMap = {
			'dailyPosts': {
				countId: 'dailyPosts',
				rateId: 'dailyPostsRate'
			},
			'dailyBookmarks': {
				countId: 'dailyBookmarks',
				rateId: 'dailyBookmarksRate'
			},
			'dailyChatRooms': {
				countId: 'dailyChatRooms',
				rateId: 'dailyChatRoomsRate'
			}
		};


		for (const key in summaryMap) {

			const count = chartData[key];
			const status = chartData[key + 'Status'];
			const rate = chartData[key + 'Rate'];

			const ids = summaryMap[key];

			const countElement = document.getElementById(ids.countId);
			const rateElement = document.getElementById(ids.rateId);

			if (countElement) {
				countElement.innerHTML = formatNumberWithCommasByAdminDashboard(count);
			}
			if (rateElement) {

				updateStatusUI(rateElement, status, rate);
			}
		}
	}

	function updateRateUI(element, status, rate) {
		element.classList.remove('public-span-increase', 'public-span-decrease', 'public-span-equal');

		if (status === "increase") {
			element.innerHTML = `&#9650;&nbsp;${rate}%`;
			element.classList.add('public-span-increase');
		} else if (status === "decrease") {
			element.innerHTML = `&#9660;&nbsp;${rate}%`;
			element.classList.add('public-span-decrease');
		} else {
			element.innerHTML = `- ${rate}%`;
			element.classList.add('public-span-equal');
		}
	}

	function updateStatusUI(element, status, rate) {
		element.classList.remove('public-span-increase', 'public-span-decrease', 'public-span-equal');

		if (status === "increase") {
			element.innerHTML = `&#9650;&nbsp;${rate}%`;
			element.classList.add('public-span-increase');
		} else if (status === "decrease") {
			element.innerHTML = `&#9660;&nbsp;${rate}%`;
			element.classList.add('public-span-decrease');
		} else if (status === "equal") {
			if (element.id === 'dailyPostsRate') {
				element.innerHTML = `${rate}%`;
			} else {
				element.innerHTML = `- ${rate}%`;
			}
			element.classList.add('public-span-equal');
		} else {
			element.innerHTML = `NEW`;
			element.classList.add('public-span-increase');
		}
	}

	function updateCommunityStats(stats, period) {
		const statsMap = {
			'POST': 'stats-box-post',
			'POST_LIKE': 'stats-box-post-like',
			'REPLY': 'stats-box-reply',
			'REPLY_LIKE': 'stats-box-reply-like'
		};

		const sinceText = period === 'month' ? 'Since last month' : 'Since last week';
		for (const key in statsMap) {
			if (stats.hasOwnProperty(key)) {
				const data = stats[key];
				const boxId = statsMap[key];


				const boxElement = document.getElementById(boxId);

				if (boxElement) {

					const countElement = boxElement.querySelector('.commOnceView-box-cnt');
					const rateElement = boxElement.querySelector('.commOnceView-rate span');
					const sinceElement = boxElement.querySelector('.public-span-since');

					if (countElement) {
						countElement.textContent = data.currentPeriod.toLocaleString();
					}
					if (rateElement) {
						updateRateUI(rateElement, data.growthStatus, data.growthRate);
					}
					if (sinceElement) {

						sinceElement.textContent = sinceText;
					}
				}
			}
		}
	}

	function displayTopJobs(jobsArray) {

		const container = document.getElementById('worldcupRn');

		if (!container) {
			console.error('ID가 "worldcupRn"인 요소를 찾을 수 없습니다.');
			return;
		}

		container.innerHTML = '';

		jobsArray.forEach(job => {
			const p = document.createElement('p');

			p.textContent = `${job.RN}. ${job.JOBNAME}`;

			container.appendChild(p);
		});
	}

	function displayStepPercentages(responseData) {
		const noStepElement = document.getElementById('roadmapStepCount-noStep');
		if (noStepElement) {
			const percentage = responseData.nonParticipatingPercentage;
			noStepElement.textContent = `${parseFloat(percentage).toFixed(1)}%`;
		}

		const apiNameToIdMap = {
			'1단계': 'roadmapStepCount-step1',
			'2단계': 'roadmapStepCount-step2',
			'3단계': 'roadmapStepCount-step3',
			'4단계': 'roadmapStepCount-step4',
			'완성': 'roadmapStepCount-complete'
		};

		const stepIds = [
			'roadmapStepCount-step1',
			'roadmapStepCount-step2',
			'roadmapStepCount-step3',
			'roadmapStepCount-step4',
			'roadmapStepCount-complete'
		];
		stepIds.forEach(id => {
			const element = document.getElementById(id);
			if (element) {
				element.textContent = '0%';
			}
		});

		const stepDistribution = responseData.stepDistribution;
		stepDistribution.forEach(stepData => {
			const stepName = stepData.stepName;
			const elementId = apiNameToIdMap[stepName];

			if (elementId) {
				const targetElement = document.getElementById(elementId);
				if (targetElement) {
					targetElement.textContent = `${parseFloat(stepData.percentage).toFixed(1)}%`;
				}
			}
		});
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})();
