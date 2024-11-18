package parkSystem.park.reservation.controller.dto;

import parkSystem.park.reservation.domain.Reservation;

public record ReservationResDTO(
        Long reservationId
) {

    public static ReservationResDTO toDTO(Reservation reservation) {
        return new ReservationResDTO(reservation.getId());
    }
}
