package com.damian.megacity.service.impl;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log
class DistanceControllerTest {
    private static final String BASE_URI = "http://localhost:8080/megacity/api/v1";
    private static final String BASE_PATH = "distance?origins=Moratuwa&destinations=Galle";
    private static final String REPORT_PATH = "Distance-Controller-Test-Report.html";
    
    private static final String DESTINATION_KEY = "data.destination_addresses[0]";
    private static final String ORIGIN_KEY = "data.origin_addresses[0]";
    private static final String DISTANCE_KEY = "data.rows[0].elements[0].distance.text";
    private static final String DURATION_KEY = "data.rows[0].elements[0].duration.text";
    private static final String STATUS_KEY = "data.rows[0].elements.status";

    private static final String EXPECTED_DESTINATION = "Galle, Sri Lanka";
    private static final String EXPECTED_ORIGIN = "Moratuwa, Sri Lanka";
    private static final String EXPECTED_DISTANCE = "109 km";
    private static final String EXPECTED_DURATION = "1 hour 50 mins";
    private static final String STATUS_NOT_FOUND = "NOT_FOUND";

    private static final ExtentReports extent = new ExtentReports();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
        var sparkReporter = new ExtentSparkReporter(REPORT_PATH);
        extent.attachReporter(sparkReporter);
    }

    @AfterAll
    static void tearDown() {
        log.info("Finalizing and flushing report...");
        extent.flush();
    }

    private static void logTestResult(ExtentTest test, boolean isSuccess) {
        if (isSuccess) {
            test.pass("Distance calculated successfully!");
        } else {
            test.fail("Failed to calculate distance!");
        }
    }

    @Test
    @Order(1)
    void testDistanceCalculations() {
        var test = extent.createTest("testDistanceCalculations");

        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        response.then()
                .body(DESTINATION_KEY, equalTo(EXPECTED_DESTINATION))
                .body(ORIGIN_KEY, equalTo(EXPECTED_ORIGIN))
                .body(DISTANCE_KEY, equalTo(EXPECTED_DISTANCE))
                .body(DURATION_KEY, equalTo(EXPECTED_DURATION));

        var isSuccess = !Objects.equals(response.jsonPath().getString(STATUS_KEY), STATUS_NOT_FOUND);

        logTestResult(test, isSuccess);
    }
}
