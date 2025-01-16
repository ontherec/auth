package kr.ontherec.authorization.security.exception;

import lombok.Getter;

@Getter
public class SecurityException extends RuntimeException {
    private final SecurityExceptionCode exceptionCode;

    public SecurityException(SecurityExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public int getHttpStatus() {
        return exceptionCode.getStatus().value();
    }
}
