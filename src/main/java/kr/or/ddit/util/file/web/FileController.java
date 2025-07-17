package kr.or.ddit.util.file.web;

import java.io.IOException;
import java.net.URLEncoder;
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

@RestController
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
    public ResponseEntity<List<FileDetailVO>> uploadFiles( @RequestParam("files") List<MultipartFile> files) throws IOException {
        // 1. 그룹 생성
        String fileGroupId = fileService.createFileGroup();

        // 2. 파일 저장 및 메타데이터 저장
        List<FileDetailVO> uploadedFiles = fileService.uploadFiles(fileGroupId, files);

        return ResponseEntity.ok(uploadedFiles);
    }
    
    @GetMapping("/download")
    public ResponseEntity<Resource> download(
            @RequestParam String groupId,
            @RequestParam int seq
    ) throws IOException {
        FileDetailVO detail = fileService.getFileDetail(groupId, seq);
        Resource resource = fileService.downloadFile(groupId, seq);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + URLEncoder.encode(detail.getFileOrgName(), "UTF-8") + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
