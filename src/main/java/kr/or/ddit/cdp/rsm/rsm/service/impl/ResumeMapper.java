package kr.or.ddit.cdp.rsm.rsm.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeDetailVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeVO;

@Mapper
public interface ResumeMapper {

	String getElement(ResumeSectionVO resumeSectionVO);

	int insertResumeDetail(ResumeDetailVO resumeDetailVO);

	ResumeDetailVO selectResumeDetailByResumeId(ResumeVO resumeVO);

}
