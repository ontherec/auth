package kr.ontherec.authorization.global.exception;

import static kr.ontherec.authorization.global.exception.CommonExceptionCode.FORBIDDEN;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.INTERNAL_SERVER_ERROR;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.MISSING_PARAMETER;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.NOT_FOUND;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.NOT_VALID;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.TYPE_MISMATCH;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.UNAUTHORIZED;
import static kr.ontherec.authorization.global.exception.CommonExceptionCode.UNSUPPORTED_MEDIA_TYPE;

import java.io.PrintWriter;
import java.io.StringWriter;
import kr.ontherec.authorization.global.model.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ResponseTemplate> handleCustomException(CustomException ex) {
		log.error("🚨 CustomException occurred: {} 🚨\n{}", ex.getMessage(), getStackTraceAsString(ex));
		return ResponseEntity.status(ex.getExceptionCode().getStatus())
			.body(ResponseTemplate.error(ex.getExceptionCode()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseTemplate> handleServerException(RuntimeException ex) {
		log.error("🚨 InternalException occurred: {} 🚨\n{}", ex.getMessage(), getStackTraceAsString(ex));
		return ResponseEntity.status(INTERNAL_SERVER_ERROR.getStatus())
			.body(ResponseTemplate.error(INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ResponseTemplate> handleNotFoundException() {
		return ResponseEntity.status(NOT_FOUND.getStatus())
			.body(ResponseTemplate.error(NOT_FOUND));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseTemplate> handleValidationError() {
		return ResponseEntity.status(NOT_VALID.getStatus())
			.body(ResponseTemplate.error(NOT_VALID));
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ResponseTemplate> handleHandlerMethodValidationException() {
		return ResponseEntity.status(NOT_VALID.getStatus())
			.body(ResponseTemplate.error(NOT_VALID));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseTemplate> handleHttpMessageNotReadableException() {
		return ResponseEntity.status(NOT_VALID.getStatus())
			.body(ResponseTemplate.error(NOT_VALID));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseTemplate> handleMethodArgumentTypeMismatchException() {
		return ResponseEntity.status(TYPE_MISMATCH.getStatus())
			.body(ResponseTemplate.error(TYPE_MISMATCH));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ResponseTemplate> handleMissingServletRequestParameterException() {
		return ResponseEntity.status(MISSING_PARAMETER.getStatus())
			.body(ResponseTemplate.error(MISSING_PARAMETER));
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ResponseTemplate> handleHttpMediaTypeNotSupportedException() {
		return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE.getStatus())
			.body(ResponseTemplate.error(UNSUPPORTED_MEDIA_TYPE));
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ResponseTemplate> handleAuthenticationException() {
		return ResponseEntity.status(UNAUTHORIZED.getStatus())
				.body(ResponseTemplate.error(UNAUTHORIZED));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseTemplate> handleAccessDeniedException() {
		return ResponseEntity.status(FORBIDDEN.getStatus())
				.body(ResponseTemplate.error(FORBIDDEN));
	}

	private String getStackTraceAsString(RuntimeException ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
