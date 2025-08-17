package kr.or.ddit.admin.cmg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.cmg.service.ContentsManagementService;
import kr.or.ddit.empt.enp.service.CompanyVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContentsManagementServiceImpl implements ContentsManagementService {

	@Autowired
	ContentsManagementMapper contentsManagementMapper;
	@Autowired
	FileService fileService;

	@Override
	public ArticlePage<CompanyVO> getEntList(CompanyVO companyVO) {

		List<CompanyVO> companyList = contentsManagementMapper.getEntList(companyVO);

		int total = contentsManagementMapper.getAllEntList(companyVO);

		return new ArticlePage<CompanyVO>(total, companyVO.getCurrentPage(), companyVO.getSize(), companyList,
				companyVO.getKeyword());
	}

	@Override
	public Map<String, Object> entDetail(String id) {
		CompanyVO companyVO = contentsManagementMapper.entDetail(id);
		String filePath = "";
		log.info("companyVO @@@@@@@@ : {}", companyVO);

		if (companyVO != null) {
			
			if (null != companyVO.getFileGroupId()) {
				log.error("여기 들어왔음@!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				FileDetailVO file = fileService.getFileDetail(companyVO.getFileGroupId(), 0);
				filePath = fileService.getSavePath(file);
			}
		}

		return Map.of("companyVO", companyVO, "filePath", filePath);
	}

}
