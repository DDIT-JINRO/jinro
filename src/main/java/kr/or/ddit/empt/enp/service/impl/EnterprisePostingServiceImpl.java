package kr.or.ddit.empt.enp.service.impl;

import org.springframework.stereotype.Service;

import kr.or.ddit.empt.enp.service.EnterprisePostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnterprisePostingServiceImpl implements EnterprisePostingService {

	private final EnterprisePostingMapper enterprisePostingMapper;
}
