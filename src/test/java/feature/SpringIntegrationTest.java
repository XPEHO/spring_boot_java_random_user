package feature;

import com.xpeho.spring_boot_java_random_user.SpringBootJavaRandomUserApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
@SpringBootTest(
    classes = SpringBootJavaRandomUserApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
        "spring.sql.init.mode=always",
        "spring.sql.init.schema-locations=classpath:schema-h2.sql"
    }
)
public class SpringIntegrationTest {

    @Value("${app.security.admin.username}")
    private String testUsername;

    @Value("${app.security.admin.password}")
    private String testPassword;

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    protected ResponseEntity<String> latestResponse;

    protected void executeGet(String path) {
        String url = "http://localhost:" + port + path;
        latestResponse = restTemplate
            .withBasicAuth(testUsername, testPassword)
            .getForEntity(url, String.class);
    }

    protected void executePost(String path, Object payload) {
        String url = "http://localhost:" + port + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(payload, headers);
        latestResponse = restTemplate
            .withBasicAuth(testUsername, testPassword)
            .postForEntity(url, request, String.class);
    }

    protected void executeDelete(String path) {
        String url = "http://localhost:" + port + path;
        latestResponse = restTemplate
            .withBasicAuth(testUsername, testPassword)
            .exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    protected void executePut(String path, Object payload) {
        String url = "http://localhost:" + port + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(payload, headers);
        latestResponse = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
    }
}
