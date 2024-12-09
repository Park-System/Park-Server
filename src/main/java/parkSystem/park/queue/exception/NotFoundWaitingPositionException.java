package parkSystem.park.queue.exception;

public class NotFoundWaitingPositionException extends RuntimeException{
    public NotFoundWaitingPositionException() {
        super();
    }

    public NotFoundWaitingPositionException(String message) {
        super(message);
    }

    public NotFoundWaitingPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundWaitingPositionException(Throwable cause) {
        super(cause);
    }

    protected NotFoundWaitingPositionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
