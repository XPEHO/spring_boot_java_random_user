Feature: Create user endpoint

  Scenario: Create a user successfully
    Given a valid user payload for creation
    When the client call to POST /random-users
    Then the response status should be 201
    And the user profile
      | id        | 1    |
      | firstname | Emma |
