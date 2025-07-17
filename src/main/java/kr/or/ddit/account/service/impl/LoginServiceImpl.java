package kr.or.ddit.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.account.service.LoginService;
import kr.or.ddit.account.service.MemDelVO;
import kr.or.ddit.account.service.MemberPenaltyVO;
import kr.or.ddit.config.jwt.JwtProperties;
import kr.or.ddit.config.jwt.JwtUtil;
import kr.or.ddit.main.service.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	
    @Autowired
    JwtUtil jwtUtil;

	@Autowired
	LoginMapper loginMapper;
	
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginServiceImpl(BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Map<String, Object> loginProcess(MemberVO memVO) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String inputEmail = memVO.getMemEmail();
		String inputPw = memVO.getMemPassword();
		String inputType = memVO.getLoginType();

		if (!"normal".equals(inputType)) {
			resultMap.put("status", "failed");
			return resultMap;
		}

		MemberVO resMemVO = loginMapper.selectMemberByEmail(inputEmail);
		if (resMemVO == null) {
			resultMap.put("status", "failed");
			return resultMap;
		}
		
		String encodedPwFromDb = resMemVO.getMemPassword();
		boolean result = passwordEncoder.matches(inputPw, encodedPwFromDb);

		if (result) {
			String delYn = resMemVO.getDelYn();
//			log.info("delYn : " + delYn);
			if (delYn.equals("Y")) {
				MemDelVO delStat = loginMapper.selectMemDelStat(resMemVO.getMemId());
//				log.info("delStat : " + delStat);
				if (delStat != null) {
					resultMap.put("status", "delRequest");
					return resultMap;
				} else {
					MemberPenaltyVO pntStat = loginMapper.selectMemPnt(resMemVO.getMemId());
//					log.info("pntStat : "+pntStat);
					resultMap.put("status", "suspend");
					return resultMap;
				}

			}
			resultMap.put("status", "success");
			
			if("success".equals(resultMap.get("status"))) {
		    	String memId = resMemVO.getMemId()+"";
		    	
				String token = jwtUtil.createAccessToken(memId);
				jwtUtil.validateToken(token);
				
		    }
			
			
			return resultMap;

		} else {
			resultMap.put("status", "failed");
		}

		return resultMap;
	}

}
