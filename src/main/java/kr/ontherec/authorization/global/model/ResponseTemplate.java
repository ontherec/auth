package kr.ontherec.authorization.global.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseTemplate {

    private final boolean success;
    private final String message;
    private Object data;

    public static ResponseTemplate success(SuccessCode code) {
        return new ResponseTemplate(true, code.message(), null);
    }

    public static  ResponseTemplate success(SuccessCode code, Object data) {
        return new ResponseTemplate(true, code.message(), data);
    }

    public static ResponseTemplate error(ExceptionCode code) {
        return new ResponseTemplate(false, code.message(), null);
    }
}
