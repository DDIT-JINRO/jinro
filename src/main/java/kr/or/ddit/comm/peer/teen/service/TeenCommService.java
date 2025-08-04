package kr.or.ddit.comm.peer.teen.service;

import java.util.List;

import kr.or.ddit.comm.vo.CommBoardVO;
import kr.or.ddit.comm.vo.CommReplyVO;

public interface TeenCommService {

	public List<CommBoardVO> selectTeenList();

	public CommBoardVO selectTeenDetail(int boardId, String memId);

	public List<CommReplyVO> selectBoardReply(int boardId, String memId);

}
