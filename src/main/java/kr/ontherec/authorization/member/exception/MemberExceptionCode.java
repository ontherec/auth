package kr.ontherec.authorization.member.exception;

import kr.ontherec.authorization.global.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberExceptionCode implements ExceptionCode {

    EXIST_USERNAME("AM001", HttpStatus.BAD_REQUEST, "이미 사용 중인 ID 입니다."),
    EXIST_NICKNAME("AM002", HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임 입니다."),
    EXIST_PHONE_NUMBER("AM003", HttpStatus.BAD_REQUEST, "이미 사용 중인 전화번호 입니다."),
    NOT_FOUND("AM004", HttpStatus.NOT_FOUND, "요청하신 사용자를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;
}
