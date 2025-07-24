package kr.or.ddit.you.service.impl;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface YoutubeMapper {

	public String getKeyword(int id);

}
