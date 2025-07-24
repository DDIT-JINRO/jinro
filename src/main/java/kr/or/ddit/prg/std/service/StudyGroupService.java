package kr.or.ddit.prg.std.service;

import java.util.List;
import java.util.Map;

public interface StudyGroupService {
	
	/**
	 * 목록 조회. 필터링, 검색 조건 추가
	 * @param stdBoardVO
	 * @return
	 */
	List<StdBoardVO> selectStudyGroupList(StdBoardVO stdBoardVO);
	
	/**
	 * BOARD 테이블에 스터디그룹 게시글 데이터 삽입
	 * content 는 json형식의 clob
	 *   '{
		    "content": "학업, 진로 등 아무 이야기나 편하게 할 수 있는 온라인 수다방입니다.",
		    "region": "G23013",
		    "gender": "women",
		    "interest": "social.talk",
		    "maxPeople": 20
		  }'
	 * @param stdBoardVO
	 * @return
	 */
	int insertStdBoard(StdBoardVO stdBoardVO);
	
	/**
	 * 목록 조회 -> 조회된 갯수만 가져오기
	 * @param stdBoardVO
	 * @return
	 */
	int selectStudyGroupTotalCount(StdBoardVO stdBoardVO);
	
	Map<String, String> getInterestsMap();
}
