package kr.or.ddit.mpg.mif.pswdchg.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import kr.or.ddit.mpg.mif.pswdchg.service.PasswordChangeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PasswordChangeServiceImpl implements PasswordChangeService{

	@Autowired
	MyInquiryService myInquiryService;
	
	@Autowired
	PasswordChangeMapper passwordChangeMapper;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 비밀번호 변경 페이지 진입 전 멤버의 정보를 확인합니다.
	 * 
	 * @param memIdStr
	 * @return
	 */
	@Override
	public MemberVO selectPasswordChangeView(String memIdStr) {
		int memId = myInquiryService.parseMemId(memIdStr);
		MemberVO member;
		try {
			member = this.passwordChangeMapper.selectPasswordChangeView(memId);
		} catch (CustomException e) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}
		
		return member;
	}
	
	/**
	 * 특정 멤버의 비밀번호를 변경합니다.
	 * 
	 * @param memId 멤버id
	 * @param map 기존비밀번호, 변경비밀번호
	 * @return 성공여부 map
	 */
	@Override
	public String updatePasswordChange(String memIdStr, Map<String, Object> map) {
		String oldPassword = (String) map.get("password");
		String newPassword = (String) map.get("newPassword");
		
		String checkResult = this.myInquiryService.checkPassword(memIdStr, oldPassword);
		
		if(!"success".equals(checkResult)) {
			return "현재 비밀번호가 일치하지 않습니다.";
		}
		if (newPassword.equals(oldPassword)) {
			return "현재 비밀번호와 동일한 비밀번호는 이용 할 수 없습니다."; 
		}
		
		int memId = this.myInquiryService.parseMemId(memIdStr);
		
		newPassword = bCryptPasswordEncoder.encode(newPassword);
		
		map.put("memId", memId);
		map.put("newPassword", newPassword);
		
		try {
			this.passwordChangeMapper.updatePasswordChange(map);
		} catch (CustomException e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		return "success";
	}

}
