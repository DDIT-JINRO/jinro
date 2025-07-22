package kr.or.ddit.rdm.service.impl;

import java.util.List;

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
	public List<RoadmapVO> selectMemberRoadmap(int memId) {
		if(this.roadmapMapper.selectMemberRoadmap(memId).isEmpty()) {
			insertMemberRoadmap(memId);
		}
		List<RoadmapVO> roadmap = this.roadmapMapper.selectMemberRoadmap(memId);
		return roadmap;
	}

	private int insertMemberRoadmap(int memId) {
		int result = this.roadmapMapper.insertMemberRoadmap(memId);
		return result;
	}
}
