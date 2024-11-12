package parkSystem.park.coupon.exception;

public class CouponEmptyException extends RuntimeException{
    public CouponEmptyException() {
        super();
    }

    public CouponEmptyException(String message) {
        super(message);
    }

    public CouponEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponEmptyException(Throwable cause) {
        super(cause);
    }

    protected CouponEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
