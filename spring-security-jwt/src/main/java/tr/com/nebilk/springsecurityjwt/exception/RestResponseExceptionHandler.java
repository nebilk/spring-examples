package tr.com.nebilk.springsecurityjwt.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JWTVerificationException.class})
    protected ResponseEntity<Object> handleTokenResolveException(JWTVerificationException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = {UsernameTakenException.class})
    protected ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error_message", exception.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

}
