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

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.hgschl.service.HighSchoolDeptVO;
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
			@RequestParam(value = "schoolType", required = false) List<String> schoolType,
			@RequestParam(value = "coedTypeFilter", required = false) List<String> coedTypeFilter,
			Model model) {
		log.info("regionFilter : " + regionFilter);
		log.info("schoolType : " + schoolType);
		log.info("coedTypeFilter : " + coedTypeFilter);
	
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
		highSchoolVO.setStartRow(startRow);
		highSchoolVO.setEndRow(endRow);
		
		List<ComCodeVO> regionList = highSchoolService.selectRegionList();
	    List<ComCodeVO> schoolTypeList = highSchoolService.selectSchoolTypeList();
	    List<ComCodeVO> coedTypeList = highSchoolService.selectCoedTypeList();
	    
	    model.addAttribute("regionList", regionList);
	    model.addAttribute("schoolTypeList", schoolTypeList);
	    model.addAttribute("coedTypeList", coedTypeList);
		
		int total = highSchoolService.selectHighSchoolCount(highSchoolVO);
		List<HighSchoolVO> schoolList = highSchoolService.highSchoolList(highSchoolVO);
		log.info("schoolList", schoolList);
		
		ArticlePage<HighSchoolVO> page = new ArticlePage<>(total, currentPage, size, schoolList, keyword);
		page.setUrl("/ertds/hgschl/selectHgschList.do");
		
		model.addAttribute("articlePage", page);
		model.addAttribute("checkedRegionFilter", highSchoolVO);

		return "ertds/hgschl/HighSchoolList"; // /WEB-INF/views/erds/hgschl/list.jsp
	}

	// 고등학교 상세
	@GetMapping("/hgschl/selectHgschDetail.do")
	public String highSchoolDetailPage(@RequestParam("hsId") int hsId, Model model) {
		
		// 1. 기존 학교 상세 정보 조회
		HighSchoolVO highSchool = highSchoolService.highSchoolDetail(hsId); // DB에서 상세 정보를 조회

	    // 2. 해당 학교의 학과 목록 조회
	    List<HighSchoolDeptVO> deptList = highSchoolService.selectDeptsBySchoolId(hsId);

	    // 3. 모델에 두 가지 정보를 모두 담아서 전달
		model.addAttribute("highSchool", highSchool); // HighSchoolVO 객체 자체를 JSP로 전달
		model.addAttribute("deptList", deptList);
		
		return "ertds/hgschl/HighSchoolDetail"; // /WEB-INF/views/erds/hgschl/detail.jsp
	}

}
