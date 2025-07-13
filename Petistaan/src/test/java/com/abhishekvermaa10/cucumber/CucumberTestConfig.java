package com.abhishekvermaa10.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.abhishekvermaa10.Demo;

import io.cucumber.spring.CucumberContextConfiguration;

/**
 * Cucumber test configuration for Petistaan application
 * 
 * @author abhishekvermaa10
 */
@CucumberContextConfiguration
@SpringBootTest(classes = Demo.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CucumberTestConfig {
    // This class provides the Spring context for Cucumber tests
    // The @CucumberContextConfiguration annotation tells Cucumber to use this class
    // for Spring context configuration
} 