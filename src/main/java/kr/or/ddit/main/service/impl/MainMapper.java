package kr.or.ddit.main.service.impl;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	Date getAge(String memId);

}
