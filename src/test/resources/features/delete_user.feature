Feature: Delete user endpoint

  Scenario: Delete a user successfully after creation
    Given a valid user payload for creation
    When the client call to POST /random-users
    Then the response status should be 201
    And the user profile
      | id | <generated_id> |
      | firstname | Emma |
    When the client call to DELETE the created user
    Then the response status should be 204
    When the client call to GET the deleted user
    Then the response status should be 404

  Scenario: Delete a user that does not exist
    When the client call to DELETE /random-users/999
    Then the response status should be 404
