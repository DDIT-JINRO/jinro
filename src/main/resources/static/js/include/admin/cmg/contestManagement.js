/**
 *
 */
function activityManagementInit () {
	function fetchActList(page = 1) {
		
		const pageSize = 10;
		const keyword = document.querySelector('input[name="keyword"]').value;
		
		const paramData = {
			currentPage: page,
			size: pageSize,
			keyword: keyword,
		}
		
		axios.get('/admin/cmg/selectCttList.do', {
			params: paramData
		}).then(({data}) => {
			const {articlePage, contestTypeList} = data;
			console.log(data);
			if(data.success) {
				let contestTypeHtml = '';
				contestTypeList.map(contestType => {
					contestTypeHtml += `<option value="${contestType.ccId}">${contestType.ccName}</option>`
				})
				document.getElementById('contestType').innerHTML = contestTypeHtml;
				
				const countEl = document.getElementById('actList-count');
				if (countEl) {
					countEl.textContent = parseInt(articlePage.total, 10).toLocaleString();
				}
					
				const listEl = document.getElementById('actList');
				if (!listEl) return;
				
				if (articlePage.content.length < 1 && keyword.trim() !== '') {
					listEl.innerHTML = `<tr><td colspan='2' style="text-align: center;">검색 결과를 찾을 수 없습니다.</td></tr>`;
				} else {
					const rows = articlePage.content.map(item =>{
						
						const formattedDate = formatDateOne(item.contestCreatedAt);
						
						return `<tr>
									<td>${item.contestId}</td>
									<td>${item.contestTitle}</td>
									<td>${formattedDate}</td>
									<td>${item.contestHost}</td>
								</tr>`;
						}).join('');
					listEl.innerHTML = rows;
				}
				renderPaginationAct(data.articlePage);
			}
		})
		.catch(err => console.error('목록 조회 중 에러:', err));
	}
	
	function renderPaginationAct({ startPage, endPage, currentPage, totalPages }) {
		let html = `<a href="#" data-page="${startPage - 1}" class="page-link ${startPage <= 1 ? 'disabled' : ''}">← Previous</a>`;

		for (let p = startPage; p <= endPage; p++) {
			html += `<a href="#" data-page="${p}" class="page-link ${p === currentPage ? 'active' : ''}">${p}</a>`;
		}

		html += `<a href="#" data-page="${endPage + 1}" class="page-link ${endPage >= totalPages ? 'disabled' : ''}">Next →</a>`;

		const footer = document.querySelector('.actListPage');
		if (footer) footer.innerHTML = html;
	}
	
	function actListPangingFn() {
		const actListPaginationContainer = document.querySelector('.panel-footer.pagination');
		if (actListPaginationContainer) {
			
			actListPaginationContainer.addEventListener('click', e => {
				e.preventDefault();
				const link = e.target.closest('a[data-page]');
				
				if (!link || link.parentElement.classList.contains('disabled')) {
					return;
				}

				const page = parseInt(link.dataset.page, 10);

				if (!isNaN(page) && page > 0) {
					fetchActList(page);
				}
			});
		}
	}

	document.getElementById("actList").addEventListener("click", function(e) {
		const tr = e.target.closest("tr");
		if (!tr) return;

		const tds = tr.querySelectorAll("td");
		const id = tds[0].textContent.trim();

		let formData = new FormData();
		formData.set("id", id);

		actDetail(formData);
	});

	function actDetail(formData) {
		axios.post('/admin/cmg/selectActDetail.do', formData)
			.then(({data}) => {
				const {success, activity} = data;
				
				if(success) {
					const formattedStartDate = formatDateOne(activity.contestStartDate);
					const formattedEndDate   = formatDateOne(activity.contestEndDate);
					
					// 우측 상세 내용 출력 시작
					document.getElementById('actImg').src                        = activity.savePath;
					document.getElementById('actName').innerHTML                 = activity.contestTitle
					document.getElementById('gubun').innerHTML                   = activity.contestGubunCode
					document.getElementById('gubunName').innerHTML               = activity.contestGubunName
					document.getElementById('contestHostProfile').innerHTML      = activity.contestHost
					document.getElementById('contestOrganizerProfile').innerHTML = activity.contestOrganizer == null ? '-' : activity.contestOrganizer == 'N/A' ? '-' : activity.contestOrganizer;
					document.getElementById('contestDate').innerHTML             = `${formattedStartDate} - ${formattedEndDate} `;
					document.getElementById('actDetailAbout').innerHTML          = `${activity.descriptionSections == null ? '활동 설명이 없습니다.' : activity.descriptionSections[0]}`;
					
					// 하단 상세 내용 출력 시작
					document.getElementById('contestId').value = activity.contestId
					
					// 이미지 출력 시작
					const actImgPreview = document.getElementById('actImgPreview');
					const imageUploadBox = document.getElementById('imageUploadBox');
					if (activity.savePath) {
						actImgPreview.src = activity.savePath;
						actImgPreview.style.display = 'block';
						if (imageUploadBox) {
							imageUploadBox.querySelector('i').style.display = 'none';
							imageUploadBox.querySelector('p').style.display = 'none';
						}
					} else {
						actImgPreview.src = '';
						actImgPreview.style.display = 'none';
						if (imageUploadBox) {
							imageUploadBox.querySelector('i').style.display = 'block';
							imageUploadBox.querySelector('p').style.display = 'block';
						}
					}
					// 이미지 출력 종료
					
					document.getElementById('contestTitle').value = activity.contestTitle
					
					const contestGubunSelect = document.getElementById('contestGubun');
					const gubunOptions = contestGubunSelect.options;
					const gubunSelectedValue = activity.contestGubunCode;
					for (let i = 0; i < gubunOptions.length; i++) {
						if (gubunOptions[i].value === gubunSelectedValue) {
							gubunOptions[i].selected = true;
							break;
						}
					}
					
					const contestTargetSelect = document.getElementById('contestTarget');
					const targetOptions = contestTargetSelect.options;
					const targetSelectedValue = activity.contestTarget;
					for (let i = 0; i < targetOptions.length; i++) {
						if (targetOptions[i].value === targetSelectedValue) {
							targetOptions[i].selected = true;
							break;
						}
					}
					document.getElementById('applicationMethod').value      = activity.applicationMethod == null ? '-' : activity.applicationMethod == 'N/A' ? '-' : activity.applicationMethod;
					document.getElementById('awardType').value              = activity.awardType;
					document.getElementById('contestUrl').value             = activity.contestUrl == null ? '-' : activity.contestUrl == 'N/A' ? '-' : activity.contestUrl;
					document.getElementById('contestHost').value            = activity.contestHost == null ? '-' : activity.contestHost == 'N/A' ? '-' : activity.contestHost;
					document.getElementById('contestSponsor').value         = activity.contestSponsor == null ? '-' : activity.contestSponsor == 'N/A' ? '-' : activity.contestSponsor;
					document.getElementById('contestOrganizer').value       = activity.contestOrganizer == null ? '-' : activity.contestOrganizer == 'N/A' ? '-' : activity.contestOrganizer;
					document.getElementById('contestStartDate').value       = formatDateTwo(activity.contestStartDate);
					document.getElementById('contestEndDate').value         = formatDateTwo(activity.contestEndDate);
					document.getElementById('contestDescription').innerHTML = activity.contestDescription;
				}
			})
			.catch(error => {
				console.error('활동 정보 불러오기 실패', error);
			});
	}

	document.getElementById('btnSearch').addEventListener("click", function() {
		window.currentPage = 1;
		fetchActList(1);
	});

	function imgView() {
		const actImgFile     = document.getElementById('actImgFile');
		const actImgPreview  = document.getElementById('actImgPreview');
		const btnChangeImg   = document.getElementById('btnChangeImg');
		const imageUploadBox = document.getElementById('imageUploadBox');

		actImgFile.addEventListener('change', (event) => {
			const file = event.target.files[0];
			if (file) {
				const reader = new FileReader();

				reader.onload = (e) => {
					actImgPreview.src = e.target.result;
					actImgPreview.style.display = 'block';
					imageUploadBox.querySelector('i').style.display = 'none';
					imageUploadBox.querySelector('p').style.display = 'none';
				};
				reader.readAsDataURL(file);
			}
		});

		btnChangeImg.addEventListener('click', () => {
			actImgFile.click();
		});
	}
	
	function actSave() {
		const saveBtn = document.getElementById('btnRegister');

		saveBtn.addEventListener('click', async function() {
			const contestId          = document.getElementById('contestId').value.trim();
			const contestTitle       = document.getElementById('contestTitle').value.trim();
			const contestGubun       = document.getElementById('contestGubun').value;
			const contestDescription = document.getElementById('contestDescription').value.trim();
			const contestType        = document.getElementById('contestType').value;
			const contestTarget      = document.getElementById('contestTarget').value;
			const contestStartDate   = document.getElementById('contestStartDate').value;
			const contestEndDate     = document.getElementById('contestEndDate').value;
			const contestHost        = document.getElementById('contestHost').value.trim();
			const contestOrganizer   = document.getElementById('contestOrganizer').value.trim();
			const contestSponsor     = document.getElementById('contestSponsor').value.trim();
			const applicationMethod  = document.getElementById('applicationMethod').value.trim();
			const awardType          = document.getElementById('awardType').value.trim();
			const contestUrl         = document.getElementById('contestUrl').value.trim();

			const form = new FormData();

			if (contestId && contestId !== '-') {
				form.append("contestId", contestId);
			}

			form.append("contestTitle", contestTitle);
			form.append("contestGubun", contestGubun);
			form.append("contestDescription", contestDescription);
			form.append("contestType", contestType);
			form.append("contestTarget", contestTarget);
			form.append("contestStartDate", contestStartDate);
			form.append("contestEndDate", contestEndDate);
			form.append("contestHost", contestHost);
			form.append("contestOrganizer", contestOrganizer);
			form.append("contestSponsor", contestSponsor);
			form.append("applicationMethod", applicationMethod);
			form.append("awardType", awardType);
			form.append("contestUrl", contestUrl);

			const actImgFile         = document.getElementById('actImgFile').files[0]
			form.append("file", actImgFile);
			
			console.log("--- 전송될 FormData 데이터 ---");
			for (const [key, value] of form.entries()) {
			    console.log(`${key}:`, value);
			}
			
			try {
		        const response = await fetch("/prg/ctt/contestUpdate.do", {
		            method: "POST",
		            body: form
		        });

		        if (response.ok) {
		            const result = await response.json();
		            
		            if (result.success) {
		                alert("후기 등록 요청이 완료되었습니다");
		                window.location.href = "/empt/ivfb/interViewFeedback.do";
		            } else {
		                alert(result.message || "등록에 실패했습니다.");
		            }
		        } else {
		            throw new Error(`서버 응답 오류: ${response.status}`);
		        }
		    } catch (error) {
		        console.error("등록 중 오류:", error);
		        alert("등록에 실패했습니다.");
		    }
		/*	
			fetch('/prg/ctt/contestUpdate.do', {
				method: "POST",
				body: form
			}).then(res => {
				return res.json();
			}).then(result => {
				console.log(result);
			}).catch(err => {
				console.error("저장 실패", err);

			});*/
		});
	}
	
	function actDelete() {
		const deleteBtn = document.getElementById('btnDelete');

		deleteBtn.addEventListener('click', function() {
			const contestId = document.getElementById('contestId').value.trim();
			
			const data = {
				contestId : contestId
			}

			axios.post('/prg/ctt/contestDelete.do', data).then(res => {
				alert('삭제 완료');
				fetchActList();
			}).catch(err => {
				console.error("삭제 실패", err);
			});

		});
	}

	function resetForm() {
		const resetButton = document.getElementById('btnReset');
		resetButton.addEventListener('click', function() {
			// 텍스트, 숫자, URL 입력 필드 초기화
			document.getElementById('contestTitle').value = '';
			document.getElementById('contestGubun').selectedIndex = 0;
			document.getElementById('contestStartDate').value = '';
			document.getElementById('contestEndDate').value = '';
			document.getElementById('contestDescription').value = '';
			document.getElementById('contestType').selectedIndex = 0;
			document.getElementById('contestTarget').selectedIndex = 0;
			document.getElementById('contestHost').value = '';
			document.getElementById('contestOrganizer').value = '';
			document.getElementById('contestSponsor').value = '';
			document.getElementById('applicationMethod').value = '';
			document.getElementById('awardType').value = '';
			document.getElementById('contestUrl').value = '';

			/*
			const cpLogoFile = document.getElementById('cpLogoFile');
			if (cpLogoFile) {
				cpLogoFile.value = ''; // 파일 선택 내용 삭제
			}*/
			
			// 이미지 미리보기 초기화
			const actImgPreview = document.getElementById('actImgPreview');
			const imageUploadBox = document.getElementById('imageUploadBox');

			actImgPreview.src = '';
			actImgPreview.style.display = 'none';

			// 업로드 안내 텍스트와 아이콘 다시 표시
			if (imageUploadBox) {
				const icon = imageUploadBox.querySelector('i');
				const text = imageUploadBox.querySelector('p');
				if (icon) icon.style.display = 'block';
				if (text) text.style.display = 'block';
			}

			// 변경/삭제 버튼 숨기기 (만약 있다면)
			const imageUploadControls = document.querySelector('.image-upload-controls');
			if (imageUploadControls) {
				imageUploadControls.style.display = 'none';
			}
		});
	}

	const formatDateOne = (date) => {
		return date.substring(0, 10).replaceAll('-', '. ');
	}
	
	const formatDateTwo = (date) => {
		return date.substring(0, 10);
	}
	
	resetForm();
	actSave();
	actDelete();
	imgView();
	actListPangingFn();
	fetchActList();
}


activityManagementInit();