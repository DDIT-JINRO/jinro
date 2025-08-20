package kr.or.ddit.admin.csmg.service.impl;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CounselManagementMapper {
	Map<String, Object> selectMonthlyCounselingStatList();
}
