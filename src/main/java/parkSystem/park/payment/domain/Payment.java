package parkSystem.park.payment.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import parkSystem.park.payment.domain.enums.PaymentStatus;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String discountRate;


    public Payment(String discountRate, Integer totalPrice, PaymentStatus paymentStatus) {
        this.discountRate = discountRate;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }
}
