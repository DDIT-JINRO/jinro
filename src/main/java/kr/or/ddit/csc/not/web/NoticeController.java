package kr.or.ddit.csc.not.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import kr.or.ddit.csc.not.service.NoticeService;
import kr.or.ddit.csc.not.service.NoticeVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/csc")
// 공지사항 컨트롤러
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	FileService fileService;
	
	// 공지사항 리스트
	@GetMapping("/noticeList.do")
	public String noticeList(Model model,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="5") int size,
			@RequestParam(value="keyword",required=false) String keyword) {

		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;
		
		// 파라미터
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		log.info("map : "+map);
		
		
		// 공지사항 게시글
		List<NoticeVO>list = noticeService.getList(map);
		log.info("list"+list);
		
		// 전체 게시글 수
		int getAllNotice = noticeService.getAllNotice(map);
		log.info("getAllNotice : "+getAllNotice);
		
		//---URL 설정 필수 ########################################
		//페이징 객체 생성
		ArticlePage<NoticeVO> articlePage = 
				new ArticlePage<NoticeVO>(getAllNotice, currentPage, 5, list, keyword);
		log.info("list->articlePage : " + articlePage);
		articlePage.setUrl("/csc/noticeList.do");
		
		model.addAttribute("articlePage", articlePage);
		model.addAttribute("getAllNotice", getAllNotice);
		model.addAttribute("getList",list);
		
		
		return "csc/noticeList";
	}
	
	// 공지사항 세부 화면
	@GetMapping("/noticeDetail.do")
	public String noticeDetail(@RequestParam String no, Model model) {

		log.info("no : " + no);
		// 게시글 조회수 +1
		int cnt  = noticeService.upNoticeCnt(no);
		log.info("no : "+no);
		
		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeService.getNoticeDetail(no);
		log.info("noticeDetail : " + noticeDetail);
		
		// 파일 가져오기
		List<FileDetailVO> getFileList = fileService.getFileList(noticeDetail.getFileGroupNo());
		if(getFileList !=null && getFileList.size() >1) {
			noticeDetail.setGetFileList(getFileList);
			log.info("getfileList : "+getFileList);
		}
		
		model.addAttribute("noticeDetail", noticeDetail);

		return "csc/noticeDetail"; // JSP 경로: /WEB-INF/views/csc/noticeDetail.jsp
	}
	// 관리자 목록 조회
	@ResponseBody
	@GetMapping("/admin/noticeList.do")
	public ArticlePage<NoticeVO> adminNoticeList(@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="size",required=false,defaultValue="5") int size,
			@RequestParam(value="keyword",required=false) String keyword,
			//연도별 구분
			@RequestParam(value="status",required = false)String status) {
		
		
		int startNo = (currentPage - 1) * size + 1;
		int endNo = currentPage * size;
		
		// 파라미터
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("status", status);
		map.put("currentPage", currentPage);
		map.put("startNo", startNo);
		map.put("endNo", endNo);
		log.info("map : "+map);
		
		
		// 공지사항 게시글
		List<NoticeVO>list = noticeService.getList(map);
		log.info("list"+list);
		
		// 전체 게시글 수
		int getAllNotice = noticeService.getAllNotice(map);
		log.info("getAllNotice : "+getAllNotice);
		
		//---URL 설정 필수 ########################################
		//페이징 객체 생성
		ArticlePage<NoticeVO> articlePage = 
				new ArticlePage<NoticeVO>(getAllNotice, currentPage, 5, list, keyword);
		log.info("list->articlePage : " + articlePage);
		articlePage.setUrl("/csc/admin/noticeList.do");

		return articlePage;
	}
	
	// 관리자 공지사항 세부 화면
	@ResponseBody
	@GetMapping("/admin/noticeDetail.do")
	public NoticeVO adminNoticeDetail(@RequestParam String no) {

		log.info("no : " + no);
		
		// 게시글 상세 조회
		NoticeVO noticeDetail = noticeService.getNoticeDetail(no);
		log.info("noticeDetail : " + noticeDetail);
		
		// 파일 가져오기
		List<FileDetailVO> getFileList = fileService.getFileList(noticeDetail.getFileGroupNo());
		if(getFileList !=null && getFileList.size() >1) {
			noticeDetail.setGetFileList(getFileList);
			log.info("getfileList : "+getFileList);
		}
		
		return noticeDetail ;
	}

	// 관리자 공지사항 등록
	// consumes = MediaType.MULTIPART_FORM_DATA_VALUE => 멀티파트 요청임을 명시하면
	// MultipartResolver 가 동작해서 List<MultipartFile> 을 채워 줍니다.
	// @ModelAttribute => 파일(MultipartFile)과 폼 필드를 섞어 받을 땐 무조건 @ModelAttribute 로
	// 바인딩해야 합니다.
	@ResponseBody
	@PostMapping(value = "/admin/insertNotice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public int insertNotice(@ModelAttribute NoticeVO noticeVo) {

		log.info("noticeVo : {}", noticeVo);
		// 1. 파일 그룹 생성.
		Long createFileGroupId = fileService.createFileGroup();
		noticeVo.setFileGroupNo(createFileGroupId);
		log.info("createFileGroupId : "+createFileGroupId);

		
        // 2) 공지사항 저장 (제목·내용 등)
        int noticeCount = noticeService.insertNotice(noticeVo);
        log.info("notice insert 결과: {}", noticeCount);
		
        // 3) 파일 저장
        try {
            List<MultipartFile> files = noticeVo.getFiles();

            if (files != null && !files.isEmpty()) {
                // 유효한 파일만 필터링 (빈 파일, 이름 없는 파일 제외)
                List<MultipartFile> validFiles = files.stream()
                    .filter(file -> file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                    .toList();

                if (!validFiles.isEmpty()) {
                	List<FileDetailVO> detailList = fileService.uploadFiles(createFileGroupId, noticeVo.getFiles());
                	log.info("detailList : {}", detailList);
                } else {
                    log.info("유효한 파일이 없어 업로드 생략");
                }
            } else {
                log.info("첨부된 파일이 없습니다. 파일 저장 생략.");
            }
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생", e);
        }

		
		return 1;
	}
	
	//파일삭제
	@ResponseBody
	@GetMapping("/admin/deleteFile")
	public boolean deleteFile(@RequestParam Long groupId, @RequestParam int seq) {
		
		log.info("groupId : "+groupId);
		log.info("seq : "+seq);
		
		boolean resultDeleteFile =fileService.deleteFile(groupId, seq);
		log.info("resultDeleteFile : "+resultDeleteFile);

		return resultDeleteFile;
	}
	
	//파일 수정
	@ResponseBody
	@PostMapping(value = "/admin/updateNotice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public boolean updateNotice(@ModelAttribute NoticeVO noticeVo) {

		log.info("데이터 확인 : "+noticeVo.toString());
		
        // 1) 공지사항 수정 (제목·내용 등)
		log.info("asd");
        int noticeCount = noticeService.updateNotice(noticeVo);
        log.info("notice insert 결과: {}", noticeCount);
		
		// 2) 파일 저장
        try {
            List<MultipartFile> files = noticeVo.getFiles();

            if (files != null && !files.isEmpty()) {
                // 유효한 파일만 필터링 (빈 파일, 이름 없는 파일 제외)
                List<MultipartFile> validFiles = files.stream()
                    .filter(file -> file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                    .toList();

                if (!validFiles.isEmpty()) {
                	List<FileDetailVO> detailList = fileService.uploadFiles(noticeVo.getFileGroupNo(), noticeVo.getFiles());
                	log.info("detailList : {}", detailList);
                } else {
                    log.info("유효한 파일이 없어 업로드 생략");
                }
            } else {
                log.info("첨부된 파일이 없습니다. 파일 저장 생략.");
            }
        } catch (IOException e) {
            log.error("파일 업로드 중 오류 발생", e);
        }


		return true;
	}
	
	@ResponseBody
	@PostMapping("/admin/deleteNotice")
	public int deleteNotice(@ModelAttribute NoticeVO noticeVO) {
		
		log.info("noticeVO : "+noticeVO.toString());
		
		int noticeId = noticeVO.getNoticeId();
		int resultNotice = noticeService.deleteNotice(noticeId);
		log.info("resultNotice : "+resultNotice);
		
		List<MultipartFile> files = noticeVO.getFiles();

		if (files != null && !files.isEmpty()) {
		    // 유효한 파일만 필터링 (빈 파일, 이름 없는 파일 제외)
		    List<MultipartFile> validFiles = files.stream()
		        .filter(file -> file != null && !file.isEmpty() && file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
		        .toList();

		    if (!validFiles.isEmpty()) {
		    	boolean result = fileService.deleteFileGroup(null);
		    } else {
		        log.info("유효한 파일이 없어 업로드 생략");
		    }
		} else {
		    log.info("첨부된 파일이 없습니다. 파일 저장 생략.");
		}
		
		return resultNotice;
	}
}
