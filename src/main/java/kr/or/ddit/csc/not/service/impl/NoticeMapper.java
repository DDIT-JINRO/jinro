package kr.or.ddit.csc.not.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.csc.not.service.NoticeVO;

@Mapper
public interface NoticeMapper {
	public List<NoticeVO> getList(Map<String, Object>map);

	public NoticeVO getNoticeDetail(int no);

	public int upNoticeCnt(int no);
	
	public int getAllNotice(Map<String, Object>map);

	public int insertNotice(NoticeVO noticeVo);

	public int updateNotice(NoticeVO noticeVo);

	public int deleteNotice(int noticeId);
}
