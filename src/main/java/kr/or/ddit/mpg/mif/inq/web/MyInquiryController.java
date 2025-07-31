package kr.or.ddit.mpg.mif.inq.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.exception.CustomException;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class MyInquiryController {

	@Autowired
	MyInquiryService myInquiryService;

	/**
	 * 마이페이지 진입 전 유저의 정보를 확인합니다.
	 * 
	 * @param memId 유저id
	 * @param model 모델
	 * @return url 페이지 이동
	 */
	@GetMapping("/mif/inq/selectMyInquiryView.do")
	public String selectMyInquiryView(@AuthenticationPrincipal String memId, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = this.myInquiryService.selectMyInquiryView(memId);
			model.addAttribute("member", map.get("member"));
			model.addAttribute("imgPath", map.get("imgPath"));
		} catch(CustomException e) {
			return "redirect:/login";
		} catch(Exception e) {
            return "redirect:/";
		}

		return "mpg/mif/inq/selectMyInquiryView";
	}
	
	/**
	 * 
	 * @param memId 유저id
	 * @param map 확인용 비밀번호
	 * @return 일치 여부
	 */
	@ResponseBody
	@PostMapping("/mif/inq/checkPassword.do")
	public ResponseEntity<Map<String, Object>> checkPassword(@AuthenticationPrincipal String memId, @RequestBody Map<String, String> map) {
		Map<String, Object> result = this.myInquiryService.checkPassword(memId, map);

		return ResponseEntity.ok(result);
	}

	/**
	 * form데이터로 받은 변경 내용을 업데이트합니다.
	 * 
	 * @param memId  유저id
	 * @param member 변경 내용
	 * @return result 결과값
	 */
	@PostMapping("/mif/inq/updateMyInquiryDetail.do")
	public String updateMyInquiryView(@AuthenticationPrincipal String memId, MemberVO member) {
		try {
			this.myInquiryService.updateMyInquiryView(memId, member);
			return "redirect:/mpg/mif/inq/selectMyInquiryView.do";
		} catch(Exception e) {
            return "redirect:/";
		}
	}
}
