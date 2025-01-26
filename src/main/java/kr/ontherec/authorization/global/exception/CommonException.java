package kr.ontherec.authorization.global.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final CommonExceptionCode exceptionCode;

    public CommonException(CommonExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
