package kr.or.ddit.account.join.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface MemberJoinMapper {

	MemberVO selectUserEmail(String email);

	boolean isNicknameExists(String nickname);

}
