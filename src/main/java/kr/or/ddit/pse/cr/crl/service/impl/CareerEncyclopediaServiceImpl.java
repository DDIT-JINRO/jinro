package kr.or.ddit.pse.cr.crl.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.pse.cr.crl.service.CareerEncyclopediaService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.worldcup.service.JobsVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CareerEncyclopediaServiceImpl implements CareerEncyclopediaService {
	
	@Autowired
	CareerEncyclopediaMapper careerEncyclopediaMapper;
	
	@Override
	public Map<String, String> selectJobLclCode() {
		List<Map<String, String>> jobLclCodeList = this.careerEncyclopediaMapper.selectJobLclCode();
		
		if (jobLclCodeList == null || jobLclCodeList.isEmpty()) {
			throw new RuntimeException();
		}
		
		Map<String, String> jobLclCode = parseMap(jobLclCodeList);
	
		return jobLclCode;
	}
	
	@Override
	public ArticlePage<JobsVO> selectCareerList(JobsVO jobs, String memIdStr) {
		
		if (!"".equals(memIdStr) && !"anonymousUser".equals(memIdStr)) {
			jobs.setMemId(Integer.parseInt(memIdStr));
		}
		
		List<JobsVO> careerList = this.careerEncyclopediaMapper.selectCareerList(jobs);
		
		int total = this.careerEncyclopediaMapper.selectCareerTotal(jobs);
		
		ArticlePage<JobsVO> articlePage = new ArticlePage<>(total, jobs.getCurrentPage(), jobs.getSize(), careerList, jobs.getKeyword());
		
		articlePage.setUrl("/pse/cr/crl/selectCareerList.do");
		
		return articlePage;
	}
	
	@Override
	public JobsVO selectCareerDetail(JobsVO jobs) {
		
		JobsVO careerDetail = this.careerEncyclopediaMapper.selectCareerDetail(jobs);
		
		if (careerDetail == null) {
			throw new RuntimeException();
		}
		
		return careerDetail;
	}

	
	public Map<String, String> parseMap (List<Map<String, String>> mapList) {
		
		Map<String, String> result = new LinkedHashMap<>();
		
		for(Map<String, String> map : mapList) {
			String key = map.get("CC_ID");
			String value = map.get("CC_NAME");
			
			result.put(key, value);
		}
		
		return result;
	}

}
