package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.AiImitationInterviewService;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewDetailListVO;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.InterviewQuestionVO;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/cdp/imtintrvw/aiimtintrvw")
@Controller
@Slf4j
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AiImitationInterviewController {
	
	@Autowired
	AiImitationInterviewService aiImitationInterviewService;
	
    /**
     * AI 모의면접 메인 페이지
     */
	@GetMapping("/aiImitationInterview.do")
	public String aiImitationInterviewPage() {
		return "cdp/imtintrvw/aiimtintrvw/aiImitationInterview";
	}
	
	/**
     * 사용자 정의 질문 리스트 조회
     */
    @GetMapping("/getCustomQuestionList")
    @ResponseBody
    public ResponseEntity<List<InterviewDetailListVO>> getCustomQuestionList(@AuthenticationPrincipal String memId) {
        try {
            log.info("=== getCustomQuestionList 호출 시작 ===");
            // 로그인 체크
    	    if (memId == null || memId.equals("anonymousUser")) {
    	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	    }
    	    MemberVO memberVO = new MemberVO();
    	    memberVO.setMemId(Integer.parseInt(memId));
    	  
            List<InterviewDetailListVO> list = aiImitationInterviewService.getCustomQuestionList(memberVO);
            
            log.info("=== 조회 결과 ===");
            log.info("리스트 크기: {}", list != null ? list.size() : "null");
            if (list != null && !list.isEmpty()) {
                log.info("첫 번째 항목: {}", list.get(0));
            } else {
                log.warn("조회된 질문 리스트가 비어있습니다!");
            }
            
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("사용자 정의 질문 리스트 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 업종별 질문 리스트 조회
     */
    @GetMapping("/getIndustryList")
    @ResponseBody
    public ResponseEntity<List<InterviewQuestionVO>> getIndustryList() {
        try {
            log.info("=== getIndustryList 호출 시작 ===");
            
            List<InterviewQuestionVO> list = aiImitationInterviewService.getIndustryList();
            
            log.info("=== 조회 결과 ===");
            log.info("리스트 크기: {}", list != null ? list.size() : "null");
            if (list != null && !list.isEmpty()) {
                log.info("첫 번째 항목: {}", list.get(0));
                for (InterviewQuestionVO item : list) {
                    log.info("업종코드: {}, 업종명: {}", item.getIqGubun(), item.getIndustryName());
                }
            } else {
                log.warn("조회된 업종 리스트가 비어있습니다!");
            }
            
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("업종 리스트 조회 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * React에서 직접 호출하는 면접 질문 조회 API
     */
    @GetMapping("/api/getInterviewQuestions")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getInterviewQuestionsForReact(
            @RequestParam String type,
            @RequestParam(required = false) String questionListId,
            @RequestParam(required = false) String industryCode,
            @RequestParam(defaultValue = "10") int questionCount) {
        
        log.info("=== React에서 면접 질문 조회 요청 ===");
        log.info("파라미터 - type: {}, questionListId: {}, industryCode: {}, questionCount: {}", 
                type, questionListId, industryCode, questionCount);
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            List<InterviewQuestionVO> questions = null;
            
            if ("saved".equals(type) && questionListId != null) {
                log.info("저장된 질문 리스트 조회 시작 - ID: {}", questionListId);
                questions = aiImitationInterviewService.getQuestionsByDetailListId(questionListId);
                result.put("type", "saved");
                result.put("questionListId", questionListId);
                log.info("저장된 질문 리스트 조회 완료: {} 개", questions != null ? questions.size() : 0);
                
            } else if ("random".equals(type) && industryCode != null) {
                log.info("업종별 랜덤 질문 조회 시작 - 업종: {}, 개수: {}", industryCode, questionCount);
                questions = aiImitationInterviewService.getRandomQuestionsByIndustry(industryCode, questionCount);
                result.put("type", "random");
                result.put("industryCode", industryCode);
                result.put("questionCount", questionCount);
                log.info("업종별 랜덤 질문 조회 완료: {} 개", questions != null ? questions.size() : 0);
                
            } else {
                log.warn("잘못된 파라미터 - type: {}, questionListId: {}, industryCode: {}", 
                        type, questionListId, industryCode);
                result.put("success", false);
                result.put("message", "잘못된 파라미터입니다.");
                return ResponseEntity.badRequest().body(result);
            }
            
            if (questions == null || questions.isEmpty()) {
                log.warn("조회된 질문이 없습니다");
                result.put("success", false);
                result.put("message", "해당 조건에 맞는 질문이 없습니다.");
                return ResponseEntity.ok(result);
            }
            
            result.put("success", true);
            result.put("questions", questions);
            result.put("totalCount", questions.size());
            
            log.info("React 질문 조회 성공 - type: {}, questions count: {}", type, questions.size());
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            log.error("React 면접 질문 조회 중 오류 발생", e);
            result.put("success", false);
            result.put("message", "질문을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 기존 JSP용 면접 질문 조회 API (호환성 유지)
     */
    @GetMapping("/getInterviewQuestions")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getInterviewQuestions(
            @RequestParam String type,
            @RequestParam(required = false) String questionListId,
            @RequestParam(required = false) String industryCode,
            @RequestParam(defaultValue = "10") int questionCount) {
        
        // React용 API와 동일한 로직 사용
        return getInterviewQuestionsForReact(type, questionListId, industryCode, questionCount);
    }

    /**
     * AI 모의면접 상세 페이지
     */
    @GetMapping("detail.do")
    public String aiImitationInterviewDetailPage() {
        return "cdp/imtintrvw/aiimtintrvw/aiImitationInterviewDetail";
    }
}