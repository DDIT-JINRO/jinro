package kr.or.ddit.empt.enp.service;

import lombok.Data;

@Data
public class CompanyVO {
	private int cpId;
	private String cpName;
	private String cpScale;
	private double cpLocationX;
	private double cpLocationY;
	private String cpDescription;
	private String cpWebsite;
	private Long cpBusino;
	private String cpImgUrl;
	private String cpRegion;
	private int fileGroupId;
}
