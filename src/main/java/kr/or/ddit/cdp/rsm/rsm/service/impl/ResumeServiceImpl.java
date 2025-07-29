package kr.or.ddit.cdp.rsm.rsm.service.impl;

import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

	private final ResumeMapper resumeMapper;
	
	@Override
	public String getElement(ResumeSectionVO resumeSectionVO) {
		// TODO Auto-generated method stub
		return resumeMapper.getElement(resumeSectionVO);
	}

}
