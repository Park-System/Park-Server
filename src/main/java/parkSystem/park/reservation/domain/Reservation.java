package parkSystem.park.reservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.common.baseEntity.BaseEntity;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.payment.domain.Payment;
import parkSystem.park.reservation.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Cars car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpot parkingSpot;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime limitDepositTime;

    public Reservation(ReservationStatus status, Cars car, ParkingSpot parkingSpot) {
        this.status = status;
        this.car = car;
        this.parkingSpot = parkingSpot;
        countDown();
    }

    public static Reservation createReservation(Cars car, ParkingSpot parkingSpot){
        parkingSpot.getParkingInfo().decreaseParkingAmount(); // 예약 시에 주차 자리 감소;

        return new Reservation(ReservationStatus.WAIT, car, parkingSpot);
    }

    public void successDeposit(){
        this.status = ReservationStatus.COMPLETE;
    }

    public void failDeposit(){
        this.status = ReservationStatus.FAIL;
    }

    public void countDown(){
        limitDepositTime = this.getCreatedDate().plusMinutes(30);
    }
}
