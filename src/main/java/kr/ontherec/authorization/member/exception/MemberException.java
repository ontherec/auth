package kr.ontherec.authorization.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final MemberExceptionCode exceptionCode;

    public MemberException(MemberExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public int getHttpStatus() {
        return exceptionCode.getStatus().value();
    }
}
