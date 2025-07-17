package kr.or.ddit.util.file.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String createFileGroup(); // FILE_GROUP_ID 생성 + 저장
    
    List<FileDetailVO> uploadFiles(String fileGroupId, List<MultipartFile> files) throws IOException;
    
    boolean deleteFile(String fileId);
    
    List<FileDetailVO> getFilesByGroupId(String fileGroupId);
    
    Resource downloadFile(String fileGroupId, int fileSeq) throws IOException;
    
    FileDetailVO getFileDetail(String fileGroupId, int fileSeq);

}
