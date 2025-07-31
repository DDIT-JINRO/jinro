package kr.or.ddit.cdp.rsm.rsm.service;

public interface ResumeService {

	String getElement(ResumeSectionVO resumeSectionVO);

	ResumeVO mergeIntoResume(ResumeVO resumeVO);
	
	ResumeVO selectResumeByResumeId(ResumeVO resumeVO);

}
