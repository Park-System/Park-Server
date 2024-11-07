package parkSystem.park.reservation.controller.dto;

import jakarta.validation.constraints.NotNull;

public record ReservationReqDTO(
        @NotNull(message = "null안됨")
        Long carsId,
        @NotNull(message = "null")
        Long parkingSpotId
) {
}
