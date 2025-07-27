package kr.or.ddit.rdm.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.rdm.service.RoadmapService;
import kr.or.ddit.rdm.service.RoadmapStepVO;
import kr.or.ddit.rdm.service.RoadmapVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoadmapServiceImpl implements RoadmapService {

	private static final Set<String> ALLOWED_TABLE_NAMES = new HashSet<>(
			Arrays.asList("INTEREST_CN", "APTITUDE_TEST", "WORLDCUP", "BOOKMARK", "CHAT_MEMBER", "COUNSELING", "BOARD",
					"RESUME", "SELF_INTRO", "MOCK_INTERVIEW_HISTORY"));

	@Autowired
	RoadmapMapper roadmapMapper;

	// 특정 사용자의 로드맵 정보 조회
	@Override
	public Map<String, Object> selectMemberRoadmap(int memId) {
		boolean isFirst = false;

		if (this.roadmapMapper.selectMemberRoadmap(memId).isEmpty()) {
			this.roadmapMapper.insertMemberRoadmap(memId);
			isFirst = true;
		}
		// 현재 캐릭터 위치
		int currentCharPosition = this.roadmapMapper.selectCurrentCharPosition(memId);

		// 현재 받은 미션들
		List<RoadmapVO> progressMissions = this.roadmapMapper.selectProgressMissionList(memId);

		// 완료한 미션들
		List<RoadmapVO> completedMissions = this.roadmapMapper.selectCompletedMissionList(memId);

		return Map.of("currentCharPosition", currentCharPosition, "progressMissions", progressMissions,
				"completedMissions", completedMissions, "isFirst", isFirst);
	}

	// 로드맵 노드별 미션 리스트
	@Override
	public List<RoadmapStepVO> selectMissionList() {
		return this.roadmapMapper.selectMissionList();
	}

	// 특정 사용자의 미션 완료 확인 메서드
	@Override
	public String updateCompleteMission(String memId, int rsId) {
		String tableName = this.roadmapMapper.selectTableName(rsId);
		if (rsId == 11) {
			this.roadmapMapper.insertCompleteRoadmap(memId);
			return "complete";
		}
		
	    if (!ALLOWED_TABLE_NAMES.contains(tableName)) {
	        throw new IllegalArgumentException("허용되지 않은 테이블 이름입니다: " + tableName);
	    }

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tableName", tableName);
		parameter.put("memId", memId);
		parameter.put("rsId", rsId);

		int searchResult = this.roadmapMapper.isCompleteExists(parameter);

		if (searchResult <= 0)
			return "fail";

		int updateResult = this.roadmapMapper.updateCompleteMission(parameter);

		if (updateResult <= 0)
			return "fail";

		return "success";
	}

	// 특정 사용자의 미션 등록 메서드
	@Override
	public String insertMission(String memId, RoadmapVO request) {
		request.setMemId(Integer.parseInt(memId));

		int result = this.roadmapMapper.insertMission(request);
		
		if (result > 0) return "success";
		
		return "fail";
	}
	
	// 특정 사용자의 미션 완료 날짜 업데이트
	@Override
	public String updateDueDate(String memId, RoadmapVO request) {
		request.setMemId(Integer.parseInt(memId));
		
		int result = this.roadmapMapper.updateDueDate(request);
		
		if (result > 0) return "success";
		
		return "fail";
	}

	// 특정 사용자의 완료 로드맵 정보
	@Override
	public String selectResultData(String memId) {
		String memName = this.roadmapMapper.selectMember(memId).getMemName();
		
		return memName;
	}

}
