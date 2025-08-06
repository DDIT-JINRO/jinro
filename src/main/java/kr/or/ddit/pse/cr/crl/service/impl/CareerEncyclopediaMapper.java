package kr.or.ddit.pse.cr.crl.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CareerEncyclopediaMapper {

	List<Map<String, String>> selectJobLclCode();

}
