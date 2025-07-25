package kr.or.ddit.account.join.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.account.join.service.MemberJoinService;
import kr.or.ddit.main.service.MemberVO;

@Service
public class MemberJoinServiceImpl implements MemberJoinService{

	@Autowired
	MemberJoinMapper memberJoinMapper;
	
	@Override
	public MemberVO selectUserEmail(String email) {
		// TODO Auto-generated method stub
		return memberJoinMapper.selectUserEmail(email);
	}

	@Override
	public boolean isNicknameExists(String nickname) {
		String result = this.memberJoinMapper.isNicknameExists(nickname);
		
		boolean tf = true;
		
		if(result.equals("false")) {
			tf=false;
		}
		
		return tf;
	}

}
