package kr.or.ddit.admin.umg.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.admin.umg.service.MemberPenaltyCountVO;
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

}
