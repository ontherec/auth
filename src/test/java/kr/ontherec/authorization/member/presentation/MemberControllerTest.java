package kr.ontherec.authorization.member.presentation;

import static io.restassured.RestAssured.given;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_NICKNAME;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_PHONE_NUMBER;
import static kr.ontherec.authorization.member.exception.MemberExceptionCode.EXIST_USERNAME;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import kr.ontherec.authorization.infra.IntegrationTest;
import kr.ontherec.authorization.member.dto.MemberSignUpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;

@IntegrationTest
class MemberControllerTest {

    @LocalServerPort
    private int port;

    private RequestSpecification spec;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/v1";
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder().addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "new",
                "password",
                null,
                "new",
                "010-0000-0001",
                null,
                null
        );

        given(this.spec)
                .contentType(ContentType.JSON)
                .body(dto)
                .filter(RestAssuredRestDocumentationWrapper.document("index")
                )
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
                null,
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
                null,
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
                null,
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