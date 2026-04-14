Feature: GET /random-users endpoint

  Scenario: Fetch random users with default parameters
    When the client call to GET /random-users
    Then the response status should be 200
    And the response contains a list of users

  Scenario: Fetch random users with custom page and size
    When the client call to GET /random-users with page 1 and size 5
    Then the response status should be 200
    And the response contains 5 users

  Scenario: Fetch random users with size above maximum
    When the client call to GET /random-users with page 1 and size 31
    Then the response status should be 400
