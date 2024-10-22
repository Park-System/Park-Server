package parkSystem.park.reservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.car.domain.Cars;
import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.payment.domain.Payment;
import parkSystem.park.reservation.domain.enums.ReservationStatus;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {

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

    public Reservation(ReservationStatus status, Cars car, ParkingSpot parkingSpot, Payment payment) {
        this.status = status;
        this.car = car;
        this.parkingSpot = parkingSpot;
        this.payment = payment;
    }
}
