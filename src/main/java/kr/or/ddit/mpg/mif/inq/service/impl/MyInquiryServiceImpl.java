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

		return Map.of("member", member, "imgPath", this.fileService.getSavePath(fileDetail));
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
