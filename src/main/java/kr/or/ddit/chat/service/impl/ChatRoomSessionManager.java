package kr.or.ddit.chat.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ChatRoomSessionManager {
	
	// 사용자 Id : 사용자가 현재 입장중인 채팅방Set ConcurrentHashMap은 다중스레드에서 안전하게 사용된다고 함
	Map<String, Set<Integer>> roomSessionMap = new ConcurrentHashMap<>();
	
	// 채팅방 입장 시 사용자와 채팅방 매핑해서 Map에 적용
	public void enterRoom(String userId, int roomId) {
		roomSessionMap.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(roomId);
	}
	
	// 채팅방 퇴장시(모달을 닫거나 다른 채팅방으로 전환) 제거
	public void exitRoom(String userId, int roomId) {
		Set<Integer> rooms = roomSessionMap.get(userId);
        if (rooms != null) {
            rooms.remove(roomId);
            if (rooms.isEmpty()) {
                roomSessionMap.remove(userId);
            }
        }
	}
	
	// 사용자 id와 방 번호를 기준으로 입장 여부 확인
    public boolean isRoomOpened(String userId, int roomId) {
        return roomSessionMap.getOrDefault(userId, Set.of()).contains(roomId);
    }
	
    // 방번호를 기준으로 입장 중인 모든 사용자 id 조회
    public Set<String> getOpenedUsers(Long roomId) {
        return roomSessionMap.entrySet().stream()
            .filter(e -> e.getValue().contains(roomId))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }
    
    public Map<String, Set<Integer>> getSessionState() {
        return Map.copyOf(roomSessionMap);
    }
}
