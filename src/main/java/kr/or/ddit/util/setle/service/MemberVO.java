package kr.or.ddit.util.setle.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * USERS 또는 회원 관련 테이블과 매핑되는 엔티티 클래스입니다.
 * 결제/구독 기능에 필요한 사용자 정보를 담고 있습니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private Long memId; // 회원 고유 ID (PK)
    
}
