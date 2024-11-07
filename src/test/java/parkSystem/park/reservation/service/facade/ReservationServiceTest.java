package parkSystem.park.reservation.service.facade;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.car.domain.enums.CarType;
import parkSystem.park.member.domain.Member;
import parkSystem.park.member.domain.enums.UserRole;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.domain.enums.ParkingType;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntityManager entityManager;

    Cars cars;

    ParkingSpot parkingSpot;

    @BeforeEach
    public void setUp() {
        Member member = new Member("123123", "k12002@nate.com", "테스트", "테스트", UserRole.EXAMPLE);
        entityManager.persist(member);

       cars = new Cars(CarType.EXAMPLE, "테스트 차량", "테스트 번호", member);
        entityManager.persist(cars);

        ParkingInfo parkingInfo = new ParkingInfo(
                "Downtown Parking",         // parkingName
                "123 Main St, Cityville",   // parkingAddress
                "Public",                   // parkType
                100,                        // parkingAmount
                "08:00",                    // weekDaysStartTime
                "20:00",                    // weekDaysEndTime
                "09:00",                    // weekendStartTime
                "22:00",                    // weekendEndTime
                "20.00",                    // dayTicketPrice
                "1000"
        );

        entityManager.persist(parkingInfo);


       parkingSpot = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

        entityManager.persist(parkingSpot);

    }


    @Test
    @DisplayName("해당 차량의 아이디와 주차장 아이디로 주차장 선예약")
    public void 주차장_예약() throws Exception {
       //given
        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId());

        //when
        ReservationResDTO reservation = reservationService.reservation(reservationReqDTO);

        Reservation findReservation = reservationRepository.findById(reservation.reservationId()).get();

        //then
        Assertions.assertThat(findReservation.getStatus()).isEqualTo(ReservationStatus.WAIT);

    }

}