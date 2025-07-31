package kr.or.ddit.mpg.mif.inq.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface MyInquiryMapper {

	MemberVO selectMyInquiryView(int memId);

}
