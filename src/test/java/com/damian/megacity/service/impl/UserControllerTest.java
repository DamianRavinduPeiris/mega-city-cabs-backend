package com.damian.megacity.service.impl;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.damian.megacity.dto.UserDTO;
import com.google.gson.Gson;
import com.damian.megacity.service.constants.UserConstants;

import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log
public class UserControllerTest {

    private static final String BASE_URI = "http://localhost:8080/megacity/api/v1";
    private static final String BASE_PATH = "user";
    private static final Gson GSON = new Gson();
    private static final ExtentReports EXTENT = new ExtentReports();

    private static final String MESSAGE_KEY = "message";
    private static final String USER_ID_KEY = "data.userId";

    private static final String REPORT_FILE = "User-Controller-Test-Report.html";
    private static final String USER_NAME = "John Doe";
    private static final String UPDATED_USER_NAME = "John Updated";
    private static final String USER_EMAIL = "john.doe@example.com";
    private static final String UPDATED_USER_EMAIL = "john.updated@example.com";
    private static final String USER_PROFILE = "profile.jpg";
    private static final String UPDATED_USER_PROFILE = "updated.jpg";

    private static final String TEST_USER_ID = UUID.randomUUID().toString();
    private static ExtentTest test;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        var sparkReporter = new ExtentSparkReporter(REPORT_FILE);
        EXTENT.attachReporter(sparkReporter);
    }

    @AfterAll
    public static void tearDown() {
        log.info("Finalizing and flushing report...");
        EXTENT.flush();
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
    public void testCreateUser() {
        test = EXTENT.createTest("testCreateUser");

        var user = new UserDTO(TEST_USER_ID, USER_NAME, USER_EMAIL, USER_PROFILE);

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(UserConstants.USER_CREATED))
                .extract().response();

        var createdUserId = response.jsonPath().getString(USER_ID_KEY);
        boolean userCreated = createdUserId != null;
        logTestResult(test, userCreated, "User created successfully with ID: " + createdUserId, "Failed to create user");

        assertNotNull(createdUserId);
    }

    @Test
    @Order(2)
    public void testGetUser() {
        test = EXTENT.createTest("testGetUser");

        var response = given()
                .queryParam(UserConstants.USER_ID, TEST_USER_ID)
                .when()
                .get(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(UserConstants.USER_RETRIEVED))
                .body(USER_ID_KEY, equalTo(TEST_USER_ID))
                .extract().response();

        boolean userRetrieved = response.jsonPath().getString(USER_ID_KEY) != null;
        logTestResult(test, userRetrieved, "User retrieved successfully with ID: " + TEST_USER_ID, "Failed to retrieve user");
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        test = EXTENT.createTest("testUpdateUser");

        var updatedUser = new UserDTO(TEST_USER_ID, UPDATED_USER_NAME, UPDATED_USER_EMAIL, UPDATED_USER_PROFILE);

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(updatedUser))
                .when()
                .put(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(UserConstants.USER_UPDATED))
                .body("data.name", equalTo(UPDATED_USER_NAME))
                .extract().response();

        boolean userUpdated = UPDATED_USER_NAME.equals(response.jsonPath().getString("data.name"));
        logTestResult(test, userUpdated, "User updated successfully to: " + UPDATED_USER_NAME, "Failed to update user");
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        test = EXTENT.createTest("testDeleteUser");

        var response = given()
                .queryParam(UserConstants.USER_ID, TEST_USER_ID)
                .when()
                .delete(BASE_PATH)
                .then()
                .statusCode(200)
                .extract().response();

        boolean userDeleted = response.statusCode() == 200;
        logTestResult(test, userDeleted, "User deleted successfully", "Failed to delete user");
    }
}