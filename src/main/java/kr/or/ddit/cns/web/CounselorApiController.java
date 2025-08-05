package kr.or.ddit.cns.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cns")
public class CounselorApiController {


	@GetMapping("/counselList.do")
	public ResponseEntity<?> counselList(){
		return null;
	}

}
