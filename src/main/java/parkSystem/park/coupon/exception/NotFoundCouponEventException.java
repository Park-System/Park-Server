package parkSystem.park.coupon.exception;

public class NotFoundCouponEventException extends RuntimeException{
    public NotFoundCouponEventException() {
        super();
    }

    public NotFoundCouponEventException(String message) {
        super(message);
    }

    public NotFoundCouponEventException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundCouponEventException(Throwable cause) {
        super(cause);
    }

    protected NotFoundCouponEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
