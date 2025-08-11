package kr.or.ddit.admin.umg.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.account.lgn.service.MemberPenaltyVO;
import kr.or.ddit.admin.umg.service.MemberPenaltyCountVO;
import kr.or.ddit.com.report.service.ReportVO;
import kr.or.ddit.main.service.MemberVO;

@Mapper
public interface UserManagementMapper {

	List<MemberVO> getMemberList(String memRole);

	List<MemberVO> getUserList(Map<String, Object> map);

	int getAlluserList(Map<String, Object> map);

	MemberVO getMemberVO(String id);

	List<String> getMemberInterest(String id);

	MemberPenaltyCountVO selectPenaltyCountByMemberId(String id);

	int insertUserByAdmin(MemberVO member);

	int updateMemberInfo(MemberVO memberVO);

	int selectVacByCns(String id);

	int selectCounseling(String id);

	double selectAvgRate(String id);

	List<ReportVO> getReportList(Map<String, Object> map);

	int getAllReportList(Map<String, Object> map);

	ReportVO getReportVO(String id);

	List<MemberPenaltyVO> getPenaltyList(Map<String, Object> map);

	int getAllMemberPenaltyList(Map<String, Object> map);

	int getMemIdByReport(int reportedId);

	int submitPenalty(MemberPenaltyVO memberPenaltyVO);

	MemberPenaltyVO getPenaltyDetail(String id);

	void updateReport(int reportedId);

	int reportModify(ReportVO reportVO);

}
