package kr.or.ddit.cdp.sint.service;

import java.util.Date;

import lombok.Data;

@Data
public class SelfIntroVO {
	private int siId;
	private int memId;
	private String siTitle;
	private String siStatus;
	private Date siCreatedAt;
	private Date siUpdatedAt;
	private int fileGroupNo;
}
