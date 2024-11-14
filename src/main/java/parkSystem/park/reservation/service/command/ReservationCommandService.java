package parkSystem.park.reservation.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.car.service.query.CarQueryService;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.service.command.ParkingInfoCommandService;
import parkSystem.park.park.service.command.ParkingSpotCommandService;
import parkSystem.park.park.service.query.ParkingSpotQueryService;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;
import parkSystem.park.reservation.service.query.ReservationQueryService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    private final ParkingSpotQueryService parkingSpotQueryService;

    private final ParkingSpotCommandService parkingSpotCommandService;

    private final ParkingInfoCommandService parkingInfoCommandService;

    private final CarQueryService carQueryService;

    private final ReservationQueryService reservationQueryService;



    /**
     *
     * 예매를 먼저 실행하여 예매 상태 변경 후 결제는 외부 API를 사용하기 때문에 이벤트처리를 하여
     * 트랜잭션 분리
     * @param
     */

    public ReservationResDTO reservation(ReservationReqDTO reservationReqDTO){

        Cars findCars = carQueryService.findByCarId(reservationReqDTO.carsId()); //보유 차량 찾아옴

        ParkingSpot findParking = parkingSpotQueryService.findParkingSpotById(reservationReqDTO.parkingSpotId()); // 주차자리 찾아옴

        ParkingInfo parkingInfo = parkingInfoCommandService.getParkingInfo(reservationReqDTO.parkingInfoId());


        Reservation reservation = Reservation.createReservation(findCars, findParking, parkingInfo); //예약 대기 상태인 reservation 생성


        //여기서 저장하는데
        Reservation saveReservation = reservationRepository.save(reservation);


        return ReservationResDTO.toDTO(saveReservation);
    }


    public void cancelReservation(Long reservationId){

        log.info("예약 아이디 ={}", reservationId);

        Reservation findByReservation = reservationQueryService.findReservationById(reservationId);

        findByReservation.failDeposit();
    }

    public void bulkReservationRollBack() {
        log.info("RollBack reservation");

        List<Reservation> reservationList = reservationQueryService.findByWaitStats(ReservationStatus.FAIL);

        if(!reservationList.isEmpty()) {
            parkingSpotCommandService.updateParkingSpotAvailable(reservationList);

            parkingInfoCommandService.updateParkingInfoAmount(reservationList);

            List<Long> longs = reservationList.stream().map(Reservation::getId).toList();

            updateBulkReservation(ReservationStatus.ARCHIVED, longs);
        }

    }


    public void bulk_update_CancelStatus() {
        log.info("RollBack cancel status");

        List<Reservation> reservationList = reservationQueryService.findByWaitAndLimit(ReservationStatus.WAIT);

        if(!reservationList.isEmpty()){
            log.info("limit Time reservation");
            List<Long> longs = reservationList.stream().map(Reservation::getId).toList();

            updateBulkReservation(ReservationStatus.FAIL,longs);
        }
    }


    public void updateBulkReservation(ReservationStatus reservationStatus, List<Long> reservationIds){
        reservationRepository.updateStatus(reservationStatus, reservationIds);
    }


}
