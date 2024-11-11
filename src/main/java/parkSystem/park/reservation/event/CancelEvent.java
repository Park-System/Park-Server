package parkSystem.park.reservation.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import parkSystem.park.reservation.domain.Reservation;

@Getter
@AllArgsConstructor
public class CancelEvent {

    private Reservation reservation;




}
