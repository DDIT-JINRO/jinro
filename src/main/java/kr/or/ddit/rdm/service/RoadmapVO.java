package kr.or.ddit.rdm.service;

import java.util.Date;

import lombok.Data;

@Data
public class RoadmapVO {

	private int roadId;
	private int memId;
	private int rsId;
	private String roadComplete;
	private Date completeAt;
	private Date createdAt;
	
}
