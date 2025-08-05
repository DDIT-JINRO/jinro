package kr.or.ddit.csc.inq.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.csc.inq.service.InqService;
import kr.or.ddit.csc.inq.service.InqVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmType;
import kr.or.ddit.util.alarm.service.AlarmVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InqServiceimpl implements InqService{

	@Autowired
	InqMapper inqMapper;
	
	@Autowired
	AlarmService alarmService;
	
	// 사용자 1:1문의 목록 조회
	@Override
	public ArticlePage<InqVO> getUserInqryPage(int currentPage, int size, String keyword, List<String> filterKeywords,String memId) {
		
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;
		
		Map<String, Object> map = new HashMap<>();
		// 키워드가 있다면
		map.put("keyword", keyword);
		// 내가 쓴 글 찾기
		if(filterKeywords != null && filterKeywords.contains("mine")) {
		    // 내가 쓴 글보기
		    if (memId == null || "anonymousUser".equals(memId)) {
		        map.put("mine", null); 
		    } else {
		        // memId가 숫자형이 아니라 문자열 ID일 경우
		        map.put("mine", memId); // String 타입의 memId를 그대로 사용
		    }
		}
		// 공개 설정인지
		if(filterKeywords != null &&filterKeywords.contains("open")){
			map.put("open", "open");
		}

		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		// 리스트 불러오기
		List<InqVO> list = inqMapper.getInqList(map);

		// 건수
		int total = inqMapper.getAllInq(map);

		// 페이지 네이션
		ArticlePage<InqVO> page = new ArticlePage<>(total, currentPage, size, list, keyword);
		page.setUrl("/csc/not/noticeList.do");

		return page;
	}

	// 사용자 1:1문의 등록
	@Override
	public int insertInqData(InqVO inqVO) {
		return this.inqMapper.insertInqData(inqVO);
	}

	// 관리자 1:1문의 목록 조회
	@Override
	public ArticlePage<InqVO> getAdminInqList(int currentPage, int size, String keyword, String status) {
		// 파라미터
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);

		// 리스트 불러오기
		List<InqVO> list = inqMapper.getAdminInqList(map);
		// 건수
		int total = inqMapper.getAllInq(map);
		// 페이지 네이션
		ArticlePage<InqVO> articlePage = new ArticlePage<InqVO>(total, currentPage, size, list, keyword);
		articlePage.setUrl("/csc/admin/faqList.do");
		return articlePage;
	}

	// 관리자 1:1문의 상세조회
	@Override
	public InqVO getAdminInqDetail(int inqId) {
		// 관리자 FAQ 상세 조회
		return inqMapper.getAdminInqDetail(inqId);
	}

	// 관리자 1:1문의 답변 등록
	@Transactional
	@Override
	public int insertInq(InqVO inqVO) {
		
		log.info(inqVO.toString());
		
		//AlarmVO 생성
		AlarmVO alarmVO = new AlarmVO();
		
		// 알림 타입 지정
		alarmVO.setAlarmTargetType(AlarmType.REPLY_TO_CONTACT);
		
		// 1:1문의 사용자 ID 조회
		int memId = inqMapper.getMemId(inqVO.getContactId());	
		log.info("memId : "+memId);
		
		// 알림 타겟 지정
		alarmVO.setMemId(memId);
		
		// 알림 URL 등록
		alarmVO.setAlarmTargetUrl("/csc/inq/inqryList.do"); 
		
		// 알림 전송
		alarmService.sendEvent(alarmVO);
		
		return inqMapper.insertInq(inqVO);
	}
}
