package parkSystem.park.reservation.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.car.service.query.CarQueryService;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.service.query.ParkingSpotQueryService;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.repository.ReservationRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    private final ParkingSpotQueryService parkingSpotQueryService;

    private final CarQueryService carQueryService;

    public void reservation(Long carId, Long parkingSpotId){

        Cars findCars = carQueryService.findByCarId(carId); //보유 차량 찾아옴

        ParkingSpot findParking = parkingSpotQueryService.findParkingSpotById(parkingSpotId); // 주차자리 찾아옴

        Reservation reservation = Reservation.createReservation(findCars, findParking); //예약 대기 상태인 reservation 생성









    }










}
