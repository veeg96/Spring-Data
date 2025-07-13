Feature: Pet Management
  As a Petistaan system user
  I want to manage pet information
  So that I can access pet details and statistics

  Background:
    Given the Petistaan application is running
    And I have access to the pet management API

  @PetCRUD
  Scenario: Find pet by valid ID
    Given a pet exists with ID "1"
    When I send a GET request to "/pets/1"
    Then the response status should be 200
    And the response should contain pet details
    And the pet ID should be "1"

  @PetCRUD
  Scenario: Find pet by invalid ID
    Given no pet exists with ID "999"
    When I send a GET request to "/pets/999"
    Then the response status should be 404
    And the response should contain "Pet not found" error message

  @PetCRUD
  Scenario: Find pet by negative ID
    When I send a GET request to "/pets/-1"
    Then the response status should be 400
    And the response should contain validation error message

  @PetStatistics
  Scenario: Get average age of domestic pets
    Given multiple domestic pets exist in the system
    When I send a GET request to "/pets/average-age"
    Then the response status should be 200
    And the response should contain average age information
    And the average age should be a positive number

  @PetStatistics
  Scenario: Get average age when no domestic pets exist
    Given no domestic pets exist in the system
    When I send a GET request to "/pets/average-age"
    Then the response status should be 200
    And the response should contain average age of 0

  @PetDetails
  Scenario: Get domestic pet details
    Given a domestic pet exists with ID "1"
    When I send a GET request to "/pets/1"
    Then the response status should be 200
    And the response should contain domestic pet details
    And the pet should have a date of birth

  @PetDetails
  Scenario: Get wild pet details
    Given a wild pet exists with ID "37"
    When I send a GET request to "/pets/37"
    Then the response status should be 200
    And the response should contain wild pet details
    And the pet should have a place of birth

  @PetValidation
  Scenario: Access pet with invalid ID format
    When I send a GET request to "/pets/abc"
    Then the response status should be 400
    And the response should contain validation error message

  @PetValidation
  Scenario: Access pet with zero ID
    When I send a GET request to "/pets/0"
    Then the response status should be 400
    And the response should contain validation error message 