package kr.or.ddit.mpg.mif.inq.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.exception.CustomException;
import kr.or.ddit.exception.ErrorCode;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileGroupVO;
import kr.or.ddit.util.file.service.FileService;
import kr.or.ddit.util.file.service.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyInquiryServiceImpl implements MyInquiryService {

	@Autowired
	MyInquiryMapper myInquiryMapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	FileService fileService;

	@Override
	public Map<String, Object> selectMyInquiryView(String memIdStr) {
		int memId = parseMemId(memIdStr);
		
		MemberVO member = this.myInquiryMapper.selectMyInquiryView(memId);

		FileDetailVO fileDetail = fileService.getFileDetail(member.getFileProfile(), 1);
		
		List<ComCodeVO> interetsKeywordList = this.myInquiryMapper.selectInteretsKeywordList();
		if(interetsKeywordList == null || interetsKeywordList.isEmpty()) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return Map.of("member", member, "imgPath", this.fileService.getSavePath(fileDetail), "interetsKeywordList", interetsKeywordList);
	}

	@Override
	public Map<String, Object> checkPassword(String memIdStr, Map<String, String> map) {
		int memId = parseMemId(memIdStr);

		String password = map.get("password");
		MemberVO memberVO = this.myInquiryMapper.checkPassword(memId);

		if (bCryptPasswordEncoder.matches(password, memberVO.getMemPassword())) {
			return Map.of("result", "success");
		};

		return Map.of("result", "fail");
	}

	@Override
	public String updateMyInquiryView(String memIdStr, MemberVO member) {
		int memId = parseMemId(memIdStr);

		member.setMemId(memId);

		int result = this.myInquiryMapper.updateMyInquiryView(member);
		if (result == 0) {
			return "redirect:";
		}

		return "success";
	}

	@Override
	public Map<String, Object> updateProfileImg(String memIdStr, MultipartFile profileImg) {
		int memId = parseMemId(memIdStr);

		if (profileImg == null || profileImg.isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_FILE);
		}

		MemberVO member = this.myInquiryMapper.selectMyInquiryView(memId);

		Long fileGroupId = fileService.createFileGroup();
		member.setFileProfile(fileGroupId);

		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(profileImg);

		List<FileDetailVO> fileDetail = new ArrayList<FileDetailVO>();
		try {
			fileDetail = this.fileService.uploadFiles(fileGroupId, files);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		int result = this.myInquiryMapper.updateFileGroup(member);
		if (result > 0) {
			return Map.of("result", "success");
		}

		return Map.of("result", "fail");
	}

	/**
	 * 멤버의 관심사 키워드를 입력 or 변경합니다.
	 * 
	 * @param memIdStr        멤버id
	 * @param interestKeyword 관심사키워드
	 */
	@Override
	public void insertInterestList(String memIdStr, List<String> interestKeyword) {
		int memId = parseMemId(memIdStr);

		this.myInquiryMapper.deleteInterestList(memId);

		if (interestKeyword == null || interestKeyword.isEmpty()) {
			this.myInquiryMapper.insertEmptyInterest(memId);
		} else {
			for (String keyword : interestKeyword) {
				this.myInquiryMapper.insertInterestList(Map.of("memId", memId, "keyword", keyword));
			}
		}
	}

	/**
	 * String타입의 멤버 Id를 int 타입으로 변환합니다.
	 * 
	 * @param memIdStr 멤버id
	 */
	public int parseMemId(String memIdStr) {
		int memId;
		try {
			memId = Integer.parseInt(memIdStr);
		} catch (NumberFormatException e) {
			throw new CustomException(ErrorCode.INVALID_USER);
		}

		return memId;
	}

}
