package parkSystem.park.reservation.service.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.park.service.command.ParkingInfoCommandService;
import parkSystem.park.park.service.command.ParkingSpotCommandService;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.service.command.ReservationCommandService;
import parkSystem.park.reservation.service.query.ReservationQueryService;
import parkSystem.park.reservation.service.redis.RedisService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationCommandService reservationCommandService;

    private final ReservationQueryService reservationQueryService;

    private final RedisService redisService;

    private final ParkingSpotCommandService parkingSpotCommandService;

    private final ParkingInfoCommandService parkingInfoCommandService;


    /**
     *
     * 레디스에 키와 ttl 저장
     *
     */
    @Transactional
    public ReservationResDTO reservation(ReservationReqDTO reqDTO) {

        ReservationResDTO reservation = reservationCommandService.reservation(reqDTO);

        redisService.setAuctionExpiredKey(reservation.reservationId());

        return  reservation;
    }

    @Transactional
    public void bulk_reservationRollBack() {
        log.info("RollBack reservation");

        List<Reservation> reservationList = reservationQueryService.findByWaitStats(ReservationStatus.FAIL);

        if(!reservationList.isEmpty()) {
            parkingSpotCommandService.updateParkingSpotAvailable(reservationList);

            parkingInfoCommandService.updateParkingInfoAmount(reservationList);
        }

    }

    public void bulk_update_CancelStatus() {
        log.info("RollBack cancel status");

        List<Reservation> reservationList = reservationQueryService.findByWaitStats(ReservationStatus.WAIT);

        if(!reservationList.isEmpty()){
            log.info("limit Time reservation");
            List<Long> longs = reservationList.stream().map(Reservation::getId).toList();

            reservationCommandService.cancelBulkReservation(longs);
        }
    }


}
