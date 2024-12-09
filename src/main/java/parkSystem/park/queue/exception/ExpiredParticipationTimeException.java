package parkSystem.park.queue.exception;

public class ExpiredParticipationTimeException extends RuntimeException{
    public ExpiredParticipationTimeException() {
        super();
    }

    public ExpiredParticipationTimeException(String message) {
        super(message);
    }

    public ExpiredParticipationTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredParticipationTimeException(Throwable cause) {
        super(cause);
    }

    protected ExpiredParticipationTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
