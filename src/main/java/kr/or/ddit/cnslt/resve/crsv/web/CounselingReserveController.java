package kr.or.ddit.cnslt.resve.crsv.web;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cnslt/resve")
public class CounselingReserveController {
	
	private final CounselingReserveService counselingReserveService;
	
	
	@GetMapping("/crsv/reservation.do")
	public String counselingReservation() {
		log.info("asdasd");
		return "cnslt/resve/crsv/counselingreserve";
	}
	
	/**
     * 특정 상담사의 특정 날짜 예약 가능 시간 목록을 반환하는 API
     * (프론트엔드의 캘린더에서 날짜 선택 시 호출)
     * GET 요청: /cnslt/resve/available-times?counselorId=123&date=2025-08-05
     */
    @GetMapping("/availableTimes")
    @ResponseBody // JSON 데이터를 반환
    public ResponseEntity<List<String>> getAvailableTimes(@ModelAttribute CounselingVO counselingVO) {
    	
    	int counselId = counselingVO.getCounsel();
    	Date counselReqDate = counselingVO.getCounselReqDate();
        log.info("예약 가능 시간 조회 요청 - 상담사 ID: {}, 날짜: {}", counselId, counselReqDate);
        
        try {
            // 서비스 계층의 메서드를 호출하여 예약 가능한 시간 목록을 가져옴
            List<String> availableTimes = counselingReserveService.getAvailableTimes(counselId, counselReqDate);

            return ResponseEntity.ok(availableTimes); // HTTP 200 OK와 함께 목록 반환
        } catch (Exception e) {
            log.error("예약 가능 시간 조회 중 오류 발생", e);
            // 오류 발생 시 HTTP 500 Internal Server Error 반환
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * 상담 예약을 최종적으로 확정하는 API
     * (프론트엔드의 예약 신청 폼 제출 시 호출)
     * POST 요청: /cnslt/resve/reserve
     */
    @PostMapping("/reserve")
    @ResponseBody // JSON 데이터를 반환
    public ResponseEntity<String> reserveCounsel(@RequestBody CounselingVO counselingVO) {
        log.info("상담 예약 신청 요청: {}", counselingVO);
        
        try {
            // 서비스 계층의 핵심 로직(Redis 락, DB 트랜잭션 등) 호출
            boolean success = counselingReserveService.tryReserveCounsel(counselingVO);
            
            if (success) {
                return ResponseEntity.ok("예약이 성공적으로 완료되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("이미 다른 사용자가 예약 진행 중이거나 예약이 불가능한 시간입니다.");
            }
        } catch (Exception e) {
            log.error("상담 예약 신청 중 오류 발생", e);
            return ResponseEntity.status(500).body("예약 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }
	
}
