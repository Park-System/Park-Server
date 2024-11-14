package parkSystem.park.common.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import parkSystem.park.common.config.dto.ErrorResult;

@RestController
@Slf4j
public class ApiExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResult> handleNotFoundException(NotFoundException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("NOT_FOUND", ex.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImportException.class)
    public ResponseEntity<ErrorResult> handleNotFoundException(ImportException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("NOT_FOUND", "ImportException"), HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistParkingSpotCarsException.class)
    public ResponseEntity<ErrorResult> handleNotFoundException(ExistParkingSpotCarsException ex) {
        log.error("[exceptionHandle] ex", ex);

        return new ResponseEntity<>(ErrorResult.toDto("BAD_REQUEST", ex.getMessage()), HttpStatus.NOT_FOUND);
    }



}
