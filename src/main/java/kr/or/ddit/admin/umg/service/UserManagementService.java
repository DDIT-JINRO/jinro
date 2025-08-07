package kr.or.ddit.admin.umg.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;

public interface UserManagementService {

	ArticlePage<MemberVO> getMemberList(int currentPage, int size, String keyword, String status, String memRole);

	Map<String, Object> getMemberDetail(String id);

}
