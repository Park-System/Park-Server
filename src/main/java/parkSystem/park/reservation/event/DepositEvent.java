package parkSystem.park.reservation.event;


import lombok.Getter;

@Getter
public class DepositEvent {

    private Long reservationId;

    private String imPortId;

    public DepositEvent(Long reservationId, String imPortId) {
        this.reservationId = reservationId;
        this.imPortId = imPortId;
    }

    public DepositEvent() {
    }
}
