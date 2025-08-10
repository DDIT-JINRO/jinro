package kr.or.ddit.prg.act.vol.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.prg.act.service.ActivityVO;
import kr.or.ddit.prg.act.vol.service.ActivityVolunteerService;

@Service
public class ActivityVolunteerServiceImpl implements ActivityVolunteerService {

	@Autowired
	private ActivityVolunteerMapper activityVolunteerMapper;

	// 봉사활동 총 개수 조회
	@Override
	public int selectVolCount(ActivityVO activityVO) {
		if (activityVO.getContestGubunFilter() == null || activityVO.getContestGubunFilter().isEmpty()) {
			List<String> contestGubunFilter = new ArrayList<>();
			contestGubunFilter.add("G32003");
			activityVO.setContestGubunFilter(contestGubunFilter);
		}

		int totalCount = activityVolunteerMapper.selectVolCount(activityVO);
		return totalCount;
	}

	// 봉사활동 목록 조회
	@Override
	public List<ActivityVO> selectVolList(ActivityVO activityVO) {
		// 페이징 정보 계산 (startRow, endRow)
		int size = activityVO.getSize();
		int currentPage = activityVO.getCurrentPage();

		int startRow = (currentPage - 1) * size + 1;
		int endRow = currentPage * size;
		activityVO.setStartRow(startRow);
		activityVO.setEndRow(endRow);

		return activityVolunteerMapper.selectVolList(activityVO);
	}

	// 봉사활동 상세
	@Override
	@Transactional
	public ActivityVO selectVolDetail(String volId) {
		// 0. [추가] 조회수를 먼저 1 증가시킵니다.
		activityVolunteerMapper.updateVolViewCount(volId);

		// 1. DB에서 상세 정보 조회
		ActivityVO detail = activityVolunteerMapper.selectSupDetail(volId);

		if (detail != null && detail.getContestDescription() != null) {
			// 2. 상세 설명(contestDescription)을 '●' 기준으로 나누기
			String[] sections = detail.getContestDescription().split("●");

			// 3. 나눈 조각들을 List<String>으로 변환하여 새로운 필드에 저장
			List<String> sectionList = new ArrayList<>();
			for (String section : sections) {
				String trimmedSection = section.trim();
				if (!trimmedSection.isEmpty()) {
					sectionList.add(trimmedSection);
				}
			}
			detail.setDescriptionSections(sectionList);
		}

		return detail;
	}

}
