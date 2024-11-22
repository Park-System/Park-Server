package parkSystem.park.luckDraw.exception;

public class NotFoundLuckyDrawException extends RuntimeException{
    public NotFoundLuckyDrawException() {
        super();
    }

    public NotFoundLuckyDrawException(String message) {
        super(message);
    }

    public NotFoundLuckyDrawException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundLuckyDrawException(Throwable cause) {
        super(cause);
    }

    protected NotFoundLuckyDrawException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
