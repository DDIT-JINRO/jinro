package kr.or.ddit.util.setle.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.util.setle.service.SubscriptionVO;

@Mapper
public interface SubscriptionMapper {
	
	//구독상품조회
	public List<SubscriptionVO> selectAllProducts();

}
