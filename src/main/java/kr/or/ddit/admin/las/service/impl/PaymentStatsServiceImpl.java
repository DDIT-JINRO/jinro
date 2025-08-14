package kr.or.ddit.admin.las.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.las.service.PaymentStatsService;

@Service
public class PaymentStatsServiceImpl implements PaymentStatsService {
	
	@Autowired
	private PaymentStatsMapper paymentStatsMapper;
}
