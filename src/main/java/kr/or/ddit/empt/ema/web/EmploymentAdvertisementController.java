package kr.or.ddit.empt.ema.web;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.empt.ema.service.EmploymentAdvertisementService;
import kr.or.ddit.empt.ema.service.HireVO;
import kr.or.ddit.empt.enp.service.CompanyVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/empt")
public class EmploymentAdvertisementController {
	
	private final EmploymentAdvertisementService employmentAdvertisementService;
	
	@GetMapping("/ema/employmentAdvertisement.do")
	public String emplymentAdvertisementList(
			@ModelAttribute HireVO hireVO,
			Model model,
			Principal principal
			) {
		return "empt/ema/employmentAdvertisement";
	}
	
}
