package kr.or.ddit.rdm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.rdm.service.RoadmapService;
import kr.or.ddit.rdm.service.RoadmapVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoadmapServiceImpl implements RoadmapService{

	@Autowired
	RoadmapMapper roadmapMapper;
	
	@Override
	public Map<String, Object> selectMemberRoadmap(int memId) {
		if(this.roadmapMapper.selectMemberRoadmap(memId).isEmpty()) {
			insertMemberRoadmap(memId);
		}
		// 현재 캐릭터 위치
		int currentCharPosition = this.roadmapMapper.selectCurrentCharPosition(memId);
		
		// 현재 받은 미션들
		List<RoadmapVO> progressMissions = this.roadmapMapper.selectProgressMissionList(memId);
		
		// 완료한 미션들
		List<RoadmapVO> completedMissions = this.roadmapMapper.selectCompletedMissionList(memId);
		
		return Map.of("currentCharPosition", currentCharPosition, "progressMissions", progressMissions, "completedMissions", completedMissions);
	}

	private int insertMemberRoadmap(int memId) {
		int result = this.roadmapMapper.insertMemberRoadmap(memId);
		return result;
	}

}
