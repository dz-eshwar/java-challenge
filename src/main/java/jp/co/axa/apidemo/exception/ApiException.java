package jp.co.axa.apidemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String errorMessage;
    private String errorCode;

    public ApiException(String message, Throwable cause, HttpStatus httpStatus, String errorMessage, String errorCode) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ApiException(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;

    }
}
