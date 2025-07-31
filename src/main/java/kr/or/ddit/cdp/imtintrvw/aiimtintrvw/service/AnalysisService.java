package kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service;

import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisRequest;
import kr.or.ddit.cdp.imtintrvw.aiimtintrvw.service.dto.AnalysisResponse;

public interface AnalysisService {
	
	AnalysisResponse analyzeInterview(AnalysisRequest request);
	
}
