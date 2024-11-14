package parkSystem.park.reservation.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.service.command.ReservationCommandService;
import parkSystem.park.reservation.service.redis.RedisService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationCommandService reservationCommandService;

    private final RedisService redisService;


    /**
     *
     * 레디스에 키와 ttl 저장
     *
     */

    public ReservationResDTO reservation(ReservationReqDTO reqDTO) {

        ReservationResDTO reservation = reservationCommandService.reservation(reqDTO);

        redisService.setAuctionExpiredKey(reservation.reservationId());

        return  reservation;
    }


    public void bulkReservation(){
        reservationCommandService.bulkReservationRollBack();
    }


    public void bulkUpdateCancel(){
        reservationCommandService.bulk_update_CancelStatus();
    }







}
