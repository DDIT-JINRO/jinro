package kr.or.ddit.account.join.service;

import kr.or.ddit.main.service.MemberVO;

public interface MemberJoinService {

	MemberVO selectUserEmail(String email);

	boolean isNicknameExists(String nickname);

}
