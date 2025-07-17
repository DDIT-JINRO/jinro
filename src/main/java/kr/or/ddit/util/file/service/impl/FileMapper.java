package kr.or.ddit.util.file.service.impl;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileGroupVO;

@Mapper
public interface FileMapper {

	

	public void insertFileGroup(FileGroupVO vo);

	public String getMaxFileGroupId(String today);

	public void insertFileDetail(FileDetailVO detail);

	public FileDetailVO getFileDetailByGroupAndSeq(@Param("fileGroupId") String fileGroupId, @Param("fileSeq") int fileSeq);
	
    public FileDetailVO getFileDetailById(String fileId);
    
    FileDetailVO selectFile(@Param("groupId") String groupId, @Param("seq") int seq);
    
    int deleteFile(@Param("groupId") String groupId, @Param("seq") int seq);

}
