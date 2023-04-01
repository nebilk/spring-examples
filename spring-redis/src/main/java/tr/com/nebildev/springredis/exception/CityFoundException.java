package tr.com.nebildev.springredis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class CityFoundException extends RuntimeException {
    public CityFoundException() {
    }

    public CityFoundException(String message) {
        super(message);
    }

    public CityFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
