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
    private static final ExtentReports extent = new ExtentReports();

    private static final String MESSAGE_KEY = "message";
    private static final String USER_ID_KEY = "data.userId";

    private static final String TEST_USER_ID = UUID.randomUUID().toString();

    private static ExtentTest test;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-report.html");
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
    public void testCreateUser() {
        test = extent.createTest("testCreateUser");

        var user = new UserDTO(TEST_USER_ID, "John Doe", "john.doe@example.com", "profile.jpg");

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(user))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(UserConstants.USER_CREATED))
                .extract().response();

        boolean userCreated = response.jsonPath().getString(USER_ID_KEY) != null;
        logTestResult(test, userCreated, "User created successfully with ID: " + response.jsonPath().getString(USER_ID_KEY), "Failed to create user");

        assertNotNull(response.jsonPath().getString(USER_ID_KEY));
    }

    @Test
    @Order(2)
    public void testGetUser() {
        test = extent.createTest("testGetUser");

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
        test = extent.createTest("testUpdateUser");

        var updatedUser = new UserDTO(TEST_USER_ID, "John Updated", "john.updated@example.com", "updated.jpg");

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(updatedUser))
                .when()
                .put(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(UserConstants.USER_UPDATED))
                .body("data.name", equalTo("John Updated"))
                .extract().response();

        boolean userUpdated = response.jsonPath().getString("data.name").equals("John Updated");
        logTestResult(test, userUpdated, "User updated successfully to: John Updated", "Failed to update user");
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        test = extent.createTest("testDeleteUser");

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
