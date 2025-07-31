package kr.or.ddit.mpg.mif.pswdchg.web;

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

import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.pswdchg.service.PasswordChangeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class PasswordChangeController {
	
	@Autowired
	PasswordChangeService passwordChangeService;

	/**
	 * 비밀번호 변경 페이지 진입 전 멤버의 정보를 확인합니다.
	 * 
	 * @param memId 멤버id
	 * @param model
	 * @return 이동 url
	 */
	@GetMapping("/mif/pswdchg/selectPasswordChangeView.do")
	public String selectPasswordChangeView (@AuthenticationPrincipal String memId, Model model) {
		try {
			MemberVO member = this.passwordChangeService.selectPasswordChangeView(memId);
			model.addAttribute("member", member);
		} catch (CustomException e) {
			return "redirect:/login";
		} catch (Exception e) {
			return "redirect:/";
		}
		return "mpg/mif/pswdchg/selectPasswordChangeView";
	}
	
	/**
	 * 특정 멤버의 비밀번호를 변경합니다.
	 * 
	 * @param memId 멤버id
	 * @param map 기존비밀번호, 변경비밀번호
	 * @return 성공여부 map
	 */
	@ResponseBody
	@PostMapping("/mif/pswdchg/updatePassword.do")
	public String updatePassword (@AuthenticationPrincipal String memId, @RequestBody Map<String, Object> map) {
		try {
			return this.passwordChangeService.updatePasswordChange(memId, map);
		} catch (CustomException e) {
			throw new CustomException(ErrorCode.INVALID_USER);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
