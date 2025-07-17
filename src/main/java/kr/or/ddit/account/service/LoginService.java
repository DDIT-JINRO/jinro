package kr.or.ddit.account.service;

import java.util.Map;

import kr.or.ddit.main.service.MemberVO;

public interface LoginService {

	Map<String, Object> loginProcess(MemberVO memVO);

}
