package kr.or.ddit.comm.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommBoardVO {
	
	private int boardId;
	private int memId;
	private String ccId;
	private String boardTitle;
	private String boardContent;
	private Date boardCreatedAt;
	private Date boardUpdatedAt;
	private int boardCnt;
	private String boardDelYn;
	private Long filegroupId;
	
	private String memName;		// 작성자 이름 추출을 위해
	private int boardLikeCnt;     // 게시글 좋아요 수
	private int boardIsLiked;     // 내가 좋아요 눌렀는지 여부 (1 or 0)
}
