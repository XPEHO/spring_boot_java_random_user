package feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinition extends SpringIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserRequest payload;

    @Given("a valid user payload for creation")
    public void aValidUserPayloadForCreation() {
        payload = new UserRequest(
            "female",
            "Emma",
            "Stone",
            "Ms",
            "emma@example.com",
            "0644444444",
            "emma.jpg",
            "FR"
        );
    }

    @When("the client call to POST \\/random-users")
    public void theClientCallToPostRandomUser() {
        executePost("/random-users", payload);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, latestResponse.getStatusCode().value());
    }

    @And("the response should contain the created user id {long}")
    public void theResponseShouldContainTheCreatedUserId(long expectedId) throws Exception {
        JsonNode body = objectMapper.readTree(latestResponse.getBody());
        assertEquals(expectedId, body.get("id").asLong());
    }

    @And("the response should contain the firstname {string}")
    public void theResponseShouldContainTheFirstname(String expectedFirstname) throws Exception {
        JsonNode body = objectMapper.readTree(latestResponse.getBody());
        assertEquals(expectedFirstname, body.get("firstname").asText());
    }
}
