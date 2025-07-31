package kr.or.ddit.mpg.mif.inq.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
}
