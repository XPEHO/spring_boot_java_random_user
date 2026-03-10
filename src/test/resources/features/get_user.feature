Feature: Get user endpoint

  Scenario: Get a user by ID after creation
    Given a valid user payload for creation
    When the client call to POST /random-users
    Then the response status should be 201
    And the user profile
      | id | <generated_id> |
    When the client call to GET the created user
    Then the response status should be 200
    And the user profile
      | id        | <created_id> |
      | firstname | Emma |

  Scenario: Get a user that does not exist
    When the client call to GET /random-users/999
    Then the response status should be 404
