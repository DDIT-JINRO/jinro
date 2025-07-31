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

}
