package kr.or.ddit.prg.act.sup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.prg.act.sup.service.ActivitySupportersService;

@Service
public class ActivitySupportersServiceImpl implements ActivitySupportersService{
	
	@Autowired
	ActivitySupportersMapper activitySupportersMapper;
	
}
