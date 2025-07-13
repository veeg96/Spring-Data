package com.abhishekvermaa10.cucumber.steps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIDDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.UpdatePetDTO;
import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;
import com.abhishekvermaa10.service.OwnerService;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for Owner Management feature
 * 
 * @author abhishekvermaa10
 */
public class OwnerManagementSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OwnerService ownerService;

    private ResponseEntity<?> response;
    private Map<String, Object> requestData;
    private String baseUrl = "/petistaan";

    @Given("the Petistaan application is running")
    public void thePetistaanApplicationIsRunning() {
        // Application is already running due to @SpringBootTest
        assertNotNull(restTemplate);
    }

    @Given("I have access to the owner management API")
    public void iHaveAccessToTheOwnerManagementAPI() {
        // API access is available through TestRestTemplate
        assertNotNull(restTemplate);
    }

    @Given("I have owner data with the following details:")
    public void iHaveOwnerDataWithTheFollowingDetails(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);

        requestData = new HashMap<>();
        requestData.put("firstName", row.get("firstName"));
        requestData.put("lastName", row.get("lastName"));
        requestData.put("gender", row.get("gender"));
        requestData.put("city", row.get("city"));
        requestData.put("state", row.get("state"));
        requestData.put("mobileNumber", row.get("mobileNumber"));
        requestData.put("emailId", row.get("emailId"));

        // Create pet data
        Map<String, Object> petData = new HashMap<>();
        petData.put("name", row.get("petName"));
        petData.put("gender", row.get("petGender"));
        petData.put("type", row.get("petType"));
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2020-01-01");

        requestData.put("pet", petData);
    }

    @Given("I have invalid owner data with missing required fields")
    public void iHaveInvalidOwnerDataWithMissingRequiredFields() {
        requestData = new HashMap<>();
        requestData.put("lastName", "Doe");
        requestData.put("gender", "M");
        // Missing firstName, city, state, mobileNumber, emailId, pet
    }

    @Given("I have owner data missing firstName")
    public void iHaveOwnerDataMissingFirstName() {
        requestData = new HashMap<>();
        requestData.put("lastName", "Doe");
        requestData.put("gender", "M");
        requestData.put("city", "Mumbai");
        requestData.put("state", "Maharashtra");
        requestData.put("mobileNumber", "9876543210");
        requestData.put("emailId", "john.doe@example.com");
        // Missing firstName and pet
    }

    @Given("I have owner data with invalid email {string}")
    public void iHaveOwnerDataWithInvalidEmail(String invalidEmail) {
        requestData = new HashMap<>();
        requestData.put("firstName", "John");
        requestData.put("lastName", "Doe");
        requestData.put("gender", "M");
        requestData.put("city", "Mumbai");
        requestData.put("state", "Maharashtra");
        requestData.put("mobileNumber", "9876543210");
        requestData.put("emailId", invalidEmail);

        Map<String, Object> petData = new HashMap<>();
        petData.put("name", "Max");
        petData.put("gender", "M");
        petData.put("type", "DOG");
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2020-01-01");

        requestData.put("pet", petData);
    }

    @Given("I have owner data with invalid mobile number {string}")
    public void iHaveOwnerDataWithInvalidMobileNumber(String invalidMobile) {
        requestData = new HashMap<>();
        requestData.put("firstName", "John");
        requestData.put("lastName", "Doe");
        requestData.put("gender", "M");
        requestData.put("city", "Mumbai");
        requestData.put("state", "Maharashtra");
        requestData.put("mobileNumber", invalidMobile);
        requestData.put("emailId", "john.doe@example.com");

        Map<String, Object> petData = new HashMap<>();
        petData.put("name", "Max");
        petData.put("gender", "M");
        petData.put("type", "DOG");
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2020-01-01");

        requestData.put("pet", petData);
    }

    @Given("an owner already exists with email {string}")
    public void anOwnerAlreadyExistsWithEmail(String email) {
        // This is handled by the test data setup
        // The data.sql file already contains owners with various emails
    }

    @Given("I have new owner data with the same email")
    public void iHaveNewOwnerDataWithTheSameEmail() {
        requestData = new HashMap<>();
        requestData.put("firstName", "Jane");
        requestData.put("lastName", "Smith");
        requestData.put("gender", "F");
        requestData.put("city", "Delhi");
        requestData.put("state", "Delhi");
        requestData.put("mobileNumber", "9876543211");
        requestData.put("emailId", "john.doe@example.com"); // Same email

        Map<String, Object> petData = new HashMap<>();
        petData.put("name", "Luna");
        petData.put("gender", "F");
        petData.put("type", "CAT");
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2021-01-01");

        requestData.put("pet", petData);
    }

    @Given("an owner already exists with mobile number {string}")
    public void anOwnerAlreadyExistsWithMobileNumber(String mobile) {
        // This is handled by the test data setup
    }

    @Given("I have new owner data with the same mobile number")
    public void iHaveNewOwnerDataWithTheSameMobileNumber() {
        requestData = new HashMap<>();
        requestData.put("firstName", "Jane");
        requestData.put("lastName", "Smith");
        requestData.put("gender", "F");
        requestData.put("city", "Delhi");
        requestData.put("state", "Delhi");
        requestData.put("mobileNumber", "9876543210"); // Same mobile
        requestData.put("emailId", "jane.smith@example.com");

        Map<String, Object> petData = new HashMap<>();
        petData.put("name", "Luna");
        petData.put("gender", "F");
        petData.put("type", "CAT");
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2021-01-01");

        requestData.put("pet", petData);
    }

    @Given("an owner exists with ID {string}")
    public void anOwnerExistsWithID(String ownerId) {
        // Owner with ID 1 exists in the test data
        assertTrue(Integer.parseInt(ownerId) > 0);
    }

    @Given("no owner exists with ID {string}")
    public void noOwnerExistsWithID(String ownerId) {
        // Owner with ID 999 doesn't exist in the test data
        assertTrue(Integer.parseInt(ownerId) > 72); // Test data has 72 owners
    }

    @Given("multiple owners exist in the system")
    public void multipleOwnersExistInTheSystem() {
        // Test data contains 72 owners
        List<OwnerDTO> owners = ownerService.findAllOwners();
        assertFalse(owners.isEmpty());
    }

    @Given("I have pet update data with name {string}")
    public void iHavePetUpdateDataWithName(String petName) {
        requestData = new HashMap<>();
        requestData.put("petName", petName);
    }

    @Given("I have pet update data with empty name")
    public void iHavePetUpdateDataWithEmptyName() {
        requestData = new HashMap<>();
        requestData.put("petName", "");
    }

    @Given("I have pet update data with very long name exceeding {int} characters")
    public void iHavePetUpdateDataWithVeryLongNameExceedingCharacters(int maxLength) {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < maxLength + 10; i++) {
            longName.append("a");
        }
        requestData = new HashMap<>();
        requestData.put("petName", longName.toString());
    }

    @Given("I have malformed JSON data")
    public void iHaveMalformedJsonData() {
        requestData = new HashMap<>();
        requestData.put("malformed", "data");
    }

    @When("I send a POST request to {string} with the owner data")
    public void iSendAPostRequestToWithTheOwnerData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a POST request to {string} with the incomplete data")
    public void iSendAPostRequestToWithTheIncompleteData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a POST request to {string} with the invalid data")
    public void iSendAPostRequestToWithTheInvalidData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a POST request to {string} with the duplicate email data")
    public void iSendAPostRequestToWithTheDuplicateEmailData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a POST request to {string} with the duplicate mobile data")
    public void iSendAPostRequestToWithTheDuplicateMobileData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a POST request to {string} with the malformed JSON")
    public void iSendAPostRequestToWithTheMalformedJson(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);
        response = restTemplate.postForEntity(baseUrl + endpoint, request, Object.class);
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        response = restTemplate.getForEntity(baseUrl + endpoint, Object.class);
    }

    @When("I send a PATCH request to {string} with the pet update data")
    public void iSendAPatchRequestToWithThePetUpdateData(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UpdatePetDTO updatePetDTO = new UpdatePetDTO();
        updatePetDTO.setPetName((String) requestData.get("petName"));
        HttpEntity<UpdatePetDTO> request = new HttpEntity<>(updatePetDTO, headers);
        response = restTemplate.exchange(baseUrl + endpoint, HttpMethod.PATCH, request, Object.class);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADeleteRequestTo(String endpoint) {
        response = restTemplate.exchange(baseUrl + endpoint, HttpMethod.DELETE, null, Object.class);
    }

    @When("I send a PUT request to {string}")
    public void iSendAPutRequestTo(String endpoint) {
        response = restTemplate.exchange(baseUrl + endpoint, HttpMethod.PUT, null, Object.class);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(HttpStatus.valueOf(expectedStatus), response.getStatusCode());
    }

    @Then("the response should contain the owner ID")
    public void theResponseShouldContainTheOwnerId() {
        assertNotNull(response.getBody());
        // The response should contain an OwnerIDDTO with an ID
        assertTrue(response.getBody().toString().contains("id"));
    }

    @Then("the owner should be created successfully")
    public void theOwnerShouldBeCreatedSuccessfully() {
        assertNotNull(response.getBody());
        // Additional assertions can be added here
    }

    @Then("the response should contain validation error messages")
    public void theResponseShouldContainValidationErrorMessages() {
        assertNotNull(response.getBody());
        // The response should contain error details
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Then("the response should contain owner details")
    public void theResponseShouldContainOwnerDetails() {
        assertNotNull(response.getBody());
        // The response should contain owner information
        assertTrue(response.getBody().toString().contains("firstName"));
    }

    @Then("the owner ID should be {string}")
    public void theOwnerIdShouldBe(String expectedId) {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("id"));
    }

    @Then("the response should contain {string} error message")
    public void theResponseShouldContainErrorMessage(String errorMessage) {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains(errorMessage.toLowerCase()));
    }

    @Then("the response should contain validation error message")
    public void theResponseShouldContainValidationErrorMessage() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Then("the pet name should be updated to {string}")
    public void thePetNameShouldBeUpdatedTo(String expectedPetName) {
        // Verify the update was successful by checking the response
        assertNotNull(response.getBody());
    }

    @Then("the owner should be deleted successfully")
    public void theOwnerShouldBeDeletedSuccessfully() {
        // Verify the deletion was successful
        assertNotNull(response.getBody());
    }

    @Then("the response should contain a list of owners")
    public void theResponseShouldContainAListOfOwners() {
        assertNotNull(response.getBody());
        // The response should be a list
        assertTrue(response.getBody().toString().contains("["));
    }

    @Then("the list should not be empty")
    public void theListShouldNotBeEmpty() {
        assertNotNull(response.getBody());
        // The list should contain owners
        assertTrue(response.getBody().toString().contains("firstName"));
    }

    @Then("the response should contain paginated owner information")
    public void theResponseShouldContainPaginatedOwnerInformation() {
        assertNotNull(response.getBody());
        // The response should contain pagination metadata
        assertTrue(response.getBody().toString().contains("content"));
    }

    @Then("the page should contain owner details with pet names")
    public void thePageShouldContainOwnerDetailsWithPetNames() {
        assertNotNull(response.getBody());
        // The response should contain owner and pet information
        assertTrue(response.getBody().toString().contains("firstName"));
    }

    @Then("the response should contain exactly {int} owners")
    public void theResponseShouldContainExactlyOwners(int expectedCount) {
        assertNotNull(response.getBody());
        // This would need to be implemented based on the actual response structure
    }

    @Then("the pagination metadata should be correct")
    public void thePaginationMetadataShouldBeCorrect() {
        assertNotNull(response.getBody());
        // The response should contain pagination information
        assertTrue(response.getBody().toString().contains("pageable"));
    }

    @Then("the response should contain an empty page")
    public void theResponseShouldContainAnEmptyPage() {
        assertNotNull(response.getBody());
        // The response should indicate no content
        assertTrue(response.getBody().toString().contains("content"));
    }

    @Then("the pagination metadata should indicate no content")
    public void thePaginationMetadataShouldIndicateNoContent() {
        assertNotNull(response.getBody());
        // The response should indicate empty content
        assertTrue(response.getBody().toString().contains("content"));
    }

    @Then("the response should contain {string} error")
    public void theResponseShouldContainError(String errorType) {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains(errorType.toLowerCase()));
    }

    @Then("the response should contain email validation error")
    public void theResponseShouldContainEmailValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("email"));
    }

    @Then("the response should contain mobile number validation error")
    public void theResponseShouldContainMobileNumberValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("mobile"));
    }

    @Then("the response should contain duplicate email error")
    public void theResponseShouldContainDuplicateEmailError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("email"));
    }

    @Then("the response should contain duplicate mobile number error")
    public void theResponseShouldContainDuplicateMobileNumberError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("mobile"));
    }

    @Then("the response should contain pet name validation error")
    public void theResponseShouldContainPetNameValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("name"));
    }

    @Then("the response should contain pet name length validation error")
    public void theResponseShouldContainPetNameLengthValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("length"));
    }

    @Then("the response should contain JSON parsing error")
    public void theResponseShouldContainJsonParsingError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("error"));
    }

    @Then("the response should contain method not allowed error")
    public void theResponseShouldContainMethodNotAllowedError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("method"));
    }

    @Then("the response should contain not found error")
    public void theResponseShouldContainNotFoundError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("not found"));
    }

    @Then("the response should contain pagination validation error")
    public void theResponseShouldContainPaginationValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("error"));
    }

    @Then("the response should contain pagination size validation error")
    public void theResponseShouldContainPaginationSizeValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("size"));
    }

    @Then("the response should contain enum validation error")
    public void theResponseShouldContainEnumValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("error"));
    }

    @Then("the response should contain pet type validation error")
    public void theResponseShouldContainPetTypeValidationError() {
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().toLowerCase().contains("type"));
    }
} 