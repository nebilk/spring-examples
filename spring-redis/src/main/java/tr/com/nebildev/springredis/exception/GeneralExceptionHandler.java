package tr.com.nebildev.springredis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<?> handleCityNotFoundException(CityNotFoundException cityNotFoundException){
        return new ResponseEntity<>(cityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CityFoundException.class)
    public ResponseEntity<?> handleCityFoundException(CityFoundException cityFoundException){
        return new ResponseEntity<>(cityFoundException.getMessage(), HttpStatus.CONFLICT);
    }
}
