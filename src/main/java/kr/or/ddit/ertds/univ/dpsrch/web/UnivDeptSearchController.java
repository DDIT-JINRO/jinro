package kr.or.ddit.ertds.univ.dpsrch.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptCompareVO;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptDetailVO;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptService;
import kr.or.ddit.ertds.univ.dpsrch.service.UnivDeptVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/ertds")
public class UnivDeptSearchController {
	
	@Autowired
	UnivDeptService univDeptService;

	// 중분류 학과정보로 이동
	@GetMapping("univ/dpsrch/selectDeptList.do")
	public String selectDeptList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam(required = false, defaultValue = "5") int size,
			UnivDeptVO univDeptVO,
			Model model,
			Principal principal) {
		if (univDeptVO != null && univDeptVO.getSize() == 0) univDeptVO.setSize(size);
		if (univDeptVO != null && univDeptVO.getCurrentPage() == 0) univDeptVO.setCurrentPage(currentPage);		
		
		List<UnivDeptVO> list = this.univDeptService.selectUnivDeptList(univDeptVO);
		int totalCount = this.univDeptService.selectUniversityTotalCount(univDeptVO);
		
		List<ComCodeVO> lClass = this.univDeptService.selectCodeVOUnivDeptLClassList();
		
		ArticlePage<UnivDeptVO> articlePage = new ArticlePage<>(totalCount, currentPage, size, list, keyword);
		List<BookMarkVO> bookMarkVOList = new ArrayList<>();
		
		if(principal!=null && !principal.getName().equals("anonymousUser")) { 
			int	memId = Integer.parseInt(principal.getName()); BookMarkVO bookMarkVO = new
			BookMarkVO(); bookMarkVO.setMemId(memId);
			bookMarkVO.setBmCategoryId("G03005");
			
			bookMarkVOList = this.univDeptService.selectBookMarkVO(bookMarkVO);
			log.info("bookMarkVOList"+bookMarkVOList); 
		}
		
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("bookMarkVOList", bookMarkVOList);
		model.addAttribute("lClass", lClass);

		return "ertds/univ/dpsrch/selectDeptList";
	}
	
	// 대학비교
	@GetMapping("/univ/dpsrch/selectCompare.do")
	public String selectCompare(
			@RequestParam(value = "uddIds", required = false) List<Integer> uddIdList,
            Model model) {
        
	    log.info("학과 비교 페이지 진입 - 학과 IDs: {}", uddIdList);
        
        // URL 파라미터로 학과 ID가 전달된 경우 미리 조회
	    if (uddIdList != null && !uddIdList.isEmpty()) {
	        try {
	            List<UnivDeptCompareVO> compareList = univDeptService.getDeptCompareList(uddIdList);
	            model.addAttribute("compareList", compareList);
	            model.addAttribute("preloadedDepts", uddIdList);  // ✅ 변경

	        } catch (Exception e) {
	            log.error("학과 비교 데이터 조회 오류: {}", uddIdList, e);
	        }
	    }
		return "ertds/univ/dpsrch/selectCompare"; // /WEB-INF/views/erds/univ/compare.jsp
	}

	// 대학
	@GetMapping("/univ/dpsrch/selectDetail.do")
	public String selectDetail(
			@RequestParam int uddId, 
			Model model,
			Principal principal) {
		UnivDeptDetailVO deptDetail = this.univDeptService.selectDeptDetail(uddId);
		List<BookMarkVO> bookMarkVOList = new ArrayList<>();
				
		if(principal!=null && !principal.getName().equals("anonymousUser")) { 
			int	memId = Integer.parseInt(principal.getName()); BookMarkVO bookMarkVO = new
			BookMarkVO(); bookMarkVO.setMemId(memId);
			bookMarkVO.setBmCategoryId("G03005");
			
			bookMarkVOList = this.univDeptService.selectBookMarkVO(bookMarkVO);
			log.info("bookMarkVOList"+bookMarkVOList); 
		}
		
		model.addAttribute("deptDetail", deptDetail);
		model.addAttribute("bookMarkVOList", bookMarkVOList);
		return "ertds/univ/dpsrch/selectDetail"; // /WEB-INF/views/erds/univ/detail.jsp
	}

}
