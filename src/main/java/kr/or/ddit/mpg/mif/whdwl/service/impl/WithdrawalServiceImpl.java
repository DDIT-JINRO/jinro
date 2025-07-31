package kr.or.ddit.mpg.mif.whdwl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.account.lgn.service.MemDelVO;
import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import kr.or.ddit.mpg.mif.pswdchg.service.PasswordChangeService;
import kr.or.ddit.mpg.mif.whdwl.service.WithdrawalService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WithdrawalServiceImpl implements WithdrawalService {

	@Autowired
	MyInquiryService myInquiryService;

	@Autowired
	WithdrawalMapper withdrawalMapper;

	@Autowired
	PasswordChangeService passwordChangeService;

	@Override
	public Map<String, Object> selectMdcategoryList(String memIdStr) {
		int memId = this.myInquiryService.parseMemId(memIdStr);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<ComCodeVO> mdCategoryList = this.withdrawalMapper.selectMdcategoryList();
		
		MemberVO member = this.withdrawalMapper.selectDelYN(memId);
		
		map.put("mdCategoryList", mdCategoryList);
		
		if ("Y".equals(member.getDelYn())) {
			MemDelVO memDel = this.withdrawalMapper.selectMemDel(memId);
			map.put("mdDeletedAt", memDel.getMdDeletedAt());
		}

		return map;
	}

	@Override
	public String insertMemDelete(String memIdStr, Map<String, Object> map) {
		String password = (String) map.get("password");

		if (!"success".equals(this.myInquiryService.checkPassword(memIdStr, password))) {
			return "비밀번호가 일치하지 않습니다.";
		}

		MemberVO member = this.passwordChangeService.selectPasswordChangeView(memIdStr);
		

		MemDelVO memDel = new MemDelVO();

		memDel.setMemId(member.getMemId());
		memDel.setMemEmail(member.getMemEmail());
		memDel.setMdCategory((String) map.get("category"));
		memDel.setMdReason((String) map.get("reason"));

		int insertResult = this.withdrawalMapper.insertMemDelete(memDel);
		int updateResult = this.withdrawalMapper.updateMemDelYN(member.getMemId());

		if (insertResult < 1 || updateResult < 1) {
			return "회원 탈퇴 중 오류가 발생했습니다.";
		}

		return "success";
	}

}
