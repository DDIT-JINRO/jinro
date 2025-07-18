package kr.or.ddit.main.service;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	
	private int memId;
	private String memEmail;
	private String memPassword;
	private String memNickname;
	private String memPhoneNumber;
	private String memName;
	private String memGen;
	private Date memBirth;
	private String memAlarm;
	private String memStudent;
	private String memRole;
	private int memPoint;
	private Date createdAt;
	private Date pwUpdatedAt;
	private String loginType;
	private String memToken;
	private String delYn;
	private int fileProfile;
	private int fileEtc;
	
	public String getSMemId() {
		return memId+"";
	}
	
}
