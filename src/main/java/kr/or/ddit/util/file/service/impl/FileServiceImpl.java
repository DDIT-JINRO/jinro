package kr.or.ddit.util.file.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileGroupVO;
import kr.or.ddit.util.file.service.FileService;
import kr.or.ddit.util.file.service.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

	private final FileMapper fileMapper;
	private final FileUtil fileUtil;
	
	@Override
	public String createFileGroup() {
	    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    log.info("createFileGroup -> today :" +today);
	    String maxId = fileMapper.getMaxFileGroupId(today);

	    int next = (maxId == null) ? 1 : Integer.parseInt(maxId.substring(8)) + 1;
	    String fileGroupId = today + String.format("%03d", next);

	    FileGroupVO vo = new FileGroupVO();
	    vo.setFileGroupId(fileGroupId);
	    vo.setFileGroupDate(LocalDateTime.now());
	    fileMapper.insertFileGroup(vo);

	    return fileGroupId;
	}


	@Override
	@Transactional
	public List<FileDetailVO> uploadFiles(String fileGroupId, List<MultipartFile> files) throws IOException {
	    List<FileDetailVO> detailList = new ArrayList<>();

	    // ✅ 기존 그룹이면 max seq 조회
	    int fileSeq = fileMapper.getMaxFileSeq(fileGroupId) + 1;

	    for (MultipartFile file : files) {
	        FileDetailVO detail = fileUtil.saveFile(file, fileGroupId, fileSeq++);
	        fileMapper.insertFileDetail(detail);
	        detailList.add(detail);
	    }

	    return detailList;
	}

	@Override
	public FileDetailVO getFileDetail(String fileGroupId, int fileSeq) {
	    return fileMapper.getFileDetailByGroupAndSeq(fileGroupId, fileSeq);
	}

	@Override
	public Resource downloadFile(String fileGroupId, int fileSeq) throws IOException {
	    FileDetailVO detail = fileMapper.getFileDetailByGroupAndSeq(fileGroupId, fileSeq);
	    if (detail == null) {
	        throw new FileNotFoundException("파일 정보를 찾을 수 없습니다.");
	    }

	    // 경로: C:/upload/yyyy/MM/dd/UUID_원본파일명
	    String datePath = detail.getFileSaveDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	    Path filePath = Paths.get("C:/upload", datePath, detail.getFileSaveName());

	    if (!Files.exists(filePath)) {
	        throw new FileNotFoundException("파일이 존재하지 않습니다.");
	    }

	    return new UrlResource(filePath.toUri());
	}

	
	@Override
	public boolean deleteFile(String groupId, int seq) {
	    FileDetailVO detail = fileMapper.selectFile(groupId, seq);
	    if (detail == null) return false;

	    // 📌 파일 저장 경로 계산 (yyyy/MM/dd)
	    String datePath = detail.getFileSaveDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	    Path fullPath = Paths.get("C:/upload", datePath, detail.getFileSaveName());

	    log.info("삭제 시도 파일 경로: " + fullPath);

	    try {
	        // 1. 파일 삭제 (존재하면)
	        Files.deleteIfExists(fullPath);

	        // 2. DB 삭제
	        fileMapper.deleteFile(groupId, seq);

	        return true;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public boolean deleteFileGroup(String groupId) {
	    List<FileDetailVO> fileList = fileMapper.selectFileList(groupId);
	    if (fileList == null || fileList.isEmpty()) return false;

	    for (FileDetailVO detail : fileList) {
	        String datePath = detail.getFileSaveDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	        Path fullPath = Paths.get("C:/upload", datePath, detail.getFileSaveName());

	        try {
	            Files.deleteIfExists(fullPath);
	        } catch (IOException e) {
	            e.printStackTrace(); // 삭제 실패해도 계속 진행
	        }
	    }

	    // 모든 파일 삭제 후 DB 삭제
	    // 1. 파일 메타 삭제
	    fileMapper.deleteFilesByGroupId(groupId);

	    // 2. 그룹 정보 삭제
	    fileMapper.deleteFileGroup(groupId);
	    
	    log.info("삭제 대상 파일 수: {}", fileList.size());
	    log.info("FILE_DETAIL, FILE_GROUP 삭제 완료");

	    return true;
	}




	@Override
	public List<FileDetailVO> getFileList(String groupId) {
		 return fileMapper.selectFileList(groupId);
	}



}
