package kr.ontherec.authorization.global.model;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {
    HttpStatus getStatus();
    String getMessage();
}
