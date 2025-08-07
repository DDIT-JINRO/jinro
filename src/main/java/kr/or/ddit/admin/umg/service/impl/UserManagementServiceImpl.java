package kr.or.ddit.admin.umg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.umg.service.MemberPenaltyCountVO;
import kr.or.ddit.admin.umg.service.UserManagementService;
import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.csc.not.service.NoticeVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.InterestCnVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	FileService fileService;
	@Autowired
	UserManagementMapper userManagementMapper;

	@Override
	public ArticlePage<MemberVO> getMemberList(int currentPage, int size, String keyword, String status,
			String memRole) {
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		map.put("memRole", memRole);

		// 리스트 불러오기
		List<MemberVO> list = userManagementMapper.getUserList(map);
		// 건수
		int total = userManagementMapper.getAlluserList(map);
		// 페이지 네이션
		ArticlePage<MemberVO> articlePage = new ArticlePage<>(total, currentPage, size, list, keyword);

		log.info(articlePage + "@@@@@@@");

		return articlePage;
	}

	@Override
	public Map<String, Object> getMemberDetail(String id) {

		// 그냥 vo
		MemberVO memberDetail = userManagementMapper.getMemberVO(id);

		// 관심키워드 리스트
		List<String> interestCn = userManagementMapper.getMemberInterest(id);

		String filePath = null;
		if (memberDetail.getFileProfile() != null) {
			FileDetailVO file = fileService.getFileDetail(memberDetail.getFileProfile(), 1);
			filePath = fileService.getSavePath(file);
		}

		// 포르필 경로

		// 정지 경고 횟수 이력
		MemberPenaltyCountVO countVO = userManagementMapper.selectPenaltyCountByMemberId(id);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("memberDetail", memberDetail);
		map.put("interestCn", interestCn);
		map.put("filePath", filePath);
		map.put("countVO", countVO);

		return map;
	}

}
