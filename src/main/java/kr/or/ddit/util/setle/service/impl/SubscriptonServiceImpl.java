package kr.or.ddit.util.setle.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.util.setle.service.SubscriptionService;
import kr.or.ddit.util.setle.service.SubscriptionVO;

public class SubscriptonServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionMapper subscriptionMapper;
	
	//구독상품조회
	@Override
	public List<SubscriptionVO> selectAllProducts() {
		
		return SubscriptionMapper.selectAllProducts();
	}

}
