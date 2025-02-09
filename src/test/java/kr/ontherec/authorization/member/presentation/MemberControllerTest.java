package kr.ontherec.authorization.member.presentation;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static io.restassured.RestAssured.given;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_NICKNAME;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_PHONE_NUMBER;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_USERNAME;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import com.epages.restdocs.apispec.Schema;
import io.restassured.http.ContentType;
import kr.ontherec.authorization.infra.IntegrationTest;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import kr.ontherec.authorization.member.dto.MemberUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

class MemberControllerTest extends IntegrationTest {

    @Value("${authorization-server.api-key}")
    private String API_KEY;

    @Test
    @DisplayName("사용자 회원가입 성공")
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
                                                .description("프로필 사진 URL")
                                                .optional())
                                .build())))
        .when()
                .post("/members")
        .then()
                .statusCode(HttpStatus.CREATED.value())
                        .header("Location", startsWith("/v1/members"));
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 중복된 ID")
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
                .post("/members")
        .then()
                .statusCode(EXIST_USERNAME.getStatus().value())
                .body("message", equalTo(EXIST_USERNAME.getMessage()));
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 중복된 닉네임")
    void signUpWithDuplicatedNickname() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "new",
                "password",
                null,
                "test",
                "010-0000-0001",
                null
        );

        given()
                .contentType(ContentType.JSON)
                .body(dto)
        .when()
                .post("/members")
        .then()
                .statusCode(EXIST_NICKNAME.getStatus().value())
                .body("message", equalTo(EXIST_NICKNAME.getMessage()));
    }

    @Test
    @DisplayName("사용자 회원가입 실패 - 중복된 전화번호")
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
                .post("/members")
        .then()
                .statusCode(EXIST_PHONE_NUMBER.getStatus().value())
                .body("message", equalTo(EXIST_PHONE_NUMBER.getMessage()));
    }

    @Test
    @DisplayName("내 정보 수정 성공")
    void update() {

        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "password",
                null,
                "new",
                "010-0000-0001",
                null
        );

        given(getSpec())
                .header("X-API-KEY", API_KEY)
                .contentType(ContentType.JSON)
                .body(dto)
                .filter(RestAssuredRestDocumentationWrapper.document(
                        "update",
                        resource(ResourceSnippetParameters.builder()
                                .tag("member")
                                .summary("update")
                                .description("내 정보 수정")
                                .requestSchema(Schema.schema(MemberUpdateRequestDto.class.getSimpleName()))
                                .requestFields(
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
                                                .description("닉네임")
                                                .optional(),
                                        fieldWithPath("phoneNumber")
                                                .type(JsonFieldType.STRING)
                                                .description("전화번호")
                                                .optional(),
                                        fieldWithPath("picture")
                                                .type(JsonFieldType.STRING)
                                                .description("프로필 사진 URL")
                                                 .optional())
                                .build())))
        .when()
                .patch("/members/me")
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("내 정보 수정 실패 - 중복된 닉네임")
    void updateWithDuplicatedNickname() {
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "password",
                null,
                "test",
                "010-0000-0001",
                null
        );

        given()
                .header("X-API-KEY", API_KEY)
                .contentType(ContentType.JSON)
                .body(dto)
        .when()
                .patch("/members/me")
        .then()
                .statusCode(EXIST_NICKNAME.getStatus().value())
                .body("message", equalTo(EXIST_NICKNAME.getMessage()));
    }

    @Test
    @DisplayName("내 정보 수정 실패 - 중복된 전화번호")
    void updateWithDuplicatedPhoneNumber() {
        MemberUpdateRequestDto dto = new MemberUpdateRequestDto(
                "password",
                null,
                "new",
                "010-0000-0000",
                null
        );

        given()
                .header("X-API-KEY", API_KEY)
                .contentType(ContentType.JSON)
                .body(dto)
        .when()
                .patch("/members/me")
        .then()
                .statusCode(EXIST_PHONE_NUMBER.getStatus().value())
                .body("message", equalTo(EXIST_PHONE_NUMBER.getMessage()));
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void withdraw() {
        given(getSpec())
                .header("X-API-KEY", API_KEY)
                .contentType(ContentType.JSON)
                .filter(RestAssuredRestDocumentationWrapper.document(
                        "withdraw",
                        resource(ResourceSnippetParameters.builder()
                                .tag("member")
                                .summary("withdraw")
                                .description("회원탈퇴")
                                .build())))
        .when()
                .delete("/members/me")
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("회원탈퇴 실패 - 미인증된 사용자")
    void withdrawWithoutAuthentication() {
        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/members/me")
        .then()
                .statusCode(HttpStatus.FOUND.value())
                .header("Location", endsWith("/login"));
    }
}