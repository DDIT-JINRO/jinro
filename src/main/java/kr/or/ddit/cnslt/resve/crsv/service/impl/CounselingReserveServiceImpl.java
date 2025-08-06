package kr.or.ddit.cnslt.resve.crsv.service.impl;

import org.springframework.stereotype.Service;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounselingReserveServiceImpl implements CounselingReserveService {

	private final CounselingReserveMapper counselingReserveMapper;
}
