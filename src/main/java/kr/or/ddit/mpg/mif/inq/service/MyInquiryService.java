package kr.or.ddit.mpg.mif.inq.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.main.service.MemberVO;

public interface MyInquiryService {

	Map<String, Object> selectMyInquiryView(String memId);

	String updateMyInquiryView(String memId, MemberVO member);

	Map<String, Object> checkPassword(String memId, Map<String, String> map);
}
