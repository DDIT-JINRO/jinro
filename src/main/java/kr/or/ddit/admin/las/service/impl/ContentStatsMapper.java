package kr.or.ddit.admin.las.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContentStatsMapper {

	/**
	 * 북마크 카테고리별 횟수 <br/>
	 * 요구파라미터 <br/>
	 * 1.from 북마크생성 날짜 기간 필터<br/>
	 * 2.to 북마크생성 날짜 기간 필터 <br/>
	 * 날짜는 문자열 yyyy-MM-dd 값으로 전달<br/>
	 * 3.gender 성별필터 <br/>
	 * 남자 G11001<br/>
	 * 여자 G11002<br/>
	 * 4.ageBand 연령필터 <br/>
	 * ALL, U15, 15-19, 20-24, 25-29, 30+ .6종류의 문자열값.<br/>
	 * <br/>
	 * 반환컬럼 : categoryId, categoryName, maleCnt, femaleCnt
	 * @param Map<String, Object> param
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> bookmarkCountsStatistic(Map<String, Object> param);


	/**
	 * 북마크 상세 TOP N <br/>
	 * 요구 파라미터:<br/>
	 *  - from, to: 문자열 'yyyy-MM-dd'<br/>
	 *  - gender: 'ALL' | 'G11001'(남) | 'G11002'(여)<br/>
	 *  - ageBand: 'ALL' | 'U15' | '15-19' | '20-24' | '25-29' | '30+'<br/>
	 *  - categoryId: 'ALL' | 'G03001'(대학) | 'G03002'(기업) | 'G03004'(직업) | 'G03005'(이력서템플릿) | 'G03006'(학과)<br/>
	 *  - limit: 가져올 개수 (기본 5 권장)<br/>
	 * 반환 컬럼: categoryId, categoryName, targetId, targetName, cnt
	 * @param Map<String, Object> param
	 * @return List<Map<String, Object>>
	 */
	List<Map<String,Object>> bookmarkTopN(Map<String, Object> param);
}
