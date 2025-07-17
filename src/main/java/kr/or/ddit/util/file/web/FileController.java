package kr.or.ddit.util.file.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<FileDetailVO>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam(required = false) String groupId // 수정 시 기존 그룹ID 전달
    ) throws IOException {

        String fileGroupId = groupId;
        if (fileGroupId == null || fileGroupId.isBlank()) {
            fileGroupId = fileService.createFileGroup(); // 신규 작성 시에만 생성
        }

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
    
    @PostMapping("/delete")
    public ResponseEntity<String> deleteFile(
            @RequestParam String groupId,
            @RequestParam int seq) {
        
        boolean result = fileService.deleteFile(groupId, seq);
        
        if (result) {
            return ResponseEntity.ok("파일 삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일을 찾을 수 없습니다.");
        }
    }

    
    @PostMapping("/delete/group")
    public ResponseEntity<String> deleteFileGroup(@RequestParam String groupId) {
        boolean result = fileService.deleteFileGroup(groupId);

        if (result) {
            return ResponseEntity.ok("그룹 전체 파일 삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 그룹에 파일이 없습니다.");
        }
    }

    
    @GetMapping("/list")
    public ResponseEntity<List<FileDetailVO>> getFileList(@RequestParam String groupId) {
        List<FileDetailVO> list = fileService.getFileList(groupId);

        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(list); // 200 OK + JSON 목록 반환
    }


}
