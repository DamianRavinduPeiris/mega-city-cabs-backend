package com.damian.megacity.service.impl;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.damian.megacity.dto.DriverDTO;
import com.damian.megacity.service.constants.DriverConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.Gson;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log
public class DriverControllerTest {

    private static final String BASE_URI = "http://localhost:8080/megacity/api/v1";
    private static final String BASE_PATH = "driver";
    private static final String REPORT_PATH = "Driver-Controller-Test-Report.html";

    private static final Gson GSON = new Gson();
    private static final ExtentReports extent = new ExtentReports();

    private static final String MESSAGE_KEY = "message";
    private static final String DRIVER_ID_KEY = "data.driverId";
    private static final String DRIVER_NAME_KEY = "data.driverName";

    private static final String TEST_DRIVER_ID = UUID.randomUUID().toString();
    private static final String TEST_DRIVER_NAME = "John Doe";
    private static final String UPDATED_DRIVER_NAME = "Updated_Driver_Name";
    private static final String TEST_DRIVER_PHONE = "0772-786-091";
    private static final String TEST_DRIVER_EMAIL = "test@testmail.com";

    private static ExtentTest test;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        var sparkReporter = new ExtentSparkReporter(REPORT_PATH);
        extent.attachReporter(sparkReporter);
    }

    @AfterAll
    public static void tearDown() {
        log.info("Finalizing and flushing report...");
        extent.flush();
    }

    private static void logTestResult(ExtentTest test, boolean isSuccess, String successMessage, String failureMessage) {
        if (isSuccess) {
            test.pass(successMessage);
        } else {
            test.fail(failureMessage);
        }
    }

    @Test
    @Order(1)
    public void testCreateDriver() {
        test = extent.createTest("Create Driver");

        var driver = new DriverDTO(TEST_DRIVER_ID, TEST_DRIVER_NAME, TEST_DRIVER_PHONE, TEST_DRIVER_EMAIL);

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(driver))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(MESSAGE_KEY, equalTo(DriverConstants.DRIVER_CREATED))
                .extract().response();

        var driverId = response.jsonPath().getString(DRIVER_ID_KEY);
        boolean isDriverCreated = driverId != null;

        logTestResult(test, isDriverCreated, "Driver created successfully with ID: " + driverId, "Failed to create Driver");
        assertNotNull(driverId);
    }

    @Test
    @Order(2)
    public void testGetDriver() {
        test = extent.createTest("Get Driver");

        var response = given()
                .queryParam(DriverConstants.DRIVER_ID, TEST_DRIVER_ID)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(MESSAGE_KEY, equalTo(DriverConstants.DRIVER_RETRIEVED))
                .body(DRIVER_ID_KEY, equalTo(TEST_DRIVER_ID))
                .extract().response();

        boolean isDriverRetrieved = response.jsonPath().getString(DRIVER_ID_KEY) != null;

        logTestResult(test, isDriverRetrieved, "Driver retrieved successfully with ID: " + TEST_DRIVER_ID, "Failed to retrieve Driver.");
    }

    @Test
    @Order(3)
    public void testUpdateDriver() {
        test = extent.createTest("Update Driver");

        var updatedDriver = new DriverDTO(TEST_DRIVER_ID, UPDATED_DRIVER_NAME, TEST_DRIVER_PHONE, TEST_DRIVER_EMAIL);

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(updatedDriver))
                .when()
                .put(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(MESSAGE_KEY, equalTo(DriverConstants.DRIVER_UPDATED))
                .body(DRIVER_NAME_KEY, equalTo(UPDATED_DRIVER_NAME))
                .extract().response();

        boolean isDriverUpdated = response.jsonPath().getString(DRIVER_NAME_KEY).equals(UPDATED_DRIVER_NAME);

        logTestResult(test, isDriverUpdated, "Driver updated successfully to: " + UPDATED_DRIVER_NAME, "Failed to update Driver.");
    }

    @Test
    @Order(4)
    public void testDeleteDriver() {
        test = extent.createTest("Delete Driver");

        var response = given()
                .queryParam(DriverConstants.DRIVER_ID, TEST_DRIVER_ID)
                .when()
                .delete(BASE_PATH)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        boolean isDriverDeleted = response.statusCode() == HttpStatus.SC_OK;

        logTestResult(test, isDriverDeleted, "Driver deleted successfully", "Failed to delete Driver.");
    }
}
