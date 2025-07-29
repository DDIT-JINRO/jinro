package kr.or.ddit.ertds.hgschl.web;

import java.util.List;

import org.springframework.http.HttpStatus;
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



@Controller
@RequestMapping("highSchool")
public class HighSchoolController {

	private final HighSchoolService highSchoolService;

	public HighSchoolController(HighSchoolService highSchoolService) {
		this.highSchoolService = highSchoolService;
	}

	// 1. 모든 고등학교 목록 조회 API (리스트 화면용)

	@GetMapping("/list")
	public String highSchoolListPage(Model model) {

		List<HighSchoolVO> highSchools = highSchoolService.getAllHighSchools();

		// JSP에서는 ${highSchools}로 이 데이터에 접근
		model.addAttribute("highSchools", highSchools);

		System.out.println("고등학교 개수: " + highSchools.size());

		return "highSchool/list";
	}

	// 고등학교 상세
	@GetMapping("/detail")
	public String highSchoolDetailPage(@RequestParam("hsId") int hsId, Model model) {
		HighSchoolVO highSchool = highSchoolService.getHighSchoolById(hsId); // DB에서 상세 정보를 조회

		model.addAttribute("highSchool", highSchool); // HighSchoolVO 객체 자체를 JSP로 전달
		return "highSchool/detail";
	}

	// 특정 고등학교 ID로 상세 정보를 JSON 형태로 반환하는 API 엔드포인트
	@GetMapping("/api/highschools/{hsId}") // 이 경로로 요청이 오면
	@ResponseBody // HighSchoolVO 객체를 JSON으로 변환하여 HTTP 응답 본문에 씀
	public ResponseEntity<HighSchoolVO> getHighSchoolByIdApi(@PathVariable("hsId") int hsId) {
		HighSchoolVO highSchool = highSchoolService.getHighSchoolById(hsId);
		
		return new ResponseEntity<>(highSchool, HttpStatus.OK); // 200 OK와 데이터 반환
	}

	/**
	 * 특정 고등학교의 위도, 경도 정보를 JSON 형태로 반환하는 API 엔드포인트입니다.
	 */
	@GetMapping("/api/highschools/search")
	@ResponseBody
	public ResponseEntity<List<HighSchoolVO>> searchHighSchoolCoordinatesApi(@RequestParam("name") String schoolName) {

		List<HighSchoolVO> schools = highSchoolService.getHighSchoolsByName(schoolName);

		return ResponseEntity.ok(schools);
	}
}
