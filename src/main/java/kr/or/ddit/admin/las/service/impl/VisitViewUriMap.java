package kr.or.ddit.admin.las.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class VisitViewUriMap {
	
	private static final Map<String, String> uriNameMap = new HashMap<>();

    static {
    	uriNameMap.put("/", "메인");
        uriNameMap.put("/pse/cat/careerAptitudeTest.do", "진로탐색");
        uriNameMap.put("/pse/cr/crl/selectCareerList.do", "직업백과 - 직업목록");
        uriNameMap.put("/pse/cr/crr/selectCareerRcmList.do", "직업백과 - 추천직업");        
    }

    public static String getPageName(String path) {
        return uriNameMap.get(path);
    }

    public static boolean contains(String path) {
        return uriNameMap.containsKey(path);
    }
	
}
