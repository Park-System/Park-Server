package parkSystem.park.reservation.controller.dto;

public record ReservationReqDTO(
        Long carsId,
        Long parkingSpotId,
        String importId
) {
}
