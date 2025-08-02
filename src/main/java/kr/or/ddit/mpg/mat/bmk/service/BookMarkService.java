package kr.or.ddit.mpg.mat.bmk.service;

import java.util.List;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.util.ArticlePage;

public interface BookMarkService {

	ArticlePage<BookMarkVO> selectBookmarkList(String memId, BookMarkVO bookmarkVO);

	List<ComCodeVO> selectBmCategoryIdList();

}
