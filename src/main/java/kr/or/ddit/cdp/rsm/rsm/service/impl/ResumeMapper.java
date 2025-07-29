package kr.or.ddit.cdp.rsm.rsm.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;

@Mapper
public interface ResumeMapper {

	String getElement(ResumeSectionVO resumeSectionVO);

}
