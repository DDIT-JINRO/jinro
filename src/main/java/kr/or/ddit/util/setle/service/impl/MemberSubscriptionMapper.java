package kr.or.ddit.util.setle.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.util.setle.service.MemberSubscriptionVO;

@Mapper
public interface MemberSubscriptionMapper {
	
	// 구독 등록
	public int insertMemberSubscription(MemberSubscriptionVO memberSubscriptionVO);
	
	// 회원 ID로 구독 정보 조회
	public MemberSubscriptionVO selectByMemberId(int memId);

	// 구독번호로 구독 정보 조회
	public MemberSubscriptionVO selectByMsId(int msId);

}
