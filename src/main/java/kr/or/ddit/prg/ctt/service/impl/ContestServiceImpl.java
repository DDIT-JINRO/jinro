package kr.or.ddit.prg.ctt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.prg.ctt.service.ContestService;

@Service
public class ContestServiceImpl implements ContestService{
	
	@Autowired
	ContestMapper contestMapper;
	
}
