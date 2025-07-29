package kr.or.ddit.util.file.web;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
	
	private final FileService fileService;
	  
    @GetMapping("/download")
    public ResponseEntity<Resource> download(
            @RequestParam Long groupId,
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
