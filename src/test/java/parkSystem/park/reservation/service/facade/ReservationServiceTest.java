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
import parkSystem.park.park.service.command.ParkingInfoCommandService;
import parkSystem.park.park.service.command.ParkingSpotCommandService;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;

import java.util.List;


@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ParkingSpotCommandService parkingSpotCommandService;

    @Autowired
    private ParkingInfoCommandService parkingInfoCommandService;

    @Autowired
    private EntityManager entityManager;

    Cars cars;

    ParkingSpot parkingSpot;

    ParkingInfo parkingInfo;

    private static Long apply(Reservation reservation) {
        return reservation.getParkingSpot().getId();
    }

    @BeforeEach
    public void setUp() {
        Member member = new Member("123123", "k12002@nate.com", "테스트", "테스트", UserRole.EXAMPLE);
        entityManager.persist(member);

       cars = new Cars(CarType.EXAMPLE, "테스트 차량", "테스트 번호", member);
        entityManager.persist(cars);

     parkingInfo = new ParkingInfo(
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
        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(),parkingInfo.getId());

        //when
        ReservationResDTO reservation = reservationService.reservation(reservationReqDTO);

        Reservation findReservation = reservationRepository.findById(reservation.reservationId()).get();

        //then
        Assertions.assertThat(findReservation.getStatus()).isEqualTo(ReservationStatus.WAIT);
        Assertions.assertThat(findReservation.getParkingSpot().getParkingInfo().getParkingAmount()).isEqualTo(99);

    }

    @Test
    @DisplayName("에약을 취소하면 해당 주차자리는 예약 가능으로 업데이트 된다.")
    public void 주차장_자리_업데이트() throws Exception {
       //given
        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);
        ParkingSpot parkingSpot2 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

        entityManager.persist(parkingSpot1);
        entityManager.persist(parkingSpot2);

        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO1 = new ReservationReqDTO(cars.getId(), parkingSpot1.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO2= new ReservationReqDTO(cars.getId(), parkingSpot2.getId(), parkingInfo.getId());

        reservationService.reservation(reservationReqDTO);
        reservationService.reservation(reservationReqDTO1);
        reservationService.reservation(reservationReqDTO2);
        
       //when
        List<Reservation> all = reservationRepository.findAll();

        
        parkingSpotCommandService.updateParkingSpotAvailable(all);


        List<Reservation> findUpdate = reservationRepository.findAll();

        // 모든 spotAvailable 값이 true인지 확인
        boolean allSpotsAvailable = findUpdate.stream()
                .allMatch(reservation -> reservation.getParkingSpot().isSpotAvailable());


        //then

        Assertions.assertThat(findUpdate.get(0).getParkingSpot().isSpotAvailable()).isTrue();
        Assertions.assertThat(allSpotsAvailable).isTrue();
        Assertions.assertThat(parkingInfo.getParkingAmount()).isEqualTo(97);

    }



    @Test
    @DisplayName("결제 실패를 통한 주차장 수 RollBack 테스트")
    public void 주차장자리_수_RollBackTest() throws Exception {
        //given
        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);
        ParkingSpot parkingSpot2 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

        entityManager.persist(parkingSpot1);
        entityManager.persist(parkingSpot2);

        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO1 = new ReservationReqDTO(cars.getId(), parkingSpot1.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO2= new ReservationReqDTO(cars.getId(), parkingSpot2.getId(), parkingInfo.getId());

        reservationService.reservation(reservationReqDTO);
        reservationService.reservation(reservationReqDTO1);
        reservationService.reservation(reservationReqDTO2);

       //when

        List<Reservation> parkingAll = reservationRepository.findAll();


        parkingInfoCommandService.updateParkingInfoAmount(parkingAll);

        ParkingInfo saveParkingInfo = entityManager.find(ParkingInfo.class, parkingInfo.getId());


        //then

        Assertions.assertThat(saveParkingInfo.getParkingAmount()).isEqualTo(100);

    }




}