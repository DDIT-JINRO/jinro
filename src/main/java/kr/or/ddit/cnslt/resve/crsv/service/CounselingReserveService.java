package kr.or.ddit.cnslt.resve.crsv.service;

import java.util.Date;
import java.util.List;

public interface CounselingReserveService {
    /**
     * 상담 예약을 시도하는 메서드. Redis 분산 락을 사용하여 동시성을 제어합니다.
     * @param counselingVO 상담 예약 정보
     * @return 예약 성공 시 true, 실패 시 false
     */
    boolean tryReserveCounsel(CounselingVO counselingVO);
    
    List<String> getAvailableTimes(int counselId, Date counselReqDate);
}
