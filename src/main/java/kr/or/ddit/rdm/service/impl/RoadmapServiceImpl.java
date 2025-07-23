package kr.or.ddit.rdm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.rdm.service.RoadmapService;
import kr.or.ddit.rdm.service.RoadmapStepVO;
import kr.or.ddit.rdm.service.RoadmapVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoadmapServiceImpl implements RoadmapService{

	@Autowired
	RoadmapMapper roadmapMapper;
	
	// 특정 사용자의 로드맵 정보 조회
	@Override
	public Map<String, Object> selectMemberRoadmap(int memId) {
		if(this.roadmapMapper.selectMemberRoadmap(memId).isEmpty()) {
			this.roadmapMapper.insertMemberRoadmap(memId);
		}
		// 현재 캐릭터 위치
		int currentCharPosition = this.roadmapMapper.selectCurrentCharPosition(memId);
		
		// 현재 받은 미션들
		List<RoadmapVO> progressMissions = this.roadmapMapper.selectProgressMissionList(memId);
		
		// 완료한 미션들
		List<RoadmapVO> completedMissions = this.roadmapMapper.selectCompletedMissionList(memId);
		
		return Map.of("currentCharPosition", currentCharPosition, "progressMissions", progressMissions, "completedMissions", completedMissions);
	}

	// 로드맵 노드별 미션 리스트
	@Override
	public List<RoadmapStepVO> selectMissionList() {
		return this.roadmapMapper.selectMissionList();
	}

	// 특정 사용자의 미션 완료 확인 메서드
	@Override
	public String updateCompleteMission(String memId, int rsId) {
		String result = "fail";
		String tableName = this.roadmapMapper.selectTableName(rsId);
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tableName", tableName);
		parameter.put("memId", memId);
		parameter.put("rsId", rsId);
		
		int searchResult = this.roadmapMapper.isCompleteExists(parameter);
		
		if(searchResult > 0) {
			int updateResult = this.roadmapMapper.updateCompleteMission(parameter);
			result = "complete";
		}
		
		return result;
	}



}
