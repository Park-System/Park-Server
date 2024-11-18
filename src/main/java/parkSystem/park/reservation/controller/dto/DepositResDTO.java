package parkSystem.park.reservation.controller.dto;

public record DepositResDTO(
        boolean success,
        String message
) {

    public static DepositResDTO success(String message) {
        return new DepositResDTO(true, message);
    }
}
