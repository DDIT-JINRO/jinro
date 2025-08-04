package kr.or.ddit.empt.enp.service;

import lombok.Data;

@Data
public class CompanyVO {
	private int cpId;
	private String cpName;
	private String cpScale;
	private String cpLocationX;
	private String cpLocationY;
	private String cpDescription;
	private String cpWebsite;
	private int cpBusino;
	private String cpImgUrl;
	private int fileGroupId;
}
