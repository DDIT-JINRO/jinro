package kr.or.ddit.rdm.service;

import java.util.List;
import java.util.Map;

public interface RoadmapService {

	// 특정 사용자의 로드맵 정보 조회
	public Map<String, Object> selectMemberRoadmap(int memId);

	// 로드맵 노드별 미션 리스트
	public List<RoadmapStepVO> selectMissionList();

	// 특정 사용자의 미션 완료 확인 메서드
	public String updateCompleteMission(String memId, int rsId);

}
