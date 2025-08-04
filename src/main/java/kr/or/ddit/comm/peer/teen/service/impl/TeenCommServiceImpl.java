package kr.or.ddit.comm.peer.teen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.comm.peer.teen.service.TeenCommService;
import kr.or.ddit.comm.vo.CommBoardVO;
import kr.or.ddit.comm.vo.CommReplyVO;
import kr.or.ddit.main.service.MemberVO;
import kr.or.ddit.prg.std.service.StdReplyVO;
import kr.or.ddit.prg.std.service.impl.StudyGroupMapper;
import kr.or.ddit.util.file.service.FileDetailVO;
import kr.or.ddit.util.file.service.FileService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeenCommServiceImpl implements TeenCommService {

	@Autowired
	TeenCommMapper teenCommMapper;
	
	@Autowired
	FileService fileService;

	@Override
	public List<CommBoardVO> selectTeenList() {

		List<CommBoardVO> teenList = teenCommMapper.selectTeenList();

		return teenList;
	}

	@Override
	public CommBoardVO selectTeenDetail(int boardId, String memId) {

		int intMemId = 0;

		if (memId != "" && memId != "annoymousUser" && memId != null) {
			intMemId = Integer.parseInt(memId);
		}

		CommBoardVO paramBoard = new CommBoardVO();
		paramBoard.setBoardId(boardId);
		paramBoard.setMemId(intMemId);

		return teenCommMapper.selectTeenDetail(paramBoard);
	}

	@Override
	public List<CommReplyVO> selectBoardReply(int boardId, String memId) {

		int intMemId = Integer.parseInt(memId);

		CommReplyVO paramReplyVO = new CommReplyVO();
		paramReplyVO.setBoardId(boardId);
		paramReplyVO.setMemId(intMemId);

		List<CommReplyVO> replyVOList = teenCommMapper.selectBoardReply(paramReplyVO);

		if (replyVOList != null && !replyVOList.isEmpty() && replyVOList.get(0).getReplyContent() != null) {
			for (CommReplyVO commReplyVO : replyVOList) {
				Long replyFileBadgeId = commReplyVO.getFileBadge();
				Long replyFileProfileId = commReplyVO.getFileProfile();
				Long replyFileSubId = commReplyVO.getFileSub();

				FileDetailVO replyFileBadgeDetail = this.fileService.getFileDetail(replyFileBadgeId, 1);
				FileDetailVO replyFileProfileDetail = this.fileService.getFileDetail(replyFileProfileId, 1);
				FileDetailVO replyFileSubDetail = this.fileService.getFileDetail(replyFileSubId, 1);

				if (replyFileBadgeDetail != null) {
					commReplyVO.setFileBadgeStr(this.fileService.getSavePath(replyFileBadgeDetail));
				}
				if (replyFileProfileDetail != null) {
					commReplyVO.setFileProfileStr(this.fileService.getSavePath(replyFileProfileDetail));
				}
				if (replyFileSubDetail != null) {
					commReplyVO.setFileSubStr(this.fileService.getSavePath(replyFileSubDetail));
				}
				
				int childCount = commReplyVO.getChildCount();
				if (childCount == 0)
					continue;
				
				log.info("나 자식수 : "+childCount);
				
				if (childCount > 0) {
					
					int parentReplyId = commReplyVO.getReplyId();
					List<CommReplyVO> childReplies = this.getChildReplies(parentReplyId);
					
					// 자식댓글 리스트 세팅
					commReplyVO.setChildReplyVOList(childReplies);
				}
				
			}
		}
		return replyVOList;
	}

	private List<CommReplyVO> getChildReplies(int replyId) {
		List<CommReplyVO> replyList = this.teenCommMapper.selectChildReplyList(replyId);
		for (CommReplyVO replyVO : replyList) {
			Long fileBadgeId = replyVO.getFileBadge();
			Long fileProfileId = replyVO.getFileProfile();
			Long fileSubId = replyVO.getFileSub();

			FileDetailVO fileBadgeDetail = this.fileService.getFileDetail(fileBadgeId, 1);
			FileDetailVO fileProfileDetail = this.fileService.getFileDetail(fileProfileId, 1);
			FileDetailVO fileSubDetail = this.fileService.getFileDetail(fileSubId, 1);

			if (fileBadgeDetail != null) {
				replyVO.setFileBadgeStr(this.fileService.getSavePath(fileBadgeDetail));
			}
			if (fileProfileDetail != null) {
				replyVO.setFileProfileStr(this.fileService.getSavePath(fileProfileDetail));
			}
			if (fileSubDetail != null) {
				replyVO.setFileSubStr(this.fileService.getSavePath(fileSubDetail));
			}

			if (replyVO.getChildCount() == 0)
				continue;
			int parentReplyId = replyVO.getReplyId();
			List<CommReplyVO> childReplies = getChildReplies(parentReplyId);
			// 자식댓글 리스트 세팅
			replyVO.setChildReplyVOList(childReplies);
		}
		return replyList;
	}
	
}
