package parkSystem.park.queue.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
        redisTemplate.opsForZSet().add(PARTICIPANTS_KEY, userId, System.currentTimeMillis()+EXPIRATION_TIME);
    }

    // 대기열 추가
    public void addWaiting(String userId) {
        redisTemplate.opsForZSet().add(WAITING_KEY, userId, System.currentTimeMillis());
    }

    // 참여자에서 제거
    public Long removeParticipant(String userId) {
        return redisTemplate.opsForZSet().remove(PARTICIPANTS_KEY, userId); // 예외 처리 필요, NPE 발생
    }

    // 대기열에서 꺼낸 후 참가자로 이동
    public List<Long> popFromWaitingList(int count) {
        Set<String> nextUsers = redisTemplate.opsForZSet().range(WAITING_KEY, 0, count-1);

        if (nextUsers != null && !nextUsers.isEmpty()) {
            List<Long> waitingList = new ArrayList<>();

            nextUsers.forEach(nextMemberId -> {
                redisTemplate.opsForZSet().remove(WAITING_KEY, nextMemberId);
                waitingList.add(Long.valueOf(nextMemberId));
            });

            return waitingList;
        }
        return null;
    }

    // 시간이 만료된 참가자 삭제
    public Long removeExpireParticipants(){
        return redisTemplate.opsForZSet().removeRangeByScore(PARTICIPANTS_KEY, 0, System.currentTimeMillis());
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
