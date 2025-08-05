package kr.or.ddit.com;

import java.util.Date;

abstract public class AbstractMemberVO {
	int memId;
	String memEmail;
	String memPassword;
	String memNickname;
	String memPhoneNumber;
	String memName;

	Long fileBadge;
	Long fileProfile;
	Long fileSub;

	String fileBadgeStr;
	String fileProfileStr;
	String fileSubStr;
}
