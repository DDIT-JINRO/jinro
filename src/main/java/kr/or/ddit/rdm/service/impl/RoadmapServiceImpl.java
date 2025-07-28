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

/**
 * {@link RoadmapService} 인터페이스의 구현체. 로드맵 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Slf4j
public class RoadmapServiceImpl implements RoadmapService {

	/**
	 * 미션 완료 여부 확인 시 허용되는 테이블 이름 목록.
	 * 이 목록에 없는 테이블 이름은 보안상 허용되지 않습니다.
	 */
	private static final Set<String> ALLOWED_TABLE_NAMES = new HashSet<>(
			Arrays.asList("INTEREST_CN", "APTITUDE_TEST", "WORLDCUP", "BOOKMARK", "CHAT_MEMBER", "COUNSELING", "BOARD",
					"RESUME", "SELF_INTRO", "MOCK_INTERVIEW_HISTORY"));

	@Autowired
	RoadmapMapper roadmapMapper;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 각 진행 중인 미션에 대해 현재 완료 가능한 상태인지(isComplete) 확인하는 로직을 포함
	 * </p>
	 */
	@Override
	public Map<String, Object> selectMemberRoadmap(int memId) {
		boolean isFirst = false;

		// 초기 로드맵 생성
		if (this.roadmapMapper.selectMemberRoadmap(memId).isEmpty()) {
			this.roadmapMapper.insertMemberRoadmap(memId);
			isFirst = true;
		}
		
		// 현재 캐릭터 위치
		int currentCharPosition = this.roadmapMapper.selectCurrentCharPosition(memId);

		// 현재 받은 미션들
		List<RoadmapVO> progressMissions = this.roadmapMapper.selectProgressMissionList(memId);

		// 각 진행 중인 미션에 대해 완료 가능 여부를 조회 및 설정
		for(RoadmapVO mission : progressMissions) {
			int rsId = mission.getRsId();
			
			String tableName = this.roadmapMapper.selectTableName(rsId);
			if (tableName != null && ALLOWED_TABLE_NAMES.contains(tableName)) {
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("tableName", tableName);
				parameter.put("memId", memId);
				parameter.put("rsId", rsId);
				
				int result = this.roadmapMapper.isCompleteExists(parameter);
				
				if(result > 0) mission.setComplete(true);
			} else {
				mission.setComplete(false);
			}
		}
		
		// 완료한 미션들
		List<RoadmapVO> completedMissions = this.roadmapMapper.selectCompletedMissionList(memId);

		return Map.of("currentCharPosition", currentCharPosition, "progressMissions", progressMissions,
				"completedMissions", completedMissions, "isFirst", isFirst);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RoadmapStepVO> selectMissionList() {
		return this.roadmapMapper.selectMissionList();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * rsId가 11인 경우 로드맵 전체 완료 처리를 수행
	 * </p>
	 */
	@Override
	public String updateCompleteMission(String memId, int rsId) {
		int point;
		String tableName = this.roadmapMapper.selectTableName(rsId);
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("memId", memId);
		
		
		if (rsId == 11) {
			point = 10;
			parameter.put("point", point);
			this.roadmapMapper.insertCompleteRoadmap(memId);
			this.roadmapMapper.updateUserPoint(parameter);
			return "complete";
		}

		if (!ALLOWED_TABLE_NAMES.contains(tableName)) {
			throw new IllegalArgumentException("허용되지 않은 테이블 이름입니다: " + tableName);
		}

		parameter.put("tableName", tableName);
		parameter.put("rsId", rsId);

		int searchResult = this.roadmapMapper.isCompleteExists(parameter);

		if (searchResult <= 0)
			return "fail";

		int updateResult = this.roadmapMapper.updateCompleteMission(parameter);

		if (updateResult <= 0)
			return "fail";
		
		point = 1;
		parameter.put("point", point);
		int result = this.roadmapMapper.updateUserPoint(parameter);
		
		if (result <= 0)
			return "fail";

		return "success";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String insertMission(String memId, RoadmapVO request) {
		request.setMemId(Integer.parseInt(memId));

		int result = this.roadmapMapper.insertMission(request);

		if (result > 0)
			return "success";

		return "fail";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateDueDate(String memId, RoadmapVO request) {
		request.setMemId(Integer.parseInt(memId));

		int result = this.roadmapMapper.updateDueDate(request);

		if (result > 0)
			return "success";

		return "fail";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String selectResultData(String memId) {
		String memName = this.roadmapMapper.selectMember(memId).getMemName();

		return memName;
	}

}
