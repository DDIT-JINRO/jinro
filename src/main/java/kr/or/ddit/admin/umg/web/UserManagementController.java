package kr.or.ddit.admin.umg.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.account.join.service.MemberJoinService;
import kr.or.ddit.account.lgn.service.MemberPenaltyVO;
import kr.or.ddit.admin.umg.service.UserManagementService;
import kr.or.ddit.com.report.service.ReportVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.util.alarm.service.AlarmService;
import kr.or.ddit.util.alarm.service.AlarmType;
import kr.or.ddit.util.alarm.service.AlarmVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/umg")
@Slf4j
public class UserManagementController {

	@Autowired
	UserManagementService userManagementService;
	@Autowired
	MemberJoinService memberJoinService;
	@Autowired
	FileService fileService;
	@Autowired
	AlarmService alarmService;

	@GetMapping("/getMemberList.do")
	public ArticlePage<MemberVO> getMemberList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "memRole") String memRole) {

		return userManagementService.getMemberList(currentPage, size, keyword, status, memRole);
	}

	@PostMapping("/getMemberDetail.do")
	public Map<String, Object> getMemberDetail(@RequestParam("id") String id) {

		Map<String, Object> memberDetail = userManagementService.getMemberDetail(id);

		return memberDetail;
	}

	@PostMapping("/insertUserByAdmin.do")
	public String insertUserByAdmin(MemberVO memberVO, @RequestParam(required = false) MultipartFile profileImage) {

		return userManagementService.insertUserByAdmin(memberVO, profileImage) == 1 ? "success" : "failed";

	}

	@PostMapping("/updateMemberInfo.do")
	public int updateMemberInfo(MemberVO memberVO) {

		int res = userManagementService.updateMemberInfo(memberVO);

		return res;
	}

	@PostMapping("/selectEmailByAdmin.do")
	public String selectEmailByAdmin(@RequestParam("email") String email) {

		MemberVO memberVO = memberJoinService.selectUserEmail(email);

		if (memberVO != null) {
			return "중복된 이메일입니다.";
		} else
			return "사용 가능한 이메일입니다.";
	}

	@PostMapping("/selectNicknameByAdmin.do")
	public String selectNicknameByAdmin(@RequestParam("nickname") String nickname) {

		boolean boolRes = memberJoinService.isNicknameExists(nickname);

		if (boolRes) {
			return "중복된 닉네임입니다.";
		} else
			return "사용 가능한 닉네임입니다.";
	}

	@GetMapping("/getReportList.do")
	public ArticlePage<ReportVO> getReportList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", required = false) String status) {

		return userManagementService.getReportList(currentPage, size, keyword, status);

	}

	@PostMapping("/getReportDetail.do")
	public Map<String, Object> getReportDetail(@RequestParam("id") String id) {

		return userManagementService.getReportDetail(id);
	}

	@GetMapping("/getPenaltyList.do")
	public ArticlePage<MemberPenaltyVO> getPenaltyList(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", required = false) String status) {

		return userManagementService.getPenaltyList(currentPage, size, keyword, status);
	}

	@PostMapping("/submitPenalty.do")
	public String submitPenalty(MemberPenaltyVO memberPenaltyVO,
			@RequestParam(required = false) MultipartFile[] evidenceFiles) {

		int res = userManagementService.submitPenalty(memberPenaltyVO, evidenceFiles);

		AlarmVO alarmVO = new AlarmVO();

		alarmVO.setAlarmTargetType(AlarmType.REPLY_TO_PENALTY);
		alarmVO.setMemId(memberPenaltyVO.getMemId());
		alarmVO.setAlarmTargetUrl("#");
		this.alarmService.sendEvent(alarmVO);

		return res == 1 ? "success" : "failed";

	}

	@PostMapping("/getPenaltyDetail.do")
	public Map<String, Object> getPenaltyDetail(@RequestParam("id") String id) {

		return userManagementService.getPenaltyDetail(id);
	}

	@PostMapping("/reportModify.do")
	public int reportModify(ReportVO reportVO) {

		return userManagementService.reportModify(reportVO);
	}

}
