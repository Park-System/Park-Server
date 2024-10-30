package parkSystem.park.common.config.dto;

public record ErrorResult(String code, String message) {


    public static ErrorResult toDto(String code, String message) {
        return new ErrorResult(code, message);
    }
}
