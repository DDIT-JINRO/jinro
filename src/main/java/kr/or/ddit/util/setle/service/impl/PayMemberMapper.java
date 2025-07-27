package kr.or.ddit.util.setle.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface PayMemberMapper {

	// 이메일을 기반으로 회원정보 조회
	public MemberVO selectMemberByEmail(String email);

}
