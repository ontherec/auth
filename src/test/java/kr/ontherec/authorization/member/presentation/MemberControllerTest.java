package kr.ontherec.authorization.member.presentation;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static io.restassured.RestAssured.given;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_NICKNAME;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_PHONE_NUMBER;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_USERNAME;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import io.restassured.http.ContentType;
import java.util.Arrays;
import kr.ontherec.authorization.infra.IntegrationTest;
import kr.ontherec.authorization.member.domain.Authority;
import kr.ontherec.authorization.member.dto.MemberResponseDto;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class MemberControllerTest extends IntegrationTest {

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "new",
                "password",
                null,
                "new",
                "010-0000-0001",
                null
        );

        given(getSpec())
                .contentType(ContentType.JSON)
                .body(dto)
                .filter(RestAssuredRestDocumentationWrapper.document(
                "signup",
                        resource(ResourceSnippetParameters.builder()
                                .tag("member")
                                .summary("signup")
                                .description("회원가입")
                                .requestSchema(Schema.schema(MemberSignUpRequestDto.class.getSimpleName()))
                                .requestFields(
                                        fieldWithPath("username")
                                                .type(JsonFieldType.STRING)
                                                .description("아이디"),
                                        fieldWithPath("password")
                                                .type(JsonFieldType.STRING)
                                                .description("비밀번호")
                                                .optional(),
                                        fieldWithPath("name")
                                                .type(JsonFieldType.STRING)
                                                .description("이름")
                                                .optional(),
                                        fieldWithPath("nickname")
                                                .type(JsonFieldType.STRING)
                                                .description("닉네임"),
                                        fieldWithPath("phoneNumber")
                                                .type(JsonFieldType.STRING)
                                                .description("전화번호"),
                                        fieldWithPath("picture")
                                                .type(JsonFieldType.STRING)
                                                .description("프로필 사진")
                                                .optional())
                                .responseSchema(Schema.schema(MemberResponseDto.class.getSimpleName()))
                                .responseFields(
                                        fieldWithPath("id")
                                                .type(JsonFieldType.NUMBER)
                                                .description("식별자"),
                                        fieldWithPath("username")
                                                .type(JsonFieldType.STRING)
                                                .description("아이디"),
                                        fieldWithPath("name")
                                                .type(JsonFieldType.STRING)
                                                .description("이름")
                                                .optional(),
                                        fieldWithPath("nickname")
                                                .type(JsonFieldType.STRING)
                                                .description("닉네임"),
                                        fieldWithPath("phoneNumber")
                                                .type(JsonFieldType.STRING)
                                                .description("전화번호"),
                                        fieldWithPath("picture")
                                                .type(JsonFieldType.STRING)
                                                .description("프로필 사진 URL")
                                                .optional(),
                                        fieldWithPath("roles")
                                                .type(JsonFieldType.ARRAY)
                                                .description("권한 목록 " + Arrays.toString(Authority.values()))
                                                .attributes(key("itemsType").value("ENUM"))
                                                .optional())
                                .build())))
        .when()
                .post("/signup")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/v1/me")
                .body("id", notNullValue())
                .body("username", equalTo(dto.username()));

    }

    @Test
    @DisplayName("사용자 회원가입 실패 - ID 중복")
    void signUpWithDuplicatedUsername() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "test",
                "password",
                null,
                "new",
                "010-0000-0001",
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/signup")
                .then()
                .statusCode(EXIST_USERNAME.getStatus().value())
                .body("message", equalTo(EXIST_USERNAME.getMessage()));

    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 닉네임 중복")
    void signUpWithDuplicatedNickname() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "new",
                "password",
                null,
                "nickname",
                "010-0000-0001",
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/signup")
                .then()
                .statusCode(EXIST_NICKNAME.getStatus().value())
                .body("message", equalTo(EXIST_NICKNAME.getMessage()));

    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 전화번호 중복")
    void signUpWithDuplicatedPhoneNumber() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "new",
                "password",
                null,
                "new",
                "010-0000-0000",
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/signup")
                .then()
                .statusCode(EXIST_PHONE_NUMBER.getStatus().value())
                .body("message", equalTo(EXIST_PHONE_NUMBER.getMessage()));

    }
}