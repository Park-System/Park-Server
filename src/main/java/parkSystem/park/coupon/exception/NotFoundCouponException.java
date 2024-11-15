package parkSystem.park.coupon.exception;

public class NotFoundCouponException extends RuntimeException{
    public NotFoundCouponException() {
        super();
    }

    public NotFoundCouponException(String message) {
        super(message);
    }

    public NotFoundCouponException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundCouponException(Throwable cause) {
        super(cause);
    }

    protected NotFoundCouponException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
