package com.abhishekvermaa10.cucumber.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Shared step definitions for common scenarios
 * 
 * @author abhishekvermaa10
 */
public class SharedSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<?> response;
    private String baseUrl = "/petistaan";

    @Given("the Petistaan application is running")
    public void thePetistaanApplicationIsRunning() {
        // Application is already running due to @SpringBootTest
        assertNotNull(restTemplate);
    }

    @Given("I have access to the API endpoints")
    public void iHaveAccessToTheApiEndpoints() {
        // API access is available through TestRestTemplate
        assertNotNull(restTemplate);
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        response = restTemplate.getForEntity(baseUrl + endpoint, Object.class);
    }

    @When("I send a PUT request to {string}")
    public void iSendAPutRequestTo(String endpoint) {
        response = restTemplate.exchange(baseUrl + endpoint, org.springframework.http.HttpMethod.PUT, null, Object.class);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(HttpStatus.valueOf(expectedStatus), response.getStatusCode());
    }

    @Then("the response should contain {string} error")
    public void theResponseShouldContainError(String errorType) {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains(errorType.toLowerCase()));
    }

    @Then("the response should contain validation error message")
    public void theResponseShouldContainValidationErrorMessage() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Then("the response should contain not found error")
    public void theResponseShouldContainNotFoundError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("not found"));
    }

    @Then("the response should contain method not allowed error")
    public void theResponseShouldContainMethodNotAllowedError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("method"));
    }
} 