package kr.or.ddit.empt.enp.service;

import java.util.Date;

import lombok.Data;

@Data
public class InterviewReviewVO {
	private int irId;
	private int memId;
	private String irType;
	private int targetId;
	private String irContent;
	private Date irCreatedAt;
	private Date irModAt;
	private int irRating;
	
	private String memNickname;
}
