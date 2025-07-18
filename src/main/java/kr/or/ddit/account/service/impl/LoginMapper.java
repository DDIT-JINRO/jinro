package kr.or.ddit.account.service.impl;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.account.service.MemDelVO;
import kr.or.ddit.account.service.MemberPenaltyVO;
import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface LoginMapper {
	
	public MemberVO selectMemberByEmail(String inputEmail);

	public MemDelVO selectMemDelStat(int memId);

	public MemberPenaltyVO selectMemPnt(int memId);

	public int memTokenInsert(Map<String, Object> paramMap);

	public MemberVO selectAuthByMemId(int intMemId);

	public MemberVO selectById(int intMemId);

	public MemberVO getRefreshToken(String refreshToken);

	

	
	
}
