package parkSystem.park.reservation.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import parkSystem.park.reservation.event.CancelEvent;
import parkSystem.park.reservation.service.query.ReservationQueryService;

@Component
@Slf4j
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private final ApplicationEventPublisher eventPublisher;

    private final ReservationQueryService reservationQueryService;

    public RedisKeyExpiredListener(
            RedisMessageListenerContainer listenerContainer,
            ApplicationEventPublisher eventPublisher,
            ReservationQueryService reservationQueryService) {
        super(listenerContainer);
        this.eventPublisher = eventPublisher;
        this.reservationQueryService = reservationQueryService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        log.info("이벤트");
        String messageToStr = message.toString();

        if(messageToStr.startsWith("reservationId:")){
            Long reservationId = Long.parseLong(messageToStr.split(":")[1]);

            reservationQueryService.findReservationById(reservationId);

            eventPublisher.publishEvent(new CancelEvent(reservationId));
        }
    }




}
