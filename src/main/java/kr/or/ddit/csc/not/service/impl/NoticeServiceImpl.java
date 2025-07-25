package kr.or.ddit.csc.not.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.csc.not.service.NoticeService;
import kr.or.ddit.csc.not.service.NoticeVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
// 공지사항 서비스 impl
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	NoticeMapper noticeMapper;
	
	public List<NoticeVO> getList(Map<String, Object>map ) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getList(map);
	}

	@Override
	public NoticeVO getNoticeDetail(String no) {
		// TODO Auto-generated method stub
		int data = Integer.parseInt(no);
		return this.noticeMapper.getNoticeDetail(data);
	}

	@Override
	public int upNoticeCnt(String no) {
		// TODO Auto-generated method stub
		int data = Integer.parseInt(no);
		return this.noticeMapper.upNoticeCnt(data);
	}

	@Override
	public int getAllNotice(Map<String, Object>map ) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getAllNotice(map);
	}

}
