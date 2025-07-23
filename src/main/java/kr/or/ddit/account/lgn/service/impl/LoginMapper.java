package kr.or.ddit.account.lgn.service.impl;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.account.lgn.service.MemDelVO;
import kr.or.ddit.account.lgn.service.MemberPenaltyVO;
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

	public MemberVO selectByEmailForKakao(MemberVO member);

	public int kakaoInsert(MemberVO member);

	public MemberVO selectMemberByEmailForKakao(String memEmail);

	public MemberVO selectByEmailForNaver(MemberVO member);

	public int naverInsert(MemberVO member);

	public MemberVO selectMemberByEmailForNaver(String memEmail);

	

	

	
	
}
