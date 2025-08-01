package kr.or.ddit.cdp.rsm.rsm.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class ResumeVO {
	private int resumeId;
	private int memId;
	private String resumeTitle;
	private String resumeIsTemp;
	private Date createdAt;
	private Date updatedAt;
	private Long fileGroupId; 
	MultipartFile files;
	private String resumeContent;
}
