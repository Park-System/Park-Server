package parkSystem.park.common.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.config.dto.ErrorResult;
import parkSystem.park.coupon.exception.CouponEmptyException;
import parkSystem.park.coupon.exception.NotFoundCouponEventException;
import parkSystem.park.coupon.exception.NotFoundCouponException;

@RestController
@Slf4j
public class ApiExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> handleNotFoundException(NotFoundException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CouponEmptyException.class)
    public ResponseEntity<ErrorResult> couponEmptyException(CouponEmptyException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("BAD_REQUEST", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundCouponEventException.class)
    public ResponseEntity<ErrorResult> notFoundCouponEventException(NotFoundCouponEventException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("BAD_REQUEST", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundCouponException.class)
    public ResponseEntity<ErrorResult> notFoundCouponException(NotFoundCouponException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("BAD_REQUEST", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
