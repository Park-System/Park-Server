package parkSystem.park.reservation.service.facade;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import parkSystem.park.car.CarRepository;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.car.domain.enums.CarType;
import parkSystem.park.park.domain.ParkingInfo;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.domain.enums.ParkingType;
import parkSystem.park.park.repository.ParkingInfoRepository;
import parkSystem.park.park.repository.ParkingSpotRepository;
import parkSystem.park.park.service.command.ParkingInfoCommandService;
import parkSystem.park.park.service.command.ParkingSpotCommandService;
import parkSystem.park.reservation.controller.dto.ReservationReqDTO;
import parkSystem.park.reservation.controller.dto.ReservationResDTO;
import parkSystem.park.reservation.domain.Reservation;
import parkSystem.park.reservation.domain.enums.ReservationStatus;
import parkSystem.park.reservation.repository.ReservationRepository;
import parkSystem.park.reservation.service.command.ReservationCommandService;
import parkSystem.park.reservation.service.query.ReservationQueryService;

import java.util.List;


@SpringBootTest
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
    private ReservationQueryService reservationQueryService;

    @Autowired
    private ReservationCommandService reservationCommandService;


    @Autowired
     private CarRepository carRepository;

    @Autowired
    private ParkingInfoRepository parkingInfoRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;


    Cars cars;

    ParkingSpot parkingSpot;

    ParkingInfo parkingInfo;




    @BeforeEach
    public void setUp() {

       cars = new Cars(CarType.EXAMPLE, "테스트 차량", "테스트 번호", null);
       carRepository.save(cars);


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

       parkingInfoRepository.save(parkingInfo);


       parkingSpot = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

      parkingSpotRepository.save(parkingSpot);

    }


    @AfterEach
    public void tearDown() {

        reservationRepository.deleteAll();

        parkingSpotRepository.deleteAll();

        parkingInfoRepository.deleteAll();

        carRepository.deleteAll();

    }


    @Test
    @DisplayName("해당 차량의 아이디와 주차장 아이디로 주차장 선예약")
    @Transactional
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
    @Transactional
    public void 주차장_자리_업데이트() throws Exception {
       //given
        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);
        ParkingSpot parkingSpot2 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

       parkingSpotRepository.save(parkingSpot1);
       parkingSpotRepository.save(parkingSpot2);

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


        //then

        Assertions.assertThat(findUpdate.get(0).getParkingSpot().isSpotAvailable()).isTrue();
        Assertions.assertThat(parkingInfo.getParkingAmount()).isEqualTo(97);

    }



    @Test
    @DisplayName("결제 실패를 통한 주차장 수 RollBack 테스트")
    public void 주차장자리_수_RollBackTest() throws Exception {
        //given
        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);
        ParkingSpot parkingSpot2 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

        parkingSpotRepository.save(parkingSpot1);
        parkingSpotRepository.save(parkingSpot2);

        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO1 = new ReservationReqDTO(cars.getId(), parkingSpot1.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO2= new ReservationReqDTO(cars.getId(), parkingSpot2.getId(), parkingInfo.getId());

        reservationService.reservation(reservationReqDTO);
        reservationService.reservation(reservationReqDTO1);
        reservationService.reservation(reservationReqDTO2);

       //when

        List<Reservation> parkingAll = reservationRepository.findAll();


        parkingInfoCommandService.updateParkingInfoAmount(parkingAll);

        ParkingInfo saveParkingInfo = parkingInfoRepository.findById(parkingInfo.getId()).get();


        //then

        Assertions.assertThat(saveParkingInfo.getParkingAmount()).isEqualTo(100);

    }

    /**
     * 해당 테스트 코드는 주석처리 할려면 ttl 시간을 조정해야하기 때문에 주석처리해놈
     */


    @Test
    @DisplayName("예약 후 redis keyNotification을 활용한 6초후에 예약 실패 상태 변경 예약 실패 상태 변경 후 예약 실패 상태 롤백 테스트")
    public void 예약실패_레디스_테스트() throws Exception {
       //given

        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());

        ReservationResDTO saveReservation = reservationService.reservation(reservationReqDTO);


        // then: 비동기 이벤트가 발생하고 상태가 FAIL로 변경되었는지 확인
        Thread.sleep(6000); // 3초 후 비동기 작업이 완료되도록 기다림

        Reservation reservation = reservationRepository.findById(saveReservation.reservationId()).get();

        //then
        Assertions.assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.FAIL);


        // 이벤트가 완료되었음을 확인
    }


    @Test
    @DisplayName("redis가 죽을 가능성을 대비해 스케줄러를 통한 예약 limit 시간 초과하면 실패 상태로 업데이트")
    public void 레디스_죽을시_스케줄러를_통한_() throws Exception {
       //given
        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());

        ReservationResDTO saveReservation = reservationCommandService.reservation(reservationReqDTO); // 해당 예약은 redis를 사용하지 않은 순수 예약


       //when
        Thread.sleep(6000); //현재 만료시간 5초 이므로

        reservationService.bulk_update_CancelStatus();

        Reservation findByReservation = reservationQueryService.findReservationById(saveReservation.reservationId());
        //then
        Assertions.assertThat(findByReservation.getStatus()).isEqualTo(ReservationStatus.FAIL);

    }



    @Test
    @DisplayName("예약을 실패 할 시에 전체 데이터 RollBack")
    public void 예약_실패_상태일때_전체데이터_롤백() throws Exception {
       //given

        ParkingSpot parkingSpot1 = new ParkingSpot(ParkingType.NORMAL, "테스트", parkingInfo);

        parkingSpotRepository.save(parkingSpot1);

        ReservationReqDTO reservationReqDTO = new ReservationReqDTO(cars.getId(), parkingSpot.getId(), parkingInfo.getId());
        ReservationReqDTO reservationReqDTO1 = new ReservationReqDTO(cars.getId(), parkingSpot1.getId(), parkingInfo.getId());


        ReservationResDTO reservation = reservationCommandService.reservation(reservationReqDTO);
        reservationCommandService.reservation(reservationReqDTO1);// 해당 예약은 r

       //when
        Thread.sleep(6000);

        ParkingSpot parkingSpots = parkingSpotRepository.findById(parkingSpot.getId()).get();
        ParkingInfo parkingInfos = parkingInfoRepository.findById(parkingInfo.getId()).get();

        System.out.println("parkingSpots.isSpotAvailable() = " + parkingSpots.isSpotAvailable());
        System.out.println("parkingInfos.getParkingAmount() = " + parkingInfos.getParkingAmount());


        reservationService.bulk_update_CancelStatus();

        reservationService.bulk_reservationRollBack();

        ParkingInfo findParkingInfo = parkingInfoRepository.findById(parkingInfo.getId()).get();

        ParkingSpot findParkingSpot = parkingSpotRepository.findById(parkingSpot.getId()).get();

        Reservation findByReservation = reservationRepository.findById(reservation.reservationId()).get();

        //then
        Assertions.assertThat(findParkingSpot.isSpotAvailable()).isEqualTo(true);
        Assertions.assertThat(findParkingInfo.getParkingAmount()).isEqualTo(100);
        Assertions.assertThat(findByReservation.getStatus()).isEqualTo(ReservationStatus.ARCHIVED);


    }


}