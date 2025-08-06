package kr.or.ddit.cnslt.resve.crsv.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.cnslt.resve.crsv.service.CounselingReserveService;
import kr.or.ddit.cnslt.resve.crsv.service.CounselingVO;
import kr.or.ddit.cnslt.resve.crsv.service.VacationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounselingReserveServiceImpl implements CounselingReserveService {
	
    // Redis 락을 위한 상수 정의
    private static final String LOCK_KEY_PREFIX = "counsel_lock:";
    private static final long LOCK_TIMEOUT_SECONDS = 300; // 5분 TTL

	private final CounselingReserveMapper counselingReserveMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	@Transactional
	public boolean tryReserveCounsel(CounselingVO counselingVO) {
		// Redis 락 키 생성: 상담사ID + 날짜 + 시간
        String lockKey = LOCK_KEY_PREFIX + counselingVO.getCounsel() + ":" +
                counselingVO.getCounselReqDate().getTime() + ":" + counselingVO.getCounselReqTime().getTime();

        // 안전한 락 해제를 위한 고유 값 생성
        String lockValue = UUID.randomUUID().toString();

        try {
            // Redis 분산 락 획득 시도 (동기 방식)
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (Boolean.FALSE.equals(locked)) {
                // 락 획득 실패 -> 다른 사용자가 이미 예약 진행 중
                return false;
            }

            // --- Redis 락 획득 성공! 이제부터 DB 검증 및 삽입 로직 진행 ---

            // 1. 회원의 중복 예약 확인 (MyBatis)
            // 반환된 카운트가 0보다 크면 중복 예약 존재
            if (counselingReserveMapper.selectDuplicateCounselingByMemId(counselingVO) > 0) {
                return false;
            }
            
            // 2. 상담사의 휴가 기간 확인 (MyBatis)
            // 해당 날짜가 휴가 기간에 포함되는지 확인
            VacationVO vacationVO = new VacationVO();
            vacationVO.setVaRequestor(counselingVO.getCounsel());
            vacationVO.setVaStart(counselingVO.getCounselReqDate());

            if (counselingReserveMapper.selectCounselorVacations(vacationVO) > 0) {
                return false;
            }

            // 3. DB에 예약 정보 삽입 (MyBatis)
            int result = counselingReserveMapper.insertReservation(counselingVO);

            // DB 삽입 성공 시 true 반환, 실패 시 false 반환
            return result > 0;
            
        } finally {
            // 예외 발생 여부와 상관없이 락을 해제하는 것이 중요
            if (lockValue.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }
    }
	
}
