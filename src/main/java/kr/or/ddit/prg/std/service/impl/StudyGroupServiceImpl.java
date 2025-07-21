package kr.or.ddit.prg.std.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.prg.std.service.StudyGroupService;

@Service
public class StudyGroupServiceImpl implements StudyGroupService{
	
	@Autowired
	StudyGroupMapper studyGroupMapper;
	
}
