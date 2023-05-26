package jp.co.axa.apidemo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserNotFoundException extends ApiException{

    public UserNotFoundException() {
    }

    public UserNotFoundException(HttpStatus httpStatus, String errorMessage, String errorCode) {
        super(httpStatus, errorMessage, errorCode);
    }

    public UserNotFoundException(String message, Throwable cause, HttpStatus httpStatus, String errorMessage, String errorCode) {
        super(message, cause, httpStatus, errorMessage, errorCode);
    }

    public UserNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
