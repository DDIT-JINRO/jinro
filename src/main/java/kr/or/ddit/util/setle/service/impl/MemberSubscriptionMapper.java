package kr.or.ddit.util.setle.service.impl;

import java.util.List;

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


    // 오늘 결제해야 할 구독 목록 조회
    public List<MemberSubscriptionVO> findSubscriptionsDueForToday();
    
    // 정기결제 성공 후 구독 정보 업데이트
	public int updateAfterRecurringPayment(int msId);

	// 활성화된 구독 정보 조회
	public MemberSubscriptionVO findActiveSubscriptionByMemberId(int memId);

	// 구독 상태를 '취소'로 변경
	public int updateStatusToCancelled(int msId);
}
