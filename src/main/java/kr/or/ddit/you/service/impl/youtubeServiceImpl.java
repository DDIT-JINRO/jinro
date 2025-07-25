package kr.or.ddit.you.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.you.service.YoutubeService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
//유튜브 동영상 가져오기 API 
public class youtubeServiceImpl implements YoutubeService {

	@Autowired
	YoutubeMapper mapper;
	
	@Override
	// 사용자별 관심사 키워드 가져오기
	public String getKeyword(String memId) {
		String data = "";
		data = this.mapper.getKeyword(Integer.parseInt(memId));
		
		if(data != null) {
			return data;
		}else {
			return "직업|흥미";
		}
	}
}
