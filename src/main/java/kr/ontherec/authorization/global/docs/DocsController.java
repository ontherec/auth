
package kr.ontherec.authorization.global.docs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/.well-known/openapi-specification")
public class DocsController {

	@GetMapping(produces = "application/json")
	public byte[] getOpenApiSpec() throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources/static/openapi3.json"));
	}
}