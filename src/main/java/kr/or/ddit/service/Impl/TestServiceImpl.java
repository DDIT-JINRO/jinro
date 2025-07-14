package kr.or.ddit.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.TestMapper;
import kr.or.ddit.service.TestService;
import kr.or.ddit.vo.TestVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestServiceImpl implements TestService {
	
	@Autowired
	TestMapper testMapper;
	
	@Override
	public List<TestVO> test() {
		// TODO Auto-generated method stub
		log.info("TsetService -> ok");
		return testMapper.test();
	}

}
