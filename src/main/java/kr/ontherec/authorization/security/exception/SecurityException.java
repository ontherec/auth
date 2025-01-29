package kr.ontherec.authorization.security.exception;

import kr.ontherec.authorization.global.exception.CustomException;
import lombok.Getter;

@Getter
public class SecurityException extends CustomException {

    public SecurityException(SecurityExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
