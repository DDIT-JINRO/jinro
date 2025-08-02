package kr.or.ddit.mpg.mat.bmk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.com.ComCodeVO;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkService;
import kr.or.ddit.mpg.mat.bmk.service.BookMarkVO;
import kr.or.ddit.mpg.mif.inq.service.MyInquiryService;
import kr.or.ddit.util.ArticlePage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookMarkServiceImpl implements BookMarkService{

	@Autowired
	MyInquiryService myInquiryService;
	
	@Autowired
	BookMarkMapper bookmarkMapper;
	
	@Override
	public ArticlePage<BookMarkVO> selectBookmarkList(String memIdStr, BookMarkVO bookmarkVO) {
		
		int memId = myInquiryService.parseMemId(memIdStr);
		
		bookmarkVO.setMemId(memId);
		
		List<BookMarkVO> selectBookmark = this.bookmarkMapper.selectBookmarkList(bookmarkVO);
		
		int total = this.bookmarkMapper.selectBookmarkTotal(bookmarkVO);
		
		ArticlePage<BookMarkVO> articlePage = new ArticlePage<>(total, bookmarkVO.getCurrentPage(), bookmarkVO.getSize(), selectBookmark, bookmarkVO.getKeyword());
		
		articlePage.setUrl("/mpg/mat/bmk/selectBookMarkList.do");
		
		return articlePage;
	}

	@Override
	public List<ComCodeVO> selectBmCategoryIdList() {
		
		List<ComCodeVO> bmCategoryIdList = this.bookmarkMapper.selectBmCategoryIdList();
		
		return bmCategoryIdList;
	}

}
