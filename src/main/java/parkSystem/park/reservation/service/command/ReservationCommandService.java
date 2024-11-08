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
import parkSystem.park.park.service.query.ParkingSpotQueryService;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.repository.ReservationRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    private final ParkingSpotQueryService parkingSpotQueryService;

    private final ParkingInfoCommandService parkingInfoCommandService;

    private final CarQueryService carQueryService;


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

        Reservation saveReservation = reservationRepository.save(reservation);

        return ReservationResDTO.toDTO(saveReservation);
    }











}
