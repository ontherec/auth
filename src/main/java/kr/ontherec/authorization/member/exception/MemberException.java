package kr.ontherec.authorization.member.exception;

import kr.ontherec.authorization.global.exception.CustomException;
import lombok.Getter;

@Getter
public class MemberException extends CustomException {

    public MemberException(MemberExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
