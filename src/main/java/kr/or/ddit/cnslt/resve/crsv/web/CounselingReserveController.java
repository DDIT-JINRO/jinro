package kr.or.ddit.cnslt.resve.crsv.web;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
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
		return "cnslt/resve/crsv/counselingreserve";
	}
	
	/**
     * 특정 상담사의 특정 날짜 예약 가능 시간 목록을 반환하는 API
     */
    @GetMapping("/availableTimes")
    @ResponseBody
    public ResponseEntity<List<String>> getAvailableTimes(
    		@RequestParam int counsel,
            @RequestParam String counselReqDatetime) {
    	
        try {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	Date counselReqDate = dateFormat.parse(counselReqDatetime);
        	log.info("예약 가능 시간 조회 요청 - 상담사 ID: {}, 날짜: {}", counsel, counselReqDate);
        	
            List<String> availableTimes = counselingReserveService.getAvailableTimes(counsel, counselReqDate);

            return ResponseEntity.ok(availableTimes);
        } catch (Exception e) {
            log.error("예약 가능 시간 조회 중 오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * 상담 시간 임시 점유를 시도하고, 성공하면 상세 페이지로 리디렉션하는 API
     * @return 성공 시 상세 페이지 리디렉션, 실패 시 에러 응답
     */
    @PostMapping("/holdAndRedirect") // 새로운 엔드포인트
    public String holdAndRedirect(@RequestBody CounselingVO counselingVO, Principal principal, RedirectAttributes redirectAttributes) {
        log.info("상담 시간 임시 점유 요청: {}", counselingVO);
        
        if (principal != null && !principal.getName().equals("anonymousUser")) {
            String memIdString = principal.getName();
            counselingVO.setMemId(Integer.parseInt(memIdString));
            
            boolean isHeld = counselingReserveService.tryHoldCounsel(counselingVO);
            
            if (isHeld) {
                // 임시 락 성공 시, VO 객체를 FlashAttribute에 담아 리디렉션 후에도 유지
                redirectAttributes.addFlashAttribute("counselingVO", counselingVO);
                
                // 날짜를 "yyyy-MM-dd HH:mm" 형식의 문자열로 변환하여 쿼리 파라미터에 추가
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String formattedDate = dateFormat.format(counselingVO.getCounselReqDatetime());
                
                redirectAttributes.addAttribute("counselorId", counselingVO.getCounselId());
                redirectAttributes.addAttribute("counselReqDatetime", formattedDate);
                
                return "redirect:/cnslt/resve/crsv/reservationDetail.do";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "이미 점유된 시간입니다.");
                return "redirect:/cnslt/resve/crsv/reservation.do";
            }
        } else {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }

    /**
     * 상담 예약을 최종적으로 확정하는 API
     */
    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<String> reserveCounsel(@RequestBody CounselingVO counselingVO) {
        log.info("상담 예약 신청 요청: {}", counselingVO);
        
		boolean isReserved = counselingReserveService.tryReserveCounsel(counselingVO);
		        
		if (isReserved) {
		    return ResponseEntity.ok("상담 예약 성공!");
		} else {
		    return ResponseEntity.status(HttpStatus.GONE).body("예약 실패.");
		}
    }
    
    /**
     * 상세 페이지 뷰를 반환하는 메서드
     */
    @GetMapping("/crsv/reservationDetail.do")
    public String reservationDetail(@ModelAttribute CounselingVO counselingVO) {
        log.info("상세 페이지 진입 - 전달받은 VO: {}", counselingVO);
        return "cnslt/resve/crsv/counselingreserveDetail";
    }
}
