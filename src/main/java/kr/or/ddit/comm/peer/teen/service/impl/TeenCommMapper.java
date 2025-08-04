package kr.or.ddit.comm.peer.teen.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.comm.vo.CommBoardVO;
import kr.or.ddit.comm.vo.CommReplyVO;

@Mapper
public interface TeenCommMapper {

	List<CommBoardVO> selectTeenList();

	CommBoardVO selectTeenDetail(CommBoardVO paramBoard);

	List<CommReplyVO> selectBoardReply(CommReplyVO paramReplyVO);

	List<CommReplyVO> selectChildReplyList(int replyId);

}
