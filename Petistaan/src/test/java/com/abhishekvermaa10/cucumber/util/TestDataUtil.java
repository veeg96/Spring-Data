package com.abhishekvermaa10.cucumber.util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;

/**
 * Test data utility for Cucumber tests
 * 
 * @author abhishekvermaa10
 */
public class TestDataUtil {

    /**
     * Creates a valid owner data map for testing
     */
    public static Map<String, Object> createValidOwnerData() {
        Map<String, Object> ownerData = new HashMap<>();
        ownerData.put("firstName", "John");
        ownerData.put("lastName", "Doe");
        ownerData.put("gender", "M");
        ownerData.put("city", "Mumbai");
        ownerData.put("state", "Maharashtra");
        ownerData.put("mobileNumber", "9876543210");
        ownerData.put("emailId", "john.doe@example.com");

        Map<String, Object> petData = new HashMap<>();
        petData.put("name", "Max");
        petData.put("gender", "M");
        petData.put("type", "DOG");
        petData.put("category", "Domestic");
        petData.put("dateOfBirth", "2020-01-01");

        ownerData.put("pet", petData);
        return ownerData;
    }

    /**
     * Creates an invalid owner data map for testing validation
     */
    public static Map<String, Object> createInvalidOwnerData() {
        Map<String, Object> ownerData = new HashMap<>();
        ownerData.put("lastName", "Doe");
        ownerData.put("gender", "M");
        // Missing required fields: firstName, city, state, mobileNumber, emailId, pet
        return ownerData;
    }

    /**
     * Creates owner data with invalid email
     */
    public static Map<String, Object> createOwnerDataWithInvalidEmail(String invalidEmail) {
        Map<String, Object> ownerData = createValidOwnerData();
        ownerData.put("emailId", invalidEmail);
        return ownerData;
    }

    /**
     * Creates owner data with invalid mobile number
     */
    public static Map<String, Object> createOwnerDataWithInvalidMobile(String invalidMobile) {
        Map<String, Object> ownerData = createValidOwnerData();
        ownerData.put("mobileNumber", invalidMobile);
        return ownerData;
    }

    /**
     * Creates owner data with duplicate email
     */
    public static Map<String, Object> createOwnerDataWithDuplicateEmail(String existingEmail) {
        Map<String, Object> ownerData = createValidOwnerData();
        ownerData.put("emailId", existingEmail);
        ownerData.put("firstName", "Jane");
        ownerData.put("lastName", "Smith");
        ownerData.put("mobileNumber", "9876543211");
        return ownerData;
    }

    /**
     * Creates owner data with duplicate mobile number
     */
    public static Map<String, Object> createOwnerDataWithDuplicateMobile(String existingMobile) {
        Map<String, Object> ownerData = createValidOwnerData();
        ownerData.put("mobileNumber", existingMobile);
        ownerData.put("firstName", "Jane");
        ownerData.put("lastName", "Smith");
        ownerData.put("emailId", "jane.smith@example.com");
        return ownerData;
    }

    /**
     * Creates pet update data
     */
    public static Map<String, Object> createPetUpdateData(String petName) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("petName", petName);
        return updateData;
    }

    /**
     * Creates malformed JSON data for testing error handling
     */
    public static Map<String, Object> createMalformedJsonData() {
        Map<String, Object> malformedData = new HashMap<>();
        malformedData.put("malformed", "data");
        malformedData.put("invalid", "json");
        return malformedData;
    }

    /**
     * Creates owner data with invalid gender
     */
    public static Map<String, Object> createOwnerDataWithInvalidGender(String invalidGender) {
        Map<String, Object> ownerData = createValidOwnerData();
        ownerData.put("gender", invalidGender);
        return ownerData;
    }

    /**
     * Creates owner data with invalid pet type
     */
    public static Map<String, Object> createOwnerDataWithInvalidPetType(String invalidPetType) {
        Map<String, Object> ownerData = createValidOwnerData();
        Map<String, Object> petData = (Map<String, Object>) ownerData.get("pet");
        petData.put("type", invalidPetType);
        return ownerData;
    }

    /**
     * Creates owner data with very long field values for length validation
     */
    public static Map<String, Object> createOwnerDataWithLongFields() {
        Map<String, Object> ownerData = createValidOwnerData();
        
        // Create very long strings
        StringBuilder longString = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longString.append("a");
        }
        
        ownerData.put("firstName", longString.toString());
        ownerData.put("lastName", longString.toString());
        ownerData.put("city", longString.toString());
        ownerData.put("state", longString.toString());
        
        return ownerData;
    }

    /**
     * Creates pet update data with very long pet name
     */
    public static Map<String, Object> createPetUpdateDataWithLongName(int length) {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < length; i++) {
            longName.append("a");
        }
        
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("petName", longName.toString());
        return updateData;
    }

    /**
     * Gets a valid owner ID from test data
     */
    public static int getValidOwnerId() {
        return 1; // Owner with ID 1 exists in test data
    }

    /**
     * Gets an invalid owner ID (non-existent)
     */
    public static int getInvalidOwnerId() {
        return 999; // Owner with ID 999 doesn't exist in test data
    }

    /**
     * Gets a valid pet ID from test data
     */
    public static int getValidPetId() {
        return 1; // Pet with ID 1 exists in test data
    }

    /**
     * Gets an invalid pet ID (non-existent)
     */
    public static int getInvalidPetId() {
        return 999; // Pet with ID 999 doesn't exist in test data
    }

    /**
     * Gets a valid domestic pet ID from test data
     */
    public static int getValidDomesticPetId() {
        return 1; // Domestic pet with ID 1 exists in test data
    }

    /**
     * Gets a valid wild pet ID from test data
     */
    public static int getValidWildPetId() {
        return 37; // Wild pet with ID 37 exists in test data
    }
} 