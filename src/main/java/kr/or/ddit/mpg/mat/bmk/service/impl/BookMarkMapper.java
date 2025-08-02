package kr.or.ddit.mpg.mat.bmk.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;

@Mapper
public interface BookMarkMapper {

	List<BookMarkVO> selectBookmarkList(BookMarkVO bookmarkVO);

	List<ComCodeVO> selectBmCategoryIdList();

	int selectBookmarkTotal(BookMarkVO bookmarkVO);

}
