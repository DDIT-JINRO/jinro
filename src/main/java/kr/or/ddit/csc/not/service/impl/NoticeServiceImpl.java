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
	public NoticeVO getNoticeDetail(String noticeIdStr) {
		// TODO Auto-generated method stub
		int noticeId = Integer.parseInt(noticeIdStr);
		return this.noticeMapper.getNoticeDetail(noticeId);
	}

	@Override
	public int upNoticeCnt(String noticeIdStr) {
		// TODO Auto-generated method stub
		int noticeId = Integer.parseInt(noticeIdStr);
		return this.noticeMapper.upNoticeCnt(noticeId);
	}

	@Override
	public int getAllNotice(Map<String, Object>map ) {
		// TODO Auto-generated method stub
		return this.noticeMapper.getAllNotice(map);
	}

	@Override
	public int insertNotice(NoticeVO noticeVo) {
		// TODO Auto-generated method stub
		return this.noticeMapper.insertNotice(noticeVo);
	}

	@Override
	public int updateNotice(NoticeVO noticeVo) {
		// TODO Auto-generated method stub
		return  this.noticeMapper.updateNotice(noticeVo);
	}

	@Override
	public int deleteNotice(int noticeId) {
		// TODO Auto-generated method stub
		return this.noticeMapper.deleteNotice(noticeId);
	}

}
