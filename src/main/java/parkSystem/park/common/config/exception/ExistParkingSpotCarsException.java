package parkSystem.park.common.config.exception;

public class ExistParkingSpotCarsException extends RuntimeException {
    public ExistParkingSpotCarsException() {
    }

    public ExistParkingSpotCarsException(String message) {
        super(message);
    }

    public ExistParkingSpotCarsException(Throwable cause) {
        super(cause);
    }

    public ExistParkingSpotCarsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistParkingSpotCarsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
