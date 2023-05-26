package jp.co.axa.apidemo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIAbortedException extends ApiException{
    public APIAbortedException(HttpStatus httpStatus, String errorMessage, String errorCode) {
        super(httpStatus, errorMessage, errorCode);
    }

    public APIAbortedException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
