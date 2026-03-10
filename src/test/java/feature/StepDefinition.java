package feature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserRequest;
import io.cucumber.datatable.DataTable;
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
    public void theUserProfile(DataTable table) throws Exception{
        JsonNode body = objectMapper.readTree(latestResponse.getBody());
        List<List<String>> rows = table.asLists(String.class);
        for (List<String> row : rows) {
            String field = row.get(0).trim();
            String expected = row.get(1).trim();
            JsonNode valueNode = body.get(field);
            assertNotNull(valueNode);

            if ("<generated_id>".equals(expected)) {
                createdUserId = valueNode.asLong();
                assertTrue(createdUserId > 0);
                continue;
            }
            if ("<created_id>".equals(expected)) {
                assertNotNull(createdUserId);
                assertEquals(createdUserId.toString(), valueNode.asText());
                continue;
            }
            assertEquals(expected, valueNode.asText());
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
}
