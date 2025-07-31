package kr.or.ddit.util.file.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

	private final FileService fileService;
	
    /**
     * 다중 파일 업로드
     * @param files - MultipartFile 리스트
     * @return 업로드된 파일들의 상세 정보 리스트
     */
    @PostMapping("/upload")
    public ResponseEntity<List<FileDetailVO>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(required = false) Long groupId // 수정 시 기존 그룹ID 전달
    ) throws IOException {

        Long fileGroupId = groupId;
        if (fileGroupId == null || fileGroupId==0) {
            fileGroupId = fileService.createFileGroup(); // 신규 작성 시에만 생성
        }

        List<FileDetailVO> uploadedFiles = fileService.uploadFiles(fileGroupId, files);
        return ResponseEntity.ok(uploadedFiles);
    }
    
    @PostMapping("/update")
    public ResponseEntity<List<FileDetailVO>> updateFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(required = false) Long groupId // 수정 시 기존 그룹ID 전달
    ) throws IOException {

        Long fileGroupId = groupId;
        
        List<FileDetailVO> updateFiles = new ArrayList<>();
        
        if (fileGroupId != null || fileGroupId!=0) {
        	updateFiles = fileService.updateFile(fileGroupId, files); // 업데이트
        }
        
        for(FileDetailVO file : updateFiles) {
        	String path = fileService.getSavePath(file);
        	log.info("path : "+path);
        }

        return ResponseEntity.ok(updateFiles);
    }

	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestParam Long fileGroupId, @RequestParam int seq) throws IOException {
		FileDetailVO detail = fileService.getFileDetail(fileGroupId, seq);
		Resource resource = fileService.downloadFile(fileGroupId, seq);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + URLEncoder.encode(detail.getFileOrgName(), "UTF-8") + "\"")
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

}
