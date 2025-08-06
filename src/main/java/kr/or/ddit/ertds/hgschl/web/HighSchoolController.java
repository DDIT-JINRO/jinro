package kr.or.ddit.ertds.hgschl.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.ertds.hgschl.service.HighSchoolService;
import kr.or.ddit.ertds.hgschl.service.HighSchoolVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/ertds")
@Controller
@Slf4j
public class HighSchoolController {
	
	@Autowired
	private HighSchoolService highSchoolService;

	// 고등학교 리스트
	@GetMapping("/hgschl/selectHgschList.do")
	public String highSchoolListPage(@RequestParam(defaultValue = "1") int currentPage,
			@RequestParam(required = false) String keyword,
			@RequestParam(value = "regionFilter", required = false) List<String> regionFilter,
			@RequestParam(value = "schoolType", required = false) String schoolType,
			@RequestParam(value = "coedTypeFilter", required = false) String coedTypeFilter,
			@RequestParam(value = "departmentFilter", required = false) String departmentFilter,
			Model model) {
		log.info("regionFilter : " + regionFilter);
		log.info("schoolType : " + schoolType);
		log.info("coedTypeFilter : " + coedTypeFilter);
		log.info("departmentFilter : " + departmentFilter);
	
		int size = 5; // 한 페이지에 5개
		int startRow = (currentPage - 1) * size;
		int endRow = currentPage * size;

		if (regionFilter == null) {
			regionFilter = new ArrayList<>(); 
		}
		
		HighSchoolVO highSchoolVO = new HighSchoolVO();
		highSchoolVO.setKeyword(keyword);
		highSchoolVO.setRegionFilter(regionFilter);
		highSchoolVO.setSchoolType(schoolType);
		highSchoolVO.setCoedTypeFilter(coedTypeFilter);
		highSchoolVO.setDepartmentFilter(departmentFilter);
		highSchoolVO.setStartRow(startRow);
		highSchoolVO.setEndRow(endRow);
		
		int total = highSchoolService.selectHighSchoolCount(highSchoolVO);
		List<HighSchoolVO> schoolList = highSchoolService.highSchoolList(highSchoolVO);
		log.info("schoolList", schoolList);
		
		ArticlePage<HighSchoolVO> page = new ArticlePage<>(total, currentPage, size, schoolList, keyword);
		page.setUrl("/ertds/hgschl/selectHgschList.do");
		
		model.addAttribute("articlePage", page);
		model.addAttribute("checkedRegionFilter", regionFilter);
		model.addAttribute("checkedSchoolType", schoolType);
		model.addAttribute("checkedCoedTypeFilter", coedTypeFilter);
		model.addAttribute("checkedDepartmentFilter", departmentFilter);

		return "ertds/hgschl/HighSchoolList"; // /WEB-INF/views/erds/hgschl/list.jsp
	}

	// 고등학교 상세
	@GetMapping("/hgschl/selectHgschDetail.do")
	public String highSchoolDetailPage(@RequestParam("hsId") int hsId, Model model) {
		
		HighSchoolVO highSchool = highSchoolService.highSchoolDetail(hsId); // DB에서 상세 정보를 조회


		model.addAttribute("highSchool", highSchool); // HighSchoolVO 객체 자체를 JSP로 전달
		
		return "ertds/hgschl/HighSchoolDetail"; // /WEB-INF/views/erds/hgschl/detail.jsp
	}
	
	 //특정 고등학교 ID로 상세 정보를 JSON 형태로 반환하는 API 엔드포인트
    @GetMapping("/api/highschools/{hsId}") // 이 경로로 요청이 오면
    @ResponseBody // HighSchoolVO 객체를 JSON으로 변환하여 HTTP 응답 본문에 씀
    public ResponseEntity<HighSchoolVO> getHighSchoolByIdApi(@PathVariable("hsId") int hsId) {
        HighSchoolVO highSchool = highSchoolService.highSchoolDetail(hsId);
        if (highSchool == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<>(highSchool, HttpStatus.OK); // 200 OK와 데이터 반환
    }
    
   

}
