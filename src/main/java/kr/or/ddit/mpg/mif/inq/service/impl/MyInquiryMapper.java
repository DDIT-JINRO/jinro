package kr.or.ddit.mpg.mif.inq.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface MyInquiryMapper {

	MemberVO selectMyInquiryView(int memId);

	MemberVO checkPassword(int memId);

	int updateMyInquiryView(MemberVO member);

	int updateFileGroup(MemberVO member);

	List<ComCodeVO> selectInteretsKeywordList();

}
