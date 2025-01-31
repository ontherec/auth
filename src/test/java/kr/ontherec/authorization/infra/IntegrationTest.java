package kr.ontherec.authorization.infra;

import io.restassured.RestAssured;
import kr.ontherec.authorization.infra.fixture.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(DatabaseInitializer.class)
public abstract class IntegrationTest {

    @BeforeAll
    static void setUpAll(@LocalServerPort int port) {
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/v1";
        RestAssured.port = port;
    }
}
