package kr.or.ddit.csc.not.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.csc.not.service.NoticeService;
import kr.or.ddit.csc.not.service.NoticeVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
// 공지사항 서비스 impl
public class NoticeServiceImpl implements NoticeService {

	// 공지사항 Mapper
	@Autowired
	NoticeMapper noticeMapper;

	// 파일처리 Service
	@Autowired
	FileService fileService;

	// getList
	public List<NoticeVO> getList(Map<String, Object> map) {

		return this.noticeMapper.getList(map);
	}

	// 수정 후
	// 1. 사용자 목록 조회
	@Override
	public ArticlePage<NoticeVO> getUserNoticePage(int currentPage, int size, String keyword) {
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;

		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);

		// 리스트 불러오기
		List<NoticeVO> list = noticeMapper.getList(map);

		// 건수
		int total = noticeMapper.getAllNotice(map);

		// 페이지 네이션
		ArticlePage<NoticeVO> page = new ArticlePage<>(total, currentPage, size, list, keyword);
		page.setUrl("/csc/not/noticeList.do");

		return page;
	}

	// 2. 관리자 목록 조회
	@Override
	public ArticlePage<NoticeVO> getAdminNoticePage(int currentPage, int size, String keyword, String status) {

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
		List<NoticeVO> list = noticeMapper.getList(map);
		// 건수
		int total = noticeMapper.getAllNotice(map);
		// 페이지 네이션
		ArticlePage<NoticeVO> articlePage = new ArticlePage<>(total, currentPage, size, list, keyword);
		articlePage.setUrl("/csc/admin/noticeList.do");
		return articlePage;
	}

	// 3. 사용자 공지사항 세부 조회
	@Override
	public NoticeVO getUserNoticeDetail(String noticeIdStr) {

		int noticeId = Integer.parseInt(noticeIdStr);

		// 조회수 증가
		int cnt = noticeMapper.upNoticeCnt(noticeId);

		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeMapper.getNoticeDetail(noticeId);

		// 파일 불러오기
		List<FileDetailVO> getFileList = fileService.getFileList(noticeDetail.getFileGroupNo());
		if (getFileList != null && getFileList.size() > 0) {
			noticeDetail.setGetFileList(getFileList);
		}

		return noticeDetail;
	}

	// 4. 관리자 공지사항 세부 조회
	@Override
	public NoticeVO getAdminNoticeDetail(String noticeIdStr) {

		int noticeId = Integer.parseInt(noticeIdStr);

		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeMapper.getNoticeDetail(noticeId);

		// 파일 불러오기
		List<FileDetailVO> getFileList = fileService.getFileList(noticeDetail.getFileGroupNo());
		if (getFileList != null && getFileList.size() > 0) {
			noticeDetail.setGetFileList(getFileList);
		}

		return noticeDetail;
	}

	// 5. 관리자 공지사항 등록
	@Override
	public int insertNotice(NoticeVO noticeVo) {


		// 파일 그룹 생성.
		Long createFileGroupId = fileService.createFileGroup();
		noticeVo.setFileGroupNo(createFileGroupId);

		// 공지사항 등록
		int insertNotice = this.noticeMapper.insertNotice(noticeVo);

		// 3) 파일 저장
		try {
			List<MultipartFile> files = noticeVo.getFiles();

			if (files != null && !files.isEmpty()) {
				// 유효한 파일만 필터링 (빈 파일, 이름 없는 파일 제외)
				List<MultipartFile> validFiles = files.stream().filter(file -> file != null && !file.isEmpty()
						&& file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank()).toList();

				if (!validFiles.isEmpty()) {
					List<FileDetailVO> detailList = fileService.uploadFiles(createFileGroupId, noticeVo.getFiles());
				}
			}
		} catch (IOException e) {
			log.error("파일 업로드 중 오류 발생", e);
			return 0;
		}
		return insertNotice;
	}

	// 6. 공지사항 수정
	@Override
	public int updateNotice(NoticeVO noticeVo) {
	    int result = noticeMapper.updateNotice(noticeVo);
	 
	    try {
	        List<MultipartFile> files = noticeVo.getFiles();

	        if (files != null && !files.isEmpty()) {
	            // 유효한 파일만 필터링
	            List<MultipartFile> validFiles = files.stream()
	                    .filter(file -> file != null && !file.isEmpty()
	                            && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
	                    .toList();

	            if (!validFiles.isEmpty()) {
	                // 기존에 파일 그룹이 없으면 새로 생성
	                if (noticeVo.getFileGroupNo() == null) {
	                    Long newGroupId = fileService.createFileGroup();
	                    noticeVo.setFileGroupNo(newGroupId);

	                    // 공지사항 테이블에 FILE_GROUP_NO 업데이트
	                    noticeMapper.updateNoticeFileGroup(noticeVo.getNoticeId(), newGroupId);
	                }
	                List<FileDetailVO> detailList = fileService.uploadFiles(noticeVo.getFileGroupNo(), validFiles);
	            }
	        }
	    } catch (IOException e) {
	        log.error("파일 업로드 중 오류 발생", e);
	        return 0;
	    }

	    return result;
	}


	// 7. 공지사항 삭제
	@Override
	public int deleteNotice(int noticeId) {
		// TODO Auto-generated method stub
		return this.noticeMapper.deleteNotice(noticeId);
	}

}
