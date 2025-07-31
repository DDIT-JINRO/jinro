package kr.or.ddit.cdp.rsm.rsm.service.impl;

import org.springframework.stereotype.Service;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeVO;
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

	@Override
	public ResumeVO mergeIntoResume(ResumeVO resumeVO) {
		//만약 resumeVO에 아이디가 있으면 update, 없으면 insert
		resumeVO.setResumeIsTemp("Y");
		if (resumeVO.getResumeId() == 0) { //id가 없을때 
		    int newResumeId = resumeMapper.selectNextResumeId();
		    resumeVO.setResumeId(newResumeId);
		}
		int result = resumeMapper.mergeIntoResume(resumeVO);
		
		resumeVO = resumeMapper.selectResumeByResumeId(resumeVO); //방금 isnert 또는 update한 ResumId로 가져옴
		
		return resumeVO;
	}

	@Override
	public ResumeVO selectResumeByResumeId(ResumeVO resumeVO) {
		// TODO Auto-generated method stub
		return resumeVO = resumeMapper.selectResumeByResumeId(resumeVO);
	}



}
