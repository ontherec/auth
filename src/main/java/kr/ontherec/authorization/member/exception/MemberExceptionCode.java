package kr.ontherec.authorization.member.exception;

import kr.ontherec.authorization.global.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MemberExceptionCode implements ExceptionCode {

    // 400 Bad Request
    EXIST_USERNAME(HttpStatus.BAD_REQUEST, "이미 사용 중인 ID 입니다."),
    EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임 입니다."),
    EXIST_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "이미 사용 중인 전화번호 입니다."),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 사용자를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
