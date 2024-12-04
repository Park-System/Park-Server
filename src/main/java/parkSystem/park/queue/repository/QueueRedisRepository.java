package parkSystem.park.queue.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static parkSystem.park.queue.QueueConst.*;

@Repository
public class QueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public QueueRedisRepository(
            @Qualifier("luckyDrawRedisTemplate") RedisTemplate<String, String> redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    // 현재 참여자 수
    public long getParticipantCount() {
        return redisTemplate.opsForZSet().size(PARTICIPANTS_KEY); // 예외 처리 필요, NPE 발생
    }

    // 참가자 추가
    public void addParticipant(String userId) {
        redisTemplate.opsForZSet().add(PARTICIPANTS_KEY, userId, System.currentTimeMillis());
    }

    // 대기열 추가
    public void addWaiting(String userId) {
        redisTemplate.opsForZSet().add(WAITING_KEY, userId, System.currentTimeMillis());
    }

    // 참여자에서 제거
    public boolean removeParticipant(String userId) {
        return redisTemplate.opsForZSet().remove(PARTICIPANTS_KEY, userId) > 0; // 예외 처리 필요, NPE 발생
    }

    // 대기열에서 한 명을 꺼내기
    public Long popFromWaitingList() {

        Set<String> nextUsers = redisTemplate.opsForZSet().range(WAITING_KEY, 0, 0);
        if (nextUsers != null && !nextUsers.isEmpty()) {
            String nextMemberId = nextUsers.iterator().next();
            redisTemplate.opsForZSet().remove(WAITING_KEY, nextMemberId);
            return Long.valueOf(nextMemberId); // String -> Long으로 변환
        }
        return null;
    }

    // 현재 참여자 목록 조회
    public Set<String> getParticipants() {
        return redisTemplate.opsForZSet().range(PARTICIPANTS_KEY, 0, -1);
    }

    // 대기열 목록 조회
    public Set<String> getWaiting() {
        return redisTemplate.opsForZSet().range(WAITING_KEY, 0, -1);
    }
}
