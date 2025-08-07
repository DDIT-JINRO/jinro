package kr.or.ddit.pse.cr.crr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.pse.cr.crr.service.CareerEncyclopediaRcmService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.worldcup.service.JobsVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CareerEncyclopediaRcmServiceImpl implements CareerEncyclopediaRcmService {

	@Autowired
	CareerEncyclopediaRcmMapper careerEncyclopediaRcmMapper;
	
	@Override
	public ArticlePage<JobsVO> selectCareerRcmList(JobsVO jobs, String memIdStr) {
		
		if ("".equals(memIdStr) && "anonymousUser".equals(memIdStr)) {
			throw new RuntimeException();
		}
		
		jobs.setMemId(Integer.parseInt(memIdStr));
		
		List<JobsVO> careerList = this.careerEncyclopediaRcmMapper.selectCareerRcmList(jobs);
		
		int total = this.careerEncyclopediaRcmMapper.selectCareerRcmTotal(jobs);
		
		ArticlePage<JobsVO> articlePage = new ArticlePage<>(total, jobs.getCurrentPage(), jobs.getSize(), careerList, jobs.getKeyword());
		
		articlePage.setUrl("/pse/cr/crr/selectCareerRcmList.do");
		
		return articlePage;
	}
}
