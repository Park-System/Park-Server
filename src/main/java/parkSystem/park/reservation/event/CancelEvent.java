package parkSystem.park.reservation.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelEvent {

    private Long reservationId;

}
