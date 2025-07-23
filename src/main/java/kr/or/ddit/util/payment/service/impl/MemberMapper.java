package kr.or.ddit.util.payment.service.impl;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.util.payment.service.MemberVO;

/**
 * MEMBER 테이블에 대한 데이터베이스 작업을 정의하는 매퍼 인터페이스입니다. 
 * MemberVO 객체를 사용하여 DB와 데이터를 주고받습니다.
 */
@Mapper
public interface MemberMapper {
	/**
	 * 회원 ID를 기반으로 MemberVO 정보를 조회합니다.
	 * 
	 * @param memId 회원 ID
	 * @return 조회된 MemberVO 객체 또는 null
	 */
	MemberVO selectMemberById(Long memId);

	// TODO: 필요하다면 다른 회원 관련 CRUD 메서드 추가 (예: 회원 정보 업데이트)
	// int updateMember(MemberVO memberVO);

}
