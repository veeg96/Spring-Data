package com.abhishekvermaa10.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Cucumber test runner for Petistaan application
 * 
 * @author abhishekvermaa10
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "com.abhishekvermaa10.cucumber.steps",
        "com.abhishekvermaa10.cucumber"
    },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/CucumberTestReport.json",
        "junit:target/cucumber-reports/CucumberTestReport.xml"
    },
    monochrome = true,
    tags = "not @Ignore"
)
public class CucumberTestRunner {
    // This class runs all Cucumber tests
    // Features are located in src/test/resources/features
    // Step definitions are in com.abhishekvermaa10.cucumber.steps package
    // Reports are generated in target/cucumber-reports directory
} 