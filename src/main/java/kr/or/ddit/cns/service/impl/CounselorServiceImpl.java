package kr.or.ddit.cns.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.cns.service.CounselingLogVO;
import kr.or.ddit.cns.service.CounselingVO;
import kr.or.ddit.cns.service.CounselorService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CounselorServiceImpl implements CounselorService {

	@Autowired
	CounselorMapper counselorMapper;

	@Autowired
	FileService fileService;

	@Override
	public List<CounselingVO> selectCounselList() {
		return null;
	}

	@Override
	public ArticlePage<CounselingVO> selectCompletedCounselList(CounselingVO counselingVO) {

		List<CounselingVO> list = counselorMapper.selectCompletedCounselList(counselingVO);
		int total = counselorMapper.selectTotalCompletedCounselList(counselingVO);

		ArticlePage<CounselingVO> articlePage = new ArticlePage<>(total, counselingVO.getCurrentPage(), counselingVO.getSize(), list, counselingVO.getKeyword());

		return articlePage;
	}

	@Override
	public CounselingVO selectCounselDetail(int counselId) {
		CounselingVO counselVO = counselorMapper.selectCounselDetail(counselId);
		CounselingLogVO counselLogVO = counselVO.getCounselingLog();
		Long fileGroupId = counselLogVO.getFileGroupId();

		List<FileDetailVO> fileList = fileService.getFileList(fileGroupId);
		counselLogVO.setFileDetailList(fileList);
		return counselVO;
	}

	@Transactional
	@Override
	public boolean updateCnsLog(CounselingLogVO counselingLogVO) {
		// 실제로 첨부된 유효한 파일만 필터링

		List<MultipartFile> validFiles = counselingLogVO.getFiles().stream()
			.filter(file -> file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
			.toList();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@ filegroupId : " + counselingLogVO.getFileGroupId());
		// 파일이 있을 경우에만 업로드 처리
		if (!validFiles.isEmpty()) {

			log.info("파일 업롣");

			// 파일그룹이 이미 존재하는 경우 체크해서 존재하면 기존 그룹ID 없으면 새로운 ID
			if(counselingLogVO.getFileGroupId() == null || counselingLogVO.getFileGroupId() == 0L) {
				counselingLogVO.setFileGroupId(fileService.createFileGroup());
			}
			// 파일 업로드
			try {
				fileService.uploadFiles(counselingLogVO.getFileGroupId(), validFiles);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int result = counselorMapper.updateCnsLog(counselingLogVO);
		return result > 0 ? true : false;
	}

	@Override
	@Transactional
	public boolean deleteFile(Long fileGroupId, int seq) {
		try {
			fileService.deleteFile(fileGroupId, seq);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
