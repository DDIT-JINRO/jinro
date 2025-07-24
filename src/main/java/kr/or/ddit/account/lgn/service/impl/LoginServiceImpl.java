package kr.or.ddit.account.lgn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.account.lgn.service.LoginService;
import kr.or.ddit.account.lgn.service.MemDelVO;
import kr.or.ddit.account.lgn.service.MemberPenaltyVO;
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
			if (delYn.equals("Y")) {
				MemDelVO delStat = loginMapper.selectMemDelStat(resMemVO.getMemId());

				if (delStat != null) {
					resultMap.put("status", "delRequest");
					return resultMap;
				} else {
					MemberPenaltyVO pntStat = loginMapper.selectMemPnt(resMemVO.getMemId());

					resultMap.put("mpWarnReason", pntStat.getMpWarnReason());
					resultMap.put("mpCompleteAt", pntStat.getMpCompleteAt());
					resultMap.put("status", "suspend");
					return resultMap;
				}

			}
			resultMap.put("status", "success");

			if ("success".equals(resultMap.get("status"))) {
				String memId = resMemVO.getMemId() + "";

				String accessToken = jwtUtil.createAccessToken(memId);
				String refreshToken = jwtUtil.createRefreshToken(memId);
				jwtUtil.validateToken(accessToken);

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("refreshToken", refreshToken);
				paramMap.put("memId", memId);

				int tokenResult = loginMapper.memTokenInsert(paramMap);

				if (tokenResult == 1) {
					log.info("리프레쉬 토큰 db 저장 성공");
				} else {
					log.info("리프레쉬 토큰 db 저장 중 에러 발생");
				}

				resultMap.put("status", "success");
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);

			} else {
				resultMap.put("status", "failed");
			}

			return resultMap;

		} else {
			resultMap.put("status", "failed");
		}

		return resultMap;
	}

	@Override
	public MemberVO getRefreshToken(String refreshToken) {

		return this.loginMapper.getRefreshToken(refreshToken);
	}

	@Override
	public MemberVO selectById(int userId) {

		return this.loginMapper.selectById(userId);
	}

	@Override
	public Map<String, Object> kakaoLgnProcess(MemberVO member) {

		MemberVO result = this.loginMapper.selectByEmailForKakao(member);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (result != null) {
			String memId = result.getMemId() + "";

			String accessToken = jwtUtil.createAccessToken(memId);
			String refreshToken = jwtUtil.createRefreshToken(memId);

			jwtUtil.validateToken(accessToken);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("refreshToken", refreshToken);
			paramMap.put("memId", memId);

			int tokenResult = loginMapper.memTokenInsert(paramMap);

			if (tokenResult == 1) {
				log.info("리프레쉬 토큰 db 저장 성공");
			} else {
				log.info("리프레쉬 토큰 db 저장 중 에러 발생");
			}

			resultMap.put("status", "success");
			resultMap.put("accessToken", accessToken);
			resultMap.put("refreshToken", refreshToken);

			return resultMap;

		} else {
			
			int res = this.loginMapper.kakaoInsert(member);
			
			if(res == 0) {
				resultMap.put("status", "failed");
				return resultMap;
			}else if(res == 1) {
				MemberVO kaMem = loginMapper.selectMemberByEmailForKakao(member.getMemEmail());
				
				int kaMemId = kaMem.getMemId();
				
				String memId = kaMemId+"";
				
				String accessToken = jwtUtil.createAccessToken(memId);
				String refreshToken = jwtUtil.createRefreshToken(memId);

				jwtUtil.validateToken(accessToken);

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("refreshToken", refreshToken);
				paramMap.put("memId", memId);

				int tokenResult = loginMapper.memTokenInsert(paramMap);

				if (tokenResult == 1) {
					log.info("리프레쉬 토큰 db 저장 성공");
				} else {
					log.info("리프레쉬 토큰 db 저장 중 에러 발생");
				}

				resultMap.put("status", "success");
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);

				return resultMap;
			}else {
				return null;
			}
		}
	}

	@Override
	public Map<String, Object> naverLgnProcess(MemberVO member) {
		MemberVO result = this.loginMapper.selectByEmailForNaver(member);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (result != null) {
			String memId = result.getMemId() + "";

			String accessToken = jwtUtil.createAccessToken(memId);
			String refreshToken = jwtUtil.createRefreshToken(memId);

			jwtUtil.validateToken(accessToken);

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("refreshToken", refreshToken);
			paramMap.put("memId", memId);

			int tokenResult = loginMapper.memTokenInsert(paramMap);

			if (tokenResult == 1) {
				log.info("리프레쉬 토큰 db 저장 성공");
			} else {
				log.info("리프레쉬 토큰 db 저장 중 에러 발생");
			}

			resultMap.put("status", "success");
			resultMap.put("accessToken", accessToken);
			resultMap.put("refreshToken", refreshToken);

			return resultMap;

		} else {
			
			int res = this.loginMapper.naverInsert(member);
			
			if(res == 0) {
				resultMap.put("status", "failed");
				return resultMap;
			}else if(res == 1) {
				MemberVO nvMem = loginMapper.selectMemberByEmailForNaver(member.getMemEmail());
				
				int nvMemId = nvMem.getMemId();
				
				String memId = nvMemId+"";
				
				String accessToken = jwtUtil.createAccessToken(memId);
				String refreshToken = jwtUtil.createRefreshToken(memId);

				jwtUtil.validateToken(accessToken);

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("refreshToken", refreshToken);
				paramMap.put("memId", memId);

				int tokenResult = loginMapper.memTokenInsert(paramMap);

				if (tokenResult == 1) {
					log.info("리프레쉬 토큰 db 저장 성공");
				} else {
					log.info("리프레쉬 토큰 db 저장 중 에러 발생");
				}

				resultMap.put("status", "success");
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);

				return resultMap;
			}else {
				return null;
			}
		}
	}

	@Override
	public List<MemberVO> findEmailStringByNameAndPhone(MemberVO member) {
		// TODO Auto-generated method stub
		return loginMapper.findEmailStringByNameAndPhone(member);
	}

	@Override
	public MemberVO validateUser(MemberVO inputMem) {
		// TODO Auto-generated method stub
		return loginMapper.validateUser(inputMem);
	}

	@Override
	public int insertEncodePass(MemberVO memVO) {
		// TODO Auto-generated method stub
		return loginMapper.insertEncodePass(memVO);
	}

	
}
