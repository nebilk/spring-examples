package tr.com.nebildev.springsecurityjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameTakenException extends RuntimeException{

    public UsernameTakenException(){
        super();
    }

    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameTakenException(String message) {
        super(message);
    }

}
