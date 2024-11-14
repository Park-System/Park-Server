package parkSystem.park.reservation.service.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.service.query.ReservationQueryService;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisService  {

    private final RedisTemplate<String,String> redisTemplate;

    private final ReservationQueryService reservationQueryService;


    @Transactional
    public void setAuctionExpiredKey(Long reservationId){

        log.info("에약아이디 ={}" , reservationId);

        Reservation reservation = reservationQueryService.findReservationById(reservationId);


        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        log.info("reservation.time = {}", reservation.getCreatedDate());

        Duration ttl = Duration.between(LocalDateTime.now(), reservation.getLimitDepositTime());

        Duration testDuration = Duration.ofSeconds(5);// 5초 TTL 설정


        String redisKey = "reservationId:" + reservationId;

        stringStringValueOperations.set(redisKey, "1", testDuration);

        log.info("예약 만료 시간 설정: " + ttl.toMinutes() + "분 후");

    }





}
