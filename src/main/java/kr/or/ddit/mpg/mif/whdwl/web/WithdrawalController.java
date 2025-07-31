package kr.or.ddit.mpg.mif.whdwl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.whdwl.service.WithdrawalService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class WithdrawalController {

	@Autowired
	WithdrawalService withdrawalService;
	
	/**
	 * 페이지 이동 전 회원 탈퇴 분류 조회 후 이동
	 * 
	 * @param model
	 * @return url이동
	 */
	@GetMapping("/mif/whdwl/selectWithdrawalView.do")
	public String selectWithdrawalView (@AuthenticationPrincipal String memId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			 map = this.withdrawalService.selectMdcategoryList(memId);
		} catch (CustomException e) {
			return "redirect:/";
		}
		
		model.addAllAttributes(map);
		
		return "mpg/mif/whdwl/selectWithdrawalView";
	}
	
	/**
	 * 회원 탈퇴 신청
	 * 
	 * @param memId 멤버 id
	 * @param map 비밀번호, 탈퇴 카테고리, 탈퇴 사유
	 * @return 탈퇴 신청 여부
	 */
	@ResponseBody
	@PostMapping("/mif/whdwl/insertMemDelete.do")
	public String insertMemDelete (@AuthenticationPrincipal String memId, @RequestBody Map<String, Object> map) {
		
		String result = this.withdrawalService.insertMemDelete(memId, map);
		
		return result;
	}
	
}
