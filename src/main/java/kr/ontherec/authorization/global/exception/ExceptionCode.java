package kr.ontherec.authorization.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getStatus();
    String getMessage();
}
