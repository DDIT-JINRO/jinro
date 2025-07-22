package kr.or.ddit.rdm.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.rdm.service.RoadmapVO;

@Mapper
public interface RoadmapMapper {

	List<RoadmapVO> selectMemberRoadmap(int memId);

	int insertMemberRoadmap(int memId);

	int selectCurrentStage(int memId);

}
