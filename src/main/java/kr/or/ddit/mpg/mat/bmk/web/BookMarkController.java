package kr.or.ddit.mpg.mat.bmk.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mpg")
@Slf4j
public class BookMarkController {

	@GetMapping("/mat/bmk/selectBookMarkList.do")
	public String selectBookMarkList () {
		return "mpg/mat/bmk/selectBookMarkList";
	}
	
}
