package kr.or.ddit.highSchool.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.highSchool.service.HighSchoolService;
import kr.or.ddit.highSchool.service.HighSchoolVO;

@Controller
@RequestMapping("highSchool")
public class HighSchoolController {
	
	private final HighSchoolService highSchoolService;

    public HighSchoolController(HighSchoolService highSchoolService) {
        this.highSchoolService = highSchoolService;
        
    }
    
 // 1. 모든 고등학교 목록 조회 API (리스트 화면용)
    
    @GetMapping("/list")
    public String highSchoolListPage(Model model){
    	
    	List<HighSchoolVO> highSchools = highSchoolService.getAllHighSchools();
    	
    	// JSP에서는 ${highSchools}로 이 데이터에 접근
    	model.addAttribute("highSchools", highSchools);
    	
    	System.out.println("고등학교 개수: " + highSchools.size());
    	
    	return "highSchool/list";
    }
    
    //고등학교 상세 
    @GetMapping("/detail")
    public String highSchoolDetailPage(@RequestParam("hsId") Long hsId, Model model) {
        HighSchoolVO highSchool = highSchoolService.getHighSchoolById(hsId); // DB에서 상세 정보를 조회

        

        model.addAttribute("highSchool", highSchool); // HighSchoolVO 객체 자체를 JSP로 전달
        return "highSchool/detail";
    }
    
}
