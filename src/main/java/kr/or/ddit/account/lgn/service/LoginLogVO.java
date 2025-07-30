package kr.or.ddit.account.lgn.service;

import java.util.Date;

import lombok.Data;

@Data
public class LoginLogVO {
	
	private int memId;
	private int llId;
	private Date llCreatedAt;
	private String llStatus;
	
}
