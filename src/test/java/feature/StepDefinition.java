package feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import feature.CucumberTypeConfig.FieldAssertion;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinition extends SpringIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private UserRequest payload;
    private Long createdUserId;

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

    @And("the user profile")
    public void theUserProfile(List<FieldAssertion> assertions) throws Exception {
        JsonNode body = objectMapper.readTree(latestResponse.getBody());

        for (FieldAssertion assertion : assertions) {
            JsonNode valueNode = body.get(assertion.field());
            assertNotNull(valueNode, "Field '%s' not found in response".formatted(assertion.field()));

            switch (assertion.expected()) {
                case "<generated_id>" -> {
                    createdUserId = valueNode.asLong();
                    assertTrue(createdUserId > 0);
                }
                case "<created_id>" -> {
                    assertNotNull(createdUserId);
                    assertEquals(createdUserId.toString(), valueNode.asText());
                }
                default -> assertEquals(assertion.expected(), valueNode.asText(),
                        "Mismatch on field '%s'".formatted(assertion.field()));
            }
        }
    }

    @When("the client call to GET \\/random-users\\/{int}")
    public void theClientCallToGetRandomUser(int id) {
        executeGet("/random-users/" + id);
    }

    @When("the client call to GET the created user")
    public void theClientCallToGetTheCreatedUser() {
        assertNotNull(createdUserId, "No user was created before this step");
        executeGet("/random-users/" + createdUserId);
    }

    @When("the client call to GET \\/random-users")
    public void theClientCallToGetRandomUsers() {
        executeGet("/random-users");
    }

    @When("the client call to GET \\/random-users with page {int} and size {int}")
    public void theClientCallToGetRandomUsersWithPageAndSize(int page, int size) {
        executeGet("/random-users?page=" + page + "&size=" + size);
    }

    @When("the client call to DELETE \\/random-users\\/{int}")
    public void theClientCallToDeleteRandomUser(int id) {
        executeDelete("/random-users/" + id);
    }

    @When("the client call to DELETE the created user")
    public void theClientCallToDeleteTheCreatedUser() {
        assertNotNull(createdUserId, "No user was created before this step");
        executeDelete("/random-users/" + createdUserId);
    }

    @And("the response contains a list of users")
    public void theResponseContainsAListOfUsers() throws Exception {
        JsonNode body = objectMapper.readTree(latestResponse.getBody());
        assertNotNull(body.get("data"));
        assertTrue(body.get("data").isArray());
    }

    @And("the response contains {int} users")
    public void theResponseContainsUsers(int expectedSize) throws Exception {
        JsonNode body = objectMapper.readTree(latestResponse.getBody());
        assertNotNull(body.get("data"));
        assertEquals(expectedSize, body.get("data").size());
    }
}
