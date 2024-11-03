package parkSystem.park.park.dto.response;

import parkSystem.park.park.domain.ParkingSpot;
import parkSystem.park.park.domain.enums.ParkingType;

public record ParkingSpotResDTO(
         ParkingType parkingType,

         String parkingSpotName

) {

    public static ParkingSpotResDTO toDTO(ParkingSpot parkingSpot) {
        return new ParkingSpotResDTO(parkingSpot.getParkingType(), parkingSpot.getParkingSpotName());
    }

}
