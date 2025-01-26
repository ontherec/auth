package kr.ontherec.authorization.security.exception;

import kr.ontherec.authorization.global.exception.ExceptionCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SecurityExceptionCode implements ExceptionCode {
	UNAUTHORIZED("AS001", HttpStatus.UNAUTHORIZED, "유효한 자격 증명이 없습니다."),
	FORBIDDEN("AS002", HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다.");

	private final String code;
	private final HttpStatus status;
	private final String message;
}
