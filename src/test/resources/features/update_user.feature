Feature: Update user endpoint

  Scenario: Update a user by ID after creation
    Given a valid user payload for creation
    When the client call to POST /random-users
    Then the response status should be 201
    And the user profile
      | id        | <generated_id> |
      | firstname | Emma |
    Given a valid user payload for update
    When the client call to PUT the created user
    Then the response status should be 200
    And the user profile
      | firstname | John |
      | lastname  | Doe |

  Scenario: Update a user that does not exist
    Given a valid user payload for update
    When the client call to PUT /random-users/999999
    Then the response status should be 404

