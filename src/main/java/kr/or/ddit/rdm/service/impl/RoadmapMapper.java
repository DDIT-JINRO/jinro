package kr.or.ddit.rdm.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.rdm.service.RoadmapStepVO;
import kr.or.ddit.rdm.service.RoadmapVO;

@Mapper
public interface RoadmapMapper {

	// 특정 사용자의 로드맵있는지에 대한 여부 조회
	List<RoadmapVO> selectMemberRoadmap(int memId);

	// 로드맵이 없는 사용자의 첫 로드맵 생성
	int insertMemberRoadmap(int memId);
	
	// 특정 사용자의 로드맵의 현재 캐릭터의 위치 조회
	int selectCurrentCharPosition(int memId);

	// 특정 사용자의 진행 중인 미션 리스트 조회
	List<RoadmapVO> selectProgressMissionList(int memId);

	// 특정 사용자의 완료된 미션 리스트 조회
	List<RoadmapVO> selectCompletedMissionList(int memId);

	// 로드맵 노드별 미션 리스트
	List<RoadmapStepVO> selectMissionList();
	
	// 파라미터 단계에 해당하는 테이블명 조회
	String selectTableName(int rsId);
	
	// 미션 완료 확인 여부 조회
	int isCompleteExists(Map<String, Object> parameter);
	
	// 미션 완료 시 완료 여부 업데이트
	int updateCompleteMission(Map<String, Object> parameter);


}
