package com.damian.megacity.service.impl;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.damian.megacity.dto.RideBookingDTO;
import com.damian.megacity.service.constants.RideBookingConstants;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.java.Log;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log
public class RideBookingControllerTest {
    private static final String BASE_URI = "http://localhost:8080/megacity/api/v1";
    private static final String BASE_PATH = "bookings?userEmail=drpeiris3@gmail.com";
    private static final String REPORT_PATH = "Booking-Controller-Test-Report.html";
    private static final String MESSAGE_KEY = "message";
    private static final String ORDER_ID_KEY = "data.orderId";
    private static final String DATA_KEY = "data";

    private static final String TEST_USER_ID = "d8bcd381-00f6-48ed-9db3-2cbfd7398e95";
    private static final String TEST_DRIVER_ID = "1ae16e31-21c0-4768-bca8-9669a7f20b73";
    private static final String TEST_VEHICLE_ID = "28dbf03a-cfc7-4f10-aa1a-fa46a59e2222";
    private static final String TEST_VEHICLE_MODEL = "Wagon R";
    private static final String TEST_VEHICLE_PLATE = "KL-07-AA";
    private static final String TEST_PICKUP = "Moratuwa";
    private static final String TEST_DROPOFF = "Galle";
    private static final String TEST_DURATION = "1 Hour 30 Minutes";
    private static final String TEST_DATE_TIME = "2021-09-01T10:00:00";
    private static final double TEST_FARE = 10000.00;
    private static final String TEST_NAME = "Damian Peiris";

    private static final Gson GSON = new Gson();
    private static final ExtentReports extent = new ExtentReports();
    private static final String TEST_BOOKING_ID = UUID.randomUUID().toString();

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
    public void testCreateBooking() {
        var test = extent.createTest("testCreateBooking");

        var bookingDetails = new RideBookingDTO(
                TEST_BOOKING_ID,
                TEST_USER_ID,
                TEST_NAME,
                TEST_DRIVER_ID,
                TEST_VEHICLE_ID,
                TEST_VEHICLE_MODEL,
                TEST_VEHICLE_PLATE,
                TEST_PICKUP,
                TEST_DROPOFF,
                TEST_DURATION,
                TEST_DATE_TIME,
                TEST_FARE
        );

        var response = given()
                .contentType(ContentType.JSON)
                .body(GSON.toJson(bookingDetails))
                .when()
                .post(BASE_PATH)
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(RideBookingConstants.BOOKING_ADDED_SUCCESSFULLY))
                .extract().response();

        var bookingCreated = response.jsonPath().getString(DATA_KEY) != null;
        logTestResult(test, bookingCreated, "Booking added successfully with ID: " + response.jsonPath().getString(ORDER_ID_KEY), "Failed to create the booking!");

        assertNotNull(response.jsonPath().getString(DATA_KEY));
    }

    @Test
    @Order(2)
    public void testGetAllBookings() {
        var test = extent.createTest("testGetAllBookings");

        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/bookings")
                .then()
                .statusCode(200)
                .body(MESSAGE_KEY, equalTo(RideBookingConstants.BOOKINGS_RETRIEVED_SUCCESSFULLY))
                .extract().response();

        var bookingsRetrieved = response.jsonPath().getString(DATA_KEY) != null;
        logTestResult(test, bookingsRetrieved, "Bookings retrieved successfully!", "Failed to fetch bookings!");

        assertNotNull(response.jsonPath().getString(DATA_KEY));
    }
}
