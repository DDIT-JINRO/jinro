package kr.or.ddit.admin.csmg.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CounselManagementMapper {
	//상담사관리 통계 상단 4개
	Map<String, Object> selectMonthlyCounselingStatList();
	
	int selectCounselorStatTotalCount(Map<String, Object> map);
	
	//상담사별 처리건수 및 만족도 평가
	List<Map<String, Object>> selectCounselorStatList(Map<String, Object> map);
	
	Map<String, Object> selectCounselorDetail(int counselor);
	
	int selectCounselingListTotalCount(Map<String, Object> map);
	
	List<Map<String, Object>> selectCounselingList(Map<String, Object> map);
}
