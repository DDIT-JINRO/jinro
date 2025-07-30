package kr.or.ddit.cdp.rsm.rsm.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.cdp.rsm.rsm.service.ResumeDetailVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeSectionVO;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeService;
import kr.or.ddit.cdp.rsm.rsm.service.ResumeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@RequestMapping("/rsm/rsm")
@Controller
@Slf4j
public class ResumeController {
	
	private final ResumeService resumeService;

	@GetMapping()
	public String resumePage(@ModelAttribute ResumeVO resumeVO,Model model) {
		log.info("resumeVO",resumeVO);
		
		ResumeDetailVO resumeDetailVO =  resumeService.selectResumeDetailByResumeId(resumeVO);
		
		model.addAttribute("resumeDetailVO",resumeDetailVO);
		
		return "cdp/rsm/rsm/resume";
	}
	
	@GetMapping("/detail.do")
	public String resumedeatilPage() {
		return "cdp/rsm/rsm/resumeDetail";
	}
	
	@GetMapping("/getElement")
	public ResponseEntity<String>getElement(@ModelAttribute  ResumeSectionVO resumeSectionVO){
		log.info("resumeSectionVO "+resumeSectionVO);
		
		String element = resumeService.getElement(resumeSectionVO);
		
		return ResponseEntity.ok(element);
		
	}
	
	@PostMapping("/insertElement")
	public ResponseEntity<String>insertElement(@RequestBody String html) throws UnsupportedEncodingException{
		 try {
	            // URL 디코딩
	            String decodedHtml = URLDecoder.decode(html, StandardCharsets.UTF_8.name());
	            
	            // 디코딩된 HTML 처리 (예: DB에 저장)
	            //System.out.println("디코딩된 HTML: " + decodedHtml);
	            
	            ResumeDetailVO resumeDetailVO = new ResumeDetailVO();
	            resumeDetailVO.setFieldValue(decodedHtml);
	            
	            // DB 저장 로직 추가
	            int cnt = resumeService.insertResumeDetail(resumeDetailVO);
	            
	            
	            return ResponseEntity.ok("HTML 데이터가 성공적으로 처리되었습니다!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.ok("오류 발생: " + e.getMessage());
	        }
	}
	
	
	
}