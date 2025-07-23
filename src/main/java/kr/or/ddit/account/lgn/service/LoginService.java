package kr.or.ddit.account.lgn.service;

import java.util.Map;


import kr.or.ddit.main.service.MemberVO;

public interface LoginService {

	public Map<String, Object> loginProcess(MemberVO memVO);

	public MemberVO getRefreshToken(String refreshToken);

	public MemberVO selectById(int userId);

	public Map<String, Object> kakaoLgnProcess(MemberVO member);



}
