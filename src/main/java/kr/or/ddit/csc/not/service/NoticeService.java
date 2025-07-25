package kr.or.ddit.csc.not.service;

import java.util.List;
import java.util.Map;

public interface NoticeService {
	//리스트 조회
	public List<NoticeVO> getList(Map<String, Object>map);
	//게시글 상세
	public NoticeVO getNoticeDetail(String no);
	//조회수
	public int upNoticeCnt(String no);
	//전체 게시글 수
	public int getAllNotice(Map<String, Object>map);
}
