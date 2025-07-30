package kr.or.ddit.admin.las.service;

import java.util.Date;

import lombok.Data;

@Data
public class PageLogVO {
	
	private int plId;
	private String memId;
	private String plUrl;
	private String plTitle;
	private String plRefererUrl;
	private Date plCreatedAt;
	
}
