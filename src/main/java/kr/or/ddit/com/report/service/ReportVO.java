package kr.or.ddit.com.report.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReportVO {
	private int reportId;
	private String targetType;
	private int targetId;
	private String reportReason;
	private String reportStatus;
	private Date reportCreatedAt;
	private Date reportCompleteAt;
	private int reportCompleteId;
	private int memId;
	private Long fileGroupNo;

	private List<MultipartFile> reportFile;
}
