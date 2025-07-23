package kr.or.ddit.com;

import java.util.Date;

import lombok.Data;

@Data
public class FileDetailVO {
	private int fileId;
	private int fileGroupId;
	private String fileOrgName;
	private String fileSaveName;
	private int fileSize;
	private String fileExt;
	private String fileMime;
	private Date fileSaveDate;
	private int fileSeq;
}
